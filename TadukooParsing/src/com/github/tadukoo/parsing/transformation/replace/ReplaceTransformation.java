package com.github.tadukoo.parsing.transformation.replace;

import com.github.tadukoo.parsing.transformation.Transformation;
import com.github.tadukoo.parsing.transformation.TransformationType;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a {@link Transformation} where you replace part of the text with something else (which could be
 * simply a transformation of the found text)
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.4
 */
public class ReplaceTransformation implements Transformation{
	
	/**
	 * A {@link ReplaceTransformationBuilder builder} to build a {@link ReplaceTransformation}.
	 * It takes the following parameters:
	 * <table>
	 *     <caption>ReplaceTransformation Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default (or Required)</th>
	 *     </tr>
	 *     <tr>
	 *         <td>replaceType</td>
	 *         <td>The general {@link ReplaceType replacement type} of the {@link ReplaceTransformation}</td>
	 *         <td>{@link ReplaceType#ALL}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>replaceFromType</td>
	 *         <td>The type for the "from" part of the {@link ReplaceTransformation}</td>
	 *         <td>{@link ReplaceFromType#REGEX}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>replaceToType</td>
	 *         <td>The type for the "to" part of the {@link ReplaceTransformation}</td>
	 *         <td>{@link ReplaceToType#EXACT_STRING}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>ignoreCase</td>
	 *         <td>Whether to ignore case for matching on the "replaceFrom" string</td>
	 *         <td>{@code false}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>replaceFrom</td>
	 *         <td>The String to use for matching</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>replaceTo</td>
	 *         <td>The String to be used as a replacement for any matches</td>
	 *         <td>Required if using {@link ReplaceToType#EXACT_STRING} for {@code replaceToType}</td>
	 *     </tr>
	 *     <tr>
	 *         <td>replaceToTransformation</td>
	 *         <td>The {@link Transformation} to apply to any matches</td>
	 *         <td>Required if using {@link ReplaceToType#TRANSFORMATION} for {@code replaceToType}</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.4
	 */
	public static class ReplaceTransformationBuilder{
		/** The general {@link ReplaceType replacement type} of the {@link ReplaceTransformation} */
		private ReplaceType replaceType = ReplaceType.ALL;
		/** The type for the "from" part of the {@link ReplaceTransformation} */
		private ReplaceFromType replaceFromType = ReplaceFromType.REGEX;
		/** The type for the "to" part of the {@link ReplaceTransformation} */
		private ReplaceToType replaceToType = ReplaceToType.EXACT_STRING;
		/** Whether to ignore case for matching on the "replaceFrom" string */
		private boolean ignoreCase = false;
		/** The String to use for matching */
		private String replaceFrom = null;
		/** The String to be used as a replacement for any matches */
		private String replaceTo = null;
		/** The {@link Transformation} to apply to any matches */
		private Transformation replaceToTransformation = null;
		
		/** Not allowed to instantiate outside of {@link ReplaceTransformation} */
		private ReplaceTransformationBuilder(){ }
		
		/**
		 * @param replaceType The general {@link ReplaceType replacement type} of the {@link ReplaceTransformation}
		 * @return this, to continue building
		 */
		public ReplaceTransformationBuilder replaceType(ReplaceType replaceType){
			this.replaceType = replaceType;
			return this;
		}
		
		/**
		 * @param replaceFromType The type for the "from" part of the {@link ReplaceTransformation}
		 * @return this, to continue building
		 */
		public ReplaceTransformationBuilder replaceFromType(ReplaceFromType replaceFromType){
			this.replaceFromType = replaceFromType;
			return this;
		}
		
		/**
		 * @param replaceToType The type for the "to" part of the {@link ReplaceTransformation}
		 * @return this, to continue building
		 */
		public ReplaceTransformationBuilder replaceToType(ReplaceToType replaceToType){
			this.replaceToType = replaceToType;
			return this;
		}
		
		/**
		 * @param ignoreCase Whether to ignore case for matching on the "replaceFrom" string
		 * @return this, to continue building
		 */
		public ReplaceTransformationBuilder ignoreCase(boolean ignoreCase){
			this.ignoreCase = ignoreCase;
			return this;
		}
		
