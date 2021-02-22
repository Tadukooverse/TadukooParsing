package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.logger.EasyLogger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class contains methods for verifying a file's format using {@link FormatNode}s.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.1
 */
public class FormatNodeVerification{
	
	// Not allowed to instantiate Format Node Verification
	private FormatNodeVerification(){ }
	
	/**
	 * Verifies if the format of the Node given in the parameters is of the correct format.
	 * 
	 * @param criteria The parameters object that holds all the needed arguments
	 * @return If the Node had the correct format or not
	 */
	public static boolean verifySingleNode(NodeVerificationCriteria criteria){
		// Grab the params out of the pojo
		EasyLogger logger = criteria.getLogger();
		String filepath = criteria.getFilepath();
		Node node = criteria.getNode();
		FormatNode format = criteria.getFormat();
		Node parent = criteria.getParent();
		Node child = criteria.getChild();
		Node prevSibling = criteria.getPrevSibling();
		Node nextSibling = criteria.getNextSibling();
		
		// Check if the title of this Node matches the FormatNode's format
		boolean titleMatch = verifyFormat(filepath, format.getTitleRegex(), node.getTitle());
		// If title doesn't match, give a Logger message
		if(!titleMatch){
			logger.logDebugFine("Title doesn't match!\n"
					+ "* In checking Node " + node.toString() + " as a " + format.getName() + "\n"
					+ "* Format Expected: " + format.getTitleRegex() + "\n"
					+ "* Title Received: " + node.getTitle());
		}
		
		// Check if the data of this Node matches the FormatNode's format
		boolean dataMatch = verifyFormat(filepath, format.getDataRegex(), node.getData());
		// If data doesn't match, give a Logger message
		if(!dataMatch){
			logger.logDebugFine("Data doesn't match!\n"
					+ "* In checking Node " + node.toString() + " as a " + format.getName() + "\n"
					+ "* Format Expected: " + format.getDataRegex() + "\n"
					+ "* Data Received: " + node.getData());
		}
		
		// Check if the level of this Node matches the FormatNode's required level
		boolean levelMatch = format.getLevel() == node.getLevel();
		// If level doesn't match, give a Logger message
		if(!levelMatch){
			logger.logDebugFine("Incorrect Node level!\n"
					+ "* In checking Node " + node.toString() + " as a " + format.getName() + "\n"
					+ "* Expected: " + format.getLevel() + ", but was: " + node.getLevel() + "!");
		}
		
		// It's a good Node if all things matched
		boolean goodNode = titleMatch && dataMatch && levelMatch;
		
		// If it should have a null parent, assert its parent is null
		if(criteria.checkNullParent() && !assertNullParent(logger, node)){
			// If parent isn't null (when it should be), it's a bad node
			goodNode = false;
		}else if(parent != null && !assertParent(logger, node, parent)){
			// Check that this Node has the correct parent (if specified)
			// If not, it's a bad Node
			goodNode = false;
		}
		
		// If it should have a null child, assert its child is null
		if(criteria.checkNullChild() && !assertNullChild(logger, node)){
			// If child isn't null (when it should be), it's a bad node
			goodNode = false;
		}else if(child != null && !assertChild(logger, node, child)){
			// Check that this Node has the correct child (if specified)
			// If not, it's a bad Node
			goodNode = false;
		}
		
		// If it should have a null previous sibling, assert its previous sibling is null
		if(criteria.checkNullPrevSibling() && !assertNullPrevSibling(logger, node)){
			// If previous sibling isn't null (when it should be), it's a bad node
			goodNode = false;
		}else if(prevSibling != null && !assertPrevSibling(logger, node, prevSibling)){
			// Check that this Node has the correct previous sibling (if specified)
			// If not, it's a bad Node
			goodNode = false;
		}
		
		// If it should have a null next sibling, assert its next sibling is null
		if(criteria.checkNullNextSibling() && !assertNullNextSibling(logger, node)){
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
		String filename = "";
		String fileTitle = "";
		String fileExtension = "";
		
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
			fileExtension = filename.substring(periodIndex+1);
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
	 * @param logger The {@link EasyLogger} to use to log any issues
	 * @param node The Node to check the parent of
	 * @param parent The expected parent
	 * @return Whether the parent matches the expected or not
	 */
	public static boolean assertParent(EasyLogger logger, Node node, Node parent){
		// Check that Node's parent is correct
		if(!parent.equals(node.getParent())){
			logger.logDebugFine("* Node " + node.toString() + " should have Node " +
						parent.toString() + " as its parent!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the parent on the given Node is null.
	 * 
	 * @param logger The {@link EasyLogger} to use to log any issues
	 * @param node The Node to check the parent of
	 * @return Whether the parent is null or not
	 */
	public static boolean assertNullParent(EasyLogger logger, Node node){
		// Check if Node has a parent (it shouldn't)
		if(node.getParent() != null){
			logger.logDebugFine("* Node " + node.toString() + " shouldn't have a parent!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the given child matches the child on the given Node.
	 * 
	 * @param logger The {@link EasyLogger} to use to log any issues
	 * @param node The Node to check the child of
	 * @param child The expected child
	 * @return Whether the child matches the expected or not
	 */
	public static boolean assertChild(EasyLogger logger, Node node, Node child){
		// Check that Node's previous sibling is correct
		if(!child.equals(node.getChild())){
			logger.logDebugFine("* Node " + node.toString() + " should have Node " +
						child.toString() + " as its child!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the child on the given Node is null.
	 * 
	 * @param logger The {@link EasyLogger} to use to log any issues
	 * @param node The Node to check the child of
	 * @return Whether the child is null or not
	 */
	public static boolean assertNullChild(EasyLogger logger, Node node){
		// Check if Node has a child (it shouldn't)
		if(node.getChild() != null){
			logger.logDebugFine("* Node " + node.toString() + " shouldn't have a child!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the given previous sibling matches the previous sibling on the given Node.
	 * 
	 * @param logger The {@link EasyLogger} to use to log any issues
	 * @param node The Node to check the previous sibling of
	 * @param prevSibling The expected previous sibling
	 * @return Whether the previous sibling matches the expected or not
	 */
	public static boolean assertPrevSibling(EasyLogger logger, Node node, Node prevSibling){
		// Check that Node's previous sibling is correct
		if(!prevSibling.equals(node.getPrevSibling())){
			logger.logDebugFine("* Node " + node.toString() + " should have Node " +
						prevSibling.toString() + " as its previous sibling!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the previous sibling on the given Node is null.
	 * 
	 * @param logger The {@link EasyLogger} to use to log any issues
	 * @param node The Node to check the previous sibling of
	 * @return Whether the previous sibling is null or not
	 */
	public static boolean assertNullPrevSibling(EasyLogger logger, Node node){
		// Check if Node has a previous sibling (it shouldn't)
		if(node.getPrevSibling() != null){
			logger.logDebugFine("* Node " + node.toString() + " shouldn't have a previous sibling!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the given next sibling matches the next sibling on the given Node.
	 * 
	 * @param logger The {@link EasyLogger} to use to log any issues
	 * @param node The Node to check the next sibling of
	 * @param nextSibling The expected next sibling
	 * @return Whether the next sibling matches the expected or not
	 */
	public static boolean assertNextSibling(EasyLogger logger, Node node, Node nextSibling){
		// Check that Node's previous sibling is correct
		if(!nextSibling.equals(node.getNextSibling())){
			logger.logDebugFine("* Node " + node.toString() + " should have Node " +
						nextSibling.toString() + " as its next sibling!");
			return false;
		}
		
		return true;
	}
	
	/**
	 * Checks that the next sibling on the given Node is null.
	 * 
	 * @param logger The {@link EasyLogger} to use to log any issues
	 * @param node The Node to check the next sibling of
	 * @return Whether the next sibling is null or not
	 */
	public static boolean assertNullNextSibling(EasyLogger logger, Node node){
		// Check if Node has a child (it shouldn't)
		if(node.getNextSibling() != null){
			logger.logDebugFine("* Node " + node.toString() + " shouldn't have a next sibling!");
			return false;
		}
		
		return true;
	}
}
