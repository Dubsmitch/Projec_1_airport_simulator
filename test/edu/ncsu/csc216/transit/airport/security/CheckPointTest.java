package edu.ncsu.csc216.transit.airport.security;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.simulation_utils.Log;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

public class CheckPointTest {
	/**
	 * tests checkpoint constructor
	 */
	@Test
	public void testCheckpointConstructor() {
		Reporter log = new Log();
		CheckPoint ck = new CheckPoint();
		
		assertEquals(ck.getTimeWhenAvailable(), 0);
		Passenger a = new OrdinaryPassenger(10, 100, log);
		ck.addToLine(a);
		assertEquals(ck.nextToGo().getArrivalTime(), 10);
		ck.nextToGo().clearSecurity();
		assertEquals(ck.size(), 1);
	}
	
	/**
	 * tests size getter method
	 */
	@Test
	public void testSize() {
		Reporter log = new Log();
		CheckPoint ck = new CheckPoint();
		
		assertEquals(ck.getTimeWhenAvailable(), 0);
		Passenger a = new OrdinaryPassenger(10, 100, log);
		ck.addToLine(a);
		assertEquals(ck.nextToGo().getArrivalTime(), 10);
		ck.nextToGo().clearSecurity();
		assertEquals(ck.size(), 1);	}
	
	/**
	 * tests removeFromLine method
	 */
	@Test
	public void testRemoveFromLine() {
		Reporter log = new Log();
		CheckPoint ck = new CheckPoint();
		
		assertEquals(ck.getTimeWhenAvailable(), 0);
		Passenger a = new OrdinaryPassenger(10, 100, log);
		ck.addToLine(a);
		assertEquals(ck.nextToGo().getArrivalTime(), 10);
		ck.nextToGo().clearSecurity();
		assertEquals(ck.size(), 1);
		ck.removeFromLine();
		assertEquals(ck.size(), 0);
		}
	/**
	 * tests hasNext method
	 */
	@Test
	public void testHasNext() {
		Reporter log = new Log();
		CheckPoint ck = new CheckPoint();
		
		assertEquals(ck.getTimeWhenAvailable(), 0);
		Passenger a = new OrdinaryPassenger(10, 100, log);
		ck.addToLine(a);
		assertEquals(ck.nextToGo().getArrivalTime(), 10);
		ck.nextToGo().clearSecurity();
		assertEquals(ck.size(), 1);
		assertTrue(ck.hasNext());
		ck.removeFromLine();
		assertEquals(ck.size(), 0);	
		assertFalse(ck.hasNext());
	}
	/**
	 * tests departTimeNext() method
	 */
	@Test
	public void testDepartTimeNext() {
		Reporter log = new Log();
		CheckPoint ck = new CheckPoint();
		
		assertEquals(ck.getTimeWhenAvailable(), 0);
		Passenger a = new OrdinaryPassenger(10, 100, log);
		Passenger b = new OrdinaryPassenger(20, 70, log);
		Passenger c = new OrdinaryPassenger(30, 75, log);
		ck.addToLine(a);
		ck.addToLine(b);
		ck.addToLine(c);
		assertEquals(ck.departTimeNext(), 110);
	}
	
	/**
	 * tests nextToGo method
	 */
	@Test
	public void testNextToGo() {
		Reporter log = new Log();
		CheckPoint ck = new CheckPoint();
		
		assertEquals(ck.getTimeWhenAvailable(), 0);
		Passenger a = new OrdinaryPassenger(10, 100, log);
		Passenger b = new OrdinaryPassenger(20, 70, log);
		Passenger c = new OrdinaryPassenger(30, 75, log);
		ck.addToLine(a);
		ck.addToLine(b);
		ck.addToLine(c);
		assertEquals(a, ck.nextToGo());
	}
	
	/**
	 * tests addtoline method
	 */
	@Test
	public void testAddToLine() {
		Reporter log = new Log();
		CheckPoint ck = new CheckPoint();
		
		assertEquals(ck.getTimeWhenAvailable(), 0);
		Passenger a = new OrdinaryPassenger(10, 100, log);
		Passenger b = new OrdinaryPassenger(20, 70, log);
		Passenger c = new OrdinaryPassenger(30, 75, log);
		ck.addToLine(a);
		ck.addToLine(b);
		ck.addToLine(c);
		//make sure all 3 were added
		assertEquals(ck.size(), 3);
		//make sure that a was added first
		assertEquals(ck.nextToGo(), a);
	}
}
