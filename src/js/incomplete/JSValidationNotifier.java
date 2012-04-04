package js.incomplete;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JSValidationNotifier extends JLabel {
	
	private static final long serialVersionUID = -3735000639680316032L;
	boolean correct;
	boolean visible;
	JFrame popup;
	JLabel popupLabel;
	String popupText;
	ImageIcon cross;
	ImageIcon tick;
	
	JSValidationNotifier() {
		cross = new ImageIcon("images/cross.png");
		tick = new ImageIcon("images/tick.png");
		this.setSize(35, 35);
	}
	
	public void setCorrect(boolean b) {
		correct = b;
	}
	
	public boolean isCorrect() {
		return correct;
	}
	
	public boolean isPopupShowing() {
		return popup != null;
	}
	
	public void setPopupText(String text) {
		popupText = text;
	}
	
	public void showIcon() {
		if (correct) {
			this.setIcon(tick);
		} else {
			this.setIcon(cross);
			popup = new JFrame();
			popup.setSize(200, 75);
			popup.setLayout(null);
			popup.setUndecorated(true);
			popup.setLocation(this.getLocationOnScreen().x + 46, this.getLocationOnScreen().y - 17);
			popupLabel = new JLabel("<html>" + popupText + "</html>");
			popupLabel.setBounds(5, 5, 190, 65);
			popup.add(popupLabel);
			popup.setVisible(true);
		}
		visible = true;
	}
	
	public void hideIcon() {
		if (visible) {
			this.setIcon(null);
			popup.setVisible(false);
			visible = false;
		}
	}
	
	public void hidePopup() {
		popup.setVisible(false);
	}
	
}
