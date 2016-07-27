package ru.gubber.citools.artifacts;

import jetbrains.buildServer.serverSide.SRunningBuild;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifact;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifacts;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifactsViewMode;

/**
 * Creates ArtifactFile to process.
 * Created by gubber on 24.11.2015.
 */
public class ArtifactFileFactory {

	public ATCArtifactFile createArtifactFile(SRunningBuild build, String pathToFile) {
		BuildArtifacts artifacts = build.getArtifacts(BuildArtifactsViewMode.VIEW_ALL);

		String realArtifactPath = getRealArtifactPath(pathToFile);
		String pathToConcreteFile = getPathToProperties(pathToFile);

		BuildArtifact artifact = artifacts.getArtifact(realArtifactPath);
		if ( (artifact == null) || (artifact.isDirectory()) )
			return null;
		if (!artifact.isArchive())
			return new FileArtifact(artifact);
		return new ArchiveArtifact(artifact, pathToConcreteFile);
	}

	String getPathToProperties(String artifactPath) {
		if (!artifactPath.contains("!"))
			return artifactPath;
		return artifactPath.substring(artifactPath.indexOf("!") + 1);
	}

	String getRealArtifactPath(String artifactPath) {
		if (!artifactPath.contains("!"))
			return artifactPath;
		else
			return artifactPath.substring(0, artifactPath.indexOf("!"));
	}

}