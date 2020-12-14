package com.github.tadukoo.parsing.fileformat;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class used to convert between TadFormatting and Java regexes.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version 0.1-Alpha-SNAPSHOT
 */
public class TadFormatRegexConverter{
	
	// Not allowed to instantiate TadFormatRegexConverter
	private TadFormatRegexConverter(){ }
	
	/**
	 * Convert the given TadFormatting into a Java regex.
	 * 
	 * @param logger The Logger to log any messages to
	 * @param TadFormat The TadFormatting string to convert to a regex
	 * @return The regex resulting from the TadFormatting conversion
	 */
	public static String convertTadFormatToRegex(Logger logger, String TadFormat){
		// Log the original TadFormatting
		logger.log(Level.FINER, "TadFormat: " + TadFormat);
		
		// If TadFormatting contains a period, escape it in the regex
		// TadFormatting treats period as a normal character
		if(TadFormat.contains(".")){
			TadFormat = TadFormat.replaceAll("\\.", "\\\\.");
			logger.log(Level.FINEST, "* Found .\n"
					+ "* TadFormat changed to " + TadFormat);
		}
		
		// If TadFormatting contains "<text>", change it to ".*"
		// <text> is TadFormat's "any amount of any characters"
		if(TadFormat.contains("<text>")){
			TadFormat = TadFormat.replaceAll("<text>", ".*");
			logger.log(Level.FINEST, "* Found <text>\n"
					+ "* TadFormat changed to " + TadFormat);
		}
		
		// If TadFormatting contains "<boolean>", change it to "(true|false)"
		// <boolean> is TadFormat's way of including a boolean
		if(TadFormat.contains("<boolean>")){
			TadFormat = TadFormat.replaceAll("<boolean>", "(true|false)");
			logger.log(Level.FINEST, "* Found <boolean>\n" 
					+ "* TadFormat changed to " + TadFormat);
		}
		
		// If TadFormatting contains "<Boolean>", change it to "(true|false|null)"
		// <Boolean> is TadFormat's way of including a Boolean (which may be null)
		if(TadFormat.contains("<Boolean>")){
			TadFormat = TadFormat.replaceAll("<Boolean>", "(true|false|null)");
			logger.log(Level.FINEST, "* Found <Boolean>\n" 
					+ "* TadFormat changed to " + TadFormat);
		}
		
		// If TadFormatting contains <imagefile>, change it to look for common image file extensions
		// Currently just .jpg (TODO: Add other image file extensions)
		if(TadFormat.contains("<imagefile>")){
			TadFormat = TadFormat.replaceAll("<imagefile>", ".*\\\\.jpg");
			logger.log(Level.FINEST, "* Found <imagefile>\n"
					+ "* TadFormat changed to " + TadFormat);
		}
		
		// If TadFormatting contains <#>, change it to (\d)*
		// <#> is TadFormat's "number" requirement
		if(TadFormat.contains("<#>")){
			TadFormat = TadFormat.replaceAll("<#>", "(\\\\d)*");
			logger.log(Level.FINEST, "* Found <#>\n"
					+ "* TadFormat changed to " + TadFormat);
		}
		
		// If TadFormatting contains a $, escape it
		// TadFormatting treats dollar sign as a normal character
		if(TadFormat.contains("$")){
			TadFormat = TadFormat.replaceAll("\\$", "\\\\\\$");
			logger.log(Level.FINEST, "* Found $\n"
					+ "* TadFormat changed to " + TadFormat);
		}
		
		// If TadFormatting contains brackets, change from [<whatever>] to (<whatever>)?
		// Brackets represent optionals in TadFormatting
		if(TadFormat.contains("[")){
			TadFormat = TadFormat.replaceAll("\\[", "(");
			TadFormat = TadFormat.replaceAll("]", ")?");
			logger.log(Level.FINEST, "* Found [\n"
					+ "* TadFormat changed to " + TadFormat);
		}
		
		// Log the resulting regex
		logger.log(Level.FINER, "Regex: " + TadFormat);
		
		return TadFormat;
	}
	
	/**
	 * Convert the given Java regex into TadFormatting.
	 * 
	 * @param logger The Logger to log any messages to
	 * @param regex The Java regex to convert to TadFormatting
	 * @return The TadFormatting resulting from the regex conversion
	 */
	public static String convertRegexToTadFormat(Logger logger, String regex){
		// Log the original regex
		logger.log(Level.FINER, "Regex: " + regex);
		
		// Change Java number matching to <#>
		// <#> is TadFormatting's number checking
		if(regex.contains("(\\d)*")){
			regex = regex.replaceAll("\\(\\\\d\\)\\*", "<#>");
			logger.log(Level.FINEST, "* Found (\\d)*\n"
					+ "* Regex changed to " + regex);
		}
		
		// Change boolean checks ("(true|false)") to "<boolean>"
		// <boolean> is TadFormat's way of checking for a boolean
		if(regex.contains("(true|false)")){
			regex = regex.replaceAll("\\(true\\|false\\)", "<boolean>");
			logger.log(Level.FINEST, "* Found (true|false)\n"
					+ "* Regex changed to " + regex);
		}
		
		// Change Boolean checks ("(true|false|null)") to "<Boolean>"
		// <Boolean> is TadFormat's way of checking for a Boolean (which may be null)
		if(regex.contains("(true|false|null)")){
			regex = regex.replaceAll("\\(true\\|false\\|null\\)", "<Boolean>");
			logger.log(Level.FINEST, "* Found (true|false|null)\n"
					+ "* Regex changed to " + regex);
		}
		
		// Change (<whatever>)? to [<whatever>]
		// Bracketed items are how TadFormatting does optionals
		if(regex.contains("(")){
			regex = regex.replaceAll("\\(", "[");
			regex = regex.replaceAll("\\)\\?", "]");
			logger.log(Level.FINEST, "* Found (\n"
					+ "* Regex changed to " + regex);
		}
		
		// Change escaped dollar signs to unescaped ones
		// TadFormatting treats dollar signs as regular characters to match on
		if(regex.contains("\\$")){
			// TODO: Handle regular $'s from regexes
			regex = regex.replaceAll("\\\\\\$", "\\$");
			logger.log(Level.FINEST, "* Found \\$\n"
					+ "* Regex changed to " + regex);
		}
		
		// Replace image file check with <imageFile>
		// TadFormatting checks for image files with <imageFile>
		if(regex.contains(".*\\.jpg")){
			// TODO: Add other image file types
			regex = regex.replaceAll("\\.\\*\\\\.jpg", "<imagefile>");
			logger.log(Level.FINEST, "* Found .*\\.jpg\n"
					+ "* Regex changed to " + regex);
		}
		
		// Change escaped periods to unescaped ones
		// TadFormatting treat periods as regular characters to match on
		if(regex.contains("\\.")){
			regex = regex.replaceAll("\\\\.", ".");
			logger.log(Level.FINEST, "* Found \\.\n"
					+ "* Regex changed to " + regex);
		}
		
		// Change any character matches to <text>
		// TadFormatting checks for an arbitrary length any characters using <text>
		if(regex.contains(".*")){
			regex = regex.replaceAll("\\.\\*", "<text>");
			logger.log(Level.FINEST, "* Found .*\n"
					+ "* Regex changed to " + regex);
		}
		
		// Log the resulting TadFormatting
		logger.log(Level.FINER, "TadFormat: " + regex);
		
		return regex;
	}
}
