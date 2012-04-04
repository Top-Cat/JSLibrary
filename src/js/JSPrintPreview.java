package js;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.ImageObserver;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.text.DecimalFormat;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * JSPrintPreview provides a 'print preview' interface for printing a document using Java.
 * A {@link JDialog} is created with a simulation of an A4 piece of paper and some tools for zooming, printing and showing a grid.
 * The instance of <code>JSPrintPreview</code> uses methods similar to that of a {@link Graphics2D} object,
 * such as <code>drawString</code> and <code>drawImage</code>.
 * 
 * @author Josh Sunshine
 * 
 * @version 1.1
 *
 */

public class JSPrintPreview extends JDialog implements Printable, ActionListener {
   
   private static final long serialVersionUID = 1L;
   private static String version = "JSPrintPreview Version 1.1.1";
   private PaperPanel panel = new PaperPanel();
   private JScrollPane scrollPane = new JScrollPane(this.panel);
   private JPanel buttonBar = new JPanel(new GridLayout(1, 5));
   private JTextField coordinateField = new JFormattedTextField();
   private JTextField zoomField = new JFormattedTextField();
   private JButton printButton;
   private JButton gridButton;
   private JButton zoomInButton;
   private JButton zoomOutButton;
   public static final int PORTRAIT_ORIENTATION = 1;
   public static final int LANDSCAPE_ORIENTATION = 0;
   private String currencySymbol = "£";
   private Point mousePos = new Point();
   private double zoomLevel = 100.0;
   private Vector<Object> elements = new Vector<Object>();
   private int orientation = PORTRAIT_ORIENTATION;
   private Font currentFont = new Font("Arial", Font.PLAIN, 12);
   private boolean showGrid = false;
/*   private final int DRAW_LINE = 0;
   private final int SET_COLOR = 1;
   private final int SET_COLOR_RGB = 2;
   private final int DRAW_STRING = 3;
   private final int DRAW_RECT = 4;
   private final int FILL_RECT = 5;
   private final int DRAW_OVAL = 6;
   private final int FILL_OVAL = 7;
   private final int DRAW_IMAGE = 8;
   private final int DRAW_IMAGE_2 = 9;
   private final int SET_FONT = 10;
   private final int DRAW_POLY = 11;
   private final int FILL_POLY = 12;
   private final int DRAW_POLY_2 = 13;
   private final int FILL_POLY_2 = 14;
   private final int SET_STROKE = 15;
   private final int AFFINE_TRANSFORM = 16;
   private final int SET_GRADUATED_FILL = 17;
   private final int DRAW_ARC = 18;
   private final int FILL_ARC = 19;
   private final int SET_COLOR_ALPHA = 20;
   private final int ALIGN_LEFT = 0;
   private final int ALIGN_RIGHT = 1; */
   
   public JSPrintPreview() {
	   JSUtil.checkForJSLibraryUpdate();
	   setModal(true);
      setLayout(new BorderLayout());
      this.panel.addMouseListener(this.panel);
      this.panel.addMouseMotionListener(this.panel);
      this.printButton = new JButton("Print");
      this.gridButton = new JButton("Toggle grid");
      this.zoomInButton = new JButton("Zoom in");
      this.zoomOutButton = new JButton("Zoom out");
      this.buttonBar.add(this.coordinateField);
      this.zoomField.setText("Zoom: 75%");
      this.buttonBar.add(this.zoomField);
      this.coordinateField.setEditable(false);
      this.zoomField.setEditable(false);
      this.buttonBar.add(this.printButton);
      this.buttonBar.add(this.gridButton);
      this.buttonBar.add(this.zoomInButton);
      this.buttonBar.add(this.zoomOutButton);
      this.buttonBar.setBounds(0, 0, 400, 30);
      this.printButton.addActionListener(this);
      this.gridButton.addActionListener(this);
      this.zoomInButton.addActionListener(this);
      this.zoomOutButton.addActionListener(this);
      if (this.orientation == 0)
        this.panel.setPreferredSize(new Dimension(850, 650));
      else {
        this.panel.setPreferredSize(new Dimension(650, 850));
      }
      add("North", this.buttonBar);
      this.scrollPane.setBounds(0, 40, 400, 400);
      add("Center", this.scrollPane);
      setSize(500, 600);
      setLocation(50,0);
      setTitle("Print Preview");
      setDefaultCloseOperation(2);
   }
   
