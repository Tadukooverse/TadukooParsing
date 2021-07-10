package com.github.tadukoo.parsing.json;

import com.github.tadukoo.util.logger.EasyLogger;
import com.github.tadukoo.util.pojo.MappedPojo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
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
	
	/**
	 * Grabs the item with the given key as a {@link List}, using the fact that it's a {@link JSONArray}.
	 *
	 * @param key The key for the item to be grabbed
	 * @param clazz The class of items in the list/array
	 * @param <T> The class of items in the list/array
	 * @return A List containing the items from the JSON Array
	 * @throws NoSuchMethodException If we can't find the constructor for the given class
	 * @throws InvocationTargetException If something goes wrong in casting the objects
	 * @throws InstantiationException If something goes wrong in instantiating the objects
	 * @throws IllegalAccessException If we illegally access a constructor while casting
	 */
	@SuppressWarnings("unchecked")
	default <T> JSONArray<T> getJSONArrayItem(String key, Class<T> clazz)
			throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException{
		if(getItem(key) == null){
			return null;
		}
		
		if(clazz == Object.class || clazz == String.class || clazz == Boolean.class || clazz == Double.class){
			// Certain classes can just be cast
			return (JSONArrayList<T>) getItem(key);
		}else if(JSONClass.class.isAssignableFrom(clazz)){
			// If it's a JSONClass, we can use constructors
			Constructor<T> constructor = clazz.getDeclaredConstructor(MappedPojo.class);
			JSONArrayList<JSONClass> classList = (JSONArrayList<JSONClass>) getItem(key);
			
			// Convert the JSONClasses to the proper class
			JSONArrayList<T> itemList = new JSONArrayList<>();
			for(JSONClass jsonClass: classList){
				if(clazz.isInstance(jsonClass)){
					itemList.add((T) jsonClass);
				}else{
					itemList.add(constructor.newInstance(jsonClass));
				}
			}
			return itemList;
		}else{
			throw new IllegalArgumentException("Don't know how to convert with class " + clazz.getCanonicalName());
		}
	}
	
	/**
	 * Helper method to cast an item being stored in this JSON class as a proper {@link JSONArray} easily.
	 * The class specified for the {@link JSONArray} can be a {@link JSONClass}, but if so, it must
	 * have a constructor that accepts a {@link JSONClass}.
	 * <br><br>
	 * This version does not throw any exceptions and will not log any errors, just return null if something
	 * goes wrong
	 *
	 * @param key The key of the item to grab
	 * @param clazz The class to be used in the {@link JSONArray}
	 * @param <T> The class of the items in the {@link JSONArray}
	 * @return The item as a proper {@link JSONArray} instance, or null
	 */
	default <T> JSONArray<T> getJSONArrayItemNoThrow(String key, Class<T> clazz){
		return getJSONArrayItemNoThrow(null, key, clazz);
	}
	
	/**
	 * Helper method to cast an item being stored in this JSON class as a proper {@link JSONArray} easily.
	 * The class specified for the {@link JSONArray} can be a {@link JSONClass}, but if so, it must
	 * have a constructor that accepts a {@link JSONClass}.
	 * <br><br>
	 * This version does not throw any exceptions and will log any errors to the given {@link EasyLogger}, then
	 * return null if something goes wrong
	 *
	 * @param logger The {@link EasyLogger} to log any errors to
	 * @param key The key of the item to grab
	 * @param clazz The class to be used in the {@link JSONArray}
	 * @param <T> The class of the items in the {@link JSONArray}
	 * @return The item as a proper {@link JSONArray} instance, or null
	 */
	default <T> JSONArray<T> getJSONArrayItemNoThrow(EasyLogger logger, String key, Class<T> clazz){
		try{
			return getJSONArrayItem(key, clazz);
		}catch(Exception e){
			if(logger != null){
				logger.logError("Failed to get JSON Array item: " + key, e);
			}
			return null;
		}
	}
}
