package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UppercaseTransformationTest{
	private Transformation transformation;
	
	@BeforeEach
	public void setup(){
		transformation = new UppercaseTransformation();
	}
	
	@Test
	public void testType(){
		assertEquals(TransformationType.UPPERCASE, transformation.getType());
	}
	
	@Test
	public void testParameters(){
		assertEquals(new ArrayList<>(), transformation.getParameters());
	}
	
	@Test
	public void testSubTransformations(){
		assertEquals(new ArrayList<>(), transformation.getSubTransformations());
	}
	
	@Test
	public void testApplyTransformation(){
		assertEquals("YEP IT WORKS", transformation.applyTransformation("yep it works"));
	}
}
