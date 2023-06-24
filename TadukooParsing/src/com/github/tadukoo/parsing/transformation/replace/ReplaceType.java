package com.github.tadukoo.parsing.transformation.replace;

import com.github.tadukoo.util.StringUtil;

/**
 * The General Replacement Type of a {@link ReplaceTransformation}
 * (e.g. ALL / FIRST)
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public enum ReplaceType{
	/** Replaces all instances found */
	ALL("ALL"),
	/** Replaces only the first instance found */
	FIRST("FIRST");
	
	/** The String representation of the {@link ReplaceType type} */
	private final String asString;
	
	/**
	 * Constructs a new {@link ReplaceType}
	 *
	 * @param asString The String representation of the {@link ReplaceType type}
	 */
	ReplaceType(String asString){
		this.asString = asString;
	}
	
	/**
	 * @param text The String to use to match to a {@link ReplaceType type}
	 * @return The matched {@link ReplaceType type}, or {@code null}
	 */
	public static ReplaceType fromString(String text){
		for(ReplaceType type: values()){
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
