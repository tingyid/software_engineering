package edu.upenn.hangman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GUI_PlayGameControllerTest {

	private GUI_PlayGame cont;
	
	@Before
	public void setUp() throws Exception {
		cont = new GUI_PlayGame(4, 2);
		cont.show();
	}

	@Test
	public void testBadGuess() {
		String msg = "Should display Nope! for an incorrect guess";
		cont.inputLetter = 'E';
		cont.controller();
		String expectedDisplayString = "Nope!";
		assertEquals(msg, expectedDisplayString, cont.result.getText());
		
		msg = "Should reduce total number of guesses by 1";
		int expectedNumGuesses = 1;
		assertEquals(msg, expectedNumGuesses, cont.game.numGuessesRemaining());
		
		verifyLabels();
	}
	
	@Test
	public void testGoodGuessEvil() {
		String msg = "Should display Yes! for a correct guess";
		setNewWordList("BARK");
		cont.inputLetter = 'B';
		cont.controller();
		String expectedDisplayString = "Yes!";
		assertEquals(msg, expectedDisplayString, cont.result.getText());
			
		msg = "Total number of guesses should remain the same";
		int expectedNumGuesses = 2;
		assertEquals(msg, expectedNumGuesses, cont.game.numGuessesRemaining());
		
		verifyLabels();
	}
	
	@Test
	public void testGoodGuessNormal() {
		String msg = "Should return true for a guess that must be correct";
		cont.game = new NormalHangMan("BARK", 2, "");
		cont.isEvil = false;
		cont.inputLetter = 'A';
		cont.controller();
		String expectedDisplayString = "Yes!";
		assertEquals(msg, expectedDisplayString, cont.result.getText());
		
		msg = "Total number of guesses should remain the same";
		int expectedNumGuesses = 2;
		assertEquals(msg, expectedNumGuesses, cont.game.numGuessesRemaining());
		
		verifyLabels();
	}
	
	@Test
	public void testWinGame() {
		String msg = "Game should have value true for isWin";
		cont.game = new NormalHangMan("BBBB", 2, "");
		cont.isEvil = false;
		cont.inputLetter = 'B';
		cont.controller();
				
		assertTrue(msg, cont.game.isWin());
		
		verifyLabels();
	}
	
	@Test
	public void testLoseGame() {
		String msg = "Game should have value false for isWin";
		cont.game = new NormalHangMan("BARK", 1, "");
		cont.isEvil = false;
		cont.inputLetter = 'C';
		cont.controller();
				
		assertFalse(msg, cont.game.isWin());
		
		verifyLabels();
	}
	
	private void setNewWordList(String newWordList) {
		String[] newWordlist = {newWordList};
		EvilHangMan game = (EvilHangMan) cont.game;
		game.wordlist = newWordlist;
		game.numWords = 1;
	}
	
	private void verifyLabels() {
		String msg = "Label2 should have the correct value";
		String label2Expected = "Secret Word: "+ cont.game.displayGameState();
		assertEquals(msg, label2Expected, cont.label2.getText());
		
		msg = "Label3 should have the correct value";
		String label3Expected = "Guesses Remaining: " + cont.game.numGuessesRemaining();
		assertEquals(msg, label3Expected, cont.label3.getText());
	}
}
