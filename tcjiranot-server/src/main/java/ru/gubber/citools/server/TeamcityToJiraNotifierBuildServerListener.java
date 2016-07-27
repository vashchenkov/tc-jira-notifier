package ru.gubber.citools.server;

import jetbrains.buildServer.BuildAgent;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.BuildType;
import jetbrains.buildServer.issueTracker.Issue;
import jetbrains.buildServer.messages.BuildMessage1;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.responsibility.ResponsibilityEntry;
import jetbrains.buildServer.responsibility.TestNameResponsibilityEntry;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.mute.MuteInfo;
import jetbrains.buildServer.serverSide.problems.BuildProblemInfo;
import jetbrains.buildServer.tests.TestName;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.util.EventDispatcher;
import jetbrains.buildServer.vcs.SVcsRoot;
import jetbrains.buildServer.vcs.VcsModification;
import jetbrains.buildServer.vcs.VcsRoot;
import org.jetbrains.annotations.NotNull;
import ru.gubber.citools.TJPConstants;
import ru.gubber.citools.context.JiraLabelSetterContext;
import ru.gubber.citools.nitifiers.JiraUser;
import ru.gubber.citools.nitifiers.NotifierFactory;
import ru.gubber.citools.utils.PropertiesExtractor;
import ru.gubber.citools.utils.PropertiesToLabelEvaluator;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by gubber on 20.10.2015.
 */
public class TeamcityToJiraNotifierBuildServerListener implements BuildServerListener, TJPConstants {

	private PropertiesExtractor propertiesExtractor;
	private PropertiesToLabelEvaluator propertiesToLabelEvaluator;

	public TeamcityToJiraNotifierBuildServerListener(@NotNull EventDispatcher<BuildServerListener> eventDispatcher,
	                                                 @NotNull PropertiesExtractor propertiesExtractor,
	                                                 @NotNull PropertiesToLabelEvaluator propertiesToLabelEvaluator) {
		eventDispatcher.addListener(this);
		this.propertiesExtractor = propertiesExtractor;
		this.propertiesToLabelEvaluator = propertiesToLabelEvaluator;
	}

	@Override
	public void agentRegistered(@NotNull SBuildAgent agent, long currentlyRunningBuildId) {
		//ignored
	}

	@Override
	public void agentDescriptionUpdated(@NotNull SBuildAgent agent) {
		//ignored
	}

	@Override
	public void beforeAgentUnregistered(@NotNull SBuildAgent agent) {
		//ignored
	}

	@Override
	public void agentUnregistered(@NotNull SBuildAgent agent) {
		//ignored
	}

	@Override
	public void agentStatusChanged(@NotNull SBuildAgent agent, boolean wasEnabled, boolean wasAuthorized) {
		//ignored
	}

	@Override
	public void agentRemoved(@NotNull SBuildAgent agent) {
		//ignored
	}

	@Override
	public void buildTypeAddedToQueue(@NotNull SBuildType buildType) {
		//ignored
	}

	@Override
	public void buildTypeAddedToQueue(@NotNull SQueuedBuild queuedBuild) {
		//ignored
	}

	@Override
	public void buildRemovedFromQueue(@NotNull SQueuedBuild queuedBuild, User user, String comment) {
		//ignored
	}

	@Override
	public void buildPinned(@NotNull SBuild build, User user, String comment) {
		//ignored
	}

	@Override
	public void buildUnpinned(@NotNull SBuild build, User user, String comment) {
		//ignored
	}

	@Override
	public void buildQueueOrderChanged() {
		//ignored
	}

	@Override
	public void buildTypeRegistered(@NotNull SBuildType buildType) {
		//ignored
	}

	@Override
	public void buildTypeUnregistered(@NotNull SBuildType buildType) {
		//ignored
	}

	@Override
	public void buildTypeMoved(@NotNull SBuildType buildType, @NotNull SProject original) {
		//ignored
	}

	@Override
	public void buildTypeTemplateExternalIdChanged(@NotNull BuildTypeTemplate buildTypeTemplate, @NotNull String oldExternalId, @NotNull String newExternalId) {
		//ignored
	}

	@Override
	public void buildTypeExternalIdChanged(@NotNull SBuildType buildType, @NotNull String oldExternalId, @NotNull String newExternalId) {
		//ignored
	}

	@Override
	public void projectMoved(@NotNull SProject project, @NotNull SProject originalParentProject) {
		//ignored
	}

	@Override
	public void beforeBuildTypeDeleted(@NotNull String buildTypeId) {
		//ignored
	}

