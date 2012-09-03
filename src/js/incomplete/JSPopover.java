package js.incomplete;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JSPopover extends JFrame {
	
	public static final int VERTICAL = 0;
	public static final int HORIZONTAL = 1;
	
	private Color strokeColor = Color.black;
	private JPanel panel;
	private int direction;
	
	public JSPopover() {
		direction = VERTICAL;
		setLayout(null);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setAlwaysOnTop(true);
		setFocusableWindowState(false);
		getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
		
		panel = new JPanel();
		panel.setBounds(10, 30, getWidth() - 20, getHeight() - 20);
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		add(panel);
	}
	
	public JSPopover(int direction) {
		this.direction = direction;
		setLayout(null);
		setUndecorated(true);
		setBackground(new Color(0, 0, 0, 0));
		setAlwaysOnTop(true);
		setFocusableWindowState(false);
		getRootPane().putClientProperty("apple.awt.draggableWindowBackground", Boolean.FALSE);
		
		panel = new JPanel();
		if (direction == HORIZONTAL)
			panel.setBounds(30, 10, getWidth() - 40, getHeight() - 20);
		else
			panel.setBounds(10, 30, getWidth() - 20, getHeight() - 40);
		panel.setBackground(Color.WHITE);
		panel.setLayout(null);
		add(panel);
	}
	
	public void setStrokeColor(Color c) {
		strokeColor = c;
		repaint();
	}
	
	public void setContentBackground(Color c) {
		panel.setBackground(c);
	}
	
	public void paint(Graphics g) {
		g.clearRect(0, 0, getWidth(), getHeight());
		g.setColor(strokeColor);
		
		int width = getWidth();
		int height = getHeight();
		int middleH = height / 2;
		int middleW = width / 2;
		
		if (direction == HORIZONTAL) {
			int[] xPoints = {0, 20, 20, 0};
			int[] yPoints = {middleH, middleH - 20, middleH + 20, middleH};
			int nPoints = xPoints.length;
			Polygon p = new Polygon(xPoints, yPoints, nPoints);
			g.fillPolygon(p);
			g.fillRoundRect(20, 0, width - 21, height - 1, 20, 20);
		} else {
			int[] xPoints = {middleW, middleW + 20, middleW - 20, middleW};
			int[] yPoints = {0, 20, 20, 0};
			int nPoints = xPoints.length;
			Polygon p = new Polygon(xPoints, yPoints, nPoints);
			g.fillPolygon(p);
			g.fillRoundRect(0, 20, width - 1, height - 21, 20, 20);
		}

		if (direction == HORIZONTAL)
			panel.setBounds(30, 10, getWidth() - 40, getHeight() - 20);
		else
			panel.setBounds(10, 30, getWidth() - 20, getHeight() - 40);
		super.paint(g);
	}
	
	public void setLocation(int x, int y) {
		int width = getWidth();
		int height = getHeight();
		int middleH = height / 2;
		int middleW = width / 2;
		
		if (direction == HORIZONTAL)
			super.setLocation(x, y - middleH);
		else
			super.setLocation(x - middleW, y);
	}
	
	public Component add(Component comp) {
		if (comp != panel) {
			return panel.add(comp);
		} else {
			return super.add(comp);
		}
	}
	
	public void setSize(int width, int height) {
		if (direction == HORIZONTAL) {
			panel.setBounds(30, 10, width - 40, height - 20);
			super.setSize(width + 20, height);
		} else {
			panel.setBounds(10, 30, getWidth() - 20, getHeight() - 40);
			super.setSize(width, height + 20);
		}
	}
	
	public void toggleDirection() {
		if (direction == HORIZONTAL)
			setDirection(VERTICAL);
		else
			setDirection(HORIZONTAL);
	}
	
	public void setDirection(int direction) {
		if (this.direction != direction) {
			Point old = getLocationOnScreen();
			this.direction = direction;
			if (this.direction == VERTICAL) {
				setSize(getWidth() - 20, getHeight());
				old.y += (getHeight() / 2) - 10;
			} else {
				setSize(getWidth(), getHeight() - 20);
				old.x += (getWidth() / 2) - 10;
			}
			repaint();
			setLocation(old);
		}
	}

}
