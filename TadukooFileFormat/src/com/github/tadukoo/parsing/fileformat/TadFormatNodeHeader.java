package com.github.tadukoo.parsing.fileformat;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class defines the Tad Format Node Header section that should be at the top 
 * of all files.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version 0.1-Alpha-SNAPSHOT
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
	 * @param logger The Logger to use in logging any issues
	 * @return The FormatNode for the head Tad Format Header Node
	 */
	private static FormatNode getHeadFormatNode(Logger logger){
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
	 * @param logger The Logger to use in logging any issues
	 * @return The FormatNode for the Tad Format Version Node
	 */
	private static FormatNode getTadVersionNode(Logger logger){
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
	 * @param logger The Logger to use in logging any issues
	 * @return The FormatNode for the File Format Node
	 */
	private static FormatNode getFileFormatNode(Logger logger, FileFormat format){
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
	 * @param logger The Logger to use in logging any issues
	 * @return The FormatNode for the File Format Schema Node
	 */
	private static FormatNode getSchemaNode(Logger logger){
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
	 * @param logger The Logger to use in logging any issues
	 * @return The FormatNode for the File Format Schema Version Node
	 */
	private static FormatNode getVersionStringNode(Logger logger, FileFormatSchema schema){
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
	 * @param logger The Logger to use in logging any issues
	 * @return The FormatNode for the File Format Schema Version Number Node
	 */
	private static FormatNode getVersionNumNode(Logger logger, FileFormatSchema schema){
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
		Node headNode = new Node(HEAD_NAME, "", 0, null, null, null, null);
		
		// Create the Tad Format Version Node
		Node tadVersionNode = new Node(TAD_VERSION_NUMBER, String.valueOf(FileFormat.TAD_FORMAT_VERSION_NUM), 1, 
				headNode, null, null, null);
		headNode.setChild(tadVersionNode);
		
		// Create the File Format Node
		Node fileFormatNode = new Node(FILE_FORMAT, format.getName(), 1, null, null, tadVersionNode, null);
		tadVersionNode.setNextSibling(fileFormatNode);
		
		// Create the File Format Schema Node
		Node schemaNode = new Node(SCHEMA, "", 2, fileFormatNode, null, null, null);
		fileFormatNode.setChild(schemaNode);
		
		// Create the File Format Schema Version Node
		Node versionStringNode = new Node(VERSION_STRING, schema.getVersionString(), 3, schemaNode, null, null, null);
		schemaNode.setChild(versionStringNode);
		
		// Create the File Format Schema Version Number Node
		Node versionNumNode = new Node(VERSION_NUMBER, String.valueOf(schema.getVersionNum()), 3, 
				null, null, versionStringNode, null);
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
	 * @param logger The Logger to use in logging any issues or successes
	 * @param tadFormatNode The Tad Format Node being checked
	 * @param format The FileFormat to use in checking parameters
	 * @param schema The FileFormatSchema to use in checking parameters
	 * @return Whether the Tad Format Node was the correct format or not
	 */
	public static boolean verifyTadFormatNode(Logger logger, Node tadFormatNode, FileFormat format, FileFormatSchema schema){
		// Check if the Head TadFormat Node matches the correct format
		boolean goodHeadNode = FormatNodeVerification.verifySingleNode(
				FormatNodeVerification.singleNodeVerificationParametersBuilder()
										.logger(logger)
										.node(tadFormatNode)
										.format(getHeadFormatNode(logger))
										.nullParent()
										.nullPrevSibling()
										.build());
		
		// Head TadFormat Node should have a next sibling that is the head Node for the actual contents of the file
		if(tadFormatNode.getNextSibling() == null){
			logger.log(Level.FINE, "* Head TadFormat Node has no next sibling! This file has no content!");
			goodHeadNode = false;
		}
		
		// Grab the Tad Format Version Number Node
		Node tadFormatNumNode = tadFormatNode.getChild();
		
		// Check if the Tad Format Version Number Node matches the correct format
		boolean goodTadFormatNumNode = FormatNodeVerification.verifySingleNode(
				FormatNodeVerification.singleNodeVerificationParametersBuilder()
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
				FormatNodeVerification.singleNodeVerificationParametersBuilder()
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
				FormatNodeVerification.singleNodeVerificationParametersBuilder()
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
				FormatNodeVerification.singleNodeVerificationParametersBuilder()
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
				FormatNodeVerification.singleNodeVerificationParametersBuilder()
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
