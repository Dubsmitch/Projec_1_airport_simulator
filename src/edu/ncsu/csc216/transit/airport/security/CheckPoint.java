package edu.ncsu.csc216.transit.airport.security;

import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.airport.travelers.PassengerQueue;

/**
 * represents a security checkpoint line and its line, 
 * where the front passenger is undergoing the actual security process.
 * It delegates strict queue activities to its instance variable line.
 * 
 * @author William
 *
 */
public class CheckPoint {
	
	/**the time a checkpoint is available**/
	private int timeWhenAvailable;
	/**a queue of passengers **/
	private PassengerQueue line; 
	/**
	 * creates a checkpoint
	 */
	public CheckPoint() {
		line = new PassengerQueue();
		//setting the timeWhenAvailiable to 0 because line is empty when created
		this.timeWhenAvailable = 0;
	}
	
	/**
	 * size of the line
	 * 
	 * @param int
	 * 		size of the line
	 */
	public int size() {
		return line.size();
	}
	
	/**
	 * remove from the line 
	 * @return Passenger
	 * 		passenger that is removed from the line
	 */
	public Passenger removeFromLine() {
		return line.remove();
	}
	
	/**
	 * whether or not a checkpoint has a passenger or not
	 * 
	 * @return boolean
	 * 		if there is a passenger next in line or not
	 */
	public boolean hasNext() {
		if (line.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
	
	/**
	 * time of the next departure
	 * 
	 * @return int
	 * 		time a person is going to depart
	 */
	public int departTimeNext() {
		if (line.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return this.nextToGo().getProcessTime() + this.nextToGo().getArrivalTime();
		}
	}
	
	/**
	 * selects the next passenger to go
	 * 
	 * @return Passenger
	 * 		the next passenger to go
	 */
	public Passenger nextToGo() {
		return line.front();
	}
	
	/**
	 * adds a passenger to a line
	 * 
	 * @param Passenger
	 * 		The passenger to be added to a line
	 */
	public void addToLine(Passenger passenger) {
		//line.add(passenger);
		//passenger.setWaitTime(this.getTimeWhenAvailable()-passenger.getArrivalTime());
		if (line.size() == 0 || this.timeWhenAvailable < passenger.getArrivalTime()) {
			line.add(passenger);
			passenger.setWaitTime(0);
			this.timeWhenAvailable = passenger.getArrivalTime() + passenger.getProcessTime();
		} else {
			//passenger waiting time is: is the previous time when avilable - minus arrival time
			//theTime when available is: when the line is available (the last passenger will clear security)
			// it starts at 0 and the arrival time  + the first passenger's process time
			// the next passenger's wait time is the time when available (line will be empty) minus their arrival time of the next passenger
			// (because time will have elapsed)
			
			//the time when available then becomes the old wait time - arrival time of the next passenger
			// + the processTime of that passenger
			//
			//sets the wait time of a passenger base on the time when the line will be available
			//and the arrival time of the passenger
			//adds a passenger
			line.add(passenger);
			//calculates new time when available by
			//adding process time to the time when available and the process time of the passenger
			passenger.setWaitTime(this.timeWhenAvailable - passenger.getArrivalTime());
			this.timeWhenAvailable = this.getTimeWhenAvailable() + passenger.getProcessTime();
		}
		//why is this here?//
		passenger.isWaitingInSecurityLine();
	}
	/**
	 * getter for timewhenavailabe
	 * @return int
	 * 		time when available
	 */
	public int getTimeWhenAvailable() {
		return timeWhenAvailable;
	}
	
}
