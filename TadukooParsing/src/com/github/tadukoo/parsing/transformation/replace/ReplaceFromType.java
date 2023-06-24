package com.github.tadukoo.parsing.transformation.replace;

import com.github.tadukoo.util.StringUtil;

/**
 * Represents the {@link ReplaceFromType type} to be used for the String to match to be replaced in a
 * {@link ReplaceTransformation}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public enum ReplaceFromType{
	/** Represents a Java regex is to be used for matching the String to be replaced */
	REGEX("REGEX"),
	/** Represents an exact String is to be used for matching the String to be replaced */
	EXACT_STRING("EXACT STRING");
	
	/** The String representation of the {@link ReplaceFromType type} */
	private final String asString;
	
	/**
	 * Constructs a new {@link ReplaceFromType}
	 *
	 * @param asString The String representation of the {@link ReplaceFromType type}
	 */
	ReplaceFromType(String asString){
		this.asString = asString;
	}
	
	/**
	 * @param text The String to use to match to a {@link ReplaceFromType}
	 * @return The matched {@link ReplaceFromType} or {@code null}
	 */
	public static ReplaceFromType fromString(String text){
		for(ReplaceFromType type: values()){
			if(StringUtil.equalsIgnoreCase(text, type.asString)){
				return type;
			}
		}
		return null;
	}
	
	/** {@inheritDoc} */
	@Override
	public String toString(){
		return asString;
	}
}
