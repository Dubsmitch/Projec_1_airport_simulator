package edu.ncsu.csc216.transit.airport.travelers;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

import java.awt.Color;
/**
 * creates a trustedTraveler that extends passenger and knows its own color.
 * 
 * @author William
 *
 */
public class TrustedTraveler extends Passenger {
	/** maximim expected processing time **/
	public static final int MAX_EXPECTED_PROCESS_TIME = 180;
	
	/** the color of the passenger **/
	public Color color;
	
	/**
	 * Creates a trusted traveler with a color of Blue
	 * 
	 * @param waitTime
	 * 		The time a passenger waits
	 * @param arrivalTime
	 * 		the time that a passenger arrives into the airport
	 * @param reporter
	 * 		updates a log that each passenger has
	 */
	public TrustedTraveler(int arrivalTime, int processTime, Reporter reporter) {
		super(arrivalTime, processTime, reporter);
		if (this.getProcessTime() < 100) {
			Color lightGreen = new Color(153, 255, 153);
			this.color = lightGreen;
		} else {
			Color green = new Color(0, 255, 0);
			this.color = green;
		}
	}
	
	/**
	 * finds to optimal line for a passenger to enter
	 * 
	 * @param transitgroup
	 * 			Transitgroup
	 * @return int
	 * 			int is the security line that the passenger should choose
	 */
	private int pickLine (TransitGroup transitgroup) {
		return ((SecurityArea) transitgroup).shortestTSAPreLine();
		
	}
	/**
	 * puts the passenger in a given security line
	 */
	@Override
	public void getInLine(TransitGroup transitgroup) {

		int idx = this.pickLine(transitgroup);
		((SecurityArea) transitgroup).addToLine(idx, this);
		this.setLineIndex(idx);
	}
	/**
	 * sets the color of a passenger based on their kind and process time
	 * @return Color
	 * 			returns the color of the passengers based on its
	 * 			kind and process time.
	 */
	@Override
	public Color getColor() {
		return this.color;
	}

}