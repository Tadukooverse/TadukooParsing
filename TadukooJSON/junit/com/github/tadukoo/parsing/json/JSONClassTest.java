package com.github.tadukoo.parsing.json;

import com.github.tadukoo.junit.logger.JUnitEasyLogger;
import com.github.tadukoo.util.map.MapUtil;
import com.github.tadukoo.util.pojo.AbstractMappedPojo;
import com.github.tadukoo.util.pojo.MappedPojo;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.*;

public class JSONClassTest{
	private JSONClass clazz = new AbstractJSONClass(){ };
	
	public static class TestClass extends AbstractJSONClass{
		
		public TestClass(){ }
		
		@SuppressWarnings("unused")
		public TestClass(MappedPojo pojo){
			super(pojo);
		}
	}
	
	@Test
	public void testConstructor(){
		assertTrue(clazz.getMap().isEmpty());
	}
	
	@Test
	public void testPojoConstructor(){
		clazz = new AbstractJSONClass(new AbstractMappedPojo(){
			@Override
			public Map<String, Object> getMap(){
				return MapUtil.createMap(Pair.of("Derp", 5), Pair.of("Test", true));
			}
		}){ };
		Map<String, Object> map = clazz.getMap();
		assertFalse(map.isEmpty());
		assertTrue(map.containsKey("Derp"));
		assertEquals(5, map.get("Derp"));
		assertTrue(map.containsKey("Test"));
		assertTrue((Boolean) map.get("Test"));
	}
	
	@Test
	public void testHasKeyFalse(){
		assertFalse(clazz.hasKey("Derp"));
	}
	
	@Test
	public void testHasKeyTrue(){
		clazz.setItem("Derp", 5);
		assertTrue(clazz.hasKey("Derp"));
	}
	
	@Test
	public void testGetKeysEmpty(){
		assertTrue(clazz.getKeys().isEmpty());
	}
	
	@Test
	public void testGetKeysPopulated(){
		clazz.setItem("Derp", 5);
		clazz.setItem("Test", "Yes");
		Set<String> keys = clazz.getKeys();
		assertFalse(keys.isEmpty());
		assertTrue(keys.contains("Derp"));
		assertTrue(keys.contains("Test"));
	}
	
	@Test
	public void testHasItemNotSet(){
		assertFalse(clazz.hasItem("Test"));
	}
	
	@Test
	public void testHasItemSetToNull(){
		clazz.setItem("Test", null);
		assertFalse(clazz.hasItem("Test"));
	}
	
	@Test
	public void testHasItemTrue(){
		clazz.setItem("Derp", 5);
		assertTrue(clazz.hasItem("Derp"));
	}
	
	@Test
	public void testGetItemNotSet(){
		assertNull(clazz.getItem("Derp"));
	}
	
	@Test
	public void testGetItem(){
		clazz.setItem("Derp", 5);
		assertEquals(5, clazz.getItem("Derp"));
	}
	
	@Test
	public void testSetItem(){
		clazz.setItem("Derp", 5);
		assertTrue(clazz.hasKey("Derp"));
		assertTrue(clazz.hasItem("Derp"));
		assertEquals(5, clazz.getItem("Derp"));
	}
	
	@Test
	public void testRemoveItem(){
		clazz.setItem("Derp", 5);
		assertTrue(clazz.hasKey("Derp"));
		assertTrue(clazz.hasItem("Derp"));
		assertEquals(5, clazz.getItem("Derp"));
		
		clazz.removeItem("Derp");
		assertFalse(clazz.hasKey("Derp"));
		assertFalse(clazz.hasItem("Derp"));
		assertNull(clazz.getItem("Derp"));
	}
	
	@Test
	public void testGetMapEmpty(){
		Map<String, Object> map = clazz.getMap();
		assertTrue(map.isEmpty());
	}
	
	@Test
	public void testGetMap(){
		clazz.setItem("Derp", 5);
		clazz.setItem("Test", "Yes");
		
		Map<String, Object> map = clazz.getMap();
		assertFalse(map.isEmpty());
		assertEquals(2, map.keySet().size());
		assertTrue(map.containsKey("Derp"));
		assertEquals(5, map.get("Derp"));
		assertTrue(map.containsKey("Test"));
		assertEquals("Yes", map.get("Test"));
	}
	
	@Test
	public void testConvertToJSONEmpty(){
		assertEquals("{}", clazz.convertToJSON(new JSONConverter()));
	}
	
	@Test
	public void testConvertToJSONPopulated(){
		clazz.setItem("Derp", 5);
		
		assertEquals("{\"Derp\":5}", clazz.convertToJSON(new JSONConverter()));
	}
	
	@Test
	public void testGetJSONArrayItemNull()
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException{
		clazz.setItem("Table", null);
		
		JSONArray<TestClass> table = clazz.getJSONArrayItem("Table", TestClass.class);
		assertNull(table);
	}
	
	@Test
	public void testGetJSONArrayItem()
			throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException{
		JSONArray<MappedPojo> brokenTable = new JSONArrayList<>();
		TestClass testClass1 = new TestClass();
		testClass1.setItem("Derp", "nope");
		testClass1.setItem("Plop", 5);
		brokenTable.add(testClass1);
		JSONClass otherPojo = new AbstractJSONClass(){ };
		otherPojo.setItem("Derp", "yep");
		otherPojo.setItem("Plop", 42);
		brokenTable.add(otherPojo);
		clazz.setItem("Table", brokenTable);
		
		List<TestClass> table = clazz.getJSONArrayItem("Table", TestClass.class);
		assertEquals(2, table.size());
		assertEquals(testClass1, table.get(0));
		TestClass testClass2 = table.get(1);
		assertEquals("yep", testClass2.getItem("Derp"));
		assertEquals(42, testClass2.getItem("Plop"));
	}
	
	@Test
	public void testGetJSONArrayItemNoThrowPass(){
		JSONArray<MappedPojo> brokenTable = new JSONArrayList<>();
		TestClass testClass1 = new TestClass();
		testClass1.setItem("Derp", "nope");
		testClass1.setItem("Plop", 5);
		brokenTable.add(testClass1);
		JSONClass otherPojo = new AbstractJSONClass(){ };
		otherPojo.setItem("Derp", "yep");
		otherPojo.setItem("Plop", 42);
		brokenTable.add(otherPojo);
		clazz.setItem("Table", brokenTable);
		
		List<TestClass> table = clazz.getJSONArrayItemNoThrow("Table", TestClass.class);
		assertEquals(2, table.size());
		assertEquals(testClass1, table.get(0));
		TestClass testClass2 = table.get(1);
		assertEquals("yep", testClass2.getItem("Derp"));
		assertEquals(42, testClass2.getItem("Plop"));
	}
	
	@Test
	public void testGetJSONArrayItemNoThrow(){
		clazz.setItem("Test", 5);
		assertNull(clazz.getJSONArrayItemNoThrow("Test", TestClass.class));
	}
	
	@Test
	public void testGetListItemNoThrowLogger(){
		clazz.setItem("Test", 5);
		JUnitEasyLogger logger = new JUnitEasyLogger();
		assertNull(clazz.getJSONArrayItemNoThrow(logger, "Test", TestClass.class));
		List<JUnitEasyLogger.JUnitEasyLoggerEntry> entries = logger.getEntries();
		assertEquals(1, entries.size());
		JUnitEasyLogger.JUnitEasyLoggerEntry entry = entries.get(0);
		assertEquals(Level.SEVERE, entry.level());
		assertEquals("Failed to get JSON Array item: Test", entry.message());
		assertNotNull(entry.throwable());
	}
}
