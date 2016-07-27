package ru.gubber.citools.artifacts;

import jetbrains.buildServer.serverSide.artifacts.BuildArtifact;
import ru.gubber.citools.artifacts.readers.ArtifactFileReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Class represented archive file that can read concrete file from this archive. Now it applies only for ZIP archives.
 * Created by gubber on 24.11.2015.
 */
public class ArchiveArtifact extends ATCArtifactFile{
	private String filePathInArchive;

	 ArchiveArtifact(BuildArtifact artifact, String filePathInArchive) {
		super(artifact);
		if (!artifact.isFile() && artifact.isArchive())
			throw new IllegalArgumentException("ArchiveArtifact must be a file and must be an archive");
		this.filePathInArchive = filePathInArchive;
	}

	@Override
	public void readFile(ArtifactFileReader reader) {
		InputStream inputStream = null;
		try {
			inputStream = artifact.getInputStream();
			ZipInputStream zis = new ZipInputStream(inputStream);
			ZipEntry nextEntry;
			try {
				logger.info("Path in settings " + filePathInArchive);
				while ((nextEntry = zis.getNextEntry()) != null) {
					logger.debug("current entity is " + nextEntry.getName());
					if (nextEntry.getName().equals(filePathInArchive))
						logger.debug("paths are equals " + nextEntry.getName());
						reader.readStream(zis);
					try {
						zis.closeEntry();
					} catch (IOException e) {
						logger.error("Can't close entry.", e);
					}
				}
			} catch (IOException e) {
				logger.error("Can't iterate over archive.", e);
			}
		} catch (IOException e) {
			logger.error("can't read an artifact " + artifact.getName(), e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				logger.error("Can't close stream for artifact " + artifact.getName(), e);
			}
		}

	}
}