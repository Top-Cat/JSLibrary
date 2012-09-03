package js.incomplete;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JSPopover extends JFrame {
	
	public static final int TOP = 0;
	public static final int LEFT = 1;
	
	private Color strokeColor = Color.black;
	private JPanel panel;
	private int direction;
	
	public void setStrokeColor(Color c) {
		strokeColor = c;
		repaint();
	}
	
	public void setBackground(Color c) {
		panel.setBackground(c);
	}
	
	public void paint(Graphics g) {				
		g.setColor(strokeColor);
		
		int width = getWidth();
		int height = getHeight();
		int middleH = height / 2;
		int middleW = width / 2;
		
		if (direction == LEFT) {
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
		
		panel.setBackground(Color.RED);
		add(panel);
		super.paint(g);
	}
	
	public void setLocation(int x, int y) {
		int width = getWidth();
		int height = getHeight();
		int middleH = height / 2;
		int middleW = width / 2;
		
		if (direction == LEFT)
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
		if (direction == LEFT)
			super.setSize(width + 20, height);
		else
			super.setSize(width, height + 20);
		panel.setBounds(30, 10, width - 20, height - 20);
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
		repaint();
	}

}
