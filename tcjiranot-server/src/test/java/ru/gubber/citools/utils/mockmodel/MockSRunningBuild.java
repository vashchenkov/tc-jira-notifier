package ru.gubber.citools.utils.mockmodel;

import jetbrains.buildServer.AgentRestrictor;
import jetbrains.buildServer.BuildProblemData;
import jetbrains.buildServer.StatusDescriptor;
import jetbrains.buildServer.issueTracker.Issue;
import jetbrains.buildServer.messages.BuildMessage1;
import jetbrains.buildServer.messages.Status;
import jetbrains.buildServer.parameters.ParametersProvider;
import jetbrains.buildServer.parameters.ValueResolver;
import jetbrains.buildServer.parameters.impl.MapParametersProviderImpl;
import jetbrains.buildServer.serverSide.*;
import jetbrains.buildServer.serverSide.artifacts.*;
import jetbrains.buildServer.serverSide.buildLog.BuildLog;
import jetbrains.buildServer.serverSide.comments.Comment;
import jetbrains.buildServer.serverSide.impl.RunningBuildState;
import jetbrains.buildServer.serverSide.userChanges.CanceledInfo;
import jetbrains.buildServer.serverSide.vcs.VcsLabel;
import jetbrains.buildServer.tests.TestInfo;
import jetbrains.buildServer.users.SUser;
import jetbrains.buildServer.users.User;
import jetbrains.buildServer.users.UserSet;
import jetbrains.buildServer.vcs.SVcsModification;
import jetbrains.buildServer.vcs.SelectPrevBuildPolicy;
import jetbrains.buildServer.vcs.VcsException;
import jetbrains.buildServer.vcs.VcsRootInstanceEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by gubber on 21.10.2015.
 */
public class MockSRunningBuild implements SRunningBuild {

	private MapParametersProviderImpl parametersProvider =new MapParametersProviderImpl();

	private MockBuildArtifacts artifacts = new MockBuildArtifacts();

	@Override
	public String getCurrentPath() {
		return null;
	}

	@NotNull
	@Override
	public File getArtifactsDirectory() {
		return null;
	}


	public void addArtifact(String relativePath, BuildArtifact artifact) {
		artifacts.addArtifact(relativePath, artifact);
	}
	@NotNull
	@Override
	public BuildArtifacts getArtifacts(@NotNull BuildArtifactsViewMode mode) {
		return artifacts;
	}

	@NotNull
	@Override
	public List<SArtifactDependency> getArtifactDependencies() {
		return null;
	}

	@Override
	public boolean isArtifactsExists() {
		return false;
	}

	@Override
	public boolean isHasInternalArtifactsOnly() {
		return false;
	}

	@Override
	public boolean isResponsibleNeeded() {
		return false;
	}

	@NotNull
	@Override
	public BuildLog getBuildLog() {
		return null;
	}

	@NotNull
	@Override
	public ShortStatistics getShortStatistics() {
		return null;
	}

	@NotNull
	@Override
	public BuildStatistics getFullStatistics() {
		return null;
	}

	@NotNull
	@Override
	public BuildStatistics getBuildStatistics(@NotNull BuildStatisticsOptions options) {
		return null;
	}

	@Nullable
	@Override
	public SUser getOwner() {
		return null;
	}

	@Override
	public TriggeredBy getTriggeredBy() {
		return null;
	}

	@NotNull
	@Override
	public Date getStartDate() {
		return null;
	}

	@Override
	public String getAgentName() {
		return null;
	}

	@Override
	public long getBuildId() {
		return 0;
	}

	@Override
	public StatusDescriptor getStatusDescriptor() {
		return null;
	}

	@Override
	public List<String> getLogMessages(int startFromIdx, int maxCount) {
		return null;
	}

	@Override
	public List<TestInfo> getTestMessages(int startFromIdx, int maxTestsToLoad) {
		return null;
	}

	@Override
	public List<String> getCompilationErrorMessages() {
		return null;
	}

	@Nullable
	@Override
	public SBuildType getBuildType() {
		return null;
	}

	@NotNull
	@Override
	public String getBuildTypeId() {
		return null;
	}

	@NotNull
	@Override
	public String getBuildTypeExternalId() {
		return null;
	}

	@NotNull
	@Override
	public String getBuildTypeName() {
		return null;
	}

	@NotNull
	@Override
	public String getFullName() {
		return null;
	}

	@Nullable
	@Override
	public String getProjectId() {
		return null;
	}

	@Nullable
	@Override
	public String getProjectExternalId() {
		return null;
	}

	@NotNull
	@Override
	public DownloadedArtifacts getDownloadedArtifacts() {
		return null;
	}

	@NotNull
	@Override
	public DownloadedArtifacts getProvidedArtifacts() {
		return null;
	}

	@Override
	public boolean isUsedByOtherBuilds() {
		return false;
	}

	@NotNull
	@Override
	public List<SVcsModification> getContainingChanges() {
		return null;
	}

	@Override
	public boolean isPersonal() {
		return false;
	}

