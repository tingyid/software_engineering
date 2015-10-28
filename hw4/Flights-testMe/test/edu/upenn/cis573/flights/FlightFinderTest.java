package edu.upenn.cis573.flights;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

	@Test
	public void testNumFlightsDirect() {
		
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

}
