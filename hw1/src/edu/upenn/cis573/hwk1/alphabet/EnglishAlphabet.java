package edu.upenn.cis573.hwk1.alphabet;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * The English Alphabet, represented as
 * characters in a set
 * @author davehand
 */
public final class EnglishAlphabet implements Alphabet {
	
	private final static Set<Character> alphabet;
	
	static {
		Set<Character> aSet = new HashSet<>();
		aSet.add('a');
		aSet.add('b');
		aSet.add('c');
		aSet.add('d');
		aSet.add('e');
		aSet.add('f');
		aSet.add('g');
		aSet.add('h');
		aSet.add('i');
		aSet.add('j');
		aSet.add('k');
		aSet.add('l');
		aSet.add('m');
		aSet.add('n');
		aSet.add('o');
		aSet.add('p');
		aSet.add('q');
		aSet.add('r');
		aSet.add('s');
		aSet.add('t');
		aSet.add('u');
		aSet.add('v');
		aSet.add('w');
		aSet.add('x');
		aSet.add('y');
		aSet.add('z');
		alphabet = Collections.unmodifiableSet(aSet);
	}
	
	/**
	 * Helper for accessing the english alphabet set
	 * @return static set for the alphabet
	 */
	public Set<Character> getAlphabet() {
		return alphabet;
	}	
}
