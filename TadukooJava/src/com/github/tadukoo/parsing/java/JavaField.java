package com.github.tadukoo.parsing.java;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Java Field represents a field in a {@link JavaClass Java class}
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.2
 */
public class JavaField{
	
	/**
	 * Java Field Builder is a builder to create a {@link JavaField}. It has the following parameters:
	 *
	 * <table>
	 *     <caption>Java Field Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>annotations</td>
	 *         <td>The {@link JavaAnnotation annotations} on the field</td>
	 *         <td>An empty list</td>
	 *     </tr>
	 *     <tr>
	 *         <td>visibility</td>
	 *         <td>The {@link Visibility} of the field</td>
	 *         <td>{@link Visibility#PRIVATE}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>type</td>
	 *         <td>The type of the field</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>name</td>
	 *         <td>The name of the field</td>
	 *         <td>Required</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 * @since Alpha v.0.2
	 */
	public static class JavaFieldBuilder{
		/** The {@link JavaAnnotation annotations} on the field */
		private List<JavaAnnotation> annotations = new ArrayList<>();
		/** The {@link Visibility} of the field */
		private Visibility visibility = Visibility.PRIVATE;
		/** The type of the field */
		private String type = null;
		/** The name of the field */
		private String name = null;
		
		// Can't create outside of JavaField
		private JavaFieldBuilder(){ }
		
		/**
		 * @param annotations The {@link JavaAnnotation annotations} on the field
		 * @return this, to continue building
		 */
		public JavaFieldBuilder annotations(List<JavaAnnotation> annotations){
			this.annotations = annotations;
			return this;
		}
		
		/**
		 * @param annotation A single {@link JavaAnnotation annotation} on the field
		 * @return this, to continue building
		 */
		public JavaFieldBuilder annotation(JavaAnnotation annotation){
			annotations.add(annotation);
			return this;
		}
		
		/**
		 * @param visibility The {@link Visibility} of the field
		 * @return this, to continue building
		 */
		public JavaFieldBuilder visibility(Visibility visibility){
			this.visibility = visibility;
			return this;
		}
		
		/**
		 * @param type The type of the field
		 * @return this, to continue building
		 */
		public JavaFieldBuilder type(String type){
			this.type = type;
			return this;
		}
		
		/**
		 * @param name The name of the field
		 * @return this, to continue building
		 */
		public JavaFieldBuilder name(String name){
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
			
			if(StringUtil.isBlank(type)){
				errors.add("Must specify type!");
			}
			
			if(StringUtil.isBlank(name)){
				errors.add("Must specify name!");
			}
			
			if(!errors.isEmpty()){
				throw new IllegalArgumentException(StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Checks for any errors in the current parameters, then builds a new {@link JavaField}
		 *
		 * @return A newly built {@link JavaField}
		 * @throws IllegalArgumentException if anything is wrong with the current parameters
		 */
		public JavaField build(){
			checkForErrors();
			
			return new JavaField(annotations, visibility, type, name);
		}
	}
	
	/** The {@link JavaAnnotation annotations} on the field */
	private final List<JavaAnnotation> annotations;
	/** The {@link Visibility} of the field */
	private final Visibility visibility;
	/** The type of the field */
	private final String type;
	/** The name of the field */
	private final String name;
	
	/**
	 * Constructs a Java Field with the given parameters
	 *
	 * @param annotations The {@link JavaAnnotation annotations} on the field
	 * @param visibility The {@link Visibility} of the field
	 * @param type The type of the field
	 * @param name The name of the field
	 */
	private JavaField(List<JavaAnnotation> annotations, Visibility visibility, String type, String name){
		this.annotations = annotations;
		this.visibility = visibility;
		this.type = type;
		this.name = name;
	}
	
	/**
	 * @return A new {@link JavaFieldBuilder} to create a new {@link JavaField}
	 */
	public static JavaFieldBuilder builder(){
		return new JavaFieldBuilder();
	}
	
	/**
	 * @return The {@link JavaAnnotation annotations} on the field
	 */
	public List<JavaAnnotation> getAnnotations(){
		return annotations;
	}
	
	/**
	 * @return The {@link Visibility} of the field
	 */
	public Visibility getVisibility(){
		return visibility;
	}
	
	/**
	 * @return The type of the field
	 */
	public String getType(){
		return type;
	}
	
	/**
	 * @return The name of the field
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return A string of the form "{visibility.getText()} {type} {name}", with annotations on newlines above
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
		
		// Add field declaration
		content.add(visibility.getText() + " " + type + " " + name);
		
		return StringUtil.buildStringWithNewLines(content);
	}
}
