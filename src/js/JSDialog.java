package js;

import javax.swing.JDialog;

/**
 * An extension to {@link JDialog} which simply provides a way to animate size changes.
 * 
 * @author Josh
 * @version 1.0
 */
public class JSDialog extends JDialog {
	
	private static final long serialVersionUID = 6371265511529986868L;

	public JSDialog() {
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
			
			if (x != currentX && ppsX != 0) {
				if ((x - i) / ppsX <= 1 && (x - i) / -ppsX <= 1)
					i = x;
			}
			if (y != currentY && ppsY != 0) {
				if ((y - j) / ppsY <= 1 && (y - j) / -ppsY <= 1)
					j = y;
			}
			
			setSize(i, j);
		}
	}
	
}