	@Override
	public Status getBuildStatus() {
		return null;
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@NotNull
	@Override
	public List<SVcsModification> getChanges(SelectPrevBuildPolicy policy, boolean includeModificationsIfPreviousBuildIsAbsent) {
		return null;
	}

	@Override
	public UserSet<SUser> getCommitters(SelectPrevBuildPolicy policy) {
		return null;
	}

	@Override
	public String getBuildNumber() {
		return null;
	}

	@Nullable
	@Override
	public Date getFinishDate() {
		return null;
	}

	@Override
	public CanceledInfo getCanceledInfo() {
		return null;
	}

	@Override
	public long getDuration() {
		return 0;
	}

	@Override
	public boolean isOutOfChangesSequence() {
		return false;
	}

	@Override
	public List<String> getTags() {
		return null;
	}

	@Override
	public void setTags(List<String> tags) {

	}

	@Override
	public void setTags(User user, List<String> tags) {

	}

	@NotNull
	@Override
	public byte[] getFileContent(String filePath) throws VcsException {
		return new byte[0];
	}

	@Override
	public List<BuildRevision> getRevisions() {
		return null;
	}

	@Override
	public List<VcsLabel> getLabels() {
		return null;
	}

	@NotNull
	@Override
	public Date getQueuedDate() {
		return null;
	}

	@NotNull
	@Override
	public Date getServerStartDate() {
		return null;
	}

	@Override
	public List<VcsRootInstanceEntry> getVcsRootEntries() {
		return null;
	}

	@Nullable
	@Override
	public Date getClientStartDate() {
		return null;
	}

	@Override
	public boolean isStartedOnAgent() {
		return false;
	}

	@NotNull
	@Override
	public Date convertToServerTime(@NotNull Date agentTime) {
		return null;
	}

	@NotNull
	@Override
	public Date convertToAgentTime(@NotNull Date serverTime) {
		return null;
	}

	@Nullable
	@Override
	public String getBuildDescription() {
		return null;
	}


	public MockSRunningBuild addProperty(String key, String value) {
		parametersProvider.put(key, value);
		return this;
	}

	@NotNull
	@Override
	public ParametersProvider getParametersProvider() {

		return parametersProvider;
	}

	@NotNull
	@Override
	public ValueResolver getValueResolver() {
		return null;
	}

	@Nullable
	@Override
	public Comment getBuildComment() {
		return null;
	}

	@Override
	public void setBuildComment(@Nullable User user, @Nullable String comment) {

	}

	@Override
	public boolean isPinned() {
		return false;
	}

	@NotNull
	@Override
	public Collection<Issue> getRelatedIssues() {
		return null;
	}

	@Override
	public boolean isHasRelatedIssues() {
		return false;
	}

	@NotNull
	@Override
	public Map<String, String> getBuildOwnParameters() {
		return null;
	}

	@Override
	public String getRawBuildNumber() {
		return null;
	}

	@Override
	public boolean isInternalError() {
		return false;
	}

	@Nullable
	@Override
	public String getFirstInternalError() {
		return null;
	}

	@Nullable
	@Override
	public String getFirstInternalErrorMessage() {
		return null;
	}

	@Nullable
	@Override
	public TimeZone getClientTimeZone() {
		return null;
	}

	@NotNull
	@Override
	public SBuildAgent getAgent() {
		return null;
	}

	@Override
	public void addBuildProblem(@NotNull BuildProblemData buildProblem) {

	}

	@Override
	public boolean hasBuildProblemOfType(@NotNull String type) {
		return false;
	}

	@NotNull
	@Override
	public List<BuildProblemData> getFailureReasons() {
		return null;
	}

	@Override
	public void muteBuildProblems(@NotNull SUser user, boolean muteIfTrue, @NotNull String comment) {

	}

	@Override
	public BuildProblemData addUserBuildProblem(@NotNull SUser user, @NotNull String problemDesciption) {
		return null;
	}

	@Nullable
	@Override
	public Branch getBranch() {
		return null;
	}

	@Nullable
	@Override
	public SFinishedBuild getPreviousFinished() {
		return null;
	}

	@Nullable
	@Override
	public BigDecimal getStatisticValue(String valueTypeKey) {
		return null;
	}

	@NotNull
	@Override
	public Map<String, BigDecimal> getStatisticValues() {
		return null;
	}

	@NotNull
	@Override
	public Collection<SBuildFeatureDescriptor> getBuildFeaturesOfType(@NotNull String featureType) {
		return null;
	}

	@Nullable
	@Override
	public Integer getQueuedAgentId() {
		return null;
	}

	@Nullable
	@Override
	public AgentRestrictor getQueuedAgentRestrictor() {
		return null;
	}

	@Override
	public boolean isInterrupted() {
		return false;
	}

	@Override
	public int getSignature() {
		return 0;
	}

	@Override
	public void setSignature(int newSignatureValue) {

	}

	@Override
	public int getCompletedPercent() {
		return 0;
	}

	@Override
	public void addBuildMessages(@NotNull List<BuildMessage1> messages) {

	}

	@Override
	public void addBuildMessage(@NotNull BuildMessage1 message) {

	}

	@Override
	public void setBuildNumber(@NotNull String newBuildNumber) {

	}

	@Override
	public void setBuildStatus(Status status) {

	}

	@Override
	public void setInterrupted(@NotNull RunningBuildState state, @Nullable User user, @Nullable String reason) {

	}

	@Override
	public String getAgentAccessCode() {
		return null;
	}

	@Override
	public boolean isProbablyHanging() {
		return false;
	}

	@Override
	public boolean isOutdated() {
		return false;
	}

	@Nullable
	@Override
	public SFinishedBuild getRecentlyFinishedBuild() {
		return null;
	}

	@Override
	public Date getLastBuildActivityTimestamp() {
		return null;
	}

	@Override
	public long getTimeSpentSinceLastBuildActivity() {
		return 0;
	}

	@Override
	public void stop(@Nullable User user, @Nullable String comment) {

	}

	@Override
	public long getEstimationForTimeLeft() {
		return 0;
	}

	@Override
	public long getDurationEstimate() {
		return 0;
	}

	@Override
	public long getDurationOvertime() {
		return 0;
	}

	@Override
	public long getElapsedTime() {
		return 0;
	}

	@NotNull
	@Override
	public BuildPromotion getBuildPromotion() {
		return null;
	}

	@Nullable
	@Override
	public SBuild getSequenceBuild() {
		return null;
	}

}
