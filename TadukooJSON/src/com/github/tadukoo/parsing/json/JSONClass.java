package com.github.tadukoo.parsing.json;

import com.github.tadukoo.util.pojo.MappedPojo;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * JSON Class represents a collection of named values in JSON. It's represented using {@link MappedPojo}.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 */
public interface JSONClass extends JSONObject, MappedPojo{
	
	/** {@inheritDoc} */
	@Override
	default String convertToJSON(JSONConverter converter){
		return buildJSON(getKeys(), converter);
	}
	
	/**
	 * Builds the JSON string using the given Collection of keys and a {@link JSONConverter}. This method is used
	 * in case we need to get the keys in a different way in sub-classes (and {@link #convertToJSON(JSONConverter)}
	 * can be overridden and used to pass the keys differently).
	 *
	 * @param keys The Collection of keys to be used - may be ordered or not ordered
	 * @param converter The {@link JSONConverter} to use for converting
	 * @return The JSON string representation of this JSONClass
	 */
	default String buildJSON(Collection<String> keys, JSONConverter converter){
		StringBuilder JSONStringBuilder = new StringBuilder(String.valueOf(JSONConverter.classStartChar));
		Map<String, Object> items = getMap();
		if(!keys.isEmpty()){
			// Convert each item to a JSON string representation
			JSONStringBuilder.append(keys.stream()
					.map(key -> "\"" + key + "\"" + JSONConverter.keyEndChar + converter.convertToJSON(items.get(key)))
					.collect(Collectors.joining(String.valueOf(JSONConverter.nextValueChar))));
		}
		JSONStringBuilder.append(JSONConverter.classEndChar);
		return JSONStringBuilder.toString();
	}
}
