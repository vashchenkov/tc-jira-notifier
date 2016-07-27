package ru.gubber.citools.artifacts;

import jetbrains.buildServer.serverSide.artifacts.BuildArtifact;
import ru.gubber.citools.TJPConstants;
import ru.gubber.citools.artifacts.readers.ArtifactFileReader;

/**
 * Created by gubber on 24.11.2015.
 */
public abstract class ATCArtifactFile implements TJPConstants{

	protected BuildArtifact artifact;

	ATCArtifactFile(BuildArtifact artifact) {
		this.artifact = artifact;
	}

	public abstract void readFile(ArtifactFileReader reader);
}
