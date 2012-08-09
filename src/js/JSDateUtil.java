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
