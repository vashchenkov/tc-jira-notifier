package ru.gubber.citools;

import com.intellij.openapi.diagnostic.Logger;
import jetbrains.buildServer.log.Loggers;

/**
 * Created by gubber on 20.10.2015.
 */
public interface TJPConstants {

	Logger logger = Loggers.ACTIVITIES;
	Logger logger_startup = Loggers.STARTUP;

	String TEAMCITY_TO_JIRA_SUCCESS_LABEL = "teamcityToJira.success.label";
	String TEAMCITY_TO_JIRA_SUCCESS_LABEL_POLICY = "teamcityToJira.success.label.policy";
	String TEAMCITY_TO_JIRA_FAILED_LABEL = "teamcityToJira.failed.label";
	String TEAMCITY_TO_JIRA_FAILED_LABEL_POLICY = "teamcityToJira.failed.label.policy";
	String TEAMCITY_TO_JIRA_CUSTOM_FIELD_ID = "teamcityToJira.custom.field.id";
	String TEAMCITY_TO_JIRA_EXTRA_PROPS_FILE = "teamcityToJira.extra.properties.file";
}