   /**
    * Causes the preview dialog to appear on-screen. This method should be called last,
    * after adding all components.
    */
   public void display() {
      setVisible(true);
   }
   
   /**
    * Sets whether the simulated paper should be in portrait or landscape mode.
    * 
    * @param orientation the new orientation for the simulated paper. Either
    * <code>PORTRAIT_ORIENTATION</code> or <code>LANDSCAPE_ORIENTATION</code>.
    */
   public void setOrientation(int orientation) {
      if (orientation == PORTRAIT_ORIENTATION)
          this.orientation = PORTRAIT_ORIENTATION;
      else
         this.orientation = LANDSCAPE_ORIENTATION;
   }
   
   /**
    * Sets whether or not the grid should be shown as soon as the preview is displayed.
    * 
    * @param show the state of the grid. <code>true</code> will show the grid and <code>false</code> will hide the grid.
    */
   public void setGrid(Boolean show) {
      if (show)
         this.showGrid = true;
      else
         this.showGrid = false;
   }
   
   public void actionPerformed(ActionEvent e) {
      if (e.getSource() == this.zoomInButton) {
         this.zoomLevel += 25D;
         this.zoomField.setText("Zoom: " + (this.zoomLevel-25D) + "%" );
         if (this.orientation == 0)
           this.panel.setPreferredSize(new Dimension((int)(750.0D * (this.zoomLevel/100)) + 100, (int)(550.0D * (this.zoomLevel/100)) + 100));
         else {
           this.panel.setPreferredSize(new Dimension((int)(550.0D * (this.zoomLevel/100)) + 100, (int)(750.0D * (this.zoomLevel/100)) + 100));
         }
         validate();
         repaint();
       } else if (e.getSource() == this.zoomOutButton) {
            if (this.zoomLevel != 50.0) {
               this.zoomLevel -= 25D;
            this.zoomField.setText("Zoom: " + (this.zoomLevel-25D) + "%" );
            if (this.orientation == 0)
              this.panel.setPreferredSize(new Dimension((int)(750.0D * (this.zoomLevel/100)) + 100, (int)(550.0D * (this.zoomLevel/100)) + 100));
            else {
              this.panel.setPreferredSize(new Dimension((int)(550.0D * (this.zoomLevel/100)) + 100, (int)(750.0D * (this.zoomLevel/100)) + 100));
            }
         }

         validate();
         repaint();

         repaint();
       }
       else if (e.getSource() == this.printButton) {
         print();
       }
       else if (e.getSource() == this.gridButton){
         if (this.showGrid) {
            setGrid(false);
            repaint();
         }
         else
            setGrid(true);
            repaint();
       }
   }
   
   public int print(Graphics g, PageFormat format, int page) throws PrinterException {
      if (page > 0) {
         return 1;
       }

       Graphics2D g2d = (Graphics2D)g;
       g2d.translate(format.getImageableX(), format.getImageableY());
       render(g2d, 1.0D, false, showGrid);

       return 0;
   }
   
   /**
    * Sets the symbol to be used when drawing currencies.
    * 
    * @param currencySymbol the character, or characters, to be drawn before currencies.
    */
   public void setCurrencySymbol(String currencySymbol)
   {
     this.currencySymbol = currencySymbol.toString();
   }
   
   /**
    * Renders a string on the paper.
    * 
    * @param string the string to be rendered.
    * @param x the x-coordinate, measured in pixels from the top-left corner of the page, of the string's destination.
    * @param y the y-coordinate, measured in pixels from the top-left corner of the page, of the string's destination.
    */
   public void drawString(String string, int x, int y)
   {
     drawString(string, x, y, 0);
   }

