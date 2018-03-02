package multimedia;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.lang.Thread;

public class MapPanel extends JPanel {
	private JPanel PanelHolder[][];

	public MapPanel(int DimX, int DimY, int[][] Grid, ArrayList<Airports> airports) {
		super(new GridLayout(DimX, DimY));
		super.setSize(DimY * 16, DimX * 16);
		PanelHolder = new JPanel[DimX][DimY];

		for (int i = 0; i < DimX; i++) {
			for (int j = 0; j < DimY; j++) {
				int temp = Grid[i][j];
				if (temp < 0) {
					System.out.println("Negative height in map!");
					System.exit(-1);
				}
				else if (temp > 3500)
					PanelHolder[i][j] = createSquareJPanel(new Color(145, 80, 20), 16);
				else if (temp > 1500)
					PanelHolder[i][j] = createSquareJPanel(new Color(205,133,63), 16);
				else if (temp > 700)
					PanelHolder[i][j] = createSquareJPanel(new Color(222,184,135), 16);
				else if (temp > 400)
					PanelHolder[i][j] = createSquareJPanel(new Color(34,139,34), 16);
				else if (temp > 200)
					PanelHolder[i][j] = createSquareJPanel(new Color(46,139,87), 16);
				else if (temp > 0)
					PanelHolder[i][j] = createSquareJPanel(new Color(60,179,113), 16);
				else if (temp == 0)
					PanelHolder[i][j] = createSquareJPanel(new Color(0,0,255), 16);
				this.add(PanelHolder[i][j]);
			}
		}

		for (Airports airport: airports){
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.CENTER;
			PanelHolder[airport.getX()][airport.getY()].add(airport.getImg(), c);
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

	public void moveToNextPanel(Flights fl) {
		int[] node = null;
		JLabel img1 = fl.getImage();
		JLabel img2 = new JLabel(new ImageIcon("./SimulationIcons/small_w.png"));
		JPanel previous = null;
		boolean flag = !fl.checkPath();
		if (img1 != null) {
			node = fl.getPreviousPanel();
			previous = PanelHolder[node[0]][node[1]];
			previous.remove(img1);
		}
		if (flag) {
			node = fl.removeFromPath();
			PanelHolder[node[0]][node[1]].add(img2);
			fl.setPreviousPanel(node);
			fl.setImage(img2);
		}
		else fl.finishSim();
	}

	public void clearImg(Flights fl) {
		if (fl.getImage() != null) {
			int[] node = fl.getPreviousPanel();
			JPanel previous = PanelHolder[node[0]][node[1]];
			previous.remove(fl.getImage());
		}
	}

	public void redraw() {
		revalidate();
		repaint();
	}
}
