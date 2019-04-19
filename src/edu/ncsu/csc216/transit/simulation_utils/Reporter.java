package edu.ncsu.csc216.transit.simulation_utils;

import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * Reporter describes reporting methods required to log passenger group activities. 
 * @author Jo Perry
 * 
 * -Adapted for use by William Mitchell
 */
public interface Reporter {
	
	/**
	 * How many passengers have completed all their activities?
	 * @return the number of passengers who have completed their activities
	 */
	int getNumCompleted();
	
	/**
	 * Log the data for a passenger.
	 * @param p - passenger whose data is to be logged
	 */
	void logData(Passenger p);
	
	/**
	 * How long did passengers have to wait before completing processing?
	 * @return average passenger waiting time (in minutes).
	 */
	double averageWaitTime();
	
	/**
	 * How long did it take passengers to complete processing?
	 * @return average processing time
	 */
	double averageProcessTime();
}
