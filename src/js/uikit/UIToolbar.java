package js.uikit;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import js.JSPanel;

/**
 * UIToolbar is the toolbar seen in iOS at the top and bottom of apps such as Mail, Safari and Notes. UIToolbar in the JSLibrary 
 * can be styled to look like the iPad (silver coloured) or the iPhone (blue coloured). Two buttons and a title can be displayed
 * in each toolbar.
 * 
 * @author Josh
 *
 * @version 1.0.1
 */
public class UIToolbar extends JSPanel implements MouseListener, KeyListener {

	private JLabel titleLabel;
	private JButton leftButton, rightButton;
	private int style;
	private boolean titleEditable, editMode = false;
	private JTextField editField;
	
	private ActionListener listener;
	
	private static final Color GREY = new Color(128, 128, 128);
	
	/** Style the toolbar blue like the iPhone. **/
	public static final int IPHONE_STYLE = 0;
	/** Style the toolbar silver like the iPad. **/
	public static final int IPAD_STYLE = 1;
	
	// The colors for the iPad style bar
	private static final Color IPAD_TOP = new Color(255, 255, 255);
	private static final Color IPAD_BOTTOM = new Color(168, 172, 185);
	private static final Color IPAD_BUTTON_TOP = new Color(177, 181, 187);
	private static final Color IPAD_BUTTON_BOTTOM = new Color(108, 115, 126);
	
	// The colors for the iPhone style bar
	private static final Color IPHONE_TOP = new Color(185, 195, 208);
	private static final Color IPHONE_BOTTOM = new Color(109, 131, 161);
	private static final Color IPHONE_BUTTON_TOP = new Color(133, 155, 183);
	private static final Color IPHONE_BUTTON_BOTTOM = new Color(74, 108, 155);
	
	private static final Font TITLE_FONT = new Font("", Font.PLAIN, 18);
	private static final Font BUTTON_FONT = new Font("", Font.PLAIN, 12);
	
	/* Begin constructors */

	/** Creates a new UIToolbar with the iPad style, no buttons and no title. **/
	public UIToolbar() {
		this(IPAD_STYLE, "", "", "");
	}
	
	/**
	 * Creates a new UIToolbar with the iPad style, no buttons and the specified title.
	 * 
	 * @param title the title to display in this toolbar
	 */
	public UIToolbar(String title) {
		this(IPAD_STYLE, "", title, "");
	}
	
	/**
	 * Creates a new UIToolbar with the iPad style, the specified buttons and specified title.
	 * 
	 * @param leftButton the text to display in the left button
	 * @param title the title to display
	 * @param rightButton the text to display in the right button
	 */
	public UIToolbar(String leftButton, String title, String rightButton) {
		this(IPAD_STYLE, leftButton, title, rightButton);
	}
	
	/**
	 * Creates a new UIToolbar with the specified style, no buttons and no title.
	 * 
	 * @param style the style to use for this toolbar, either <code>IPAD_STYLE</code> or <code>IPHONE_STYLE</code>.
	 */
	public UIToolbar(int style) {
		this(style, "", "", "");
	}
	
	/**
	 * Creates a new UIToolbar with the specified style, no buttons and the specified title.
	 * 
	 * @param style the style to use for this toolbar, either <code>IPAD_STYLE</code> or <code>IPHONE_STYLE</code>.
	 * @param title the title to display in this toolbar
	 */
	public UIToolbar(int style, String title) {
		this(style, "", title, "");
	}
	
	/**
	 * Creates a new UIToolbar with the specified style, buttons and title.
	 * 
	 * @param style the style to use for this toolbar, either <code>IPAD_STYLE</code> or <code>IPHONE_STYLE</code>.
	 * @param leftButton the text to display in the left button
	 * @param title the title to display
	 * @param rightButton the text to display in the right button
	 */
	public UIToolbar(int style, String leftButton, String title, String rightButton) {
		this.style = style;
		
		setLayout(null);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		addMouseListener(this);
		
		if (style == IPHONE_STYLE)
			setGradientBackground(IPHONE_TOP, IPHONE_BOTTOM);
		else
			setGradientBackground(IPAD_TOP, IPAD_BOTTOM);
			
		titleLabel = new JLabel(title, JLabel.CENTER);
		titleLabel.setFont(TITLE_FONT);
		if (style == IPHONE_STYLE)
			titleLabel.setForeground(Color.WHITE);
		//else
		//	titleLabel.setForeground(GREY);
		titleLabel.setBounds((getWidth() - 200) / 2, 10, 200, 25);
		titleLabel.addMouseListener(this);
		add(titleLabel);
		
		editField = new JTextField();
		editField.setFont(TITLE_FONT);
		editField.setHorizontalAlignment(JTextField.CENTER);
		editField.addKeyListener(this);
		editField.setBounds((getWidth() - 200) / 2, 10, 200, 25);
		
		if (leftButton.length() > 0) {
			if (style == IPHONE_STYLE)
				this.leftButton = new JButton(leftButton);
			else
				this.leftButton = new JButton(leftButton);
		//	this.leftButton.setForeground(GREY);
			this.leftButton.setFont(BUTTON_FONT);
			this.leftButton.setFocusPainted(false);
			this.leftButton.setBounds(10, 5, 65, 35);
			add(this.leftButton);
		}
		
		if (rightButton.length() > 0) {
			if (style == IPHONE_STYLE)
				this.rightButton = new JButton(rightButton);
			else
				this.rightButton = new JButton(rightButton);
		//	this.rightButton.setForeground(GREY);
			this.rightButton.setFont(BUTTON_FONT);
			this.rightButton.setFocusPainted(false);
			this.rightButton.setBounds((getWidth() - 75), 5, 65, 35);
			add(this.rightButton);
		}
	}
	
