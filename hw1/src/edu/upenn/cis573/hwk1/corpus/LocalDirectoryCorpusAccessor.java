package edu.upenn.cis573.hwk1.corpus;

import java.io.File;

/**
 * Provides access to a corpus on the local machine
 * @author davehand
 */
public class LocalDirectoryCorpusAccessor implements CorpusAccessor {

	private final File corpusDirectory;
	
	public LocalDirectoryCorpusAccessor(File corpusDirectory) {
		this.corpusDirectory = corpusDirectory;
	}
	
	@Override
	public File[] getFiles() {
		return corpusDirectory.listFiles();
	}
}
