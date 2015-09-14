package edu.upenn.cis573.hwk1.utils;

/**
 * Class for recording the number of correct
 * and incorrect instances for a file
 * @author davehand
 */
public final class DecryptionResult {

	private final long correct;
	private final long incorrect;
	
	public DecryptionResult(long correct, long incorrect) {
		this.correct = correct;
		this.incorrect = incorrect;
	}
	
	public long getCorrect() {
		return correct;
	}
	
	public long getIncorrect() {
		return incorrect;
	}
}
