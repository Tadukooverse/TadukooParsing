package com.github.tadukoo.parsing.java;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Method represents a method in a Java class or interface, etc.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.2
 */
public class JavaMethod{
	
	/**
	 * Java Method Builder is used to build a new {@link JavaMethod}. It contains the following parameters:
	 *
	 * <table>
	 *     <caption>Java Method Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>annotations</td>
	 *         <td>The {@link JavaAnnotation annotations} on the method</td>
	 *         <td>An empty list</td>
	 *     </tr>
	 *     <tr>
	 *         <td>visibility</td>
	 *         <td>The {@link Visibility} of the method</td>
	 *         <td>{@link Visibility#PUBLIC}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>returnType</td>
	 *         <td>The return type of the method</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>name</td>
	 *         <td>The name of the method</td>
	 *         <td>null (used for constructors)</td>
	 *     </tr>
	 *     <tr>
	 *         <td>parameters</td>
	 *         <td>The parameters used in the method - pairs of type, then name</td>
	 *         <td>An empty list</td>
	 *     </tr>
	 *     <tr>
	 *         <td>lines</td>
	 *         <td>The actual lines of code in the method</td>
	 *         <td>An empty list</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 * @since Alpha v.0.2
	 */
	public static class JavaMethodBuilder{
		/** The {@link JavaAnnotation annotations} on the method */
		private List<JavaAnnotation> annotations = new ArrayList<>();
		/** The {@link Visibility} of the method */
		private Visibility visibility = Visibility.PUBLIC;
		/** The return type of the method */
		private String returnType = null;
		/** The name of the method */
		private String name = null;
		/** The parameters used in the method - pairs of type, then name */
		private List<Pair<String, String>> parameters = new ArrayList<>();
		/** The actual lines of code in the method */
		private List<String> lines = new ArrayList<>();
		
		// Can't create outside of JavaMethod
		private JavaMethodBuilder(){ }
		
		/**
		 * @param annotations The {@link JavaAnnotation annotations} on the method
		 * @return this, to continue building
		 */
		public JavaMethodBuilder annotations(List<JavaAnnotation> annotations){
			this.annotations = annotations;
			return this;
		}
		
		/**
		 * @param annotation A single {@link JavaAnnotation annotation} on the method
		 * @return this, to continue building
		 */
		public JavaMethodBuilder annotation(JavaAnnotation annotation){
			annotations.add(annotation);
			return this;
		}
		
		/**
		 * @param visibility The {@link Visibility} of the method
		 * @return this, to continue building
		 */
		public JavaMethodBuilder visibility(Visibility visibility){
			this.visibility = visibility;
			return this;
		}
		
		/**
		 * @param returnType The return type of the method
		 * @return this, to continue building
		 */
		public JavaMethodBuilder returnType(String returnType){
			this.returnType = returnType;
			return this;
		}
		
		/**
		 * @param name The name of the method
		 * @return this, to continue building
		 */
		public JavaMethodBuilder name(String name){
			this.name = name;
			return this;
		}
		
		/**
		 * @param parameters The parameters used in the method - pairs of type, then name
		 * @return this, to continue building
		 */
		public JavaMethodBuilder parameters(List<Pair<String, String>> parameters){
			this.parameters = parameters;
			return this;
		}
		
		/**
		 * @param parameter A single parameter, with type first, then name - to add to the list
		 * @return this, to continue building
		 */
		public JavaMethodBuilder parameter(Pair<String, String> parameter){
			parameters.add(parameter);
			return this;
		}
		
		/**
		 * @param type The type of the parameter to be added
		 * @param name The name of the parameter to be added
		 * @return this, to continue building
		 */
		public JavaMethodBuilder parameter(String type, String name){
			parameters.add(Pair.of(type, name));
			return this;
		}
		
		/**
		 * @param lines The actual lines of code in the method
		 * @return this, to continue building
		 */
		public JavaMethodBuilder lines(List<String> lines){
			this.lines = lines;
			return this;
		}
		
		/**
		 * @param line A single line of code in the method, to add to the list
		 * @return this, to continue building
		 */
		public JavaMethodBuilder line(String line){
			lines.add(line);
			return this;
		}
		
