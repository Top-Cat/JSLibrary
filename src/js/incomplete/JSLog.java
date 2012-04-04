package js.incomplete;

import java.util.Calendar;

import js.JSTextFileUtility;


public class JSLog extends JSTextFileUtility {
	
	boolean shouldUseDate;
	int dateFormat = LONG_FORMAT;
	
	public static final int SHORT_FORMAT = 0;
	public static final int LONG_FORMAT = 1;
	
	public JSLog(String filename) {
		super(filename);
	}
	
	public void append(String textToAppend) {
		String text = (shouldUseDate) ? (getDate() + textToAppend) : (textToAppend);
		appendToFileWithSeparator(text, "\n");
	}

	public void setShouldUseDate(boolean shouldUseDate) {
		this.shouldUseDate = shouldUseDate;
	}
	
	public void setDateFormat(int dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	private String getDate() {
		Calendar now = Calendar.getInstance();
		if (dateFormat == SHORT_FORMAT) {
			return dateToString(now) + " ";
		} else {
			return dateToString(now) + "," + timeToString(now) + " "; 
		}
	}
	
	private String dateToString(Calendar date) {
		return Integer.toString(date.get(Calendar.YEAR)) + "-" + getDoubleDigits(date.get(Calendar.MONTH)) +
				"-" + getDoubleDigits(date.get(Calendar.DATE));
	}
	
	private String timeToString(Calendar date) {
		return getDoubleDigits(date.get(Calendar.HOUR_OF_DAY)) + ":" + getDoubleDigits(date.get(Calendar.MINUTE)) +
				":" + getDoubleDigits(date.get(Calendar.SECOND));
	}
	
	private String getDoubleDigits(int number) {
		if (number < 10)
			return "0" + number;
		else
			return Integer.toString(number);
	}
	
}
