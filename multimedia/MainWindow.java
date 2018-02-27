package multimedia;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.util.ArrayList;

public class MainWindow extends JFrame {
	private MapPanel mapPanel;
	private InfoBarPanel infoBarPanel;
	private TextAreaPanel textAreaPanel;

	public MainWindow(Map map, ArrayList<Airports> airports, ArrayList<Flights> flights){
		super("MediaLab Flight Simulation");
		mapPanel = new MapPanel(map.getDimX(), map.getDimY(), map.getGrid(), airports);
		infoBarPanel = new InfoBarPanel();
		textAreaPanel = new TextAreaPanel();

		JSplitPane mainAreaSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, textAreaPanel);
		mainAreaSplitPane.setDividerSize(0);
		JSplitPane barMainAreaSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, infoBarPanel, mainAreaSplitPane);
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
}