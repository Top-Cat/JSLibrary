package js;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

/**
 * JSTable is a solution for creating a simple {@link JTable}. The table is placed into a panel along with a title label, and can also be created in a separate window.
 * 
 * @author Josh Sunshine
 * 
 * @version 1.0
 *
 */
public class JSTable extends JPanel implements MouseListener {

   private static final long serialVersionUID = 1L;
   private static String version = "JSTableDialog Version 1.0";
   private JLabel titleLabel;
   private JTable table;
   private JScrollPane scrollPane = new JScrollPane();
   private int rows;
   private int columns;
   private int currentSortedColumn = -100;
   private boolean ascending = true;
   public TableModel tableModel;
   
   /**
    * Create a new JSTable.
    * 
    * @param title the text for the title label above the table.
    * @param headings an array of strings representing the table's column headings.
    * @param rows the initial number of rows in the table. 
    */
   public JSTable(String title, String[] headings, int rows) {
      setSize(500,500);
      setLayout(new BorderLayout());
//    setTitle(title);
      this.titleLabel = new JLabel(title, SwingConstants.CENTER);
      this.titleLabel.setFont(new Font("Arial", Font.PLAIN, 24));
      this.titleLabel.setOpaque(true);
      this.titleLabel.setBackground(Color.black);
      this.titleLabel.setForeground(Color.white);
      add("North", this.titleLabel);
      
      this.tableModel = new TableModel(headings, rows);
      this.table = new JTable(tableModel);
      
      this.table.setGridColor(new Color(230,230,230));
      Font tableFont = this.table.getFont();
      int size = tableFont.getSize();
      this.table.setRowHeight(size + 5);
      
      this.rows = rows;
      this.columns = this.table.getColumnCount();
      this.scrollPane.setViewportView(table);
      
      JTableHeader header = table.getTableHeader();
      header.addMouseListener(this);
      add("Center", scrollPane);
   }
   
   /**
    * Create a new {@link JDialog} containing the table, title and buttons.
    * Do not call this method if you plan on using the table as a panel.
    */
   public JDialog makeDialog() {
	   JDialog dialog = new JDialog();
	   dialog.setSize(500,500);
	   dialog.setLocationRelativeTo(null);
	   dialog.setLayout(new BorderLayout());
	   dialog.add("North", this.titleLabel);
	   dialog.add("Center", this.scrollPane);
	   return dialog;
   }
   
   /**
    * Create a new {@link JDialog} containing the table, title and buttons. Up to two other panels can also be added,
    * one above and one below the table. Do not call this method if you plan on using the table as a panel.
    * 
    * @param northPanel the panel to be added above the table.
    * @param southPanel the panel to be added below the table.
    * @return a centered JDialog containing the table and any extra panels. 
    */
   public JDialog makeDialog(JPanel northPanel, JPanel southPanel) {
	   JDialog dialog = new JDialog();
	   JPanel panel = new JPanel();
	   panel.setLayout(new BorderLayout());
	   panel.add("North", this.titleLabel);
	   panel.add("Center", this.scrollPane);
	   dialog.setSize(500,500);
	   dialog.setLayout(new BorderLayout());
	   dialog.setLocationRelativeTo(null);
	   if (northPanel != null)
		   dialog.add("North", northPanel);
	   if (southPanel != null)
		   dialog.add("South", southPanel);
	   dialog.add("Center", panel);
	   return dialog;
   }
   
   /**
    * Sets the width in pixels of a specific column.
    * 
    * @param column the column whose width is to be changed.
    * @param width the new width, in pixels, of the column.
    */
   public void setColumnWidth(int column, int width) {
	   TableColumnModel tcm = this.table.getColumnModel();
       TableColumn tc = tcm.getColumn(column);
       tc.setPreferredWidth(width);
   }
   
   /**
    * Sets the height in pixels of all rows in the table.
    * 
    * @param height the new height, in pixels, of the rows.
    */
   public void setRowHeight(int height) {
	   this.table.setRowHeight(height);
   }
   
   /**
    * Gets the currently selected row of the table.
    * 
    * @return an integer relating to the selected row's index number.
    */
   public int getSelectedRow() {
	   int row = this.table.getSelectedRow();
	   return row;
   }
   
