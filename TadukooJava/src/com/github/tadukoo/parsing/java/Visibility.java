package com.github.tadukoo.parsing.java;

import com.github.tadukoo.util.StringUtil;

/**
 * Visibility represents the visibility of a given Java class, method, etc.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.2
 */
public enum Visibility{
	/** Used for public visibility - anything can see it */
	PUBLIC("public"),
	/** Used for protected visibility - only the current class and subclasses can see it */
	PROTECTED("protected"),
	/** Used for private visibility - only the current class can see it */
	PRIVATE("private"),
	/** Used when there's no specified visibility (e.g. interface methods, where public is implied) */
	NONE("");
	
	/** The text to use for the visibility */
	private final String text;
	
	/**
	 * Creates a new Visibility enum with the given text
	 *
	 * @param text The text to use for the visibility
	 */
	Visibility(String text){
		this.text = text;
	}
	
	/**
	 * @return The text to use for the visibility
	 */
	public String getText(){
		return text;
	}
	
	/**
	 * Grabs the {@link Visibility} that corresponds to the given text
	 *
	 * @param text The text used for the visibility (to use in searching)
	 * @return The found {@link Visibility}, or null
	 */
	public static Visibility fromText(String text){
		for(Visibility visibility: values()){
			if(StringUtil.equalsIgnoreCase(visibility.getText(), text)){
				return visibility;
			}
		}
		return null;
	}
}
