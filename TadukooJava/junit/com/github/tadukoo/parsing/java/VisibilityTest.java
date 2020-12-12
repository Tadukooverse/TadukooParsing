package com.github.tadukoo.parsing.java;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class VisibilityTest{
	
	@Test
	public void testPublicText(){
		assertEquals("public", Visibility.PUBLIC.getText());
	}
	
	@Test
	public void testProtectedText(){
		assertEquals("protected", Visibility.PROTECTED.getText());
	}
	
	@Test
	public void testPrivateText(){
		assertEquals("private", Visibility.PRIVATE.getText());
	}
	
	@Test
	public void testNoneText(){
		assertEquals("", Visibility.NONE.getText());
	}
	
	@Test
	public void testFromTextPublic(){
		assertEquals(Visibility.PUBLIC, Visibility.fromText("pUBlIc"));
	}
	
	@Test
	public void testFromTextProtected(){
		assertEquals(Visibility.PROTECTED, Visibility.fromText("PROtecTeD"));
	}
	
	@Test
	public void testFromTextPrivate(){
		assertEquals(Visibility.PRIVATE, Visibility.fromText("PRiVAtE"));
	}
	
	@Test
	public void testFromTextNone(){
		assertEquals(Visibility.NONE, Visibility.fromText(""));
	}
	
	@Test
	public void testFromTextGarbage(){
		assertNull(Visibility.fromText("some_garbage_Stuff"));
	}
}
