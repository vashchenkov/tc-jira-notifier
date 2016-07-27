package ru.gubber.citools.context;

import jetbrains.buildServer.serverSide.SBuildFeatureDescriptor;
import jetbrains.buildServer.serverSide.SRunningBuild;
import ru.gubber.citools.TJPConstants;
import ru.gubber.citools.server.TeamcityToJiraBuildFeature;
import ru.gubber.citools.utils.PropertiesExtractor;
import ru.gubber.citools.utils.PropertiesToLabelEvaluator;
import ru.gubber.jiraconnect.JiraLabelSetter;

import java.util.*;

/**
 * Created by gubber on 23.11.2015.
 */
public class JiraLabelSetterContext implements TJPConstants, NotifierContext {

	private String label;
	private JiraLabelSetter.LabelSetterPolicy labelSetterPolicy;
	private String customField;

	public static JiraLabelSetterContext initializeFromFeatureDescriptor(SRunningBuild build,
	                                                                     PropertiesExtractor propertiesExtractor, PropertiesToLabelEvaluator labelEvaluator) {
		Collection<SBuildFeatureDescriptor> buildFeatures = build.getBuildFeaturesOfType(TeamcityToJiraBuildFeature.FEATURE_TYPE);
		if (buildFeatures.size() == 0) {
			logger.warn("Jira teamcity connector stops. There is no feature for build =" + build.getBuildNumber());
			return null;
		}
		SBuildFeatureDescriptor descriptor = buildFeatures.iterator().next();

		Map<String, String> parameters = descriptor.getParameters();
		boolean buildFailed = build.getBuildStatus().isFailed();

		String label = buildFailed ?
				parameters.get(TJPConstants.TEAMCITY_TO_JIRA_FAILED_LABEL)
				:
				parameters.get(TJPConstants.TEAMCITY_TO_JIRA_SUCCESS_LABEL);
		String customField = parameters.get(TJPConstants.TEAMCITY_TO_JIRA_CUSTOM_FIELD_ID);

		if ((label == null) || (label.trim().length() == 0) ||
				(customField == null) || (customField.trim().length() == 0)) {
			logger.warn("Jira teamcity connector stops. Label or extraField is blank.");
			return null;
		}

		String labelPolicyStr = buildFailed ?

				parameters.get(TJPConstants.TEAMCITY_TO_JIRA_FAILED_LABEL_POLICY)
				:
				parameters.get(TJPConstants.TEAMCITY_TO_JIRA_SUCCESS_LABEL_POLICY);

		JiraLabelSetter.LabelSetterPolicy labelSetterPolicy;
		try {
			labelSetterPolicy = JiraLabelSetter.LabelSetterPolicy.valueOf(labelPolicyStr);
		} catch (IllegalArgumentException e) {
			labelSetterPolicy = JiraLabelSetter.LabelSetterPolicy.EMPTY_ONLY;
		}

		String extraPropsFile = parameters.get(TJPConstants.TEAMCITY_TO_JIRA_EXTRA_PROPS_FILE);
		List<String> artifactsPaths = extraPropsFile != null ? Collections.singletonList(extraPropsFile) : Collections.emptyList();
		Properties props = propertiesExtractor.readPropertiesFromSBuildAndArtifacts(build, artifactsPaths);

		label = labelEvaluator.evaluateLabelFromProperties(label, props);

		JiraLabelSetterContext result = new JiraLabelSetterContext();
		result.label = label;
		result.customField = customField;
		result.labelSetterPolicy = labelSetterPolicy;
		return result;
	}

	public Collection<String> getLabels() {
		return Collections.singleton(label);
	}

	public JiraLabelSetter.LabelSetterPolicy getLabelSetterPolicy() {
		return labelSetterPolicy;
	}

	public String getCustomField() {
		return customField;
	}

	@Override
	public ContextType getContextType() {
		return ContextType.LABELS;
	}
}