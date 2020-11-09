package com.github.tadukoo.parsing.json;

import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class JSONArrayTest{
	private JSONArray array = new AbstractJSONArray(ListUtil.createList("Derp", 5, true, null)){ };
	
	@Test
	public void testGetItems(){
		List<Object> items = array.getItems();
		assertEquals(4, items.size());
		assertEquals("Derp", items.get(0));
		assertEquals(5, items.get(1));
		assertEquals(true, items.get(2));
		assertNull(items.get(3));
	}
	
	@Test
	public void testConvertToJSONPopulated(){
		assertEquals("[\"Derp\",5,true,null]", array.convertToJSON(new JSONConverter()));
	}
	
	@Test
	public void testConvertToJSONEmpty(){
		array = new AbstractJSONArray(new ArrayList<>()){ };
		assertEquals("[]", array.convertToJSON(new JSONConverter()));
	}
}
