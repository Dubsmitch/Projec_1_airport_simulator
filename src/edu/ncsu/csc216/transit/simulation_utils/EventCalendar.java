package edu.ncsu.csc216.transit.simulation_utils;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.entrance.PreSecurity;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * provides methods to determine which passengers to process next
 * 
 * @author William Mitchell
 *
 */
public class EventCalendar {
	/** creates a transit group of High Priority **/
	private TransitGroup HighPriority;
	/** creates a transit group of Low Priority **/
	private TransitGroup LowPriority;
	/**
	 * creates an Event Calendar from which to determine
	 * the next passenger to be processed
	 * @param transitGroup1
	 * 		the transit group representing the ticketing area
	 * @param transitGroup2
	 * 		the transit group representing the security area
	 */
	public EventCalendar(TransitGroup highPriority, TransitGroup lowPriority) {
		//securityarea is the low priority
		//create an instance
		LowPriority= (SecurityArea) lowPriority;
		//presecurity line is the highpriority
		//create an instance
		HighPriority = (PreSecurity) highPriority;
		
		
	}
	
	/**
	 * determines which passenger needs to be processed next
	 * 
	 * @return Passenger
	 * 		the next passenger to be processed
	 */
	public Passenger nextToAct() {
		if (HighPriority.nextToGo() == null && LowPriority.nextToGo() == null) {
			throw new IllegalStateException ("can't do this"); //fix me later//
		}
		
		
		if (HighPriority.departTimeNext() == LowPriority.departTimeNext()) {
			return HighPriority.nextToGo();
		} 
		
		else if (HighPriority.departTimeNext() < LowPriority.departTimeNext()) {

			return HighPriority.nextToGo();
		} 
		
		
		else {
			System.out.println("presec is greater than check");

			return LowPriority.nextToGo();
		}
	}
}

