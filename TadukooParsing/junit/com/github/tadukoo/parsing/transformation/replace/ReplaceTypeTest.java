package com.github.tadukoo.parsing.transformation.replace;

import org.junit.jupiter.api.Test;

import static com.github.tadukoo.parsing.transformation.replace.ReplaceType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReplaceTypeTest{
	
	@Test
	public void testFromStringGarbageString(){
		assertNull(ReplaceType.fromString("garbage_string"));
	}
	
	@Test
	public void testALLToString(){
		assertEquals("ALL", ALL.toString());
	}
	
	@Test
	public void testALLFromString(){
		assertEquals(ALL, ReplaceType.fromString("ALL"));
	}
	
	@Test
	public void testFIRSTToString(){
		assertEquals("FIRST", FIRST.toString());
	}
	
	@Test
	public void testFIRSTFromString(){
		assertEquals(FIRST, ReplaceType.fromString("FIRST"));
	}
}
