package js.testing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import js.JSFrame;
import js.JSLog;
import js.JSTable;
import js.JSTableDialog;

public class Sandbox extends JSFrame implements ActionListener, MouseListener,
		ItemListener, ComponentListener {

	private static final long serialVersionUID = 1275283783541551650L;
	JTextField xField, yField;
	
	JSLog log;
	
	boolean b = true;
	JPanel anotherPanel;
	
	Sandbox() {
		
		String[] headings = {"X#", "Y#", "Z#"};
		JSTableDialog td = new JSTableDialog("", headings, 5);
		JSTable t = td.getTable();
		
		for (int i = 0; i < 5; i ++) {
			for (int j = 0; j < 3; j ++) {
				t.setValueAt(i + j, i, j);
			}
		}
		
		td.removeKeyListener();
		td.setVisible(true);
		
	}

	public void actionPerformed(ActionEvent e) {
		JButton source = (JButton) e.getSource();
		log.append("You clicked button number " + source.getText());
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
