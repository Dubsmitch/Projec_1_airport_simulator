package edu.ncsu.csc216.transit.ui;

import java.util.ArrayList;
import java.util.Hashtable;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.ncsu.csc216.transit.simulation_utils.Reporter;
import edu.ncsu.csc216.transit.simulation_utils.Simulator;

/**
 * Animates simulations of passengers going through security checkpoints at an airport.
 * @author Jo Perry
 * @version 2.1
 */
public class SecurityCheckpointViewer extends JFrame implements ActionListener, ChangeListener { 
	
	// ID used for serialization
	private static final long serialVersionUID = 1L;
	
	// Size constants for the window and panels
	private final static int FRAME_WIDTH = 750;   // Application window width
	private final static int FRAME_HEIGHT = 600;  // Application window height
	private final static int GRAPH_WIDTH = 550;   // Simulation graph width 
	private final static int GRAPH_HEIGHT = 550;  // Simulation graph height
	private final static int SCREEN_OFFSET = 100; // Placement of application window on screen
	
	//	Strings for window, labels, slider, and combo
	private final static String TITLE = "Security Check Simulation"; 
	private final static String INPUT_PASSENGERS = "# Passengers: ";
	private final static String START = "Start";
	private final static String QUIT = "Quit";
	private final static String BLANK = "";
	private final static String PCT_TRUSTED = "% Trusted Travelers: ";
	private final static String PCT_FAST = "% Fast Track Travelers: ";
	private final static String PCT_ORDINARY = "% Ordinary Travelers: ";
	private final static String WAIT_TIME = "Average Wait Time: ";
	private final static String PROCESS_TIME = "Average Process Time: ";
	private final static String NUMBER_OF_CHECKPOINTS = "# Security Checkpoints: ";
	private final static String ANIMATION_SPEED  = "Animation Speed: ";
	private final static String PROGRESS = "Progress: ";
	private final static String GO_SLOW = "Slow";
	private final static String GO_FAST = "Fast";
	private final static String TITLE_INPUT = "Simulation Parameters";
	private final static String TITLE_RESULTS = "Simulation Results";
	private final static String[] NUM_CHECKPOINT_CHOICES = {"3", "4", "5", "6", "7", 
			"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"}; // Dropdown choices for number of checkpoints
	
	// Constants for animation
	private final static int CIRCLE_WIDTH = 12; // Radius of circle that represents a passenger in line
	private final static int SLOW = 500;        // Thread sleep for slow animation
	private final static int FAST = 0;          // Thread sleep for fast animation
	private final static Color CENTER_COLOR = Color.YELLOW; // Default color for center of every other circle in animation
	private final static Color CENTER_COLOR_ALTERNATE = Color.MAGENTA;  // Alternate circle center color (to avoid yellow-on-yellow)
	
	// Buttons 
	private JButton btnStart = new JButton(START); // Start a new simulation
	private JButton btnQuit = new JButton(QUIT);   // Quit execution	

	// Fixed labels that identify corresponding input fields/choices
	private final JLabel lblNumPassengers = new JLabel(INPUT_PASSENGERS);
	private final JLabel lblSpeed = new JLabel(ANIMATION_SPEED);
	private final JLabel lblSlow = new JLabel(GO_SLOW);
	private final JLabel lblFast = new JLabel(GO_FAST);	
	private final JLabel lblWaitTime = new JLabel(WAIT_TIME);
	private final JLabel lblProcessTime = new JLabel(PROCESS_TIME);
	private final JLabel lblProgress = new JLabel(PROGRESS);
	private final JLabel lblNumCheckpoints = new JLabel(NUMBER_OF_CHECKPOINTS);		
	private final JLabel lblTrusted = new JLabel(PCT_TRUSTED);
	private final JLabel lblFastPass = new JLabel(PCT_FAST);
	private final JLabel lblOrdPass = new JLabel(PCT_ORDINARY);

	// Simulation results
	private JLabel lblWaitCalc = new JLabel(BLANK);     // Average wait time goes here
	private JLabel lblProcessCalc = new JLabel(BLANK);  // Average process time toes here

	// Fields for user input and results
	private JTextField txtNumPassengers = new JTextField();
	private JComboBox<String> cmbCheckpoints = new JComboBox<String>(NUM_CHECKPOINT_CHOICES);
	private JSlider slideSleep = new JSlider(JSlider.HORIZONTAL, FAST, SLOW, (FAST + SLOW) / 2);
	private int sleepTime = 100;
	private JProgressBar progress = new JProgressBar();	
	private JTextField txtTrusted = new JTextField(); 
	private JTextField txtFast = new JTextField(); 
	private JTextField txtOrdinary = new JTextField(); 
	
