package edu.ncsu.csc216.transit.airport.travelers;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Log;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;
public class PassengerTest {
	
	/**
	 * tests the constructor for passenger class
	 */
	@Test
	public void testPassenger() {
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		assertEquals(a.getArrivalTime(), 10);
		assertEquals(a.getProcessTime(), 100);
		assertFalse("Passengers should not be created waiting in line", a.isWaitingInSecurityLine());
		
	}
	
	/**
	 * tests getArrivalTime method
	 */
	@Test
	public void testGetArrivalTime() {
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		assertEquals(a.getArrivalTime(), 10);
	}
	
	/**
	 * tests getWaitTime method
	 */
	@Test
	public void testGetWaitTime() {
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
	 * tests setWaitTime method
	 */
	@Test
	public void testSetWaitTime() {
		//TransitGroup sa = new SecurityArea(3);
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		//Passenger b = new OrdinaryPassenger(10, 115, log);
		//Passenger c = new OrdinaryPassenger(10, 120, log);
		a.setWaitTime(200);
		assertEquals(a.getWaitTime(), 200);
	}
	
	/**
	 * tests getProcessTime method
	 */
	@Test
	public void testGetProcessTime() {
		//TransitGroup sa = new SecurityArea(3);
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		//Passenger b = new OrdinaryPassenger(10, 115, log);
		//Passenger c = new OrdinaryPassenger(10, 120, log);
		assertEquals(a.getProcessTime(), 100);	
	}
	
	/**
	 * tests getLineIndex method
	 */
	@Test
	public void testGetLineIndex() {
		TransitGroup sa = new SecurityArea(3);
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		//Passenger b = new OrdinaryPassenger(10, 115, log);
		//Passenger c = new OrdinaryPassenger(10, 120, log);
		assertEquals(a.getLineIndex(), 0);
		a.getInLine(sa);
		assertEquals(a.getLineIndex(), 1);
	}
	/**
	 * tests isWaitingInSecurityLine method
	 */
	@Test
	public void testIsWaitingInSecurityLine() {
		TransitGroup sa = new SecurityArea(3);
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		//Passenger b = new OrdinaryPassenger(10, 115, log);
		//Passenger c = new OrdinaryPassenger(10, 120, log);
		assertEquals(a.isWaitingInSecurityLine(), false);
		a.getInLine(sa);
		assertEquals(a.isWaitingInSecurityLine(), true);	
	}
	
	/**
	 * tests clearSecurity method
	 */
	@Test
	public void testClearSecurity() {
		TransitGroup sa = new SecurityArea(3);
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		//Passenger b = new OrdinaryPassenger(10, 115, log);
		//Passenger c = new OrdinaryPassenger(10, 120, log);
		assertEquals(a.isWaitingInSecurityLine(), false);
		a.getInLine(sa);
		assertEquals(a.isWaitingInSecurityLine(), true);
		a.clearSecurity();
		assertEquals(a.isWaitingInSecurityLine(), false);
		assertEquals(log.getNumCompleted(), 1);
		assertTrue(log.averageProcessTime() == 100);
	}
	
	/**
	 * tests setLineIndex method
	 */
	@Test
	public void testSetLineIndex() {
//		TransitGroup sa = new SecurityArea(3);
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100 , log);
		//Passenger b = new OrdinaryPassenger(10, 115, log);
		//Passenger c = new OrdinaryPassenger(10, 120, log);
		a.setLineIndex(5);
		assertEquals(a.getLineIndex(), 5);
	}
}
