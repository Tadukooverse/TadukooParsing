package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.parsing.TadFormatRegexConverter;
import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.logger.EasyLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents all the information about how a {@link Node} should be formatted for a {@link FileFormat}.
 * 
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.1
 */
public class FormatNode{
	
	/** The text used to signify a null (e.g. as an allowed Node for parent/child/sibling) */
	public static final String NULL_NODE = "<null>";
	
	/**
	 * Builder for constructing a {@link FormatNode} object.
	 * <br><br>
	 * <b>MUST</b> specify the following:
	 * <ul>
	 * <li>logger</li>
	 * <li>text OR <ul>
	 * <li>name</li>
	 * <li>titleRegex OR titleFormat</li>
	 * <li>dataRegex OR dataFormat</li>
	 * <li>level</li>
	 * </ul>
	 * </li>
	 * </ul>
	 * Defaults are:
	 * <ul>
	 * <li>parentNames - List with {@link #NULL_NODE} - meaning no parent nodes allowed</li>
	 * <li>childNames - List with {@link #NULL_NODE} - meaning no child nodes allowed</li>
	 * <li>prevSiblingNames - List with {@link #NULL_NODE} - meaning no previous siblings allowed</li>
	 * <li>nextSiblingNames - List with {@link #NULL_NODE} - meaning no next siblings allowed</li>
	 * </ul>
	 * <b>Notes:</b>
	 * <ul>
	 * <li>Specifying text means you can't specify anything else 
	 * other than logger (which is required). You will get an IllegalArgumentException 
	 * if you try.
	 * <br>
	 * Specifying text will fill in the name, titleRegex, dataRegex, level, parentNames, 
	 * childNames, prevSiblingNames, and nextSiblingNames based on the parsed text.</li>
	 * <li>Specifying titleFormat will convert it into a titleRegex</li>
	 * <li>Specifying dataFormat will convert it into a dataRegex</li>
	 * </ul>
	 * 
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 * @since Alpha v.0.1
	 */
	public static class FormatNodeBuilder{
		/** The {@link EasyLogger} to use for logging initialization messages */
		private EasyLogger logger = null;
		/** Text to use in parsing into a FormatNode */
		private String text = null;
		/** The name of the Node for identification purposes */
		private String name = null;
		/** The regex used to ensure the title is of the correct format */
		private String titleRegex = null;
		/** The TFormatting to convert into a regex for the title */
		private String titleFormat = null;
		/** The regex used to ensure the data is of the correct format */
		private String dataRegex = null;
		/** The TFormatting to convert into a regex for the data */
		private String dataFormat = null;
		/** The required level of the Node */
		private int level = -1;
		/** Names of allowed parent Nodes */
		private List<String> parentNames = null;
		/** Names of allowed child Nodes */
		private List<String> childNames = null;
		/** Names of allowed previous sibling Nodes */
		private List<String> prevSiblingNames = null;
		/** Names of allowed next sibling Nodes */
		private List<String> nextSiblingNames = null;
		
		/** Not allowed to make FormatNodeBuilder outside of FormatNode */
		private FormatNodeBuilder(){ }
		
		/**
		 * Set the {@link EasyLogger} to use for logging initialization messages.
		 * 
		 * @param logger The {@link EasyLogger} to use for logging messages
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder logger(EasyLogger logger){
			this.logger = logger;
			return this;
		}
		
		/**
		 * If you specify text, you should only specify the Logger in addition to this. 
		 * Specifying anything else will cause an IllegalArgumentException, as the text 
		 * is supposed to be the full formatting as a Tadukoo File Format format.
		 * 
		 * @param text The text to parse as a Tadukoo File Format format
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder text(String text){
			this.text = text;
			return this;
		}
		
		/**
		 * Sets the name to use for the FormatNode.
		 * 
		 * @param name The name of the FormatNode
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder name(String name){
			this.name = name;
			return this;
		}
		
		/**
		 * Sets the regex to use for the title of the Node.
		 * 
		 * @param titleRegex The regex to use for the title of the Node
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder titleRegex(String titleRegex){
			this.titleRegex = titleRegex;
			return this;
		}
		
		/**
		 * Sets the TFormatting to use for the title of the Node. This 
		 * will be converted to a regex when building.
		 * 
		 * @param titleFormat The TFormatting to use for the title of the Node
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder titleFormat(String titleFormat){
			this.titleFormat = titleFormat;
			return this;
		}
		
		/**
		 * Sets the regex to use for the data of the Node.
		 * 
		 * @param dataRegex The regex to use for the data of the Node
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder dataRegex(String dataRegex){
			this.dataRegex = dataRegex;
			return this;
		}
		
		/**
		 * Sets the TFormatting to use for the data of the Node. This 
		 * will be converted to a regex when building.
		 * 
		 * @param dataFormat The TFormatting to use for the data of the Node
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder dataFormat(String dataFormat){
			this.dataFormat = dataFormat;
			return this;
		}
		
		/**
		 * Sets the level of the Node.
		 * 
		 * @param level The required level of the Node
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder level(int level){
			this.level = level;
			return this;
		}
		
		/**
		 * Initializes the parentNames list to an empty ArrayList if it's null
		 */
		private void parentNamesCheck(){
			if(parentNames == null){
				parentNames = new ArrayList<>();
			}
		}
		
