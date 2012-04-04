package js;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;

/**
 * Provides a way to show a confirmation dialog, similar to that seen when using
 * <code>JOptionPane.showConfirmDialog</code>, but which contains a checkbox.
 * 
 * @author Josh
 * @version 1.0
 * 
 * @deprecated <code>JSConfirmDialogWithCheckbox</code> has been deprecated because it's much easier and looks much nicer if you use JOptionPane.
 * 				It turns out that the <code>message</code> parameter of <code>JOptionPane.showMessageDialog</code> can take an array of objects,
 * 				which you can use to pass a String as the message and a JCheckBox together.
 */
public class JSConfirmDialogWithCheckbox extends JDialog implements
		ActionListener {

	private static final long serialVersionUID = 1215605544562156289L;
	private JButton okButton;
	private JButton cancelButton;
	private JCheckBox checkBox;
	private JLabel messageLabel;
	private JLabel iconLabel;
	private boolean checkboxSelected;
	private int selectedButton;

	public static final int CANCEL_BUTTON = 0;
	public static final int OK_BUTTON = 1;

	/**
	 * Creates a new dialog, but doesn't display it yet.
	 * 
	 * @param message
	 *            - The text to display in the dialog
	 * @param title
	 *            - The title of the dialog
	 * @param checkboxLabel
	 *            - The label to add to the checkbox
	 * @param iconFilePath
	 *            - A file path pointing to an image file to display in the
	 *            dialog
	 */
	public JSConfirmDialogWithCheckbox(String message, String title,
			String checkboxLabel, String iconFilePath) {
		init(message, title, checkboxLabel, iconFilePath);
		JSUtil.checkForJSLibraryUpdate();
	}

	private void init(String message, String title, String checkboxLabel,
			String iconFilePath) {
		setTitle(title);
		setModal(true);
		setSize(300, 170);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable(false);
		if (iconFilePath.length() > 0) {
			iconLabel = new JLabel(new ImageIcon(iconFilePath));
			iconLabel.setBounds(10, 10, 60, 60);
			add(iconLabel);
		} else {
			iconLabel = new JLabel(" ");
			iconLabel.setBounds(10, 10, 60, 60);
			add(iconLabel);
		}
		this.messageLabel = new JLabel(message);
		this.messageLabel.setBounds(80, 0, 210, 80);
		add(messageLabel);
		this.checkBox = new JCheckBox(checkboxLabel);
		this.checkBox.setBounds(70, 80, 280, 20);
		add(checkBox);
		this.cancelButton = new JButton("Cancel");
		this.cancelButton.addActionListener(this);
		this.cancelButton.setBounds(70, 110, 100, 30);
		add(this.cancelButton);
		this.okButton = new JButton("OK");
		this.okButton.addActionListener(this);
		this.okButton.setBounds(180, 110, 100, 30);
		add(this.okButton);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JSConfirmDialogWithCheckbox.this.selectedButton = 0;
				JSConfirmDialogWithCheckbox.this.setVisible(false);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.cancelButton) {
			this.selectedButton = 0;
			this.setVisible(false);
		}
		if (e.getSource() == this.okButton) {
			this.selectedButton = 1;
			if (this.checkBox.isSelected())
				this.checkboxSelected = true;
			else
				this.checkboxSelected = false;
			this.setVisible(false);
		}
	}

	/**
	 * Display the dialog and return the index of the selected button.
	 * 
	 * @return An integer representing the index of the button clicked.
	 */
	public int display() {
		this.setVisible(true);
		return this.selectedButton;
	}

	/**
	 * Determines whether the checkbox was selected when the dialog was
	 * displayed.
	 * 
	 * @return <code>true</code> if the checkbox was selected, otherwise
	 *         <code>false</code>.
	 */
	public boolean isCheckboxSelected() {
		return checkboxSelected;
	}
}
