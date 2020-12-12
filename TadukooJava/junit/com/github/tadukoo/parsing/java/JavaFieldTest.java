package com.github.tadukoo.parsing.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaFieldTest{
	private JavaField field = JavaField.builder()
			.type("int").name("test")
			.build();
	
	@Test
	public void testDefaultVisibility(){
		assertEquals(Visibility.PRIVATE, field.getVisibility());
	}
	
	@Test
	public void testSetType(){
		assertEquals("int", field.getType());
	}
	
	@Test
	public void testSetName(){
		assertEquals("test", field.getName());
	}
	
	@Test
	public void testSetVisibility(){
		field = JavaField.builder()
				.visibility(Visibility.PUBLIC).type("int").name("test")
				.build();
		
		assertEquals(Visibility.PUBLIC, field.getVisibility());
	}
	
	@Test
	public void testNullType(){
		try{
			field = JavaField.builder()
					.name("test")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify type!", e.getMessage());
		}
	}
	
	@Test
	public void testNullName(){
		try{
			field = JavaField.builder()
					.type("int")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify name!", e.getMessage());
		}
	}
	
	@Test
	public void testNullTypeAndName(){
		try{
			field = JavaField.builder()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify type!\nMust specify name!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("private int test", field.toString());
	}
}
