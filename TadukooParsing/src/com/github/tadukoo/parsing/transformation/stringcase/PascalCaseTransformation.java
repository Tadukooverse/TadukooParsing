package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;
import com.github.tadukoo.util.StringUtil;

/**
 * Represents a {@link Transformation} where the text is changed to PascalCase
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class PascalCaseTransformation extends AbstractCaseTransformation{
	
	/**
	 * Constructs a new {@link PascalCaseTransformation}
	 */
	public PascalCaseTransformation(){
		super(TransformationType.PASCAL_CASE);
	}
	
	/** {@inheritDoc} */
	@Override
	public String applyTransformation(String text){
		return StringUtil.toPascalCase(text);
	}
}
