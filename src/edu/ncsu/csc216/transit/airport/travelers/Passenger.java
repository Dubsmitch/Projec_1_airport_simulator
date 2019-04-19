package edu.ncsu.csc216.transit.airport.travelers;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;
/**
 * an abstract method that provides functionality for passengers and
 * manages arrival time, wait time, process time, line index and if 
 * a passenger is waiting to be processed or not
 * 
 * @author William
 *
 */
public abstract class Passenger {
	
	/** sets the minimum process time to 20 seconds **/
	public static final int MIN_PROCESS_TIME = 20; 
	/** the arrival time of a passenger **/
	private int arrivalTime;
	/** the wait time of a passenger **/
	private int waitTime;
	/** the process time of a passenger **/
	private  int processTime;
	/** the spot in line of a passenger **/
	private int lineIndex;
	/** If a passenger is awaiting processing **/
	private boolean waitingProcessing;
	/** an instance of the log for each passenger **/
	private Reporter myLog;
	
	
	/** abstract method for choosing a line **/
	public abstract void getInLine (TransitGroup transitgroup);
	
	/** abstract method for finding a passenger's color **/
	public abstract Color getColor();
	
	/**
	 * creates a Passenger
	 * 
	 * @param waitTime
	 * 		sets the amount of time a passenger waits
	 * @param arrivalTime
	 * 		sets the arrival time of a passenger
	 * @param reporter
	 * 		creates a log for a passenger
	 */
	public Passenger(int arrivalTime, int processTime, Reporter log) {
		this.arrivalTime = arrivalTime; 
		this.processTime = processTime;
		myLog = (Reporter) log;
		
		this.waitingProcessing = false;
		this.lineIndex = -1;
	}
	/**
	 * provides the arrival time of a passenger
	 * @return int
	 * 			the time of arrival
	 */
	public int getArrivalTime() {
		return arrivalTime;
	}
	/**
	 * provides the wait time of a passenger
	 * 
	 * @return int
	 * 			the wait time of a passenger
	 */
	public int getWaitTime() {
		return waitTime;
	}
	/**
	 * sets the wait time of a passenger
	 * 
	 * @param waitTime
	 * 			the time that a passenger will wait
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	/**
	 * provides the process time of a passenger
	 * 
	 * @return int
	 * 		The time that a passenger will take to be processed
	 */
	public int getProcessTime() {
		return processTime;
	}
	/**
	 * provides the spot a passenger is in line
	 * 
	 * @return int
	 * 			the location in line a passenger is
	 */
	public int getLineIndex() {
		return lineIndex;
	}
	/**
	 * provides whether or not a passenger is in line
	 * 
	 * @return boolean
	 * 			whether or not a passenger is in line
	 */
	public boolean isWaitingInSecurityLine() {
		return waitingProcessing;
	}
	 /**
	  * sets waiting processing to false
	  * (no longer waiting to be processed)
	  */
	public void clearSecurity() {
		this.waitingProcessing = false;
		myLog.logData(this);
		
	}
	/**
	 * sets the index in line for a passenger in a 
	 * security line
	 * @param number
	 * 			the number in line of a passenger
	 */
	protected void setLineIndex (int number) {
		this.lineIndex = number;
		this.waitingProcessing = true;
	}
}
