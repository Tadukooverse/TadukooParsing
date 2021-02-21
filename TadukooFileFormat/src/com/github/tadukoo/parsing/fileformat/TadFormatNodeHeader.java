package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.logger.EasyLogger;

/**
 * This class defines the Tad Format Node Header section that should be at the top 
 * of all files.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.1
 */
public class TadFormatNodeHeader{
	/** The name for the Head Tad Format Node */
	public static final String HEAD_NAME = "TadFormat";
	/** The name for the Tad Format Version Number Node */
	private static final String TAD_VERSION_NUMBER = "TadFormat Version Num";
	/** The name for the File Format Node */
	private static final String FILE_FORMAT = "File Format";
	/** The name for the File Format Schema Node */
	private static final String SCHEMA = "Schema";
	/** The name for the File Format Schema Version Node */
	private static final String VERSION_STRING = "Version";
	/** The name for the File Format Schema Version Number Node */
	private static final String VERSION_NUMBER = "Version Num";
	
	/**
	 * Creates a {@link FormatNode} for the head Node of the Tad Format Header.
	 * 
	 * @param logger The {@link EasyLogger} to use in logging any issues
	 * @return The FormatNode for the head Tad Format Header Node
	 */
	private static FormatNode getHeadFormatNode(EasyLogger logger){
		return FormatNode.builder()
							.logger(logger)
							.name(HEAD_NAME)
							.titleRegex(HEAD_NAME)
							.dataRegex("")
							.level(0)
							.childName(TAD_VERSION_NUMBER)
							.build();
	}
	
	/**
	 * Creates a {@link FormatNode} for the Tad Format Version Node.
	 * 
	 * @param logger The {@link EasyLogger} to use in logging any issues
	 * @return The FormatNode for the Tad Format Version Node
	 */
	private static FormatNode getTadVersionNode(EasyLogger logger){
		return FormatNode.builder()
							.logger(logger)
							.name(TAD_VERSION_NUMBER)
							.titleRegex(TAD_VERSION_NUMBER)
							.dataRegex(String.valueOf(FileFormat.TAD_FORMAT_VERSION_NUM))
							.level(1)
							.parentName(HEAD_NAME)
							.nextSiblingName(FILE_FORMAT)
							.build();
	}
	
	/**
	 * Creates a {@link FormatNode} for the File Format Node.
	 * 
	 * @param logger The {@link EasyLogger} to use in logging any issues
	 * @return The FormatNode for the File Format Node
	 */
	private static FormatNode getFileFormatNode(EasyLogger logger, FileFormat format){
		return FormatNode.builder()
							.logger(logger)
							.name(FILE_FORMAT)
							.titleRegex(FILE_FORMAT)
							.dataRegex(format.getName())
							.level(1)
							.childName(SCHEMA)
							.prevSiblingName(TAD_VERSION_NUMBER)
							.build();
	}
	
	/**
	 * Creates a {@link FormatNode} for the File Format Schema Node.
	 * 
	 * @param logger The {@link EasyLogger} to use in logging any issues
	 * @return The FormatNode for the File Format Schema Node
	 */
	private static FormatNode getSchemaNode(EasyLogger logger){
		return FormatNode.builder()
							.logger(logger)
							.name(SCHEMA)
							.titleRegex(SCHEMA)
							.dataRegex("")
							.level(2)
							.parentName(FILE_FORMAT)
							.childName(VERSION_STRING)
							.build();
	}
	
	/**
	 * Creates a {@link FormatNode} for the File Format Schema Version Node.
	 * 
	 * @param logger The {@link EasyLogger} to use in logging any issues
	 * @return The FormatNode for the File Format Schema Version Node
	 */
	private static FormatNode getVersionStringNode(EasyLogger logger, FileFormatSchema schema){
		return FormatNode.builder()
							.logger(logger)
							.name(VERSION_STRING)
							.titleRegex(VERSION_STRING)
							.dataRegex(schema.getVersionString())
							.level(3)
							.parentName(SCHEMA)
							.nextSiblingName(VERSION_NUMBER)
							.build();
	}
	
	/**
	 * Creates a {@link FormatNode} for the File Format Schema Version Number Node.
	 * 
	 * @param logger The {@link EasyLogger} to use in logging any issues
	 * @return The FormatNode for the File Format Schema Version Number Node
	 */
	private static FormatNode getVersionNumNode(EasyLogger logger, FileFormatSchema schema){
		return FormatNode.builder()
							.logger(logger)
							.name(VERSION_NUMBER)
							.titleRegex(VERSION_NUMBER)
							.dataRegex(String.valueOf(schema.getVersionNum()))
							.level(3)
							.prevSiblingName(VERSION_STRING)
							.build();
	}
	
