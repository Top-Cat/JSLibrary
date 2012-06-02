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
	private Color[] dayColors;
	private Color[] labelColors;
	private DayPanel[] dayPanels;
	private Color emptyColor;
	private int selectedDay;
	
	/* Interface */
	
	private JSGridPanel days;
	private JSGridPanel dayLabels;
	private JPanel northPanel;
	private JButton previousButton, nextButton, todayButton;
	private JLabel titleLabel;
	private JLabel dateLabel;
	
	/* Constructors */
	
	/**
	 * Creates a new JSCalendar with today's date as the initial view.
	 */
	public JSCalendar() {
		this(Calendar.getInstance());
	}
	
	/**
	 * Creates a new JSCalendar with the initial view set to the specified month of the specified year.
	 * 
	 * @param initialMonth the integer index of the month to set as the initial view (one of <code>Calendar.JANUARY</code>, <code>Calendar.FEBRUARY</code>, etc.)
	 * @param initialYear the integer year to set as the initial view.
	 */
	public JSCalendar(int initialMonth, int initialYear) {
		this(getCalendar(1, initialMonth, initialYear));
	}
	
	/**
	 * Creates a new JSCalendar with the initial view set to the specified date.
	 * 
	 * @param initialDate a Calendar object representing the date to show as the initial view.
	 */
	public JSCalendar(Calendar initialDate) {
		this.year = initialDate.get(Calendar.YEAR);
		this.month = initialDate.get(Calendar.MONTH);
		this.day = initialDate.get(Calendar.DATE);
		
		this.emptyColor = new Color(220, 220, 220);
		
		setLayout(new BorderLayout());
		
		JPanel buttonsPanel = new JPanel(new BorderLayout());
		previousButton = new JButton("◀");
		previousButton.addActionListener(this);
		previousButton.setPreferredSize(new Dimension(50, 30));
		buttonsPanel.add(previousButton, BorderLayout.WEST);
		
		nextButton = new JButton("▶");
		nextButton.addActionListener(this);
		nextButton.setPreferredSize(new Dimension(50, 30));
		buttonsPanel.add(nextButton, BorderLayout.EAST);
		
		todayButton = new JButton("Today");
		todayButton.addActionListener(this);
		buttonsPanel.add(todayButton, BorderLayout.CENTER);
		
		JPanel dateAndButtons = new JPanel(new BorderLayout());
		
		northPanel = new JPanel(new BorderLayout());
				
		dateLabel = new JLabel();
		String monthName = initialDate.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocale());
		dateLabel.setText(" " + monthName + " " + year);
		dateLabel.setFont(dateLabel.getFont().deriveFont(20f));
		dateLabel.setPreferredSize(new Dimension(getWidth(), 50));
		
		dateAndButtons.add(buttonsPanel, BorderLayout.EAST);
		dateAndButtons.add(dateLabel, BorderLayout.CENTER);
		
		northPanel.add(dateAndButtons, BorderLayout.NORTH);
		
		String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		dayLabels = new JSGridPanel(1, 7);
		dayLabels.setPreferredSize(new Dimension(getWidth(), 20));
		for (int i = 0; i < 7; i ++) {
			JLabel label;
			if (getWidth() > 510) {
				label = new JLabel(dayNames[i], JLabel.CENTER);
			} else {
				label = new JLabel(dayNames[i].substring(0, 3), JLabel.CENTER);
			}
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			dayLabels.addComponent(label);
		}
		northPanel.add(dayLabels, BorderLayout.CENTER);
		
		add(northPanel, BorderLayout.NORTH);
		
		JPanel southPanel = new JPanel();
		southPanel.setPreferredSize(new Dimension(getWidth(), 30));
		add(southPanel, BorderLayout.SOUTH);
		
		dayColors = new Color[36];
		labelColors = new Color[36];
		
		int firstOfMonth = initialDate.get(Calendar.DAY_OF_WEEK) - 1;
		
		Calendar now = Calendar.getInstance();
		int today = now.get(Calendar.DATE);
		events = new Vector<Event>();
		
		int rows = ((firstOfMonth == 6 && getDaysInMonth(month, year) == 31 || (firstOfMonth == 0 && getDaysInMonth(month, year) >= 30))) ? 6 : 5;
		
		days = new JSGridPanel(rows, 7);
		dayColors = new Color[rows * 7 + 1];
		labelColors = new Color[rows * 7 + 1];
		dayPanels = new DayPanel[rows * 7 + 1];
		
		for (int i = 1; i <= (rows * 7); i ++) {
			dayColors[i] = Color.WHITE;
			labelColors[i] = Color.black;
			DayPanel panel = new DayPanel(i);
			panel.setBackground(Color.WHITE);
			panel.addMouseListener(this);
			
			if (((firstOfMonth != 0 && i >= firstOfMonth) || (firstOfMonth == 0 && i > 6)) && ((i - firstOfMonth) < getDaysInMonth(month, year)
					|| (firstOfMonth == 0 && (i - firstOfMonth) < getDaysInMonth(month, year) + 7))) {
				if (rows == 6 && firstOfMonth == 0)
					panel.setDate(i - firstOfMonth - 6);
				else
					panel.setDate(i - firstOfMonth + 1);
				if ((i - firstOfMonth + 1) == today && now.get(Calendar.MONTH) == month)
					panel.number.setForeground(new Color(64, 128, 255));
				for (Event e : events) {
					if (e.date.get(Calendar.YEAR) == year && e.date.get(Calendar.MONTH) == month && e.date.get(Calendar.DAY_OF_MONTH) == (i - firstOfMonth + 1)) {
						panel.addEvent(e);
					}
				}
				days.addComponent(panel);
				dayPanels[i] = panel;
			} else {
				panel = null;
				JPanel empty = new JPanel();
				empty.setBackground(emptyColor);
				empty.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				days.addComponent(empty);
			}
		}
		add(days, BorderLayout.CENTER);
		
		repaint();
	}
	
	/* Getters */
	
	/**
	 * Returns the color being used as the blank days on the calendar, i.e. the grid squares before the 1st and after the last day of the month.
	 * 
	 * @return a Color object representing the color of the blank days.
	 */
	public Color getBlankDayColor() {
		return emptyColor;
	}
	
	/**
	 * Returns the day number of the grid square most recently clicked by the user, i.e. the value that would represent <code>Calendar.DAY_OF_MONTH</code>.
	 * 
	 * @return an integer representing the day of the month that was clicked.
	 */
	public int getSelectedDay() {
		return selectedDay;
	}
	
	/**
	 * Returns an array of all the events that have been added to the specified date.
	 * 
	 * @param date the date to retrieve the events for.
	 * @return an array of Event objects representing all the events from the specified date.
	 */
	public Event[] getEventsForDate(Calendar date) {
		Vector<Event> temp = new Vector<Event>();
		for (Event e : events) {
			if (JSDateUtil.isSameDate(e.date, date)) {
				temp.add(e);
			}
		}
		if (temp.size() > 0) {
			Event[] array = new Event[temp.size()];
			for (int i = 0; i < temp.size(); i ++) {
				array[i] = temp.get(i);
			}
			return array;
		} else
			return null;
	}
	
	/* Setters */
	
	/**
	 * Sets the background color of the specified day's grid square
	 * 
	 * @param day the integer representing the value of <code>Calendar.DAY_OF_MONTH</code> for the day to change the color of.
	 * @param c the color to set the day's background to.
	 */
	public void setDayColor(int day, Color c) {
		dayColors[day] = c;
	}
	
	/**
	 * Sets the color of the day number label of the specified day's grid square
	 * 
	 * @param day the integer representing the value of <code>Calendar.DAY_OF_MONTH</code> for the day to change the color of.
	 * @param c the color to set the day's label to.
	 */
	public void setDayLabelColor(int day, Color c) {
		labelColors[day] = c;
	}
	
	/**
	 * Sets the color of blank grid squares, i.e. those before the 1st and after the last day of the month.
	 * 
	 * @param c the color to set the background of blank grid squares to.
	 */
	public void setBlankDayColor(Color c) {
		emptyColor = c;
	}
	
	/* Public Methods */
	
	public void addEvent(Calendar date, String title, Color color) {
		Event event = new Event(date, title, color);
		events.add(event);
	}
	
	public void update() {
		Calendar date = Calendar.getInstance();
		date.set(Calendar.DATE, day);
		date.set(Calendar.MONTH, month);
		date.set(Calendar.YEAR, year);
		
		dateLabel.setText(" " + date.getDisplayName(Calendar.MONTH, Calendar.LONG, getLocale()) + " " + year);
		
		int firstOfMonth = date.get(Calendar.DAY_OF_WEEK) - 1;
		
		Calendar now = Calendar.getInstance();
		int today = now.get(Calendar.DATE);
		
		String[] dayNames = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
		dayLabels = new JSGridPanel(1, 7);
		dayLabels.setPreferredSize(new Dimension(getWidth(), 20));
		for (int i = 0; i < 7; i ++) {
			JLabel label;
			if (getWidth() > 510) {
				label = new JLabel(dayNames[i], JLabel.CENTER);
			} else {
				label = new JLabel(dayNames[i].substring(0, 3), JLabel.CENTER);
			}
			label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
			dayLabels.addComponent(label);
		}
		northPanel.add(dayLabels, BorderLayout.CENTER);
		
		remove(days);
		int rows = ((firstOfMonth == 6 && getDaysInMonth(month, year) == 31 || (firstOfMonth == 0 && getDaysInMonth(month, year) >= 30))) ? 6 : 5;
		
		days = new JSGridPanel(rows, 7);
		dayColors = new Color[rows * 7 + 1];
		labelColors = new Color[rows * 7 + 1];
		dayPanels = new DayPanel[rows * 7 + 1];
		
		for (int i = 1; i <= (rows * 7); i ++) {
			dayColors[i] = Color.WHITE;
			labelColors[i] = Color.black;
			DayPanel panel = new DayPanel(i);
			panel.setBackground(Color.WHITE);
			panel.addMouseListener(this);
			
			if (((firstOfMonth != 0 && i >= firstOfMonth) || (firstOfMonth == 0 && i > 6)) && ((i - firstOfMonth) < getDaysInMonth(month, year)
					|| (firstOfMonth == 0 && (i - firstOfMonth) < getDaysInMonth(month, year) + 7))) {
				if (rows == 6 && firstOfMonth == 0)
					panel.setDate(i - firstOfMonth - 6);
				else
					panel.setDate(i - firstOfMonth + 1);
				if ((i - firstOfMonth + 1) == today && now.get(Calendar.MONTH) == month)
					panel.number.setForeground(new Color(64, 128, 255));
				for (Event e : events) {
					if (rows == 5 || firstOfMonth == 6) {
						if (e.date.get(Calendar.YEAR) == year && e.date.get(Calendar.MONTH) == month && e.date.get(Calendar.DAY_OF_MONTH) == (i - firstOfMonth + 1)) {
							panel.addEvent(e);
						}
					} else {
						if (e.date.get(Calendar.YEAR) == year && e.date.get(Calendar.MONTH) == month && e.date.get(Calendar.DAY_OF_MONTH) == (i - firstOfMonth - 6)) {
							panel.addEvent(e);
						}
					}
				}
				days.addComponent(panel);
				dayPanels[i] = panel;
			} else {
				panel = null;
				JPanel empty = new JPanel();
				empty.setBackground(emptyColor);
				empty.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				days.addComponent(empty);
			}
		}
		add(days, BorderLayout.CENTER);
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
		for (DayPanel p : dayPanels) {
			if (e.getSource() == p) {
				selectedDay = p.date;
			}
		}
		dispatchEvent(new MouseEvent(this, e.getID(), e.getWhen(), e.getModifiers(), e.getX(), e.getY(), e.getClickCount(), e.isPopupTrigger()));
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
		if (e.getSource() == previousButton) {
			month --;
			if (month < Calendar.JANUARY) {
				year --;
				month = Calendar.DECEMBER;
			}
			update();
		} else if (e.getSource() == nextButton) {
			month ++;
			if (month > Calendar.DECEMBER) {
				year ++;
				month = Calendar.JANUARY;
			}
			update();
		} else if (e.getSource() == todayButton) {
			Calendar today = Calendar.getInstance();
			month = today.get(Calendar.MONTH);
			year = today.get(Calendar.YEAR);
			update();
		}
	}
	
	private class DayPanel extends JPanel {
		JLabel number;
		JLabel event1, event2, event3;		
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
		
		void addEvent(Event e) {
			if (event1 == null) {
				event1 = new JLabel(e.title);
				event1.setFont(event1.getFont().deriveFont(12f));
				event1.setBackground(e.color);
				event1.setOpaque(true);
				event1.setBounds(1, 25, 130, 15);
				this.add(event1);
				this.repaint();
			} else if (event2 == null) {
				event2 = new JLabel(e.title);
				event2.setFont(event2.getFont().deriveFont(12f));
				event2.setBackground(e.color);
				event2.setOpaque(true);
				event2.setBounds(1, 42, 130, 15);
				this.add(event2);
				this.repaint();
			} else if (event3 == null) {
				event3 = new JLabel(e.title);
				event3.setFont(event3.getFont().deriveFont(12f));
				event3.setBackground(e.color);
				event3.setBounds(1, 59, 130, 15);
				event3.setOpaque(true);
				this.add(event3);
				this.repaint();
			}
		}
	}
	
	public class Event {
		Calendar date;
		String title;
		Color color;
		
		Event(Calendar date, String title, Color color) {
			this.date = date;
			this.title = title;
			this.color = color;
		}
	}

}
