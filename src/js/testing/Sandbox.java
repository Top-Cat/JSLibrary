package js.testing;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
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

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import js.JSBadgeButton;
import js.JSDateEntry;
import js.JSFrame;
import js.JSProgressBar;
import js.JSStepper;
import js.uikit.UITableView;
import js.uikit.UIToolbar;

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
		
		setGradientBackground(Color.pink, Color.orange);
		
		JCheckBox box = new JCheckBox("Moo");
		box.setBounds(20, 150, 100, 30);
		add(box);
		
		JButton button = new JButton("Hi there");
		button.setBounds(20, 20, 50, 100);
		add(button);
		
		setVisible(true);
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
	}

	public void itemStateChanged(ItemEvent e) {

	}

	public void mouseClicked(MouseEvent e) {
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
