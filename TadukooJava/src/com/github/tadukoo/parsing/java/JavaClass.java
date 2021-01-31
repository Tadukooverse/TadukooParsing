package com.github.tadukoo.parsing.java;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Class is used to represent a class in Java.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.2
 */
public class JavaClass{
	
	/**
	 * Java Class Builder is used to create a {@link JavaClass}. It has the following parameters:
	 *
	 * <table>
	 *     <caption>Java Class Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>packageName</td>
	 *         <td>The name of the package the class is in</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>imports</td>
	 *         <td>The classes imported by the class</td>
	 *         <td>An empty list</td>
	 *     </tr>
	 *     <tr>
	 *         <td>annotations</td>
	 *         <td>The {@link JavaAnnotation annotations} on the class</td>
	 *         <td>An empty list</td>
	 *     </tr>
	 *     <tr>
	 *         <td>visibility</td>
	 *         <td>The {@link Visibility} of the class</td>
	 *         <td>{@link Visibility#PUBLIC}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>className</td>
	 *         <td>The name of the class</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>superClassName</td>
	 *         <td>The name of the class this one extends (may be null)</td>
	 *         <td>null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>fields</td>
	 *         <td>The {@link JavaField fields} on the class</td>
	 *         <td>An empty list</td>
	 *     </tr>
	 *     <tr>
	 *         <td>methods</td>
	 *         <td>The {@link JavaMethod methods} in the class</td>
	 *         <td>An empty list</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 * @since Alpha v.0.2
	 */
	public static class JavaClassBuilder{
		/** The name of the package the class is in */
		private String packageName = null;
		/** The classes imported by the class */
		private List<String> imports = new ArrayList<>();
		/** The {@link JavaAnnotation annotations} on the class */
		private List<JavaAnnotation> annotations = new ArrayList<>();
		/** The {@link Visibility} of the class */
		private Visibility visibility = Visibility.PUBLIC;
		/** The name of the class */
		private String className = null;
		/** The name of the class this one extends (may be null) */
		private String superClassName = null;
		/** The {@link JavaField fields} on the class */
		private List<JavaField> fields = new ArrayList<>();
		/** The {@link JavaMethod methods} in the class */
		private List<JavaMethod> methods = new ArrayList<>();
		
		// Can't create outside of JavaClass
		private JavaClassBuilder(){ }
		
		/**
		 * @param packageName The name of the package the class is in
		 * @return this, to continue building
		 */
		public JavaClassBuilder packageName(String packageName){
			this.packageName = packageName;
			return this;
		}
		
		/**
		 * @param imports The classes imported by the class
		 * @return this, to continue building
		 */
		public JavaClassBuilder imports(List<String> imports){
			this.imports = imports;
			return this;
		}
		
		/**
		 * @param singleImport A single class imported by the class, to be added to the list
		 * @return this, to continue building
		 */
		public JavaClassBuilder singleImport(String singleImport){
			imports.add(singleImport);
			return this;
		}
		
		/**
		 * @param annotations The {@link JavaAnnotation annotations} on the class
		 * @return this, to continue building
		 */
		public JavaClassBuilder annotations(List<JavaAnnotation> annotations){
			this.annotations = annotations;
			return this;
		}
		
		/**
		 * @param annotation A single {@link JavaAnnotation annotation} on the class
		 * @return this, to continue building
		 */
		public JavaClassBuilder annotation(JavaAnnotation annotation){
			annotations.add(annotation);
			return this;
		}
		
		/**
		 * @param visibility The {@link Visibility} of the class
		 * @return this, to continue building
		 */
		public JavaClassBuilder visibility(Visibility visibility){
			this.visibility = visibility;
			return this;
		}
		
		/**
		 * @param className The name of the class
		 * @return this, to continue building
		 */
		public JavaClassBuilder className(String className){
			this.className = className;
			return this;
		}
		
		/**
		 * @param superClassName The name of the class this one extends (may be null)
		 * @return this, to continue building
		 */
		public JavaClassBuilder superClassName(String superClassName){
			this.superClassName = superClassName;
			return this;
		}
		
		/**
		 * @param fields The {@link JavaField fields} on the class
		 * @return this, to continue building
		 */
		public JavaClassBuilder fields(List<JavaField> fields){
			this.fields = fields;
			return this;
		}
		
		/**
		 * @param field A {@link JavaField field} on the class, to be added to the list
		 * @return this, to continue building
		 */
		public JavaClassBuilder field(JavaField field){
			fields.add(field);
			return this;
		}
		
		/**
		 * @param methods The {@link JavaMethod methods} in the class
		 * @return this, to continue building
		 */
		public JavaClassBuilder methods(List<JavaMethod> methods){
			this.methods = methods;
			return this;
		}
		
		/**
		 * @param method A {@link JavaMethod method} in the class, to be added to the list
		 * @return this, to continue building
		 */
		public JavaClassBuilder method(JavaMethod method){
			methods.add(method);
			return this;
		}
		
