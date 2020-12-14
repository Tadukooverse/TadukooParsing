package com.github.tadukoo.parsing.fileformat;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains methods for verifying a file's format using {@link FormatNode}s.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version 0.1-Alpha-SNAPSHOT
 */
public class FormatNodeVerification{
	
	public static class SingleNodeVerifyParams{
		
		public static class SingleNodeVerifyParamsBuilder{
			/** The Logger to use in logging messages */
			private Logger logger = null;
			/** The path to the file - used in some formatting */
			private String filepath = null;
			/** The Node to be checked */
			private Node node = null;
			/** The FormatNode that defines the expected format */
			private FormatNode format = null;
			/** Whether to check that the parent is null or not */
			private boolean nullParent = false;
			/** Whether to check that the child is null or not */
			private boolean nullChild = false;
			/** Whether to check that the previous sibling is null or not */
			private boolean nullPrevSibling = false;
			/** Whether to check that the next sibling is null or not */
			private boolean nullNextSibling = false;
			/** The expected parent for the Node */
			private Node parent = null;
			/** The expected child for the Node */
			private Node child = null;
			/** The expected previous sibling for the Node */
			private Node prevSibling = null;
			/** The expected next sibling for the Node */
			private Node nextSibling = null;
			
			// Not allowed to create this outside of FormatNodeVerification
			private SingleNodeVerifyParamsBuilder(){ }
			
			/** 
			 * Sets the Logger to use in logging messages
			 * 
			 * @param logger The Logger to use in logging messages
			 * @return This builder, to continue in building
			 */
			public SingleNodeVerifyParamsBuilder logger(Logger logger){
				this.logger = logger;
				return this;
			}
			
			/**
			 * Sets the path to the file to be used in some formatting checks
			 * 
			 * @param filepath The path to the file
			 * @return This builder, to continue in building
			 */
			public SingleNodeVerifyParamsBuilder filepath(String filepath){
				this.filepath = filepath;
				return this;
			}
			
			/**
			 * Sets the Node to be checked
			 * 
			 * @param node The Node to be checked
			 * @return This builder, to continue in building
			 */
			public SingleNodeVerifyParamsBuilder node(Node node){
				this.node = node;
				return this;
			}
			
			/**
			 * Sets the FormatNode, that defines the expected format
			 * 
			 * @param format The FormatNode that defines the expected format
			 * @return This builder, to continue in building
			 */
			public SingleNodeVerifyParamsBuilder format(FormatNode format){
				this.format = format;
				return this;
			}
			
			/** 
			 * Sets to check that the parent is null
			 * 
			 * @return This builder, to continue in building
			 */
			public SingleNodeVerifyParamsBuilder nullParent(){
				nullParent = true;
				return this;
			}
			
			/** 
			 * Sets to check that the child is null
			 * 
			 * @return This builder, to continue in building
			 */
			public SingleNodeVerifyParamsBuilder nullChild(){
				nullChild = true;
				return this;
			}
			
			/** 
			 * Sets to check that the previous sibling is null
			 * 
			 * @return This builder, to continue in building
			 */
			public SingleNodeVerifyParamsBuilder nullPrevSibling(){
				nullPrevSibling = true;
				return this;
			}
			
			/** 
			 * Sets to check that the next sibling is null
			 * 
			 * @return This builder, to continue in building
			 */
			public SingleNodeVerifyParamsBuilder nullNextSibling(){
				nullNextSibling = true;
				return this;
			}
			
			/**
			 * Sets the expected parent for the Node being checked
			 * 
			 * @param parent The expected parent for the Node being checked
			 * @return This builder, to continue building
			 */
			public SingleNodeVerifyParamsBuilder parent(Node parent){
				this.parent = parent;
				return this;
			}
			
			/**
			 * Sets the expected child for the Node being checked
			 * 
			 * @param child The expected child for the Node being checked
			 * @return This builder, to continue building
			 */
			public SingleNodeVerifyParamsBuilder child(Node child){
				this.child = child;
				return this;
			}
			
			/**
			 * Sets the expected previous sibling for the Node being checked
			 * 
			 * @param prevSibling The expected previous sibling for the Node being checked
			 * @return This builder, to continue building
			 */
			public SingleNodeVerifyParamsBuilder prevSibling(Node prevSibling){
				this.prevSibling = prevSibling;
				return this;
			}
			
			/**
			 * Sets the expected next sibling for the Node being checked
			 * 
			 * @param nextSibling The expected next sibling for the Node being checked
			 * @return This builder, to continue building
			 */
			public SingleNodeVerifyParamsBuilder nextSibling(Node nextSibling){
				this.nextSibling = nextSibling;
				return this;
			}
			
