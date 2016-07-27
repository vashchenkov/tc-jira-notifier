package ru.gubber.citools.utils;

import jetbrains.buildServer.serverSide.SRunningBuild;
import org.jetbrains.annotations.NotNull;
import ru.gubber.citools.artifacts.ArtifactExplorer;
import ru.gubber.citools.artifacts.readers.PropertyReader;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by gubber on 21.10.2015.
 */
public class PropertiesExtractor {

	private static final String BUILD_PREFIX = "build.";
	private ArtifactExplorer artifactExplorer;

	public PropertiesExtractor(@NotNull ArtifactExplorer artifactExplorer) {
		this.artifactExplorer = artifactExplorer;
	}

	/**
	 * Returns properties from build with appended properties from artifacts
	 * @param build build, to get properties from
	 * @param artifactsPaths paths to properties files in artifacts. For example "buildnumber.properts" if this file is
	 *                       single(!!!) artifact or "test.war!WEB-INF/classes/buildnumber.properties"
	 *                       if properties file is inside some archived artifact.
	 * @return
	 */
	public Properties readPropertiesFromSBuildAndArtifacts(SRunningBuild build, @NotNull List<String> artifactsPaths) {

		Properties properties = new Properties();
		for (Map.Entry<String, String> entry : build.getParametersProvider().getAll().entrySet()) {
			properties.put(BUILD_PREFIX + entry.getKey(), entry.getValue());
		}
		PropertyReader propertyReader = new PropertyReader(properties);
		artifactExplorer.findArtifactsByPattern(build, artifactsPaths).stream().forEach(file ->
				file.readFile(propertyReader));
		return properties;
	}
}