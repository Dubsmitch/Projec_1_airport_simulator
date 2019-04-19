package edu.ncsu.csc216.transit.simulation_utils;

import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * log that keeps track of events
 * 
 * @author William
 *
 */
public class Log implements Reporter{

	/**the number of steps completed**/
	public int numCompleted;
	
	/**the total wait time of all passengers**/
	public int totalWaitTime;
	
	/**the total process time of all passengers**/
	public int totalProcessTime;
	
	/**
	 * creates an instance of the log
	 */
	public Log() {
		this.numCompleted = 0;
		this.totalWaitTime = 0;
		this.totalProcessTime = 0;
		//add method here
	}
	/**
	 * gives the number of steps completed 
	 * @return int
	 * 		the number of the steps completed
	 */
	public int getNumCompleted() {
		return numCompleted;
	}
	/**
	 * logs a passenger's data
	 * @param passenger
	 * 		the passenger whom's data will be extracted
	 */
	public void logData(Passenger passenger) {
		int totalWait = this.totalWaitTime;
		int totalProcess = this.totalProcessTime;
		int numberComp = this.numCompleted;
		totalWait = totalWait + passenger.getWaitTime();
		
		totalProcess = totalProcess + passenger.getProcessTime();
		numberComp = numberComp + 1;
		this.numCompleted = numberComp;
		this.totalWaitTime = totalWait;
		this.totalProcessTime = totalProcess;
		

	
	}
	
	/**
	 * gives the average wait time
	 * 
	 * @return double
	 * 		the average wait time
	 */
	public double averageWaitTime() {
		if (this.totalWaitTime == 0) {
			return 0;
		} else {
			System.out.println(totalWaitTime);
			double AvgWait = this.totalWaitTime / this.numCompleted;
			return (AvgWait/60);
		}
	}
	
	/**
	 * gives the average process time of all passengers
	 * 
	 * @return double
	 * 		the average process time of all passengers
	 */
	public double averageProcessTime() {
		if (this.totalProcessTime == 0) {
			return 0;
		} else {
			double avgProcess = (this.totalProcessTime/this.numCompleted);
			return (avgProcess/60) + .01;
		}
	}
}
