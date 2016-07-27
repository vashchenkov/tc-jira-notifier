package ru.gubber.citools.artifacts.readers;

import ru.gubber.citools.TJPConstants;

import java.io.InputStream;

/**
 * Created by gubber on 24.11.2015.
 */
public abstract class ArtifactFileReader implements TJPConstants{

	public abstract void readStream(InputStream is);
}