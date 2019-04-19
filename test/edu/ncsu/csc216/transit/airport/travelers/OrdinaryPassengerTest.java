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
 * tests the ordinary passenger class
 * 
 * @author William
 *
 */
public class OrdinaryPassengerTest {
	
	/**
	 * tests constructor for an fastTrack passenger
	 */
	@Test
	public void testOrdinaryPassenger() {
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		assertEquals(a.getArrivalTime(), 10);
		assertEquals(a.getProcessTime(), 100);
		assertFalse("Passengers should not be created waiting in line", a.isWaitingInSecurityLine());
		
	}

	/**
	 * tests getInLine method
	 */
	@Test
	public void testGetInLine() {
		TransitGroup sa = new SecurityArea(3);
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		Passenger b = new OrdinaryPassenger(10, 115, log);
		Passenger c = new OrdinaryPassenger(10, 120, log);
		a.getInLine(sa);
		b.getInLine(sa);
		c.getInLine(sa);
		assertEquals(a.getLineIndex(), 1);
		assertEquals(b.getLineIndex(), 1);
		assertEquals(c.getLineIndex(), 1);
		assertEquals(c.getWaitTime(), 215);
	}
	
	/**
	 * tests getColor method
	 */
	@Test
	public void testGetColor() {
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 20 , log);
		Passenger b = new OrdinaryPassenger(10, 149, log);
		Color lightRed = new Color (255, 153, 153);
		Color red = new Color (255, 0, 0);
		assertEquals(a.getColor(), lightRed);
		assertEquals(b.getColor(), red);

	}
}