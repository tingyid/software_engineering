package edu.upenn.cis573.flights;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import edu.upenn.cis573.flights.FlightFinder;

/**
 * Class for testing the functionality of
 * FlightFinder. Uses weak, robust testing
 * as opposed to strong, robust testing
 */
public class FlightFinderTest {

	// field to use in the various tests
	FlightFinder ff;
	
	@Before
	public void setUp() throws Exception {
		// initialize a new FlightFinder object before EVERY test
		ff = new FlightFinder();
	}
	
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
		// test an indirect flight that should not exist and qualify
		// based on what's in Flight.allFlights
		
		int actual = ff.numFlights("PHL", "SEA", false, 600);
		
		int expected = 1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size());
			
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 1, indirect.size());
		
		Flight[] flights = indirect.get(0);
		assertEquals("Starting airport incorrect in first element when finding direct flights", "PHL", flights[0].getStart());
		assertEquals("Ending airport incorrect in first element when finding direct flights", "SEA", flights[1].getEnd());
	}
	
	@Test
	public void testNumFlightsIndirectFlightExistsNotUnderTimeLimit() {
		// test an indirect flight that should not exist and doesn't qualify
		// based on what's in Flight.allFlights
		
		int actual = ff.numFlights("PHL", "SEA", false, 500);
		
		int expected = 0;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size());
			
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsIndirectFlightDoesNotExist() {
		// test a direct and indirect flight that should not exist
		// based on what's in Flight.allFlights
		
		int actual = ff.numFlights("BOS", "SEA", false, 1000);
		
		int expected = 0;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size());
			
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsInvalidHome() {
		// test invalid code for the home parameter
		// based on what's in Flight.allFlights
		
		int actual = ff.numFlights("B", "SEA", false, 1000);
		
		int expected = -1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size());
			
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsHomeNull() {
		// test null for the home parameter
		// based on what's in Flight.allFlights
		
		int actual = ff.numFlights(null, "SEA", false, 1000);
		
		int expected = -1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size());
			
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsInvalidDest() {
		// test invalid code for the dest parameter
		// based on what's in Flight.allFlights
		
		int actual = ff.numFlights("BOS", "S", false, 1000);
		
		int expected = -1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size());
			
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsDestNull() {
		// test null for the dest parameter
		// based on what's in Flight.allFlights
		
		int actual = ff.numFlights("BOS", null, false, 1000);
		
		int expected = -1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size());
			
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}
	
	@Test
	public void testNumFlightsDirectIsFaleAndInvalidTimeLimit() {
		// test direct is false and the timeLimit is invalid
		
		int actual = ff.numFlights("PHL", "SEA", false, -1);
		
		int expected = -1;
		assertEquals("Return value from numFlights incorrect when finding direct flights", expected, actual);
		
		List<Flight> direct = ff.getDirectFlights();
		assertEquals("Size of directFlights incorrect when finding direct flights", 0, direct.size());
			
		List<Flight[]> indirect = ff.getIndirectFlights();
		assertEquals("Size of indirectFlights incorrect when finding direct flights", 0, indirect.size());
	}

}