		/**
		 * Sets the allowed names for parent Nodes.
		 * 
		 * @param parentNames Names allowed for parent Nodes
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder parentNames(List<String> parentNames){
			this.parentNames = parentNames;
			return this;
		}
		
		/**
		 * Adds the given string to the list of allowed names for parent Nodes.
		 * 
		 * @param parentName The name to add to the list of allowed parent Nodes
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder parentName(String parentName){
			// Initialize parentNames if it's null
			parentNamesCheck();
			// Add the given name to the list
			parentNames.add(parentName);
			return this;
		}
		
		/**
		 * Adds null to the allowed names for parent Nodes.
		 * 
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder nullParentName(){
			// Initialize parentNames if it's null
			parentNamesCheck();
			// Add null to the list
			parentNames.add(NULL_NODE);
			return this;
		}
		
		/**
		 * Initializes the childNames list to an empty ArrayList if it's null
		 */
		private void childNamesCheck(){
			if(childNames == null){
				childNames = new ArrayList<>();
			}
		}
		
		/**
		 * Sets the allowed names for child Nodes.
		 * 
		 * @param childNames Names allowed for child Nodes
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder childNames(List<String> childNames){
			this.childNames = childNames;
			return this;
		}
		
		/**
		 * Adds the given string to the list of allowed names for child Nodes.
		 * 
		 * @param childName The name to add to the list of allowed child Nodes
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder childName(String childName){
			// Initialize childNames if it's null
			childNamesCheck();
			// Add the given name to the list
			childNames.add(childName);
			return this;
		}
		
		/**
		 * Adds null to the allowed names for child Nodes.
		 * 
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder nullChildName(){
			// Initialize childNames if it's null
			childNamesCheck();
			// Add null to the list
			childNames.add(NULL_NODE);
			return this;
		}
		
		/**
		 * Initializes the prevSiblingNames list to an empty ArrayList if it's null
		 */
		private void prevSiblingNamesCheck(){
			if(prevSiblingNames == null){
				prevSiblingNames = new ArrayList<>();
			}
		}
		
		/**
		 * Sets the allowed names for previous sibling Nodes.
		 * 
		 * @param prevSiblingNames Names allowed for previous sibling Nodes
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder prevSiblingNames(List<String> prevSiblingNames){
			this.prevSiblingNames = prevSiblingNames;
			return this;
		}
		
		/**
		 * Adds the given string to the list of allowed names for previous sibling Nodes.
		 * 
		 * @param prevSiblingName The name to add to the list of allowed previous sibling Nodes
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder prevSiblingName(String prevSiblingName){
			// Initialize prevSiblingNames if it's null
			prevSiblingNamesCheck();
			// Add the given name to the list
			prevSiblingNames.add(prevSiblingName);
			return this;
		}
		
		/**
		 * Adds null to the allowed names for previous sibling Nodes.
		 * 
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder nullPrevSiblingName(){
			// Initialize prevSiblingNames if it's null
			prevSiblingNamesCheck();
			// Add null to the list
			prevSiblingNames.add(NULL_NODE);
			return this;
		}
		
		/**
		 * Initializes the nextSiblingNames list to an empty ArrayList if it's null
		 */
		private void nextSiblingNamesCheck(){
			if(nextSiblingNames == null){
				nextSiblingNames = new ArrayList<>();
			}
		}
		
