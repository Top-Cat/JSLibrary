package js;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.JPanel;

/**
 * GridPanel is a type of JPanel. It provides a grid of subpanels which components can be added to.
 * The effect is the same as a JPanel with a {@link GridLayout}, but this solution allows empty cells.
 * 
 * @author Josh Sunshine
 * 
 * @version 1.0.1
 *
 */
public class JSGridPanel extends JPanel {
	
	private static final long serialVersionUID = 4016349207149048480L;
	boolean[][] filled;			// Used to determine which cells are already filled.
	JPanel[][] panelHolder;		// Holds the subpanels for each cell.
	int rowCount;
	int columnCount;
	GridLayout grid;			// The GridLayout of the main panel.
	
	/**
	 * Creates a new GridPanel with the specified number of rows and columns.
	 * 
	 * @param rows the number of rows the grid should have.
	 * @param columns the number of columns the grid should have.
	 */
	public JSGridPanel(int rows, int columns) {
		grid = new GridLayout(rows, columns);
		this.setLayout(grid);
		panelHolder = new JPanel[rows][columns];	// Add enough memory to the array to fill all cells of the grid.
		filled = new boolean[rows][columns];		// As above.
		
		rowCount = rows;
		columnCount = columns;
			
		for (int r = 0; r < rows; r ++) {			// For each row of the grid.
			for (int c = 0; c < columns; c ++) {		// For each column of that row.
				panelHolder[r][c] = new JPanel(new BorderLayout());		// Create a JPanel and store it in the 2D array.
				this.add(panelHolder[r][c]);							// Add the new panel to the GridLayout.
			}
		}
	}
	
	/**
	 * Adds a component to the first empty cell in the panel.
	 * 
	 * @param comp the component that should be added to the panel.
	 * @return <code>true</code> if the component is successfully added; otherwise <code>false</code>.
	 */
	public boolean addComponent(Component comp) {
		boolean empty = false;
		boolean broken = false;
		int c = 0;
		int r = 0;
		while (! empty && ! broken) {
			if (! filled[r][c])
				empty = true;
			else {
				if (c < (columnCount - 1))	// Check we're not on the last column.
					c ++;
				else {
					c = 0;
					if (r < (rowCount - 1))	// Check we're not on the last row.
						r ++;
					else {	// If we get to this point, we've been through every row and every column,
							//	and none of the cells were empty.
						System.out.println("Could not add component; No empty cells.");
						broken = true;
					}
				}
			}
		}
		if (! broken) {
			filled[r][c] = true;
			panelHolder[r][c].add(comp);
			return true;
		} 
		else
			return false;
	}
	
	/**
	 * Adds a component to a specified cell of the grid.
	 * 
	 * @param comp the component to be added to the panel.
	 * @param row the row of the cell to add the component to.
	 * @param column the column of the cell to add the component to.
	 * @return <code>true</code> if the component is successfully added; otherwise <code>false</code>.
	 */
	public boolean addComponent(Component comp, int row, int column) {
		if (! filled[row][column]) {
			panelHolder[row][column].add(comp);
			filled[row][column] = true;
			return true;
		}
		else {
			System.out.println("Could not add component; Cell already filled.");
			return false;
		}
	}
	
	/**
	 * Sets the vertical gap between cells of the grid.
	 * 
	 * @param gap the size of the gap to add in pixels.
	 */
	public void setVGap(int gap) {
		grid.setVgap(gap);
	}
	
	
	/**
	 * Sets the horizontal gap between cells of the grid.
	 * 
	 * @param gap the size of the gap to add in pixels.
	 */
	public void setHGap(int gap) {
		grid.setHgap(gap);
	}
	
	
	/**
	 * Sets the background colour for every cell of the grid.
	 * 
	 * @param color the colour to set the background to.
	 */
	public void setBackgroundColor(Color color) {
		this.setBackground(color);
		for (int r = 0; r < rowCount; r ++) {
			for (int c = 0; c < columnCount; c ++) {
				panelHolder[r][c].setBackground(color);
			}
		}
	}
	
	/**
	 * Sets the background colour for a specific cell of the grid.
	 * 
	 * @param color the colour to set the background to.
	 * @param row the row of the cell to set the background of.
	 * @param column the column of the cell to set the background of.
	 */
	public void setBackgroundColor(Color color, int row, int column) {
		panelHolder[row][column].setBackground(color);
	}
	
	/**
	 * Determines whether there are any empty cells left in this panel.
	 * 
	 * @return <code>true</code> if there are empty cells remaining or <false> if all cells are filled.
	 */
	public boolean moreEmptyCells() {
		boolean empty = false;
		for (int row = 0; row < rowCount; row ++) {
			for (int col = 0; col < columnCount; col ++) {
				if (! filled[row][col])
					empty = true;
			}
		}
		return empty;
	}
}