	@Override
	public void buildTypeDeleted(@NotNull String buildTypeId) {
		//ignored
	}

	@Override
	public void buildTypeTemplateDeleted(@NotNull String buildTypeTemplateId) {
		//ignored
	}

	@Override
	public void buildTypeActiveStatusChanged(@NotNull SBuildType buildType) {
		//ignored
	}

	@Override
	public void buildStarted(@NotNull SRunningBuild build) {
		//ignored
	}

	@Override
	public void changesLoaded(@NotNull SRunningBuild build) {
		//ignored
	}

	@Override
	public void buildChangedStatus(@NotNull SRunningBuild build, Status oldStatus, Status newStatus) {
		//ignored
	}

	@Override
	public void beforeBuildFinish(@NotNull SRunningBuild runningBuild) {
		//ignored
	}

	@Override
	public void buildFinished(@NotNull SRunningBuild build) {

		Collection<Issue> relatedIssues = collectIssues(build);

		JiraLabelSetterContext context = JiraLabelSetterContext.initializeFromFeatureDescriptor(build, propertiesExtractor, propertiesToLabelEvaluator);
		if (context == null) {
			logger.warn("Jira teamcity connector finished. Context wasn't initialized.");
			return;
		}

		if (logger.isDebugEnabled())
			logger.debug(String.format("Jira teamcity connector starts set labels. {label:%s, customFieldId:%s, labelSetterPolicy:%s } for %s issue(s).",
					new Object[]{context.getLabels(), context.getCustomField(), context.getLabelSetterPolicy(), relatedIssues.size()}));
		if (relatedIssues.size() > 0) {
			Map<JiraUser, List<Issue>> issues = new HashMap<>();
			relatedIssues.forEach(issue -> {
				JiraUser jiraUser = new JiraUser(issue.getProvider().getProperties().get("host"),
						issue.getProvider().getProperties().get("username"),
						issue.getProvider().getProperties().get("secure:password"));
				List<Issue> userIssues = issues.get(jiraUser);
				if (userIssues == null) {
					userIssues = new ArrayList<>();
					issues.put(jiraUser, userIssues);
				}
				userIssues.add(issue);
			});

			NotifierFactory.generateNotifierForContext(context).notificateToJiraIssues(issues);
		}
		logger.info("Jira teamcity connector finished. Send the label to " + relatedIssues.size() + " issues.");
	}

	@NotNull
	private Collection<Issue> collectIssues(@NotNull SRunningBuild build) {
		Collection<Issue> relatedIssues = build.getSequenceBuild().getRelatedIssues();

		SFinishedBuild prevBuild = build.getPreviousFinished();

		while ( (prevBuild != null) &&  (!prevBuild.getBuildStatus().isSuccessful()) ) {
			relatedIssues.addAll(prevBuild.getRelatedIssues());
		}
		return relatedIssues;
	}

	@Override
	public void buildInterrupted(@NotNull SRunningBuild build) {
		//ignored
	}

	@Override
	public void messageReceived(@NotNull SRunningBuild build, @NotNull BuildMessage1 message) {
		//ignored
	}

	@Override
	public void responsibleChanged(@NotNull SBuildType bt, @NotNull ResponsibilityEntry oldValue, @NotNull ResponsibilityEntry newValue) {
		//ignored
	}

	@Override
	public void responsibleChanged(@NotNull SProject project, TestNameResponsibilityEntry oldValue, @NotNull TestNameResponsibilityEntry newValue, boolean isUserAction) {
		//ignored
	}

	@Override
	public void responsibleChanged(@NotNull SProject project, @NotNull Collection<TestName> testNames, @NotNull ResponsibilityEntry entry, boolean isUserAction) {
		//ignored
	}

	@Override
	public void responsibleChanged(@NotNull SProject project, @NotNull Collection<BuildProblemInfo> buildProblems, ResponsibilityEntry entry) {
		//ignored
	}

	@Override
	public void responsibleRemoved(@NotNull SProject project, @NotNull TestNameResponsibilityEntry entry) {
		//ignored
	}

	@Override
	public void beforeEntryDelete(@NotNull SFinishedBuild entry) {
		//ignored
	}

	@Override
	public void entryDeleted(@NotNull SFinishedBuild entry) {
		//ignored
	}

	@Override
	public void entriesDeleted(@NotNull Collection<SFinishedBuild> removedEntries) {
		//ignored
	}

	@Override
	public void projectCreated(@NotNull String projectId, SUser user) {
		//ignored
	}

