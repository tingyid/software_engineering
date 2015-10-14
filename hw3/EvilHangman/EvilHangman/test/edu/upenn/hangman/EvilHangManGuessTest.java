package edu.upenn.hangman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EvilHangManGuessTest {

	private EvilHangMan evil;
	
	@Before
	public void setUp() throws Exception {
		evil = new EvilHangMan(4, 4);
	}
	
	@Test
	public void testFirstGuess() {
		String msg = "Guess should return false since there are words without 'e'.";
		
		boolean guessResult = evil.makeGuess('E');
		assertFalse(msg, guessResult);
		
		msg = "Secret word after guess shouldn't have an 'e'";
		boolean hasE = evil.getSecretWord().indexOf('E') > -1;
		assertFalse(msg, hasE);
	}
	
	@Test
	public void testGuessSameCharTwice() {
		String msg = "Should return false when guessing letter both times.";
		
		boolean guessResult = evil.makeGuess('E');
		assertFalse(msg, guessResult);
		
		boolean secondGuessResult = evil.makeGuess('E');
		assertFalse(msg, secondGuessResult);
	}
	
	@Test
	public void testGuessNonCharacter() {
		String msg = "Should return false for a non character guess";
		
		boolean guessResult = evil.makeGuess('!');
		assertFalse(msg, guessResult);
	}
	
	@Test
	public void testCorrectGuess() {
		String msg = "Should return true for a guess that must be correct";
		
		String[] newWordlist = {"BARK"};
		evil.wordlist = newWordlist;
		evil.numWords = 1;
		
		boolean guessResult = evil.makeGuess('B');
		
		assertTrue(msg, guessResult);
	}

}
