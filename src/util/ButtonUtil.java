package util;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.SwingConstants;

public class ButtonUtil extends JButton {

	public ButtonUtil(String text) {
		setText(text);
		setBackground(Color.WHITE);
		setForeground(Colors.MainColor);
		setHorizontalAlignment(SwingConstants.CENTER);
		
	}

}
