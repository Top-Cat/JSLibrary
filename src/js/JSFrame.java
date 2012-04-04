package js;

import javax.swing.JFrame;

/**
 * An extension to {@link JFrame} which simply provides a way to animate size changes.
 * 
 * @author Josh
 * @version 1.0.1
 */
public class JSFrame extends JFrame {
	
	private static final long serialVersionUID = 5850064278763136458L;

	public JSFrame() {
		JSUtil.checkForJSLibraryUpdate();
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
	
}