		/**
		 * Checks for any errors in the current parameters
		 *
		 * @throws IllegalArgumentException if anything is wrong
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			if(StringUtil.isBlank(returnType)){
				errors.add("Must specify returnType!");
			}
			
			if(!errors.isEmpty()){
				throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Checks for any errors in the current parameters, then builds a new {@link JavaMethod}
		 *
		 * @return A newly built {@link JavaMethod}
		 * @throws IllegalArgumentException if anything is wrong with the current parameters
		 */
		public JavaMethod build(){
			checkForErrors();
			
			return new JavaMethod(annotations, visibility, returnType, name, parameters, lines);
		}
	}
	
	/** The {@link JavaAnnotation annotations} on the method */
	private final List<JavaAnnotation> annotations;
	/** The {@link Visibility} of the method */
	private final Visibility visibility;
	/** The return type of the method */
	private final String returnType;
	/** The name of the method */
	private final String name;
	/** The parameters used in the method - pairs of type, then name */
	private final List<Pair<String, String>> parameters;
	/** The actual lines of code in the method */
	private final List<String> lines;
	
	/**
	 * Constructs a new Java Method with the given parameters
	 *
	 * @param annotations The {@link JavaAnnotation annotations} on the method
	 * @param visibility The {@link Visibility} of the method
	 * @param returnType The return type of the method
	 * @param name The name of the method
	 * @param parameters The parameters used in the method - pairs of type, then name
	 * @param lines The actual lines of code in the method
	 */
	private JavaMethod(List<JavaAnnotation> annotations, Visibility visibility, String returnType, String name,
	                   List<Pair<String, String>> parameters, List<String> lines){
		this.annotations = annotations;
		this.visibility = visibility;
		this.returnType = returnType;
		this.name = name;
		this.parameters = parameters;
		this.lines = lines;
	}
	
	/**
	 * @return A new {@link JavaMethodBuilder} to use to build a {@link JavaMethod}
	 */
	public static JavaMethodBuilder builder(){
		return new JavaMethodBuilder();
	}
	
	/**
	 * @return The {@link JavaAnnotation annotations} on the method
	 */
	public List<JavaAnnotation> getAnnotations(){
		return annotations;
	}
	
	/**
	 * @return The {@link Visibility} of the method
	 */
	public Visibility getVisibility(){
		return visibility;
	}
	
	/**
	 * @return The return type of the method
	 */
	public String getReturnType(){
		return returnType;
	}
	
	/**
	 * @return The name of the method
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return The parameters used in the method - pairs of type, then name
	 */
	public List<Pair<String, String>> getParameters(){
		return parameters;
	}
	
	/**
	 * @return The actual lines of code in the method
	 */
	public List<String> getLines(){
		return lines;
	}
	
	/**
	 * @return This Java Method as a String, ready to be put in some Java code
	 */
	@Override
	public String toString(){
		List<String> content = new ArrayList<>();
		
		// Annotations
		if(ListUtil.isNotBlank(annotations)){
			for(JavaAnnotation annotation: annotations){
				content.add(annotation.toString());
			}
		}
		
		/*
		 * Declaration
		 */
		StringBuilder declaration = new StringBuilder(visibility.getText() + " " + returnType);
		
		// Add name to declaration if we have it
		if(StringUtil.isNotBlank(name)){
			declaration.append(" ").append(name);
		}
		
		// Start of parameter section
		declaration.append("(");
		
		// Add parameters to the declaration
		if(ListUtil.isNotBlank(parameters)){
			for(Pair<String, String> parameter: parameters){
				declaration.append(parameter.getLeft()).append(" ").append(parameter.getRight()).append(", ");
			}
			// Remove final comma + space
			declaration.setLength(declaration.length()-2);
		}
		
		// End of declaration
		declaration.append("){");
		content.add(declaration.toString());
		
		// Add the lines to the method
		if(ListUtil.isNotBlank(lines)){
			for(String line: lines){
				content.add("\t" + line);
			}
		}
		
		// Closing brace of the method
		content.add("}");
		
		return StringUtil.buildStringWithNewLines(content);
	}
}