		/**
		 * Checks for any errors in the current parameters
		 *
		 * @throws IllegalArgumentException if anything is wrong
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			if(StringUtil.isBlank(packageName)){
				errors.add("Must specify packageName!");
			}
			
			if(StringUtil.isBlank(className)){
				errors.add("Must specify className!");
			}
			
			if(!errors.isEmpty()){
				throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Checks for any errors in the current parameters, then builds a new {@link JavaClass}
		 *
		 * @return A newly built {@link JavaClass}
		 * @throws IllegalArgumentException if anything is wrong with the current parameters
		 */
		public JavaClass build(){
			// Run the error check
			checkForErrors();
			
			// Actually build the Java Class
			return new JavaClass(packageName, imports, annotations,
					visibility, className, superClassName, fields, methods);
		}
	}
	
	/** The name of the package the class is in */
	private final String packageName;
	/** The classes imported by the class */
	private final List<String> imports;
	/** The {@link JavaAnnotation annotations} on the class */
	private final List<JavaAnnotation> annotations;
	/** The {@link Visibility} of the class */
	private final Visibility visibility;
	/** The name of the class */
	private final String className;
	/** The name of the class this one extends (may be null) */
	private final String superClassName;
	/** The {@link JavaField fields} on the class */
	private final List<JavaField> fields;
	/** The {@link JavaMethod methods} in the class */
	private final List<JavaMethod> methods;
	
	/**
	 * Constructs a new Java Class with the given parameters
	 *
	 * @param packageName The name of the package the class is in
	 * @param imports The classes imported by the class
	 * @param annotations The {@link JavaAnnotation annotations} on the class
	 * @param visibility The {@link Visibility} of the class
	 * @param className The name of the class
	 * @param superClassName The name of the class this one extends (may be null)
	 * @param fields The {@link JavaField fields} on the class
	 * @param methods The {@link JavaMethod methods} in the class
	 */
	private JavaClass(String packageName, List<String> imports, List<JavaAnnotation> annotations,
	                  Visibility visibility, String className, String superClassName,
	                  List<JavaField> fields, List<JavaMethod> methods){
		this.packageName = packageName;
		this.imports = imports;
		this.annotations = annotations;
		this.visibility = visibility;
		this.className = className;
		this.superClassName = superClassName;
		this.fields = fields;
		this.methods = methods;
	}
	
	/**
	 * @return A new {@link JavaClassBuilder} to use to build a {@link JavaClass}
	 */
	public static JavaClassBuilder builder(){
		return new JavaClassBuilder();
	}
	
	/**
	 * @return The name of the package the class is in
	 */
	public String getPackageName(){
		return packageName;
	}
	
	/**
	 * @return The classes imported by the class
	 */
	public List<String> getImports(){
		return imports;
	}
	
	/**
	 * @return The {@link JavaAnnotation annotations} on the class
	 */
	public List<JavaAnnotation> getAnnotations(){
		return annotations;
	}
	
	/**
	 * @return The {@link Visibility} of the class
	 */
	public Visibility getVisibility(){
		return visibility;
	}
	
	/**
	 * @return The name of the class
	 */
	public String getClassName(){
		return className;
	}
	
	/**
	 * @return The name of the class this one extends (may be null)
	 */
	public String getSuperClassName(){
		return superClassName;
	}
	
	/**
	 * @return The {@link JavaField fields} on the class
	 */
	public List<JavaField> getFields(){
		return fields;
	}
	
	/**
	 * @return The {@link JavaMethod methods} in the class
	 */
	public List<JavaMethod> getMethods(){
		return methods;
	}
	
	/**
	 * @return The actual Java code this {@link JavaClass} represents
	 */
	@Override
	public String toString(){
		// Create a list of the lines of the class
		List<String> content = new ArrayList<>();
		
		// Package Declaration
		content.add("package " + packageName + ";");
		
		// Import Statements
		if(ListUtil.isNotBlank(imports)){
			// Newline between package declaration + imports
			content.add("");
			for(String singleImport: imports){
				content.add("import " + singleImport + ";");
			}
		}
		
		// Newline between package declaration/imports + annotations/class declaration
		content.add("");
		
		// Annotations
		if(ListUtil.isNotBlank(annotations)){
			for(JavaAnnotation annotation: annotations){
				content.add(annotation.toString());
			}
		}
		
		// Class Declaration
		content.add(visibility.getText() + " class " + className +
				(StringUtil.isNotBlank(superClassName)?" extends " + superClassName:"") + "{");
		
		// Newline at start of class
		content.add("\t");
		
		// Fields on the class
		if(ListUtil.isNotBlank(fields)){
			for(JavaField field: fields){
				content.add("\t" + field.toString() + ";");
			}
		}
		
		// Methods in the class
		if(ListUtil.isNotBlank(methods)){
			// Newline to separate fields from methods
			if(ListUtil.isNotBlank(fields)){
				content.add("\t");
			}
			for(JavaMethod method: methods){
				// Split the method into its lines so we can add it to our lines
				List<String> lines = StringUtil.parseListFromStringWithSeparator(
						method.toString(), "\n", false);
				for(String line: lines){
					content.add("\t" + line);
				}
				content.add("\t");
			}
			// Remove extra newline at the end
			content.remove(content.size()-1);
		}
		
		// Closing brace at end of class and empty newline at end of file
		content.add("}");
		content.add("");
		
		// Build the full string
		return StringUtil.buildStringWithNewLines(content);
	}
}
