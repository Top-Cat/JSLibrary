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
	
	private int year, month;
	private Vector<Event> events;
	private boolean todayShowing;
	private int weekStartsOn;
	
	/* Interface */
	
	private JSGridPanel days;
	private JSGridPanel dayLabels;
	private JButton previousButton, nextButton, todayButton;
	private JLabel titleLabel;
	
	/* Constructors */
	
	public JSCalendar() {
		
	}
	
	/* Getters */
	
	
	
	/* Setters */
	
	
	
	/* Public Methods */
	
	public void update() {
		
	}
	
	public void goToDate(Calendar date) {
		
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
