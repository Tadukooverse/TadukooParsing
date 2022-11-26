package com.github.tadukoo.parsing.transformation.stringcase;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnakeCaseTransformationTest{
	private Transformation transformation;
	
	@BeforeEach
	public void setup(){
		transformation = new SnakeCaseTransformation();
	}
	
	@Test
	public void testType(){
		assertEquals(TransformationType.SNAKE_CASE, transformation.getType());
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
		assertEquals("yep_it_works", transformation.applyTransformation("Yep It Works"));
	}
}
