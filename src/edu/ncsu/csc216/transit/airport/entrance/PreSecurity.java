package edu.ncsu.csc216.transit.airport.entrance;



import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.airport.travelers.PassengerQueue;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * this class represents passengers who have not yet entered a security checkpoint line
 * 
 * @author William
 *
 */
public class PreSecurity implements TransitGroup {
	
	/** creates a passenger Queue somehow **/
	private PassengerQueue outsideSecurity;
	
	/**
	 * creates passengers and a reporter log and adds them
	 * to the queue
	 * 
	 * @param number
	 * 			the number of passengers to generate
	 * @param reporter
	 * 			the reporter log
	 * 
	 * @throws IllegalArgumentException
	 * 			throws and IAE if the number of passengers is negative
	 */
	public PreSecurity(int number, Reporter log) {
		outsideSecurity = new PassengerQueue();
		
		if (number < 1) {
			throw new IllegalArgumentException ("There must be at least one passenger.");
		}

		for (int i = 0; i < number; i++) {
			outsideSecurity.add(Ticketing.generatePassenger(log));
		}
		
	}
	/**
	 * returns the arrival time of the passenger in the
	 * front of the line.
	 * 
	 * @return int
	 * 			the time of departure
	 */
	public int departTimeNext() {
		if (outsideSecurity.front() == null) {
			return Integer.MAX_VALUE;
		}
		int departureTime = outsideSecurity.front().getArrivalTime();

		return departureTime;
	}
	
	/**
	 * gives the next passenger
	 * 
	 * @return Passenger
	 * 		The next passenger to go
	 */
	public Passenger nextToGo() {
		return outsideSecurity.front();
	}
	
	/**
	 * Whether or not there is another Passenger
	 * @return boolean
	 * 			if there is another passenger 
	 */
	public boolean hasNext() {
		if(outsideSecurity.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * removes the next passenger
	 * @return Passenger
	 * 		returns the passenger to be removed
	 */
	public Passenger removeNext() {
		return outsideSecurity.remove();
	}
	
	/**
	 * for checking if a queue was made in testing
	 * @return PassengerQueue
	 * 			the queue that was instantiated by 
	 * 			the constructor call
	 */
	protected PassengerQueue getOutSideSecurity() {
		return outsideSecurity;
	}
}
