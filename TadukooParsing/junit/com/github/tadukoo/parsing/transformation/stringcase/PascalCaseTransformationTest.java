package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PascalCaseTransformationTest{
	private Transformation transformation;
	
	@BeforeEach
	public void setup(){
		transformation = new PascalCaseTransformation();
	}
	
	@Test
	public void testType(){
		assertEquals(TransformationType.PASCAL_CASE, transformation.getType());
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
		assertEquals("YepItWorks", transformation.applyTransformation("yep it works"));
	}
}
