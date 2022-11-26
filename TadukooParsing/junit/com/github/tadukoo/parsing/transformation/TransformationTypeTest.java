package com.github.tadukoo.parsing.transformation;

import org.junit.jupiter.api.Test;

import static com.github.tadukoo.parsing.transformation.TransformationType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TransformationTypeTest{
	
	@Test
	public void testFromStringGarbageString(){
		assertNull(TransformationType.fromString("garbage_string"));
	}
	
	@Test
	public void testLOWERCASEToString(){
		assertEquals("lowercase", LOWERCASE.toString());
	}
	
	@Test
	public void testLOWERCASEFromString(){
		assertEquals(LOWERCASE, TransformationType.fromString("lowercase"));
	}
	
	@Test
	public void testUPPERCASEToString(){
		assertEquals("UPPERCASE", UPPERCASE.toString());
	}
	
	@Test
	public void testUPPERCASEFromString(){
		assertEquals(UPPERCASE, TransformationType.fromString("UPPERCASE"));
	}
	
	@Test
	public void testSNAKE_CASEToString(){
		assertEquals("snake_case", SNAKE_CASE.toString());
	}
	
	@Test
	public void testSNAKE_CASEFromString(){
		assertEquals(SNAKE_CASE, TransformationType.fromString("snake_case"));
	}
	
	@Test
	public void testCAMEL_CASEToString(){
		assertEquals("camelCase", CAMEL_CASE.toString());
	}
	
	@Test
	public void testCAMEL_CASEFromString(){
		assertEquals(CAMEL_CASE, TransformationType.fromString("camelCase"));
	}
	
	@Test
	public void testPASCAL_CASEToString(){
		assertEquals("PascalCase", PASCAL_CASE.toString());
	}
	
	@Test
	public void testPASCAL_CASEFromString(){
		assertEquals(PASCAL_CASE, TransformationType.fromString("PascalCase"));
	}
	
	@Test
	public void testREPLACEToString(){
		assertEquals("replace", REPLACE.toString());
	}
	
	@Test
	public void testREPLACEFromString(){
		assertEquals(REPLACE, TransformationType.fromString("replace"));
	}
}
