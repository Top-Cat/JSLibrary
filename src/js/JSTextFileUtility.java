package js;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

/**
 * JSTextFileUtility is a class which provides simple read/write access to a plain text file stored on disk.
 * 
 * @author Josh
 * @version 1.0
 */
public class JSTextFileUtility {

	public static final String SPACE = " ";
	public static final String NEW_LINE = "\n";
	
	BufferedReader reader;
	BufferedWriter writer;
	String filename;
	Vector<String> contents;
	
	/**
	 * Creates a new <code>JSTextFileUtility</code> to deal with the specified file.
	 * 
	 * @param filename the path to the file to use for this <code>JSTextFileUtility</code>.
	 */
	public JSTextFileUtility(String filename) {
		this.filename = filename;
	}
	
	/**
	 * Reads the contents of this <code>JSTextFileUtility</code>'s file as returns them as one <code>String</code>.
	 * 
	 * @return the contents of this <code>JSTextFileUtility</code>'s file as a String.
	 */
	public String getContentsOfFile() {
		String contents = "";
		if (openForReading()) {
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					if (contents.length() > 0)
						contents += "\n";
					contents += line;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeReadAccess();
			}
		}
		return contents;
	}
	
	/**
	 * Reads the contents of this <code>JSTextFileUtility</code>'s file as returns them as an array of
	 * <code>String</code>s, with one line of the file in each index of the array.
	 * 
	 * @return the contents of this <code>JSTextFileUtility</code>'s file as an array of <code>String</code>s.
	 */
	public String[] getContentsOfFileAsArray() {
		String[] contents = new String[0];
		if (openForReading()) {
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					String[] temp = new String[contents.length + 1];
					System.arraycopy(contents, 0, temp, 0, contents.length);
					temp[contents.length] = line;
					contents = temp;
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeReadAccess();
			}
		}
		return contents;
	}
	
	/**
	 * Reads the contents of this <code>JSTextFileUtility</code>'s file as returns them as a <code>Vector</code>,
	 * with one line of the file in each index of the vector.
	 * 
	 * @return the contents of this <code>JSTextFileUtility</code>'s file as a <code>Vector</code>.
	 */
	public Vector<String> getContentsOfFileAsVector() {
		contents = new Vector<String>();
		if (openForReading()) {
			String line = "";
			try {
				while ((line = reader.readLine()) != null) {
					contents.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				closeReadAccess();
			}
		}
		return contents;
	}
	
	/**
	 * Deletes the contents of this <code>JSTextFileUtility</code>'s file and replaces them with the specified text.
	 * 
	 * @param contents the new contents to add to the file
	 * @return <code>true</code> if the operation was successful; <code>false</code> otherwise.
	 */
	public boolean replaceContentsOfFile(String contents) {
		if (openForWriting()) {
			try {
				writer.write(contents);
				writer.flush();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				closeWriteAccess();
			}
		} else
			return false;
	}
	
	/**
	 * Appends the specified text to the end of this <code>JSTextFileUtility</code>'s file.
	 * 
	 * @param stringToAppend the text to append to the file
	 * @return <code>true</code> if the operation was successful; <code>false</code> otherwise.
	 */
	public boolean appendToFile(String stringToAppend) {
		String contents = getContentsOfFile();
		contents += stringToAppend;
		if (openForWriting()) {
			try {
				writer.write(contents);
				writer.flush();
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				closeWriteAccess();
			}
		} else
			return false;
	}
	
	/**
	 * Appends the specified text to the end of this <code>JSTextFileUtility</code>'s file, after adding the specified separator.<br><br>
	 * 
	 * Usually the separator will be either a space or a new line, so these are provided as <code>JSTextFileUtility.SPACE</code> and
	 * <code>JSTextFileUtility.NEW_LINE</code> for more readable code.
	 * 
	 * @param stringToAppend the text to append to the file
	 * @param separator a string that is added to the file between the current contents and the new text
	 * @return <code>true</code> if the operation was successful; <code>false</code> otherwise.
	 */
	public boolean appendToFileWithSeparator(String stringToAppend, String separator) {
		return appendToFile(separator + stringToAppend);
	}
	
	private boolean openForReading() {
		try {
			reader = new BufferedReader(new FileReader(filename));
			return true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean openForWriting() {
		try {
			writer = new BufferedWriter(new FileWriter(filename));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private void closeReadAccess() {
		try {
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void closeWriteAccess() {
		try {
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
