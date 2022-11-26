package com.github.tadukoo.parsing.transformation;

import java.util.List;

/**
 * Represents a transformation that can be applied to text
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public interface Transformation{
	
	/**
	 * @return The {@link TransformationType type} of {@link Transformation}
	 */
	TransformationType getType();
	
	/**
	 * @return The List of parameters for the {@link Transformation}
	 */
	List<String> getParameters();
	
	/**
	 * @return The List of {@link Transformation transformations} contained within this {@link Transformation}
	 */
	List<Transformation> getSubTransformations();
	
	/**
	 * Applies the {@link Transformation} to the given text
	 *
	 * @param text The text to apply the {@link Transformation} to
	 * @return The result of applying the {@link Transformation} to the given text
	 */
	String applyTransformation(String text);
}
