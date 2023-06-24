package com.github.tadukoo.parsing.transformation.replace;

import org.junit.jupiter.api.Test;

import static com.github.tadukoo.parsing.transformation.replace.ReplaceToType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReplaceToTypeTest{
	
	@Test
	public void testFromStringGarbageString(){
		assertNull(ReplaceToType.fromString("garbage_string"));
	}
	
	@Test
	public void testEXACT_STRINGToString(){
		assertEquals("EXACT STRING", EXACT_STRING.toString());
	}
	
	@Test
	public void testEXACT_STRINGFromString(){
		assertEquals(EXACT_STRING, ReplaceToType.fromString("EXACT STRING"));
	}
	
	@Test
	public void testTRANSFORMATIONToString(){
		assertEquals("TRANSFORMATION", TRANSFORMATION.toString());
	}
	
	@Test
	public void testTRANSFORMATIONFromString(){
		assertEquals(TRANSFORMATION, ReplaceToType.fromString("TRANSFORMATION"));
	}
}