		/**
		 * Sets {@link #ignoreCase} to {@code true}
		 *
		 * @return this, to continue building
		 */
		public ReplaceTransformationBuilder ignoreCase(){
			ignoreCase = true;
			return this;
		}
		
		/**
		 * @param replaceFrom The String to use for matching
		 * @return this, to continue building
		 */
		public ReplaceTransformationBuilder replaceFrom(String replaceFrom){
			this.replaceFrom = replaceFrom;
			return this;
		}
		
		/**
		 * @param replaceTo The String to be used as a replacement for any matches
		 * @return this, to continue building
		 */
		public ReplaceTransformationBuilder replaceTo(String replaceTo){
			this.replaceTo = replaceTo;
			return this;
		}
		
		/**
		 * @param replaceToTransformation The {@link Transformation} to apply to any matches
		 * @return this, to continue building
		 */
		public ReplaceTransformationBuilder replaceToTransformation(Transformation replaceToTransformation){
			this.replaceToTransformation = replaceToTransformation;
			return this;
		}
		
		/**
		 * Checks for any errors in the set parameters
		 */
		private void checkForErrors(){
			List<String> errors = new ArrayList<>();
			
			// replaceType can't be null
			if(replaceType == null){
				errors.add("replaceType can't be null!");
			}
			
			// replaceFromType can't be null
			if(replaceFromType == null){
				errors.add("replaceFromType can't be null!");
			}
			
			// replaceToType can't be null
			if(replaceToType == null){
				errors.add("replaceToType can't be null!");
			}
			
			// replaceFrom can't be blank
			if(StringUtil.isBlank(replaceFrom)){
				errors.add("replaceFrom can't be blank!");
			}
			
			// replaceTo can't be null if using exact string to type
			if(replaceToType == ReplaceToType.EXACT_STRING && replaceTo == null){
				errors.add("replaceTo can't be null if using " + ReplaceToType.EXACT_STRING + " as the replaceToType!");
			}
			
			// replaceToTransformation can't be null if using transformation to type
			if(replaceToType == ReplaceToType.TRANSFORMATION && replaceToTransformation == null){
				errors.add("replaceToTransformation can't be null if using " + ReplaceToType.TRANSFORMATION +
						" as the replaceToType!");
			}
			
			// replaceTo and replaceToTransformation can't both be set
			if(replaceTo != null && replaceToTransformation != null){
				errors.add("replaceTo and replaceToTransformation can't both be set!");
			}
			
			// Report any errors found
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Errors encountered building a ReplaceTransformation: \n" +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Builds a new {@link ReplaceTransformation} after checking for errors in the set parameters
		 *
		 * @return The newly built {@link ReplaceTransformation}
		 */
		public ReplaceTransformation build(){
			checkForErrors();
			
			return new ReplaceTransformation(replaceType, replaceFromType, replaceToType,
					ignoreCase, replaceFrom, replaceTo,
					replaceToTransformation);
		}
	}
	
	/** The general {@link ReplaceType replacement type} of the {@link ReplaceTransformation} */
	private final ReplaceType replaceType;
	/** The type for the "from" part of the {@link ReplaceTransformation} */
	private final ReplaceFromType replaceFromType;
	/** The type for the "to" part of the {@link ReplaceTransformation} */
	private final ReplaceToType replaceToType;
	/** Whether to ignore case for matching on the "replaceFrom" string */
	private final boolean ignoreCase;
	/** The String to use for matching */
	private final String replaceFrom;
	/** The String to be used as a replacement for any matches */
	private final String replaceTo;
	/** The {@link Transformation} to apply to any matches */
	private final Transformation replaceToTransformation;
	
	/**
	 * Constructs a new {@link ReplaceTransformation} with the given parameters
	 *
	 * @param replaceType The general {@link ReplaceType replacement type} of the {@link ReplaceTransformation}
	 * @param replaceFromType The type for the "from" part of the {@link ReplaceTransformation}
	 * @param replaceToType The type for the "to" part of the {@link ReplaceTransformation}
	 * @param ignoreCase Whether to ignore case for matching on the "replaceFrom" string
	 * @param replaceFrom The String to use for matching
	 * @param replaceTo The String to be used as a replacement for any matches
	 * @param replaceToTransformation The {@link Transformation} to apply to any matches
	 */
	private ReplaceTransformation(
			ReplaceType replaceType, ReplaceFromType replaceFromType, ReplaceToType replaceToType,
			boolean ignoreCase, String replaceFrom, String replaceTo,
			Transformation replaceToTransformation){
		this.replaceType = replaceType;
		this.replaceFromType = replaceFromType;
		this.replaceToType = replaceToType;
		this.ignoreCase = ignoreCase;
		this.replaceFrom = replaceFrom;
		this.replaceTo = replaceTo;
		this.replaceToTransformation = replaceToTransformation;
	}
	
