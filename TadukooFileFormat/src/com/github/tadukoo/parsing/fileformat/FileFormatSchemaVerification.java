package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.FileUtil;
import com.github.tadukoo.util.logger.EasyLogger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class is used to verify that files match a particular {@link FileFormatSchema}.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.1
 */
public class FileFormatSchemaVerification{
	
	// Not allowed to make a FileFormatSchemaVerification instance
	private FileFormatSchemaVerification(){ }
	
	/**
	 * Checks if the file located at the given file path matches the given 
	 * {@link FileFormatSchema} or not.
	 * 
	 * @param logger The {@link EasyLogger} to log any messages
	 * @param format The FileFormat to use for checking against
	 * @param schema The FileFormatSchema to use for checking against
	 * @param filepath The path to the file to be checked
	 * @return If the file matches the formatting or not
	 */
	public static boolean verifyFileFormat(EasyLogger logger, FileFormat format, FileFormatSchema schema, String filepath){
		// Load the file
		Node headNode = Node.loadFromFile(filepath);
		
		return verifyFileFormat(logger, format, schema, filepath, headNode);
	}
	
	/**
	 * Checks if the given Nodes (given via the head Node) located (or to be located) 
	 * at the given file path matches the given {@link FileFormatSchema} or not.
	 * <br>
	 * This version allows for checking Nodes before they're saved.
	 * 
	 * @param logger The {@link EasyLogger} to log any messages
	 * @param format The FileFormat to use for checking against
	 * @param schema The FileFormatSchema to use for checking against
	 * @param filepath The path to the file to be checked
	 * @param headNode The head Node of those to be checked
	 * @return If the file (/Nodes) matches the formatting or not
	 */
	public static boolean verifyFileFormat(EasyLogger logger, FileFormat format, FileFormatSchema schema, String filepath,
	                                       Node headNode){
		logger.logInfo("Starting verification of file " + filepath + "...");
		// This will be the return value. It gets set false on any failure to match the FileFormatSchema
		boolean correctFileFormat = true;
		
		// Grab the file extension off the file name
		String fileExtension = FileUtil.getFileExtension(filepath);
		
		// Verify that the FileFormatSchema's fileExtension matches the one on the actual file
		if(!schema.getFileExtension().equalsIgnoreCase(fileExtension)){
			logger.logError("File Extension doesn't match!\n"
					+ "* Expected: " + schema.getFileExtension() + ", but was: " + fileExtension + "!");
			correctFileFormat = false;
		}
		
		// Convert the List of nodes from FileFormatSchema to a Map for easier access to a particularly named FormatNode
		Map<String, FormatNode> nodes = getFormatNodesMap(schema);
		
		// Check the Tad Format Node at the start of the file
		boolean goodTadFormatNode = TadFormatNodeHeader.verifyTadFormatNode(logger, headNode, format, schema);
		headNode = headNode.getNextSibling();
		
		// Find the FormatNodes that are allowed to be used after the Tad Format Node
		List<String> headNodeNames = nodes.values().stream()
													.filter(node -> node.getPrevSiblingNames()
																		.contains(TadFormatNodeHeader.HEAD_NAME))
													.map(FormatNode::getName)
													.collect(Collectors.toList());
		
		// Check the rest of the Nodes, that they're correct
		boolean goodNodes = verifyNode(logger, nodes, headNode, headNodeNames, filepath);
		
		// Update correctFileFormat to false if the nodes failed
		correctFileFormat = correctFileFormat && goodTadFormatNode && goodNodes;
		
		// Give a logger message on whether the format of the file matched or not
		if(correctFileFormat){
			logger.logInfo("File: " + filepath + " matches the FileFormatSchema!");
		}else{
			logger.logWarning("File: " + filepath + " does not match the FileFormatSchema!");
		}
		
		// Return whether the file's format matches that of the FileFormatSchema or not
		return correctFileFormat;
	}
	
	/**
	 * Creates a Map out of the {@link FormatNode}s present in the given {@link FileFormatSchema}.
	 * 
	 * @param schema The FileFormatSchema to grab the FormatNodes off of
	 * @return A mapping of FormatNode names to the FormatNodes themselves
	 */
	private static Map<String, FormatNode> getFormatNodesMap(FileFormatSchema schema){
		// Grab the List of Nodes from the schema
		List<FormatNode> nodes = schema.getFormatNodes();
		
		// Make a Map of the Nodes' names to the Nodes themselves
		Map<String, FormatNode> nodesMap = new HashMap<>();
		for(FormatNode node: nodes){
			nodesMap.put(node.getName(), node);
		}
		
		return nodesMap;
	}
	
	/**
	 * Checks if the given Node (and any children and siblings down the line) matches 
	 * the proper formatting passed in or not.
	 * 
	 * @param logger The {@link EasyLogger} to log any messages
	 * @param formatNodes The FormatNodes as a Map of their names to them
	 * @param node The Node to be tested
	 * @param nodeNames The allowed names for this particular Node's format
	 * @param filepath The path to the file (used in some formatting)
	 * @return If the Node matches the formatting appropriately or not
	 */
	private static boolean verifyNode(EasyLogger logger, Map<String, FormatNode> formatNodes, Node node,
			List<String> nodeNames, String filepath){
		// Need to determine if the passed-in Node is a good one or not
		boolean goodNode = false;
		int i = 0;
		String name;
		FormatNode format = null;
		
		if(node == null){
			// If Node is null, it's only good if null is allowed
			goodNode = nodeNames.contains(FormatNode.NULL_NODE);
		}else{
			// If Node isn't null, compare its format with the allowed formats until we find a match
			while(!goodNode && i < nodeNames.size()){
				// Grab current Node name to check
				name = nodeNames.get(i);
				// If it's the null node name, move on
				if(name.equals(FormatNode.NULL_NODE)){
					i++;
					continue;
				}
				// Grab FormatNode with the given name
				format = formatNodes.get(name);
				
				// Check if this Node matches the current FormatNode
				goodNode = FormatNodeVerification.verifySingleNode(
						NodeVerificationCriteria.builder()
												.logger(logger)
												.filepath(filepath)
												.node(node)
												.format(format)
												.build());
				
				// If not a good Node, give a Logger message
				if(!goodNode){
					logger.logDebugFine("Node does not match the " + name + " FormatNode!");
				}
				
				// Increment i to move to next name if it's not a match
				i++;
			}
			
			// If no match was made, we got an issue
			if(!goodNode){
				logger.logWarning("Node could not be identified!");
			}else{
				// If it's a match, continue in checking child and next sibling
				// Note: Parent and PrevSibling are not checked due to proper relationships being enforced elsewhere
				boolean childMatch = verifyNode(logger, formatNodes, node.getChild(), format.getChildNames(), filepath);
				boolean nextSiblingMatch = verifyNode(logger, formatNodes, node.getNextSibling(), format.getNextSiblingNames(), 
						filepath);
				
				// If child or next sibling fail, we got issues
				goodNode = childMatch && nextSiblingMatch;
			}
		}
		
		// If it's a good Node, give a Logger message about it
		if(goodNode && node != null){
			logger.logDebugFiner("This was a good node!\n"
					+ "* Format: " + format.getNodeRegex() + "\n"
					+ "* Actual: " + node);
		}
		
		return goodNode;
	}
}
