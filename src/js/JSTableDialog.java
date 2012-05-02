package js;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;


/**
 * JSTableDialog is a class which allows a {@link JSTable} to be placed into a dialog along with a customisable set of <code>JButton</code>s.
 * The dialog is modal, which allows it to wait for user input, such as selecting a row of the table, before continuing. 
 * <code>ActionListener</code>s, <code>KeyListener</code>s and <code>MouseListener</code>s for the table are handled automatically. The dialog's size
 * changes dynamically when the row count or row height of the table changes so that as many rows as possible can be displayed at once.
 * 
 * @author Josh
 * @version 1.0.3
 *
 */
public class JSTableDialog extends JSDialog implements ActionListener, KeyListener, MouseListener, ContainerListener {

	private static final long serialVersionUID = -8876337760155225997L;
	private JSTable table;
	private JPanel buttonPanel;
	private JButton[] buttons;
	
	private int selectedButton = -1;
	private int selectedRow = -1;
	
	/* *** Begin constructors *** */
	
	/**
	 * Creates a basic JSTableDialog with the default buttons, a 'done' button and a 'cancel' button.
	 * 
	 * @param title the title to display above the table.
	 * @param headings the headings for the table.
	 * @param rows the initial number of rows for the table.
	 */
	public JSTableDialog(String title, String[] headings, int rows) {
		this(title, headings, getDefaultButtonArray(), rows);
	}
	
	/**
	 * Creates a JSTableDialog with a 'cancel' button and a customisable 'done' button.
	 * 
	 * @param title the title to display above the table.
	 * @param headings the headings for the table.
	 * @param doneButtonText the text to display on the right-hand button.
	 * @param rows the initial number of rows for the table.
	 */
	public JSTableDialog(String title, String[] headings, String doneButtonText, int rows) {
		this(title, headings, getButtonArrayWithCustomDone(doneButtonText), rows);
	}
	
	/**
	 * Creates a JSTableDialog with a fully customisable set of buttons. There is no limit to the number of buttons
	 * that can be used, but a limit of around five is recommended.
	 * 
	 * @param title the title to display above the table.
	 * @param headings the headings for the table.
	 * @param buttonLabels an array of strings to be used for button labels.
	 * @param rows the initial number of rows for the table.
	 */
	public JSTableDialog(String title, String[] headings, String[] buttonLabels, int rows) {
		table = new JSTable(title, headings, rows);
		table.addMouseListener(this);
		table.setEditable(false);
		
		buttonPanel = new JPanel(new GridLayout(1, 2));
		buttonPanel.setPreferredSize(new Dimension(600, 50));
		
		buttons = new JButton[buttonLabels.length];
		for (int b = 0; b < buttons.length; b ++) {
			buttons[b] = new JButton(buttonLabels[b]);
			buttons[b].setPreferredSize(new Dimension(600 / buttons.length, 50));
			buttons[b].addActionListener(this);
			buttonPanel.add(buttons[b]);
		}
		
		setLayout(new BorderLayout());
		add(BorderLayout.SOUTH, buttonPanel);
		add(BorderLayout.CENTER, table);
		
		addKeyAndContainerListenerRecursively(this);
		setModal(true);
	}
	
	/* *** Begin getters *** */
	
	/**
	 * Returns the <code>JSTable</code> from this dialog for customisation.
	 * 
	 * @return the JSTable from this dialog.
	 */
	public JSTable getTable() {
		return this.table;
	}
	
	/**
	 * Determines the button that was selected when the dialog was displayed.
	 * 
	 * @return the index of the selected button.
	 */
	public int getSelectedButton() {
		return this.selectedButton;
	}
	
	/**
	 * Determines the row of the table that was selected when the dialog was displayed.
	 * 
	 * @return the index of the selected row.
	 */
	public int getSelectedRow() {
		return this.selectedRow;
	}
	
	/* *** Begin setters *** */
	