			/**
			 * Checks the currently set parameters for errors and throws an exception if there are any
			 */
			private void checkForErrors(){
				List<String> errors = new ArrayList<>();
				
				// Logger is required
				if(logger == null){
					errors.add("Logger can't be null!");
				}
				
				// Node is required
				if(node == null){
					errors.add("Node can't be null!");
				}
				
				// FormatNode is required
				if(format == null){
					errors.add("Format (Node) can't be null!");
				}
				
				// Can't check null and existing parent
				if(nullParent && parent != null){
					throw new IllegalArgumentException("Can't have a null parent and one defined!");
				}
				
				// Can't check null and existing child
				if(nullChild && child != null){
					throw new IllegalArgumentException("Can't have a null child and one defined!");
				}
				
				// Can't check null and existing previous sibling
				if(nullPrevSibling && prevSibling != null){
					throw new IllegalArgumentException("Can't have a null previous sibling and one defined!");
				}
				
				// Can't check null and existing next sibling
				if(nullNextSibling && nextSibling != null){
					throw new IllegalArgumentException("Can't have a null next sibling and one defined!");
				}
				
				// If any errors, throw an exception
				if(!errors.isEmpty()){
					throw new IllegalArgumentException("Failed to create Single Node Verification Parameters: " + 
							errors.toString());
				}
			}
			
			/**
			 * Actually builds the SingleNodeVerifyParams object
			 * 
			 * @return The created SingleNodeVerifyParams
			 */
			public SingleNodeVerifyParams build(){
				// Check for any problems
				checkForErrors();
				
				// Build the actual params object
				return new SingleNodeVerifyParams(logger, filepath, node, format, 
						nullParent, nullChild, nullPrevSibling, nullNextSibling,
						parent, child, prevSibling, nextSibling);
			}
		}
		
		/** The Logger to use in logging messages */
		private final Logger logger;
		/** The path to the file - used in some formatting */
		private final String filepath;
		/** The Node to be checked */
		private final Node node;
		/** The FormatNode that defines the expected format */
		private final FormatNode format;
		/** Whether to check that the parent is null or not */
		private final boolean nullParent;
		/** Whether to check that the child is null or not */
		private final boolean nullChild;
		/** Whether to check that the previous sibling is null or not */
		private final boolean nullPrevSibling;
		/** Whether to check that the next sibling is null or not */
		private final boolean nullNextSibling;
		/** The expected parent for the Node */
		private final Node parent;
		/** The expected child for the Node */
		private final Node child;
		/** The expected previous sibling for the Node */
		private final Node prevSibling;
		/** The expected next sibling for the Node */
		private final Node nextSibling;
		
		// Constructs a SingleNodeVerifyParams object
		private SingleNodeVerifyParams(Logger logger, String filepath, Node node, FormatNode format,
		                               boolean nullParent, boolean nullChild, boolean nullPrevSibling, boolean nullNextSibling,
		                               Node parent, Node child, Node prevSibling, Node nextSibling){
			this.logger = logger;
			this.filepath = filepath;
			this.node = node;
			this.format = format;
			this.nullParent = nullParent;
			this.nullChild = nullChild;
			this.nullPrevSibling = nullPrevSibling;
			this.nullNextSibling = nullNextSibling;
			this.parent = parent;
			this.child = child;
			this.prevSibling = prevSibling;
			this.nextSibling = nextSibling;
		}
	}
	
	/**
	 * @return A builder for the params object for verifying a single Node
	 */
	public static SingleNodeVerifyParams.SingleNodeVerifyParamsBuilder singleNodeVerificationParametersBuilder(){
		return new SingleNodeVerifyParams.SingleNodeVerifyParamsBuilder();
	}
	
