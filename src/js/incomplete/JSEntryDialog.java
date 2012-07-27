package js.incomplete;

import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class JSEntryDialog extends JDialog {

	private Vector<JComponent> components;
	private Vector<JLabel> labels;
	
	public JSEntryDialog(String title, String[] componentList, String[] buttons) {
		components = new Vector<JComponent>();
		labels = new Vector<JLabel>();
	}
	
}
