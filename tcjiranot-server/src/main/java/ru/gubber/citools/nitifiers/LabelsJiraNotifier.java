package ru.gubber.citools.nitifiers;

import jetbrains.buildServer.issueTracker.Issue;
import ru.gubber.citools.context.JiraLabelSetterContext;
import ru.gubber.jiraconnect.JiraLabelSetter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class calls JiraLabelSetter, to set labels to jira issues.
 * Created by gubber on 24.11.2015.
 */
public class LabelsJiraNotifier extends AbstractNotifier<JiraLabelSetterContext> {

	LabelsJiraNotifier(JiraLabelSetterContext context) {
		super(context);
	}

	@Override
	public void notificateToJiraIssues(Map<JiraUser, List<Issue>> jiraIssues) {
		JiraLabelSetter labelSetter = new JiraLabelSetter();
		jiraIssues.entrySet().stream().forEach(entry ->
				labelSetter.setIssueLabel(entry.getKey().getHost(), entry.getKey().getUsername(), entry.getKey().getPassword(),
						entry.getValue().stream().map(Issue::getId).collect(Collectors.toList()),
						context.getCustomField(), context.getLabels(), context.getLabelSetterPolicy()));
	}
}
