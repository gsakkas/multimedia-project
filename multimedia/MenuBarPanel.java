package multimedia;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import java.io.File;

public class MenuBarPanel extends JPanel {
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenu simMenu;

	// Simulation helping variables
	private boolean simStatus;
	private MenuBarPanel self;
	private String world_file;
	private String airports_file;
	private String flights_file;
	private boolean loaded;
						

	public MenuBarPanel(){
		super(new FlowLayout(FlowLayout.LEADING));

		// Initializing helping variables
		simStatus = false;
		self = this;
		loaded = false;

		menuBar = new JMenuBar();
		this.add(menuBar);

		gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem startItem = new JMenuItem("Start");
		gameMenu.add(startItem);
		ActionListener startSim = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					enableSim();
				}
			};
		startItem.addActionListener(startSim);
		
		JMenuItem stopItem = new JMenuItem("Stop");
		gameMenu.add(stopItem);
		ActionListener stopSim = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					disableSim();
				}
			};
		stopItem.addActionListener(stopSim);
		
		JMenuItem loadItem = new JMenuItem("Load");
		gameMenu.add(loadItem);
		ActionListener loadFile = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int mapid = 0;
					boolean successfulFiles = true;
					try {
						mapid = Integer.parseInt(
							JOptionPane.showInputDialog(self, "Type in the MAPID:", "Load File"));
						world_file = "examples/world_" + mapid + ".txt";
						File f = new File(world_file);
						if(!f.exists() || f.isDirectory()) { 
							JOptionPane.showMessageDialog(self, "\"world_" + mapid + ".txt\" was not found!");
							successfulFiles = false;
						}
						airports_file = "examples/airports_" + mapid + ".txt";
						f = new File(airports_file);
						if(!f.exists() || f.isDirectory()) { 
							JOptionPane.showMessageDialog(self, "\"airports_" + mapid + ".txt\" was not found!");
							successfulFiles = false;
						}
						flights_file = "examples/flights_" + mapid + ".txt";
						f = new File(flights_file);
						if(!f.exists() || f.isDirectory()) { 
							JOptionPane.showMessageDialog(self, "\"flights_" + mapid + ".txt\" was not found!");
							successfulFiles = false;
						}
						if (successfulFiles) loadedFile();
					}
					catch (NumberFormatException ex) {
						System.out.println("MAPID was not an Integer!");
					}
				}
			};
		loadItem.addActionListener(loadFile);

		JMenuItem exitItem = new JMenuItem("Exit");
		gameMenu.add(exitItem);
		ActionListener exitProg = new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
			};
		exitItem.addActionListener(exitProg);

		simMenu = new JMenu("Simulation");
		menuBar.add(simMenu);
		simMenu.add(new JMenuItem("Airports"));
		simMenu.add(new JMenuItem("Aircrafts"));
		simMenu.add(new JMenuItem("Flights"));

		this.setOpaque(true);
	}

	public void enableSim() {
		simStatus = true;
	}

	public void disableSim() {
		simStatus = false;
	}

	public boolean getStatus() {
		return simStatus;
	}

	public void loadedFile() {
		loaded = true;
	}

	public void unloadFile() {
		loaded = false;
	}

	public boolean getLoadedStatus() {
		return loaded;
	}

	public String getWorldFile() {
		return world_file;
	}

	public String getAirportsFile() {
		return airports_file;
	}

	public String getFlightsFile() {
		return flights_file;
	}
}