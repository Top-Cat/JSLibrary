package js.incomplete;

import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;

public class JSEntryDialog extends JDialog {

	private Vector<JComponent> components;
	private Vector<JLabel> labels;
	private Vector<JButton> buttons;
	
	private JLabel titleLabel;
	
	private int y;
	
	public JSEntryDialog(String title, String[] componentList, String[] buttonList) {
		components = new Vector<JComponent>();
		labels = new Vector<JLabel>();
		buttons = new Vector<JButton>();
		
		setTitle(title);
		setLayout(null);
		setSize(400, 400);
		
		titleLabel = new JLabel(title, JLabel.CENTER);
		titleLabel.setFont(titleLabel.getFont().deriveFont(18f));
		titleLabel.setBounds(0, 0, 400, 30);
		add(titleLabel);
	}
	
	public JComponent[] getComponents() {
		return components.toArray(new JComponent[components.size()]);
	}
	
	
	
}
