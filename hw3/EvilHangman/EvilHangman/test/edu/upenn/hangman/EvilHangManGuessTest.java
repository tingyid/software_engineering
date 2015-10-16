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
		
		msg = "Should reduce total number of guesses by 1";
		int expectedNumGuesses = 3;
		assertEquals(msg, expectedNumGuesses, evil.numGuessesRemaining());
		
		msg = "Should add guess to guessHistory";
		String expectedGuessHistory = "E";
		assertEquals(msg, expectedGuessHistory, evil.letterGuessHistory);
	}
	
	@Test
	public void testGuessSameCharTwice() {
		String msg = "Should return false for guessing letter twice.";
		evil.makeGuess('E');		
		boolean secondGuessResult = evil.makeGuess('E');
		assertFalse(msg, secondGuessResult);
		
		msg = "Should reduce total number of guesses by 1";
		int expectedNumGuesses = 3;
		assertEquals(msg, expectedNumGuesses, evil.numGuessesRemaining());
	}
	
	@Test
	public void testGuessNonCharacter() {
		String msg = "Should return false for a non character guess";
		boolean guessResult = evil.makeGuess('!');
		assertFalse(msg, guessResult);
		
		msg = "Should not reduce total number of guesses";
		int expectedNumGuesses = 4;
		assertEquals(msg, expectedNumGuesses, evil.numGuessesRemaining());
	}
	
	@Test
	public void testCorrectGuess() {
		String msg = "Should return true for a correct guess";
		//set the new wordlist so that we will get a correct guess
		String[] newWordlist = {"BARK"};
		evil.wordlist = newWordlist;
		evil.numWords = 1;
		boolean guessResult = evil.makeGuess('B');
		assertTrue(msg, guessResult);
		
		msg = "Should not reduce total number of guesses";
		int expectedNumGuesses = 4;
		assertEquals(msg, expectedNumGuesses, evil.numGuessesRemaining());
	}
}
