package com.github.tadukoo.parsing.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaAnnotationTest{
	private JavaAnnotation annotation;
	
	@Test
	public void testBuilderName(){
		annotation = JavaAnnotation.builder().name("Test").build();
		assertEquals("Test", annotation.getName());
	}
	
	@Test
	public void testBuilderMissingName(){
		try{
			annotation = JavaAnnotation.builder().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify name!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		annotation = JavaAnnotation.builder().name("Test").build();
		assertEquals("@Test", annotation.toString());
	}
}