   /**
    * Sets the specified string value in the specified cell.
    * 
    * @param value the string to be added to the table.
    * @param row the row of the cell to add the object to.
    * @param column the column of the cell to add the object to.
    * @return <code>true</code> if the value is successfully set; otherwise <code>false</code>.
    */
   public boolean setValueAt(String value, int row, int column) {
      if ((column >= 0) && (column < this.tableModel.getColumnCount()) && (row >= 0) && (row < this.tableModel.getRowCount())) {
         Class cell = this.table.getValueAt(row, column).getClass();
         if (cell.isInstance(new String())) {
           this.table.setValueAt(value, row, column);
           return true;
         }
         String classType = cell.toString();
         tableError("Error assigning value at cell " + row + ", " + column + ".\n"
              + "Trying to assign a String to a cell with class " + classType.substring(16, classType.length()) + ".");
       }
       return false;
   }
   
   /**
    * Sets the specified object in the specified cell. Use this method for adding objects other than
    * String, Boolean, Double or Integer to the table.
    * 
    * @param value the object to be added to the table.
    * @param row the row of the cell to add the object to.
    * @param column the column of the cell to add the object to.
    * @return <code>true</code> if the value is successfully set; otherwise <code>false</code>.
    */
   public boolean setObjectAt(Object value, int row, int column) {
	      if ((column >= 0) && (column < this.tableModel.getColumnCount()) && (row >= 0) && (row < this.tableModel.getRowCount())) {
	         Class cell = this.table.getValueAt(row, column).getClass();
	    //     if (cell.isInstance(new String())) {
	           this.table.setValueAt(value, row, column);
	           return true;
	    //     }
	    //     String classType = cell.toString();
	    //     tableError("Error assigning value at cell " + row + ", " + column + ".\n"
	    //          + "Trying to assign a "+ value.getClass().toString().substring(16, classType.length()) +" to a cell with class " + classType.substring(16, classType.length()) + ".");
	       }
	       return false;
	   }
   
   /**
    * Sets the specified integer value in the specified cell.
    * 
    * @param value the integer value to be added to the table.
    * @param row the row of the cell to add the object to.
    * @param column the column of the cell to add the object to.
    * @return <code>true</code> if the value is successfully set; otherwise <code>false</code>.
    */
   public boolean setValueAt(int value, int row, int column) {
     if ((column >= 0) && (column < this.tableModel.getColumnCount()) && (row >= 0) && (row < this.tableModel.getRowCount())) {
       Class cell = this.table.getValueAt(row, column).getClass();
       if (cell.isInstance(new Integer(0))) {
         this.table.setValueAt(new Integer(value), row, column);
         return true;
       }
       String classType = cell.toString();
       tableError("Error assigning value at cell " + row + ", " + column + ".\n"
             + "Trying to assign an Integer to a cell with class " + classType.substring(16, classType.length()) + ".");
     }

     return false;
   }

   /**
    * Sets the specified double value in the specified cell.
    * 
    * @param value the double value to be added to the table.
    * @param row the row of the cell to add the object to.
    * @param column the column of the cell to add the object to.
    * @return <code>true</code> if the value is successfully set; otherwise <code>false</code>.
    */
   public boolean setValueAt(double value, int row, int column) {
     if ((column >= 0) && (column < this.tableModel.getColumnCount()) && (row >= 0) && (row < this.tableModel.getRowCount())) {
       Class cell = this.table.getValueAt(row, column).getClass();
       if (cell.isInstance(new Double(0.0D))) {
         this.table.setValueAt(new Double(value), row, column);
         return true;
       }
       String classType = cell.toString();
       tableError("Error assigning value at cell " + row + ", " + column + ".\n"
             + "Trying to assign a Double to a cell with class " + classType.substring(16, classType.length()) + ".");
     }

     return false;
   }

   /**
    * Sets the specified boolean value in the specified cell.
    * 
    * @param value the boolean value to be added to the table.
    * @param row the row of the cell to add the object to.
    * @param column the column of the cell to add the object to.
    * @return <code>true</code> if the value is successfully set; otherwise <code>false</code>.
    */
   public boolean setValueAt(boolean value, int row, int column) {
     if ((column >= 0) && (column < this.tableModel.getColumnCount()) && (row >= 0) && (row < this.tableModel.getRowCount())) {
       Class cell = this.table.getValueAt(row, column).getClass();
       if (cell.isInstance(new Boolean(true))) {
         this.table.setValueAt(new Boolean(value), row, column);
         return true;
       }
       String classType = cell.toString();
       tableError("Error assigning value at cell " + row + ", " + column + ".\n"
             + "Trying to assign a Boolean to a cell with class " + classType.substring(16, classType.length()) + ".");
     }

     return false;
   }
   
