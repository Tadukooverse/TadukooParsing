package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;
import com.github.tadukoo.util.StringUtil;

/**
 * Represents a {@link Transformation} where the text is changed to camelCase
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class CamelCaseTransformation extends AbstractCaseTransformation{
	
	/**
	 * Constructs a new {@link CamelCaseTransformation}
	 */
	public CamelCaseTransformation(){
		super(TransformationType.CAMEL_CASE);
	}
	
	/** {@inheritDoc} */
	@Override
	public String applyTransformation(String text){
		return StringUtil.toCamelCase(text);
	}
}
