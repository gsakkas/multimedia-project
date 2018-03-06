package multimedia;

import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class TextAreaPanel extends JLabel {

	public TextAreaPanel(){
		super("<html>Creating map...");
		setVerticalAlignment(SwingConstants.TOP);
		addText("Reading all input files...");
		addText("Read default input files");
		addText("Map created!");
		setOpaque(true);
	}

	public void addText(String text) {
		setText(getText() + "<br>" + text);
	}

}