   /**
    * Gets the value of the specified cell, converted to a String. 
    * 
    * @param row the row of the cell to get the value from.
    * @param column the column of the to get the value from.
    * @return a string containing the value of the cell.
    */
   public String getStringValueAt(int row, int column)
   {
     if ((column >= 0) && (column < this.tableModel.getColumnCount()) && (row >= 0) && (row < this.tableModel.getRowCount()))
     {
       Object result = this.table.getValueAt(row, column);
       if (result == null) {
         return "";
       }

       return result.toString();
     }

     return "";
   }
   
   /**
    * Gets the object in the specified cell.
    * 
    * @param row the row of the cell to get the value from.
    * @param column the column of the to get the value from.
    * @return the object from the cell.
    */
   public Object getValueAt(int row, int column)
   {
     if ((column >= 0) && (column < this.tableModel.getColumnCount()) && (row >= 0) && (row < this.tableModel.getRowCount()))
     {
       Object result = this.table.getValueAt(row, column);
       if (result == null) {
         return null;
       }

       return result;
     }

     return null;
   }
   
   /**
    * Sets whether the cells in the table should be editable once the table is displayed.
    * 
    * @param state whether or not the table should be editable.
    */
   public void setEditable(Boolean state) {
	   if (state == true) {
		   for (int col = 0; col < this.tableModel.getColumnCount(); col++) {
	            for (int row = 0; row < rows; row++) {
	               this.tableModel.editable[row][col] = true;
	            }
	         }
	   }
	   else {
		   for (int col = 0; col < this.tableModel.getColumnCount(); col++) {
	            for (int row = 0; row < rows; row++) {
	               this.tableModel.editable[row][col] = false;
	            }
	         }
	   }
   }
   
   private void tableError(String message) {
      JOptionPane.showMessageDialog(null, message, "Error with Table", JOptionPane.WARNING_MESSAGE);
   }
   
   /**
    * Sets the horizontal alignment of the text in the specified row to centered.
    * 
    * @param column the column to center the text of.
    */
   public void centerColumn(int column) {
      TableColumn col = this.table.getColumnModel().getColumn(column);  
      DefaultTableCellRenderer dtcr = new DefaultTableCellRenderer();    
      dtcr.setHorizontalAlignment(SwingConstants.CENTER);  
      col.setCellRenderer(dtcr);
   }
   
   /**
    * Sets the horizontal alignment of the table header text to centered.
    */
   public void centerHeaders() {
      TableCellRenderer renderer = this.table.getTableHeader().getDefaultRenderer();
      JLabel label = (JLabel)renderer;
      label.setHorizontalAlignment(JLabel.CENTER);
   }
   
