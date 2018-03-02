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

	public MainWindow(Map map, ArrayList<Airports> airports, ArrayList<Flights> flights){
		super("MediaLab Flight Simulation");
		mapPanel = new MapPanel(map.getDimX(), map.getDimY(), map.getGrid(), airports);
		infoBarPanel = new InfoBarPanel();
		textAreaPanel = new TextAreaPanel();
		menuBarPanel = new MenuBarPanel();

		JSplitPane mainAreaSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, textAreaPanel);
		mainAreaSplitPane.setDividerSize(0);
		JSplitPane topBarSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, menuBarPanel, infoBarPanel);
		topBarSplitPane.setDividerSize(0);
		JSplitPane barMainAreaSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topBarSplitPane, mainAreaSplitPane);
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

	public boolean simRunning() {
		return menuBarPanel.getStatus();
	}

	public void setTime(int t) {
		infoBarPanel.setTime(t);
	}

	public void setAircrafts(int airc) {
		infoBarPanel.setAircrafts(airc);
	}

	public void setCrashes(int c) {
		infoBarPanel.setCrashes(c);
	}

	public void setLandings(int l) {
		infoBarPanel.setLandings(l);
	}
}