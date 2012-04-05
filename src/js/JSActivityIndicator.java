package js;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * JSActivityIndicator allows a spinner to be displayed when invisible processing is occurring. It is modelled on the spinner
 * seen in Mac OS X and iOS. The size of the spinner is always 36 by 36 pixels.
 * 
 * @author Josh
 *
 * @version	1.0
 */
public class JSActivityIndicator extends JComponent {

	private JLabel label;
	
	/**
	 * Creates a new spinner but doesn't start animating it yet.
	 */
	public JSActivityIndicator() {
		label = new JLabel();
	}
	
	/**
	 * Displays the spinner and starts the animation.
	 */
	public void startAnimating() {
		label.setIcon(new ImageIcon("images/ActivityIndicator.gif"));
		label.setBounds(0, 0, 36, 36);
		add(label);
		repaint();
	}
	
	/**
	 * Stops the animation and removes the spinner from view.
	 */
	public void stopAnimating() {
		remove(label);
		repaint();
	}
	
	/**
	 * <b>This method does nothing when called</b>. It is overridden to cause the size of the spinner
	 * to always be 36 by 36. 
	 */
	public void setSize(int width, int height) {
		super.setSize(36, 36);
	}
	
	/**
	 * Allows the location of the spinner to be set, but the width and height parameters are
	 * overridden to cause the size of the spinner to always be 36 by 36.
	 */
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, 36, 36);
	}
	
}
