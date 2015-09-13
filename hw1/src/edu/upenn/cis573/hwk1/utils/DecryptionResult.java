package edu.upenn.cis573.hwk1.utils;

/**
 * Class for recording the number of correct
 * and incorrect instances for a file
 * @author davehand
 */
public final class DecryptionResult {

	private final int correct;
	private final int incorrect;
	
	public DecryptionResult(int correct, int incorrect) {
		this.correct = correct;
		this.incorrect = incorrect;
	}
	
	public int getCorrect() {
		return correct;
	}
	
	public int getIncorrect() {
		return incorrect;
	}
}
