package edu.upenn.cis573.hwk1.alphabet;

import java.util.Set;

/**
 * Expected behavior for all alphabets
 * @author davehand
 */
public interface Alphabet {
	
	/**
	 * Accessor for other classes to get the
	 * characters of the alphabet
	 * @return the alphabet
	 */
	public Set<Character> getAlphabet();
}
