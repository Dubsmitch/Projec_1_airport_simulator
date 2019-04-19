package edu.ncsu.csc216.transit.airport.security;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.simulation_utils.Log;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

public class SecurityAreaTest {
	/**
	 * tests the securityArea constructor
	 */
	@Test
	public void testSecurityAreaConstructr() {
		SecurityArea sa = new SecurityArea(3);
		//test that all three checkpoints were created
		assertEquals(sa.lengthOfLine(0), 0);
		assertEquals(sa.lengthOfLine(1), 0);
		assertEquals(sa.lengthOfLine(2), 0);
		
		//try {
		//	SecurityArea b = new SecurityArea(1);
		//	fail("Shouldn't be able to construct with less than 3 checkpoints");
		//} catch (IllegalArgumentException e) {
		//	assertNull(b);
		//}
	}
	
	/**
	 * tests addToLine method
	 */
	@Test
	public void testAddToLine() {
		Reporter log = new Log();
		SecurityArea sa = new SecurityArea(3);
		//test that all three checkpoints were created
		assertEquals(sa.lengthOfLine(0), 0);
		assertEquals(sa.lengthOfLine(1), 0);
		assertEquals(sa.lengthOfLine(2), 0);	
		
		Passenger a = new OrdinaryPassenger(10, 100, log);
		Passenger b = new OrdinaryPassenger(20, 70, log);
		Passenger c = new OrdinaryPassenger(30, 75, log);
		sa.addToLine(1, b);
		sa.addToLine(0, a);
		sa.addToLine(2, c);
		
		assertEquals(sa.lengthOfLine(0), 1);
		assertEquals(sa.lengthOfLine(1), 1);
		assertEquals(sa.lengthOfLine(2), 1);
	}
	
	/**
	 * tests shortestRegularLine method
	 */
	@Test
	public void testShortestRegularLine() {
		Reporter log = new Log();
		SecurityArea sa = new SecurityArea(7);
		//test that all three checkpoints were created
		assertEquals(sa.lengthOfLine(0), 0);
		assertEquals(sa.lengthOfLine(1), 0);
		assertEquals(sa.lengthOfLine(2), 0);	
		
		Passenger a = new OrdinaryPassenger(10, 100, log);
		Passenger b = new OrdinaryPassenger(20, 70, log);
		Passenger c = new OrdinaryPassenger(20, 70, log);
		Passenger d = new OrdinaryPassenger(20, 70, log);
		Passenger e = new OrdinaryPassenger(20, 70, log);
		Passenger f = new OrdinaryPassenger(20, 70, log);
		Passenger g = new OrdinaryPassenger(20, 70, log);
		Passenger h = new OrdinaryPassenger(20, 70, log);
		Passenger i = new OrdinaryPassenger(20, 70, log);
		//Passenger j = new OrdinaryPassenger(20, 70, log);
		//Passenger k = new OrdinaryPassenger(20, 70, log);
		//Passenger l = new OrdinaryPassenger(20, 70, log);
		//Passenger m = new OrdinaryPassenger(20, 70, log);
		
		
		sa.addToLine(1, b);
		sa.addToLine(0, a);
		sa.addToLine(2, c);
		sa.addToLine(3, d);
		sa.addToLine(4, e);
		sa.addToLine(5, f);
		sa.addToLine(6, g);

		
		assertEquals(sa.shortestRegularLine(), 3);

		sa.addToLine(3, h);
		
		assertEquals(sa.shortestRegularLine(), 4);
		
		sa.addToLine(4, i);
		
		assertEquals(sa.shortestRegularLine(), 5);
		
	}
	/**
	 * tests shortestTSAPreLine method
	 */
	@Test
	public void testShortestTSAPreLine() {
		fail("Not yet implemented");	
	}
	
	/**
	 * tests shortestFastTrackLine method
	 */
	@Test
	public void testShortestFastTrackLine() {
		Reporter log = new Log();
		SecurityArea sa = new SecurityArea(7);
		//test that all three checkpoints were created
		assertEquals(sa.lengthOfLine(0), 0);
		assertEquals(sa.lengthOfLine(1), 0);
		assertEquals(sa.lengthOfLine(2), 0);	
		
		Passenger a = new OrdinaryPassenger(10, 100, log);
		Passenger b = new OrdinaryPassenger(20, 70, log);
		Passenger c = new OrdinaryPassenger(20, 70, log);
		Passenger d = new OrdinaryPassenger(20, 70, log);
		Passenger e = new OrdinaryPassenger(20, 70, log);
		Passenger f = new OrdinaryPassenger(20, 70, log);
		Passenger g = new OrdinaryPassenger(20, 70, log);
		Passenger h = new OrdinaryPassenger(20, 70, log);
		Passenger i = new OrdinaryPassenger(20, 70, log);
		Passenger j = new OrdinaryPassenger(20, 70, log);
		Passenger k = new OrdinaryPassenger(20, 70, log);
		Passenger l = new OrdinaryPassenger(20, 70, log);
		Passenger m = new OrdinaryPassenger(20, 70, log);
		
		
		sa.addToLine(1, b);
		sa.addToLine(0, a);
		sa.addToLine(2, c);
		sa.addToLine(3, d);
		sa.addToLine(4, e);
		sa.addToLine(5, f);
		sa.addToLine(6, g);

		
		assertEquals(sa.shortestFastTrackLine(), 0);

		sa.addToLine(0, h);
		
		assertEquals(sa.shortestFastTrackLine(), 1);
		
		sa.addToLine(1, i);
		
		assertEquals(sa.shortestFastTrackLine(), 2);

		sa.addToLine(2, j);
		
		assertEquals(sa.shortestFastTrackLine(), 3);
		
		sa.addToLine(3, k);
		
		assertEquals(sa.shortestFastTrackLine(), 4);
		
		sa.addToLine(4, l);
		
		assertEquals(sa.shortestFastTrackLine(), 5);
		
		sa.addToLine(5, m);
		
		assertEquals(sa.shortestFastTrackLine(), 0);
	}
	
