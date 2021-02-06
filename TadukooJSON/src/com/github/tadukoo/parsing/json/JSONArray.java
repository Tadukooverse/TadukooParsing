package com.github.tadukoo.parsing.json;

import java.util.List;
import java.util.stream.Collectors;

/**
 * JSON Array represents an array of objects in JSON. It's stored as a List of Objects.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 */
public interface JSONArray extends JSONObject{
	
	/**
	 * @return The list of items in the array
	 */
	List<Object> getItems();
	
	/**
	 * @return The number of items in the array
	 */
	int size();
	
	/** {@inheritDoc} */
	@Override
	default String convertToJSON(JSONConverter converter){
		StringBuilder JSONStringBuilder = new StringBuilder(String.valueOf(JSONConverter.arrayStartChar));
		List<Object> items = getItems();
		if(!items.isEmpty()){
			// Convert each item to a JSON string representation
			JSONStringBuilder.append(items.stream()
					.map(converter::convertToJSON)
					.collect(Collectors.joining(String.valueOf(JSONConverter.nextValueChar))));
		}
		JSONStringBuilder.append(JSONConverter.arrayEndChar);
		return JSONStringBuilder.toString();
	}
}