	/**
	 * Sets the background color for the buttons underneath the table.
	 * 
	 * @param color the color to use as the buttons' background.
	 */
	public void setButtonBackground(Color color) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setBackground(color);
		}
	}
	
	/**
	 * Sets the font to use for the buttons underneath the table.
	 * 
	 * @param font the font to use for the buttons.
	 */
	public void setButtonFont(Font font) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setFont(font);
		}
	}
	
	/**
	 * Sets the foreground (text) color for the buttons underneath the table.
	 * 
	 * @param color the color to use as the buttons' foreground.
	 */
	public void setButtonForeground(Color color) {
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].setForeground(color);
		}
	}
	
	/**
	 * Sets the height, in pixels, of the rows of the table and resizes the dialog accordingly.
	 * 
	 * @param height the new height for the table's rows in pixels.
	 */
	public void setRowHeight(int height) {
		table.setRowHeight(height);
		resize();
	}
	
	/**
	 * Sets the font to use for the cells of the table and resizes the dialog if the font size has changed.
	 * 
	 * @param f the new font to use in the cells of the table.
	 */
	public void setTableFont(Font f) {
		table.setTableFont(f);
		resize();
	}
	
	public void setVisible(boolean b) {
		resize();
		super.setVisible(b);
	}
	
	/* *** Begin public methods *** */
	
	/**
	 * Adds a new blank row to the table and resizes the dialog accordingly. 
	 */
	public void addRow() {
		table.addRow();
		resize();
	}
	
	/**
	 *  Removes the key listener from the dialog so that the mouse must be used to select rows and buttons.
	 */
	public void removeKeyListener() {
		removeKeyAndContainerListenerRecursively(this);
	}
	
	/* *** Begin private methods *** */
	
	private static String[] getDefaultButtonArray() {
		String[] array = {"Cancel", "Done"};
		return array;
	}
	
	private static String[] getButtonArrayWithCustomDone(String doneText) {
		String[] array = {"Cancel", doneText};
		return array;
	}
	
	private void resize() {
		if (110 + (table.getRowCount() + 1.5) * table.getRowHeight() <= 650)
			setSize(600, 110 + (int) (((table.getRowCount() + 1.5) * table.getRowHeight())));
		else
			setSize(600, 650);
	}
	
	private void addKeyAndContainerListenerRecursively(Component c) {
         c.addKeyListener(this);
         if(c instanceof Container) {
              Container cont = (Container) c;
              cont.addContainerListener(this);
              Component[] children = cont.getComponents();
              for (int i = 0; i < children.length; i++) {
                   addKeyAndContainerListenerRecursively(children[i]);
              }
         }
    }
	
	private void removeKeyAndContainerListenerRecursively(Component c) {
        c.removeKeyListener(this);
        if(c instanceof Container) {
             Container cont = (Container) c;
             cont.removeContainerListener(this);
             Component[] children = cont.getComponents();
             for (int i = 0; i < children.length; i++) {
                  removeKeyAndContainerListenerRecursively(children[i]);
             }
        }
    }
	
	/* *** Begin Listener methods *** */
	
	public void mouseClicked(MouseEvent event) {
		if (event.getSource() == table && event.getClickCount() == 2) {
			this.selectedButton = buttons.length - 1;
			this.selectedRow = table.getSelectedRow();
			setVisible(false);
		}
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
		if (event.getKeyCode() == KeyEvent.VK_ENTER) {
			this.selectedButton = buttons.length - 1;
			this.selectedRow = table.getSelectedRow();
			setVisible(false);
		} else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.selectedButton = -1;
			this.selectedRow = -1;
			setVisible(false);
		}
	}
	
	public void keyTyped(KeyEvent event) {
		
	}
	
	public void actionPerformed(ActionEvent event) {
		JButton clickedButton = (JButton) event.getSource();
		for (int i = 0; i < buttons.length; i ++) {
			if (clickedButton == buttons[i]) {
				this.selectedButton = i;
				this.selectedRow = table.getSelectedRow();
				setVisible(false);
			}
		}
	}
	
	public void componentAdded(ContainerEvent e) {
        addKeyAndContainerListenerRecursively(e.getChild());
    }

	public void componentRemoved(ContainerEvent e) {
		
	}
}
