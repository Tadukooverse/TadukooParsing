package com.github.tadukoo.parsing.json;

import com.github.tadukoo.util.pojo.MappedPojo;

/**
 * Abstract Ordered JSON Class is a simple implementation of {@link OrderedJSONClass} that extends
 * {@link AbstractJSONClass}. Subclasses will need to implement the key order.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.2
 * @since Alpha v.0.1
 */
public abstract class AbstractOrderedJSONClass extends AbstractJSONClass implements OrderedJSONClass{
	
	/**
	 * Constructs an AbstractOrderedJSONClass with an empty map
	 */
	protected AbstractOrderedJSONClass(){
		super();
	}
	
	/**
	 * Constructs an AbstractOrderedJSONClass and uses the given pojo for field mappings
	 *
	 * @param pojo A {@link MappedPojo} to use for field mappings
	 */
	protected AbstractOrderedJSONClass(MappedPojo pojo){
		super(pojo);
	}
}
