package js;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

/**
 * A dialog which displays a progress bar and a message. It contains no buttons, and components cannot be added to it.
 * 
 * @author Josh
 * @version 1.0
 */
public class JSProgressWindow extends JDialog {

	private static final long serialVersionUID = 8240418399735183547L;
	private JProgressBar progressBar;
	private String message;
	private JLabel messageLabel;
	
	/**
	 * Create a new dialog with a specified maximum value for the progress bar, no title and no message. 
	 * 
	 * @param maximum the maximum value for the progress bar
	 */
	public JSProgressWindow(int maximum) {
		this(maximum, "", "");
	}
	
	/**
	 * Create a new dialog with a specified maximum value for the progress bar, a specified message and no title. 
	 * 
	 * @param maximum the maximum value for the progress bar
	 * @param message the message to display below the progress bar
	 */
	public JSProgressWindow(int maximum, String message) {
		this(maximum, message, "");
	}
	
	/**
	 * Create a new dialog with a specified maximum value for the progress bar, a specified message and a specified title. 
	 * 
	 * @param maximum the maximum value for the progress bar
	 * @param message the message to display below the progress bar
	 * @param title the title for the dialog
	 */
	public JSProgressWindow(int maximum, String message, String title) {
		setTitle(title);
		setSize(450, 150);
		setResizable(false);
		setLayout(null);
		
		progressBar = new JProgressBar(0, maximum);
		progressBar.setBounds(20, 30, 410, 30);
		add(progressBar);
		
		this.message = message;
		messageLabel = new JLabel(message, SwingConstants.CENTER);
		messageLabel.setBounds(0, 70, 450, 30);
		add(messageLabel);
	}
	
	/**
	 * Resets the progress bar's value back to zero.
	 */
	public void reset() {
		progressBar.setValue(0);
	}
	
	/**
	 * Sets the value of the progress bar.
	 * 
	 * @param value the value to give the progress bar
	 */
	public void setValue(int value) {
		progressBar.setValue(value);
	}
	
	/**
	 * Determines the current value of the progress bar.
	 * 
	 * @return the current value of the progress bar
	 */
	public int getValue() {
		return progressBar.getValue();
	}
	
	/**
	 * Sets the maximum value for the progress bar.
	 * 
	 * @param value the maximum value to give the progress bar
	 */
	public void setMaximumValue(int value) {
		progressBar.setMaximum(value);
	}
	
	/**
	 * Determines the current maximum value of the progress bar.
	 * 
	 * @return the current maximum value of the progress bar
	 */
	public int getMaximumValue() {
		return progressBar.getMaximum();
	}
	
	/**
	 * Sets the message to display below the progress bar.
	 * 
	 * @param message the message to display below the progress bar
	 */
	public void setMessage(String message) {
		this.message = message;
		messageLabel.setText(message);
	}
	
	/**
	 * Determines the current message being displayed below the progress bar.
	 * 
	 * @return the current message being displayed below the progress bar
	 */
	public String getMessage() {
		return this.message;
	}
}
