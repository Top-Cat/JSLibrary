package js.preferences;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import js.JSDialog;
import js.JSUtil;

public class JSPreferenceWindow implements ActionListener {

	Vector<JSPreferencePane> panes;
	Vector<JToggleButton> buttons;
	Preferences prefs;
	JSDialog window;
	Font regFont = new Font("Lucida Grande", Font.PLAIN, 11);
	Font boldFont = new Font("Lucida Grande", Font.BOLD, 11);
	Class<?> theClass;
	JPanel panel;
	JPanel mainPanel;
	
	public JSPreferenceWindow(Class<?> c, JSPreferencePane[] thePanes) {
		prefs = Preferences.userNodeForPackage(c);
		theClass = c;
		panes = new Vector<JSPreferencePane>();
		buttons = new Vector<JToggleButton>();
		window = new JSDialog();
		
		for (int i = 0; i < thePanes.length; i ++) {
			panes.add(thePanes[i]);
			JToggleButton button = new JToggleButton(thePanes[i].title, thePanes[i].getImageIcon());
			button.setDisabledIcon(thePanes[i].getImageIcon());
			button.setOpaque(false);
			button.setBorderPainted(false);
			button.setFocusPainted(false);
			button.setVerticalTextPosition(SwingConstants.BOTTOM);
			button.setHorizontalTextPosition(SwingConstants.CENTER);
			button.addActionListener(this);
			buttons.add(button);
		}
		
		window.setSize(550, 500);
		window.setResizable(false);
		window.setModal(true);
		window.setLayout(new BorderLayout());
		window.setLocationRelativeTo(null);
		window.setTitle(panes.get(0).title);
		window.getRootPane().putClientProperty("apple.awt.brushMetalLook", Boolean.TRUE);
		
		render();
	}
	
