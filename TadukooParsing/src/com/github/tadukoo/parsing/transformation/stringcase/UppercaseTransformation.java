package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;

/**
 * Represents a {@link Transformation} that changes the text to uppercase
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class UppercaseTransformation extends AbstractCaseTransformation{
	
	/**
	 * Constructs a new {@link UppercaseTransformation}
	 */
	public UppercaseTransformation(){
		super(TransformationType.UPPERCASE);
	}
	
	/** {@inheritDoc} */
	@Override
	public String applyTransformation(String text){
		return text.toUpperCase();
	}
}
