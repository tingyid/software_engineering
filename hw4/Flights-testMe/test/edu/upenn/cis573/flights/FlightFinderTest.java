package edu.upenn.cis573.flights;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis573.flights.FlightFinder;


public class FlightFinderTest {

	// field to use in the various tests
	FlightFinder ff;
	
	@Before
	public void setUp() throws Exception {
		// initialize a new FlightFinder object before EVERY test
		ff = new FlightFinder();
	}

	/*
	 * List of cases to cover:
	 * 1. Bad home parameter (don't care about case sensitivity)
	 * 2. Bad dest parameter (don't care about case sensitivity)
	 * 3. Direct = false and timeLimit < 0
	 * 4. Correct home/dest, direct=true, flight exists
	 * 5. Correct home/dest, direct=true, flight doesn't exist
	 * 6. Correct home/dest, direct=false, timeLimit >= 0, all flights less than timeLimit
	 * 
	 * Paramter possibilities:
	 * Home: valid or invalid
	 * Dest: valid or invalid
	 * Direct: true or false
	 * timeLimit: <0 or >= 0
	 */
	
	
	@Test
	public void testNumFlightsDirectFlightExists() {
		// test a direct flight that should exist
		// based on what's in Flight.allFlights
		
		// note that, if "direct" is true, then "timeLimit" should not be used
		int actual = ff.numFlights("PHL", "BOS", true, 0);
		
		int expected = 1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		// don't forget to check the side effects!!
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 1, direct.size()); // check number of elements in list
		
		Flight f = direct.get(0);
		assertEquals("Starting airport incorrect in first element when finding direct flights", "PHL", f.getStart());
		assertEquals("Ending airport incorrect in first element when finding direct flights", "BOS", f.getEnd());
			
		// check that there are no indirect flights reported
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsDirectFlightDoesNotExist() {
		// test a direct flight that should not exist
		// based on what's in Flight.allFlights
		
		// note that, if "direct" is true, then "timeLimit" should not be used
		int actual = ff.numFlights("PHL", "SEA", true, -100);
		
		int expected = 0;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		// don't forget to check the side effects!!
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size()); // check number of elements in list
			
		// check that there are no indirect flights reported
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsIndirectFlightExistsAndUnderTimeLimit() {
		// test a direct flight that should not exist
		// based on what's in Flight.allFlights
		
		// note that, if "direct" is true, then "timeLimit" should not be used
		int actual = ff.numFlights("PHL", "SEA", false, 600);
		
		int expected = 1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		// don't forget to check the side effects!!
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size()); // check number of elements in list
			
		// check that there are no indirect flights reported
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 1, indirect.size());
		
		Flight[] flights = indirect.get(0);
		assertEquals("Starting airport incorrect in first element when finding direct flights", "PHL", flights[0].getStart());
		assertEquals("Ending airport incorrect in first element when finding direct flights", "SEA", flights[1].getEnd());
	}
	
	@Test
	public void testNumFlightsIndirectFlightExistsNotUnderTimeLimit() {
		// test a direct flight that should not exist
		// based on what's in Flight.allFlights
		
		// note that, if "direct" is true, then "timeLimit" should not be used
		int actual = ff.numFlights("PHL", "SEA", false, 500);
		
		int expected = 0;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		// don't forget to check the side effects!!
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size()); // check number of elements in list
			
		// check that there are no indirect flights reported
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsIndirectFlightDoesNotExist() {
		// test a direct flight that should not exist
		// based on what's in Flight.allFlights
		
		// note that, if "direct" is true, then "timeLimit" should not be used
		int actual = ff.numFlights("BOS", "SEA", false, 1000);
		
		int expected = 0;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		// don't forget to check the side effects!!
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size()); // check number of elements in list
			
		// check that there are no indirect flights reported
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsInvalidHome() {
		// test a direct flight that should not exist
		// based on what's in Flight.allFlights
		
		// note that, if "direct" is true, then "timeLimit" should not be used
		int actual = ff.numFlights("B", "SEA", false, 1000);
		
		int expected = -1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		// don't forget to check the side effects!!
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size()); // check number of elements in list
			
		// check that there are no indirect flights reported
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsInvalidDest() {
		// test a direct flight that should not exist
		// based on what's in Flight.allFlights
		
		// note that, if "direct" is true, then "timeLimit" should not be used
		int actual = ff.numFlights("BOS", "S", false, 1000);
		
		int expected = -1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		// don't forget to check the side effects!!
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size()); // check number of elements in list
			
		// check that there are no indirect flights reported
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsDirectIsFaleAndInvalidTimeLimit() {
		// test a direct flight that should not exist
		// based on what's in Flight.allFlights
		
		// note that, if "direct" is true, then "timeLimit" should not be used
		int actual = ff.numFlights("PHL", "SEA", false, -1);
		
		int expected = -1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		// don't forget to check the side effects!!
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size()); // check number of elements in list
			
		// check that there are no indirect flights reported
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}

}
