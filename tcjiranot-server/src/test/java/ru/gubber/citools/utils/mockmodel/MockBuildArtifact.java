package ru.gubber.citools.utils.mockmodel;

import jetbrains.buildServer.serverSide.artifacts.BuildArtifact;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

/**
 * Created by gubber on 22.10.2015.
 */
public class MockBuildArtifact implements BuildArtifact {
	private final File artifactFile;

	public MockBuildArtifact(String filePath) {
		artifactFile = new File(filePath);
		if (!artifactFile.exists())
			throw new IllegalArgumentException("File " + filePath + " doesn't exist");
	}

	@Override
	public boolean isDirectory() {
		return artifactFile.isDirectory();
	}

	@Override
	public boolean isArchive() {
		return artifactFile.isFile() && artifactFile.getPath().endsWith(".war");
	}

	@Override
	public boolean isFile() {
		return artifactFile.isFile();
	}

	@Override
	public boolean isContainer() {
		return false;
	}

	@Override
	public long getSize() {
		return artifactFile.length();
	}

	@Override
	public long getTimestamp() {
		return 0;
	}

	@NotNull
	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(artifactFile);
	}

	@NotNull
	@Override
	public Collection<BuildArtifact> getChildren() {
		return null;
	}

	@NotNull
	@Override
	public String getRelativePath() {
		return null;
	}

	@NotNull
	@Override
	public String getName() {
		return null;
	}
}