package js;


import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


/**
 * An extension to JTextField which provides a suggestion list and the ability to set a maximum entry length. Suggestions are stored in a list, and
 * as the user types in the text field, the entered text is compared to words and phrases in the suggestion list. Any words from the list containing
 * the string entered are displayed below the text field as suggestions. The user can then press <code>Return</code> to set the text field's text to
 * the word or phrase being suggested.
 * 
 * @author Josh Sunshine
 * @version 1.0
 */
public class JSTextField extends JTextField implements KeyListener, FocusListener, ListSelectionListener {
	
	private static final long serialVersionUID = -2666379707500459008L;
	private int maximumChars = 9999;
	private JFrame suggestionFrame;
	private JScrollPane suggestionScroller;
	private JList suggestionList;
	private Vector<String> suggestionVector;
	private boolean shouldShowSuggestions;
	private DefaultListModel listModel;
	private int suggestionType;
	
	public static final int ENTIRE_WORD = 0;
	public static final int BEGINNING_ONLY = 1;
	
	/**
	 * Creates a new text field with no maximum entry length, and no suggestion list.
	 */
	public JSTextField() {
		super();
		JSUtil.checkForJSLibraryUpdate();
		this.addKeyListener(this);
		this.addFocusListener(this);
		init();
	}
	
	/**
	 * Creates a new text field with the specified width, mesaured in characters.
	 * 
	 * @param width the width of the text field in characters
	 */
	public JSTextField(int width) {
		super(width);
		JSUtil.checkForJSLibraryUpdate();
		this.addKeyListener(this);
		this.addFocusListener(this);
		init();
	}
	
	/**
	 * Creates a new text field with the specified width and a specified maximum entry length.
	 * 
	 * @param maximumCharacters the maximum length of text that can be entered
	 * @param width the width of the text field in characters
	 */
	public JSTextField(int maximumCharacters, int width) {
		super(width);
		JSUtil.checkForJSLibraryUpdate();
		setMaximumCharacters(maximumCharacters);
		this.addKeyListener(this);
		this.addFocusListener(this);
		init();
	}
	
	private void init() {
		suggestionFrame = new JFrame();
		suggestionFrame.setResizable(false);
		suggestionFrame.setFocusable(false);
		suggestionFrame.setFocusableWindowState(false);
		suggestionFrame.setUndecorated(true);
		
		listModel = new DefaultListModel();
		suggestionList = new JList(listModel);
		suggestionList.addListSelectionListener(this);
		
		suggestionScroller = new JScrollPane(suggestionList);
		suggestionFrame.add(suggestionScroller);
		
		suggestionVector = new Vector<String>();
	}
	
	private void showSuggestionList() {
		suggestionFrame.setSize(this.getWidth(), 75);
		suggestionFrame.setLocation(this.getLocationOnScreen().x, this.getLocationOnScreen().y + this.getHeight());
		suggestionFrame.setVisible(true);
	}
	
	private void hideSuggestionList() {
		suggestionFrame.setVisible(false);
	}
	
	private void updateSuggestions() {
		String text = this.getText();
		if (text.length() > 0) {
			listModel.clear();
			for (String listItem : suggestionVector) {
				if (this.suggestionType == ENTIRE_WORD) {
					if (listItem.toLowerCase().contains(text.toLowerCase())) {
						listModel.addElement(listItem);
					}
				} else {
					if (text.length() <= listItem.length()) {
						if (listItem.toLowerCase().substring(0, text.length()).contains(text.toLowerCase())) {
							listModel.addElement(listItem);
						}
					}
				}
			}
			if (listModel.getSize() == 0) {
				hideSuggestionList();
			}
		}
		else {
			listModel.clear();
			hideSuggestionList();
		}
	}
	
	/**
	 * Adds suggestions to the text field's suggestion list.
	 * 
	 * @param suggestions an array of strings containing possible words or phrases to be suggested
	 */
	public void addSuggestions(String[] suggestions) {
		for (int i = 0; i < suggestions.length; i ++) {
			suggestionVector.add(suggestions[i]);
		}
	}
	