   /**
    * Sorts the table into ascending order by the values in the specified column.
    * If this method is called on the same column twice consecutively, the sort order is changed to descending.
    * 
    * @param column
    */
   public void sortByColumn(int column) {
	   for (int i = 0; i < this.tableModel.getColumnCount(); i ++) {
		   TableColumnModel tcm = this.table.getColumnModel();
	       TableColumn tc = tcm.getColumn(i);
	       String heading = tc.getHeaderValue().toString();
	       if (heading.contains("  ▲") || heading.contains("  ▼")) {
	    	   heading = heading.substring(0, heading.length()-3);
	       }
	       setColumnHeading(i, heading);
	   }
	   
	   TableColumnModel tcm = this.table.getColumnModel();
       TableColumn tc = tcm.getColumn(column);
       String heading = tc.getHeaderValue().toString();
       heading += "  ▲";
       
	   if (this.currentSortedColumn == column && ascending) {
			   ascending = false;
			   tcm = this.table.getColumnModel();
		       tc = tcm.getColumn(column);
		       heading = tc.getHeaderValue().toString();
		       heading += "  ▼";
		   
	   }
	   else if (this.currentSortedColumn == column && !ascending) {
			   ascending = true;
			   tcm = this.table.getColumnModel();
		       tc = tcm.getColumn(column);
		       heading = tc.getHeaderValue().toString();
		       heading += "  ▲";
	   }
	   else {
		   ascending = true;
	   }
       
       setColumnHeading(column, heading);
       
       this.currentSortedColumn = column;
	   
	   if (!(this.tableModel.getColumnClass(column).isInstance(new Double(0.0D)) || this.tableModel.getColumnClass(column).isInstance(new Integer(0)) || 
			   (getStringValueAt(0, column).length() == 8 && getStringValueAt(0, column).substring(2, 3).equals(":") && getStringValueAt(0, column).substring(5, 6).equals(":")) ||
			   (getStringValueAt(0, column).length() == 8 && getStringValueAt(0, column).substring(2, 3).equals("/") && getStringValueAt(0, column).substring(5, 6).equals("/")))) {
		   boolean finished;
		   do {
			   finished = true;
			   for (int i = 0; i < this.tableModel.getRowCount()-1; i ++) {
				   if (ascending) {
					   if (getValueAt(i, column).toString().compareToIgnoreCase(getValueAt(i+1, column).toString()) > 0) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
				   else {
					   if (getValueAt(i, column).toString().compareToIgnoreCase(getValueAt(i+1, column).toString()) < 0) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
			   }
		   } while (!finished);
	   }
	   else if (this.tableModel.getColumnClass(column).isInstance(new Double(0.0D)) || this.tableModel.getColumnClass(column).isInstance(new Integer(0))) {
		   boolean finished;
		   do {
			   finished = true;
			   for (int i = 0; i < this.tableModel.getRowCount()-1; i ++) {
				   if (ascending) {
					   if (Double.parseDouble(getValueAt(i, column).toString()) > Double.parseDouble(getValueAt(i+1, column).toString())) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
				   else {
					   if (Double.parseDouble(getValueAt(i, column).toString()) < Double.parseDouble(getValueAt(i+1, column).toString())) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
			   }
		   } while (!finished);
	   }
	   else {
		   boolean finished;
		   do {
			   finished = true;
			   for (int i = 0; i < this.tableModel.getRowCount()-1; i ++) {
				   if (ascending) {
					   if (getStringValueAt(i, column).substring(6, 8).compareTo(getStringValueAt(i+1, column).substring(6, 8)) > 0) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
				   else {
					   if (getStringValueAt(i, column).substring(6, 8).compareTo(getStringValueAt(i+1, column).substring(6, 8)) < 0) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
			   }
		   } while (!finished);
		   
		 
		   do {
			   finished = true;
			   for (int i = 0; i < this.tableModel.getRowCount()-1; i ++) {
				   if (ascending) {
					   if (getStringValueAt(i, column).substring(3, 5).compareTo(getStringValueAt(i+1, column).substring(3, 5)) > 0 
							   && getStringValueAt(i, column).substring(6, 8).equals(getStringValueAt(i+1, column).substring(6, 8))) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
				   else {
					   if (getStringValueAt(i, column).substring(3, 5).compareTo(getStringValueAt(i+1, column).substring(3, 5)) < 0 
							   && getStringValueAt(i, column).substring(6, 8).equals(getStringValueAt(i+1, column).substring(6, 8))) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
			   }
		   } while (!finished);
		   
		   
		   do {
			   finished = true;
			   for (int i = 0; i < this.tableModel.getRowCount()-1; i ++) {
				   if (ascending) {
					   if (getStringValueAt(i, column).substring(0, 2).compareTo(getStringValueAt(i+1, column).substring(0, 2)) > 0 
							   && getStringValueAt(i, column).substring(6, 8).equals(getStringValueAt(i+1, column).substring(6, 8))
							   && getStringValueAt(i, column).substring(3, 5).equals(getStringValueAt(i+1, column).substring(3, 5))) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
				   else {
					   if (getStringValueAt(i, column).substring(0, 2).compareTo(getStringValueAt(i+1, column).substring(0, 2)) < 0 
							   && getStringValueAt(i, column).substring(6, 8).equals(getStringValueAt(i+1, column).substring(6, 8))
							   && getStringValueAt(i, column).substring(3, 5).equals(getStringValueAt(i+1, column).substring(3, 5))) {
						   swapRows(i, i+1);
						   finished = false;
					   }
				   }
			   }
		   } while (!finished);
	   }
   }
   
   private void swapRows(int r1, int r2) {
	   for (int col = 0; col < this.tableModel.getColumnCount(); col ++) {
		   Object o = getValueAt(r1, col);
		   setObjectAt(getValueAt(r2, col), r1, col);
		   setObjectAt(o, r2, col);
	   }
   }
   
   /**
    * Sets the header text for the specified column.
    * 
    * @param column the column to change the header of.
    * @param text the text to change the header to.
    */
   public void setColumnHeading(int column, String text) {
     if (column < this.tableModel.getColumnCount()) {
       TableColumnModel tcm = this.table.getColumnModel();
       TableColumn tc = tcm.getColumn(column);
       tc.setHeaderValue(text);
     }
   }
   
   /**
    * Set the background color for the title label.
    * 
    * @param c the color for the title background.
    */
   public void setTitleBackground(Color c) {
	   this.titleLabel.setOpaque(true);
	   this.titleLabel.setBackground(c);
   }
   
   /**
    * Set the text color for the title label.
    * 
    * @param c the color for the title text.
    */
   public void setTitleForeground(Color c) {
	   this.titleLabel.setOpaque(true);
	   this.titleLabel.setForeground(c);
   }
   
   /**
    * Set the font for the title label.
    * 
    * @param f the font for the title background.
    */
   public void setTitleFont(Font f) {
	   this.titleLabel.setFont(f);
   }
   
   /**
    * Set the background color for the table rows.
    * 
    * @param c the color for the row backgrounds.
    */
   public void setTableBackground(Color c) {
	   this.table.setBackground(c);
   }
   
   /**
    * Set the text color for the table.
    * 
    * @param c the color for the table text.
    */
   public void setTableForeground(Color c) {
	   this.table.setForeground(c);
   }
   
   /**
    * Set the font for the table.
    * 
    * @param f the font for the table text.
    */
   public void setTableFont(Font f) {
	   int fontSize = f.getSize();
	   this.table.setRowHeight(fontSize + 10);
	   this.table.setFont(f);
   }
   
   /**
    * Set the color for the table grid lines.
    * 
    * @param c the color for the grid lines.
    */
   public void setGridColor(Color c) {
	   this.table.setGridColor(c);
   }
   
   /**
    * Set the background color for the header row.
    * 
    * @param c the color for the header row background.
    */
   public void setHeaderBackground(Color c) {
	   JTableHeader header = this.table.getTableHeader();
	   header.setBackground(c);
   }
   
   /**
    * Set the text color for the header row.
    * 
    * @param c the color for the header row text.
    */
   public void setHeaderForeground(Color c) {
	   JTableHeader header = this.table.getTableHeader();
	   header.setForeground(c);
   }
   
   /**
    * Set the font for the header row.
    * 
    * @param f the font for the header row text.
    */
   public void setHeaderFont(Font f) {
	   JTableHeader header = this.table.getTableHeader();
	   header.setFont(f);
   }
   
   public void mouseClicked(MouseEvent e) {
      TableColumnModel columnModel = this.table.getColumnModel();
      int index = columnModel.getColumnIndexAtX(e.getX());
      sortByColumn(index);
   }
   
   public void mousePressed(MouseEvent e) {
      
   }
   
   public void mouseReleased(MouseEvent e) {
      
   }
   
   public void mouseEntered(MouseEvent e) {
      
   }
   
   public void mouseExited(MouseEvent e) {
      
   }
   
   class TableModel extends AbstractTableModel {
	  private static final long serialVersionUID = 1L;
	  Object[][] data;
      String[] columnNames;
      Object[] columnTypes;
      boolean[][] editable;
      
      TableModel(String[] columns, int rows) {
         int columnCount = columns.length;
         this.columnNames = new String[columnCount];
         this.columnTypes = new Object[columnCount];
         this.editable = new boolean[rows][columnCount];
         
         for (int col = 0; col < columnCount; col++) {
            for (int row = 0; row < rows; row++) {
               this.editable[row][col] = true;
            }
         }
         
         for (int col = 0; col < columnCount; col++) {
            this.columnNames[col] = columns[col];
         }
         
         this.data = new Object[rows][columnCount];
         for (int col = 0; col < columnCount; col++) {
            if (this.columnNames[col].charAt(this.columnNames[col].length() - 1) == '$') {
               this.columnNames[col] = this.columnNames[col].substring(0, this.columnNames[col].length() - 1);
               for (int row = 0; row < rows; row++) {
                 this.data[row][col] = new Double(0.0D);
               }
               this.columnTypes[col] = new Double(0.0D);
            }
            else if (this.columnNames[col].charAt(this.columnNames[col].length() - 1) == '#') {
               this.columnNames[col] = this.columnNames[col].substring(0, this.columnNames[col].length() - 1);
               for (int row = 0; row < rows; row++) {
                 this.data[row][col] = new Integer(0);
               }
               this.columnTypes[col] = new Integer(0);
             }
            else if (this.columnNames[col].charAt(this.columnNames[col].length() - 1) == '~') {
               this.columnNames[col] = this.columnNames[col].substring(0, this.columnNames[col].length() - 1);
               for (int row = 0; row < rows; row++) {
                 this.data[row][col] = new Boolean(false);
               }
               this.columnTypes[col] = new Boolean(false);
             }
             else {
               for (int row = 0; row < rows; row++) {
                 this.data[row][col] = new String("");
               }
               this.columnTypes[col] = new String();
             }
         } 
         
      }
      
      private Object getColumnType(int i)
      {
        if (this.columnTypes[i].getClass().isInstance(new Boolean(false))) {
          return new Boolean(false);
        }
        if (this.columnTypes[i].getClass().isInstance(new Double(0.0D))) {
          return new Double(0.0D);
        }
        if (this.columnTypes[i].getClass().isInstance(new Integer(0))) {
           return new Integer(0);
         }
        return new String("");
      }

      TableModel(int columnCount, int rows)
      {
        this.columnNames = new String[columnCount];
        this.editable = new boolean[rows][columnCount];

        for (int j = 0; j < columnCount; j++) {
          this.columnNames[j] = new String(" ");
          for (int i = 0; i < rows; i++) {
            this.editable[i][j] = true;
          }
        }

        this.data = new Object[rows][columnCount];

        for (int i = 0; i < columnCount; i++)
        {
          for (int j = 0; j < rows; j++)
            this.data[j][i] = new String("");
        }
      }

      public void addRow()
      {
        Object[][] dataTemp = new Object[getRowCount() + 1][getColumnCount()];
        boolean[][] editableCopy = new boolean[getRowCount() + 1][getColumnCount()];

        for (int i = 0; i < getColumnCount(); i++) {
          for (int j = 0; j < getRowCount(); j++) {
            dataTemp[j][i] = this.data[j][i];
            editableCopy[j][i] = this.editable[j][i];
          }
        }

        for (int i = 0; i < getColumnCount(); i++) {
          dataTemp[getRowCount()][i] = getColumnType(i);
          if (getRowCount() > 0) {
            editableCopy[getRowCount()][i] = this.editable[(getRowCount() - 1)][i];
          }
        }
        this.editable = editableCopy;
        this.data = dataTemp;
        fireTableDataChanged();
      }

      public void deleteRow(int row)
      {
        if (row < getRowCount()) {
          Object[][] dataTemp = new Object[getRowCount() - 1][getColumnCount()];
          boolean[][] editableCopy = new boolean[getRowCount() + 1][getColumnCount()];

          for (int i = 0; i < getColumnCount(); i++) {
            int r = 0;
            for (int j = 0; j < getRowCount(); j++) {
              if (row != j) {
                dataTemp[r][i] = this.data[j][i];
                editableCopy[r][i] = this.editable[j][i];
                r++;
              }
            }
          }

          this.editable = editableCopy;
          this.data = dataTemp;
          fireTableDataChanged();
        }
      }

      public int getColumnCount()
      {
        return this.columnNames.length;
      }

      public int getRowCount()
      {
        return this.data.length;
      }

      public String getColumnName(int col)
      {
        return this.columnNames[col];
      }

      public Object getValueAt(int row, int col)
      {
        return this.data[row][col];
      }

      public void setEditable(int col, boolean state)
      {
        for (int i = 0; i < getRowCount(); i++)
          this.editable[i][col] = state;
      }

      public void setEditable(int row, int col, boolean state)
      {
        this.editable[row][col] = state;
      }

      public void setEditable(boolean state)
      {
        for (int i = 0; i < getRowCount(); i++)
          for (int j = 0; j < getColumnCount(); j++)
            this.editable[i][j] = state;
      }

      public Class getColumnClass(int c)
      {
        return this.columnTypes[c].getClass();
      }

      public boolean isCellEditable(int row, int col)
      {
        return this.editable[row][col];
      }

      public void setValueAt(Object value, int row, int col)
      {
        this.data[row][col] = value;
        fireTableCellUpdated(row, col);
      }
      
   }
}
