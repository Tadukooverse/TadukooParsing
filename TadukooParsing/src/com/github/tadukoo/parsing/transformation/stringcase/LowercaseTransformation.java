package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;

/**
 * Represents a {@link Transformation} that changes the case of the text to lowercase
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class LowercaseTransformation extends AbstractCaseTransformation{
	
	/**
	 * Constructs a new {@link LowercaseTransformation}
	 */
	public LowercaseTransformation(){
		super(TransformationType.LOWERCASE);
	}
	
	/** {@inheritDoc} */
	@Override
	public String applyTransformation(String text){
		return text.toLowerCase();
	}
}
