package multimedia;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.util.ArrayList;

public class MainWindow extends JFrame{
	private JPanel mapPanel = new JPanel();
	private JPanel menuBarPanel = new JPanel();
	private JPanel infoPanel = new JPanel();

	public MainWindow(Map map, ArrayList<Airports> airports, ArrayList<Flights> flights){
		super("MediaLab Flight Simulation");
		super.setLocationRelativeTo(null);
		MapPanel mapPanel = new MapPanel(map.getDimX(), map.getDimY(), map.getGrid(), airports);

		JSplitPane mainSplitPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mapPanel, infoPanel);	
		JSplitPane splitPaneV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, menuBarPanel, mainSplitPanel);	
		
		super.setContentPane(splitPaneV);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.pack();
		super.setVisible(true);
	}
	
}