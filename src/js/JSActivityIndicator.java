package js;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class JSActivityIndicator extends JComponent {

	private boolean animating = false;
	private JLabel label = new JLabel();
	
	public JSActivityIndicator() {
		
	}
	
	public void startAnimating() {
		animating = true;
		label.setIcon(new ImageIcon("images/ActivityIndicator.gif"));
		label.setBounds(0, 0, 36, 36);
		add(label);
		repaint();
	}
	
	public void stopAnimating() {
		animating = false;
		remove(label);
		repaint();
	}
	
	public void setSize(int width, int height) {
		super.setSize(36, 36);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, 36, 36);
	}
	
}
