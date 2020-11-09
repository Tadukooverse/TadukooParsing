package com.github.tadukoo.parsing.json;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class JSONConverterTest{
	private final JSONConverter converter = new JSONConverter();
	private final String nullType = "null_type";
	private final String boolTypeTrue = "bool_type_true";
	private final String boolTypeFalse = "bool_type_false";
	private final String stringType = "string_type";
	private final String classType = "class_type";
	
	@Test
	public void testSingleClass(){
		String JSON = "{ " +
				"\n  \"" + nullType + "\": null," +
				"\n  \"" + boolTypeTrue + "\": true," +
				"\n  \"" + boolTypeFalse + "\": false," +
				"\n  \"" + stringType + "\": \"test_string\"" +
				"\n}";
		
		JSONObject obj = converter.parseJSON(JSON);
		if(obj instanceof JSONClass clazz){
			// Verify keys
			Set<String> keys = clazz.getKeys();
			assertEquals(4, keys.size());
			assertTrue(keys.contains(nullType));
			assertTrue(keys.contains(boolTypeTrue));
			assertTrue(keys.contains(boolTypeFalse));
			assertTrue(keys.contains(stringType));
			
			// Verify items
			Map<String, Object> items = clazz.getMap();
			assertEquals(4, items.size());
			assertNull(items.get(nullType));
			assertEquals(true, items.get(boolTypeTrue));
			assertEquals(false, items.get(boolTypeFalse));
			assertEquals("test_string", items.get(stringType));
		}else{
			throw new IllegalStateException("Didn't get a JSONClass object");
		}
	}
	
	@Test
	public void testClassInsideClass(){
		String JSON = "{ " +
				"\n  \"" + nullType + "\": null," +
				"\n  \"" + classType + "\": {" +
				"\n    \"" + boolTypeFalse + "\": false," +
				"\n    \"" + boolTypeTrue + "\": true" +
				"\n  }," +
				"\n  \"" + stringType + "\": \"test_string\"" +
				"\n}";
		
		JSONObject obj = converter.parseJSON(JSON);
		if(obj instanceof JSONClass clazz){
			// Verify keys
			Set<String> keys = clazz.getKeys();
			assertEquals(3, keys.size());
			assertTrue(keys.contains(nullType));
			assertTrue(keys.contains(classType));
			assertTrue(keys.contains(stringType));
			
			// Verify items
			Map<String, Object> items = clazz.getMap();
			assertEquals(3, items.size());
			assertNull(items.get(nullType));
			assertEquals("test_string", items.get(stringType));
			
			// Verify the sub-class item
			Object subObj = items.get(classType);
			if(subObj instanceof JSONClass subClazz){
				// Verify keys
				Set<String> subKeys = subClazz.getKeys();
				assertEquals(2, subKeys.size());
				assertTrue(subKeys.contains(boolTypeFalse));
				assertTrue(subKeys.contains(boolTypeTrue));
				
				// Verify items
				Map<String, Object> subItems = subClazz.getMap();
				assertEquals(2, subItems.size());
				assertEquals(false, subItems.get(boolTypeFalse));
				assertEquals(true, subItems.get(boolTypeTrue));
			}else{
				throw new IllegalStateException("Sub-class is not a JSONClass object");
			}
		}else{
			throw new IllegalStateException("Didn't get a JSONClass object");
		}
	}
	
	@Test
	public void testSingleArray(){
		String JSON = """
                [\s
				  null,
				  true,
				  "test_string"
				]
				""";
		
		JSONObject obj = converter.parseJSON(JSON);
		if(obj instanceof JSONArray array){
			List<Object> items = array.getItems();
			assertEquals(3, items.size());
			assertNull(items.get(0));
			assertEquals(true, items.get(1));
			assertEquals("test_string", items.get(2));
		}else{
			throw new IllegalStateException("Didn't get a JSONArray object");
		}
	}
	
	@Test
	public void testArrayInArray(){
		String JSON = """
				[\s
				  null,
				  [
				    true,
				    false,
				    2.0,
				    "test_string"
				  ],
				  false
				]
				""";
		
		JSONObject obj = converter.parseJSON(JSON);
		if(obj instanceof JSONArray array){
			List<Object> items = array.getItems();
			assertEquals(3, items.size());
			assertNull(items.get(0));
			assertEquals(false, items.get(2));
			
			// Verify inner array
			Object subObj = items.get(1);
			if(subObj instanceof JSONArray subArray){
				List<Object> subItems = subArray.getItems();
				assertEquals(4, subItems.size());
				assertEquals(true, subItems.get(0));
				assertEquals(false, subItems.get(1));
				assertEquals(2.0, subItems.get(2));
				assertEquals("test_string", subItems.get(3));
			}
		}
	}
	
	@Test
	public void testInvalidJSON(){
		String JSON = """
				not a [ or }
				""";
		
		try{
			converter.parseJSON(JSON);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("String is not valid JSON", e.getMessage());
		}
	}
	
	@Test
	public void testInvalidArrayMissingEndChar(){
		String JSON = """
				[\s
				  null,
				  [
				    true,
				    false,
				    "test_string"
				  ],
				  false
				""";
		
		try{
			converter.parseJSON(JSON);
			fail();
		}catch(IllegalStateException e){
			assertEquals("JSON Array ended prematurely", e.getMessage());
		}
	}
	
	@Test
	public void testInvalidArrayWrongEndChar(){
		String JSON = """
				[\s
				  null,
				  [
				    true,
				    false,
				    "test_string"
				  ],
				  false
				}
				""";
		
		try{
			converter.parseJSON(JSON);
			fail();
		}catch(IllegalStateException e){
			assertEquals("End of JSON array not marked with closing bracket", e.getMessage());
		}
	}
	
	@Test
	public void testInvalidClassMissingColon(){
		String JSON = "{ " +
				"\n  \"" + nullType + "\": null," +
				"\n  \"" + classType + "\" {" +
				"\n    \"" + boolTypeFalse + "\": false," +
				"\n    \"" + boolTypeTrue + "\": true" +
				"\n  }," +
				"\n  \"" + stringType + "\": \"test_string\"" +
				"\n}";
		
		try{
			converter.parseJSON(JSON);
			fail();
		}catch(IllegalStateException e){
			assertEquals("Didn't find a colon in a key-value pair in JSON class", e.getMessage());
		}
	}
	
	@Test
	public void testInvalidClassMissingEndBrace(){
		String JSON = "{ " +
				"\n  \"" + nullType + "\": null," +
				"\n  \"" + classType + "\": {" +
				"\n    \"" + boolTypeFalse + "\": false," +
				"\n    \"" + boolTypeTrue + "\": true" +
				"\n  }," +
				"\n  \"" + stringType + "\": \"test_string\"" +
				"\n";
		
		try{
			converter.parseJSON(JSON);
			fail();
		}catch(IllegalStateException e){
			assertEquals("JSON Class ended prematurely", e.getMessage());
		}
	}
	
	@Test
	public void testInvalidClassWrongEndCharacter(){
		String JSON = "{ " +
				"\n  \"" + nullType + "\": null," +
				"\n  \"" + classType + "\": {" +
				"\n    \"" + boolTypeFalse + "\": false," +
				"\n    \"" + boolTypeTrue + "\": true" +
				"\n  }," +
				"\n  \"" + stringType + "\": \"test_string\"" +
				"\n]";
		
		try{
			converter.parseJSON(JSON);
			fail();
		}catch(IllegalStateException e){
			assertEquals("End of JSON class not marked with closing brace", e.getMessage());
		}
	}
	
	@Test
	public void testNullConversion(){
		assertEquals("null", converter.convertToJSON(null));
	}
	
	@Test
	public void testStringConversion(){
		assertEquals("\"test_string\"", converter.convertToJSON("test_string"));
	}
	
	@Test
	public void testTrueConversion(){
		assertEquals("true", converter.convertToJSON(true));
	}
	
	@Test
	public void testFalseConversion(){
		assertEquals("false", converter.convertToJSON(false));
	}
	
	@Test
	public void testIntConversion(){
		assertEquals("39482203", converter.convertToJSON(39482203));
	}
	
	@Test
	public void testShortConversion(){
		assertEquals("5", converter.convertToJSON((short) 5));
	}
	
	@Test
	public void testLongConversion(){
		assertEquals("3938393", converter.convertToJSON(3938393L));
	}
	
	@Test
	public void testFloatConversion(){
		float f = 1829384;
		assertEquals(Float.toString(f), converter.convertToJSON(f));
	}
	
	@Test
	public void testDoubleConversion(){
		double d = 93285835723842374923472394.33;
		assertEquals(Double.toString(d), converter.convertToJSON(d));
	}
	
	@Test
	public void testJSONObjectConversion(){
		JSONClass clazz = new AbstractJSONClass(){ };
		clazz.setItem("test", "whatever");
		assertEquals(clazz.convertToJSON(converter), converter.convertToJSON(clazz));
	}
	
	@Test
	public void testUnknownObjectConversion(){
		class GarbageClass{ }
		try{
			assertEquals("this won't match", converter.convertToJSON(new GarbageClass()));
		}catch(IllegalArgumentException e){
			// We should be reaching this properly. This is good
		}
	}
}