	/**
	 * tests lengthOfLine method
	 */
	@Test
	public void testLengthOfLine() {
		Reporter log = new Log();
		SecurityArea sa = new SecurityArea(7);
		//test that all three checkpoints were created
		assertEquals(sa.lengthOfLine(0), 0);
		assertEquals(sa.lengthOfLine(1), 0);
		assertEquals(sa.lengthOfLine(2), 0);	
		
		Passenger a = new OrdinaryPassenger(10, 100, log);	
		sa.addToLine(0, a);
		assertEquals(sa.lengthOfLine(0), 1);
	}
	
	/**
	 * tests departTimeNext method
	 */
	@Test
	public void testDepartTimeNext() {
		Reporter log = new Log();
		SecurityArea sa = new SecurityArea(7);
		//test that all three checkpoints were created
		assertEquals(sa.lengthOfLine(0), 0);
		assertEquals(sa.lengthOfLine(1), 0);
		assertEquals(sa.lengthOfLine(2), 0);	
		
		Passenger a = new OrdinaryPassenger(10, 100, log);
		Passenger b = new OrdinaryPassenger(20, 70, log);
		Passenger c = new OrdinaryPassenger(20, 70, log);
		Passenger d = new OrdinaryPassenger(20, 70, log);
		Passenger e = new OrdinaryPassenger(20, 70, log);
		Passenger f = new OrdinaryPassenger(20, 70, log);
		Passenger g = new OrdinaryPassenger(20, 70, log);
		Passenger h = new OrdinaryPassenger(20, 70, log);
		Passenger i = new OrdinaryPassenger(20, 70, log);
		Passenger j = new OrdinaryPassenger(20, 70, log);
		Passenger k = new OrdinaryPassenger(20, 70, log);
		Passenger l = new OrdinaryPassenger(20, 70, log);
		Passenger m = new OrdinaryPassenger(20, 70, log);
		
		
		sa.addToLine(1, b);
		sa.addToLine(0, a);
		sa.addToLine(2, c);
		sa.addToLine(3, d);
		sa.addToLine(4, e);
		sa.addToLine(5, f);
		sa.addToLine(6, g);
	
		assertEquals(sa.departTimeNext(), (sa.nextToGo().getArrivalTime()+sa.nextToGo().getProcessTime()));
	}
	
	/**
	 * tests nextToGo method
	 */
	@Test
	public void testNextToGo() {
		Reporter log = new Log();
		SecurityArea sa = new SecurityArea(7);
		//test that all three checkpoints were created
		assertEquals(sa.lengthOfLine(0), 0);
		assertEquals(sa.lengthOfLine(1), 0);
		assertEquals(sa.lengthOfLine(2), 0);	
		
		Passenger a = new OrdinaryPassenger(10, 100, log);
		Passenger b = new OrdinaryPassenger(20, 70, log);
		Passenger c = new OrdinaryPassenger(20, 70, log);
		Passenger d = new OrdinaryPassenger(20, 70, log);
		Passenger e = new OrdinaryPassenger(20, 70, log);
		Passenger f = new OrdinaryPassenger(20, 70, log);
		Passenger g = new OrdinaryPassenger(20, 70, log);
		Passenger h = new OrdinaryPassenger(20, 70, log);
		Passenger i = new OrdinaryPassenger(20, 70, log);
		Passenger j = new OrdinaryPassenger(20, 70, log);
		Passenger k = new OrdinaryPassenger(20, 70, log);
		Passenger l = new OrdinaryPassenger(20, 70, log);
		Passenger m = new OrdinaryPassenger(20, 70, log);
		
		
		sa.addToLine(1, b);
		sa.addToLine(0, a);
		sa.addToLine(2, c);
		sa.addToLine(3, d);
		sa.addToLine(4, e);
		sa.addToLine(5, f);
		sa.addToLine(6, g);
	
		assertEquals(b, sa.nextToGo());	
	}
	
	/**
	 * tests removeNext method
	 */
	@Test
	public void testRemoveNext() {
		Reporter log = new Log();
		SecurityArea sa = new SecurityArea(3);
		//test that all three checkpoints were created
		assertEquals(sa.lengthOfLine(0), 0);
		assertEquals(sa.lengthOfLine(1), 0);
		assertEquals(sa.lengthOfLine(2), 0);	
		
		Passenger a = new OrdinaryPassenger(10, 100, log);
		Passenger b = new OrdinaryPassenger(20, 70, log);
		
		
		sa.addToLine(1, b);
		sa.addToLine(0, a);

		
		assertEquals(sa.lengthOfLine(1), 1);
		sa.removeNext();
		assertEquals(0, sa.lengthOfLine(1));		}
}
