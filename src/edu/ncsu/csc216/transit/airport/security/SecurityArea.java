package edu.ncsu.csc216.transit.airport.security;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * 
 * Represents the collection of security
 * checkpoints and their waiting lines.
 * @author William Mitchell
 *
 */
public class SecurityArea implements TransitGroup {

	/**maximum number of checkpoints**/
	public static final int MAX_CHECKPOINTS = 17;
	/**minimum number of checkpoints**/
	public static final int MIN_CHECKPOINTS = 3;
	/**Error message from incorrect number of checkpoints**/
	public static final String ERROR_CHECKPOINTS = 
			"Number of checkpoints must be at least 3 and at most 17.";
	/**Error for index out of range**/
	public static final String ERROR_INDEX = 
			"Index out of range for this security area";
	/**the largest index for lines reserved for Fast Track passengers **/
	private int largestFastIndex;
	/**the index of the line reserved for TSA PreCheck/Trusted Travelers**/
	private int tsaPreIndex;
	/**array of checkpoints**/
	private CheckPoint check [];
	
	/**
	 * creates a security checkpoints
	 * 
	 * @param numCheckpoints
	 * 		the number of checkpoints
	 * 
	 * @throws IllegalArgumentException
	 * 		IAE is thrown if the number of checkpoints
	 * 		is outside of acceptable range
	 */
	public SecurityArea (int numCheckpoints) {
		//IllegalArgumentExceptions
		if (!numGatesOK(numCheckpoints)) {
			throw new IllegalArgumentException ("Number of checkpoints"
					+ " must be between 3 and 17.");
		}
		//if it passes, create the checkpoint array
		check = new CheckPoint [numCheckpoints];
		for (int i = 0; i < numCheckpoints; i++) {
			check [i] = new CheckPoint();
		}
		
		//Finding the largest index for the fastTrack
		int findLargestIndex = numCheckpoints % 3;
		if (findLargestIndex > 0) {
			findLargestIndex = numCheckpoints-findLargestIndex;
			findLargestIndex = (findLargestIndex / 3) + 1;
		} else {
			findLargestIndex = numCheckpoints / 3;
		}
		
		//set the indexs for the TSA preCheck and the FastTrack
		this.tsaPreIndex = numCheckpoints - 1;
		this.largestFastIndex = findLargestIndex - 1;
		
		
	}
	
	/**
	 * there is not information about what this does
	 * 
	 * @param numberOK
	 * 		number of gates
	 * @return boolean
	 * 		there is not information about what this does
	 */
	private boolean numGatesOK(int numberOK) {
		//IllegalArgumentExceptions
		if (numberOK > 17) {
			return false;
		}
		if (numberOK < 3) {
			return false;
		}
		return true;
	}
	 /**
	  * adds a passenger to a security line
	  * 
	  * @param index
	  * 	index of which checkpoint to add the passenger in the queue
	  * @param passenger
	  * 	The passenger to be added
	  */
	public void addToLine(int index, Passenger passenger) {
		check[index].addToLine(passenger);
		
//		Color green = new Color(0, 255, 0);
//		Color lightGreen = new Color(153, 255, 153);
//		Color red = new Color(255, 0, 0);
//		Color lightRed = new Color(255, 153, 153);
//		Color blue = new Color(0, 0, 255);
//		Color lightBlue = new Color(153, 153, 255);
//		if (passenger.getColor().equals(lightGreen) || passenger.getColor().equals(green)) {
//			
//		}
	}
	/**
	 * gives the shortest line that an ordinary
	 * passenger can access
	 * 
	 * @return int 
	 * 		returns the index of the shortest line
	 * 		for an ordinary passenger
	 */
	public int shortestRegularLine() {
		int lengthOfLine = Integer.MAX_VALUE;
		int theShortestLineIndex = -1;
		for (int i = (largestFastIndex + 1); i < (check.length - 1); i++) {
			if (check[i].size() < lengthOfLine) {
				lengthOfLine = check[i].size();
				theShortestLineIndex = i;
				
			}
		}
				
		return theShortestLineIndex;
	}
	
