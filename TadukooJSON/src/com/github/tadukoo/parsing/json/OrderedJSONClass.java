package com.github.tadukoo.parsing.json;

import com.github.tadukoo.util.pojo.OrderedMappedPojo;

/**
 * JSON Class represents a collection of named values in JSON, where order matters.
 * It's represented using {@link OrderedMappedPojo}.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 */
public interface OrderedJSONClass extends JSONClass, OrderedMappedPojo{
	
	/** {@inheritDoc} */
	@Override
	default String convertToJSON(JSONConverter converter){
		// Pass in ordered keys instead of random(?) order keys
		return buildJSON(getKeyOrder(), converter);
	}
}
