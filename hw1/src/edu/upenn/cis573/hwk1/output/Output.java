package edu.upenn.cis573.hwk1.output;

import java.io.File;

import edu.upenn.cis573.hwk1.utils.DecryptionResult;

/**
 * Expected actions for output
 * @author davehand
 */
public interface Output {

	public void displayFileResults(File file, DecryptionResult decryptionResult);
	
	public void displayAggregateResults(int correct, int incorrect);
}
