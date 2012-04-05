package js;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

/**
 * JSPanel provides an easy way to give a JPanel a gradient as a background rather than a solid color.
 * 
 * @author Josh
 *
 */
public class JSPanel extends JPanel {

	private static final long serialVersionUID = 3571522024292766588L;
	boolean hasGradient;
	Color start, end;
	
	/**
	 * Creates a new JSPanel.
	 */
	public JSPanel() {
		super();
	}
	
	/**
	 * Sets the background of the panel to a vertical linear gradient.
	 * 
	 * @param startColor the color at the top of the panel's gradient
	 * @param endColor the color at the bottom of the panel's gradient
	 */
	public void setGradientBackground(Color startColor, Color endColor) {
		hasGradient = true;
		setOpaque(false);
		start = startColor;
		end = endColor;
		repaint();
	}
	
	/**
	 * Resets the background to what it was before the gradient was added.
	 */
	public void removeGradientBackground() {
		hasGradient = false;
		setOpaque(true);
		repaint();
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		if (hasGradient) {
			GradientPaint gradient = new GradientPaint(0, 0, start, 0, this.getHeight(), end);
			g2d.setPaint(gradient);
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}
		
		super.paint(g);
	}
	
}
