package js.incomplete;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import js.JSGridPanel;
import js.JSPanel;

public class JSCalendar extends JSPanel implements ActionListener, MouseListener {

	/* Variables */
	
	private int year, month, day;
	private Vector<Event> events;
	private boolean todayShowing;
	private int weekStartsOn;
	private boolean weekView;
	private Color[] dayColors;
	private DayPanel[] dayPanels;
	
	/* Interface */
	
	private JSGridPanel days;
	private JSGridPanel dayLabels;
	private JButton previousButton, nextButton, todayButton;
	private JLabel titleLabel;
	private JLabel dateLabel;
	
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
		
		System.out.println("Hi");
		
		setLayout(new BorderLayout());
		
		JPanel northPanel = new JPanel(new BorderLayout());
				
		dateLabel = new JLabel();
		String monthName = initialDate.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocale());
		dateLabel.setText(" " + monthName + " " + year);
		dateLabel.setFont(dateLabel.getFont().deriveFont(20f));
		dateLabel.setPreferredSize(new Dimension(getWidth(), 50));
		northPanel.add(dateLabel, BorderLayout.NORTH);
		
		String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		dayLabels = new JSGridPanel(1, 7);
		dayLabels.setPreferredSize(new Dimension(getWidth(), 20));
		for (int i = 0; i < 7; i ++) {
			JLabel label = new JLabel(dayNames[i], JLabel.CENTER);
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			dayLabels.addComponent(label);
		}
		northPanel.add(dayLabels, BorderLayout.CENTER);
		
		add(northPanel, BorderLayout.NORTH);
		
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(getWidth(), 30));
		add(southPanel, BorderLayout.SOUTH);
		
		dayColors = new Color[36];
		dayPanels = new DayPanel[36];
		
		int firstOfMonth = initialDate.get(Calendar.DAY_OF_WEEK) - 1;
		
		if (weekView) {
			
		} else {
			days = new JSGridPanel(5, 7);
			for (int i = 1; i <= 35; i ++) {
				dayColors[i] = Color.WHITE;
				DayPanel panel = new DayPanel(i);
				panel.setBackground(Color.WHITE);
				
				if (i >= firstOfMonth && (i - firstOfMonth) < getDaysInMonth(month, year)) {
					panel.setDate(i - firstOfMonth + 1);
					days.addComponent(panel);
					dayPanels[i] = panel;
				} else {
					panel = null;
					JPanel empty = new JPanel();
					empty.setBackground(getBackground());
					empty.setBorder(BorderFactory.createLineBorder(Color.BLACK));
					days.addComponent(empty);
				}
			}
			add(days, BorderLayout.CENTER);
		}
		
		repaint();
	}
	
	/* Getters */
	
	
	
	/* Setters */
	
	
	
	/* Public Methods */
	
	public void update() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, day);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.YEAR, year);
		
		dateLabel.setText(" " + date.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocale()) + " " + year);
		
		int firstOfMonth = date.get(Calendar.DAY_OF_WEEK) - 1;
		
		for (int i = 1; i <= 35; i ++) {
			DayPanel panel = dayPanels[i];
			
			if (panel != null) {
				panel.setBackground(dayColors[i]);
				
				if (i >= firstOfMonth && (i - firstOfMonth) < getDaysInMonth(month, year)) {
					panel.setDate(i - firstOfMonth + 1);
					dayPanels[i] = panel;
				} else {
					panel = null;
					JPanel empty = new JPanel();
					empty.setBackground(getBackground());
					empty.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				}
			}
		}
		repaint();
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
	
	private int getDaysInMonth(int month, int year) {
		if (month == 0 || month == 2 || month == 4 || month == 6 || month == 7 || month == 9 || month == 11)
			return 31;
		else if (month != 1)
			return 30;
		else if (year % 4 == 0)
			return 29;
		else
			return 28;
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
	
	private class DayPanel extends JPanel {
		JLabel number;
		int date;
		
		DayPanel(int date) {
			setLayout(null);
			setBorder(BorderFactory.createLineBorder(Color.BLACK));
			number = new JLabel(Integer.toString(date));
			number.setBounds(5, 5, 25, 15);
			add(number);
		}
		
		void setDate(int date) {
			this.date = date;
			number.setText(Integer.toString(date));
		}
	}
	
	private class Event {
		Calendar date;
		String title;
		Color color;
		boolean allDay;
	}

}
