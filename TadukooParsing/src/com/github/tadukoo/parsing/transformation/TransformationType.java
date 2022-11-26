package com.github.tadukoo.parsing.transformation;

import com.github.tadukoo.util.StringUtil;

/**
 * An enum for types of {@link Transformation transformations}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public enum TransformationType{
	/** Represents a {@link Transformation} where the text is changed to lowercase */
	LOWERCASE("lowercase"),
	/** Represents a {@link Transformation} where the text is changed to uppercase */
	UPPERCASE("UPPERCASE"),
	/** Represents a {@link Transformation} where the text is changed to snake_case */
	SNAKE_CASE("snake_case"),
	/** Represents a {@link Transformation} where the text is changed to camelCase */
	CAMEL_CASE("camelCase"),
	/** Represents a {@link Transformation} where the text is changed to PascalCase */
	PASCAL_CASE("PascalCase"),
	/** Represents a {@link Transformation} where part of the text gets replaced */
	REPLACE("replace");
	
	/** The {@link TransformationType} represented as a string */
	private final String asString;
	
	/**
	 * Constructs a {@link TransformationType}
	 *
	 * @param asString The {@link TransformationType} represented as a string
	 */
	TransformationType(String asString){
		this.asString = asString;
	}
	
	/**
	 * @param text A string to turn into a {@link TransformationType}
	 * @return The {@link TransformationType} that uses the matching given string, or {@code null}
	 */
	public static TransformationType fromString(String text){
		for(TransformationType type: values()){
			if(StringUtil.equalsIgnoreCase(type.asString, text)){
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