   /**
    * Converts the passed object to a string and renders it on the paper.
    * 
    * @param object the object to be rendered.
    * @param x the x-coordinate, measured in pixels from the top-left corner of the page, of the object's destination.
    * @param y the y-coordinate, measured in pixels from the top-left corner of the page, of the object's destination.
    */
   public void drawString(Object object, int x, int y)
   {
     if (object != null)
       drawString(object.toString(), x, y, 0);
   }

   /**
    * Converts the passed object to a string and renders it on the paper with the specified text alignment.
    * 
    * @param object the object to be rendered.
    * @param x the x-coordinate, measured in pixels from the top-left corner of the page, of the object's destination.
    * @param y the y-coordinate, measured in pixels from the top-left corner of the page, of the object's destination.
    * @param alignment the horizontal alignment of the rendered text. Pass <code>0</code> for left alignment or <code>1</code>
    * for right alignment.
    */
   public void drawString(Object object, int x, int y, int alignment)
   {
     if (object != null)
       drawString(object.toString(), x, y, alignment);
   }

   /**
    * Rounds the passed number to two decimal places, appends the chosen <code>currencySymbol</code> to the beginning,
    * and renders the result on the paper with a right alignment.
    * 
    * @param value the value to be rendered.
    * @param x the x-coordinate, measured in pixels from the top-left corner of the page, of the number's destination.
    * @param y the y-coordinate, measured in pixels from the top-left corner of the page, of the number's destination.
    * 
    * @see #setCurrencySymbol(String currencySymbol)
    */
   public void drawCurrency(double value, int x, int y)
   {
     drawString(this.currencySymbol + round(value,2), x, y, 1);
   }

   /**
    * Rounds the passed number to two decimal places, appends the chosen <code>currencySymbol</code> to the beginning,
    * and renders the result on the paper with the chosen alignment.
    * 
    * @param value the value to be rendered.
    * @param x the x-coordinate, measured in pixels from the top-left corner of the page, of the number's destination.
    * @param y the y-coordinate, measured in pixels from the top-left corner of the page, of the number's destination.
    * @param alignment alignment the horizontal alignment of the rendered text. Pass <code>0</code> for left alignment or <code>1</code>
    * for right alignment.
    * 
    * @see #setCurrencySymbol(String currencySymbol)
    */
   public void drawCurrency(double value, int x, int y, int alignment)
   {
     drawString(this.currencySymbol + round(value,2), x, y, alignment);
   }

   public void drawString(double value, int x, int y)
   {
     drawString(value, x, y);
   }

   public void drawString(double value, int x, int y, int alignment)
   {
     drawString(value, x, y, alignment);
   }

   public void drawString(int value, int x, int y)
   {
     drawString(value, x, y);
   }

   public void drawString(int value, int x, int y, int alignment)
   {
     drawString(value, x, y, alignment);
   }
   
   public void drawImage(String filename, int x, int y)
   {
     Toolkit t = getToolkit();
     Image piccy = t.getImage(filename);
     drawImage(piccy, x, y, this);
   }

   public void drawImage(String filename, int x, int y, int width, int height)
   {
     Toolkit t = getToolkit();
     Image piccy = t.getImage(filename);
     drawImage(piccy, x, y, width, height, this);
   }
   
   public void drawLine(int x1, int y1, int x2, int y2) {
      Object[] list = new Object[5];
      list[0] = new Integer(0);
      list[1] = new Integer(x1);
      list[2] = new Integer(y1);
      list[3] = new Integer(x2);
      list[4] = new Integer(y2);
      this.elements.add(list);
   }
   
   public void setColor(Color color) {
     Object[] list = new Object[5];
     list[0] = new Integer(1);
     list[1] = color;
     this.elements.add(list);
   }
   
