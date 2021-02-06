package com.github.tadukoo.parsing.json;

import java.util.List;

/**
 * Abstract JSON Array is a simple implementation of {@link JSONArray} that just holds a List of Objects.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 */
public abstract class AbstractJSONArray implements JSONArray{
	/** The list of items in the array */
	private final List<Object> items;
	
	/**
	 * Creates an array object.
	 *
	 * @param items The list of items in the array
	 */
	protected AbstractJSONArray(List<Object> items){
		this.items = items;
	}
	
	/** {@inheritDoc} */
	@Override
	public List<Object> getItems(){
		return items;
	}
	
	/** {@inheritDoc} */
	@Override
	public int size(){
		return items.size();
	}
}
