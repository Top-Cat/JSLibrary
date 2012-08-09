package js;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class JSDateUtil {

	public static final long SECOND = 1000;
	public static final long MINUTE = 60 * SECOND;
	public static final long HOUR = 60 * MINUTE;
	public static final long DAY = 24 * HOUR;
	
	/**
	 * Returns the difference between now and the given date in a relative way, for example as <code>5 hours ago</code>. Note that
	 * this only works for dates in the past, for now. If a future date is given, an empty string will be returned.
	 * 
	 * @param date the date to express relatively
	 * @return a string containing the relative phrase
	 */
	public static String getRelativeDate(Calendar date) {		
		Calendar now = Calendar.getInstance();
		long diff = now.getTimeInMillis() - date.getTimeInMillis();
		
		if (diff > 0) {
			if (diff < 2 * SECOND)
				return "just now";
			else if (diff < MINUTE)
				return (diff / SECOND) + " seconds ago";
			else if (diff < 2 * MINUTE)
				return "a minute ago";
			else if (diff < HOUR)
				return (diff / MINUTE) + " minutes ago";
			else if (diff < 2 * HOUR)
				return "an hour ago";
			else if (diff < DAY)
				return (diff / HOUR) + " hours ago";
			else if (diff < 2 * DAY)
				return "yesterday";
			else
				return (diff / DAY) + " days ago";
		} else {
			return ""; 
		}
	}
	
	/**
	 * Determines whether two Calendar objects represent the same date (ignoring time values).
	 * 
	 * @param a the first date
	 * @param b the second date
	 * @return <code>true</code> if the Calendar objects represent the same date, and <code>false</code> if they do not.
	 */
	public static boolean isSameDate(Calendar a, Calendar b) {
		return (a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
				&& a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
				&& a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH));
	}
	
	/**
	 * Returns the specified date in a concise DDMMYYYY format, separated with an arbitrary string.
	 * 
	 * @param date the date to reformat
	 * @param separator the separator to use, such as <code>/</code> or <code>-</code>
	 * @return the reformatted date
	 */
	public static String getShortFormat(Calendar date, String separator) {
		String result = "";
		result += JSUtil.formatWithPlaceValues(date.get(Calendar.DAY_OF_MONTH), 2) + separator;
		result += JSUtil.formatWithPlaceValues(date.get(Calendar.MONTH) + 1, 2) + separator;
		result += date.get(Calendar.YEAR);
		return result;
	}
	
	/**
	 * Returns a string formatted according to the given format string using the given Calendar object. 
	 * The following characters are recognised in the format string:<br><br>
	 * 
	 * <table border="1" cellpadding="2" cellspacing="0">
	 * 	  <tr><td><b>Character</b></td><td><b>Description</b></td><td><b>Example Values</b></td></tr>
          <tr><td><code>d</code></td><td>Day of the month, 2 digits with leading zeros</td><td><code>01</code> to <code>31</code></td></tr>
          <tr><td><code>D</code></td><td>A textual representation of a day, three letters</td><td><code>Mon</code> to <code>Sun</code></td></tr>
          <tr><td><code>j</code></td><td>Day of the month without leading zeros</td><td><code>1</code> to <code>31</code></td></tr>
          <tr><td><code>l</code></td><td>A full textual representation of the day of the week</td><td><code>Sunday</code> to <code>Saturday</code></td></tr>
          <tr><td><code>N</code></td><td>Numeric day of the week</td><td><code>1</code> (for Monday) to <code>7</code> (for Sunday)</td></tr>
          <tr><td><code>S</code></td><td>Suffix for the day of the month, 2 characters</td><td><code>st</code>, <code>nd</code>, <code>rd</code> or <code>th</code>. Works well with <code>j</code></td></tr>
          <tr><td><code>w</code></td><td>Numeric representation of the day of the week</td><td><code>0</code> (for Sunday) to <code>6</code> (for Saturday)</td></tr>
          <tr><td><code>z</code></td><td>The day of the year (starting from 0)</td><td><code>0</code> to <code>365</code></td></tr>
          <tr><td><code>W</code></td><td>Week number of year</td><td>Example: <code>42</code> (the 42nd week in the year)</td></tr>
          <tr><td><code>F</code></td><td>A full textual representation of a month</td><td><code>January</code> to <code>December</code></td></tr>
          <tr><td><code>m</code></td><td>Numeric representation of a month, with leading zeros</td><td><code>01</code> to <code>12</code></td></tr>
          <tr><td><code>M</code></td><td>A short textual representation of a month, three letters</td><td><code>Jan</code> to <code>Dec</code></td></tr>
          <tr><td><code>n</code></td><td>Numeric representation of a month, without leading zeros</td><td><code>1</code> to <code>12</code></td></tr>
          <tr><td><code>t</code></td><td>Number of days in the given month</td><td><code>28</code> to <code>31</code></td></tr>
          <tr><td><code>Y</code></td><td>A full numeric representation of a year, 4 digits</td><td><code>1999</code> or <code>2003</code></td></tr>
          <tr><td><code>y</code></td><td>A two digit representation of a year</td><td><code>99</code> or <code>03</code></td></tr>
          <tr><td><code>a</code></td><td>Lowercase AM and PM</td><td><code>am</code> or <code>pm</code></td></tr>
          <tr><td><code>A</code></td><td>Uppercase AM and PM</td><td><code>AM</code> or <code>PM</code></td></tr>
          <tr><td><code>g</code></td><td>12-hour format of an hour without leading zeros</td><td><code>1</code> through <code>12</code></td></tr>
          <tr><td><code>G</code></td><td>24-hour format of an hour without leading zeros</td><td><code>0</code> through <code>23</code></td></tr>
          <tr><td><code>h</code></td><td>12-hour format of an hour with leading zeros</td><td><code>01</code> through <code>12</code></td></tr>
          <tr><td><code>H</code></td><td>24-hour format of an hour with leading zeros</td><td><code>00</code> through <code>23</code></td></tr>
          <tr><td><code>i</code></td><td>Minutes with leading zeros</td><td><code>00</code> to <code>59</code></td></tr>
          <tr><td><code>s</code></td><td>Seconds, with leading zeros</td><td><code>00</code> through <code>59</code></td></tr>
          <tr><td><code>U</code></td><td>Seconds since January 1 1970 00:00:00 GMT</td><td></td></tr>
	 * </table>
	 * 
	 * @param date the Calendar object to reformat
	 * @param format a string containing symbols representing the format the date should take
	 * @return a string containing the reformatted date
	 */
	public static String formatDate(Calendar date, String format) {
		String result = "";
		
		for (int i = 0; i < format.length(); i ++) {
			char c = format.charAt(i);
			switch (c) {
			case 'd':
				result += JSUtil.formatWithPlaceValues(date.get(Calendar.DAY_OF_MONTH), 2);
				break;
			case 'D':
				result += date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.SHORT, Locale.getDefault());
				break;
			case 'j':
				result += date.get(Calendar.DAY_OF_MONTH);
				break;
			case 'l':
				result += date.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
				break;
			case 'N':
				result += getISODayNumber(date);
				break;
			case 'S':
				String s = JSUtil.addRankingSuffix(date.get(Calendar.DAY_OF_MONTH));
				result += s.substring(s.length() - 2);
				break;
			case 'w':
				result += (date.get(Calendar.DAY_OF_WEEK) - 1);
				break;
			case 'z':
				result += (date.get(Calendar.DAY_OF_YEAR) - 1);
				break;
			case 'W':
				result += date.get(Calendar.WEEK_OF_YEAR);
				break;
			case 'F':
				result += date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());
				break;
			case 'm':
				result += JSUtil.formatWithPlaceValues(date.get(Calendar.MONTH) + 1, 2);
				break;
			case 'M':
				result += date.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale.getDefault());
				break;
			case 'n':
				result += (date.get(Calendar.MONTH) + 1);
				break;
			case 't':
				result += date.getActualMaximum(Calendar.DAY_OF_MONTH);
				break;
			case 'Y':
				result += date.get(Calendar.YEAR);
				break;
			case 'y':
				result += Integer.toString(date.get(Calendar.YEAR)).substring(2);
				break;
			case 'a':
				result += date.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault()).toLowerCase();
				break;
			case 'A':
				result += date.getDisplayName(Calendar.AM_PM, Calendar.LONG, Locale.getDefault());
				break;
			case 'g':
				result += date.get(Calendar.HOUR);
				break;
			case 'G':
				result += date.get(Calendar.HOUR_OF_DAY);
				break;
			case 'h':
				result += JSUtil.formatWithPlaceValues(date.get(Calendar.HOUR), 2);
				break;
			case 'H':
				result += JSUtil.formatWithPlaceValues(date.get(Calendar.HOUR_OF_DAY), 2);
				break;
			case 'i':
				result += JSUtil.formatWithPlaceValues(date.get(Calendar.MINUTE), 2);
				break;
			case 's':
				result += JSUtil.formatWithPlaceValues(date.get(Calendar.SECOND), 2);
				break;
			case 'U':
				result += Long.toString(date.getTimeInMillis());
				break;
			case '`':
				i ++;
				result += format.charAt(i);
				break;
			default:
				result += c;
			}
		}
		
		return result;
	}
	
	private static int getISODayNumber(Calendar d) {
		int day = d.get(Calendar.DAY_OF_WEEK);
		day --;
		if (day == 0)
			day = 7;
		return day;
	}
}
