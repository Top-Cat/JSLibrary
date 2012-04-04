package js.uikit;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * UITableViewCell represents a single row in a UITableView. It contains a title and optional subtitle and accessory.
 * 
 * @author Josh
 *
 * @version 1.0.1
 */
public class UITableViewCell extends JPanel implements MouseListener, KeyListener {

	private JLabel titleLabel, subtitleLabel, accessoryLabel;
	private int cellStyle, accessory;
	private UITableView table;
	private JTextField editField;
	private boolean selectable, editMode;
	
	/** A simple style for a cell with a text label (black and left-aligned). **/
	public static final int DEFAULT_CELL_STYLE = 0;
	/** A style for a cell with a left-aligned label across the top and a left-aligned label below it
	 * in smaller gray text. **/
	public static final int SUBTITLE_CELL_STYLE = 1;
	
	/** The cell does not have any accessory view. **/
	public static final int NO_ACCESSORY = -1;
	/** The cell has an accessory control shaped like a regular chevron. It is intended as a disclosure indicator. **/
	public static final int DISCLOSURE_INDICATOR = 0;
	/** The cell has an accessory control that is a blue button with a chevron image as content. It is intended
	 * for configuration purposes. **/
	public static final int DETAIL_DISCLOSURE_BUTTON = 1;
	
	private static final Color BLACK = Color.black;
	private static final Color BLUE = new Color(2, 113, 237);
	private static final Color GREY = new Color(128, 128, 128);
	
	private static final Font TITLE_FONT = new Font("", Font.PLAIN, 16);
	private static final Font SUBTITLE_FONT = new Font("", Font.PLAIN, 12);
	private static final Font ACCESSORY_FONT = new Font("", Font.BOLD, 18);
	
