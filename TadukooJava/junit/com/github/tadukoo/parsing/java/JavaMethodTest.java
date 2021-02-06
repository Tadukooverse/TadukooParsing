package com.github.tadukoo.parsing.java;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaMethodTest{
	private JavaMethod method = JavaMethod.builder().returnType("int").build();
	
	@Test
	public void testDefaultAnnotations(){
		assertTrue(method.getAnnotations().isEmpty());
	}
	
	@Test
	public void testDefaultVisibility(){
		assertEquals(Visibility.PUBLIC, method.getVisibility());
	}
	
	@Test
	public void testDefaultName(){
		assertNull(method.getName());
	}
	
	@Test
	public void testDefaultParameters(){
		assertTrue(method.getParameters().isEmpty());
	}
	
	@Test
	public void testDefaultLines(){
		assertTrue(method.getLines().isEmpty());
	}
	
	@Test
	public void testSetAnnotations(){
		JavaAnnotation test = JavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = JavaAnnotation.builder().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		method = JavaMethod.builder().annotations(annotations).returnType("String").build();
		assertEquals(annotations, method.getAnnotations());
	}
	
	@Test
	public void testSetAnnotation(){
		JavaAnnotation test = JavaAnnotation.builder().name("Test").build();
		method = JavaMethod.builder().annotation(test).returnType("String").build();
		List<JavaAnnotation> annotations = method.getAnnotations();
		assertEquals(1, annotations.size());
		assertEquals(test, annotations.get(0));
	}
	
	@Test
	public void testSetVisibility(){
		method = JavaMethod.builder().visibility(Visibility.PRIVATE).returnType("String").build();
		assertEquals(Visibility.PRIVATE, method.getVisibility());
	}
	
	@Test
	public void testSetReturnType(){
		assertEquals("int", method.getReturnType());
	}
	
	@Test
	public void testSetName(){
		method = JavaMethod.builder().name("someName").returnType("int").build();
		assertEquals("someName", method.getName());
	}
	
	@Test
	public void testSetParameters(){
		List<Pair<String, String>> parameters = ListUtil.createList(Pair.of("int", "someInt"),
				Pair.of("String", "someText"));
		method = JavaMethod.builder().returnType("int").parameters(parameters).build();
		assertEquals(parameters, method.getParameters());
	}
	
	@Test
	public void testSetParameterPair(){
		method = JavaMethod.builder().returnType("int").parameter(Pair.of("String", "someText")).build();
		List<Pair<String, String>> parameters = method.getParameters();
		assertEquals(1, parameters.size());
		Pair<String, String> parameter = parameters.get(0);
		assertEquals("String", parameter.getLeft());
		assertEquals("someText", parameter.getRight());
	}
	
	@Test
	public void testSetParameter(){
		method = JavaMethod.builder().returnType("int").parameter("String", "someText").build();
		List<Pair<String, String>> parameters = method.getParameters();
		assertEquals(1, parameters.size());
		Pair<String, String> parameter = parameters.get(0);
		assertEquals("String", parameter.getLeft());
		assertEquals("someText", parameter.getRight());
	}
	
	@Test
	public void testSetLines(){
		List<String> lines = ListUtil.createList("doSomething();", "return 42;");
		method = JavaMethod.builder().returnType("int").lines(lines).build();
		assertEquals(lines, method.getLines());
	}
	
	@Test
	public void testSetLine(){
		method = JavaMethod.builder().returnType("int").line("return 42;").build();
		List<String> lines = method.getLines();
		assertEquals(1, lines.size());
		assertEquals("return 42;", lines.get(0));
	}
	
	@Test
	public void testNullReturnType(){
		try{
			method = JavaMethod.builder().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify returnType!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		String javaString = """
				public int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithSingleAnnotation(){
		JavaAnnotation test = JavaAnnotation.builder().name("Test").build();
		method = JavaMethod.builder().returnType("int").annotation(test).build();
		String javaString = """
				@Test
				public int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithAnnotations(){
		JavaAnnotation test = JavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = JavaAnnotation.builder().name("Derp").build();
		method = JavaMethod.builder().returnType("int").annotation(test).annotation(derp).build();
		String javaString = """
				@Test
				@Derp
				public int(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithName(){
		method = JavaMethod.builder().returnType("int").name("someMethod").build();
		String javaString = """
				public int someMethod(){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithSingleParameter(){
		method = JavaMethod.builder().returnType("int").parameter("String", "text").build();
		String javaString = """
				public int(String text){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithParameters(){
		method = JavaMethod.builder().returnType("int").parameter("String", "text")
				.parameter("int", "something").build();
		String javaString = """
				public int(String text, int something){
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithLines(){
		method = JavaMethod.builder().returnType("int").line("doSomething();").line("return 42;").build();
		String javaString = """
				public int(){
					doSomething();
					return 42;
				}""";
		assertEquals(javaString, method.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		JavaAnnotation test = JavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = JavaAnnotation.builder().name("Derp").build();
		method = JavaMethod.builder().returnType("int")
				.annotation(test).annotation(derp).name("someMethod")
				.parameter("String", "text").parameter("int", "something")
				.line("doSomething();").line("return 42;").build();
		String javaString = """
				@Test
				@Derp
				public int someMethod(String text, int something){
					doSomething();
					return 42;
				}""";
		assertEquals(javaString, method.toString());
	}
}