	/**
	 * Adds suggestions to the text field's suggestion list.
	 * 
	 * @param suggestions a list of strings containing possible words or phrases to be suggested
	 */
	public void addSuggestions(Vector<String> suggestions) {
		for (String s : suggestions) {
			suggestionVector.add(s);
		}
	}
	
	/**
	 * Adds a single word or phrase to the text field's suggestion list.
	 * 
	 * @param suggestion a word or phrase to be suggested
	 */
	public void addSuggestion(String suggestion) {
		suggestionVector.add(suggestion);
	}
	
	/**
	 * Turns suggestions on or off. When turned on, as the user types, a list of words and phrases containing the entered text will appear.
	 * 
	 * @param state <code>true</code> to turn on suggestions, <code>false</code> to turn them off
	 */
	public void setSuggestionsEnabled(Boolean state) {
		if (state) {
			shouldShowSuggestions = true;
		}
		else {
			shouldShowSuggestions = false;
		}
	}
	
	/**
	 * Determines whether suggestions are enabled for the text field.
	 * 
	 * @return <code>true</code> if suggestions are enabled, <code>false</code> otherwise
	 */
	public boolean getSuggestionsEnabled() {
		return shouldShowSuggestions;
	}
	
	/**
	 * Sorts the suggestion list alphabetically.
	 */
	public void sortSuggestions() {
		Collections.sort(suggestionVector);
	}
	
	/**
	 * Removes all words and phrases from the suggestion list and leaves it empty.
	 */
	public void removeAllSuggestions() {
		suggestionVector.removeAllElements();
	}
	
	/**
	 * Removes a specific suggestion from the list, determined by the index number.
	 * 
	 * @param index the location in the list of the suggestion to be removed
	 */
	public void removeSuggestion(int index) {
		suggestionVector.remove(index);
	}
	
	/**
	 * Removes a specific suggestion from the list.
	 * 
	 * @param suggestion the word or phrase to remove from the suggestion list
	 */
	public void removeSuggestion(String suggestion) {
		for (int i = 0; i < suggestionVector.size(); i ++) {
			if (suggestionVector.elementAt(i) == suggestion) {
				suggestionVector.remove(i);
			}
		}
	}
	
	/**
	 * Determines the character limit set on the text field. 
	 * 
	 * @return the character limit set on the text field
	 */
	public int getMaximumCharacters() {
		return this.maximumChars;
	}
	
	/**
	 * Sets the character limit of the text field.
	 * 
	 * @param maximumCharacters
	 */
	public void setMaximumCharacters(int maximumCharacters) {
		this.maximumChars = maximumCharacters;
	}
	
	/**
	 * Sets the font of the text in the suggestion list.
	 * 
	 * @param font the font to format the list with
	 */
	public void setListFont(Font font) {
		suggestionList.setFont(font);
	}
	
	/**
	 * Sets the way text is compared to the suggestion list. <code>JSTextField.ENTIRE_WORD<code> matches the entered text anywhere in the suggestion
	 * list. <code>JSTextField.BEGINNING_ONLY<code> will only match suggestions where the start of the suggestion matches the entered text.
	 * 
	 * @param type
	 */
	public void setSuggestionType(int type) {
		this.suggestionType = type;
	}

	public void keyPressed(KeyEvent event) {
		
	}

	public void keyReleased(KeyEvent event) {
		if (event.getKeyChar() == KeyEvent.VK_ENTER) {
			this.setText(listModel.get(0).toString());
			hideSuggestionList();
		}
		else if (event.getKeyChar() == KeyEvent.VK_ESCAPE) {
			hideSuggestionList();
		}
		else if (shouldShowSuggestions && event.getModifiers() == 0) {
			updateSuggestions();
			if (listModel.getSize() > 0) {
				showSuggestionList();
			}
		}
	}

	public void keyTyped(KeyEvent event) {
		if (this.getText().length() >= maximumChars) {
			event.consume();
		}
	}

	public void focusGained(FocusEvent event) {
		
	}

	public void focusLost(FocusEvent event) {
		hideSuggestionList();
	}

	public void valueChanged(ListSelectionEvent event) {
		if (suggestionList.getSelectedValue() != null) {
			this.setText(suggestionList.getSelectedValue().toString());
		}
		hideSuggestionList();
	}
}