   public void setColor(int red, int green, int blue) {
     Object[] list = new Object[5];
     list[0] = new Integer(2);
     list[1] = new Integer(red);
     list[2] = new Integer(green);
     list[3] = new Integer(blue);
     this.elements.add(list);
   }
   
   public void drawString(String text, int x, int y, int alignment) {
     Object[] list = new Object[5];
     list[0] = new Integer(3);
     list[1] = text;
     list[2] = new Integer(x);
     list[3] = new Integer(y);
     list[4] = new Integer(alignment);
     this.elements.add(list);
   }
   
   public void drawRect(int x, int y, int w, int h) {
     Object[] list = new Object[5];
     list[0] = new Integer(4);
     list[1] = new Integer(x);
     list[2] = new Integer(y);
     list[3] = new Integer(w);
     list[4] = new Integer(h);
     this.elements.add(list);
   }
   
   public void fillRect(int x, int y, int w, int h) {
     Object[] list = new Object[5];
     list[0] = new Integer(5);
     list[1] = new Integer(x);
     list[2] = new Integer(y);
     list[3] = new Integer(w);
     list[4] = new Integer(h);
     this.elements.add(list);
   }
   
   public void drawOval(int x, int y, int w, int h) {
     Object[] list = new Object[5];
     list[0] = new Integer(6);
     list[1] = new Integer(x);
     list[2] = new Integer(y);
     list[3] = new Integer(w);
     list[4] = new Integer(h);
     this.elements.add(list);
   }

   public void fillOval(int x, int y, int w, int h) {
     Object[] list = new Object[5];
     list[0] = new Integer(7);
     list[1] = new Integer(x);
     list[2] = new Integer(y);
     list[3] = new Integer(w);
     list[4] = new Integer(h);
     this.elements.add(list);
   }
   
   public void drawImage(Image img, int x, int y, ImageObserver observer) {
      Object[] list = new Object[5];
      list[0] = new Integer(8);
      list[1] = img;
      list[2] = new Integer(x);
      list[3] = new Integer(y);
      list[4] = observer;
      this.elements.add(list);
    }