	/**
	 * gives the shortest line that TSA passengers 
	 * have access to
	 * 
	 * @return int
	 * 		the index of the shortest line that TSA passengers
	 * 		can access
	 */
	public int shortestTSAPreLine() {
		int lengthOfTSALine = (check[tsaPreIndex].size());
		if (check[tsaPreIndex].size() <= 1) {
			return tsaPreIndex;
		}
		
		//find the length of the shortest nonTSALine
		int lengthOfLine = Integer.MAX_VALUE;
		int theShortestLineIndex = tsaPreIndex;
		for (int i = (largestFastIndex + 1); i < (check.length - 1); i++) {
			if ((check[i].size() * 2) < lengthOfTSALine) {
				lengthOfLine = check[i].size();
					if (lengthOfLine > check[i].size()) {
						lengthOfLine = check[i].size();
						theShortestLineIndex = i;
					}
				theShortestLineIndex = i;
			}
		}

		
		return theShortestLineIndex;
	}
	
	
	/**
	 * gives the shortest line that FastTrack passengers 
	 * have access to
	 * 
	 * @return int
	 * 		the index of the shortest line that FastTrack passengers
	 * 		can access
	 */
	public int shortestFastTrackLine() {
		int lengthOfLine = Integer.MAX_VALUE;
		int theShortestLineIndex = -1;
		for (int i = 0; i < (check.length - 1); i++) {
			
			if (check[i].size() < lengthOfLine) {
				lengthOfLine = check[i].size();
				theShortestLineIndex = i;
			}

		}
		
		return theShortestLineIndex;
	}
	
	/**
	 * gives size of the line of the checkpoint with
	 * the given index
	 * 
	 * @param index
	 * 		the index of the checkpoint
	 * @return int
	 * 		the length of the security line
	 */
	public int lengthOfLine(int index) {
		return check[index].size();
	}

	 /**
	  * gives the depart time of the next passenger
	  * 
	  * @return int
	  * 	the departure time
	  */
	public int departTimeNext() {
		int nextTime = Integer.MAX_VALUE;
		
		for (int i = 0; i < check.length; i++) {
			if (nextTime > check[i].departTimeNext()) {
				nextTime = check[i].departTimeNext();
			}
		}

		return nextTime;
	}
	/**
	 * gives the passenger that is next to be processed
	 * 
	 * @return Passenger
	 * 		the passenger that is next to be processed
	 */
	public Passenger nextToGo() {
		
		int nextTime = Integer.MAX_VALUE;
		int indexOfCheck = -1;
		
		for (int i = 0; i < check.length; i++) {
			if (nextTime > check[i].departTimeNext()) {
				nextTime = check[i].departTimeNext();
				indexOfCheck = i;
			}
		}
		return check[indexOfCheck].nextToGo();
	}
	
	/**
	 * gives the next passenger to be processed and
	 * removed from the line
	 * 	  
	 * @return Passenger
	 * 	the passenger to be processed and removed
	 * 	from the line
	 */
	public Passenger removeNext() {
		int nextTime = Integer.MAX_VALUE;
		int indexOfCheck = -1;
		
		for (int i = 0; i < check.length; i++) {
			if (nextTime > check[i].departTimeNext()) {
				nextTime = check[i].departTimeNext();
				indexOfCheck = i;
			}
			
		}
		return check[indexOfCheck].removeFromLine();
	}
	
	/**
	 * returns the checkpoint in question, used
	 * for testing (won't be called in actual program)
	 * 
	 * @param idx
	 * 			index of the checkpoint in the checkpoint
	 * 			array
	 * @return CheckPoint
	 * 			the checkpoint of the index from the array
	 */
	protected CheckPoint getCheckPoint(int idx) {
		return this.check[idx];
	}
	
//	/**
//	 * Gives the shortest line in the range of values
//	 * @param range1
//	 * 		the lower bound of the index of the checkpoint
//	 * 		between which the shortest line needs to be
//	 * 		found
//	 * @param range2
//	 * 		the upper bound of the index of the checkpoint
//	 * 		between which the shortest line needs to be
//	 * 		found
//	 * @return int
//	 * 		the index of the shortest line within the range
//	 * 		provided
//	 */
	//private int shortestLineInRange(int range1, int range2) {
	//	
	//	return 0;
	//}
//	/**
//	 * finds the index of the checkpoint from which
//	 * to take the next passenger
//	 * 
//	 * @return
//	 * 		returns the index of the checkpoint
//	 * 		from which to clear a passenger from next
//	 */
//	private int lineWithNextToClear() {
//		return 0;
//	}
	
}
