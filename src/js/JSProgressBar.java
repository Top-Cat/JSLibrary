package js;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;


/**
 * An alternative to which has a consistent look across all platforms and is more customisable than JProgressBar.
 * The design is modelled on the progress bars seen in iOS, which have rounded ends and a two-tone design for the bar itself.
 * 
 * @author Josh
 * @version 1.0.1
 */
public class JSProgressBar extends JComponent {

	private static final long serialVersionUID = 701828973548951028L;
	private Color baseColor = new Color(82, 112, 200);
	private int maxValue;
	private int value;
	
	/**
	 * Creates a new progress bar with a maximum value of 100 and a current value of 0.
	 */
	public JSProgressBar() {
		this(100);
	}
	
	/**
	 * Creates a new progress bar with a specified maximum value and a current value of 0.
	 * 
	 * @param max the maximum value the progress bar can be set to
	 */
	public JSProgressBar(int max) {
		this(max, 0);
	}
	
	/**
	 * Creates a new progress bar with a specified maximum value and a specified current value. 
	 * 
	 * @param max the maximium value the progress bar can be set to
	 * @param value the initial value for the progress bar
	 */
	public JSProgressBar(int max, int value) {
		this.maxValue = max;
		this.value = value;
		JSUtil.checkForJSLibraryUpdate();
	}
	
	/**
	 * Sets the value of the progress bar, and updates the position of the bar accordingly.
	 * 
	 * @param value the new value for the progress bar
	 */
	public void setValue(int value) {
		this.value = value;
		repaint();
	}
	
	/**
	 * Determines the current value of the progress bar.
	 * 
	 * @return an integer representing the current value of the progress bar
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * Increases the current value of the bar by one and updates the position of the bar accordingly.
	 */
	public void increment() {
		this.value += 1;
		repaint();
	}
	
	/**
	 * Sets the maximum value the progress bar can be set to, and adjusts the position of the bar if necessary.
	 * 
	 * @param max the new maximum value for the progress bar
	 */
	public void setMax(int max) {
		this.maxValue = max;
		repaint();
	}
	
	/**
	 * Determines the current maximum value the progress bar can take.
	 * 
	 * @return an integer representing the progress bar's maximum value
	 */
	public int getMax() {
		return maxValue;
	}
	
	/**
	 * Sets the base color of the bar.
	 * 
	 * @param color the new color for the bar
	 */
	public void setForeground(Color color) {
		baseColor = color;
		repaint();
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.white);
		g.fillRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1, this.getHeight(), this.getHeight());
		g.setColor(new Color(200, 200, 200));
		g.fillArc(0, 0, this.getHeight(), this.getHeight() - 1, 90, 90);
		g.fillArc(this.getWidth() - this.getHeight() - 1, 0, this.getHeight(), this.getHeight() - 1, 0, 90);
		g.fillRect(this.getHeight() / 2, 0, this.getWidth() - this.getHeight(), this.getHeight() / 2);
		g.setColor(new Color(168, 168, 168));
		g.drawRoundRect(0, 0, this.getWidth() - 1, this.getHeight() - 1 , this.getHeight(), this.getHeight());
		
		if (value > 3) {
			g.setColor(baseColor);
			g.fillArc(0, 0, this.getHeight(), this.getHeight() - 1, 90, 90);
			g.fillArc(getBarWidth() - this.getHeight(), 0, this.getHeight(), this.getHeight() - 1, 0, 90);
			g.fillRect(this.getHeight() / 2, 0, getBarWidth() - this.getHeight(), this.getHeight() / 2);
			
			Graphics2D g2d = (Graphics2D) g;
			GradientPaint paint = new GradientPaint(0, this.getHeight() / 2, baseColor, 0, this.getHeight(), calculateColors());
			g2d.setPaint(paint);
			g2d.fillArc(0, 0, this.getHeight(), this.getHeight() - 1, 180, 90);
			g2d.fillArc(getBarWidth() - this.getHeight(), 0, this.getHeight(), this.getHeight() - 1, 270, 90);
			g2d.fillRect(this.getHeight() / 2, this.getHeight() / 2 , getBarWidth() - this.getHeight(), this.getHeight() / 2);
			
			paint = new GradientPaint(0, 0, new Color(255, 255, 255, 180), 0, this.getHeight() / 2, new Color(255, 255, 255, 100));
			g2d.setPaint(paint);
			g.fillArc(0, 0, this.getHeight(), this.getHeight(), 90, 90);
			g.fillArc(getBarWidth() - this.getHeight(), 0, this.getHeight(), this.getHeight(), 0, 90);
			g.fillRect(this.getHeight() / 2, 0, getBarWidth() - this.getHeight(), this.getHeight() / 2);
			
			g.setColor(baseColor);
			g.drawRoundRect(0, 0, getBarWidth() - 1, this.getHeight() - 1 , this.getHeight(), this.getHeight());
		}
	}
	
	private int getBarWidth() {
		int totalWidth = this.getWidth();
		double ratio = (double) value / (double) maxValue;
		int barWidth = (int) JSUtil.round(ratio * (double) totalWidth, 0);
		return barWidth;
	}
	
	private Color calculateColors() {
		int newR, newG, newB;
		
		if (baseColor.getRed() < 225)
			newR = baseColor.getRed() + 30;
		else
			newR = 255;
		
		if (baseColor.getGreen() < 225)
			newG = baseColor.getGreen() + 30;
		else
			newG = 255;
		
		if (baseColor.getBlue() < 225)
			newB = baseColor.getBlue() + 30;
		else
			newB = 255;
		
		return new Color(newR, newG, newB);
	}
	
}
