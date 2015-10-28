package edu.upenn.cis573.flights;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class FlightFinder {

	private List<Flight> directFlights = new ArrayList<Flight>();
	private List<Flight[]> indirectFlights = new ArrayList<Flight[]>();
	
	public List<Flight> getDirectFlights() { 
		return directFlights; 
	}
	public List<Flight[]> getIndirectFlights() { 
		return indirectFlights; 
	}
	
	/**
	 * Look through the (hard-coded) list of flights and return the number
	 * of flights from the home airport to the destination, according to the specification. 
	 */
	public int numFlights(String home, String dest, boolean direct, int timeLimit)
	{
		/*
		 * You are not being asked to implement this!
		 * You need to write black-box tests against the specification
		 */
		
		return Integer.MIN_VALUE;
		
	}
		

}