	/**
	 * Verifies if the format of the Node given in the parameters is of the correct format.
	 * 
	 * @param params The parameters object that holds all the needed arguments
	 * @return If the Node had the correct format or not
	 */
	public static boolean verifySingleNode(SingleNodeVerifyParams params){
		// Grab the params out of the pojo
		Logger logger = params.logger;
		String filepath = params.filepath;
		Node node = params.node;
		FormatNode format = params.format;
		Node parent = params.parent;
		Node child = params.child;
		Node prevSibling = params.prevSibling;
		Node nextSibling = params.nextSibling;
		
		// Check if the title of this Node matches the FormatNode's format
		boolean titleMatch = verifyFormat(filepath, format.getTitleRegex(), node.getTitle());
		// If title doesn't match, give a Logger message
		if(!titleMatch){
			logger.log(Level.FINE, "Title doesn't match!\n"
					+ "* In checking Node " + node.toString() + " as a " + format.getName() + "\n"
					+ "* Format Expected: " + format.getTitleRegex() + "\n"
					+ "* Title Received: " + node.getTitle());
		}
		
		// Check if the data of this Node matches the FormatNode's format
		boolean dataMatch = verifyFormat(filepath, format.getDataRegex(), node.getData());
		// If data doesn't match, give a Logger message
		if(!dataMatch){
			logger.log(Level.FINE, "Data doesn't match!\n"
					+ "* In checking Node " + node.toString() + " as a " + format.getName() + "\n"
					+ "* Format Expected: " + format.getDataRegex() + "\n"
					+ "* Data Received: " + node.getData());
		}
		
		// Check if the level of this Node matches the FormatNode's required level
		boolean levelMatch = format.getLevel() == node.getLevel();
		// If level doesn't match, give a Logger message
		if(!levelMatch){
			logger.log(Level.FINE, "Incorrect Node level!\n"
					+ "* In checking Node " + node.toString() + " as a " + format.getName() + "\n"
					+ "* Expected: " + format.getLevel() + ", but was: " + node.getLevel() + "!");
		}
		
		// It's a good Node if all things matched
		boolean goodNode = titleMatch && dataMatch && levelMatch;
		
		// If it should have a null parent, assert its parent is null
		if(params.nullParent && !assertNullParent(logger, node)){
			// If parent isn't null (when it should be), it's a bad node
			goodNode = false;
		}else if(parent != null && !assertParent(logger, node, parent)){
			// Check that this Node has the correct parent (if specified)
			// If not, it's a bad Node
			goodNode = false;
		}
		
		// If it should have a null child, assert its child is null
		if(params.nullChild && !assertNullChild(logger, node)){
			// If child isn't null (when it should be), it's a bad node
			goodNode = false;
		}else if(child != null && !assertChild(logger, node, child)){
			// Check that this Node has the correct child (if specified)
			// If not, it's a bad Node
			goodNode = false;
		}
		
		// If it should have a null previous sibling, assert its previous sibling is null
		if(params.nullPrevSibling && !assertNullPrevSibling(logger, node)){
			// If previous sibling isn't null (when it should be), it's a bad node
			goodNode = false;
		}else if(prevSibling != null && !assertPrevSibling(logger, node, prevSibling)){
			// Check that this Node has the correct previous sibling (if specified)
			// If not, it's a bad Node
			goodNode = false;
		}
		
		// If it should have a null next sibling, assert its next sibling is null
		if(params.nullNextSibling && !assertNullNextSibling(logger, node)){
			// If next sibling isn't null (when it should be), it's a bad node
			goodNode = false;
		}else if(nextSibling != null && !assertNextSibling(logger, node, nextSibling)){
			// Check that this Node has the correct next sibling (if specified)
			// If not, it's a bad Node
			goodNode = false;
		}
		
		return goodNode;
	}
	
	/**
	 * Checks if the formatting of the given String matches the given formatting. 
	 * The filepath is passed in to be replaced in the regex if any TFormatting is 
	 * present specific to filepath variables.
	 * 
	 * @param filepath The path to the file
	 * @param regex The formatting to match against
	 * @param actual The String to be tested for formatting conformity
	 * @return If the String matches the formatting or not
	 */
	private static boolean verifyFormat(String filepath, String regex, String actual){
		String filename = null;
		String fileTitle = null;
		String fileExtension = null;
		
		// If filepath isn't null, split it into the useful pieces
		if(filepath != null){
			// Get filename for use in matching against <filename> in the FileFormatSchema
			String[] filepathPieces = filepath.split("/");
			filename = filepathPieces[filepathPieces.length - 1];
			
			// Get fileTitle and fileExtension for future use
			int periodIndex = filename.indexOf('.');
			// FileTitle is used in matching against <filetitle> in the FileFormatSchema
			fileTitle = filename.substring(0, periodIndex);
			// FileExtension is used in matching against <fileExtension> in the FileFormatSchema
			fileExtension = filename.substring(periodIndex);
		}
		
		// Replace <filename> (used in TFormatting) with the actual filename
		if(regex.contains("<filename>")){
			regex = regex.replaceAll("<filename>", filename);
		}
		
		// Replace <fileTitle> (used in TFormatting) with the actual fileTitle
		if(regex.contains("<fileTitle>")){
			regex = regex.replaceAll("<fileTitle>", fileTitle);
		}
		
		// Replace <fileExtension> (used in TFormatting) with the actual fileExtension
		if(regex.contains("<fileExtension>")){
			regex = regex.replaceAll("<fileExtension>", fileExtension);
		}
		
		// Create the Pattern and a Matcher for it
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(actual);
		
		// Check if the given string actually matches the formatting
		return matcher.matches();
	}
	
