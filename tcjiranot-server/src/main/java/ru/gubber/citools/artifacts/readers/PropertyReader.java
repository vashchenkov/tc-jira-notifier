package ru.gubber.citools.artifacts.readers;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by gubber on 24.11.2015.
 */
public class PropertyReader extends ArtifactFileReader{
	private static final String ARTIFACT_PREFIX = "artifact.";
	private Properties props;

	public PropertyReader(Properties props) {
		this.props = props;
	}

	@Override
	public void readStream(InputStream inputStream) {
		try {
			Properties artifactProperties = new Properties();
			artifactProperties.load(inputStream);
			artifactProperties.stringPropertyNames().stream().forEach(
					name -> props.put(ARTIFACT_PREFIX + name, artifactProperties.get(name))
			);
		} catch (IOException e) {
			logger.error("Can't read properties." , e);
		}
	}
}
