package multimedia;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.util.ArrayList;

public class MainWindow extends JFrame {
	private MapPanel mapPanel;
	private InfoBarPanel infoBarPanel;
	private TextAreaPanel textAreaPanel;
	private MenuBarPanel menuBarPanel;
	private JSplitPane mainAreaSplitPane;
	private JSplitPane topBarSplitPane;
	private JSplitPane barMainAreaSplitPane;

	public MainWindow(Map map, ArrayList<Airports> airports, ArrayList<Flights> flights){
		super("MediaLab Flight Simulation");
		mapPanel = new MapPanel(map.getDimX(), map.getDimY(), map.getGrid(), airports);
		infoBarPanel = new InfoBarPanel();
		textAreaPanel = new TextAreaPanel();
		menuBarPanel = new MenuBarPanel(airports, flights);

		mainAreaSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, textAreaPanel);
		mainAreaSplitPane.setDividerSize(0);
		
		topBarSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, menuBarPanel, infoBarPanel);
		topBarSplitPane.setDividerSize(0);
		
		barMainAreaSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topBarSplitPane, mainAreaSplitPane);
		barMainAreaSplitPane.setDividerSize(0);
		
		super.setContentPane(barMainAreaSplitPane);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setResizable(false);
		super.pack();
		super.setLocationRelativeTo(null);
		super.setVisible(true);
	}
	
	public MapPanel getMap() {
		return mapPanel;
	}

	public void setMap(Map map, ArrayList<Airports> airports) {
		mapPanel = new MapPanel(map.getDimX(), map.getDimY(), map.getGrid(), airports);

		mainAreaSplitPane.setLeftComponent(mapPanel);
		mainAreaSplitPane.updateUI();
	}

	public void setSimStatus(boolean b) {
		if (b) menuBarPanel.enableSim();
		else menuBarPanel.disableSim();
	}

	public boolean simRunning() {
		return menuBarPanel.getStatus();
	}

	public boolean getLoadedStatus() {
		return menuBarPanel.getLoadedStatus();
	}

	public void setTime(int t) {
		infoBarPanel.setTime(t);
	}

	public void addAircraft(int airc) {
		infoBarPanel.addAircraft(airc);
	}

	public void removeAircraft(int airc) {
		infoBarPanel.removeAircraft(airc);
	}

	public void setCrashes(int c) {
		infoBarPanel.setCrashes(c);
	}

	public void setLandings(int l) {
		infoBarPanel.setLandings(l);
	}

	public void resetVars() {
		infoBarPanel.resetVars();
	}

	public void unloadFile() {
		menuBarPanel.unloadFile();
	}

	public String getWorldFile() {
		return menuBarPanel.getWorldFile();
	}

	public String getAirportsFile() {
		return menuBarPanel.getAirportsFile();
	}

	public String getFlightsFile() {
		return menuBarPanel.getFlightsFile();
	}

	public void addText(String text) {
		textAreaPanel.addText(text);
	}
}