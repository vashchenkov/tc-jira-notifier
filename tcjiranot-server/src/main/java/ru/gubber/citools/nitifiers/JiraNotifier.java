package ru.gubber.citools.nitifiers;


import jetbrains.buildServer.issueTracker.Issue;

import java.util.List;
import java.util.Map;

/**
 * Interface of jira notifier.
 * Created by gubber on 23.11.2015.
 */
public interface JiraNotifier {

	void notificateToJiraIssues(Map<JiraUser, List<Issue>> jiraIssues);
}