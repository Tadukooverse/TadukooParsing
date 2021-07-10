package com.github.tadukoo.parsing.json;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON Array List is a simple implementation of {@link JSONArray} that extends {@link ArrayList}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3.1
 * @since Alpha v.0.1
 */
public class JSONArrayList<T> extends ArrayList<T> implements JSONArray<T>{
	
	/**
	 * Creates an array object that's empty.
	 */
	public JSONArrayList(){
	
	}
	
	/**
	 * Creates an array object with the given items.
	 *
	 * @param items The list of items in the array
	 */
	public JSONArrayList(List<T> items){
		addAll(items);
	}
	
	/** {@inheritDoc} */
	@Override
	public List<T> getItems(){
		return this;
	}
}