	/**
	 * Creates a new UITableViewCell with the specified style, accessory and width. <code>Selcectable</code>
	 * determines whether clicking this cell only sends a MouseEvent, or whether it should stay 'selected'
	 * after the click by staying highlighted. The table which will hold this cell must also be passed.
	 * 
	 * @param cellStyle the style for this cell, either <code>DEFAULT_STYLE</code> or <code>SUBTITLE_STYLE</code>
	 * @param accessory the accessory for this cell, either <code>NO_ACCESSORY</code>, <code>DISCLOSURE_INDICATOR</code>
	 * 					or <code>DETAIL_DISCLOSURE_BUTTON<code> 
	 * @param width the width in pixels for the cell
	 * @param table the parent table which this cell will be added to
	 */
	public UITableViewCell(int cellStyle, int accessory, int width, boolean selectable, UITableView table) {
		this.cellStyle = cellStyle;
		this.accessory = accessory;
		this.table = table;
		this.selectable = selectable;
		
		setLayout(null);
		setSize(width, 50);
		setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, GREY));
		setBackground(Color.WHITE);
		
		titleLabel = new JLabel();
		titleLabel.setFont(TITLE_FONT);
		
		switch (this.cellStyle) {
		case DEFAULT_CELL_STYLE:
			titleLabel.setBounds(10, 15, getWidth() - 75, 20);
			titleLabel.setForeground(BLACK);
			add(titleLabel);
			editField = new JTextField();
			editField.setBounds(10, 15, getWidth() - 75, 20);
			editField.addKeyListener(this);
			titleLabel.setFont(TITLE_FONT);
			break;
		case SUBTITLE_CELL_STYLE:
			titleLabel.setBounds(10, 8, getWidth() - 75, 20);
			titleLabel.setForeground(BLACK);
			add(titleLabel);
			editField = new JTextField();
			editField.setBounds(10, 8, getWidth() - 75, 20);
			editField.addKeyListener(this);
			titleLabel.setFont(TITLE_FONT);
			subtitleLabel = new JLabel();
			subtitleLabel.setFont(SUBTITLE_FONT);
			subtitleLabel.setForeground(GREY);
			subtitleLabel.setBounds(10, 30, getWidth() - 75, 15);
			add(subtitleLabel);
			break;
		}
		
		if (this.accessory == DISCLOSURE_INDICATOR) {
			accessoryLabel = new JLabel(">", JLabel.RIGHT);
			accessoryLabel.setFont(ACCESSORY_FONT);
			accessoryLabel.setForeground(GREY);
			accessoryLabel.setBounds(getWidth() - 60, 18, 30, 15);
			add(accessoryLabel);
		} else if (this.accessory == DETAIL_DISCLOSURE_BUTTON) {
			accessoryLabel = new JLabel(new ImageIcon("images/DetailDisclosure.png"));
			accessoryLabel.setBounds(getWidth() - 55, 10, 30, 30);
			add(accessoryLabel);
		}
		
		addMouseListener(this);
		addMouseListener(this.table);
	}
	
	/**
	 * Returns the current title of the cell.
	 * 
	 * @return the title of the cell.
	 */
	public String getTitle() {
		return titleLabel.getText();
	}
	
	/**
	 * Returns the current subtitle of the cell.
	 * 
	 * @return the subtitle of the cell.
	 */
	public String getSubtitle() {
		return subtitleLabel.getText();
	}
	
	/**
	 * Sets the title of the cell.
	 * 
	 * @param title the new title to display in the cell
	 */
	public void setTitle(String title) {
		titleLabel.setText(title);
	}
	
	/**
	 * Sets the subtitle of the cell.
	 * 
	 * @param subtitle the new subtitle to display in the cell
	 */
	public void setSubtitle(String subtitle) {
		if (subtitleLabel != null)
			subtitleLabel.setText(subtitle);
	}
	
	/**
	 * Sets the width in pixels of the cell.
	 * 
	 * @param width the new width for the cell
	 */
	public void setWidth(int width) {
		setSize(width, 50);
		titleLabel.setSize(width - 37, 20);
		editField.setSize(width - 37, 20);
		if (subtitleLabel != null)
			subtitleLabel.setSize(width - 37, 15);
		if (accessoryLabel != null)
			accessoryLabel.setLocation(width - 32, 18);
		repaint();
	}
	
	/**
	 * Sets whether the cell is selectable (the highlight does not disappear after clicking).
	 * 
	 * @param state whether or not the cell should be selectable
	 */
	public void setSelelctable(boolean state) {
		selectable = state;
	}
	
	/**
	 * Determines whether the cell is currently selectable.
	 * 
	 * @return <code>true</code> if the cell is selectable, otherwise <code>false</code>
	 */
	public boolean isSelectable() {
		return selectable;
	}
	
	/**
	 * Deselects this cell by removing the blue highlight.
	 */
	public void deselect() {
		titleLabel.setForeground(BLACK);
		if (subtitleLabel != null)
			subtitleLabel.setForeground(GREY);
		if (accessoryLabel != null)
			accessoryLabel.setForeground(GREY);
		setBackground(Color.white);
	}
	
	/**
	 * Enables the title field for this cell to be edited using a JTextField.
	 */
	public void enterEditMode() {
		editField.setText(titleLabel.getText());
		remove(titleLabel);
		add(editField);
		repaint();
		editField.requestFocus();
		editField.setSelectionStart(0);
		editField.setSelectionEnd(editField.getText().length());
		editMode = true;
	}
	
	/**
	 * Exits edit mode and changes the title field to read whatever was entered
	 * during edit mode.
	 */
	public void exitEditMode() {
		titleLabel.setText(editField.getText());
		remove(editField);
		add(titleLabel);
		repaint();
		editMode = false;
	}

	public void mouseClicked(MouseEvent event) {
		if (selectable) {
			titleLabel.setForeground(Color.WHITE);
			if (subtitleLabel != null)
				subtitleLabel.setForeground(Color.WHITE);
			if (accessoryLabel != null)
				accessoryLabel.setForeground(Color.WHITE);
			setBackground(BLUE);
		}
		if (!editMode && event.getClickCount() == 2)
			enterEditMode();
		else if (editMode)
			exitEditMode();
	}

	public void mouseEntered(MouseEvent event) {
		
	}

	public void mouseExited(MouseEvent event) {
		if (!selectable) {
			titleLabel.setForeground(BLACK);
			if (subtitleLabel != null)
				subtitleLabel.setForeground(GREY);
			if (accessoryLabel != null)
				accessoryLabel.setForeground(GREY);
			setBackground(Color.white);
		}
	}

	public void mousePressed(MouseEvent event) {
		titleLabel.setForeground(Color.WHITE);
		if (subtitleLabel != null)
			subtitleLabel.setForeground(Color.WHITE);
		if (accessoryLabel != null)
			accessoryLabel.setForeground(Color.WHITE);
		setBackground(BLUE);
	}

	public void mouseReleased(MouseEvent event) {
		titleLabel.setForeground(BLACK);
		if (subtitleLabel != null)
			subtitleLabel.setForeground(GREY);
		if (accessoryLabel != null)
			accessoryLabel.setForeground(GREY);
		setBackground(Color.white);
	}

	public void keyPressed(KeyEvent event) {
		
	}

	public void keyReleased(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			if (editMode)
				exitEditMode();
		}
		
	}

	public void keyTyped(KeyEvent event) {
		
	}
	
}
