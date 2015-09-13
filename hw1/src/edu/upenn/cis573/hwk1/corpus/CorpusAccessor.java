package edu.upenn.cis573.hwk1.corpus;

import java.io.File;

/**
 * Accessor method for use by any data source
 * @author davehand
 */
public interface CorpusAccessor {
	
	public File[] getFiles();
}
