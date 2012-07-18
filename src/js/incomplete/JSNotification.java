package js.incomplete;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JSNotification extends JFrame {

	private String title;
	private String text;
	private ImageIcon icon;
	private int location;
	
	private JLabel titleLabel;
	private JLabel textLabel;
	private JLabel iconLabel;
	
	public static final int TOP_LEFT = 0;
	public static final int TOP = 1;
	public static final int TOP_RIGHT = 2;
	public static final int LEFT = 3;
	public static final int CENTER = 4;
	public static final int RIGHT = 5;
	public static final int BOTTOM_LEFT = 6;
	public static final int BOTTOM = 7;
	public static final int BOTTOM_RIGHT = 8;
	
	public JSNotification(String text) {
		this("", text, null);
	}
	
	public JSNotification(String title, String text) {
		this(title, text, null);
	}
	
	public JSNotification(String title, String text, ImageIcon icon) {
		this.title = title;
		this.text = text;
		this.icon = icon;
		
		setSize(270, 80);
		setUndecorated(true);
		setLayout(null);
		
		titleLabel = new JLabel(title);
		titleLabel.setBounds(65, 5, 200, 20);
		titleLabel.setFont(titleLabel.getFont().deriveFont(12f));
		add(titleLabel);
		
		textLabel = new JLabel("<html>" + text);
		textLabel.setBounds(65, 30, 200, 40);
		textLabel.setFont(textLabel.getFont().deriveFont(11f));
		add(textLabel);
	}
	
	public void setLocationOnScreen(int location) {
		this.location = location;
		setLocation(getPointForLocation(location));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
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
	
}
