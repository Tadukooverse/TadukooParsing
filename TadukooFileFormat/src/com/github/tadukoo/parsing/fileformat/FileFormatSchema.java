package com.github.tadukoo.parsing.fileformat;

import java.util.List;

/**
 * Represents the definition of a {@link FileFormat}. The reason this 
 * is a separate class is to allow for a FileFormat to update and maintain 
 * its old versions to allow for updating from one schema to another.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version 0.1-Alpha-SNAPSHOT
 */
public class FileFormatSchema{
	/** The version string of this schema */
	private final String versionString;
	/** The version number of this schema */
	private final int versionNum;
	/** The file extension on files using this schema */
	private final String fileExtension;
	/** The {@link FormatNode}s that define this schema */
	private final List<FormatNode> formatNodes;
	
	/**
	 * Constructs a FileFormatSchema with the given information.
	 * 
	 * @param versionString The version string of this schema
	 * @param versionNum The version number of this schema
	 * @param fileExtension The file extension on files using this schema
	 * @param formatNodes The {@link FormatNode}s that define this schema
	 */
	public FileFormatSchema(String versionString, int versionNum, String fileExtension, List<FormatNode> formatNodes){
		this.versionString = versionString;
		this.versionNum = versionNum;
		this.fileExtension = fileExtension;
		this.formatNodes = formatNodes;
	}
	
	/**
	 * @return The version string of this schema
	 */
	public String getVersionString(){
		return versionString;
	}
	
	/**
	 * @return The version number of this schema
	 */
	public int getVersionNum(){
		return versionNum;
	}
	
	/**
	 * @return The file extension on files using this schema
	 */
	public String getFileExtension(){
		return fileExtension;
	}
	
	/**
	 * @return The List of {@link FormatNode}s that define this schema
	 */
	public List<FormatNode> getFormatNodes(){
		return formatNodes;
	}
}
