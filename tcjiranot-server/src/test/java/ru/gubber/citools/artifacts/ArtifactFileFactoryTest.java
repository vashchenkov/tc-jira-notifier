package ru.gubber.citools.artifacts;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

/**
 * Created by gubber on 24.11.2015.
 */
@RunWith(BlockJUnit4ClassRunner.class)
public class ArtifactFileFactoryTest extends TestCase {

	@Test
	public void getArtifactPathFromArchiveTypePath() {
		ArtifactFileFactory extractor = new ArtifactFileFactory();
		String realArtifactPath = extractor.getRealArtifactPath("test.zip!WEB-INF/classes/buildnumber.properties");

		assertEquals("test.zip", realArtifactPath);
	}

	@Test
	public void getArtifactPathFromSimpleTypePath() {
		ArtifactFileFactory extractor = new ArtifactFileFactory();
		String realArtifactPath = extractor.getRealArtifactPath("buildnumber.properties");

		assertEquals("buildnumber.properties", realArtifactPath);
	}

	@Test
	public void getPropertiesPathFromArchiveTypePath() {
		ArtifactFileFactory extractor = new ArtifactFileFactory();
		String realArtifactPath = extractor.getPathToProperties("test.zip!WEB-INF/classes/buildnumber.properties");

		assertEquals("WEB-INF/classes/buildnumber.properties", realArtifactPath);
	}

	@Test
	public void getPropertiesPathFromSimpleTypePath() {
		ArtifactFileFactory extractor = new ArtifactFileFactory();
		String realArtifactPath = extractor.getPathToProperties("buildnumber.properties");

		assertEquals("buildnumber.properties", realArtifactPath);
	}
}