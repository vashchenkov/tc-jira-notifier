package ru.gubber.citools.server;

import jetbrains.buildServer.serverSide.BuildFeature;
import jetbrains.buildServer.web.openapi.PluginDescriptor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.gubber.citools.TJPConstants;

/**
 * Created by gubber on 20.10.2015.
 */
public class TeamcityToJiraBuildFeature extends BuildFeature implements TJPConstants {

	public static final String FEATURE_TYPE = "gubber-teamcity-to-jira-plugin";

	private final String myEditUrl;

	public TeamcityToJiraBuildFeature(@NotNull final PluginDescriptor descriptor) {
		myEditUrl = descriptor.getPluginResourcesPath("jiraNotificationSettings.jsp");
	}

	@NotNull
	@Override
	public String getType() {
		return FEATURE_TYPE;
	}

	@NotNull
	@Override
	public String getDisplayName() {
		return "Teamcity to jira notification plugin";
	}

	@Nullable
	@Override
	public String getEditParametersUrl() {
		return myEditUrl;
	}

	@Override
	public PlaceToShow getPlaceToShow() {
		return super.getPlaceToShow();
	}

	@Override
	public boolean isMultipleFeaturesPerBuildTypeAllowed() {
		return false;
	}
}