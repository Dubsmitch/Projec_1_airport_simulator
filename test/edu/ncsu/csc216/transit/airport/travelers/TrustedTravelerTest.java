package edu.ncsu.csc216.transit.airport.travelers;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Log;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

public class TrustedTravelerTest {

	/**
	 * tests constructor for an trustedTraveler passenger
	 */
	@Test
	public void testTrustedTraveler() {
		Reporter log = new Log();
		Passenger a = new TrustedTraveler(10, 100 , log);
		assertEquals(a.getArrivalTime(), 10);
		assertEquals(a.getProcessTime(), 100);
		assertFalse("Passengers should not be created waiting in line", a.isWaitingInSecurityLine());
	}
	
	/**
	 * tests pickLine method
	 */
	//@Test
	//public void testPickLine() {
	//	TransitGroup sa = new SecurityArea(3);
	//	Reporter log = new Log();
	//	Passenger a = new TrustedTraveler(10, 100 , log);
	//	Passenger b = new TrustedTraveler(10, 115, log);
	//	Passenger c = new TrustedTraveler(10, 120, log);
	//	a.getInLine(sa);
	//	b.getInLine(sa);
	//	c.getInLine(sa);
	//	assertEquals(a.getLineIndex(), 1);
	//	assertEquals(b.getLineIndex(), 1);
	//	assertEquals(c.getLineIndex(), 1);
	//	assertEquals(c.getWaitTime(), 215);
	//}

	/**
	 * tests getInLine method
	 */
	@Test
	public void testGetInLine() {
		TransitGroup sa = new SecurityArea(3);
		Reporter log = new Log();
		Passenger a = new TrustedTraveler(10, 100 , log);
		Passenger b = new TrustedTraveler(10, 115, log);
		Passenger c = new TrustedTraveler(10, 120, log);
		a.getInLine(sa);
		b.getInLine(sa);
		c.getInLine(sa);
		assertEquals(a.getLineIndex(), 2);
		assertEquals(b.getLineIndex(), 2);
		assertEquals(c.getLineIndex(), 1);
		assertEquals(c.getWaitTime(), 0);
		assertEquals(b.getWaitTime(), 100);
	}
	
	/**
	 * tests getColor method
	 */
	@Test
	public void testGetColor() {
		Reporter log = new Log();
		Passenger a = new TrustedTraveler(10, 20 , log);
		Passenger b = new TrustedTraveler(10, 149, log);
		Color lightBlue = new Color (153, 153, 255);
		Color blue = new Color (0, 0, 255);
		assertEquals(a.getColor(), lightBlue);
		assertEquals(b.getColor(), blue);	
	}
}