package com.github.tadukoo.parsing.java;

import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an Annotation in Java.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class JavaAnnotation{
	
	/**
	 * Java Annotation Builder is used to build a new {@link JavaAnnotation}. It contains the following parameters:
	 *
	 * <table>
	 *     <caption>Java Annotation Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>name</td>
	 *         <td>The name of the annotation</td>
	 *         <td>Required</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class JavaAnnotationBuilder{
		/** The name of the annotation */
		private String name;
		
		// Not allowed to create outside of Java Annotation
		private JavaAnnotationBuilder(){ }
		
		/**
		 * @param name The name of the annotation
		 * @return this, to continue building
		 */
		public JavaAnnotationBuilder name(String name){
			this.name = name;
			return this;
		}
		
		/**
		 * Checks for any errors in the current parameters
		 *
		 * @throws IllegalArgumentException if anything is wrong
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			if(StringUtil.isBlank(name)){
				errors.add("Must specify name!");
			}
			
			if(!errors.isEmpty()){
				throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Checks for any errors in the current parameters, then builds a new {@link JavaAnnotation}
		 *
		 * @return A newly built {@link JavaAnnotation}
		 * @throws IllegalArgumentException if anything is wrong with the current parameters
		 */
		public JavaAnnotation build(){
			checkForErrors();
			
			return new JavaAnnotation(name);
		}
	}
	
	/** The name of the annotation */
	private final String name;
	
	/**
	 * Constructs a Java Annotation with the given parameters
	 */
	private JavaAnnotation(String name){
		this.name = name;
	}
	
	/**
	 * @return A new {@link JavaAnnotationBuilder} to use to build a {@link JavaAnnotation}
	 */
	public static JavaAnnotationBuilder builder(){
		return new JavaAnnotationBuilder();
	}
	
	/**
	 * @return The name of the annotation
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return This Java Annotation as a string, ready to be put in some Java code
	 */
	@Override
	public String toString(){
		return "@" + name;
	}
}
