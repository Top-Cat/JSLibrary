package js.uikit;

import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

/**
 * UITableView is a list of data. Whilst called a table, there is only one column, but rows can have more than one
 * data item, making this essentially a 1.5 dimension table. Each cell can also display an arrow-like accessory, 
 * usually used to signify that more data is available by clicking that row.
 * 
 * @author Josh
 * 
 * @version 1.0.1
 *
 */
public class UITableView extends JScrollPane implements MouseListener {

	private Vector<UITableViewCell> cells;
	private JPanel table;
	private int cellStyle;
	private int selectedRow; 
	
	/* Begin constructors */
	
	/**
	 * Creates a new UITableView with the specified cell style.
	 * 
	 * @param cellStyle the cell style to use for this table, either <code>UITableViewCell.DEFAULT_CELL_STYLE</code>
	 * 					or <code>UITableViewCell.SUBTITLE_CELL_STYLE</code>.
	 */
	public UITableView(int cellStyle) {
		this.cellStyle = cellStyle;
		
		cells = new Vector<UITableViewCell>();
		
		table = new JPanel();
		table.setLayout(null);
		table.setPreferredSize(new Dimension(getWidth() - 20, 100));
		this.setViewportView(table);
		this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		this.getVerticalScrollBar().setUnitIncrement(10);
	}
	
	/**
	 * Creates a new UITableView with the default cell style.
	 */
	public UITableView() {
		this(UITableViewCell.DEFAULT_CELL_STYLE);
	}
	
	/* Begin getters */
	
	/**
	 * Returns the index of the most recently clicked cell in the table.
	 * 
	 * @return the index of the last cell clicked
	 */
	public int getSelectedRow() {
		return selectedRow;
	}
	
	/**
	 * Returns the most recently clicked cell in the table as a UITableViewCell.
	 * 
	 * @return the last cell clicked
	 */
	public UITableViewCell getSelectedCell() {
		return cells.elementAt(selectedRow);
	}
	
	/**
	 * Returns the cell at the given index as a UITableViewCell.
	 * 
	 * @param index the index of the cell to return
	 * @return the cell at the specified index
	 */
	public UITableViewCell getCellAtIndex(int index) {
		return cells.elementAt(index);
	}
	
	/**
	 * Returns the current number of cells in the table
	 * 
	 * @return the current number of cells in the table
	 */
	public int getRowCount() {
		return cells.size();
	}
	
	/* Begin setters */
	
	public void setSize(int width, int height) {
		table.setPreferredSize(new Dimension(width - 20, 50 * cells.size()));
		super.setSize(width, height);
	}
	
	public void setBounds(int x, int y, int width, int height) {
	//	table.setPreferredSize(new Dimension(width, 100 * cells.size()));
		for (UITableViewCell cell : cells) {
			cell.setWidth(width - 20);
		}
		super.setBounds(x, y, width, height);
	}
	
	/* Begin utility methods */
	
	/**
	 * Adds a new cell to the bottom of the table with no accessory and the specified title.
	 * 
	 * @param title the title to use for the new cell
	 */
	public void addRow(String title) {
		addRow(title, "", -1, false);
	}
	
	/**
	 * Adds a new cell to the bottom of the table with the specified accessory title.
	 * 
	 * @param title the title to use for the new cell
	 * @param accessory the accessory to use for the new cell, either <code>UITableViewCell.DISCLOSURE_INDICATOR</code>
	 * 					or <code>UITableViewCell.DETAIL_DISCLOSURE_BUTTON</code>.
	 */
	public void addRow(String title, int accessory) {
		addRow(title, "", accessory, false);
	}
	
	/**
	 * Adds a new cell to the bottom of the table with no accessory and the specified title and subtitle. If the cells
	 * in the table are using the default style, the subtitle is not displayed.
	 * 
	 * @param title the title to use for the new cell
	 * @param subtitle the subtitle to use for the new cell
	 */
	public void addRow(String title, String subtitle) {
		addRow(title, subtitle, -1, false);
	}
	
	/**
	 * Adds a new cell to the bottom of the table with the specified accessory, title and subtitle. If the cells
	 * in the table are using the default style, the subtitle is not displayed. 
	 * 
	 * @param title the title to use for the new cell
	 * @param subtitle the subtitle to use for the new cell
	 * @param accessory the accessory to use for the new cell, either <code>UITableViewCell.DISCLOSURE_INDICATOR</code>
	 * 					or <code>UITableViewCell.DETAIL_DISCLOSURE_BUTTON</code>.
	 * @param selectable whether the new cell should be 'selectable' or not
	 */
	public void addRow(String title, String subtitle, int accessory, boolean selectable) {
		UITableViewCell cell = new UITableViewCell(cellStyle, accessory, getWidth(), selectable, this);
		cell.setTitle(title);
		cell.setSubtitle(subtitle);
		cell.setBounds(0, 50 * cells.size(), getWidth() - 20, 50); 
		cells.add(cell);
		table.setPreferredSize(new Dimension(getWidth() - 20, 50 * cells.size()));
		table.add(cell);
		table.repaint();
	}
	
	/**
	 * Deletes the specified row of the table.
	 * 
	 * @param row the index of the row to delete
	 */
	public void deleteRow(int row) {
		UITableViewCell cell = getCellAtIndex(row);
		cells.remove(cell);
		table.remove(cell);
		for (int i = 0; i < cells.size(); i ++) {
			UITableViewCell c = getCellAtIndex(i);
			c.setBounds(0, 50 * i, getWidth() - 20, 50); 
		}
		table.repaint();
	}
	
	/* Begin listener methods */

	public void mouseClicked(MouseEvent e) {
		for (int i = 0; i < cells.size(); i ++) {
			if (e.getSource() == cells.elementAt(i)) {
				selectedRow = i;
			} else {
				if (cells.elementAt(i).isSelectable())
					cells.elementAt(i).deselect();
			}
		}
		dispatchEvent(e);
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}
	
	/* Begin private methods */
	
}
