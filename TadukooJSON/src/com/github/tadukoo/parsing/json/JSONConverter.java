package com.github.tadukoo.parsing.json;

import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class able to parse JSON into JSON Objects (either a class or array), and to convert classes or objects
 * into JSON strings.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 */
public class JSONConverter{
	/** Pattern to match null (literally just the word null lol) */
	private static final Pattern nullFormat = Pattern.compile("(null)");
	/** Pattern to match a boolean (true or false) */
	private static final Pattern booleanFormat = Pattern.compile("(true|false)");
	/** Pattern to match a number (e.g. 3, 4.192, or 1.928E32) */
	private static final Pattern numberFormat = Pattern.compile("((-)?(\\d)*(\\.\\d*)?(E([+\\-])\\d*)?)");
	/** Pattern to match a string (e.g. "some_string") */
	private static final Pattern stringFormat = Pattern.compile("\"((?:\\\\\"|[^\"])*)\"");
	/** Character at the start of an array - opening bracket - [ */
	public static final char arrayStartChar = '[';
	/** Character at the end of an array - closing bracket - ] */
	public static final char arrayEndChar = ']';
	/** Character at the start of a class - opening brace - { */
	public static final char classStartChar = '{';
	/** Character at the end of a class - closing brace - } */
	public static final char classEndChar = '}';
	/** Character that appears between a key and value - colon - : */
	public static final char keyEndChar = ':';
	/** Character used to signify there's another value after it - comma - , */
	public static final char nextValueChar = ',';
	/**
	 * List of whitespace characters to be ignored - space, tab (\t), form feed (\f), carriage return (\r),
	 * and newline (\n)
	 */
	private static final List<Character> whitespaceChars = Arrays.asList(' ', '\t', '\f', '\r', '\n');
	
	/*
	 * Matchers to use during parsing (they're instantiated at the start of parseJSON)
	 */
	private Matcher nullFormatMatcher;
	private Matcher booleanFormatMatcher;
	private Matcher numberFormatMatcher;
	private Matcher stringFormatMatcher;
	
	/**
	 * Parses the given string into a JSON object (either an array or a class, and returns it.
	 *
	 * @param JSONString The string to be parsed
	 * @return A JSONObject (either a JSONClass or JSONArray)
	 */
	public JSONObject parseJSON(String JSONString){
		int charIndex = 0;
		
		// Initialize matchers
		nullFormatMatcher = nullFormat.matcher(JSONString);
		booleanFormatMatcher = booleanFormat.matcher(JSONString);
		numberFormatMatcher = numberFormat.matcher(JSONString);
		stringFormatMatcher = stringFormat.matcher(JSONString);
		
		// Skip over leading whitespace
		charIndex = skipWhitespace(JSONString, charIndex);
		
		// Determine if we have an array or class
		return switch(JSONString.charAt(charIndex)){
			case arrayStartChar -> parseJSONArray(JSONString, ++charIndex).getLeft();
			case classStartChar -> parseJSONClass(JSONString, ++charIndex).getLeft();
			default -> throw new IllegalArgumentException("String is not valid JSON");
		};
	}
	
	/**
	 * Parses a JSON array, where startIndex is the index of the character AFTER the opening bracket - [
	 *
	 * @param JSONString The JSON string to be parsed
	 * @param startIndex The index of the character AFTER the opening bracket - [
	 * @return The parsed JSONArray, and the index of the first character after the closing bracket
	 */
	private Pair<JSONArray, Integer> parseJSONArray(String JSONString, int startIndex){
		int charIndex = startIndex;
		List<Object> items = new ArrayList<>();
		
		// Remove whitespace from the start (we could have an empty array)
		charIndex = skipWhitespace(JSONString, charIndex);
		
		while(JSONString.charAt(charIndex) != arrayEndChar){
			// Skip over leading whitespace
			charIndex = skipWhitespace(JSONString, charIndex);
			
			// Parse the value and add it to the list
			Pair<Object, Integer> valuePair = parseValue(JSONString, charIndex);
			charIndex = valuePair.getRight();
			items.add(valuePair.getLeft());
			
			// Skip trailing whitespace
			charIndex = skipWhitespace(JSONString, charIndex);
			
			// Ensure we either have the comma for next value or the array is ending
			if(JSONString.length() <= charIndex){
				throw new IllegalStateException("JSON Array ended prematurely");
			}
			if(JSONString.charAt(charIndex) == nextValueChar){
				charIndex++;
			}else if(JSONString.charAt(charIndex) != arrayEndChar){
				throw new IllegalStateException("End of JSON array not marked with closing bracket");
			}
		}
		
		// Build the JSON Array finally and return charIndex for recursion
		// charIndex is incremented due to the ending array character
		return Pair.of(new AbstractJSONArray(items){ }, ++charIndex);
	}
	
