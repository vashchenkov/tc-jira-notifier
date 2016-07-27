package ru.gubber.citools.utils.mockmodel;

import jetbrains.buildServer.serverSide.artifacts.BuildArtifact;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifactHolder;
import jetbrains.buildServer.serverSide.artifacts.BuildArtifacts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by gubber on 22.10.2015.
 */
class MockBuildArtifacts implements BuildArtifacts {

	private Map<String, BuildArtifact> arttifacts = new HashMap<>();

	void addArtifact(String relativePath, BuildArtifact artifact) {
		arttifacts.put(relativePath, artifact);
	}

	@Nullable
	@Override
	public BuildArtifact getArtifact(@NotNull String relativePath) {
		return arttifacts.get(relativePath);
	}

	@NotNull
	@Override
	public BuildArtifactHolder findArtifact(@NotNull String relativePath) {
		return null;
	}

	@NotNull
	@Override
	public BuildArtifact getRootArtifact() {
		return null;
	}

	@Override
	public boolean isAvailable() {
		return false;
	}

	@Override
	public void iterateArtifacts(@NotNull BuildArtifactsProcessor processor) {

	}
}