		/**
		 * Sets the allowed names for next sibling Nodes.
		 * 
		 * @param nextSiblingNames Names allowed for next sibling Nodes
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder nextSiblingNames(List<String> nextSiblingNames){
			this.nextSiblingNames = nextSiblingNames;
			return this;
		}
		
		/**
		 * Adds the given string to the list of allowed names for next sibling Nodes.
		 * 
		 * @param nextSiblingName The name to add to the list of allowed next sibling Nodes
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder nextSiblingName(String nextSiblingName){
			// Initialize nextSiblingNames if it's null
			nextSiblingNamesCheck();
			// Add the given name to the list
			nextSiblingNames.add(nextSiblingName);
			return this;
		}
		
		/**
		 * Adds null to the allowed names for next sibling Nodes.
		 * 
		 * @return The FormatNodeBuilder, to continue in building
		 */
		public FormatNodeBuilder nullNextSiblingName(){
			// Initialize nextSiblingNames if it's null
			nextSiblingNamesCheck();
			// Add null to the list
			nextSiblingNames.add(NULL_NODE);
			return this;
		}
		
		/**
		 * Checks the builder for any errors in the data. 
		 * Throws an IllegalArgumentException with any issues.
		 */
		private void checkForErrors(){
			// Keep a list of errors to send in an IllegalArgumentException
			List<String> errors = new ArrayList<>();
			
			// Must specify a Logger
			if(logger == null){
				errors.add("Must specify a Logger!");
			}
			
			if(text != null){
				// If text is present, most parameters will be populated from its contents
				if(name != null){
					errors.add("Can't specify text and name!");
				}
				if(titleFormat != null){
					errors.add("Can't specify text and title format!");
				}
				if(titleRegex != null){
					errors.add("Can't specify text and title regex!");
				}
				if(dataFormat != null){
					errors.add("Can't specify text and data format!");
				}
				if(dataRegex != null){
					errors.add("Can't specify text and data regex!");
				}
				if(level != -1){
					errors.add("Can't specify text and level!");
				}
				if(parentNames != null){
					errors.add("Can't specify text and parent names!");
				}
				if(childNames != null){
					errors.add("Can't specify text and child names!");
				}
				if(prevSiblingNames != null){
					errors.add("Can't specify text and previous sibling names!");
				}
				if(nextSiblingNames != null){
					errors.add("Can't specify text and next sibling names!");
				}
			}else{
				// If text is not present, some parameters are required
				
				// Name is required
				if(name == null){
					errors.add("Name can't be null!");
				}
				
				// TitleFormat or titleRegex is required
				if(titleFormat == null && titleRegex == null){
					errors.add("Must specify either titleFormat or titleRegex!");
				}else if(titleFormat != null && titleRegex != null){
					// TitleFormat converts to titleRegex, so can't have both
					errors.add("Can't specify both title format and title regex!");
				}
				
				// DataFormat or dataRegex is required
				if(dataFormat == null && dataRegex == null){
					errors.add("Must specify either dataFormat or dataRegex!");
				}else if(dataFormat != null && dataRegex != null){
					// DataFormat converts to dataRegex, so can't have both
					errors.add("Can't specify both data format and data regex!");
				}
				
				// Level is required
				if(level == -1){
					errors.add("Must specify level!");
				}
			}
			
			// If there were issues, throw an IllegalArgumentException with them
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Failed to create FormatNode: " +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Converts the text string into Nodes to be parsed into a FormatNode
		 */
		private void convertText(){
			// Convert the text into Nodes and grab them
			Node headNode = Node.loadFromString(text);
			Node titleFormatNode = headNode.getChild();
			Node dataFormatNode = titleFormatNode.getNextSibling();
			Node levelNode = dataFormatNode.getNextSibling();
			Node parentsNode = levelNode.getNextSibling();
			Node childrenNode = parentsNode.getNextSibling();
			Node prevSiblingNode = childrenNode.getNextSibling();
			Node nextSiblingNode = prevSiblingNode.getNextSibling();
			
			// Grab the name of the Node off the head Node
			name = headNode.getData();
			
			// Grab the title TFormatting or regex off the title format Node
			switch(titleFormatNode.getTitle()){
				case "Format" -> titleFormat = titleFormatNode.getData();
				case "Regex" -> titleRegex = titleFormatNode.getData();
				default -> throw new IllegalArgumentException("Unknown title format type: " +
						titleFormatNode.getTitle());
			}
			
			// Grab the data TFormatting or regex off the data format Node
			switch(dataFormatNode.getTitle()){
				case "Format" -> dataFormat = dataFormatNode.getData();
				case "Regex" -> dataRegex = dataFormatNode.getData();
				default -> throw new IllegalArgumentException("Unknown data format type: " +
						dataFormatNode.getTitle());
			}
			
			// Grab the level of the Node off the level Node
			level = Integer.parseInt(levelNode.getData());
			
			// Grab the allowed parent Node names off the parents Node
			parentNames = StringUtil.parseCommaSeparatedListFromString(parentsNode.getData());
			// Grab the allowed child Node names off the children Node
			childNames = StringUtil.parseCommaSeparatedListFromString(childrenNode.getData());
			// Grab the allowed previous sibling Node names off the previous sibling Node
			prevSiblingNames = StringUtil.parseCommaSeparatedListFromString(prevSiblingNode.getData());
			// Grab the allowed next sibling Node names off the next sibling Node
			nextSiblingNames = StringUtil.parseCommaSeparatedListFromString(nextSiblingNode.getData());
			
			// Set text to null so we don't hit errors of specifying text and other info
			text = null;
		}
		
		/**
		 * Creates a List of strings that only contains the {@link #NULL_NODE} string,
		 * to allow a null parent/child/sibling reference.
		 * 
		 * @return A List with the NULL_NODE string in it
		 */
		private List<String> makeNullAllowed(){
			return ListUtil.createList(NULL_NODE);
		}
		
		/**
		 * Builds a new {@link FormatNode} with the specified parameters after checking for any errors.
		 *
		 * @return The newly built {@link FormatNode}
		 */
		public FormatNode build(){
			// Check for any errors in the data
			checkForErrors();
			
			// If there's text to convert, convert it and check for errors again
			if(text != null){
				convertText();
				checkForErrors();
			}
			
			// If we have title TFormatting, convert it to a regex
			if(titleFormat != null){
				titleRegex = TadFormatRegexConverter.convertTadFormatToRegex(logger, titleFormat);
			}
			
			// If we have data TFormatting, convert it to a regex
			if(dataFormat != null){
				dataRegex = TadFormatRegexConverter.convertTadFormatToRegex(logger, dataFormat);
			}
			
			// If parentNames is null, change it to allow a null parent Node
			if(parentNames == null){
				parentNames = makeNullAllowed();
			}
			// If childNames is null, change it to allow a null child Node
			if(childNames == null){
				childNames = makeNullAllowed();
			}
			// If prevSiblingNames is null, change it to allow a null previous sibling Node
			if(prevSiblingNames == null){
				prevSiblingNames = makeNullAllowed();
			}
			// If nextSiblingNames is null, change it to allow a null next sibling Node
			if(nextSiblingNames == null){
				nextSiblingNames = makeNullAllowed();
			}
			
			// Construct a new FormatNode
			return new FormatNode(name, titleRegex, dataRegex, level, 
									parentNames, childNames, prevSiblingNames, nextSiblingNames);
		}
	}
	
