package multimedia;

import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class TextAreaPanel extends JLabel {

	public TextAreaPanel(){
		super("<html>Creating map...");
		setVerticalAlignment(SwingConstants.TOP);
		addText("Map created!");
		addText("Reading all input files...");
		addText("Read all input files!");
		setOpaque(true);
	}

	public void addText(String text) {
		setText(getText() + "<br>" + text);
	}

}