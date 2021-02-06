package com.github.tadukoo.parsing;

import java.util.regex.Pattern;

/**
 * Common Patterns is an interface containing common {@link Pattern}s for use in parsing
 *
 * @author Logan Ferree (Tadukoo)
 * @since Alpha v.0.3
 */
public interface CommonPatterns{
	
	/** Pattern to match null (literally just the word null lol) */
	Pattern nullFormat = Pattern.compile("(null)");
	
	/** Pattern to match a boolean (true or false) */
	Pattern booleanFormat = Pattern.compile("(true|false)");
	
	/** Pattern to match a number (e.g. 3, 4.192, or 1.928E+32) */
	Pattern numberFormat = Pattern.compile("((-)?(\\d)*(\\.\\d*)?(E([+\\-])\\d*)?)");
	
	/** Pattern to match a quoted string (e.g. "some_string") */
	Pattern quotedStringFormat = Pattern.compile("\"((?:\\\\\"|[^\"])*)\"");
}
