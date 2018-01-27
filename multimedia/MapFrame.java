package multimedia;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;

public class MapFrame extends JFrame {

	public MapFrame(int DimX, int DimY, int[][] Grid) {
		super("Multimedia flight simulator");
		super.setSize(DimY * 16, DimX * 16);
		// super.setLocationRelativeTo(null);
		// super.setLayout();

		JPanel mainPanel = new JPanel(new GridLayout(DimX, DimY));
		for (int i = 0; i < DimX; i++) {
			for (int j = 0; j < DimY; j++) {
				int temp = Grid[i][j];
				System.out.println(temp);
				if (temp < 0) {
					System.out.println("Negative height in map!");
					System.exit(-1);
				}
				else if (temp > 3500)
					mainPanel.add(this.createSquareJPanel(new Color(145, 80, 20), 16));
				else if (temp > 1500)
					mainPanel.add(this.createSquareJPanel(new Color(205,133,63), 16));
				else if (temp > 700)
					mainPanel.add(this.createSquareJPanel(new Color(222,184,135), 16));
				else if (temp > 400)
					mainPanel.add(this.createSquareJPanel(new Color(34,139,34), 16));
				else if (temp > 200)
					mainPanel.add(this.createSquareJPanel(new Color(46,139,87), 16));
				else if (temp > 0)
					mainPanel.add(this.createSquareJPanel(new Color(60,179,113), 16));
				else if (temp == 0)
					mainPanel.add(this.createSquareJPanel(new Color(0,0,255), 16));
			}
		}
		mainPanel.setOpaque(true);
		JPanel totalGUI = new JPanel();
		totalGUI.add(mainPanel);
		totalGUI.setOpaque(true);
		super.setContentPane(totalGUI);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.pack();
		super.setVisible(true);
	}

	// In this method, we create a square JPanel of a colour and set size
	// specified by the arguments.
	private JPanel createSquareJPanel(Color color, int size) {
		JPanel tempPanel = new JPanel();
		tempPanel.setBackground(color);
		tempPanel.setMinimumSize(new Dimension(size, size));
		tempPanel.setMaximumSize(new Dimension(size, size));
		tempPanel.setPreferredSize(new Dimension(size, size));
		return tempPanel;
	}
}
