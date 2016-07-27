package ru.gubber.citools.artifacts;

import jetbrains.buildServer.serverSide.artifacts.BuildArtifact;
import ru.gubber.citools.artifacts.readers.ArtifactFileReader;

import java.io.IOException;
import java.io.InputStream;

/**
 * Class represented concrete file from artifacts' list. And can read it.
 * Created by gubber on 24.11.2015.
 */
public class FileArtifact extends ATCArtifactFile {

	FileArtifact(BuildArtifact artifact) {
			super(artifact);
		if (!artifact.isFile() || artifact.isArchive())
			throw new IllegalArgumentException("FileArtifact must be a file and couldn't be an archive");
	}

	@Override
	public void readFile(ArtifactFileReader reader) {
		InputStream inputStream = null;
		try {
			inputStream = artifact.getInputStream();
			reader.readStream(inputStream);
		} catch (IOException e) {
			logger.error("Can't read artifact " + artifact.getName(), e);
		} finally {
			try {
				if (inputStream != null)
				inputStream.close();
			} catch (IOException e) {
				logger.error("Can't close input stream for artifact " + artifact.getName(), e);
			}
		}

	}
}