	/** The name of the Node - used to distinguish in parent/child/sibling requirements */
	private final String name;
	/** The regex used to ensure the title is of the correct format */
	private final String titleRegex;
	/** The regex used to ensure the data is of the correct format */
	private final String dataRegex;
	/** The required level of the Node */
	private final int level;
	/** Names of allowed parents of the Node */
	private final List<String> parentNames;
	/** Names of allowed children of the Node */
	private final List<String> childNames;
	/** Names of allowed previous siblings of the Node */
	private final List<String> prevSiblingNames;
	/** Names of allowed next siblings of the Node */
	private final List<String> nextSiblingNames;
	
	/**
	 * Constructs a FormatNode with the given data.
	 * 
	 * @param name The name of the Node
	 * @param titleRegex The regex to ensure the title is of the correct format
	 * @param dataRegex The regex to ensure the data is of the correct format
	 * @param level The required level of the Node
	 * @param parentNames The names of allowed parent Nodes to this one
	 * @param childNames The names of allowed child Nodes to this one
	 * @param prevSiblingNames The names of allowed previous sibling Nodes to this one
	 * @param nextSiblingNames The names of allowed next sibling Nodes to this one
	 */
	private FormatNode(String name, String titleRegex, String dataRegex, int level,
						List<String> parentNames, List<String> childNames, 
						List<String> prevSiblingNames, List<String> nextSiblingNames){
		this.name = name;
		this.titleRegex = titleRegex;
		this.dataRegex = dataRegex;
		this.level = level;
		this.parentNames = parentNames;
		this.childNames = childNames;
		this.prevSiblingNames = prevSiblingNames;
		this.nextSiblingNames = nextSiblingNames;
	}
	
