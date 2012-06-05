package js.incomplete;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class JSDateUtil {

	public static final long SECOND = 1000;
	public static final long MINUTE = 60 * SECOND;
	public static final long HOUR = 60 * MINUTE;
	public static final long DAY = 24 * HOUR;
	
	public static String getRelativeDate(Calendar date) {
		Calendar now = Calendar.getInstance();
		long diff = now.getTimeInMillis() - date.getTimeInMillis();
		long days = diff / DAY;
		
		if (days < 0) {
			if (days < -1)
				return "in " + Math.abs(days) + " days";
			else
				return "tomorrow";
		} else if (days > 0) {
			return days + " days ago";
		} else {
			return "today";
		}
	}
	
	public static String relativeDate(Calendar date) {		
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
		}
	}
	
	public static String getRelativeTime(Calendar date) {
		Calendar now = Calendar.getInstance();
		long diff = now.getTimeInMillis() - date.getTimeInMillis();
		if (Math.abs(diff) >= DAY) {
			return getRelativeDate(date);
		} else if (Math.abs(diff) >= HOUR) {
			long hours = diff / HOUR;
			if (hours < 0) {
				return "in " + (Math.abs(hours) + 1) + " hours";
			} else {
				return hours + " hours ago";
			}
		} else if (Math.abs(diff) >= MINUTE) {
			long mins = diff / MINUTE;
			if (mins < 0) {
				return "in " + (Math.abs(mins) + 1) + " minutes";
			} else {
				return mins + " minutes ago";
			}
		} else if (Math.abs(diff) >= SECOND) {
			long secs = diff / SECOND;
			if (secs < 0) {
				return "in " + (Math.abs(secs) + 1) + " seconds";
			} else {
				return secs + " seconds ago";
			}
		} else {
			return "now";
		}
	}
	
	public static boolean isSameDate(Calendar a, Calendar b) {
		return (a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
				&& a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
				&& a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH));
	}
	
}
