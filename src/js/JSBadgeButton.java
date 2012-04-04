package js;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;

/**
 * JSBadgeButton is an extension to {@link JButton} which allows
 * an iOS style notification badge to be added to the button.
 * 
 * When the value of the number in the badge is zero, the badge
 * is no longer painted until the value increases again.
 * 
 * @author Josh Sunshine
 * @version 1.0
 * 
 */
public class JSBadgeButton extends JButton {
	
	private static final long serialVersionUID = -7821966381274983055L;
	private int number;
	private Image badge;
	public static final int EMPTY = -1;
	
	/**
	 * Creates a new <code>JSBadgeButton</code> with the value set to zero.
	 */
	public JSBadgeButton() {
		this(0);
	}
	
	/**
	 * Creates a new <code>JSBadgeButton</code> with the value already applied and the badge showing.
	 * 
	 * @param value - the value to display in the badge initially.
	 */
	public JSBadgeButton(int value) {
		setNumber(value);
		badge = getImage();
	}
	
	/**
	 * Loads the badge image from the <code>images</code> directory.
	 * 
	 * @return The badge image.
	 */
	private Image getImage() {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("images/badge-small.png"));
		} catch (IOException e) {}
		return img;
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if (getNumber() > 0) {
			g.drawImage(badge, this.getWidth() - 37, 0, null);
			g.setColor(Color.WHITE);
			
			if (getNumber() < 10) {
				g.setFont(new Font("Helvetica Neue", Font.PLAIN, 22));
				g.drawString(Integer.toString(getNumber()), this.getWidth() - 25, 24);
			} else if (getNumber() >= 10 && getNumber() < 100) {
				g.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
				g.drawString(Integer.toString(getNumber()), this.getWidth() - 29, 23);
			} else if (getNumber() >= 100) {
				g.setFont(new Font("Helvetica Neue", Font.PLAIN, 18));
				g.drawString("99", this.getWidth() - 29, 23);
			}
		} else if (getNumber() == -1) {
			g.drawImage(badge, this.getWidth() - 34, 0, null);
		}
	}
	
	/**
	 * Changes the number in the badge to a new value.
	 * If the new value is zero, the badge will not be shown.
	 * 
	 * @param value - the new value to display in the badge.
	 */
	public void setNumber(int value) {
		number = value;
		repaint();
	}
	
	/**
	 * Returns the current value the badge is displaying (including zero).
	 * 
	 * @return The current value of the badge.
	 */
	public int getNumber() {
		return number;
	}
	
	/**
	 * Increases the value of the badge by one. 
	 */
	public void increment() {
		setNumber(getNumber() + 1);
		repaint();
	}
	
	/**
	 * Decreases the value of the badge by one, if the value is currently greater than zero. 
	 */
	public void decrement() {
		if (getNumber() > 0) {
			setNumber(getNumber() - 1);
			repaint();
		}
	}
}
