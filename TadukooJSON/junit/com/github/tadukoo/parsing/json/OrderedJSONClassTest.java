package com.github.tadukoo.parsing.json;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.map.MapUtil;
import com.github.tadukoo.util.pojo.AbstractMappedPojo;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class OrderedJSONClassTest{
	private OrderedJSONClass clazz = new AbstractOrderedJSONClass(){
		@Override
		public List<String> getKeyOrder(){
			return ListUtil.createList("Derp", "Test");
		}
	};
	
	@Test
	public void testConstructor(){
		assertTrue(clazz.getMap().isEmpty());
	}
	
	@Test
	public void testPojoConstructor(){
		clazz = new AbstractOrderedJSONClass(new AbstractMappedPojo(){
			@Override
			public Map<String, Object> getMap(){
				return MapUtil.createMap(Pair.of("Derp", 5), Pair.of("Test", true));
			}
		}){
			@Override
			public List<String> getKeyOrder(){
				return ListUtil.createList("Derp", "Test");
			}
		};
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
	public void testGetKeyOrder(){
		List<String> keyOrder = clazz.getKeyOrder();
		assertEquals(2, keyOrder.size());
		assertEquals("Derp", keyOrder.get(0));
		assertEquals("Test", keyOrder.get(1));
	}
	
	@Test
	public void testConvertToJSONEmpty(){
		assertEquals("{\"Derp\":null,\"Test\":null}", clazz.convertToJSON(new JSONConverter()));
	}
	
	@Test
	public void testConvertToJSONPopulated(){
		clazz.setItem("Derp", 5);
		
		assertEquals("{\"Derp\":5,\"Test\":null}", clazz.convertToJSON(new JSONConverter()));
	}
	
	@Test
	public void testConvertToJSONPopulatedKeyOrder(){
		clazz.setItem("Derp", 5);
		clazz.setItem("Test", "Yes");
		
		assertEquals("{\"Derp\":5,\"Test\":\"Yes\"}", clazz.convertToJSON(new JSONConverter()));
	}
}