	/**
	 * Parses a JSON class, where startIndex is the index of the character AFTER the opening brace - {
	 *
	 * @param JSONString The JSON string to be parsed
	 * @param startIndex The index of the character AFTER the opening brace - {
	 * @return The parsed JSONClass, and the index of the first character after the closing brace
	 */
	private Pair<JSONClass, Integer> parseJSONClass(String JSONString, int startIndex){
		int charIndex = startIndex;
		List<String> keys = new ArrayList<>();
		JSONClass clazz = new AbstractOrderedJSONClass(){
			@Override
			public List<String> getKeyOrder(){
				return keys;
			}
		};
		
		// Remove whitespace from the start (we could have an empty class)
		charIndex = skipWhitespace(JSONString, charIndex);
		
		while(JSONString.charAt(charIndex) != classEndChar){
			// Skip over leading whitespace
			charIndex = skipWhitespace(JSONString, charIndex);
			
			// Grab the first key
			String key = matchAtStartOrError(stringFormatMatcher, charIndex);
			charIndex = stringFormatMatcher.end();
			
			// Skip over whitespace
			charIndex = skipWhitespace(JSONString, charIndex);
			
			// Eat/Ensure colon
			if(JSONString.charAt(charIndex) != keyEndChar){
				throw new IllegalStateException("Didn't find a colon in a key-value pair in JSON class");
			}
			charIndex++;
			
			// Skip over whitespace
			charIndex = skipWhitespace(JSONString, charIndex);
			
			// Parse the value and add it to the map
			Pair<Object, Integer> valuePair = parseValue(JSONString, charIndex);
			charIndex = valuePair.getRight();
			keys.add(key);
			clazz.setItem(key, valuePair.getLeft());
			
			// Skip trailing whitespace
			charIndex = skipWhitespace(JSONString, charIndex);
			
			// Ensure we either have the comma for next value or the class is ending
			if(JSONString.length() <= charIndex){
				throw new IllegalStateException("JSON Class ended prematurely");
			}
			if(JSONString.charAt(charIndex) == nextValueChar){
				charIndex++;
			}else if(JSONString.charAt(charIndex) != classEndChar){
				throw new IllegalStateException("End of JSON class not marked with closing brace");
			}
		}
		
		// Build the JSON Class finally and return charIndex for recursion
		// charIndex is incremented due to the ending class character
		return Pair.of(clazz, ++charIndex);
	}
	
	/**
	 * Method used to parse a value in a JSON object.
	 *
	 * @param JSONString The JSON string being parsed
	 * @param startIndex The start index of the value
	 * @return The parsed Object value and the new character index after the value is over
	 */
	private Pair<Object, Integer> parseValue(String JSONString, int startIndex){
		int charIndex = startIndex;
		Object value = switch(JSONString.charAt(charIndex)){
			case '"' -> {
				String str = matchAtStartOrError(stringFormatMatcher, charIndex);
				charIndex = stringFormatMatcher.end();
				yield str;
			}
			case 't', 'f' -> {
				boolean bool = Boolean.parseBoolean(matchAtStartOrError(booleanFormatMatcher, charIndex));
				charIndex = booleanFormatMatcher.end();
				yield bool;
			}
			case 'n' -> {
				matchAtStartOrError(nullFormatMatcher, charIndex);
				charIndex = nullFormatMatcher.end();
				yield null;
			}
			case '-', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
				double doub = Double.parseDouble(matchAtStartOrError(numberFormatMatcher, charIndex));
				charIndex = numberFormatMatcher.end();
				yield doub;
			}
			case classStartChar -> {
				Pair<JSONClass, Integer> clazzPair = parseJSONClass(JSONString, ++charIndex);
				charIndex = clazzPair.getRight();
				yield clazzPair.getLeft();
			}
			case arrayStartChar -> {
				Pair<JSONArray, Integer> arrayPair = parseJSONArray(JSONString, ++charIndex);
				charIndex = arrayPair.getRight();
				yield arrayPair.getLeft();
			}
			default -> throw new IllegalArgumentException("Unknown character at start of JSON value: " +
					JSONString.charAt(charIndex));
		};
		return Pair.of(value, charIndex);
	}
	
	/**
	 * Advances the index past any whitespace characters.
	 *
	 * @param JSONString The String to look at
	 * @param startIndex The current index
	 * @return The index after all the whitespace is ignored
	 */
	private int skipWhitespace(String JSONString, int startIndex){
		int charIndex = startIndex;
		while(JSONString.length() > charIndex && whitespaceChars.contains(JSONString.charAt(charIndex))){
			charIndex++;
		}
		return charIndex;
	}
	
	/**
	 * Ensures that a match is found starting at the given index, and returns the match (on group 1, for cases of
	 * excluding surrounding info, such as quotes on a String). If there's no match, or the match doesn't start at
	 * the given index, an IllegalStateException is thrown
	 *
	 * @param matcher The Matcher to use in matching
	 * @param index The index the match should start at
	 * @return The matched value (group 1)
	 */
	private String matchAtStartOrError(Matcher matcher, int index){
		if(!matcher.find(index) || matcher.start() != index){
			throw new IllegalStateException("Matching failed");
		}
		return matcher.group(1);
	}
	
	/**
	 * Converts the given object into the proper form for use in JSON.<br>
	 * If it's a {@link JSONObject}, then we just call {@link JSONObject#convertToJSON(JSONConverter)} on it.
	 * null, true, false, strings, and numbers are handled as JSON expects.<br>
	 * If it doesn't fall into one of those categories, an error is thrown.
	 *
	 * @param obj The object to convert to a JSON string
	 * @return The JSON string representing the given object.
	 */
	public String convertToJSON(Object obj){
		if(obj == null){
			return "null";
		}else if(obj instanceof JSONObject jsonObj){
			return jsonObj.convertToJSON(this);
		}else if(obj instanceof Boolean b){
			return String.valueOf(b);
		}else if(obj instanceof Integer i){
			return String.valueOf(i);
		}else if(obj instanceof Short s){
			return String.valueOf(s);
		}else if(obj instanceof Long l){
			return String.valueOf(l);
		}else if(obj instanceof Float f){
			return Float.toString(f);
		}else if(obj instanceof Double d){
			return Double.toString(d);
		}else if(obj instanceof String s){
			return "\"" + s + "\"";
		}else{
			throw new IllegalArgumentException("Unknown how to convert object into JSON string" +
					"type: " + obj.getClass().getCanonicalName());
		}
	}
}
