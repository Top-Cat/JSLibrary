package js.testing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JPanel;
import javax.swing.JTextField;

import js.JSBadgeButton;
import js.JSDateEntry;
import js.JSFrame;
import js.JSProgressBar;
import js.JSStepper;
import js.uikit.UITableView;
import js.uikit.UITableViewCell;
import js.uikit.UIToolbar;

import org.pushingpixels.trident.Timeline;

public class Sandbox extends JSFrame implements ActionListener, MouseListener,
		ItemListener, ComponentListener {

	private static final long serialVersionUID = 1275283783541551650L;
	private static final double version = 1.0;
	JTextField xField, yField;
	
	JSBadgeButton badgeButton;
	JSProgressBar bar;
	boolean ask = true;
	JSDateEntry date;
	JSStepper stepper;
	UITableView table;
	UIToolbar toolbar;
	
	boolean b = true;
	JPanel anotherPanel;
	
	Sandbox() {
		setSize(300, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(null);
		
		table = new UITableView(UITableViewCell.SUBTITLE_CELL_STYLE);
		setLayout(null);
		table.setBounds(0, 44, 300, 435);
		add(table);
		
		table.addRow("id", "integer, primary key", UITableViewCell.DISCLOSURE_INDICATOR, false);
		table.addRow("title", "choice", UITableViewCell.DISCLOSURE_INDICATOR, false);
		table.addRow("forename", "string", UITableViewCell.DISCLOSURE_INDICATOR, true);
		table.addRow("surname", "string", UITableViewCell.DISCLOSURE_INDICATOR, true);
		table.addRow("address1", "string", UITableViewCell.DISCLOSURE_INDICATOR, true);
		table.addRow("address2", "string", UITableViewCell.DISCLOSURE_INDICATOR, true);
		table.addRow("town", "string", UITableViewCell.DISCLOSURE_INDICATOR, true);
		table.addRow("postcode", "string", UITableViewCell.DISCLOSURE_INDICATOR, true);
		table.addRow("paysVAT", "boolean", UITableViewCell.DISCLOSURE_INDICATOR, true);
		
		
		table.addMouseListener(this);
		
		
		toolbar = new UIToolbar("", "Customer", "Add New");
		toolbar.setBounds(0, 0, 300, 45);
		toolbar.getRightButton().addActionListener(this);
		toolbar.setTitleEditable(true);
		add(toolbar);
		
		anotherPanel = new JPanel();
		anotherPanel.setBackground(new Color(128, 128, 128));
		anotherPanel.setBounds(300, 44, 300, 435);
		add(anotherPanel);
		
		badgeButton = new JSBadgeButton();
		badgeButton.setText("Slide!");
		badgeButton.setBounds(25, 525, 50, 20);
		badgeButton.addActionListener(this);
		add(badgeButton);
		
		setVisible(true);
	}
	
	public void makeItSlide() {
		Timeline tableSlider = new Timeline(table);
		tableSlider.addPropertyToInterpolate("location", new Point(0, 44), new Point(-300, 44));
		tableSlider.setDuration(250);
		
		Timeline panelSlider = new Timeline(anotherPanel);
		panelSlider.addPropertyToInterpolate("location", new Point(300, 44), new Point(0, 44));
		panelSlider.setDuration(250);
		
		if (b) {
			tableSlider.play();
			panelSlider.play();
			toolbar.setTitle("forename");
			toolbar.setRightButton("");
			toolbar.setLeftButton("Back");
			toolbar.getLeftButton().addActionListener(this);
		}
		else {
			tableSlider.playReverse();
			panelSlider.playReverse();
			toolbar.setTitle("Customer");
			toolbar.setRightButton("Add new");
			toolbar.setLeftButton("");
			toolbar.getRightButton().addActionListener(this);
		}
		
		b = !b;
	}
	
	public boolean checkForUpdates() {
		
		try  {
			URL url = new URL("http://www.joshsunshine.me.uk/private/the_silvery/updates.html");
			
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String inputLine;
			String output = "";
			
			while ((inputLine = in.readLine()) != null) {
				output += inputLine + "\n";
			}
			
			in.close();
			
			double newVersion = Double.parseDouble(output);
			
			if (newVersion > version) {
				return true;
			} else {
				return false;
			}
			
		} catch (MalformedURLException mue) {
			return false;
		} catch (IOException ioe) {
			return false;
		}
	}
	
	public Boolean isInList(int[] array, int search, int from, int to) {
		int mid = (from + to) / 2;
		int val = array[mid];
		if (from == to) {
			return false;
		} else {
			 if (val < search) {
				return isInList(array, search, mid + 1, to);
			} else if (val > search) {
				return isInList(array, search, from, mid - 1);
			} else {
				return true;
			}
		}
	}
	
	public Font registerFont(String filename, float fontSize) {
		try {
		     /* Returned font is of pt size 1 */
		     Font font = Font.createFont(Font.TRUETYPE_FONT, new File(filename));

		     /* derive and return a 12 pt version : need to use float otherwise
		      * it would be interpreted as style
		      */
		     return font.deriveFont(fontSize);

		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		} catch (FontFormatException ffe) {
			ffe.printStackTrace();
			return null;
		}

	}	

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == toolbar.getLeftButton())
			makeItSlide();
	}

	public void itemStateChanged(ItemEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
		UITableViewCell cell = (UITableViewCell) e.getSource();
		if (cell.getTitle().equals("forename"));
			makeItSlide();
	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {

	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void componentMoved(ComponentEvent e) {

	}

	public void componentResized(ComponentEvent e) {

	}

	public void componentShown(ComponentEvent e) {

	}

	public void componentHidden(ComponentEvent e) {

	}

	public static void main(String[] args) {
		Sandbox sandbox = new Sandbox();
	}

}
