package edu.ncsu.csc216.transit.simulation_utils;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.entrance.PreSecurity;
import edu.ncsu.csc216.transit.airport.entrance.Ticketing;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * The Simulator initializes and runs the 'simulation'
 * 
 * @author William
 *
 */
public class Simulator {
	
	/** the number of passengers to be created for the simulation **/
	private int numPassengers;
	/** instance of transit group for security **/
	private TransitGroup inSecurity;
	/** instance of transit group for ticketing **/
	private TransitGroup inTicketing;
	/** the log **/
	private Reporter log;
	/** creates a instance of the current passenger **/
	private Passenger currentPassenger;
	/** creates the instance of eventCalendar **/
	private EventCalendar myCalendar;
	/**
	 * creates an instance of the simulator
	 * 
	 * @param numOfCheckpoints
	 * 		the number of checkpoints to be created
	 * @param numOfPassengers
	 * 		the total number of passengers to be created
	 * @param percentTSA
	 * 		the percent of the total passengers to be TSA
	 * @param percentFastTrack
	 * 		the percent of the total passengers to be fastTrack
	 * @param percentRegular
	 * 		the percent of the total passengers to be regular passengers
	 */
	public Simulator(int numOfCheckpoints, int numOfPassengers, 
			int percentTSA, int percentFastTrack, int percentRegular) {
		this.checkParameters(numOfCheckpoints, numOfPassengers, percentTSA,
				percentFastTrack, percentRegular);
		this.setUp(percentTSA, percentFastTrack);

		log = new Log();
		inSecurity = new SecurityArea(numOfCheckpoints);
		inTicketing = new PreSecurity(numOfPassengers, log);
		currentPassenger = null;
		myCalendar = new EventCalendar(inTicketing, inSecurity);
		numPassengers = numOfPassengers;
		
	}
	/**
	 * checks the number of checkpoints, passengers and their percentages to
	 * ensure they fall within the correct parameters
	 * 
	 * @param numOfCheckpoints
	 * 		checks the total number of checkpoints
	 * @param numOfPassengers
	 * 		the total number of passengers to be created
	 * @param percentTSA
	 * 		the percent of the total passengers to be TSA
	 * @param percentFastTrack
	 * 		the percent of the total passengers to be fastTrack
	 * @param percentRegular
	 * 		the percent of the total passengers to be regular passengers
	 * @return 
	 */
	private void checkParameters (int numOfCheckpoints, int numOfPassengers, 
			int percentTSA, int percentFastTrack, int percentRegular) {
		if (numOfPassengers < 1) {
			throw new IllegalArgumentException("There must be at least one passenger.");
		}
		if (numOfCheckpoints < 3 || numOfCheckpoints > 17) {
			throw new IllegalArgumentException("Number of checkpoints must be at least 3 and at most 17.");
		}
		if ((percentTSA + percentFastTrack + percentRegular) != 100) {
			throw new IllegalArgumentException("Error Dialog for bad percent distribution");

		}
	}
	/**
	 * sets up Ticketing class
	 * 
	 * @param pctTrusted
	 * 		percent of trusted Traveler
	 * @param pctFast
	 * 		percent of fastTrack
	 */
	private void setUp (int pctTrusted, int pctFast) {
		//set the distribution of passengers
		Ticketing.setDistribution(pctTrusted, pctFast);
	}
	/**
	 * gives the reporter object
	 * 
	 * @return Reporter
	 * 		the reporter
	 */
	public Reporter getReporter() {
		return this.log;
	}
	
	/**
	 * steps
	 */
	public void step() {
		currentPassenger = this.myCalendar.nextToAct();
		if (currentPassenger.isWaitingInSecurityLine()) {
			currentPassenger = inSecurity.removeNext();
			currentPassenger.clearSecurity();

		} else {
			currentPassenger = inTicketing.removeNext();
			currentPassenger.getInLine(inSecurity);
			
		}
		
	}
	
	/**
	 * true if there are more steps to be completed
	 * false if all steps have been completed
	 * 
	 * @return boolean
	 * 		whether or not there are more steps
	 */
	public boolean moreSteps() {
		if (this.numPassengers > log.getNumCompleted()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * gives the index of the current Passenger
	 * 
	 * @return int
	 * 		the index of the current passenger
	 */
	public int getCurrentIndex() {
		return currentPassenger.getLineIndex();
	}
	
	public int getNumberOfPassengers() {
		return numPassengers;
	}
	/**
	 * gives the color of the current passenger
	 * 
	 * @return Color
	 * 		the color of the current Passenger
	 */
	public Color getCurrentPassengerColor() {
		return currentPassenger.getColor();
	}
	/**
	 * is true if the current passenger cleared security
	 * is false otherwise
	 * 
	 * @return boolean
	 * 		whether or not a passenger has cleared security
	 */
	public boolean passengerClearedSecurity() {
		if (currentPassenger.isWaitingInSecurityLine()) {
			return false;
		} else {
			return true;
		}
	}
}
