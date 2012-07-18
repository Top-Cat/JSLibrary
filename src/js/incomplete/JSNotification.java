package js.incomplete;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JSNotification extends JFrame {

	private String title;
	private String text;
	private Icon icon;
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
	
	public JSNotification(String title, String text, Icon icon) {
		this.title = title;
		this.text = text;
		this.icon = icon;
		
		setSize(270, 80);
		setUndecorated(true);
		setLayout(null);
		
		iconLabel = new JLabel(icon);
		iconLabel.setBounds(5, 5, 50, 50);
		add(iconLabel);
		
		titleLabel = new JLabel(title);
		titleLabel.setBounds(80, 5, 190, 20);
		titleLabel.setFont(titleLabel.getFont().deriveFont(14f));
		add(titleLabel);
		
		textLabel = new JLabel("<html>" + text);
		textLabel.setBounds(80, 25, 185, 50);
		add(textLabel);
	}
	
	public void setLocationOnScreen(int location) {
		this.location = location;
		setLocation(getPointForLocation(location));
	}
	
	private BufferedImage resizeImage(BufferedImage originalImage, int type){
		BufferedImage resizedImage = new BufferedImage(50, 50, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, 50, 50, null);
		g.dispose();
	 
		return resizedImage;
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
