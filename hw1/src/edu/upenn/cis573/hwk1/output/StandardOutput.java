package edu.upenn.cis573.hwk1.output;

import java.io.File;
import java.text.DecimalFormat;

import edu.upenn.cis573.hwk1.utils.DecryptionResult;

/**
 * Class for outputting results to stdout
 * @author davehand
 */
public class StandardOutput implements Output {

	/**
	 * Print correct/incorrect counts for a file
	 */
	@Override
	public void displayFileResults(File file, DecryptionResult decryptionResult) {
		System.out.println(file.getName() + ": " + decryptionResult.getCorrect()
				+ " correct, " + decryptionResult.getIncorrect() + " incorrect");
	}
	
	/**
	 * Print overall correct/incorrect and accuracy
	 */
	@Override
	public void displayAggregateResults(long correct, long incorrect) {
		DecimalFormat df = new DecimalFormat("###.##");
		System.out.println();
		System.out.println("Total: " + correct + " correct, " + incorrect + " incorrect");
		double accuracy = 100 * (((double) correct)/(correct+incorrect));
		System.out.println("Accuracy: " + df.format(accuracy)  + "%");
	}	
}