    public void drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
      Object[] list = new Object[7];
      list[0] = new Integer(9);
      list[1] = img;
      list[2] = new Integer(x);
      list[3] = new Integer(y);
      list[4] = new Integer(width);
      list[5] = new Integer(height);
      list[6] = observer;
      this.elements.add(list);
    }
   
   public void setFont(Font font) {
      Object[] list = new Object[5];
      list[0] = new Integer(10);
      list[1] = font;
      this.elements.add(list);
   }
   
   public void drawPoly(int[] xPoints, int[] yPoints, int nPoints) {
     Object[] list = new Object[5];
     list[0] = new Integer(11);
     list[1] = xPoints;
     list[2] = yPoints;
     list[3] = new Integer(nPoints);
     this.elements.add(list);
   }

   public void fillPoly(int[] xPoints, int[] yPoints, int nPoints) {
     Object[] list = new Object[5];
     list[0] = new Integer(12);
     list[1] = xPoints;
     list[2] = yPoints;
     list[3] = new Integer(nPoints);
     this.elements.add(list);
   }

   public void drawPoly(Polygon polygon) {
     Object[] list = new Object[5];
     list[0] = new Integer(13);
     list[1] = polygon;

     this.elements.add(list);
   }

   public void fillPoly(Polygon polygon)
   {
     Object[] list = new Object[5];
     list[0] = new Integer(14);
     list[1] = polygon;
     this.elements.add(list);
   }
   
   public void setStroke(Stroke stroke) {
     Object[] list = new Object[5];
     list[0] = new Integer(15);
     list[1] = stroke;
     this.elements.add(list);
   }
   
   public void setTransform(AffineTransform transform) {
     Object[] list = new Object[5];
     list[0] = new Integer(16);
     list[1] = transform;
     this.elements.add(list);
   }
   
   public void setColor(GradientPaint gradientPaint) {
     Object[] list = new Object[3];
     list[0] = new Integer(17);
     list[1] = gradientPaint;
     this.elements.add(list);
   }
   
   public void drawArc(int x, int y, int width, int height, int startAngleInDegrees, int arcAngleInDegrees) {
     Object[] list = new Object[7];
     list[0] = new Integer(18);
     list[1] = new Integer(x);
     list[2] = new Integer(y);
     list[3] = new Integer(width);
     list[4] = new Integer(height);
     list[5] = new Integer(startAngleInDegrees);
     list[6] = new Integer(arcAngleInDegrees);

     this.elements.add(list);
   }

   public void fillArc(int x, int y, int width, int height, int startAngleInDegrees, int arcAngleInDegrees) {
     Object[] list = new Object[7];
     list[0] = new Integer(19);
     list[1] = new Integer(x);
     list[2] = new Integer(y);
     list[3] = new Integer(width);
     list[4] = new Integer(height);
     list[5] = new Integer(startAngleInDegrees);
     list[6] = new Integer(arcAngleInDegrees);

     this.elements.add(list);
   }
   
   private void render(Graphics2D g2D, double scale, boolean border, boolean grid)
   {
     g2D.scale(scale, scale);
     if (border) {
       g2D.setColor(Color.LIGHT_GRAY);
       g2D.fillRect(0, 0, 1920, 1080);
       g2D.setColor(Color.WHITE);
       if (this.orientation == 0)
         g2D.fillRect(0, 0, 870, 615);
       else {
         g2D.fillRect(0, 0, 615, 870);
       }
       if (grid) {
          drawGrid(g2D);
       }
     }

     g2D.setColor(Color.BLACK);
     g2D.setFont(this.currentFont);
     for (int i = 0; i < this.elements.size(); i++)
     {
       Object[] list = (Object[])this.elements.get(i);
       switch (((Integer)list[0]).intValue()) {
       case 0:
         g2D.drawLine(((Integer)list[1]).intValue(), ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue(), ((Integer)list[4]).intValue());
         break;
       case 1:
         g2D.setColor((Color)list[1]);
         break;
       case 2:
         g2D.setColor(new Color(((Integer)list[1]).intValue(), ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue()));
         break;
       case 3:
         if (((Integer)list[4]).intValue() == 0) {
           g2D.drawString(list[1].toString(), ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue());
         } else {
           FontMetrics fontMetrics = g2D.getFontMetrics();
           int width = fontMetrics.stringWidth(list[1].toString());
           g2D.drawString(list[1].toString(), ((Integer)list[2]).intValue() - width, ((Integer)list[3]).intValue());
         }
         break;
       case 4:
         g2D.drawRect(((Integer)list[1]).intValue(), ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue(), ((Integer)list[4]).intValue());
         break;
       case 5:
         g2D.fillRect(((Integer)list[1]).intValue(), ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue(), ((Integer)list[4]).intValue());
         break;
       case 6:
         g2D.drawOval(((Integer)list[1]).intValue(), ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue(), ((Integer)list[4]).intValue());
         break;
       case 7:
         g2D.fillOval(((Integer)list[1]).intValue(), ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue(), ((Integer)list[4]).intValue());
         break;
       case 8:
         g2D.drawImage((Image)list[1], ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue(), (ImageObserver)list[4]);
         break;
       case 9:
         g2D.drawImage((Image)list[1], ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue(), ((Integer)list[4]).intValue(), ((Integer)list[5]).intValue(), 
           (ImageObserver)list[6]);
         break;
       case 10:
         g2D.setFont((Font)list[1]);
         break;
       case 11:
         g2D.drawPolygon((int[])list[1], (int[])list[2], ((Integer)list[3]).intValue());
         break;
       case 12:
         g2D.fillPolygon((int[])list[1], (int[])list[2], ((Integer)list[3]).intValue());
         break;
       case 13:
         g2D.drawPolygon((Polygon)list[1]);
         break;
       case 14:
         g2D.fillPolygon((Polygon)list[1]);
         break;
       case 15:
         g2D.setStroke((Stroke)list[1]);
         break;
       case 16:
         g2D.setTransform((AffineTransform)list[1]);
         break;
       case 17:
         g2D.setPaint((GradientPaint)list[1]);
         break;
       case 18:
         g2D.drawArc(((Integer)list[1]).intValue(), ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue(), ((Integer)list[4]).intValue(), ((Integer)list[5]).intValue(), 
           ((Integer)list[6]).intValue());
         break;
       case 19:
         g2D.fillArc(((Integer)list[1]).intValue(), ((Integer)list[2]).intValue(), ((Integer)list[3]).intValue(), ((Integer)list[4]).intValue(), ((Integer)list[5]).intValue(), 
           ((Integer)list[6]).intValue());
       }
     }
   }
   
   private void drawGrid(Graphics2D g)
   {
     Color gridColor = new Color(230,230,230);
     g.setColor(gridColor);
     if (this.orientation == PORTRAIT_ORIENTATION) {
        for (int x = 0; x <= 615; x += 10) {
           g.drawLine(x, 0, x, 870);
         }
         for (int y = 0; y <= 870; y += 10) {
            g.drawLine(0, y, 615, y);
         } 
     }
     else {
        for (int x = 0; x <= 870; x += 10) {
           g.drawLine(x, 0, x, 615);
         }
         for (int y = 0; y <= 615; y += 10) {
            g.drawLine(0, y, 870, y);
         } 
     }
   }
   
   public void print()
   {
     PrinterJob job = PrinterJob.getPrinterJob();
     PageFormat pageFormat = job.defaultPage();

     pageFormat.setOrientation(this.orientation);
     job.setPrintable(this, pageFormat);
     if (job.printDialog())
       try {
         job.print();
       } catch (PrinterException ex) {
         JOptionPane.showMessageDialog(null, "Failed to print" + ex);
       }
   }
   
   public static String getVersion()
   {
     System.out.println(version);
     return version;
   }
   
   private String round(Double value, int places) {
      DecimalFormat df = new DecimalFormat();
      df.setMaximumFractionDigits(places);
      df.setMinimumFractionDigits(places);
      return df.format(value);
   }
   
   private class PaperPanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = -4319157352239575206L;

	private PaperPanel() {
      }

      public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g;

        JSPrintPreview.this.render(g2d, (JSPrintPreview.this.zoomLevel/100), true, showGrid);
      }
      
      public void mouseClicked(MouseEvent e)
      {
         JSPrintPreview.this.coordinateField.setText("Moo");
      }

      public void mousePressed(MouseEvent e) {
      }

      public void mouseReleased(MouseEvent e) {
      }

      public void mouseEntered(MouseEvent e) {
      }

      public void mouseExited(MouseEvent e) {
      }

      public void mouseDragged(MouseEvent e) {
      }

      public void mouseMoved(MouseEvent e) {
        if (JSPrintPreview.this.orientation == PORTRAIT_ORIENTATION) {
           JSPrintPreview.this.mousePos = e.getPoint();
           if (JSPrintPreview.this.mousePos.x <= 615 && JSPrintPreview.this.mousePos.y <= 870)
              JSPrintPreview.this.coordinateField.setText(JSPrintPreview.this.mousePos.x + ", " + JSPrintPreview.this.mousePos.y);
           else
              JSPrintPreview.this.coordinateField.setText("--, --");
        }
        else {
           JSPrintPreview.this.mousePos = e.getPoint();
           if (JSPrintPreview.this.mousePos.x <= 870 && JSPrintPreview.this.mousePos.y <= 615)
              JSPrintPreview.this.coordinateField.setText(JSPrintPreview.this.mousePos.x + ", " + JSPrintPreview.this.mousePos.y);
           else
              JSPrintPreview.this.coordinateField.setText("--, --");
        }
      }
    }
   
}
