package js.incomplete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import js.JSDialog;

public class JSPrintPreview2 extends JSDialog implements ActionListener, MouseListener, 
	ItemListener, Printable {

	private static final long serialVersionUID = -3760504007997774704L;
//	private boolean devMode;
	private boolean shouldShowGrid;
	private JButton printButton;
	private JComboBox zoomBox;
	private JTextField coordinateField;
	private JPanel buttonBar, zoomPanel;
	private PaperPanel paperPanel;
	private JScrollPane scrollPane;
	private JLabel zoomLabel;
	
	private Vector<Object[]> elements;
	private static final int DRAW_STRING = 0;
	private static final int DRAW_LINE = 1;
	private static final int DRAW_RECT = 2;
	private static final int DRAW_OVAL = 3;
	private static final int DRAW_IMAGE = 4;
	private static final int DRAW_POLYGON = 5;
	private static final int DRAW_ARC = 6;
	private static final int FILL_RECT = 7;
	private static final int FILL_OVAL = 8;
	private static final int FILL_POLYGON = 9;
	private static final int FILL_ARC = 10;
	private static final int SET_COLOR = 11;
	private static final int SET_FONT = 12;
	private static final int SET_STROKE = 13;
	private static final int SET_PAINT = 14;
	private static final int SET_ALIGNMENT = 15;
	private static final int SET_STYLE = 16;
	
	public static final int LEFT = SwingConstants.LEFT;
	public static final int RIGHT = SwingConstants.RIGHT;
	public static final int CENTER = SwingConstants.CENTER;
	public static final int PLAIN = Font.PLAIN;
	public static final int BOLD = Font.BOLD;
	public static final int ITALIC = Font.ITALIC;
	public static final int UNDERLINE = 3;
	public static final int BOLD_UNDERLINE = 4;
	
	private int currentAlignment = LEFT;
//	private int currentStyle = PLAIN;
	private Font currentFont;
	private Font styleFont;
	private boolean underline = false;
	private String documentTitle = "JSPrintPreview Document";
	
	private double scale = 1;
	
	private int numberOfPages = 1, pageWidth = 500, pageHeight = 700;
	
	public JSPrintPreview2(boolean devMode) {
	//	this.devMode = devMode;
		elements = new Vector<Object[]>();
		
		setBackground(new Color(175, 175, 175));
		
		setSize(600, 900);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		buttonBar = new JPanel();
		buttonBar.setLayout(new GridLayout(1, 5));
		if (devMode) {
			coordinateField = new JTextField("- - -,  - - -");
			coordinateField.setHorizontalAlignment(CENTER);
			coordinateField.setEditable(false);
			coordinateField.setPreferredSize(new Dimension(120, 30));
			buttonBar.add(coordinateField);
		}
		
		zoomPanel = new JPanel(new GridLayout(1, 2));
		
		zoomLabel = new JLabel("Zoom: ", RIGHT);
		zoomPanel.add(zoomLabel);
		
		String[] zoomLevels = {"25%", "50%", "75%", "100%"};
		zoomBox = new JComboBox(zoomLevels);
		zoomBox.addItemListener(this);
		zoomPanel.add(zoomBox);
		
		buttonBar.add(zoomPanel);
		
		printButton = new JButton("Print");
		printButton.addActionListener(this);
		buttonBar.add(printButton);
		
		paperPanel = new PaperPanel();
		paperPanel.setPreferredSize(new Dimension(600, 725));
		paperPanel.addMouseListener(paperPanel);
		paperPanel.addMouseMotionListener(paperPanel);
		
		scrollPane = new JScrollPane(paperPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		
		setLayout(new BorderLayout());
		add("North", buttonBar);
		add("Center", scrollPane);
		
		currentFont = new Font("Arial", Font.BOLD, 14);
		styleFont = currentFont;
		
		
	}
	
	public JSPrintPreview2 (boolean devMode, String documentTitle) {
		this(devMode);
		setDocumentTitle(documentTitle);
	}
	
	public JSPrintPreview2 (boolean devMode, JButton customButton) {
		this(devMode);
		buttonBar.add(customButton);
	}
	
	public JSPrintPreview2 (boolean devMode, String documentTitle, JButton customButton) {
		this(devMode, documentTitle);
		buttonBar.add(customButton);
	}
	
	public void setVisible(boolean b) {
		super.setVisible(b);
		render(paperPanel.getGraphics(), -1, this.scale);
	}
	
	public void setDocumentTitle(String title) {
		documentTitle = title;
	}
	
	public String getDocumentTitle() {
		return documentTitle;
	}
	
	public void drawString(String value, int x, int y, int pageIndex) {
		Object[] task = {DRAW_STRING, value, x, y, pageIndex};
		elements.add(task);
	}
	
	public void drawString(String value, int x, int y, int alignment, int pageIndex) {
		int oldAlignment = currentAlignment;
		setAlignment(alignment);
		Object[] task = {DRAW_STRING, value, x, y, pageIndex};
		elements.add(task);
		setAlignment(oldAlignment);
	}
	
	public void drawLine(int x1, int y1, int x2, int y2, int pageIndex) {
		Object[] task = {DRAW_LINE, x1, y1, x2, y2, pageIndex};
		elements.add(task);
	}
	
	public void drawRect(int x, int y, int width, int height, int pageIndex) {
		Object[] task = {DRAW_RECT, x, y, width, height, pageIndex};
		elements.add(task);
	}
	
	public void drawOval(int x, int y, int width, int height, int pageIndex) {
		Object[] task = {DRAW_OVAL, x, y, width, height, pageIndex};
		elements.add(task);
	}
	
	public void drawImage(String filename, int x, int y, int pageIndex) {
		Toolkit t = getToolkit();
		Image image = t.getImage(filename);
		Object[] task = {DRAW_IMAGE, image, x, y, image.getHeight(this), image.getWidth(this), pageIndex};
		elements.add(task);
	}
	
	public void drawImage(String filename, int x, int y, int width, int height, int pageIndex) {
		Toolkit t = getToolkit();
		Image image = t.getImage(filename);
		Object[] task = {DRAW_IMAGE, image, x, y, width, height, pageIndex};
		elements.add(task);
	}
	
	public void drawPolygon(int[] xPoints, int[] yPoints, int numberOfPoints, int pageIndex) {
		Object[] task = {DRAW_POLYGON, xPoints, yPoints, numberOfPoints, pageIndex};
		elements.add(task);
	}
	
	public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle, int pageIndex) {
		Object[] task = {DRAW_ARC, x, y, width, height, startAngle, arcAngle, pageIndex};
		elements.add(task);
	}
	
	public void fillRect(int x, int y, int width, int height, int pageIndex) {
		Object[] task = {FILL_RECT, x, y, width, height, pageIndex};
		elements.add(task);
	}
	
	public void fillOval(int x, int y, int width, int height, int pageIndex) {
		Object[] task = {FILL_OVAL, x, y, width, height, pageIndex};
		elements.add(task);
	}
	
	public void fillPolygon(int[] xPoints, int[] yPoints, int numberOfPoints, int pageIndex) {
		Object[] task = {FILL_POLYGON, xPoints, yPoints, numberOfPoints, pageIndex};
		elements.add(task);
	}
	
	public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle, int pageIndex) {
		Object[] task = {FILL_ARC, x, y, width, height, startAngle, arcAngle, pageIndex};
		elements.add(task);
	}
	
	public void setAlignment(int alignment) {
		Object[] task = {SET_ALIGNMENT, alignment};
		elements.add(task);
	}
	
	public void setStyle(int style) {
		Object[] task = {SET_STYLE, style};
		elements.add(task);
	}
	
	public void setColor(Color color) {
		Object[] task = {SET_COLOR, color};
		elements.add(task);
	}
	
	public void setFont(Font font) {
		Object[] task = {SET_FONT, font};
		elements.add(task);
	}
	
	public void setStroke(Stroke stroke) {
		Object[] task = {SET_STROKE, stroke};
		elements.add(task);
	}
	
	public void setPaint(Paint paint) {
		Object[] task = {SET_PAINT, paint};
		elements.add(task);
	}
	
	public void addNewPage() {
		numberOfPages ++;
	}
	
	public int getScreenResolution() {
		Toolkit t = getToolkit();
		return t.getScreenResolution();
	}
	
	private void render(Graphics g, int drawingPage, double scale) {
		Graphics2D g2 = (Graphics2D) g;
		g2.scale(scale, scale);
		int pageIndex;
		paperPanel.setPreferredSize(new Dimension(600, numberOfPages * (pageHeight + 25)));
		
		for (int page = 0; page < numberOfPages; page ++) {
			g2.setColor(Color.white);
			g2.fillRect(0, page * (pageHeight + 25), pageWidth, pageHeight);
		}
		
		g2.setColor(Color.black);
		
		for (Object[] task : elements) {
			switch ((Integer) task[0]) {
			case DRAW_STRING:
				FontMetrics metrics = getFontMetrics(g2.getFont());
				String string = task[1].toString();
				int x = (Integer) task[2];
				int y = (Integer) task[3];
				pageIndex = (Integer) task[4];
				if (drawingPage == -1)
					y += (pageIndex * (pageHeight + 25));
				int width = metrics.stringWidth(string);
				if (drawingPage == -1 || drawingPage == pageIndex) {
					if (currentAlignment == LEFT) {
						g2.drawString(string, x, y);
						if (underline) {
							g2.drawLine(x, y + 3, x + width, y + 3);
						}
					} else if (currentAlignment == RIGHT) {
						g2.drawString(string, x - width, y);
						if (underline) {
							g2.drawLine(x, y + 3, x - width, y + 3);
						}
					} else if (currentAlignment == CENTER) {
						g2.drawString(string, x - (width / 2), y);
						if (underline) {
							g2.drawLine(x - (width / 2), y + 3, x + (width / 2), y + 3);
						}
					}
				}
				break;
			case DRAW_LINE:
				int x1 = (Integer) task[1], y1 = (Integer) task[2], x2 = (Integer) task[3], y2 = (Integer) task[4];
				pageIndex = (Integer) task[5];
				if (drawingPage == -1)
					g2.drawLine(x1, (pageIndex * (pageHeight + 25)) + y1, x2, (pageIndex * (pageHeight + 25)) + y2);
				else if (drawingPage == pageIndex)
					g2.drawLine(x1, y1, x2, y2);
				break;
			case DRAW_RECT:
				int rectX = (Integer) task[1], rectY = (Integer) task[2], rectWidth = (Integer) task[3], rectHeight = (Integer) task[4];
				pageIndex = (Integer) task[5];
				if (drawingPage == -1)
					g2.drawRect(rectX, (pageIndex * (pageHeight + 25)) + rectY, rectWidth, rectHeight);
				else if (drawingPage == pageIndex)
					g2.drawRect(rectX, rectY, rectWidth, rectHeight);
				break;
			case DRAW_OVAL:
				int ovalX = (Integer) task[1], ovalY = (Integer) task[3], ovalWidth = (Integer) task[3], ovalHeight = (Integer) task[4];
				pageIndex = (Integer) task[5];
				if (drawingPage == -1)
					g2.drawOval(ovalX, (pageIndex * (pageHeight + 25)) + ovalY, ovalWidth, ovalHeight);
				else if (drawingPage == pageIndex)
					g2.drawOval(ovalX, ovalY, ovalWidth, ovalHeight);
				break;
			case DRAW_IMAGE:
				Image image = (Image) task[1];
				int imageX = (Integer) task[2], imageY = (Integer) task[3], imageWidth = (Integer) task[4], imageHeight = (Integer) task[5];
				pageIndex = (Integer) task[6];
				if (drawingPage == -1)
					g2.drawImage(image, imageX, (pageIndex * (pageHeight + 25)) + imageY, imageWidth, imageHeight, this);
				else if (drawingPage == pageIndex)
					g2.drawImage(image, imageX, imageY, imageWidth, imageHeight, this);
				break;
			case DRAW_POLYGON:
				int[] xPoints = (int[]) task[1], yPoints = (int[]) task[2];
				int numberOfPoints = (Integer) task[3];
				pageIndex = (Integer) task[4];
				if (drawingPage == -1) {
					for (int i = 0; i < yPoints.length; i ++) {
						yPoints[i] += (pageIndex * (pageHeight + 25));
					}
					g2.drawPolygon(xPoints, yPoints, numberOfPoints);
				} else if (drawingPage == pageIndex)
					g2.drawPolygon(xPoints, yPoints, numberOfPoints);
				break;
			case DRAW_ARC:
				int arcX = (Integer) task[1], arcY = (Integer) task[2], arcWidth = (Integer) task[3], arcHeight = (Integer) task[4], startAngle = (Integer) task[5],
					arcAngle = (Integer) task[6];
				pageIndex = (Integer) task[7];
				if (drawingPage == -1)
					g2.drawArc(arcX, (pageIndex * (pageHeight + 25)) + arcY, arcWidth, arcHeight, startAngle, arcAngle);
				else if (drawingPage == pageIndex)
					g2.drawArc(arcX, arcY, arcWidth, arcHeight, startAngle, arcAngle);
				break;
			case FILL_RECT:
				int fillRectX = (Integer) task[1], fillRectY = (Integer) task[2], fillRectWidth = (Integer) task[3], fillRectHeight = (Integer) task[4];
				pageIndex = (Integer) task[5];
				if (drawingPage == -1)
					g2.fillRect(fillRectX, (pageIndex * (pageHeight + 25)) + fillRectY, fillRectWidth, fillRectHeight);
				else if (drawingPage == pageIndex)
					g2.fillRect(fillRectX, fillRectY, fillRectWidth, fillRectHeight);
				break;
			case FILL_OVAL:
				int fillOvalX = (Integer) task[1], fillOvalY = (Integer) task[2], fillOvalWidth = (Integer) task[3], fillOvalHeight = (Integer) task[4];
				pageIndex = (Integer) task[5];
				if (drawingPage == -1)
					g2.fillOval(fillOvalX, (pageIndex * (pageHeight + 25)) + fillOvalY, fillOvalWidth, fillOvalHeight);
				else if (drawingPage == pageIndex)
					g2.fillOval(fillOvalX, fillOvalY, fillOvalWidth, fillOvalHeight);
				break;
			case FILL_POLYGON:
				int[] fillXPoints = (int[]) task[1], fillYPoints = (int[]) task[2];
				int fillNumberOfPoints = (Integer) task[3];
				pageIndex = (Integer) task[4];
				if (drawingPage == -1) {
					for (int i = 0; i < fillYPoints.length; i ++) {
						fillYPoints[i] += (pageIndex * (pageHeight + 25));
					}
					g2.fillPolygon(fillXPoints, fillYPoints, fillNumberOfPoints);
				} else if (drawingPage == pageIndex)
					g2.fillPolygon(fillXPoints, fillYPoints, fillNumberOfPoints);
				break;
			case FILL_ARC:
				int fillArcX = (Integer) task[1], fillArcY = (Integer) task[2], fillArcWidth = (Integer) task[3], fillArcHeight = (Integer) task[4],
				fillStartAngle = (Integer) task[5], fillArcAngle = (Integer) task[6];
				pageIndex = (Integer) task[7];
				if (drawingPage == -1)
					g2.fillArc(fillArcX, (pageIndex * (pageHeight + 25)) + fillArcY, fillArcWidth, fillArcHeight, fillStartAngle, fillArcAngle);
				else if (drawingPage == pageIndex)
					g2.fillArc(fillArcX, fillArcY, fillArcWidth, fillArcHeight, fillStartAngle, fillArcAngle);
				break;
			case SET_COLOR:
				g2.setColor((Color) task[1]); 
				break;
			case SET_FONT:
				g2.setFont((Font) task[1]);
				break;
			case SET_STROKE:
				g2.setStroke((Stroke) task[1]);
				break;
			case SET_PAINT:
				g2.setPaint((Paint) task[1]);
				break;
			case SET_ALIGNMENT:
				currentAlignment = (Integer) task[1];
				break;
			case SET_STYLE:
				int style = (Integer) task[1];
//				currentStyle = style;
				styleFont = currentFont.deriveFont(Font.PLAIN);
				if (style == BOLD)
					styleFont = styleFont.deriveFont(Font.BOLD);
				if (style == ITALIC)
					styleFont = styleFont.deriveFont(Font.ITALIC);
				underline = (style == UNDERLINE);
				if (style == BOLD_UNDERLINE) {
					styleFont = styleFont.deriveFont(Font.BOLD);
					underline = true;
				}
				g2.setFont(styleFont);
				break;
			}
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}
	
	public void mouseClicked(MouseEvent event) {
		
	}

	public void mouseEntered(MouseEvent event) {
		
	}

	public void mouseExited(MouseEvent event) {
		
	}

	public void mousePressed(MouseEvent event) {
		
	}

	public void mouseReleased(MouseEvent event) {
		
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == printButton)
			print();
	}
	
	public void itemStateChanged(ItemEvent event) {
		if (event.getSource() == zoomBox) {
			switch (zoomBox.getSelectedIndex()) {
			case 0:
				this.scale = 1.0D;
				break;
			case 1:
				this.scale = 1.25D;
				break;
			case 2:
				this.scale = 1.5D;
				break;
			case 3:
				this.scale = 1.75D;
				break;
			}
			repaint();
			render(paperPanel.getGraphics(), -1, scale);
			paperPanel.setPreferredSize(new Dimension((int) (600 * scale), (int) (numberOfPages * scale * (pageHeight + 25))));
		}
	}
	
	public void print() {
		PrinterJob job = PrinterJob.getPrinterJob();
		PageFormat pageFormat = job.defaultPage();
		Paper paper = pageFormat.getPaper();
		
		paper.setImageableArea(0.0D, 0.0D, pageFormat.getPaper().getWidth(), pageFormat.getPaper().getHeight());
	    pageFormat.setPaper(paper);
		
		job.setJobName(documentTitle);
		job.setPrintable(this, pageFormat);
		if (job.printDialog())
			try {
				job.print();
			} catch (PrinterException ex) {
				JOptionPane.showMessageDialog(null, "Failed to print" + ex);
			}
	}

	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		if (pageIndex < numberOfPages) {
			Graphics2D g2 = (Graphics2D) graphics;
			g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		    render(g2, pageIndex, 1.0D);
		    return PAGE_EXISTS;
		} else
			return NO_SUCH_PAGE;
	}
	
	private class PaperPanel extends JPanel implements MouseListener, MouseMotionListener {

		private static final long serialVersionUID = 4083939962987476686L;

		private PaperPanel() {
	    	  
	      }

	      public void paint(Graphics g) {
	    	  super.paint(g);
	    	  
	        Graphics2D g2d = (Graphics2D)g;

	        JSPrintPreview2.this.render(g2d, -1, scale);
	      }
	      
	      public void mouseClicked(MouseEvent e) {
	         JSPrintPreview2.this.coordinateField.setText("Moo");
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
              Point mousePos = e.getPoint();
              int pageY = mousePos.y % (pageHeight + 25);
              if (mousePos.x <= pageWidth && pageY <= pageHeight && (mousePos.y / pageHeight) < numberOfPages)
                 coordinateField.setText(mousePos.x + ", " + pageY);
              else
                 coordinateField.setText("- - -,  - - -");
	      }
	}
}
