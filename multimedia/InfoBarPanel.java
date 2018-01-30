package multimedia;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;

public class InfoBarPanel extends JPanel {
	private JPanel allLabels[];

	public InfoBarPanel(){
		super(new GridLayout(1, 4));

		allLabels = new JPanel[4];
		allLabels[0] = createTextJPanel("Simulated Time: 00:00");
		this.add(allLabels[0]);
		allLabels[1] = createTextJPanel("Aircrafts: 0");
		this.add(allLabels[1]);
		allLabels[2] = createTextJPanel("Crashes: 0");
		this.add(allLabels[2]);
		allLabels[3] = createTextJPanel("Landings: 0");
		this.add(allLabels[3]);

		this.setOpaque(true);
	}

	private JPanel createTextJPanel(String text) {
		JPanel panel = new JPanel(new GridBagLayout());

		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		
		panel.add(new JLabel(text), c);
		return panel;
	}
}