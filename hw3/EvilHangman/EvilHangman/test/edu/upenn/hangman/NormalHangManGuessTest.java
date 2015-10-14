package edu.upenn.hangman;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NormalHangManGuessTest {

	private NormalHangMan normal;
	
	@Before
	public void setUp() throws Exception {
		normal = new NormalHangMan("apple", 4, "bcd");
	}

	@Test
	public void test() {
		assertNotNull(normal);
	}

}
