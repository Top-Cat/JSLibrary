package js.incomplete;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;

import js.JSGridPanel;
import js.JSPanel;

public class JSCalendar extends JSPanel implements ActionListener, MouseListener {

	/* Variables */
	
	private int year, month, day;
	private Vector<Event> events;
	private boolean todayShowing;
	private int weekStartsOn;
	private boolean weekView;
	
	/* Interface */
	
	private JSGridPanel days;
	private JSGridPanel dayLabels;
	private JButton previousButton, nextButton, todayButton;
	private JLabel titleLabel;
	
	/* Constructors */
	
	public JSCalendar() {
		this("", Calendar.getInstance(), false);
	}
	
	public JSCalendar(String title) {
		this(title, Calendar.getInstance(), false);
	}
	
	public JSCalendar(String title, int initialDay, int initialMonth, int initialYear) {
		this(title, getCalendar(initialDay, initialMonth, initialYear), true);
	}
	
	public JSCalendar(String title, int initialMonth, int initialYear) {
		this(title, getCalendar(1, initialMonth, initialYear), false);
	}
	
	public JSCalendar(String title, Calendar initialDate, boolean weekView) {
		this.year = initialDate.get(Calendar.YEAR);
		this.month = initialDate.get(Calendar.MONTH);
		this.day = initialDate.get(Calendar.DATE);
		this.weekView = weekView;
	}
	
	/* Getters */
	
	
	
	/* Setters */
	
	
	
	/* Public Methods */
	
	public void update() {
		
	}
	
	public void goToDate(Calendar date) {
		year = date.get(Calendar.YEAR);
		month = date.get(Calendar.MONTH);
		update();
	}
	
	public void goToDate(int month, int year) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MONTH, month);
		date.set(Calendar.YEAR, year);
		goToDate(date);
	}
	
	public void goToMonth(int month) {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.MONTH, month);
		goToDate(date);
	}
	
	/* Private Methods */
	
	private static Calendar getCalendar(int date, int month, int year) {
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DATE, date);
		c.set(Calendar.MONTH, month);
		c.set(Calendar.YEAR, year);
		return c;
	}
	
	/* Listeners */
	
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

	public void actionPerformed(ActionEvent e) {
		
	}
	
	private class Event {
		Calendar date;
		String title;
		Color color;
		boolean allDay;
	}

}
