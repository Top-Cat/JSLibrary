package js;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;

/**
 * JSStepper is an alternative to JSpinner. It provides a text field to hold the current value, and two buttons to increment and decrement the value.
 * A minimum and maximum value can be set, as well as the increment value, i.e. the amount the value increases when incrementing the value.
 * 
 * @author Josh Sunshine
 * @version 1.1.1
 *
 */
public class JSStepper extends JComponent implements ActionListener, KeyListener, FocusListener {

	private static final long serialVersionUID = 8047237239284234568L;
	int min, max, increment, value;
	JTextField textField;
	JButton upButton, downButton;
	
	/**
	 * Creates a new JSStepper with the specified maximum value.
	 * The initial and minimum values will be 0, and the increment will be 1. 
	 * 
	 * @param max the maximum value for this JSStepper
	 */
	public JSStepper(int max) {
		this(0, max, 1, 0);
	}
	
	/**
	 * Creates a new JSStepper with the specified maximum and minimum values.
	 * The initial value will be 0, and the increment will be 1. 
	 * 
	 * @param min the minimum value for this JSStepper
	 * @param max the maximum value for this JSStepper
	 */
	public JSStepper(int min, int max) {
		this(min, max, 1, 0);
	}
	
	/**
	 * Creates a new JSStepper with the specified maximum and minimum values, and the specified initial value.
	 * The increment will be 1. 
	 * 
	 * @param min the minimum value for this JSStepper
	 * @param max the maximum value for this JSStepper
	 * @param initialValue the initial value for this JSStepper
	 */
	public JSStepper(int min, int max, int initialValue) {
		this(min, max, 1, initialValue);
	}
	
	/**
	 * Creates a new JSStepper with the specified maximum, minimum and initial values, and the specified increment.
	 * 
	 * @param min the minimum value for this JSStepper
	 * @param max the maximum value for this JSStepper
	 * @param increment the amount the value should be incremented when the increment button is clicked
	 * @param initialValue the initial value for this JSStepper
	 */
	public JSStepper(int min, int max, int increment, int initialValue) {
		this.min = min;
		this.max = max;
		this.increment = increment;
		this.value = initialValue;
		
		textField = new JTextField();
		textField.setBounds(0, 10, 70, 30);
		textField.addKeyListener(this);
		textField.addFocusListener(this);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setText(Integer.toString(value));
		textField.setCaretColor(Color.white);
		add(textField);
		
		ImageIcon downArrow = new ImageIcon("images/DownArrow.png");
		downButton = new JButton(downArrow);
		downButton.setBounds(70, 25, 20, 20);
		downButton.addActionListener(this);
		downButton.addKeyListener(this);
		add(downButton);
		
		ImageIcon upArrow = new ImageIcon("images/UpArrow.png");
		upButton = new JButton(upArrow);
		upButton.setBounds(70, 5, 20, 20);
		upButton.addActionListener(this);
		upButton.addKeyListener(this);
		add(upButton);
	}
	
	/**
	 * Sets whether the keyboard can be used to type a specific value, or whether the mouse must be used. Even when typing
	 * is not allowed, using the arrow keys still increments or decrements the value, i.e. <code>setTypingAllowed</code>
	 * only affects whether or not values can be typed in directly.
	 * 
	 * @param allowed 	If <code>true</code>, the keyboard can be used to type a specific value. If <code>false</code>, 
	 * 					the mouse must be used to change the value using the buttons.
	 */
	public void setTypingAllowed(Boolean allowed) {
		textField.setEditable(allowed);
	}
	
	/**
	 * Sets the font for the text field of this JSStepper.
	 * 
	 * @param font the font to use for the text field.
	 * 
	 */
	public void setFont(Font font) {
		textField.setFont(font);
	}
	
