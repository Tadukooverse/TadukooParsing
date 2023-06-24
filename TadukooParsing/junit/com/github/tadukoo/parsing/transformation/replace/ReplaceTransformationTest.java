package com.github.tadukoo.parsing.transformation.replace;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;
import com.github.tadukoo.parsing.transformation.stringcase.LowercaseTransformation;
import com.github.tadukoo.parsing.transformation.stringcase.UppercaseTransformation;
import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class ReplaceTransformationTest{
	private ReplaceTransformation transformation;
	private final String replaceFrom = "test";
	private final String replaceTo = "derp";
	
	@BeforeEach
	public void setup(){
		transformation = ReplaceTransformation.builder()
				.replaceFrom(replaceFrom).replaceTo(replaceTo)
				.build();
	}
	
	@Test
	public void testGetType(){
		assertEquals(TransformationType.REPLACE, transformation.getType());
	}
	
	/*
	 * Builder Default Settings
	 */
	
	@Test
	public void testDefaultReplaceType(){
		assertEquals(ReplaceType.ALL.toString(), transformation.getParameters().get(0));
	}
	
	@Test
	public void testDefaultReplaceFromType(){
		assertEquals(ReplaceFromType.REGEX.toString(), transformation.getParameters().get(1));
	}
	
	@Test
	public void testDefaultReplaceToType(){
		assertEquals(ReplaceToType.EXACT_STRING.toString(), transformation.getParameters().get(2));
	}
	
	@Test
	public void testDefaultIgnoreCase(){
		assertFalse(Boolean.parseBoolean(transformation.getParameters().get(3)));
	}
	
	@Test
	public void testDefaultReplaceTo(){
		transformation = ReplaceTransformation.builder()
				.replaceToType(ReplaceToType.TRANSFORMATION)
				.replaceFrom(replaceFrom)
				.replaceToTransformation(new LowercaseTransformation())
				.build();
		assertNull(transformation.getParameters().get(5));
	}
	
	@Test
	public void testDefaultReplaceToTransformation(){
		assertNull(transformation.getSubTransformations().get(0));
	}
	
	/*
	 * Builder Set Settings
	 */
	
	@Test
	public void testSetReplaceType(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFrom(replaceFrom).replaceTo(replaceTo)
				.build();
		assertEquals(ReplaceType.FIRST.toString(), transformation.getParameters().get(0));
	}
	
	@Test
	public void testSetReplaceFromType(){
		transformation = ReplaceTransformation.builder()
				.replaceFromType(ReplaceFromType.EXACT_STRING)
				.replaceFrom(replaceFrom).replaceTo(replaceTo)
				.build();
		assertEquals(ReplaceFromType.EXACT_STRING.toString(), transformation.getParameters().get(1));
	}
	
	@Test
	public void testSetReplaceToType(){
		transformation = ReplaceTransformation.builder()
				.replaceToType(ReplaceToType.TRANSFORMATION)
				.replaceFrom(replaceFrom).replaceToTransformation(new LowercaseTransformation())
				.build();
		assertEquals(ReplaceToType.TRANSFORMATION.toString(), transformation.getParameters().get(2));
	}
	
	@Test
	public void testSetIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.ignoreCase(true)
				.replaceFrom(replaceFrom).replaceTo(replaceTo)
				.build();
		assertTrue(Boolean.parseBoolean(transformation.getParameters().get(3)));
	}
	
	@Test
	public void testIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.ignoreCase()
				.replaceFrom(replaceFrom).replaceTo(replaceTo)
				.build();
		assertTrue(Boolean.parseBoolean(transformation.getParameters().get(3)));
	}
	
	@Test
	public void testSetReplaceFrom(){
		assertEquals(replaceFrom, transformation.getParameters().get(4));
	}
	
	@Test
	public void testSetReplaceTo(){
		assertEquals(replaceTo, transformation.getParameters().get(5));
	}
	
	@Test
	public void testSetReplaceToTransformation(){
		Transformation subTransformation = new LowercaseTransformation();
		transformation = ReplaceTransformation.builder()
				.replaceToType(ReplaceToType.TRANSFORMATION)
				.replaceFrom(replaceFrom).replaceToTransformation(subTransformation)
				.build();
		assertEquals(subTransformation, transformation.getSubTransformations().get(0));
	}
	
	/*
	 * Builder Errors
	 */
	
	@Test
	public void testNullReplaceType(){
		try{
			transformation = ReplaceTransformation.builder()
					.replaceType(null)
					.replaceFrom(replaceFrom).replaceTo(replaceTo)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered building a ReplaceTransformation: \n" +
					"replaceType can't be null!", e.getMessage());
		}
	}
	
	@Test
	public void testNullReplaceFromType(){
		try{
			transformation = ReplaceTransformation.builder()
					.replaceFromType(null)
					.replaceFrom(replaceFrom).replaceTo(replaceTo)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered building a ReplaceTransformation: \n" +
					"replaceFromType can't be null!", e.getMessage());
		}
	}
	
	@Test
	public void testNullReplaceToType(){
		try{
			transformation = ReplaceTransformation.builder()
					.replaceToType(null)
					.replaceFrom(replaceFrom).replaceTo(replaceTo)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered building a ReplaceTransformation: \n" +
					"replaceToType can't be null!", e.getMessage());
		}
	}
	
	@Test
	public void testEmptyReplaceFrom(){
		try{
			transformation = ReplaceTransformation.builder()
					.replaceFrom(null).replaceTo(replaceTo)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered building a ReplaceTransformation: \n" +
					"replaceFrom can't be blank!", e.getMessage());
		}
	}
	
	@Test
	public void testNullReplaceToWhenExactString(){
		try{
			transformation = ReplaceTransformation.builder()
					.replaceToType(ReplaceToType.EXACT_STRING)
					.replaceFrom(replaceFrom).replaceTo(null)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered building a ReplaceTransformation: \n" +
					"replaceTo can't be null if using EXACT STRING as the replaceToType!", e.getMessage());
		}
	}
	
	@Test
	public void testNullReplaceToTransformationWhenTransformation(){
		try{
			transformation = ReplaceTransformation.builder()
					.replaceToType(ReplaceToType.TRANSFORMATION)
					.replaceFrom(replaceFrom)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered building a ReplaceTransformation: \n" +
					"replaceToTransformation can't be null if using TRANSFORMATION as the replaceToType!", e.getMessage());
		}
	}
	
	@Test
	public void testBothReplaceToTypesSet(){
		try{
			transformation = ReplaceTransformation.builder()
					.replaceFrom(replaceFrom).replaceTo(replaceTo)
					.replaceToTransformation(new LowercaseTransformation())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Errors encountered building a ReplaceTransformation: \n" +
					"replaceTo and replaceToTransformation can't both be set!", e.getMessage());
		}
	}
	
	@Test
	public void testMultipleErrors(){
		try{
			transformation = ReplaceTransformation.builder()
					.replaceType(null)
					.replaceFromType(null).replaceToType(null)
					.replaceFrom("").replaceTo(replaceTo)
					.replaceToTransformation(new LowercaseTransformation())
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Errors encountered building a ReplaceTransformation:\s
					replaceType can't be null!
					replaceFromType can't be null!
					replaceToType can't be null!
					replaceFrom can't be blank!
					replaceTo and replaceToTransformation can't both be set!""", e.getMessage());
		}
	}
	
	/*
	 * FromTransformation Method
	 */
	
	@Test
	public void testFromTransformation(){
		transformation = ReplaceTransformation.fromTransformation(new Transformation(){
			@Override
			public TransformationType getType(){
				return null;
			}
			
			@Override
			public List<String> getParameters(){
				return ListUtil.createList(ReplaceType.ALL.toString(),
						ReplaceFromType.EXACT_STRING.toString(), ReplaceToType.EXACT_STRING.toString(),
						"false", replaceFrom, replaceTo);
			}
			
			@Override
			public List<Transformation> getSubTransformations(){
				return ListUtil.createList();
			}
			
			@Override
			public String applyTransformation(String text){
				return null;
			}
		});
		assertEquals(ReplaceType.ALL, ReplaceType.fromString(transformation.getParameters().get(0)));
		assertEquals(ReplaceFromType.EXACT_STRING, ReplaceFromType.fromString(transformation.getParameters().get(1)));
		assertEquals(ReplaceToType.EXACT_STRING, ReplaceToType.fromString(transformation.getParameters().get(2)));
		assertFalse(Boolean.parseBoolean(transformation.getParameters().get(3)));
		assertEquals(replaceFrom, transformation.getParameters().get(4));
		assertEquals(replaceTo, transformation.getParameters().get(5));
		assertNull(transformation.getSubTransformations().get(0));
	}
	
	@Test
	public void testFromTransformationSubTransformation(){
		Transformation subTransformation = new LowercaseTransformation();
		transformation = ReplaceTransformation.fromTransformation(new Transformation(){
			@Override
			public TransformationType getType(){
				return null;
			}
			
			@Override
			public List<String> getParameters(){
				return ListUtil.createList(ReplaceType.ALL.toString(),
						ReplaceFromType.EXACT_STRING.toString(), ReplaceToType.TRANSFORMATION.toString(),
						"false", replaceFrom, null);
			}
			
			@Override
			public List<Transformation> getSubTransformations(){
				return ListUtil.createList(subTransformation);
			}
			
			@Override
			public String applyTransformation(String text){
				return null;
			}
		});
		assertEquals(ReplaceType.ALL, ReplaceType.fromString(transformation.getParameters().get(0)));
		assertEquals(ReplaceFromType.EXACT_STRING, ReplaceFromType.fromString(transformation.getParameters().get(1)));
		assertEquals(ReplaceToType.TRANSFORMATION, ReplaceToType.fromString(transformation.getParameters().get(2)));
		assertFalse(Boolean.parseBoolean(transformation.getParameters().get(3)));
		assertEquals(replaceFrom, transformation.getParameters().get(4));
		assertNull(transformation.getParameters().get(5));
		assertEquals(subTransformation, transformation.getSubTransformations().get(0));
	}
	
	/*
	 * Apply Transformation
	 */
	
	@Test
	public void testApplyTransformationExactStringToExactStringFirst(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.EXACT_STRING).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase(false)
				.replaceFrom("dum").replaceTo("yep")
				.build();
		assertEquals("Dum yep dum", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationExactStringToExactStringAll(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.ALL)
				.replaceFromType(ReplaceFromType.EXACT_STRING).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase(false)
				.replaceFrom("dum").replaceTo("yep")
				.build();
		assertEquals("Dum yep yep", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationExactStringToExactStringFirstIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.EXACT_STRING).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase()
				.replaceFrom("dum").replaceTo("yep")
				.build();
		assertEquals("yep dum dum", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationExactStringToExactStringAllIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.ALL)
				.replaceFromType(ReplaceFromType.EXACT_STRING).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase()
				.replaceFrom("dum").replaceTo("yep")
				.build();
		assertEquals("yep yep yep", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationExactStringToTransformationFirst(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.EXACT_STRING).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(false)
				.replaceFrom("dum")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("Dum DUM dum", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationExactStringToTransformationAll(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.ALL)
				.replaceFromType(ReplaceFromType.EXACT_STRING).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(false)
				.replaceFrom("dum")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("Dum DUM DUM", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationExactStringToTransformationFirstIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.EXACT_STRING).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(true)
				.replaceFrom("dum")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("DUM dum dum", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationExactStringToTransformationFirstIgnoreCaseNotFound(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.EXACT_STRING).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(true)
				.replaceFrom("yep")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("Dum dum dum", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationExactStringToTransformationAllIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.ALL)
				.replaceFromType(ReplaceFromType.EXACT_STRING).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(true)
				.replaceFrom("dum")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("DUM DUM DUM", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationRegexToExactStringFirst(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase(false)
				.replaceFrom("(dum|no)").replaceTo("yep")
				.build();
		assertEquals("Dum yep dum No no", transformation.applyTransformation("Dum dum dum No no"));
	}
	
	@Test
	public void testApplyTransformationRegexToExactStringFirstOtherOption(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase(false)
				.replaceFrom("(dum|no)").replaceTo("yep")
				.build();
		assertEquals("No yep Dum dum dum", transformation.applyTransformation("No no Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationRegexToExactStringAll(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.ALL)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase(false)
				.replaceFrom("(dum|no)").replaceTo("yep")
				.build();
		assertEquals("Dum yep yep No yep", transformation.applyTransformation("Dum dum dum No no"));
	}
	
	@Test
	public void testApplyTransformationRegexToExactStringFirstIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase()
				.replaceFrom("(dum|no)").replaceTo("yep")
				.build();
		assertEquals("yep dum dum No no", transformation.applyTransformation("Dum dum dum No no"));
	}
	
	@Test
	public void testApplyTransformationRegexToExactStringFirstIgnoreCaseOtherOption(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase()
				.replaceFrom("(dum|no)").replaceTo("yep")
				.build();
		assertEquals("yep no Dum dum dum", transformation.applyTransformation("No no Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationRegexToExactStringAllIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.ALL)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.EXACT_STRING)
				.ignoreCase()
				.replaceFrom("(dum|no)").replaceTo("yep")
				.build();
		assertEquals("yep yep yep yep yep", transformation.applyTransformation("Dum dum dum No no"));
	}
	
	@Test
	public void testApplyTransformationRegexToTransformationFirst(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(false)
				.replaceFrom("(dum|no)")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("Dum DUM dum No no", transformation.applyTransformation("Dum dum dum No no"));
	}
	
	@Test
	public void testApplyTransformationRegexToTransformationFirstOtherOption(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(false)
				.replaceFrom("(dum|no)")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("No NO Dum dum dum", transformation.applyTransformation("No no Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationRegexToTransformationAll(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.ALL)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(false)
				.replaceFrom("(dum|no)")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("Dum DUM DUM No NO yep", transformation.applyTransformation("Dum dum dum No no yep"));
	}
	
	@Test
	public void testApplyTransformationRegexToTransformationFirstIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(true)
				.replaceFrom("(dum|no)")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("DUM dum dum No no", transformation.applyTransformation("Dum dum dum No no"));
	}
	
	@Test
	public void testApplyTransformationRegexToTransformationFirstIgnoreCaseOtherOption(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(true)
				.replaceFrom("(dum|no)")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("NO no Dum dum dum", transformation.applyTransformation("No no Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationRegexToTransformationFirstIgnoreCaseNotFound(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.FIRST)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(true)
				.replaceFrom("(yep|no)")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("Dum dum dum", transformation.applyTransformation("Dum dum dum"));
	}
	
	@Test
	public void testApplyTransformationRegexToTransformationAllIgnoreCase(){
		transformation = ReplaceTransformation.builder()
				.replaceType(ReplaceType.ALL)
				.replaceFromType(ReplaceFromType.REGEX).replaceToType(ReplaceToType.TRANSFORMATION)
				.ignoreCase(true)
				.replaceFrom("(dum|no)")
				.replaceToTransformation(new UppercaseTransformation())
				.build();
		assertEquals("NO NO DUM DUM DUM yep", transformation.applyTransformation("No no Dum dum dum yep"));
	}
}
