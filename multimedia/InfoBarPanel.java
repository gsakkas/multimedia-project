package multimedia;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.text.SimpleDateFormat;

public class InfoBarPanel extends JPanel {
	private JPanel allLabels[];
	private int time;
	private int aircrafts;
	private int crashes;
	private int landings;

	public InfoBarPanel(){
		super(new GridLayout(1, 4));

		time = 0;
		aircrafts = 0;
		crashes = 0;
		landings = 0;

		allLabels = new JPanel[4];
		allLabels[0] = createTextJPanel("Simulated Time: " + writeTime(time));
		this.add(allLabels[0]);
		allLabels[1] = createTextJPanel("Aircrafts: " + aircrafts);
		this.add(allLabels[1]);
		allLabels[2] = createTextJPanel("Crashes: " + crashes);
		this.add(allLabels[2]);
		allLabels[3] = createTextJPanel("Landings: " + landings);
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

	public void setTime(int t) {
		time += t;
		JLabel temp = (JLabel)allLabels[0].getComponent(0);
		temp.setText("Simulated Time: " + writeTime(time));
	}

	private String writeTime(int time) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
		return sdf.format(time);
	}

	public void addAircraft(int airc) {
		aircrafts += airc;
		JLabel temp = (JLabel)allLabels[1].getComponent(0);
		temp.setText("Aircrafts: " + aircrafts);
	}

	public void removeAircraft(int airc) {
		aircrafts -= airc;
		JLabel temp = (JLabel)allLabels[1].getComponent(0);
		temp.setText("Aircrafts: " + aircrafts);
	}

	public void setCrashes(int c) {
		crashes += c;
		JLabel temp = (JLabel)allLabels[2].getComponent(0);
		temp.setText("Crashes: " + crashes);
	}

	public void setLandings(int l) {
		landings += l;
		JLabel temp = (JLabel)allLabels[3].getComponent(0);
		temp.setText("Landings: " + landings);
	}

	public void resetVars() {
		time = 0;
		aircrafts = 0;
		crashes = 0;
		landings = 0;
		setTime(0);
		addAircraft(0);
		setCrashes(0);
		setLandings(0);
	}
}