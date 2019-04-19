package edu.ncsu.csc216.transit.airport.travelers;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;
import java.awt.Color;
/**
 * creates a FastTrackPassenger that extends passenger and knows its own color.
 * 
 * @author William
 *
 */
public class FastTrackPassenger extends Passenger {
	/** maximim expected processing time **/
	public static final int MAX_EXPECTED_PROCESS_TIME = 330;
	
	/** the color of the passenger **/
	public Color color;
	
	/**
	 * Creates a trusted traveler with a color of Green
	 * 
	 * @param waitTime
	 * 		The time a passenger waits
	 * @param arrivalTime
	 * 		the time that a passenger arrives into the airport
	 * @param reporter
	 * 		updates a log that each passenger has
	 */
	public FastTrackPassenger(int arrivalTime, int processTime, Reporter reporter) {
		super(arrivalTime, processTime, reporter);
		if (this.getProcessTime() < 175) {
			Color lightBlue = new Color(153, 153, 255);
			this.color=lightBlue;
		} else {
			Color blue = new Color(0, 0, 255);
			this.color=blue;
		}
		//change this based on process time!

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
		return ((SecurityArea) transitgroup).shortestFastTrackLine();
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
	
	



