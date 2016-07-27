package ru.gubber.jiraconnect;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.RestClientException;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.jetbrains.annotations.NotNull;
import ru.gubber.citools.TJPConstants;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by gubber on 14.10.2015.
 */
public class JiraLabelSetter implements TJPConstants {

	public enum LabelSetterPolicy{
		ADD, EMPTY_ONLY, REPLACE
	}

	/**
	 * Set labels to Jira issue, using JiraRestClient.
	 *
	 * @param host
	 * @param user
	 * @param password
	 * @param issueKeys     Collectuion of issue keys that identifies the issues at jira server
	 * @param fieldId       jira costom filed id. Field with this key must be the LABEL type.
	 * @param labels        Collections of labels to set for issue
	 * @param setterPolicy  policy setting labels to the issue.
	 *                         LabelSetterPolicy.ADD - this policy is use to add new labels to the ISSUE
	 *                         LabelSetterPolicy.REPLACE - this policy is use to add new labels to the ISSUE and delete old labels
	 *                         LabelSetterPolicy.EMPTY_ONLY - this policy is use to add new labels to the ISSUE only if the are no other labels
	 * */
	public void setIssueLabel(String host, String user, String password, Collection<String> issueKeys, String fieldId, Collection labels, LabelSetterPolicy setterPolicy) {
		AsynchronousJiraRestClientFactory factory = getJiraRestClientFactory();
		JiraRestClient client = factory.createWithBasicHttpAuthentication(URI.create(host), user, password);
		IssueRestClient issueClient = client.getIssueClient();

		for (String issueKey : issueKeys) {
			if (setLabelToSingleIssue(fieldId, labels, setterPolicy, client, issueClient, issueKey)) return;
		}
	}

	private boolean setLabelToSingleIssue(String fieldId, Collection labels, LabelSetterPolicy setterPolicy, JiraRestClient client, IssueRestClient issueClient, String issueKey) {
		try {
			List valuesList = new ArrayList<>();
			//if Policy is ADD - we need to collect all properties and add to them new label.
			//if policy is EMPTY_ONLY - we need to find out if atleast one label is set.
			if (!LabelSetterPolicy.REPLACE.equals(setterPolicy)) {
				loadCurrentLabels(issueKey, fieldId, issueClient, valuesList);
			}

			if ( (LabelSetterPolicy.EMPTY_ONLY.equals(setterPolicy)) && (valuesList.size() > 0) ) {
				tryToCloseJiraClient(client);
				if (logger.isDebugEnabled())
					logger.debug("Labels will not be set because of policy EMPTY_ONLY and labels count is " + valuesList.size());
				return true;
			}

			valuesList.addAll(labels);

			IssueInput input = constructIssueInput(fieldId, valuesList);
			issueClient.updateIssue(issueKey, input).
					done(aVoid -> {
						if (logger.isDebugEnabled())
							logger.debug("Labels was successfully set to issue {" + issueKey + "}");
						tryToCloseJiraClient(client);
					}).fail(throwable -> {
						logger.error("Error occurs while setting to issue {" + issueKey + "}", throwable);
						tryToCloseJiraClient(client);
					});
		} catch (RestClientException | JSONException e) {
			logger.error("Error while updating the issue {" + issueKey + "}", e);
			tryToCloseJiraClient(client);
		} catch (Throwable rh) {
			logger.error("Fatal error while updating the issue {" + issueKey + "}", rh);
		}
		return false;
	}

	@NotNull
	AsynchronousJiraRestClientFactory getJiraRestClientFactory() {
		return new AsynchronousJiraRestClientFactory();
	}

	@NotNull
	private IssueInput constructIssueInput(String fieldId, List valuesList) {
		HashMap<String, FieldInput> fieldsToInsert = new HashMap<>();
		FieldInput fieldInput = new FieldInput(fieldId, valuesList);
		fieldsToInsert.put(fieldId, fieldInput);
		return new IssueInput(fieldsToInsert);
	}

	private void tryToCloseJiraClient(JiraRestClient client) {
		try {
			client.close();
		} catch (Throwable ex) {
			logger.warn("Error closing jira client", ex);
		}
	}

	private void loadCurrentLabels(String issueKey, String fieldId, IssueRestClient issueClient, List valuesList) throws JSONException {
		Issue issue = getIssue(issueKey, issueClient);
		IssueField field = issue.getField(fieldId);
		JSONArray values = (JSONArray) field.getValue();
		if ( (values != null) && (values.length() > 0) ) {
			for (int i = 0; i < values.length(); i++) {
				Object o = values.get(i);
				valuesList.add(o);
			}
		}
	}

	public Issue getIssue(String issueKey, IssueRestClient issueClient) {
		return issueClient.getIssue(issueKey).claim();
	}
}