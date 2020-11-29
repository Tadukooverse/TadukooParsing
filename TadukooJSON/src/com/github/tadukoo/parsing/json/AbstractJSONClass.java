package com.github.tadukoo.parsing.json;

import com.github.tadukoo.util.pojo.AbstractMappedPojo;
import com.github.tadukoo.util.pojo.MappedPojo;

/**
 * Abstract JSON Class is a simple implementation of {@link JSONClass} that uses {@link AbstractMappedPojo} for its
 * implementation.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.2
 * @since Alpha v.0.1
 */
public abstract class AbstractJSONClass extends AbstractMappedPojo implements JSONClass{
	
	/**
	 * Constructs an AbstractJSONClass with an empty map
	 */
	protected AbstractJSONClass(){
		super();
	}
	
	/**
	 * Constructs an AbstractJSONClass and uses the given pojo for field mappings
	 *
	 * @param pojo A {@link MappedPojo} to use for field mappings
	 */
	protected AbstractJSONClass(MappedPojo pojo){
		super(pojo);
	}
}
