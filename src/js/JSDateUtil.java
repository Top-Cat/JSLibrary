package js;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSDateUtil {

	public static final long SECOND = 1000;
	public static final long MINUTE = 60 * SECOND;
	public static final long HOUR = 60 * MINUTE;
	public static final long DAY = 24 * HOUR;
	
	/**
	 * Returns the difference between now and the given date in a relative way, for example as <code>5 hours ago</code> or <code>in 3 days</code>.
	 * 
	 * @param date the date to express relatively
	 * @return a string contating the relative phrase
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
			diff = - diff;
			if (diff < 2 * SECOND)
				return "just now";
			else if (diff < MINUTE)
				return "in " + (diff / SECOND) + " seconds";
			else if (diff < 2 * MINUTE)
				return "in a minute";
			else if (diff < HOUR)
				return "in " + (diff / MINUTE) + " minutes";
			else if (diff < 2 * HOUR)
				return "in an hour";
			else if (diff < DAY)
				return "in " + (diff / HOUR) + " hours";
			else if (diff < 2 * DAY)
				return "tomorrow";
			else
				return "in " + (diff / DAY) + " days";
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
	
}
