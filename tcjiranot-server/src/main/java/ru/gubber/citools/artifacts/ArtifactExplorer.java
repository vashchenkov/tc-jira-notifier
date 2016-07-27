package ru.gubber.citools.artifacts;

import jetbrains.buildServer.serverSide.SRunningBuild;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Walk through artifacts and return list of ArtifactFiles by pattern.
 * Created by gubber on 24.11.2015.
 */
public class ArtifactExplorer {

	private ArtifactFileFactory artifactFileFactory;

	public ArtifactExplorer(@NotNull ArtifactFileFactory artifactFileFactory) {
		this.artifactFileFactory = artifactFileFactory;
	}

	public Collection<ATCArtifactFile> findArtifactsByPattern(SRunningBuild build, @NotNull List<String> artifactsPaths) {
		List<ATCArtifactFile> result = new ArrayList<>();
		for (String artifactPath : artifactsPaths) {
			ATCArtifactFile artifactFile = artifactFileFactory.createArtifactFile(build, artifactPath);
			if (artifactFile != null)
				result.add(artifactFile);
		}
		return result;
	}
}