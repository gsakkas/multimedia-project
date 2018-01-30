package multimedia;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.util.ArrayList;

public class MainWindow extends JFrame {
	private JPanel mapPanel;
	private JPanel infoBarPanel;
	private JPanel textAreaPanel;

	public MainWindow(Map map, ArrayList<Airports> airports, ArrayList<Flights> flights){
		super("MediaLab Flight Simulation");
		MapPanel mapPanel = new MapPanel(map.getDimX(), map.getDimY(), map.getGrid(), airports);
		InfoBarPanel infoBarPanel = new InfoBarPanel();
		TextAreaPanel textAreaPanel = new TextAreaPanel();

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
	
}