	/* Begin getters */
	
	/**
	 * Returns the left button as a JButton.
	 * 
	 * @return the left button
	 */
	public JButton getLeftButton() {
		return leftButton;
	}
	
	/**
	 * Returns the right button as a JButton.
	 * 
	 * @return the right button
	 */
	public JButton getRightButton() {
		return rightButton;
	}
	
	/**
	 * Returns the current title from the toolbar.
	 * 
	 * @return the toolbar's current title
	 */
	public String getTitle() {
		return titleLabel.getText();
	}
	
	/* Begin setters */
	
	/**
	 * Changes the title displayed in the toolbar.
	 * 
	 * @param title the new title to display
	 */
	public void setTitle(String title) {
		titleLabel.setText(title);
	}
	
	public void setSize(int width, int height) {
		super.setSize(width, height);
		titleLabel.setBounds((getWidth() - 200) / 2, 10, 200, 25);
		editField.setBounds((getWidth() - 200) / 2, 10, 200, 25);
		if (rightButton != null)
			rightButton.setBounds((getWidth() - 75), 5, 65, 35);
	}
	
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		titleLabel.setBounds((getWidth() - 200) / 2, 10, 200, 25);
		editField.setBounds((getWidth() - 200) / 2, 10, 200, 25);
		if (rightButton != null)
			rightButton.setBounds((getWidth() - 75), 5, 65, 35);
	}
	
	/**
	 * Sets the text to display in the left button. If an empty string is passed,
	 * the button is hidden from the toolbar.
	 * 
	 * @param text the text to display in the button
	 */
	public void setLeftButton(String text) {
		if (text.length() > 0) {	
			if (leftButton != null)
				leftButton.setText(text);
			else {
				if (style == IPHONE_STYLE)
					this.leftButton = new JButton(text);
				else
					this.leftButton = new JButton(text);
				this.leftButton.setFont(BUTTON_FONT);
				this.leftButton.setFocusPainted(false);
				this.leftButton.setBounds(10, 5, 65, 35);
				add(this.leftButton);
			}
		} else {
			if (leftButton != null)
				remove(leftButton);
			leftButton = null;
		}
		repaint();	
	}
	
	/**
	 * Sets the text to display in the right button. If an empty string is passed,
	 * the button is hidden from the toolbar.
	 * 
	 * @param text the text to display in the button
	 */
	public void setRightButton(String text) {
		if (text.length() > 0) {	
			if (rightButton != null)
				rightButton.setText(text);
			else {
				if (style == IPHONE_STYLE)
					this.rightButton = new JButton(text);
				else
					this.rightButton = new JButton(text);
				this.rightButton.setFont(BUTTON_FONT);
				this.rightButton.setFocusPainted(false);
				this.rightButton.setBounds((getWidth() - 75), 5, 65, 35);
				add(this.rightButton);
			}
		} else {
			if (rightButton != null)
				remove(rightButton);
			rightButton = null;
		}
		repaint();
	}
	
	/**
	 * Sets whether the title for the toolbar should be editable or not.
	 *  
	 * @param state whether or not the toolbar should be editable
	 */
	public void setTitleEditable(boolean state) {
		titleEditable = state;
	}
	
	/**
	 * Changes the title label to a text field so its text can be edited.
	 */
	public void enterEditMode() {
		if (titleEditable && ! editMode) {
			editField.setText(titleLabel.getText());
			remove(titleLabel);
			add(editField);
			repaint();
			editField.requestFocus();
			editField.setSelectionStart(0);
			editField.setSelectionEnd(editField.getText().length());
			editMode = true;
		}
	}
	
	/**
	 * Changes the edit field back into a static label containing whatever text was
	 * entered during edit mode. 
	 */
	public void exitEditMode() {
		if (editMode) {
			titleLabel.setText(editField.getText());
			remove(editField);
			add(titleLabel);
			repaint();
			editMode = false;
		}
	}
	
	public void mouseClicked(MouseEvent event) {
		if (event.getClickCount() == 2)
			enterEditMode();
		else
			exitEditMode();
	}

	public void mouseEntered(MouseEvent event) {
		
	}

	public void mouseExited(MouseEvent event) {
		
	}

	public void mousePressed(MouseEvent event) {
		
	}

	public void mouseReleased(MouseEvent event) {
		
	}

	public void keyPressed(KeyEvent event) {
		
	}

	public void keyReleased(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ENTER)
			exitEditMode();
	}

	public void keyTyped(KeyEvent event) {
		
	}
	
	/* Begin utility methods */
	
	/* Begin private methods */
	
}
