package edu.ncsu.csc216.transit.airport.entrance;

import static org.junit.Assert.*;

import org.junit.Test;

import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.simulation_utils.Log;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

public class PreSecurityTest {
	/**
	 * tests the security Area constructor
	 */
	@Test
	public void testPreSecurityConstructor() {
		Reporter log = new Log();
		PreSecurity ps = new PreSecurity(10, log);
		assertEquals(ps.getOutSideSecurity().size(), 10);
		try {
			PreSecurity a = new PreSecurity(0, log);
			fail("Can't have less than 1 passenger");
		} catch (IllegalArgumentException e){
			//not sure what to put here
		}
	}
	
	/**
	 * tests the numGatesOK method
	 */
	@Test
	public void testDepartTimeNext() {
		Reporter log = new Log();
		Passenger a = new OrdinaryPassenger(10, 100, log);
		PreSecurity ps = new PreSecurity(1, log);
		ps.getOutSideSecurity().add(a);
		assertEquals(ps.getOutSideSecurity().size(), 2);
		ps.removeNext();
		assertEquals(ps.getOutSideSecurity().size(), 1);
		//assertEquals(ps.getOutSideSecurity().front().getProcessTime(), 98); 
		assertEquals(ps.departTimeNext(), 10);
	}
	
	/**
	 * tests the addToLine method
	 */
	@Test
	public void testNextToGo() {		
		Reporter log = new Log();
		PreSecurity ps = new PreSecurity(10, log);
		assertEquals(ps.getOutSideSecurity().size(), 10);
		assertEquals(ps.getOutSideSecurity().front(), ps.nextToGo());
	}
	
	/**
	 * tests the shortestRegularLine method
	 */
	@Test
	public void testHasNext() {
		Reporter log = new Log();
		PreSecurity ps = new PreSecurity(1, log);
		assertEquals(ps.getOutSideSecurity().size(), 1);
		assertEquals(ps.hasNext(), true);
		ps.removeNext();
		assertEquals(ps.getOutSideSecurity().size(), 0);
		assertEquals(ps.hasNext(), false);
	}
	
	/**
	 * tests the removeNext method
	 */
	@Test
	public void testRemoveNext() {
		Reporter log = new Log();
		PreSecurity ps = new PreSecurity(1, log);
		assertEquals(ps.getOutSideSecurity().size(), 1);
		assertEquals(ps.hasNext(), true);
		ps.removeNext();
		assertEquals(ps.getOutSideSecurity().size(), 0);
	}
}
