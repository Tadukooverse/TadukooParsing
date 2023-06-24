package com.github.tadukoo.parsing.transformation.replace;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.util.StringUtil;

/**
 * Represents the type to use for what to replace a matched String with in a {@link ReplaceTransformation}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public enum ReplaceToType{
	/** Represents using an exact string */
	EXACT_STRING("EXACT STRING"),
	/** Represents using a {@link Transformation} */
	TRANSFORMATION("TRANSFORMATION");
	
	/** The String representation of the {@link ReplaceToType type} */
	private final String asString;
	
	/**
	 * Constructs a new {@link ReplaceToType}
	 *
	 * @param asString The String representation of the {@link ReplaceToType type}
	 */
	ReplaceToType(String asString){
		this.asString = asString;
	}
	
	/**
	 * @param text A String to use to match to a {@link ReplaceToType}
	 * @return The matched {@link ReplaceToType} or {@code null}
	 */
	public static ReplaceToType fromString(String text){
		for(ReplaceToType type: values()){
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
