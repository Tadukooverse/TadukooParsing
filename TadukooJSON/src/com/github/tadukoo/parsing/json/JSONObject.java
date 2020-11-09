package com.github.tadukoo.parsing.json;

/**
 * Represents an object in JSON, whether it be an {@link JSONArray array}, a {@link JSONClass class},
 * or something else.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.1
 */
public interface JSONObject{
	
	/**
	 * Converts this JSON object into an actual JSON string
	 *
	 * @param converter A JSON converter used for converting sub-objects if needed
	 * @return A JSON string representing this object
	 */
	String convertToJSON(JSONConverter converter);
}
