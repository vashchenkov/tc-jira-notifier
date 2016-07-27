<%@ taglib prefix="bs" tagdir="/WEB-INF/tags/" %>
<%@ taglib prefix="props" tagdir="/WEB-INF/tags/props" %>
<%@ taglib prefix="l" tagdir="/WEB-INF/tags/layout" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="forms" tagdir="/WEB-INF/tags/forms" %>

<tr id="success.label.container" >
    <th><label for="teamcityToJira.success.label">Label if build successfully:</label></th>
    <td><props:textProperty name="teamcityToJira.success.label" style="width:24em;" />
        <span class="smallNote">Label will be sent to Jira if build would be finished successfully.
            For example myProject/{artifact.app.version}-{artifact.app.branch}# {build.buildnumber}</span>
    </td>
</tr>

<tr id="success.label.policy.container" >
    <th><label for="teamcityToJira.success.label.policy">Select success label policy:</label></th>
    <td><props:selectProperty name="teamcityToJira.success.label.policy">
        <props:option value="EMPTY_ONLY">Only if field is empty</props:option>
        <props:option value="REPLACE">Replace old values</props:option>
        <props:option value="ADD">Add label value</props:option>
    </props:selectProperty>
        <span class="smallNote">Policy to set the success label to issue</span>
    </td>
</tr>

<tr id="failed.label.container" >
    <th><label for="teamcityToJira.failed.label">Label if build failed:</label></th>
    <td><props:textProperty name="teamcityToJira.failed.label" style="width:24em;" />
        <span class="smallNote">Label will be sent to Jira if build would be failed.For example FAILED</span>
    </td>
</tr>

<tr id="failed.label.policy.container" >
    <th><label for="teamcityToJira.success.label.policy">Select failed label policy:</label></th>
    <td><props:selectProperty name="teamcityToJira.success.label.policy">
        <props:option value="EMPTY_ONLY">Only if field is empty</props:option>
        <props:option value="REPLACE">Replace old values</props:option>
        <props:option value="ADD">Add label value</props:option>
    </props:selectProperty>
        <span class="smallNote">Policy to set the failed label to issue</span>
    </td>
</tr>

<tr id="custom.field.container" >
    <th><label for="teamcityToJira.custom.field.id">Custom field id:</label></th>
    <td><props:textProperty name="teamcityToJira.custom.field.id" style="width:12em;" />
        <span class="smallNote">The identifier of custom field in JIRA. For example customfield_10802</span>
    </td>
</tr>

<tr id="properties.file.container" >
    <th><label for="teamcityToJira.extra.properties.file">Path to extra properties:</label></th>
    <td><props:textProperty name="teamcityToJira.extra.properties.file" style="width:24em;" />
        <span class="smallNote">Properties from this file is available for labels with prefix "artifact."</span>
    </td>
</tr>