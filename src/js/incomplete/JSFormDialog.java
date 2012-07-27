package js.incomplete;

import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class JSFormDialog extends JDialog {

	private Vector<JComponent> components;
	private Vector<JLabel> labels;
	private Vector<JButton> buttons;
	
	private JLabel titleLabel;
	
	private int y;
	private int labelWidth;
	private int componentWidth;
	
	public JSFormDialog(String title, String[] componentList, String[] buttonList) {
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
		
		y = 40;
		labelWidth = getLabelWidth(componentList);
		componentWidth = 340 - labelWidth;
	}
	
	public JTextField createTextField(String labelText) {
		JLabel label = new JLabel(labelText, JLabel.RIGHT);
		label.setBounds(0, y, labelWidth, 20);
		add(label);
		
		JTextField field = new JTextField();
		field.setBounds(labelWidth + 30, y, componentWidth, 25);
		add(field);
		
		y += 30;
		
		return field;
	}
	
	private int getLabelWidth(String[] labels) {
		int max = 0;
		Font font = new JLabel().getFont();
		FontMetrics fm = getFontMetrics(font);
		for (int i = 0; i < labels.length; i ++) {
			int width = fm.stringWidth(labels[i]);
			if (width > max)
				max = width;
		}
		return max + 10;
	}
	
	public JComponent[] getComponents() {
		return components.toArray(new JComponent[components.size()]);
	}
	
	
	
}
