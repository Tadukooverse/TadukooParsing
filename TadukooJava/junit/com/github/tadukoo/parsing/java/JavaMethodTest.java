package com.github.tadukoo.parsing.java;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class JavaMethodTest{
	private JavaMethod method = JavaMethod.builder().returnType("int").build();
	
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
		method = JavaMethod.builder().returnType("int").name("someMethod")
				.parameter("String", "text").parameter("int", "something")
				.line("doSomething();").line("return 42;").build();
		String javaString = """
				public int someMethod(String text, int something){
					doSomething();
					return 42;
				}""";
		assertEquals(javaString, method.toString());
	}
}
