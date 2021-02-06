package com.github.tadukoo.parsing.java;

import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class JavaClassTest{
	private JavaClass clazz = JavaClass.builder()
			.packageName("some.package").className("AClassName")
			.build();
	
	@Test
	public void testDefaultImports(){
		assertNotNull(clazz.getImports());
		assertTrue(clazz.getImports().isEmpty());
	}
	
	@Test
	public void testDefaultStaticImports(){
		assertNotNull(clazz.getStaticImports());
		assertTrue(clazz.getStaticImports().isEmpty());
	}
	
	@Test
	public void testDefaultAnnotations(){
		assertNotNull(clazz.getAnnotations());
		assertTrue(clazz.getAnnotations().isEmpty());
	}
	
	@Test
	public void testDefaultVisibility(){
		assertEquals(Visibility.PUBLIC, clazz.getVisibility());
	}
	
	@Test
	public void testDefaultSuperClassName(){
		assertNull(clazz.getSuperClassName());
	}
	
	@Test
	public void testDefaultFields(){
		assertNotNull(clazz.getFields());
		assertTrue(clazz.getFields().isEmpty());
	}
	
	@Test
	public void testSetPackageName(){
		assertEquals("some.package", clazz.getPackageName());
	}
	
	@Test
	public void testSetClassName(){
		assertEquals("AClassName", clazz.getClassName());
	}
	
	@Test
	public void testSetImports(){
		List<String> imports = ListUtil.createList("com.example.*", "com.github.tadukoo.*");
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.imports(imports)
				.build();
		assertEquals(imports, clazz.getImports());
	}
	
	@Test
	public void testSetSingleImport(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.singleImport("com.example.*")
				.build();
		List<String> imports = clazz.getImports();
		assertEquals(1, imports.size());
		assertEquals("com.example.*", imports.get(0));
	}
	
	@Test
	public void testSetStaticImports(){
		List<String> staticImports = ListUtil.createList("com.example.Test", "com.github.tadukoo.*");
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.staticImports(staticImports)
				.build();
		assertEquals(staticImports, clazz.getStaticImports());
	}
	
	@Test
	public void testSetSingleStaticImport(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.staticImport("com.github.tadukoo.*")
				.build();
		List<String> staticImports = clazz.getStaticImports();
		assertEquals(1, staticImports.size());
		assertEquals("com.github.tadukoo.*", staticImports.get(0));
	}
	
	@Test
	public void testSetAnnotations(){
		JavaAnnotation test = JavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = JavaAnnotation.builder().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.annotations(annotations)
				.build();
		assertEquals(annotations, clazz.getAnnotations());
	}
	
	@Test
	public void testSetSingleAnnotation(){
		JavaAnnotation test = JavaAnnotation.builder().name("Test").build();
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.annotation(test)
				.build();
		List<JavaAnnotation> annotations = clazz.getAnnotations();
		assertEquals(1, annotations.size());
		assertEquals(test, annotations.get(0));
	}
	
	@Test
	public void testSetVisibility(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.visibility(Visibility.PRIVATE)
				.build();
		assertEquals(Visibility.PRIVATE, clazz.getVisibility());
	}
	
	@Test
	public void testSetSuperClassName(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.superClassName("AnotherClassName")
				.build();
		assertEquals("AnotherClassName", clazz.getSuperClassName());
	}
	
	@Test
	public void testSetFields(){
		List<JavaField> fields = ListUtil.createList(JavaField.builder().type("int").name("test").build(),
				JavaField.builder().type("String").name("derp").build());
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.fields(fields)
				.build();
		assertEquals(fields, clazz.getFields());
	}
	
	@Test
	public void testSetField(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.field(JavaField.builder().type("int").name("test").build())
				.build();
		List<JavaField> fields = clazz.getFields();
		assertEquals(1, fields.size());
		JavaField field = fields.get(0);
		assertEquals(Visibility.PRIVATE, field.getVisibility());
		assertEquals("int", field.getType());
		assertEquals("test", field.getName());
	}
	
	@Test
	public void testSetMethods(){
		List<JavaMethod> methods = ListUtil.createList(JavaMethod.builder().returnType("int").build(),
				JavaMethod.builder().returnType("String").build());
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.methods(methods)
				.build();
		assertEquals(methods, clazz.getMethods());
	}
	
	@Test
	public void testSetMethod(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.method(JavaMethod.builder().returnType("int").name("someMethod").line("return 42;").build())
				.build();
		List<JavaMethod> methods = clazz.getMethods();
		assertEquals(1, methods.size());
		JavaMethod method = methods.get(0);
		assertEquals(Visibility.PUBLIC, method.getVisibility());
		assertEquals("int", method.getReturnType());
		assertEquals("someMethod", method.getName());
		assertTrue(method.getParameters().isEmpty());
		List<String> lines = method.getLines();
		assertEquals(1, lines.size());
		assertEquals("return 42;", lines.get(0));
	}
	
	@Test
	public void testNullPackageName(){
		try{
			clazz = JavaClass.builder()
					.className("AClassName")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify packageName!", e.getMessage());
		}
	}
	
	@Test
	public void testNullClassName(){
		try{
			clazz = JavaClass.builder()
					.packageName("some.package")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify className!", e.getMessage());
		}
	}
	
	@Test
	public void testNullPackageNameAndClassName(){
		try{
			clazz = JavaClass.builder()
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Must specify packageName!\nMust specify className!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		String javaString = """
				package some.package;
				
				public class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithSuperClassName(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName").superClassName("AnotherClassName")
				.build();
		String javaString = """
				package some.package;
				
				public class AClassName extends AnotherClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithAnnotations(){
		JavaAnnotation test = JavaAnnotation.builder().name("Test").build();
		JavaAnnotation derp = JavaAnnotation.builder().name("Derp").build();
		List<JavaAnnotation> annotations = ListUtil.createList(test, derp);
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.annotations(annotations)
				.build();
		String javaString = """
				package some.package;
				
				@Test
				@Derp
				public class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithImports(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.imports(ListUtil.createList("com.example.*", "com.github.tadukoo.*"))
				.build();
		String javaString = """
				package some.package;
				
				import com.example.*;
				import com.github.tadukoo.*;
				
				public class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithStaticImports(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.staticImports(ListUtil.createList("com.example.Test", "com.github.tadukoo.test.*"))
				.build();
		String javaString = """
				package some.package;
				
				import static com.example.Test;
				import static com.github.tadukoo.test.*;
				
				public class AClassName{
				\t
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithFields(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.field(JavaField.builder().type("int").name("test").build())
				.field(JavaField.builder().type("String").name("derp").build())
				.build();
		String javaString = """
				package some.package;
				
				public class AClassName{
				\t
					private int test;
					private String derp;
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithMethods(){
		clazz = JavaClass.builder()
				.packageName("some.package").className("AClassName")
				.method(JavaMethod.builder().returnType("AClassName").build())
				.method(JavaMethod.builder().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		String javaString = """
				package some.package;
				
				public class AClassName{
				\t
					public AClassName(){
					}
				\t
					public String getSomething(int test){
						return doSomething();
					}
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
	
	@Test
	public void testToStringWithEverything(){
		clazz = JavaClass.builder()
				.packageName("some.package")
				.imports(ListUtil.createList("com.example.*", "com.github.tadukoo.*"))
				.staticImports(ListUtil.createList("com.example.Test", "com.github.tadukoo.test.*"))
				.annotation(JavaAnnotation.builder().name("Test").build())
				.annotation(JavaAnnotation.builder().name("Derp").build())
				.className("AClassName").superClassName("AnotherClassName")
				.field(JavaField.builder().type("int").name("test").build())
				.field(JavaField.builder().type("String").name("derp").build())
				.method(JavaMethod.builder().returnType("AClassName").build())
				.method(JavaMethod.builder().returnType("String").name("getSomething")
						.parameter("int", "test").line("return doSomething();").build())
				.build();
		String javaString = """
				package some.package;
				
				import com.example.*;
				import com.github.tadukoo.*;
				
				import static com.example.Test;
				import static com.github.tadukoo.test.*;
				
				@Test
				@Derp
				public class AClassName extends AnotherClassName{
				\t
					private int test;
					private String derp;
				\t
					public AClassName(){
					}
				\t
					public String getSomething(int test){
						return doSomething();
					}
				}
				""";
		assertEquals(javaString, clazz.toString());
	}
}