	/**
	 * Increments the current value by the increment value set at initialisation.
	 */
	public void increment() {
		int oldValue = value;
		
		if (value + increment <= max)
			value += increment;
		else
			value = max;
		
		this.firePropertyChange("value", oldValue, value);
		
		textField.setText(Integer.toString(value));
		textField.setSelectionStart(0);
		textField.setSelectionEnd(textField.getText().length());
	}
	
	/**
	 * Decrements the current value by the increment value set at initialisation.
	 */
	public void decrement() {
		int oldValue = value;
		
		if (value - increment >= min)
			value -= increment;
		else
			value = min;
		
		this.firePropertyChange("value", oldValue, value);
		textField.setText(Integer.toString(value));
		textField.setSelectionStart(0);
		textField.setSelectionEnd(textField.getText().length());
	}
	
	/**
	 * Sets the current value to a specific integer. If the value parameter is less than <code>min</code> then the value will be set to <code>min</code>, 
	 * or if the value is greater than <code>max</code> it will be set to <code>max</code>.
	 * 
	 * @param value the value to set this JSStepper to
	 */
	public void setValue(int value) {
		int oldValue = this.value;
		
		if (value >= min && value <= max)
			this.value = value;
		else if (value < min)
			this.value = min;
		else if (value > max)
			this.value = max;
		
		this.firePropertyChange("value", oldValue, this.value);
		textField.setText("");
		textField.setText(Integer.toString(this.value));
	}
	
	/**
	 * Determines the current value of this JSStepper
	 * 
	 * @return the current value of the stepper.
	 */
	public int getValue() {
		return this.value;
	}
	
	/**
	 * Sets the amount that <code>value</code> should change by when <code>increment()</code> or <code>decrement</code> are called.
	 * 
	 * @param increment the new value to increment by
	 */
	public void setIncrement(int increment) {
		this.increment = increment;
	}
	
	/**
	 * Sets the maximum allowed value for this stepper. 
	 * 
	 * @param max the new maximum value allowed
	 * @throws IllegalArgumentException if the <code>max</code> value is smaller than the current <code>min</code> value
	 */
	public void setMax(int max) {
		int oldValue = value;
		
		if (max > this.min) {
			this.max = max;
		
			if (value > max)
				value = max;
			
			firePropertyChange("value", oldValue, value);
			textField.setText(Integer.toString(value));
		} else {
			throw new IllegalArgumentException("Maximum value must be greater than minimum value.");
		}
	}
	
	/**
	 * Sets the minimum allowed value for this stepper. 
	 * 
	 * @param min the new minimum value allowed
	 * @throws IllegalArgumentException if the <code>min</code> value is greater than the current <code>max</code> value
	 */
	public void setMin(int min) {
		int oldValue = value;
		
		if (min < this.max) {
			this.min = min;
			
			if (value < min)
				value = min;
			
			firePropertyChange("value", oldValue, value);
			textField.setText(Integer.toString(value));
		} else {
			throw new IllegalArgumentException("Minimum value must be smaller than maximum value.");
		}
	}

	public void actionPerformed(ActionEvent event) {
		if (event.getSource() == downButton)
			decrement();
		else if (event.getSource() == upButton)
			increment();
	}

	public void keyPressed(KeyEvent event) {
		int code = event.getKeyCode();
		
		if (event.getSource() == upButton && code == KeyEvent.VK_ENTER)
			increment();
		
		if (event.getSource() == downButton && code == KeyEvent.VK_ENTER)
			decrement();
		
		if (code == KeyEvent.VK_UP)
			increment();
		else if (code == KeyEvent.VK_DOWN)
			decrement();
	}

	public void keyReleased(KeyEvent event) {
		
	}

	public void keyTyped(KeyEvent event) {
		char typed = event.getKeyChar();
		if (! Character.isDigit(typed) && typed != '-')
			event.consume();
	}

	public void focusGained(FocusEvent event) {
		
	}

	public void focusLost(FocusEvent event) {
		setValue(Integer.parseInt(textField.getText()));
	}
}