	/**
	 * Checks that the given parent matches the parent on the given Node.
	 * 
	 * @param logger The Logger to use to log any issues
	 * @param node The Node to check the parent of
	 * @param parent The expected parent
	 * @return Whether the parent matches the expected or not
	 */
	public static boolean assertParent(Logger logger, Node node, Node parent){
		// Check that Node's parent is correct
		if(!node.getParent().equals(parent)){
			logger.log(Level.FINE, "* Node " + node.toString() + " should have Node " + 
						parent.toString() + " as its parent!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the parent on the given Node is null.
	 * 
	 * @param logger The logger to use to log any issues
	 * @param node The Node to check the parent of
	 * @return Whether the parent is null or not
	 */
	public static boolean assertNullParent(Logger logger, Node node){
		// Check if Node has a parent (it shouldn't)
		if(node.getParent() != null){
			logger.log(Level.FINE, "* Node " + node.toString() + " shouldn't have a parent!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the given child matches the child on the given Node.
	 * 
	 * @param logger The Logger to use to log any issues
	 * @param node The Node to check the child of
	 * @param child The expected child
	 * @return Whether the child matches the expected or not
	 */
	public static boolean assertChild(Logger logger, Node node, Node child){
		// Check that Node's previous sibling is correct
		if(!node.getChild().equals(child)){
			logger.log(Level.FINE, "* Node " + node.toString() + " should have Node " + 
						child.toString() + " as its child!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the child on the given Node is null.
	 * 
	 * @param logger The logger to use to log any issues
	 * @param node The Node to check the child of
	 * @return Whether the child is null or not
	 */
	public static boolean assertNullChild(Logger logger, Node node){
		// Check if Node has a child (it shouldn't)
		if(node.getChild() != null){
			logger.log(Level.FINE, "* Node " + node.toString() + " shouldn't have a child!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the given previous sibling matches the previous sibling on the given Node.
	 * 
	 * @param logger The Logger to use to log any issues
	 * @param node The Node to check the previous sibling of
	 * @param prevSibling The expected previous sibling
	 * @return Whether the previous sibling matches the expected or not
	 */
	public static boolean assertPrevSibling(Logger logger, Node node, Node prevSibling){
		// Check that Node's previous sibling is correct
		if(!node.getPrevSibling().equals(prevSibling)){
			logger.log(Level.FINE, "* Node " + node.toString() + " should have Node " + 
						prevSibling.toString() + " as its previous sibling!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the previous sibling on the given Node is null.
	 * 
	 * @param logger The logger to use to log any issues
	 * @param node The Node to check the previous sibling of
	 * @return Whether the previous sibling is null or not
	 */
	public static boolean assertNullPrevSibling(Logger logger, Node node){
		// Check if Node has a previous sibling (it shouldn't)
		if(node.getPrevSibling() != null){
			logger.log(Level.FINE, "* Node " + node.toString() + " shouldn't have a previous sibling!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the given next sibling matches the next sibling on the given Node.
	 * 
	 * @param logger The Logger to use to log any issues
	 * @param node The Node to check the next sibling of
	 * @param nextSibling The expected next sibling
	 * @return Whether the next sibling matches the expected or not
	 */
	public static boolean assertNextSibling(Logger logger, Node node, Node nextSibling){
		// Check that Node's previous sibling is correct
		if(!node.getNextSibling().equals(nextSibling)){
			logger.log(Level.FINE, "* Node " + node.toString() + " should have Node " + 
						nextSibling.toString() + " as its next sibling!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the next sibling on the given Node is null.
	 * 
	 * @param logger The logger to use to log any issues
	 * @param node The Node to check the next sibling of
	 * @return Whether the next sibling is null or not
	 */
	public static boolean assertNullNextSibling(Logger logger, Node node){
		// Check if Node has a child (it shouldn't)
		if(node.getNextSibling() != null){
			logger.log(Level.FINE, "* Node " + node.toString() + " shouldn't have a next sibling!");
			return false;
		}
		
		return true;
	}
}
