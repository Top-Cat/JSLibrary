package js.incomplete;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class JSPopover extends JFrame {
	
	private Color strokeColor = Color.black;
	private JPanel panel;
	
	public void paint(Graphics g) {				
		g.setColor(strokeColor);
		
		int w = getWidth();
		int h = getHeight();
		int mH = h / 2;
		int mW = w / 2;
		
		int[] xPoints = {0, 20, 20, 0};
		int[] yPoints = {mH, mH - 20, mH + 20, mH};
		int nPoints = xPoints.length;
		
		Polygon p = new Polygon(xPoints, yPoints, nPoints);
		g.fillPolygon(p);
		g.fillRoundRect(20, 0, w - 21, h - 1, 20, 20);
		
		panel.setBackground(Color.RED);
		add(panel);
		super.paint(g);
	}

}
