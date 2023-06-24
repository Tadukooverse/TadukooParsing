package com.github.tadukoo.parsing.transformation.replace;

import org.junit.jupiter.api.Test;

import static com.github.tadukoo.parsing.transformation.replace.ReplaceFromType.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ReplaceFromTypeTest{
	
	@Test
	public void testFromStringGarbageString(){
		assertNull(ReplaceFromType.fromString("garbage_string"));
	}
	
	@Test
	public void testREGEXToString(){
		assertEquals("REGEX", REGEX.toString());
	}
	
	@Test
	public void testREGEXFromString(){
		assertEquals(REGEX, ReplaceFromType.fromString("REGEX"));
	}
	
	@Test
	public void testEXACT_STRINGToString(){
		assertEquals("EXACT STRING", EXACT_STRING.toString());
	}
	
	@Test
	public void testEXACT_STRINGFromString(){
		assertEquals(EXACT_STRING, ReplaceFromType.fromString("EXACT STRING"));
	}
}
