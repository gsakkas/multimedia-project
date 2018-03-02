package multimedia;

import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class MenuBarPanel extends JPanel {
	private JMenuBar menuBar;
	private JMenu gameMenu;
	private JMenu simMenu;

	// Simulation helping variables
	private boolean simStatus;

	public MenuBarPanel(){
		super(new FlowLayout(FlowLayout.LEADING));

		// Initializing helping variables
		simStatus = false;

		menuBar = new JMenuBar();
		this.add(menuBar);

		gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem startItem = new JMenuItem("Start");
		gameMenu.add(startItem);
		ActionListener startSim = new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					simStatus = true;
				}
			};
		startItem.addActionListener(startSim);
		JMenuItem stopItem = new JMenuItem("Stop");
		gameMenu.add(stopItem);
		ActionListener stopSim = new ActionListener() {
				public void actionPerformed(ActionEvent e) { 
					simStatus = false;
				}
			};
		gameMenu.add(new JMenuItem("Load"));
		JMenuItem exitItem = new JMenuItem("Exit");
		gameMenu.add(exitItem);
		exitItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					System.exit(0);
				}
		});

		simMenu = new JMenu("Simulation");
		menuBar.add(simMenu);
		simMenu.add(new JMenuItem("Airports"));
		simMenu.add(new JMenuItem("Aircrafts"));
		simMenu.add(new JMenuItem("Flights"));

		this.setOpaque(true);
	}

	public boolean getStatus() {
		return simStatus;
	}
}