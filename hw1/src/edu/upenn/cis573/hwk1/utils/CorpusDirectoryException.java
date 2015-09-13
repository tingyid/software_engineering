package edu.upenn.cis573.hwk1.utils;

/**
 * Exception to be thrown if there are problems with
 * the corpus directory passed in to the program
 * @author davehand
 */
public final class CorpusDirectoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Simple constructor for the exception
	 * @param message helpful message for the user
	 */
	public CorpusDirectoryException(String message) {
		super(message);
	}
}
