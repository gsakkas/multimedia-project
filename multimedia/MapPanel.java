package multimedia;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

public class MapPanel extends JPanel {

	public MapPanel(int DimX, int DimY, int[][] Grid, ArrayList<Airports> airports) {
		super(new GridLayout(DimX, DimY));
		super.setSize(DimY * 16, DimX * 16);
		JPanel[][] PanelHolder = new JPanel[DimX][DimY];

		for (int i = 0; i < DimX; i++) {
			for (int j = 0; j < DimY; j++) {
				int temp = Grid[i][j];
				if (temp < 0) {
					System.out.println("Negative height in map!");
					System.exit(-1);
				}
				else if (temp > 3500)
					PanelHolder[i][j] = this.createSquareJPanel(new Color(145, 80, 20), 16);
				else if (temp > 1500)
					PanelHolder[i][j] = this.createSquareJPanel(new Color(205,133,63), 16);
				else if (temp > 700)
					PanelHolder[i][j] = this.createSquareJPanel(new Color(222,184,135), 16);
				else if (temp > 400)
					PanelHolder[i][j] = this.createSquareJPanel(new Color(34,139,34), 16);
				else if (temp > 200)
					PanelHolder[i][j] = this.createSquareJPanel(new Color(46,139,87), 16);
				else if (temp > 0)
					PanelHolder[i][j] = this.createSquareJPanel(new Color(60,179,113), 16);
				else if (temp == 0)
					PanelHolder[i][j] = this.createSquareJPanel(new Color(0,0,255), 16);
				this.add(PanelHolder[i][j]);
			}
		}

		for (Airports airport: airports){
			GridBagConstraints c = new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			c.anchor = GridBagConstraints.CENTER;
			PanelHolder[airport.getX()][airport.getY()].add(airport.getImg());
		}
		
		this.setOpaque(true);
	}

	// In this method, we create a square JPanel of a colour and set size
	// specified by the arguments.
	private JPanel createSquareJPanel(Color color, int size) {
		JPanel tempPanel = new JPanel(new GridBagLayout());
		tempPanel.setBackground(color);
		tempPanel.setMinimumSize(new Dimension(size, size));
		tempPanel.setMaximumSize(new Dimension(size, size));
		tempPanel.setPreferredSize(new Dimension(size, size));
		return tempPanel;
	}
}
