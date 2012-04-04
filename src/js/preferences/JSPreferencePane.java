package js.preferences;

import java.util.Vector;

import javax.swing.ImageIcon;

public class JSPreferencePane {
	
	Vector<JSPreferenceItem> items;
	String title;
	ImageIcon icon;
	
	public static final int GENERAL_ICON = 0;
	public static final int ACCOUNTS_ICON = 1;
	public static final int ACTIONS_ICON = 2;
	
	public JSPreferencePane(String theTitle, int theIcon, JSPreferenceItem[] theItems) {
		title = theTitle;
		icon = getImageIcon(theIcon);
		items = new Vector<JSPreferenceItem>();
		
		for (int i = 0; i < theItems.length; i ++) {
			items.add(theItems[i]);
		}
	}
	
	public JSPreferencePane(String theTitle, ImageIcon theIcon, JSPreferenceItem[] theItems) {
		title = theTitle;
		icon = theIcon;
		items = new Vector<JSPreferenceItem>();
		
		for (int i = 0; i < theItems.length; i ++) {
			items.add(theItems[i]);
		}
	}
	
	public ImageIcon getImageIcon() {
		return icon;
	}
	
	public ImageIcon getImageIcon(int icon) {
		String filename = "";
		ImageIcon ii = new ImageIcon();
		
		switch (icon) {
		case GENERAL_ICON:
			filename = "general";
			break;
		case ACCOUNTS_ICON:
			filename = "accounts";
			break;
		case ACTIONS_ICON:
			filename = "actions";
			break;
		default:
			filename = "nil";	
		}
		
		if (filename != "nil") {
			ii = new ImageIcon("images/" + filename + ".png");
			return ii;
		}
		else
			return null;
	}

}