	/**
	 * @return A {@link ReplaceTransformationBuilder builder} to use to build a {@link ReplaceTransformation}
	 */
	public static ReplaceTransformationBuilder builder(){
		return new ReplaceTransformationBuilder();
	}
	
	/**
	 * Constructs a new {@link ReplaceTransformation} using the given {@link Transformation}
	 *
	 * @param transformation The {@link Transformation} to use to make a {@link ReplaceTransformation}
	 * @return The newly constructed {@link ReplaceTransformation}
	 */
	public static ReplaceTransformation fromTransformation(Transformation transformation){
		List<String> params = transformation.getParameters();
		List<Transformation> subTransformations = transformation.getSubTransformations();
		
		// Turn the parameters into the proper types
		ReplaceType replaceType = ReplaceType.fromString(params.get(0));
		ReplaceFromType replaceFromType = ReplaceFromType.fromString(params.get(1));
		ReplaceToType replaceToType = ReplaceToType.fromString(params.get(2));
		boolean ignoreCase = Boolean.parseBoolean(params.get(3));
		String replaceFrom = params.get(4);
		String replaceTo = params.get(5);
		
		// Turn the transformations into the proper types
		Transformation replaceToTransformation = subTransformations.isEmpty()?null:subTransformations.get(0);
		
		return ReplaceTransformation.builder()
				.replaceType(replaceType)
				.replaceFromType(replaceFromType).replaceToType(replaceToType)
				.ignoreCase(ignoreCase)
				.replaceFrom(replaceFrom).replaceTo(replaceTo)
				.replaceToTransformation(replaceToTransformation)
				.build();
	}
	
	/** {@inheritDoc} */
	@Override
	public TransformationType getType(){
		return TransformationType.REPLACE;
	}
	
	/** {@inheritDoc} */
	@Override
	public List<String> getParameters(){
		return ListUtil.createList(replaceType.toString(), replaceFromType.toString(), replaceToType.toString(),
				String.valueOf(ignoreCase), replaceFrom, replaceTo);
	}
	
	/** {@inheritDoc} */
	@Override
	public List<Transformation> getSubTransformations(){
		return ListUtil.createList(replaceToTransformation);
	}
	
	/** {@inheritDoc} */
	@Override
	public String applyTransformation(String text){
		// Build the regex to be used
		String replaceFromRegex = switch(replaceFromType){
			case REGEX -> replaceFrom;
			case EXACT_STRING -> Pattern.quote(replaceFrom);
		};
		if(ignoreCase){
			replaceFromRegex = "(?i)" + replaceFromRegex;
		}
		
		// Handle the replacement based on to type
		return switch(replaceToType){
			case TRANSFORMATION -> {
				// Find any matches and handle them
				Pattern pattern = Pattern.compile(replaceFromRegex);
				Matcher matcher = pattern.matcher(text);
				yield switch(replaceType){
					case ALL -> {
						String newText = text;
						while(matcher.find()){
							String found = matcher.group(0);
							String transformed = replaceToTransformation.applyTransformation(found);
							newText = newText.replaceFirst(found, transformed);
						}
						yield newText;
					}
					case FIRST -> {
						String newText = text;
						if(matcher.find()){
							String found = matcher.group(0);
							String transformed = replaceToTransformation.applyTransformation(found);
							newText = newText.replaceFirst(found, transformed);
						}
						yield newText;
					}
				};
			}
			case EXACT_STRING -> switch(replaceType){
				case ALL -> text.replaceAll(replaceFromRegex, replaceTo);
				case FIRST -> text.replaceFirst(replaceFromRegex, replaceTo);
			};
		};
	}
}
