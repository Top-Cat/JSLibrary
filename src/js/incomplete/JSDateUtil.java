package js.incomplete;

import java.util.Calendar;

public class JSDateUtil {

	public static String getRelativeDate(Calendar date) {
		Calendar now = Calendar.getInstance();
		long diff = now.getTimeInMillis() - date.getTimeInMillis();
		long days = diff / 86400000;
		
		if (days < 0) {
			return "in " + Math.abs(days) + " days";
		} else if (days > 0) {
			return days + " days ago";
		} else {
			return "today";
		}
	}
	
	public static boolean isSameDate(Calendar a, Calendar b) {
		return (a.get(Calendar.YEAR) == b.get(Calendar.YEAR)
				&& a.get(Calendar.MONTH) == b.get(Calendar.MONTH)
				&& a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH));
	}
	
}