	// Bookkeeping members
	private static Hashtable<Integer, JLabel> sleepTimeLabel = new Hashtable<Integer, JLabel>();	
	private ArrayList<Color>[] laneColors;    // Color for each circle in the animation (by line)
	
	// Panels
	private JPanel pnlControlButtons = new JPanel(new FlowLayout());  // Start, quit buttons
	private JPanel pnlSimulation = new JPanel();      // Simulation animation
	private JPanel pnlInputParameters = new JPanel(); // Input data here
	private JPanel pnlResults = new JPanel();         // Simulation results
	private JPanel pnlLeft = new JPanel();            // Groups input, results, control buttons

	// Back end parameters
	private int numberOfCheckpoints; // Number of security checkpoints in a simulation run
	private int numberOfPassengers;  // Number of passengers in a simulation run
	private Simulator simulator;     // Security simulator
	private Reporter results;        // Provides numeric results of the simulation run
	
	/** 
	 * Creates the user interface for the application.
	 */
	public SecurityCheckpointViewer() {
		// Initial work. Set up the buttons and the panels
		addListeners();
		setUpInputPanels();
		
		// Add the panels to the window. Set its size, location, title
		Container c = getContentPane();
		c.add(pnlSimulation, BorderLayout.CENTER);
		c.add(pnlLeft, BorderLayout.WEST);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setLocation(SCREEN_OFFSET, SCREEN_OFFSET);	
		setTitle(TITLE);

		// Finish setting up the frame. Exit the application when the user closes the window.
		pack();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Specifies actions to be performed when the user clicks a button or selects from a combo box.
	 * @param e the button that was clicked
	 */
	public void actionPerformed(ActionEvent e) {
		// Run the simulation when the user clicks the start button
		if (e.getSource() == btnStart){
			btnStart.setEnabled(false);

			// Clear off current information and get parameters for the new simulation run
			clearSimulationPanel();
			clearLabel(lblWaitCalc);
			clearLabel(lblProcessCalc);   

			// Set up the simulator, run it, and show the results
			try {
				// Get all the input
				numberOfPassengers = Integer.parseInt(txtNumPassengers.getText());
				int x = cmbCheckpoints.getSelectedIndex();
				numberOfCheckpoints = Integer.parseInt(NUM_CHECKPOINT_CHOICES[x]);
				int trustPct = Integer.parseInt(txtTrusted.getText());
				int fastPct = Integer.parseInt(txtFast.getText());
				int ordPct = Integer.parseInt(txtOrdinary.getText());
				
				// Create a simulator
				simulator = new Simulator (numberOfCheckpoints, numberOfPassengers, trustPct, fastPct, ordPct);  
				results = simulator.getReporter();
				
				// Run the simulation
				run();
				
				// Report the results
				lblWaitCalc.setText(String.format("%1$.2f minutes", results.averageWaitTime()));
				lblProcessCalc.setText(String.format("%1$.2f minutes", results.averageProcessTime()));
				
				// Enable the start button to run a successive simulation
				btnStart.setEnabled(true);
			} catch (NumberFormatException nfe) {
				// Exception occurs in the GUI from calls to Integer.parseInt()
				showErrorMessage("All data entered must be whole numbers.");
			} catch (IllegalArgumentException iae) {
				// Exception occurs in the simulator parameters
				showErrorMessage(iae.getMessage());
			}
		}
		// Quit program execution
		if (e.getSource() == btnQuit) {
			System.exit(0);
		}
	}
	   
	/**
	 * Determines whether the user changed the animation speed.
	 * @param e the ChangeEvent object generated when slide is interacted with
	 */
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().equals(slideSleep)) {
			sleepTime = SLOW - slideSleep.getValue();
		}
	}

