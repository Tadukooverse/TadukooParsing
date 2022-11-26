package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LowercaseTransformationTest{
	private Transformation transformation;
	
	@BeforeEach
	public void setup(){
		transformation = new LowercaseTransformation();
	}
	
	@Test
	public void testType(){
		assertEquals(TransformationType.LOWERCASE, transformation.getType());
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
		assertEquals("yep it works", transformation.applyTransformation("YEP IT WORKS"));
	}
}
