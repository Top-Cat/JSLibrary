package js;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

/**
 * An implementation of an iTunes style push button, which is a subclass of JButton. Currently only provided images can be used, but an update is
 * planned for the future which will allow the use of custom icons on buttons.
 * 
 * @author Josh
 * @version 1.0
 * @deprecated This is too much to do what with having to create two images for every icon, and I don't believe anyone uses it anyway.
 */
public class JSRoundButton extends JButton implements MouseListener {

	private static final long serialVersionUID = -4298424987380284780L;
	private int currentIcon;
	
	public static final int DOWN_FACING_ARROW = 0;
	private static final int DOWN_FACING_ARROW_PRESSED = 100;
	public static final int LEFT_FACING_ARROW = 1;
	private static final int LEFT_FACING_ARROW_PRESSED = 101;
	public static final int RIGHT_FACING_ARROW = 2;
	private static final int RIGHT_FACING_ARROW_PRESSED = 102;
	
	private ImageIcon downArrowIcon = new ImageIcon("images/down_facing_arrow.png");
	private ImageIcon downArrowIconPressed = new ImageIcon("images/down_facing_arrow_pressed.png");
	private ImageIcon leftArrowIcon = new ImageIcon("images/left_facing_arrow.png");
	private ImageIcon leftArrowIconPressed = new ImageIcon("images/left_facing_arrow_pressed.png");
	private ImageIcon rightArrowIcon = new ImageIcon("images/right_facing_arrow.png");
	private ImageIcon rightArrowIconPressed = new ImageIcon("images/right_facing_arrow_pressed.png");
	
	/**
	 * Creates a new button with the selected icon and the specified text below.
	 * 
	 * @param label the text to display below the button
	 * @param icon the icon to show on the button
	 */
	public JSRoundButton(String label, int icon) {
		this.setText(label);
		this.setVerticalTextPosition(SwingConstants.BOTTOM);
		this.setHorizontalTextPosition(SwingConstants.CENTER);
		this.setFocusPainted(false);
		this.setBorderPainted(false);
		this.setOpaque(false);
		this.setIcon(retrieveIcon(icon));
		this.addMouseListener(this);
		this.setSize(70, 70);
		this.setPreferredSize(new Dimension(70, 70));
	}
	
	/**
	 * Creates a new button with the selected icon and no text below.
	 * 
	 * @param icon the icon to show on the button
	 */
	public JSRoundButton(int icon) {
		this("", icon);
	}
	
	private ImageIcon retrieveIcon(int icon) {
		ImageIcon theIcon = null;
		switch (icon) {
		case DOWN_FACING_ARROW:
			theIcon = downArrowIcon;
			break;
		case DOWN_FACING_ARROW_PRESSED:
			theIcon = downArrowIconPressed;
			break;
		case LEFT_FACING_ARROW:
			theIcon = leftArrowIcon;
			break;
		case LEFT_FACING_ARROW_PRESSED:
			theIcon = leftArrowIconPressed;
			break;
		case RIGHT_FACING_ARROW:
			theIcon = rightArrowIcon;
			break;
		case RIGHT_FACING_ARROW_PRESSED:
			theIcon = rightArrowIconPressed;
			break;
		}
		
		currentIcon = icon;
		return theIcon;
	}
	
	/**
	 * Changes the icon on the button to the selected icon. 
	 * 
	 * @param icon the icon to display on the button
	 */
	public void setIcon(int icon) {
		this.setIcon(retrieveIcon(icon));
	}
	
	/**
	 * Returns the integer value representing the current icon on the button.
	 * 
	 * @return an integer representing the current icon
	 */
	public int getIconIdentifier() {
		return currentIcon;
	}
	
	private void setIconToPressed() {
		currentIcon += 100;
		setIcon(retrieveIcon(currentIcon));
	}
	
	private void setIconToUnpressed() {
		currentIcon -= 100;
		setIcon(retrieveIcon(currentIcon));
	}

	
	public void mouseClicked(MouseEvent event) {
		
	}

	
	public void mouseEntered(MouseEvent event) {
		
	}

	
	public void mouseExited(MouseEvent event) {
		if (getIconIdentifier() >= 100)
			setIconToUnpressed();
	}

	
	public void mousePressed(MouseEvent event) {
		setIconToPressed();
	}

	
	public void mouseReleased(MouseEvent event) {
		if (getIconIdentifier() >= 100)
			setIconToUnpressed();
	}
}