	/**
	 * Creates a Tad Format Header for the given {@link FileFormat} and {@link FileFormatSchema}.
	 * 
	 * @param format The FileFormat used for certain parameters
	 * @param schema The FileFormatSchema used for certain parameters
	 * @return The head Node created for this header
	 */
	public static Node createHeader(FileFormat format, FileFormatSchema schema){
		// Create the head Tad Format Node
		Node headNode = Node.builder()
				.title(HEAD_NAME)
				.build();
		
		// Create the Tad Format Version Node
		Node tadVersionNode = Node.builder()
				.title(TAD_VERSION_NUMBER).data(String.valueOf(FileFormat.TAD_FORMAT_VERSION_NUM)).level(1)
				.parent(headNode)
				.build();
		headNode.setChild(tadVersionNode);
		
		// Create the File Format Node
		Node fileFormatNode = Node.builder()
				.title(FILE_FORMAT).data(format.getName()).level(1)
				.prevSibling(tadVersionNode)
				.build();
		tadVersionNode.setNextSibling(fileFormatNode);
		
		// Create the File Format Schema Node
		Node schemaNode = Node.builder()
				.title(SCHEMA).level(2)
				.parent(fileFormatNode)
				.build();
		fileFormatNode.setChild(schemaNode);
		
		// Create the File Format Schema Version Node
		Node versionStringNode = Node.builder()
				.title(VERSION_STRING).data(schema.getVersionString()).level(3)
				.parent(schemaNode)
				.build();
		schemaNode.setChild(versionStringNode);
		
		// Create the File Format Schema Version Number Node
		Node versionNumNode = Node.builder()
				.title(VERSION_NUMBER).data(String.valueOf(schema.getVersionNum())).level(3)
				.prevSibling(versionStringNode)
				.build();
		versionStringNode.setNextSibling(versionNumNode);
		
		// Return the head Node of this header
		return headNode;
	}
	
	/**
	 * Grabs the Schema version string out of the Tad Format Node Header.
	 * 
	 * @param headNode The head Node of the header
	 * @return The version string used for the schema
	 */
	public static String getSchemaVersionString(Node headNode){
		// TadFormat: (headNode)
		//   TadFormat Version Num:<#> (headNode.getChild())
		//   File Format:<Format Name> (headNode.getChild().getNextSibling())
		//     Schema: (headNode.getChild().getNextSibling().getChild())
		//       Version:<What we're looking for> (headNode.getChild().getNextSibling().getChild().getChild())
		return headNode.getChild().getNextSibling().getChild().getChild().getData();
	}
	
	/**
	 * Verifies that the Tad Format Node is correct at the top of a file.
	 * 
	 * @param logger The {@link EasyLogger} to use in logging any issues or successes
	 * @param tadFormatNode The Tad Format Node being checked
	 * @param format The FileFormat to use in checking parameters
	 * @param schema The FileFormatSchema to use in checking parameters
	 * @return Whether the Tad Format Node was the correct format or not
	 */
	public static boolean verifyTadFormatNode(EasyLogger logger, Node tadFormatNode, FileFormat format, FileFormatSchema schema){
		// Check if the Head TadFormat Node matches the correct format
		boolean goodHeadNode = FormatNodeVerification.verifySingleNode(
				NodeVerificationCriteria.builder()
										.logger(logger)
										.node(tadFormatNode)
										.format(getHeadFormatNode(logger))
										.nullParent()
										.nullPrevSibling()
										.build());
		
		// Head TadFormat Node should have a next sibling that is the head Node for the actual contents of the file
		if(tadFormatNode.getNextSibling() == null){
			logger.logDebugFine("* Head TadFormat Node has no next sibling! This file has no content!");
			goodHeadNode = false;
		}
		
		// Grab the Tad Format Version Number Node
		Node tadFormatNumNode = tadFormatNode.getChild();
		
		// Check if the Tad Format Version Number Node matches the correct format
		boolean goodTadFormatNumNode = FormatNodeVerification.verifySingleNode(
				NodeVerificationCriteria.builder()
										.logger(logger)
										.node(tadFormatNumNode)
										.format(getTadVersionNode(logger))
										.nullChild()
										.nullPrevSibling()
										.parent(tadFormatNode)
										.build());
		
		// Grab the file format Node
		Node fileFormatNode = tadFormatNumNode.getNextSibling();
		
		// Check if the File Format Node matches the correct format
		boolean goodFileFormatNode = FormatNodeVerification.verifySingleNode(
				NodeVerificationCriteria.builder()
										.logger(logger)
										.node(fileFormatNode)
										.format(getFileFormatNode(logger, format))
										.nullParent()
										.nullNextSibling()
										.prevSibling(tadFormatNumNode)
										.build());
		
		// Grab the Schema Node
		Node schemaNode = fileFormatNode.getChild();
		
		// Check if the Schema Node matches the correct format
		boolean goodSchemaNode = FormatNodeVerification.verifySingleNode(
				NodeVerificationCriteria.builder()
										.logger(logger)
										.node(schemaNode)
										.format(getSchemaNode(logger))
										.nullPrevSibling()
										.nullNextSibling()
										.parent(fileFormatNode)
										.build());
		
		// Grab the Version String Node
		Node versionStringNode = schemaNode.getChild();
		
		// Check if the Version String Node matches the correct format
		boolean goodVerStrNode = FormatNodeVerification.verifySingleNode(
				NodeVerificationCriteria.builder()
										.logger(logger)
										.node(versionStringNode)
										.format(getVersionStringNode(logger, schema))
										.nullChild()
										.nullPrevSibling()
										.parent(schemaNode)
										.build());
		
		// Grab the Version Number Node
		Node versionNumNode = versionStringNode.getNextSibling();
		
		// Check if the Version Number Node matches the correct format
		boolean goodVerNumNode = FormatNodeVerification.verifySingleNode(
				NodeVerificationCriteria.builder()
										.logger(logger)
										.node(versionNumNode)
										.format(getVersionNumNode(logger, schema))
										.nullParent()
										.nullChild()
										.nullNextSibling()
										.prevSibling(versionStringNode)
										.build());
		
		// If all Nodes were good, we good
		return goodHeadNode && goodTadFormatNumNode && goodFileFormatNode && goodSchemaNode && goodVerStrNode && goodVerNumNode;
	}
}