	private void render() {
		JPanel tabBar = new JPanel(null);
		tabBar.setPreferredSize(new Dimension(window.getWidth(), 60));
		tabBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(117, 117, 117)));
		
		for (int i = 0; i < panes.size(); i ++) {
			JToggleButton button = buttons.elementAt(i);
			button.setBounds(i * 60, 3, 70, 53);
			tabBar.add(button);
		}
		
		buttons.elementAt(0).setEnabled(false);
		buttons.elementAt(0).setSelected(true);
		
		Object[] returned = renderPane(0);
		
		mainPanel = (JPanel) returned[0];
		mainPanel.setBackground(new Color(237, 237, 237));
		
		window.animateToSize(window.getWidth(), (Integer) returned[1] + 60);
		
		window.add(tabBar, BorderLayout.PAGE_START);
		window.add(mainPanel, BorderLayout.CENTER);
	}
	
	private Object[] renderPane(int thePane) {
		
		if (panel != null) {
			window.remove(panel);
			panel.removeAll();
		}
		
		panel = new JPanel(null);
		panel.setBackground(new Color(237, 237, 237));
		JSPreferencePane pane = panes.elementAt(thePane);
		
		int y = -1;
		for (int i = 0; i < pane.items.size(); i ++) {
			y ++;
			JLabel label;
			
			switch (pane.items.elementAt(i).getType()) {
			case JSPreferenceItem.DIVIDER:
				JSeparator divider = new JSeparator(SwingConstants.HORIZONTAL);
				divider.setBounds(20, (y + 1) * 35 - 15, window.getWidth() - 40, 6);
				divider.setForeground(new Color(138, 138, 138));
				panel.add(divider);
				break;
				
				
			case JSPreferenceItem.TEXT_FIELD:
				label = new JLabel(pane.items.elementAt(i).getName(), SwingConstants.RIGHT);
				JTextField textField = new JTextField(prefs.get(pane.items.elementAt(i).getPreferenceName(), pane.items.elementAt(i).getDefaultString()));
				label.setBounds(20, (y + 1) * 45 - 18, (3 * (window.getWidth() / 8) - 20), 15);
				textField.setBounds(3 * (window.getWidth() / 8) + 20, (y + 1) * 45 - 25, (window.getWidth() / 3), 30);
				
				final String prefName = pane.items.elementAt(i).getPreferenceName();
				final JTextField field = textField;
				
				textField.addKeyListener(new KeyListener() {
					public void keyPressed(KeyEvent arg0) {
					}

					public void keyReleased(KeyEvent arg0) {
					}

					public void keyTyped(KeyEvent arg0) {
						if (arg0.getKeyCode() != KeyEvent.VK_BACK_SPACE)
							prefs.put(prefName, field.getText() + arg0.getKeyChar());
						else
							prefs.put(prefName, field.getText());
						syncPrefs();
					}
				});
				
				textField.addFocusListener(new FocusListener() {
					public void focusLost(FocusEvent e) {
						prefs.put(prefName, field.getText());
						syncPrefs();
					}
					public void focusGained(FocusEvent e) {
					}
				});
				
				panel.add(label);
				panel.add(textField);
				break;
				
				
			case JSPreferenceItem.COMBO_BOX:
				label = new JLabel(pane.items.elementAt(i).getName(), SwingConstants.RIGHT);
				label.setBounds(20, (y + 1) * 45 - 18, (3 * (window.getWidth() / 8) - 20), 15);
				JComboBox comboBox = new JComboBox();
				comboBox.setBounds(3 * (window.getWidth() / 8) + 20, (y + 1) * 45 - 25, (window.getWidth() / 3), 30);
				for (int c = 0; c < pane.items.elementAt(i).getNumberOfItems(); c ++) {
					comboBox.addItem(pane.items.elementAt(i).getLabels().elementAt(c));
				}
				
				final String prefNameA = pane.items.elementAt(i).getPreferenceName();
				final Preferences q = prefs;
				final JComboBox box = comboBox;
				
				comboBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						q.putInt(prefNameA, box.getSelectedIndex());
						syncPrefs();
					}
				});
				
				comboBox.setSelectedIndex(prefs.getInt(pane.items.elementAt(i).getPreferenceName(), 0));
				panel.add(label);
				panel.add(comboBox);
				break;
				
				
				
			case JSPreferenceItem.SLIDER:
				label = new JLabel(pane.items.elementAt(i).getName(), SwingConstants.RIGHT);
				label.setBounds(20, (y + 1) * 45 - 18, (3 * (window.getWidth() / 8) - 20), 15);
				JSlider slider = new JSlider(pane.items.elementAt(i).getSliderMin(), pane.items.elementAt(i).getSliderMax());
				slider.setBounds(3 * (window.getWidth() / 8) + 20, (y + 1) * 45 - 25, (window.getWidth() / 3), 30);
				slider.setValue(prefs.getInt(pane.items.elementAt(i).getPreferenceName(), pane.items.elementAt(i).getDefaultInt()));
				JLabel valueLabel = new JLabel(Integer.toString(slider.getValue()));
				valueLabel.setBounds(slider.getX() + slider.getWidth() + 15, slider.getY(), 40, 30);
				
				final String prefNameB = pane.items.elementAt(i).getPreferenceName();
				final Preferences pB = prefs;
				final JSlider s = slider;
				final JLabel l = valueLabel;
				
				slider.addChangeListener(new ChangeListener() {
					public void stateChanged(ChangeEvent e) {
						pB.putInt(prefNameB, s.getValue());
						l.setText(Integer.toString(s.getValue()));
						syncPrefs();
					}
				});
				
				
				panel.add(label);
				panel.add(valueLabel);
				panel.add(slider);
				break;
				
				
				
			case JSPreferenceItem.RADIO_BUTTONS:
				label = new JLabel(pane.items.elementAt(i).getName(), SwingConstants.RIGHT);
				label.setBounds(20, (y + 1) * 45 - 18, (3 * (window.getWidth() / 8) - 20), 15);
				ButtonGroup group = new ButtonGroup();
				int selected = prefs.getInt(pane.items.elementAt(i).getPreferenceName(), pane.items.elementAt(i).getDefaultInt());
				int a = 0;
				JRadioButton[] buttons = new JRadioButton[pane.items.elementAt(i).getNumberOfItems()];
				for (int k = 0; k < pane.items.elementAt(i).getNumberOfItems(); k ++) {
					a = k;
					JRadioButton button = new JRadioButton(pane.items.elementAt(i).getLabels().elementAt(k));
					button.setBounds(3 * (window.getWidth() / 8) + 20, ((y + 1 + k) * 45 - 25) - (k * 20), (window.getWidth() / 3), 30);
					
					if (k == selected) {
						button.setSelected(true);
					}
					
					buttons[k] = button;
					
					group.add(button);
					panel.add(button);
				}
				
				while (a > 0) {
					y ++;
					a -= 2;
				}
				
				final JRadioButton[] radioButtons = buttons;
				final Preferences pC = prefs;
				final String prefNameC = pane.items.elementAt(i).getPreferenceName();
				
				for (int b = 0; b < buttons.length; b ++) {
					final int bob = b;
					buttons[b].addItemListener(new ItemListener() {
						public void itemStateChanged(ItemEvent e) {
							if (radioButtons[bob].isSelected())
								pC.putInt(prefNameC, bob);
							syncPrefs();
						}	
					});
				}
				
				panel.add(label);
				break;
				
				
				
			case JSPreferenceItem.CHECKBOX:
				label = new JLabel(pane.items.elementAt(i).getName(), SwingConstants.RIGHT);
				label.setBounds(20, (y + 1) * 45 - 18, (3 * (window.getWidth() / 8) - 20), 15);
				JCheckBox checkBox = new JCheckBox(pane.items.elementAt(i).getLabel(), 
						prefs.getBoolean(pane.items.elementAt(i).getPreferenceName(), pane.items.elementAt(i).getDefaultBoolean()));
				checkBox.setBounds(3 * (window.getWidth() / 8) + 20, (y + 1) * 45 - 25, (window.getWidth() / 3), 30);
				
				final JCheckBox boxA = checkBox;
				final String prefNameD = pane.items.elementAt(i).getPreferenceName();
				final Preferences pD = prefs;
				
				checkBox.addItemListener(new ItemListener() {
					public void itemStateChanged(ItemEvent e) {
						pD.putBoolean(prefNameD, boxA.isSelected());
					}
				});
				
				panel.add(label);
				panel.add(checkBox);
				break;
			}
		}
		
		int height = ((y + 1) * 55 + 60);
		if (height < 250) {
			height = 250;
		}
		
		Object[] array = new Object[3];
		array[0] = panel;
		array[1] = height;
		return array;
	}
	
	private void syncPrefs() {
		try {
			prefs.sync();
		} catch (BackingStoreException bse) {
			
		}
	}
	
	public void setVisible(Boolean b) {
		window.setVisible(b);
	}

	public void actionPerformed(ActionEvent e) {
		JToggleButton button = (JToggleButton) e.getSource();
		if (button.isSelected()) {
			button.setEnabled(false);
			window.setTitle(button.getText());
			for (int i = 0; i < buttons.size(); i ++) {
				if (! buttons.elementAt(i).equals(button)) {
					buttons.elementAt(i).setEnabled(true);
					buttons.elementAt(i).setSelected(false);
				} else {
					Object[] returned = renderPane(i);
					int newHeight = (Integer) returned[1];
					mainPanel = (JPanel) returned[0];
					window.add(mainPanel, BorderLayout.CENTER);
					window.animateToSize(window.getWidth(), newHeight + 1);
					window.setSize(window.getWidth(), newHeight - 1);
					window.setSize(window.getWidth(), newHeight);
				}
			}
		}
	}
	
}