//-----------------------Private Methods, Classes--------------------------------------------------
	/**
	 * Private method. Creates a dialog box for a simulation initialization errors.
	 * @param message string to show in the dialog box
	 */
	private void showErrorMessage(String message) {
		JOptionPane.showMessageDialog(null, message, "Simulation Initialization Error", JOptionPane.PLAIN_MESSAGE);
		btnStart.setEnabled(true);
		txtNumPassengers.setText("");
	}	
	
	/** 
	 * Private method. Adds listeners to the button, combo box, and slider bar
	 */
	private void addListeners(){
		btnStart.addActionListener(this);
		btnQuit.addActionListener(this);
		cmbCheckpoints.addActionListener(this);
		slideSleep.addChangeListener(this);
	}  
	
	/**
	 * Private method. Sets up the slider that controls speed of animation.
	 */
	private void setUpSlider() {
		sleepTimeLabel.put(Integer.valueOf(SLOW), lblFast);
		sleepTimeLabel.put(Integer.valueOf(FAST), lblSlow);
		slideSleep.setLabelTable(sleepTimeLabel);
		slideSleep.setPaintLabels(true);
	}
	
	/**
	 * Private method. Sets up the progress bar.
	 */
	private void setUpProgressBar() {
		progress = new JProgressBar( );
		progress.setPreferredSize( new Dimension( 100, 20 ) );
		progress.setBounds( 0, 0, 100, 20 );		
	}
	
	/**
	 * Private method. Right aligns a label and adds it to the panel.
	 * @param panel - panel doing the add
	 * @param label - label being right aligned
	 */
	private void addAndAlign(JPanel panel, JLabel label) {
		label.setHorizontalAlignment(JLabel.RIGHT);
		panel.add(label);
	}

	/**
	 * Private method. Creates the window geometry and puts widgets on the panels.
	 */
	private void setUpInputPanels() {
		// Set up the animation panel with color, preferred size, and start/quit buttons
		pnlSimulation.setBackground(Color.WHITE);
		pnlSimulation.setPreferredSize(new Dimension(GRAPH_WIDTH, GRAPH_HEIGHT));		
		
		// Add the buttons
		pnlControlButtons.add(btnStart);
		pnlControlButtons.add(btnQuit);
		
		// Set up input panel --  add widgets for number of passengers and checkpoints and animation speed
		TitledBorder inp = (TitledBorder) BorderFactory.createTitledBorder(TITLE_INPUT);
		pnlInputParameters.setBorder(inp);
		pnlInputParameters.setLayout(new GridLayout(6, 2));
		addAndAlign(pnlInputParameters, lblNumPassengers);
		pnlInputParameters.add(txtNumPassengers);
		addAndAlign(pnlInputParameters, lblNumCheckpoints);
		pnlInputParameters.add(cmbCheckpoints);
		addAndAlign(pnlInputParameters, lblTrusted);
		pnlInputParameters.add(txtTrusted);
		addAndAlign(pnlInputParameters, lblFastPass);
		pnlInputParameters.add(txtFast);
		addAndAlign(pnlInputParameters, lblOrdPass);
		pnlInputParameters.add(txtOrdinary);
		addAndAlign(pnlInputParameters, lblSpeed);
		setUpSlider();
		pnlInputParameters.add(slideSleep);		
		
		// Set up output panel -- add labels, progress bar for results
		TitledBorder res = (TitledBorder) BorderFactory.createTitledBorder(TITLE_RESULTS);
		pnlResults.setBorder(res);
		pnlResults.setLayout(new GridLayout(3, 2));
		addAndAlign(pnlResults, lblWaitTime);
		pnlResults.add(lblWaitCalc);
		addAndAlign(pnlResults, lblProcessTime);
		pnlResults.add(lblProcessCalc);
		addAndAlign(pnlResults, lblProgress);
		setUpProgressBar();
		pnlResults.add(progress);  

		// Put the buttons, input, results on the left side panel
		pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.PAGE_AXIS));		
		pnlLeft.add(pnlInputParameters);
		pnlLeft.add(pnlResults);
		pnlLeft.add(pnlControlButtons);		
	}
	
	/**
	 * Private method. Erases everything on the simulation animation panel.
	 */
	private void clearSimulationPanel() {
		Graphics brush = pnlSimulation.getGraphics();
		brush.setColor(Color.white);
		brush.fillRect(0, 0, pnlSimulation.getWidth(), pnlSimulation.getHeight());		
	}
	
	/**
	 * Private method. Sets the text for a label to blank.
	 * @param lbl the label being cleared
	 */
	private void clearLabel(JLabel lbl){
		lbl.setText(BLANK);
		Rectangle rectLbl = lbl.getBounds();
		rectLbl.x = 0;
		rectLbl.y = 0;
		lbl.paintImmediately(rectLbl);
	}
		
	/**
	 * Private method. Animates the simulation and shows the results.
	 */
	private void run()  {
		// Draw the security checkpoints and set up for animating their waiting lines
		drawSystem(pnlSimulation, numberOfCheckpoints);				
		laneColors = new ColorQueue[numberOfCheckpoints];
		for (int k = 0; k < numberOfCheckpoints; k++) {
			laneColors[k] = new ColorQueue();
		}
		// "Decorate" every other passenger. The decorated array keeps track of whether the 
		//    passenger at the end of each line is decorated
		boolean[] decorated = new boolean[numberOfCheckpoints]; 
		for (int k = 0; k < numberOfCheckpoints; k++) {
			decorated[k] = false;
		}
		
		// Prepare the progress bar
		progress.setMinimum(0);
		progress.setValue(0);
		progress.setMaximum(numberOfPassengers);

		// Step through the simulation
		while (simulator.moreSteps()) {
			simulator.step();
			int soFar = results.getNumCompleted();
			int index = simulator.getCurrentIndex();
			int length = laneColors[index].size();
			if (simulator.passengerClearedSecurity()) {
				laneColors[index].remove(0);
				decorated[index] = !decorated[index];
			}
			else {
				laneColors[index].add(simulator.getCurrentPassengerColor());
			}
			
			
			// Erase the last passenger in a security checkpoint line that changed on this step
			//   (needed only if the passenger leaves the security checkpoint line)
			plotPoint(pnlSimulation, (index + 1) * GRAPH_WIDTH / (numberOfCheckpoints + 1), 
					20 + 12 * (length - 1), Color.WHITE, false);
			// Redraw the line that changed, marking every other passenger with a dot so you
			//   can detect the motion
			int j = 0;
			boolean colorMarker = false;
			for (Color dotColor : laneColors[index]) {
				plotPoint(pnlSimulation, (index + 1) * GRAPH_WIDTH / (numberOfCheckpoints + 1), 
						20 + 12 * j++, dotColor, colorMarker ^ decorated[index]);
				colorMarker = !colorMarker;
			}
			// Update the progress bar
			progress.setValue(soFar);
			Rectangle progressRect = progress.getBounds();
			progressRect.x = 0;
			progressRect.y = 0;
			progress.paintImmediately( progressRect );

			// Slow down the animation
			try {
				Thread.sleep(sleepTime);
			}
			catch ( InterruptedException e) {
				//Do nothing
			}
		}          
	}			

	/** 
	 * Private method. Draws checkpoints on the animation panel.
	 * @param panel animation panel
	 * @param lanes number of checkpoints
	 */
	private void drawSystem(JPanel panel, int lanes) {
		Graphics g = panel.getGraphics();
		g.setColor(Color.BLACK);
		// Draw the checkpoints
		for (int k = 0; k < lanes; k++) {
			g.drawRect((k + 1) * GRAPH_WIDTH / (lanes + 1) - 1, 19, 
					CIRCLE_WIDTH + 2, CIRCLE_WIDTH + 2); 
		}
	}

	/**
	 * Private method. Plots a solid circle on a graph.
	 * @param panel Panel where the graph is drawn.
	 * @param x x-coordinate of the point
	 * @param y y-coordinate of the point
	 * @param c color of the point
	 * @param withHole determines whether there should be a whole in the circle
	 */
	private void plotPoint(JPanel panel, int x, int y, Color c, boolean withHole) {
		Graphics g = panel.getGraphics();
		g.setColor(c);
		g.fillOval(x, y, CIRCLE_WIDTH, CIRCLE_WIDTH);
		if (withHole) {
			if (c.equals(CENTER_COLOR))
				g.setColor(CENTER_COLOR_ALTERNATE);
			else
				g.setColor(CENTER_COLOR);
			g.fillOval(x + CIRCLE_WIDTH / 4 + 1, y + CIRCLE_WIDTH / 4 + 1, CIRCLE_WIDTH / 3, CIRCLE_WIDTH / 3);	
		}
	}

	/**
	 * Private inner class to set up equivalent of a C typedef for an ArrayList of Color objects.
	 */
	private static class ColorQueue extends ArrayList<Color> {
		private static final long serialVersionUID = 1L;	
	}
	
//-------------------End Private Methods, Classes--------------------------------------------------
	
	/**
	 * Main method. Instantiates the simulation.
	 * @param args not used
	 */
	public static void main(String[] args) {
		new SecurityCheckpointViewer();
	}
}
