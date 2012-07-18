package js.incomplete;

import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JSNotification extends JFrame {

	private String title;
	private String text;
	private Icon icon;
	
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
	}
	
}
