package js.incomplete;

import javax.swing.JTextField;

public class JSValidatedTextField extends JTextField {

	public static final int PRESENCE_CHECK = 0;
	public static final int LENGTH_CHECK = 1;
	public static final int RANGE_CHECK = 2;
	public static final int FORMAT_CHECK = 3;
	
	private int type;
	private int minLength;
	private int maxLength;
	private double minValue;
	private double maxValue;
	private String pattern;
	
}
