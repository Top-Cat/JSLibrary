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
		
		int width = getWidth();
		int height = getHeight();
		int middleH = height / 2;
		int middleW = width / 2;
		
		int[] xPoints = {0, 20, 20, 0};
		int[] yPoints = {middleH, middleH - 20, middleH + 20, middleH};
		int nPoints = xPoints.length;
		
		Polygon p = new Polygon(xPoints, yPoints, nPoints);
		g.fillPolygon(p);
		g.fillRoundRect(20, 0, width - 21, height - 1, 20, 20);
		
		panel.setBackground(Color.RED);
		add(panel);
		super.paint(g);
	}

}
