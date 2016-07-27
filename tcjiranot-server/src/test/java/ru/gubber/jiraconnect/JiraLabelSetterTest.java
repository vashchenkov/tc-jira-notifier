package ru.gubber.jiraconnect;

import com.atlassian.jira.rest.client.api.IssueRestClient;
import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.IssueField;
import com.atlassian.jira.rest.client.api.domain.input.FieldInput;
import com.atlassian.jira.rest.client.api.domain.input.IssueInput;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
import junit.framework.TestCase;
import org.codehaus.jettison.json.JSONArray;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.testng.annotations.BeforeTest;

import java.net.URI;
import java.util.Collection;
import java.util.Collections;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyCollection;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by gubber on 22.10.2015.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class JiraLabelSetterTest extends TestCase {

	public static final String FIELD_ID = "jira_fiel_id";
	public static final String ISSUE_KEY = "MYPROJ-431";
	public static final String LABEL_ONE = "label 1";
	public static final String LABEL_TWO = "label 2";

	public static final String LABEL_FOR_ADD = "Label for add";

	private static final int LABELS_COUNT_INDX = 0;
	private static final int UPDATE_COUNT_INDX = 1;

	private AsynchronousJiraRestClientFactory jiraClientFactory = mock(AsynchronousJiraRestClientFactory.class);
	private JiraRestClient client = mock(JiraRestClient.class);
	private IssueRestClient issueClient = mock(IssueRestClient.class);

	@BeforeTest
	public void init() {
	}

	@Test
	public void addPolicySuccessTest() {
		int[] counts = new int[2];
		JiraLabelSetter setter = prepareTwoLabelMocks(counts);

		setter.setIssueLabel("", "", "", Collections.singletonList(ISSUE_KEY), FIELD_ID, Collections.singleton(LABEL_FOR_ADD), JiraLabelSetter.LabelSetterPolicy.ADD);

		assertEquals(3, counts[LABELS_COUNT_INDX]);
	}

	@Test
	public void replacePolicySuccessTest() {
		int[] counts = new int[2];
		JiraLabelSetter setter = prepareTwoLabelMocks(counts);

		setter.setIssueLabel("", "", "", Collections.singletonList(ISSUE_KEY), FIELD_ID, Collections.singleton(LABEL_FOR_ADD), JiraLabelSetter.LabelSetterPolicy.REPLACE);

		assertEquals(1, counts[LABELS_COUNT_INDX]);
	}

	@Test
	public void emptyPolicySuccessTest() {
		int[] counts = new int[2];
		JiraLabelSetter setter = prepareZeroLabelMocks(counts);

		setter.setIssueLabel("", "", "", Collections.singletonList(ISSUE_KEY), FIELD_ID, Collections.singleton(LABEL_FOR_ADD), JiraLabelSetter.LabelSetterPolicy.EMPTY_ONLY);

		assertEquals(1, counts[LABELS_COUNT_INDX]);
	}

	@Test
	public void emptyPolicyFailedTest() {
		int[] counts = new int[2];
		JiraLabelSetter setter = prepareTwoLabelMocks(counts);

		setter.setIssueLabel("", "", "", Collections.singletonList(ISSUE_KEY), FIELD_ID, Collections.singleton(LABEL_FOR_ADD), JiraLabelSetter.LabelSetterPolicy.EMPTY_ONLY);

		assertEquals(0, counts[UPDATE_COUNT_INDX]);
	}

	@NotNull
	private JiraLabelSetter prepareTwoLabelMocks(int[] counts) {
		IssueField twoLabelsIssueField = getTwoLabelsIssueField();
		return prepareMocksWithIssueField(counts, twoLabelsIssueField);
	}

	@NotNull
	private JiraLabelSetter prepareZeroLabelMocks(int[] counts) {
		IssueField twoLabelsIssueField = getZeroLabelsIssueField();
		return prepareMocksWithIssueField(counts, twoLabelsIssueField);
	}

	@NotNull
	private JiraLabelSetter prepareMocksWithIssueField(int[] counts, IssueField twoLabelsIssueField) {
		Issue issue = mock(Issue.class);
		when(issue.getField(FIELD_ID)).thenReturn(twoLabelsIssueField);

		JiraLabelSetter setter = mock(JiraLabelSetter.class);
		when(setter.getIssue(anyString(), any())).thenReturn(issue);

		when(jiraClientFactory.createWithBasicHttpAuthentication(any(URI.class), anyString(), anyString())).thenReturn(client);
		when(client.getIssueClient()).thenReturn(issueClient);

		doCallRealMethod().when(setter).setIssueLabel(anyString(), anyString(), anyString(), anyCollection(),
				anyString(), anyCollection(), any());
		when(setter.getJiraRestClientFactory()).thenReturn(jiraClientFactory);
		when(issueClient.updateIssue(anyString(), any(IssueInput.class))).thenAnswer(invocationOnMock -> {
			IssueInput input = (IssueInput) invocationOnMock.getArguments()[1];
			FieldInput fieldInput = input.getFields().get(FIELD_ID);
			counts[LABELS_COUNT_INDX] = ((Collection) fieldInput.getValue()).size();
			counts[UPDATE_COUNT_INDX]++;
			Promise updateMock = mock(Promise.class);
			Promise doneMock = mock(Promise.class);
			when(doneMock.isDone()).thenReturn(true);
			when(updateMock.done(any())).thenReturn(doneMock);
			return updateMock;
		});
		return setter;
	}

	private IssueField getTwoLabelsIssueField() {
		IssueField field = mock(IssueField.class);
		JSONArray twoFields = new JSONArray();
		twoFields.put(LABEL_ONE);
		twoFields.put(LABEL_TWO);
		when(field.getValue()).thenReturn(twoFields);
		return field;
	}

	private IssueField getZeroLabelsIssueField() {
		IssueField field = mock(IssueField.class);
		JSONArray zeroFields = new JSONArray();
		when(field.getValue()).thenReturn(zeroFields);
		return field;
	}
}