	@Override
	public void projectExternalIdChanged(@NotNull SProject project, @NotNull String oldExternalId, @NotNull String newExternalId) {
		//ignored
	}

	@Override
	public void projectRemoved(@NotNull String projectId) {
		//ignored
	}

	@Override
	public void projectRemoved(@NotNull SProject project) {
		//ignored
	}

	@Override
	public void projectPersisted(@NotNull String projectId) {
		//ignored
	}

	@Override
	public void projectRestored(@NotNull String projectId) {
		//ignored
	}

	@Override
	public void projectArchived(@NotNull String projectId) {
		//ignored
	}

	@Override
	public void projectDearchived(@NotNull String projectId) {
		//ignored
	}

	@Override
	public void buildTypeTemplatePersisted(@NotNull BuildTypeTemplate buildTemplate) {
		//ignored
	}

	@Override
	public void buildTypePersisted(@NotNull SBuildType buildType) {
		//ignored
	}

	@Override
	public void pluginsLoaded() {
		//ignored
	}

	@Override
	public void changeAdded(@NotNull VcsModification modification, @NotNull VcsRoot root, Collection<SBuildType> buildTypes) {
		//ignored
	}

	@Override
	public void cleanupStarted() {
		//ignored
	}

	@Override
	public void cleanupFinished() {
		//ignored
	}

	@Override
	public void serverShutdownComplete() {
		//ignored
	}

	@Override
	public void sourcesVersionReleased(@NotNull BuildAgent agent) {
		//ignored
	}

	@Override
	public void sourcesVersionReleased(@NotNull BuildType configuration) {
		//ignored
	}

	@Override
	public void sourcesVersionReleased(@NotNull BuildType configuration, @NotNull BuildAgent agent) {
		//ignored
	}

	@Override
	public void labelingFailed(@NotNull SBuild build, @NotNull VcsRoot root, @NotNull Throwable exception) {
		//ignored
	}

	@Override
	public void labelingSucceed(@NotNull SBuild build, @NotNull BuildRevision revision) {
		//ignored
	}

	@Override
	public void buildTagsChanged(@NotNull SBuild build, @NotNull List<String> oldTags, @NotNull List<String> newTags) {/*ignored*/}

	@Override
	public void buildTagsChanged(@NotNull SBuild build, User user, @NotNull List<String> oldTags, @NotNull List<String> newTags) {/*ignored*/}

	@Override
	public void buildPromotionTagsChanged(@NotNull BuildPromotion buildPromotion, User user, @NotNull Collection<TagData> oldTags, @NotNull Collection<TagData> newTags) {/*ignored*/}

	@Override
	public void buildCommented(@NotNull SBuild build, User user, String comment) {/*ignored*/}

	@Override
	public void serverConfigurationReloaded() {/*ignored*/}

	@Override
	public void vcsRootRemoved(@NotNull SVcsRoot root) {/*ignored*/}

	@Override
	public void vcsRootUpdated(@NotNull SVcsRoot oldVcsRoot, @NotNull SVcsRoot newVcsRoot) {/*ignored*/}

	@Override
	public void vcsRootExternalIdChanged(@NotNull SVcsRoot vcsRoot, @NotNull String oldExternalId, @NotNull String newExternalId) {/*ignored*/}

	@Override
	public void vcsRootPersisted(@NotNull SVcsRoot vcsRoot) {/*ignored*/}

	@Override
	public void vcsRootsPersisted() {/*ignored*/}

	@Override
	public void testsMuted(@NotNull MuteInfo muteInfo) {/*ignored*/}

	@Override
	public void testsUnmuted(SUser user, @NotNull Map<MuteInfo, Collection<STest>> unmutedGroups) {/*ignored*/}

	@Override
	public void buildProblemsMuted(@NotNull MuteInfo muteInfo) {/*ignored*/}

	@Override
	public void buildProblemsUnmuted(SUser user, @NotNull Map<MuteInfo, Collection<BuildProblemInfo>> unmutedGroups) {/*ignored*/}

	@Override
	public void buildProblemsChanged(@NotNull SBuild build, @NotNull List<BuildProblemData> before, @NotNull List<BuildProblemData> after) {/*ignored*/}

	@Override
	public void statisticValuePublished(@NotNull SBuild build, @NotNull String valueTypeKey, @NotNull BigDecimal value) {/*ignored*/}

	@Override
	public void buildArtifactsChanged(@NotNull SBuild build) {/*ignored*/}

	@Override
	public void serverShutdown() {/*ignored*/}

	@Override
	public void serverStartup() {/*ignored*/}
}
