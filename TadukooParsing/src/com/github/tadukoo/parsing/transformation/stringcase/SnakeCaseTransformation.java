package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;
import com.github.tadukoo.util.StringUtil;

/**
 * Represents a {@link Transformation} where the text is changed to snake_case
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class SnakeCaseTransformation extends AbstractCaseTransformation{
	
	/**
	 * Constructs a new {@link SnakeCaseTransformation}
	 */
	public SnakeCaseTransformation(){
		super(TransformationType.SNAKE_CASE);
	}
	
	/** {@inheritDoc} */
	@Override
	public String applyTransformation(String text){
		return StringUtil.toSnakeCase(text);
	}
}
