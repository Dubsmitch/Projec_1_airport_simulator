package edu.ncsu.csc216.transit.airport.travelers;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Log;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;
/**
 * test class for fast Track passenger class
 * @author William
 *
 */
public class FastTrackPassengerTest {
	
	/**
	 * tests constructor for an fastTrack passenger
	 */
	@Test
	public void testFastTrackPassenger() {
		Reporter log = new Log();
		Passenger a = new FastTrackPassenger(10, 100 , log);
		assertEquals(a.getArrivalTime(), 10);
		assertEquals(a.getProcessTime(), 100);
		assertFalse("Passengers should not be created waiting in line", a.isWaitingInSecurityLine());
		
	}
	
	/**
	 * tests pickLine method
	 */
	//@Test
	//public void testPickLine() {
		//SecurityArea sa = new SecurityArea(3);
		//Reporter log = new Log();
		//Passenger a = new FastTrackPassenger(10, 100 , log);
		//a.pickLine(sa);
		
	//	fail("Not yet implemented");
	//}

	/**
	 * tests getInLine method
	 */
	@Test
	public void testGetInLine() {
		TransitGroup sa = new SecurityArea(3);
		Reporter log = new Log();
		Passenger a = new FastTrackPassenger(10, 100 , log);
		Passenger b = new FastTrackPassenger(10, 200, log);
		Passenger c = new FastTrackPassenger(10, 175, log);
		a.getInLine(sa);
		b.getInLine(sa);
		c.getInLine(sa);
		assertEquals(a.getLineIndex(), 0);
		assertEquals(b.getLineIndex(), 1);
		assertEquals(c.getLineIndex(), 0);
	}
	
	/**
	 * tests getColor method
	 */
	@Test
	public void testGetColor() {
		Reporter log = new Log();
		Passenger a = new FastTrackPassenger(10, 100 , log);
		Passenger b = new FastTrackPassenger(10, 200, log);
		Color blue = new Color (153, 153, 255);
		Color darkBlue = new Color (0, 0, 255);
		assertEquals(a.getColor(), blue);
		assertEquals(b.getColor(), darkBlue);

	}
}

