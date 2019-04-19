package edu.ncsu.csc216.transit.airport.entrance;

import java.util.Random;

import edu.ncsu.csc216.transit.airport.travelers.FastTrackPassenger;
import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.airport.travelers.TrustedTraveler;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * A simple factory class to generate passengers to go through airport security.
 * @author Jo Perry
 */
public class Ticketing {

	/** Absolute time for passengers created for the simulation.  The time starts at zero
	 * and increases by up to MAX_PASSENGER_GENERATION_DELAY for each passenger created. */
	private static int time = 0; 
	/** Random object with seed that allows for testing of simulation */
	private static Random randomNumber = new Random(10); 
	/** Maximum delay between creation of passengers */
	private static final double MAX_GENERATION_DELAY = 15; 
	/** Percentage of time a  Fast Track passenger should be created */
	private static double pctExpedite = .50;  // initialize with default value
	/** Percentage of time a trusted traveler/TSA PreCheck passenger should be created */
	private static double pctTrust = .05;  // initialize with default value

	/**
	 * Set the proportions of fast track, trusted traveler, and (by inference) ordinary passengers
	 * that should be generated. Proportion of ordinary passengers is 1 - (pctFast + pctTrusted).
	 * @param pctTrusted - proportion of passengers that are TrustedTravelers
	 * @param pctFast - proportion of passengers that are FastTrackPassengers
	 */
	public static void setDistribution(int pctTrusted, int pctFast) {
		pctExpedite = pctFast * .01;
		pctTrust = pctTrusted * .01;
	}
	
	/**
	 * Generate a new passenger as described in the class comments. 
	 * @param log - where the passenger will log his/her data 
	 * @return the passenger created
	 */
	public static Passenger generatePassenger(Reporter log) {
		// Update the overall time with up to the floor of MAX_PASSENGER_GENERATION_DELAY seconds.
		// The value is cast to an int, which is the floor of the original double.
		time += (int)(1 + randomNumber.nextDouble() * MAX_GENERATION_DELAY);
		
		// Random number x determines which type of passenger will be created. The generated number
		// is between 0 and 1.0.  By splitting across the range of numbers generated, you can simulate
		// creation of different passengers of appropriate types.
		double x = randomNumber.nextDouble();
		if (x < pctExpedite) {  // Create a Fast Track passenger 
			int ftMin = FastTrackPassenger.MIN_PROCESS_TIME;
			int ftMax = FastTrackPassenger.MAX_EXPECTED_PROCESS_TIME;
			// If the generated number is less than pctExpedite, create a passenger with expedited security
			// with a process time between FastTrack.MIN_PROCESS_TIME and FastTrack.MAX_PROCESS_TIME.
			return new FastTrackPassenger(time, // FastTrackPassenger.MIN_PROCESS_TIME + 
					(int) (randomNumber.nextDouble() * (ftMax - ftMin)) + ftMin,
					log); 
		}
		else if (x < pctExpedite + pctTrust) {  // Create a Trusted Traveler 
			int tsaMax = TrustedTraveler.MAX_EXPECTED_PROCESS_TIME;
			int tsaMin = TrustedTraveler.MIN_PROCESS_TIME;
			// Else if the generated number is less than pctExpedite + pcTrust, create a trusted
			// traveler with a process time between TrustedTraveler.MIN_PROCESS TIME and TrustedTraveler.MAX_PROCESS_TIME.
			return new TrustedTraveler(time, // TrustedTraveler.MIN_PROCESS_TIME + 
					(int) (randomNumber.nextDouble() * (tsaMax - tsaMin)) + tsaMin,
					log);
		}
		else {   // Create an ordinary passenger
			int ordMax = OrdinaryPassenger.MAX_EXPECTED_PROCESS_TIME;
			int ordMin = OrdinaryPassenger.MIN_PROCESS_TIME;
			// Otherwise, create an ordinary passenger with a process time between OrdinaryPassenger.MIN_PROCESS TIME 
			//  and OrdinaryPassenger.MAX_PROCESS_TIME
			return new OrdinaryPassenger(time, // OrdinaryPassenger.MIN_PROCESS_TIME + 
					(int) (randomNumber.nextDouble() * (ordMax - ordMin)) + ordMin,
					log);
		}
	}

	/**
	 * Resets the factory.  You can use this for testing ONLY! Do not use it in your simulator.  
	 */
	public static void resetFactory() {
		time = 0;
		randomNumber = new Random(10);
	}

}
