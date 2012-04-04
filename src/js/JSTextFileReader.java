package js;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Vector;

import js.exception.NoMoreLinesException;

/**
 * JSTextFileReader is a convenience class a text to be read file line by line, by handling the <code>InputStream</code>s
 * and <code>BufferedReader</code>s for you.<br>
 * 
 * @author Josh
 * @version 1.0.1
 * 
 * @deprecated <code>JSTextFileReader</code> has been deprecated in favour of the more fully-featured <code>{@link JSTextFileUtility}</code>, which supports
 *				writing to a file as well as reading from it. This class will be removed completely in JSLibrary 1.3.3.
 */
public class JSTextFileReader {

	private String filename;
	private Vector<String> fileContents;
	private int currentLine, totalLines;
	
	/**
	 * Creates a new JSTextFileReader from the specified file on disk. The actual text file is only read once, in this method, to save read times.
	 * 
	 * @param filename the location of the text file on disk that should be read
	 */
	public JSTextFileReader(String filename) {
		JSUtil.checkForJSLibraryUpdate();
		this.filename = filename;
		this.currentLine = 0;
		this.totalLines = 0;
		this.fileContents = new Vector<String>();
		
		File file = new File(filename);
		URI uri = file.toURI();
		URL url;
		
		try {
			url = uri.toURL();
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
			url = null;
			return;
		}
		
		BufferedReader reader;
		try {
			reader = new BufferedReader(new InputStreamReader(url.openStream()));
			String input;
			
			while((input = reader.readLine()) != null) {
				fileContents.add(input);
				totalLines ++;
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
			reader = null;
			fileContents = null;
			totalLines = 0;
			return;
		}
	}
	
	/**
	 * Creates a JSTextFileReader which allows the first line of the file to be skipped, since this often includes
	 * 'template' data which does not need to be read.
	 * 
	 * @param filename the location of the text file on disk that should be read
	 * @param hasTemplateLine whether or not the first line of the file should be ignored to account for template data
	 */
	public JSTextFileReader(String filename, boolean hasTemplateLine) {
		this(filename);
		if (hasTemplateLine)
			this.currentLine ++;
	}
	
	/**
	 * Reads and returns the next unread line of the text file. Handling which line is the next to be read is handled automatically.
	 * 
	 * @return the text from the next line of the file
	 */
	public String readLine() {
		if (currentLine < totalLines) {
			String line = fileContents.get(currentLine);
			currentLine ++;
			return line;
		} else {
			NoMoreLinesException e = new NoMoreLinesException("End of file \"" + filename + "\" reached.");
			e.fillInStackTrace();
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * Determines whether there are any unread lines left in the file.
	 * 
	 * @return <code>true</code> if any lines remain unread, <code>false</code> otherwise.
	 */
	public boolean hasMoreLines() {
		return (currentLine < totalLines);
	}
	
	/**
	 * Returns the total number of lines that make up the file
	 * 
	 * @return the total line count for the file
	 */
	public int getNumberOfLines() {
		return totalLines;
	}
	
	/**
	 * Returns the index of the <b>next unread</b> line which will be read.
	 * 
	 * @return the index of the next unread line of the file
	 */
	public int getCurrentLineNumber() {
		return currentLine;
	}
	
}
