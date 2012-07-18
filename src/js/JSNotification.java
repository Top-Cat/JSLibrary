package js;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * JSNotification provides a way to display a small notification on screen with a title and short paragraph of text.
 * It is inspired by the Growl framework for OS X.
 */
public class JSNotification extends JFrame implements PropertyChangeListener {

	private String title;
	private String text;
	private ImageIcon icon;
	private int location;
	private int duration;
	
	private JLabel titleLabel;
	private JLabel textLabel;
	private JLabel iconLabel;
	private WaitThread waitThread;
	
	public static final int TOP_LEFT = 0;
	public static final int TOP = 1;
	public static final int TOP_RIGHT = 2;
	public static final int LEFT = 3;
	public static final int CENTER = 4;
	public static final int RIGHT = 5;
	public static final int BOTTOM_LEFT = 6;
	public static final int BOTTOM = 7;
	public static final int BOTTOM_RIGHT = 8;
	
	/**
	 * Creates a new notification with no title, no image and the specified text.
	 * 
	 * @param text the text to display in the body of the notification.
	 */
	public JSNotification(String text) {
		this("", text, null);
	}
	
	/**
	 * Creates a new notification with the specified title, no image and the specified text.
	 * 
	 * @param title the text to display at the top of the notification.
	 * @param text the text to display in the body of the notification.
	 */
	public JSNotification(String title, String text) {
		this(title, text, null);
	}
	
	/**
	 * Creates a new notification with the specified title, specified image and specified text.
	 * 
	 * @param title the text to display at the top of the notification.
	 * @param text the text to display in the body of the notification.
	 * @param icon the image to display on the left hand side of the notification.
	 */
	public JSNotification(String title, String text, ImageIcon icon) {
		this.title = title;
		this.text = text;
		this.icon = icon;
		
		setSize(270, 80);
		setUndecorated(true);
		setLayout(null);
		
		titleLabel = new JLabel(title);
		titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD));
		titleLabel.setFont(titleLabel.getFont().deriveFont(12f));
		add(titleLabel);
		
		textLabel = new JLabel("<html>" + text);
		textLabel.setFont(textLabel.getFont().deriveFont(11f));
		add(textLabel);
		
		if (icon != null) {
			titleLabel.setBounds(65, 5, 200, 20);
			textLabel.setBounds(65, 30, 200, 40);
		} else {
			titleLabel.setBounds(10, 5, 260, 20);
			textLabel.setBounds(10, 30, 260, 40);
		}
	}
	
	/**
	 * Sets which corner of the screen the notification should be displayed in.<br><br>
	 * 
	 * To use this method, pass one of the JSNotification location constants such as <code>JSNotification.LEFT</code> or
	 * <code>JSNotification.BOTTOM_RIGHT</code>.
	 * 
	 * @param location the integer representing the corner of the screen to display the notification in.
	 */
	public void setLocationOnScreen(int location) {
		this.location = location;
		setLocation(getPointForLocation(location));
	}
	
	/**
	 * Sets the duration in seconds that the notification should be displayed for when using the <code>display()</code> method.
	 * 
	 * @param duration the time in seconds to display the notification for.
	 */
	public void setDisplayDuration(int duration) {
		this.duration = duration;
	}
	
	/**
	 * Makes the notification visible on screen and automatically hides it again once the specified duration has passed.
	 */
	public void display() {
		setVisible(true);
		waitThread = new WaitThread(duration);
		waitThread.addPropertyChangeListener(this);
		Thread t = new Thread(waitThread);
		t.start();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		if (icon != null) 
			g.drawImage(icon.getImage(), 5, 15, 50, 50, icon.getImageObserver());
	}
	
	private Point getPointForLocation(int location) {
		Point p = new Point();
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		switch (location) {
		case TOP_LEFT:
			p.x = 10;
			p.y = 30;
			break;
		case TOP:
			p.x = (screen.width - 270) / 2;
			p.y = 30;
			break;
		case TOP_RIGHT:
			p.x = screen.width - 280;
			p.y = 30;
			break;
		case LEFT:
			p.x = 10;
			p.y = (screen.height - 80) / 2;
			break;
		case CENTER:
			p.x = (screen.width - 270) / 2;
			p.y = (screen.height - 80) / 2;
			break;
		case RIGHT:
			p.x = screen.width - 280;
			p.y = (screen.height - 80) / 2;
			break;
		case BOTTOM_LEFT:
			p.x = 10;
			p.y = screen.height - 90;
			break;
		case BOTTOM:
			p.x = (screen.width - 270) / 2;
			p.y = screen.height - 90;
			break;
		case BOTTOM_RIGHT:
			p.x = screen.width - 280;
			p.y = screen.height - 90;
			break;
		}
		return p;
	}
	
	private class WaitThread implements Runnable {

		private int duration;
		private PropertyChangeListener listener;
		
		public WaitThread(int duration) {
			this.duration = duration;
		}
		
		public void addPropertyChangeListener(PropertyChangeListener l) {
			this.listener = l;
		}

		public void run() {
			long start = System.currentTimeMillis();
			long now = System.currentTimeMillis();
			while (now <= (start + duration)) {
				now = System.currentTimeMillis();
			}
			firePropertyChange("finished", false, true);
			listener.propertyChange(new PropertyChangeEvent(this, "finished", false, true));
		}
		
		public void setDuration(int d) {
			this.duration = d;
		}
		
	}

	public void propertyChange(PropertyChangeEvent e) {
		if (e.getSource() == waitThread) {
			setVisible(false);
		}
	}
	
}
