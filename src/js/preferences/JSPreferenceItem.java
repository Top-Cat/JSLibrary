package js.preferences;

import java.util.Vector;

public class JSPreferenceItem {
	
	public static final int TEXT_FIELD = 0;
	public static final int CHECKBOX = 1;
	public static final int RADIO_BUTTONS = 2;
	public static final int COMBO_BOX = 3;
	public static final int SLIDER = 4;
	
	public static final int DIVIDER = -1;
	
	private String name;
	private String label;
	private int type;
//	private boolean enabled;
	private Vector<String> labels;
	private int sliderMin;
	private int sliderMax;
	private String defaultString;
	private int defaultInt;
	private boolean defaultBool;
	private String preference;
	
	JSPreferenceItem(String theName, int theType, String preferenceName) {
		name = theName;
		type = theType;
//		enabled = true;
		preference = preferenceName;
	}
	
	public static JSPreferenceItem newTextFieldItem(String name, String defaultValue, String preferenceName) {
		JSPreferenceItem item = new JSPreferenceItem(name, TEXT_FIELD, preferenceName);
		item.defaultString = defaultValue;
		return item;
	}
	
	public static JSPreferenceItem newSliderItem(String name, int min, int max, int defaultValue, String preferenceName) {
		JSPreferenceItem item = new JSPreferenceItem(name, SLIDER, preferenceName);
		item.sliderMin = min;
		item.sliderMax = max;
		item.defaultInt = defaultValue;
		return item;
	}
	
	public static JSPreferenceItem newComboBoxItem(String name, String[] items, int defaultChoice, String preferenceName) {
		JSPreferenceItem item = new JSPreferenceItem(name, COMBO_BOX, preferenceName);
		item.labels = new Vector<String>();
		for (int i = 0; i < items.length; i ++) {
			item.labels.add(items[i]);
		}
		item.defaultInt = defaultChoice;
		return item;
	}
	
	public static JSPreferenceItem newCheckboxItem(String name, String label, boolean defaultState, String preferenceName) {
		JSPreferenceItem item = new JSPreferenceItem(name, CHECKBOX, preferenceName);
		item.label = label;
		item.defaultBool = defaultState;
		return item;
	}
	
	public static JSPreferenceItem newRadioButtonsItem(String name, String[] items, int defaultChoice, String preferenceName) {
		JSPreferenceItem item = new JSPreferenceItem(name, RADIO_BUTTONS, preferenceName);
		item.labels = new Vector<String>();
		for (int i = 0; i < items.length; i ++) {
			item.labels.add(items[i]);
		}
		item.defaultInt = defaultChoice;
		return item;
	}
	
	public static JSPreferenceItem newDivider() {
		JSPreferenceItem item = new JSPreferenceItem("", DIVIDER, null);
		return item;
	}
	
	public int getType() {
		return this.type;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getDefaultString() {
		return this.defaultString;
	}
	
	public String getPreferenceName() {
		return this.preference;
	}
	
	public int getNumberOfItems() {
		return labels.size();
	}
	
	public Vector<String> getLabels() {
		return labels;
	}

	public int getSliderMin() {
		return sliderMin;
	}
	
	public int getSliderMax() {
		return sliderMax;
	}
	
	public int getDefaultInt() {
		return defaultInt;
	}
	
	public boolean getDefaultBoolean() {
		return defaultBool;
	}
	
	public String getLabel() {
		return label;
	}
}
