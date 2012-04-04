package js;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * An extension to {@link JFrame} which simply provides a way to animate size changes.
 * 
 * @author Josh
 * @version 1.0.1
 */
public class JSFrame extends JFrame {

	boolean hasGradient;
	Color start, end;
	LayoutManager layout;
	
	public JSFrame() {
		
	}
	
	/**
	 * Performs the same action as <code>setSize(int x, int y)</code>, but animates the change.
	 * 
	 * @param x - The new width of the dialog
	 * @param y - The new height of the dialog
	 */
	public void animateToSize(int x, int y) {
		int currentX = getWidth();
		int currentY = getHeight();
		int dx = x - currentX;
		int dy = y - currentY;
		int ppsX = dx / 20;
		int ppsY = dy / 20;
		
		int i = currentX;
		int j = currentY;
		
		for (int c = 0; c < 20; c ++) {
			i += ppsX;
			j += ppsY;
			
			if (x != currentX) {
				if ((x - i) / ppsX <= 1 && (x - i) / -ppsX <= 1)
					i = x;
			}
			if (y != currentY) {
				if ((y - j) / ppsY <= 1 && (y - j) / -ppsY <= 1)
					j = y;
			}
			
			setSize(i, j);
		}
	}
	
	public void setGradientBackground(Color startColor, Color endColor) {
		hasGradient = true;
		start = startColor;
		end = endColor;
		
		JPanel contentPane = new JPanel() {
            @Override
            protected void paintComponent(Graphics grphcs) {
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, start, 0, getHeight(), end);

                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                super.paintComponent(grphcs);
            }
        };
        contentPane.setOpaque(false);
        setContentPane(contentPane);
        
        setLayout(layout);
		
		repaint();
	}
	
	public void setLayout(LayoutManager layout) {
		super.setLayout(layout);
		this.layout = layout;
	}
	
	public void removeGradientBackground() {
		hasGradient = false;
		repaint();
	}
	
}
