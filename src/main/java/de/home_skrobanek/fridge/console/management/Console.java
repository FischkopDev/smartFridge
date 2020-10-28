/**
 * @author
 *      Timo Skrobanek
 *
 * @date
 *      13.10.2020
 *
 * @website
 *      www.home-skrobanek.de
 *
 * @description
 *      A tool to make a better overview in the final output.
 */
package de.home_skrobanek.fridge.console.management;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * This class make a good look in the console
 */
public class Console {

	private static final String prefix = "[Information]: ";
	private static final String warningPrefix = "[Warning]: ";
	private static boolean debug = true;

	/**
	 *
	 * @param message
	 * 		set a message you want to see in the console
	 * @return
	 * 		returning the message in a better format with
	 * 		Time stamp.
	 */
	public static String debugMessage(String message) {
		if(debug)System.out.println(getTime() + prefix + message);
		
		return getTime() + prefix + message;
	}

	/**
	 *
	 * @param message
	 * 		Set a custom message
	 * @param o
	 * 		Set a object which name will also
	 * 		be displayed in console.
	 * @return
	 * 		Returning the Message in a new format.
	 */
	public static String debugMessage(String message, Object o) {
		if(debug)System.out.println(getTime() + "[" + o.getClass().getName() + "]" + prefix + message);
		
		return getTime() + prefix + message;
	}

	/**
	 *
	 * @param message
	 * 		Write a normal output message
	 * @return
	 * 		...
	 */
	public static String writeInConsole(String message) {
		System.out.println(getTime() + prefix + message);
		
		return getTime() + prefix + message;
	}

	/**
	 *
	 * @param message
	 * 		Send a warning message to console
	 * @return
	 * 		Returning the message in new format
	 */
	public static String warnInConsole(String message) {
		System.err.println(getTime() + warningPrefix + message);
		
		return getTime() + warningPrefix + message;
	}
	
	@SuppressWarnings("unused")
	private static String getTime() {
		Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        
        return "[" + cal.getTime() + "]";
	}

	/**
	 *
	 * @param toggle
	 * 		Set the debugmode which enable debug messages
	 */
	public static void setDebugging(boolean toggle) {
		debug = toggle;
	}
}
