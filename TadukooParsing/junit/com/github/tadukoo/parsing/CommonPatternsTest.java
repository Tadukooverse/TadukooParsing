package com.github.tadukoo.parsing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommonPatternsTest{
	
	@Test
	public void testNullFormatPattern(){
		assertEquals("(null)", CommonPatterns.nullFormat.pattern());
	}
	
	@Test
	public void testNullFormat(){
		assertTrue(CommonPatterns.nullFormat.matcher("null").matches());
	}
	
	@Test
	public void testNullFormatFailure(){
		assertFalse(CommonPatterns.nullFormat.matcher("something_else").matches());
	}
	
	@Test
	public void testBooleanFormatPattern(){
		assertEquals("(true|false)", CommonPatterns.booleanFormat.pattern());
	}
	
	@Test
	public void testBooleanFormatTrue(){
		assertTrue(CommonPatterns.booleanFormat.matcher("true").matches());
	}
	
	@Test
	public void testBooleanFormatFalse(){
		assertTrue(CommonPatterns.booleanFormat.matcher("false").matches());
	}
	
	@Test
	public void testBooleanFormatFailure(){
		assertFalse(CommonPatterns.booleanFormat.matcher("some_string").matches());
	}
	
	@Test
	public void testNullableBooleanFormatPattern(){
		assertEquals("(true|false|null)", CommonPatterns.nullableBooleanFormat.pattern());
	}
	
	@Test
	public void testNullableBooleanFormatTrue(){
		assertTrue(CommonPatterns.nullableBooleanFormat.matcher("true").matches());
	}
	
	@Test
	public void testNullableBooleanFormatFalse(){
		assertTrue(CommonPatterns.nullableBooleanFormat.matcher("false").matches());
	}
	
	@Test
	public void testNullableBooleanFormatNull(){
		assertTrue(CommonPatterns.nullableBooleanFormat.matcher("null").matches());
	}
	
	@Test
	public void testNullableBooleanFormatFailure(){
		assertFalse(CommonPatterns.nullableBooleanFormat.matcher("some_string").matches());
	}
	
	@Test
	public void testNumberFormatPattern(){
		assertEquals("((-)?(\\d)*(\\.\\d*)?(E([+\\-])\\d*)?)", CommonPatterns.numberFormat.pattern());
	}
	
	@Test
	public void testNumberFormatInteger(){
		assertTrue(CommonPatterns.numberFormat.matcher("42").matches());
	}
	
	@Test
	public void testNumberFormatDouble(){
		assertTrue(CommonPatterns.numberFormat.matcher("27.39").matches());
	}
	
	@Test
	public void testNumberFormatScientific(){
		assertTrue(CommonPatterns.numberFormat.matcher("38.29E+39").matches());
	}
	
	@Test
	public void testNumberFormatNegative(){
		assertTrue(CommonPatterns.numberFormat.matcher("-29.38E-10").matches());
	}
	
	@Test
	public void testNumberFormatFailure(){
		assertFalse(CommonPatterns.numberFormat.matcher("some_string").matches());
	}
	
	@Test
	public void testQuotedStringFormatPattern(){
		assertEquals("\"((?:\\\\\"|[^\"])*)\"", CommonPatterns.quotedStringFormat.pattern());
	}
	
	@Test
	public void testQuotedStringFormat(){
		assertTrue(CommonPatterns.quotedStringFormat.matcher("\"quoted_string\"").matches());
	}
	
	@Test
	public void testQuotedStringFormatUnquotedString(){
		assertFalse(CommonPatterns.quotedStringFormat.matcher("some_unquoted_string").matches());
	}
}
