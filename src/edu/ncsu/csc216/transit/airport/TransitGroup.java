package edu.ncsu.csc216.transit.airport;

import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * TransitGroup declares some common operations for a group of passengers in which there is a "next" passenger
 *   and a time when that passenger will leave the group. 
 * @author Jo Perry
 * 
 * -adapted into William's project
 */
public interface TransitGroup {
	
	/**
	 * Who will be the next passenger to leave the group?
	 * @return the next passenger
	 */
	Passenger nextToGo();

	/**
	 * When will the next passenger leave the group?
	 * @return departure time of the next passenger or Integer.MAX_VALUE if the group is empty
	 */
	int departTimeNext();
	
	/**
	 * Removes the next passenger to leave the group.
	 * @return the passenger who is removed
	 */
	Passenger removeNext();

}