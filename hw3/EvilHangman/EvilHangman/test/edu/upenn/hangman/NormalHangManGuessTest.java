package edu.upenn.hangman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NormalHangManGuessTest {

	private NormalHangMan normal;
	
	@Before
	public void setUp() throws Exception {
		normal = new NormalHangMan("APPLE", 4, "BCD");
	}

	@Test
	public void testBadGuess() {
		String msg = "Should return false for a bad guess";
		boolean guessResult = normal.makeGuess('I');
		assertFalse(msg, guessResult);
		
		msg = "Should reduce total number of guesses by 1";
		int expectedNumGuesses = 3;
		assertEquals(msg, expectedNumGuesses, normal.numGuessesRemaining());
		
		msg = "Should add guess to guessHistory";
		String expectedGuessHistory = "BCDI";
		assertEquals(msg, expectedGuessHistory, normal.history);
	}
	
	@Test
	public void testGuessSameCharTwice() {
		String msg = "Should return false when guessing letter both times.";
		normal.history += "I";
		normal.guessesRemaining--;
		boolean secondGuessResult = normal.makeGuess('I');
		assertFalse(msg, secondGuessResult);
		
		msg = "Should reduce total number of guesses by 1";
		int expectedNumGuesses = 3;
		assertEquals(msg, expectedNumGuesses, normal.numGuessesRemaining());
	}
	
	@Test
	public void testGuessNonCharacter() {
		String msg = "Should return false for a non character guess";
		boolean guessResult = normal.makeGuess('!');
		assertFalse(msg, guessResult);
		
		msg = "Should not reduce total number of guesses";
		int expectedNumGuesses = 4;
		assertEquals(msg, expectedNumGuesses, normal.numGuessesRemaining());
	}
	
	@Test
	public void testCorrectGuess() {
		String msg = "Should return true for a guess that must be correct";
		boolean guessResult = normal.makeGuess('A');
		assertTrue(msg, guessResult);
		
		msg = "Should not reduce total number of guesses";
		int expectedNumGuesses = 4;
		assertEquals(msg, expectedNumGuesses, normal.numGuessesRemaining());
	}
}
