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

	public MenuBarPanel(){
		super(new FlowLayout(FlowLayout.LEADING));

		menuBar = new JMenuBar();
		this.add(menuBar);

		gameMenu = new JMenu("Game");
		menuBar.add(gameMenu);
		
		JMenuItem startItem = new JMenuItem("Start");
		gameMenu.add(startItem);
		ActionListener startSim = new ActionListener() {
				public void actionPerformed(ActionEvent ev) { 
					System.exit(0);
				}
			};
		startItem.addActionListener(startSim);
		gameMenu.add(new JMenuItem("Stop"));
		gameMenu.add(new JMenuItem("Load"));
		JMenuItem exitItem = new JMenuItem("Exit");
		gameMenu.add(exitItem);
		exitItem.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent ev) { System.exit(0); }
		});

		simMenu = new JMenu("Simulation");
		menuBar.add(simMenu);
		simMenu.add(new JMenuItem("Airports"));
		simMenu.add(new JMenuItem("Aircrafts"));
		simMenu.add(new JMenuItem("Flights"));

		this.setOpaque(true);
	}
}