package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a {@link Transformation} that's just a case change of the text
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public abstract class AbstractCaseTransformation implements Transformation{
	/** The {@link TransformationType type} of the transformation */
	private final TransformationType type;
	
	/**
	 * Constructs a new {@link AbstractCaseTransformation} with the given {@link TransformationType}
	 *
	 * @param type The {@link TransformationType type} of the transformation
	 */
	protected AbstractCaseTransformation(TransformationType type){
		this.type = type;
	}
	
	/** {@inheritDoc} */
	@Override
	public TransformationType getType(){
		return type;
	}
	
	/** {@inheritDoc} */
	@Override
	public List<String> getParameters(){
		return new ArrayList<>();
	}
	
	/** {@inheritDoc} */
	@Override
	public List<Transformation> getSubTransformations(){
		return new ArrayList<>();
	}
}