	/**
	 * Constructs a {@link FormatNodeBuilder} to be used to construct a FormatNode.
	 * 
	 * @return A new FormatNodeBuilder
	 */
	public static FormatNodeBuilder builder(){
		return new FormatNodeBuilder();
	}
	
	/**
	 * @return The name for the Node
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return The regex to use in ensuring the title is formatted correctly
	 */
	public String getTitleRegex(){
		return titleRegex;
	}
	
	/**
	 * @return The regex to use in ensuring the data is formatted correctly
	 */
	public String getDataRegex(){
		return dataRegex;
	}
	
	/**
	 * @return The title regex and data regex combined, separated by a colon
	 */
	public String getNodeRegex(){
		return titleRegex + ":" + dataRegex;
	}
	
	/**
	 * @return The required level of the Node
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * @return The allowed names for parent Nodes of this one
	 */
	public List<String> getParentNames(){
		return parentNames;
	}
	
	/**
	 * @return The allowed names for child Nodes of this one
	 */
	public List<String> getChildNames(){
		return childNames;
	}
	
	/**
	 * @return The allowed names for previous sibling Nodes of this one
	 */
	public List<String> getPrevSiblingNames(){
		return prevSiblingNames;
	}
	
	/**
	 * @return The allowed names for next sibling Nodes of this one
	 */
	public List<String> getNextSiblingNames(){
		return nextSiblingNames;
	}
	
	/**
	 * Convert this FormatNode into Nodes to be converted into text to be 
	 * stored and later loaded if needed.
	 * 
	 * @return The String representation of this FormatNode
	 */
	@Override
	public String toString(){
		/*
		 * Create the Nodes for this FormatNode to be converted into text
		 */
		
		// The head Node = "Format" + name
		Node headNode = Node.builder()
				.title("Format").data(name)
				.build();
		// The title format Node = "Regex"/"Format" + titleRegex
		Node titleFormatNode = Node.builder()
				.title("Regex").data(titleRegex).level(1)
				.parent(headNode)
				.build();
		headNode.setChild(titleFormatNode);
		// The data format Node = "Regex"/"Format" + dataRegex
		Node dataFormatNode = Node.builder()
				.title("Regex").data(dataRegex).level(1)
				.prevSibling(titleFormatNode)
				.build();
		titleFormatNode.setNextSibling(dataFormatNode);
		// The level Node = "Level" + level
		Node levelNode = Node.builder()
				.title("Level").data(String.valueOf(level)).level(1)
				.prevSibling(dataFormatNode)
				.build();
		dataFormatNode.setNextSibling(levelNode);
		// The parents Node = "Parents" + parentNames
		Node parentsNode = Node.builder()
				.title("Parents").data(StringUtil.buildCommaSeparatedString(parentNames)).level(1)
				.prevSibling(levelNode)
				.build();
		levelNode.setNextSibling(parentsNode);
		// The children Node = "Children" + childNames
		Node childrenNode = Node.builder()
				.title("Children").data(StringUtil.buildCommaSeparatedString(childNames)).level(1)
				.prevSibling(parentsNode)
				.build();
		parentsNode.setNextSibling(childrenNode);
		// The previous sibling Node = "PrevSiblings" + prevSiblingNames
		Node prevSiblingNode = Node.builder()
				.title("PrevSiblings").data(StringUtil.buildCommaSeparatedString(prevSiblingNames)).level(1)
				.prevSibling(childrenNode)
				.build();
		childrenNode.setNextSibling(prevSiblingNode);
		// The next sibling Node = "NextSiblings" + nextSiblingNames
		Node nextSiblingNode = Node.builder()
				.title("NextSiblings").data(StringUtil.buildCommaSeparatedString(nextSiblingNames)).level(1)
				.prevSibling(prevSiblingNode)
				.build();
		prevSiblingNode.setNextSibling(nextSiblingNode);
		
		// Convert these Nodes into a string to be returned
		return headNode.fullToString();
	}
}
