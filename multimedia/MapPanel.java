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
import java.awt.Insets;
import java.awt.Dimension;
import java.lang.Math;

public class MapPanel extends JPanel {
	private JPanel PanelHolder[][];
	private Insets insets;

	public MapPanel(int DimX, int DimY, int[][] Grid, ArrayList<Airports> airports) {
		super(null);
		insets = this.getInsets();
		this.setPreferredSize(new Dimension(DimY * 16, DimX * 16));
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
				Dimension size = PanelHolder[i][j].getPreferredSize();
				PanelHolder[i][j].setBounds(j * 16 + insets.left, i * 16 + insets.top,
					size.width, size.height);
			}
		}

		for (Airports airport: airports){
			GridBagConstraints c = new GridBagConstraints();
			c.anchor = GridBagConstraints.CENTER;
			PanelHolder[airport.getX()][airport.getY()].add(airport.getImg(), c);
		}
		
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

	public void moveToNextPanel(Flights fl) {
		int[] node1 = null;
		int[] node2 = null;
		JLabel img1 = fl.getImage();
		String icon = "./SimulationIcons/small_n.png";
		if (fl.getAirplaneType() == 1) {
			icon = "./SimulationIcons/small_n.png";
		}
		else if (fl.getAirplaneType() == 2) {
			icon = "./SimulationIcons/middle_n.png";
		}
		else if (fl.getAirplaneType() == 3) {
			icon = "./SimulationIcons/big_n.png";
		}
		JLabel img2 = new JLabel(new ImageIcon(icon));
		boolean flag = !fl.checkPath();
		if (img1 != null) {
			node1 = fl.getPreviousPanel();
			this.remove(img1);
			fl.setFirstOrLast(false);
		}
		if (flag) {
			node2 = fl.removeFromPath();
			String direction = "n";
			if (node1 != null) {
				if (node2[0] - node1[0] < 0) {
					direction = "n";
				}
				else if (node2[0] - node1[0] > 0) {
					direction = "s";
				}
				else if (node2[1] - node1[1] < 0) {
					direction = "w";
				}
				else{
					direction = "e";
				}
			}
			if (fl.getAirplaneType() == 1) {
				icon = "./SimulationIcons/small_" + direction + ".png";
			}
			else if (fl.getAirplaneType() == 2) {
				icon = "./SimulationIcons/middle_" + direction + ".png";
			}
			else if (fl.getAirplaneType() == 3) {
				icon = "./SimulationIcons/big_" + direction + ".png";
			}
			img2 = new JLabel(new ImageIcon(icon));
			this.add(img2);
			Dimension size = img2.getPreferredSize();
			setComponentZOrder(img2, getMin(node2) - 2);
			int w = (size.width - 16) / 2;
			int h = (size.height - 16) / 2;
			img2.setBounds(node2[1] * 16  - w + insets.left, node2[0] * 16 - h + insets.top,
				size.width, size.height);
			// System.out.println("normal: " + (node2[1] * 16 - w + insets.left) + ", " + (node2[0] * 16 - h + insets.top));
			fl.setPreviousPanel(node2);
			fl.setImage(img2);
			fl.updateQuants();
		}
		else fl.finishSim();
	}

	public void moveToNextPanelSlightly(Flights fl) {
		int[] node1 = null;
		int[] node2 = null;
		JLabel img1 = fl.getImage();
		String icon = "./SimulationIcons/small_n.png";
		if (fl.getAirplaneType() == 1) {
			icon = "./SimulationIcons/small_n.png";
		}
		else if (fl.getAirplaneType() == 2) {
			icon = "./SimulationIcons/middle_n.png";
		}
		else if (fl.getAirplaneType() == 3) {
			icon = "./SimulationIcons/big_n.png";
		}
		JLabel img2 = new JLabel(new ImageIcon(icon));
		boolean flag = !fl.checkPath();
		if (img1 != null) {
			node1 = fl.getPreviousPanel();
			this.remove(img1);
		}
		if (flag && (fl.getPreviousPanel() != null)) {
			node2 = fl.checkFirstInPath();
			int quants = fl.getQuants();
			String direction = "n";
			if (node1 != null) {
				if (node2[0] - node1[0] < 0) {
					direction = "n";
				}
				else if (node2[0] - node1[0] > 0) {
					direction = "s";
				}
				else if (node2[1] - node1[1] < 0) {
					direction = "w";
				}
				else {
					direction = "e";
				}
			}
			if (fl.getAirplaneType() == 1) {
				icon = "./SimulationIcons/small_" + direction + ".png";
			}
			else if (fl.getAirplaneType() == 2) {
				icon = "./SimulationIcons/middle_" + direction + ".png";
			}
			else if (fl.getAirplaneType() == 3) {
				icon = "./SimulationIcons/big_" + direction + ".png";
			}
			img2 = new JLabel(new ImageIcon(icon));
			this.add(img2);
			Dimension size = img2.getPreferredSize();
			setComponentZOrder(img2, getMin(node2) - 2);
			int w = (size.width - 16) / 2;
			int h = (size.height - 16) / 2;
			int quantized_w = 16 * (quants + 1) * (node2[1] - node1[1]) / fl.getMaxQuants();
			int quantized_h = 16 * (quants + 1) * (node2[0] - node1[0]) / fl.getMaxQuants();
			img2.setBounds(node1[1] * 16 + quantized_w - w + insets.left, node1[0] * 16 + quantized_h - h + insets.top,
				size.width, size.height);
			// System.out.println("sl: " + (node1[1] * 16 + quantized_w - w + insets.left) + ", " + (node1[0] * 16 + quantized_h - h + insets.top));
			fl.setImage(img2);
			fl.updateQuants();
		}
	}

	private int getMin(int[] node) {
		int center = getComponentZOrder(PanelHolder[node[0]][node[1]]);
		int upper = node[0]-1 >= 0 ? getComponentZOrder(PanelHolder[node[0]-1][node[1]]) : center;
		int left = node[1]-1 >= 0 ? getComponentZOrder(PanelHolder[node[0]][node[1]-1]) : center;
		return Math.min(center, Math.min(upper, left));
	}

	public void clearImg(Flights fl) {
		if (fl.getImage() != null) {
			this.remove(fl.getImage());
		}
	}

	public void redraw() {
		revalidate();
		repaint();
	}
}
