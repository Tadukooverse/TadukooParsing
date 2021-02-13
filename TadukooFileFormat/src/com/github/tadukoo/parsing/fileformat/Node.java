package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.StringUtil;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * A Node is a single line of text in a full file under the TadFileFormat. 
 * From it, you can navigate up or down the file through parent/child node relationships 
 * and through sibling relationships.
 * <br>
 * <b>Note</b>: Each Node can only have one parent, one child, and can only reference its 
 * previous and next sibling Nodes.
 * <br><br>
 * Example:<br>
 * &nbsp;&nbsp;Person:<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Name: Logan Ferree<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Job: Programmer<br>
 * &nbsp;&nbsp;Person 2:<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Name: Me<br>
 * &nbsp;&nbsp;&nbsp;&nbsp;Job: Unknown
 * <br><br>
 * In this example, Person and Person 2 are siblings, so 
 * Person's Node.getNextSibling() gives Person 2's Node and 
 * Person 2's Node.getPrevSibling() gives Person's Node.
 * <br>
 * Name is the child of the Person Nodes, so from either Person 
 * Node, using .getChild() will give the following Name Node.
 * <br>
 * To go from Person to Job, you'd have to do Person Node .getChild()
 * .getNextSibling() (as Job is a sibling of Name).
 * 
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 * @since Alpha v.0.1
 */
public class Node{
	
	/**
	 * Node Builder is used to build a {@link Node}. It has the following parameters:
	 * <table>
	 *     <caption>Node Parameters</caption>
	 *     <tr>
	 *         <th>Parameter</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>title</td>
	 *         <td>The name for the piece of data</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>data</td>
	 *         <td>The actual data</td>
	 *         <td>Defaults to null/empty</td>
	 *     </tr>
	 *     <tr>
	 *         <td>level</td>
	 *         <td>The level of the Node (basically how many times you can call {@link #getParent()} from here)</td>
	 *         <td>Defaults to 0</td>
	 *     </tr>
	 *     <tr>
	 *         <td>parent</td>
	 *         <td>The parent {@link Node} to this one</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>child</td>
	 *         <td>The child {@link Node} to this one</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>prevSibling</td>
	 *         <td>The previous sibling {@link Node} to this one</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>nextSibling</td>
	 *         <td>The next sibling {@link Node} to this one</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class NodeBuilder{
		/** The name for the piece of data */
		private String title = null;
		/** The actual data */
		private String data = null;
		/** The level of the Node (basically how many times you can call {@link #getParent()} from here) */
		private int level = 0;
		/** The parent {@link Node} to this one */
		private Node parent = null;
		/** The child {@link Node} to this one */
		private Node child = null;
		/** The previous sibling {@link Node} to this one */
		private Node prevSibling = null;
		/** The next sibling {@link Node} to this one */
		private Node nextSibling = null;
		
		// Can't create NodeBuilder outside of Node
		private NodeBuilder(){ }
		
		/**
		 * @param title The name for the piece of data
		 * @return this, to continue building
		 */
		public NodeBuilder title(String title){
			this.title = title;
			return this;
		}
		
		/**
		 * @param data The actual data
		 * @return this, to continue building
		 */
		public NodeBuilder data(String data){
			this.data = data;
			return this;
		}
		
		/**
		 * @param level The level of the Node (basically how many times you can call {@link #getParent()} from here)
		 * @return this, to continue building
		 */
		public NodeBuilder level(int level){
			this.level = level;
			return this;
		}
		
		/**
		 * @param parent The parent {@link Node} to this one
		 * @return this, to continue building
		 */
		public NodeBuilder parent(Node parent){
			this.parent = parent;
			return this;
		}
		
		/**
		 * @param child The child {@link Node} to this one
		 * @return this, to continue building
		 */
		public NodeBuilder child(Node child){
			this.child = child;
			return this;
		}
		
		/**
		 * @param prevSibling The previous sibling {@link Node} to this one
		 * @return this, to continue building
		 */
		public NodeBuilder prevSibling(Node prevSibling){
			this.prevSibling = prevSibling;
			return this;
		}
		
		/**
		 * @param nextSibling The next sibling {@link Node} to this one
		 * @return this, to continue building
		 */
		public NodeBuilder nextSibling(Node nextSibling){
			this.nextSibling = nextSibling;
			return this;
		}
		
		/**
		 * Checks for errors in the current parameters
		 */
		private void checkForErrors(){
			if(StringUtil.isBlank(title)){
				throw new IllegalArgumentException("title cannot be blank!");
			}
		}
		
		/**
		 * Builds a new {@link Node} using the specified parameters
		 *
		 * @return A newly built {@link Node}
		 */
		public Node build(){
			// Check for any problems
			checkForErrors();
			
			// Change data to empty string if it's null
			if(data == null){
				data = "";
			}
			
			return new Node(title, data, level, parent, child, prevSibling, nextSibling);
		}
	}
	
	/** The name for what this piece of data is */
	private final String title;
	/** The data for this Node */
	private final String data;
	/** The level of Node this is (basically how many times you can call .getParent in a row from here) */
	private final int level;
	/** The parent Node to this one */
	private Node parent;
	/** The child Node to this one */
	private Node child;
	/** The previous sibling Node to this one */
	private Node prevSibling;
	/** The next sibling Node to this one */
	private Node nextSibling;
	
	/**
	 * Create a Node using all of the information to be stored in it.
	 * 
	 * @param title The name for what this piece of data is
	 * @param data The data for this Node
	 * @param level The level of node this is (basically how many times you can call .getParent() in a row from here)
	 * @param parent The parent Node to this one
	 * @param child The child Node to this one
	 * @param prevSibling The previous sibling Node to this one
	 * @param nextSibling The next sibling Node to this one
	 */
	private Node(String title, String data, int level, Node parent, Node child, Node prevSibling, Node nextSibling){
		this.title = title;
		this.data = data;
		this.level = level;
		setParent(parent);
		setChild(child);
		setPrevSibling(prevSibling);
		setNextSibling(nextSibling);
	}
	
	/**
	 * @return A new {@link NodeBuilder} to use to build a new {@link Node}
	 */
	public static NodeBuilder builder(){
		return new NodeBuilder();
	}
	
	/**
	 * Loads the given file and creates a Node (and any children and siblings down the line) 
	 * from the contents of the file.
	 * 
	 * @param filepath The path to the file
	 * @return The head Node created from the file
	 */
	public static Node loadFromFile(String filepath){
		// Read the file line-by-line, creating a List of lines
		List<String> lines = new ArrayList<>();
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filepath)));
			lines.add(reader.readLine());
			String line = reader.readLine();
			while(line != null){
				lines.add(line);
				line = reader.readLine();
			}
			reader.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		// Use loadFromList to do the work of converting the lines to Nodes
		return loadFromList(lines);
	}
	
	/**
	 * Loads Nodes (including children + siblings) from the given text.
	 * The text should be formatted so lines are separated using only the newline character (\n).
	 *
	 * @param text The text to convert into Nodes
	 */
	public static Node loadFromString(String text){
		List<String> lines = StringUtil.parseListFromStringWithSeparator(text, "\n", false);
		return loadFromList(lines);
	}
	
	/**
	 * Loads Nodes (including children + siblings) from the given List of lines 
	 * and returns the head Node.
	 * 
	 * @param lines The lines of text to convert into Nodes
	 * @return The head Node resulting from the given text
	 */
	public static Node loadFromList(List<String> lines){
		// Keep a List of Nodes in order to use for connecting siblings and children properly 
		List<Node> nodes = new ArrayList<>();
		// Keep track of Node number to use in navigating the List
		int nodeNum = 0;
		
		/*
		 * Create the child and sibling Nodes down the line
		 */
		for(int i = 0; i < lines.size(); i++){
			// Grab the next line to be read
			String line = lines.get(i);
			// If line doesn't contain a colon, we got issues
			if(!line.contains(":")){
				throw new IllegalArgumentException("Line '" + line + "' doesn't have colon! "
						+ "Not proper Tadukoo File Format!");
			}
			// Grab the title from the line as before the 1st colon
			String title = line.split(":")[0];
			StringBuilder data;
			if(line.split(":").length > 1){
				// If the line contains at least 1 colon, set the data
				data = new StringBuilder(line.split(":")[1]);
				
				// If line contains extra colons, add on to the data
				if(line.split(":").length > 2){
					for(int j = 2; j < line.split(":").length; j++){
						data.append(":").append(line.split(":")[j]);
					}
				}
				
				// If the data starts with an opening parenthesis, it's multi-line data
				if(data.toString().startsWith("(")){
					// Remove the parenthesis from the start of the data
					data = new StringBuilder(data.substring(1));
					// Keep adding new lines to the data until we get the closing parenthesis
					while(!data.toString().endsWith(")")){
						// Increment what line we're on
						i++;
						// If we've reached the end of the lines, we got problems
						if(i >= lines.size()){
							throw new IllegalArgumentException(
									"Reached end of lines without hitting closing parenthesis!");
						}
						// Add the next line to the data
						data.append("\n").append(lines.get(i));
					}
					// Remove the parenthesis from the end of the data
					data = new StringBuilder(data.substring(0, data.length() - 1));
				}else if(data.toString().startsWith("\\(")){
					// If data starts with an escaped parenthesis, remove the escape character
					data = new StringBuilder(data.substring(1));
				}
			}else{
				// If empty data section, set it as empty string
				data = new StringBuilder();
			}
			
			// Get the level of this Node - default 0
			int level = 0;
			while(title.startsWith("  ")){
				// If the Node has spaces at the start, every 2 spaces = 1 level
				level++;
				title = title.substring(2);
			}
			
			// Create the new Node and add it to the list of Nodes
			Node curNode = new Node(title, data.toString(), level,
					null, null, null, null);
			nodes.add(curNode);
			
			// If it's the head node, make sure we're at level 0
			if(nodeNum == 0){
				if(level != 0){
					throw new IllegalArgumentException("Head Node should be level 0, but was level " + level + "!");
				}
			}else{
				// If it's not the head Node - determine where to attach the new Node
				Node prevNode = nodes.get(nodeNum - 1);
				// If Node has greater level than previous Node, it's a child of the previous Node
				if(level > prevNode.getLevel()){
					// If level is more than 1 down, we got issues
					if(level - prevNode.getLevel() > 1){
						throw new IllegalArgumentException("Skipped a level from " + prevNode.getLevel() +
								" to " + level);
					}
					prevNode.setChild(curNode);
					curNode.setParent(prevNode);
				}else if(level == prevNode.getLevel()){
					// If Node has the same level as previous Node, it's the next sibling of previous
					prevNode.setNextSibling(curNode);
					curNode.setPrevSibling(prevNode);
				}else{
					// If Node has lesser level than previous Node, find where to add it
					for(int j = nodeNum - 2; j >= 0; j--){
						prevNode = nodes.get(j);
						// Iterate through previous Nodes
						if(level == prevNode.getLevel()){
							// If current level is equal to a previous Node, add as sibling
							prevNode.setNextSibling(curNode);
							curNode.setPrevSibling(prevNode);
							break;
						}
					}
				}
			}
			
			// Increment the current Node number
			nodeNum++;
		}
		
		// Return the head Node
		return nodes.get(0);
	}
	
	/**
	 * @return The name for what this piece of data is
	 */
	public String getTitle(){
		return title;
	}
	
	/**
	 * @return The data for this Node
	 */
	public String getData(){
		return data;
	}
	
	/**
	 * @return The level of Node this is (basically how many times 
	 * you can call .getParent in a row from here)
	 */
	public int getLevel(){
		return level;
	}
	
	/**
	 * @return The parent Node to this one
	 */
	public Node getParent(){
		return parent;
	}
	
	/**
	 * Sets the parent Node to this one. 
	 * Checks if the level of the parent is less than the level of this Node, 
	 * and if not, throws an {@link IllegalArgumentException}.
	 * 
	 * @param parent The Node to set as the parent of this one
	 */
	public void setParent(Node parent){
		// If parent's level isn't less than this Node's level, we got an issue
		if(parent != null && parent.getLevel() >= level){
			throw new IllegalArgumentException("Parent's level must be less than child's level!");
		}
		
		this.parent = parent;
	}
	
	/**
	 * @return The child Node to this one
	 */
	public Node getChild(){
		return child;
	}
	
	/**
	 * Sets the child Node to this one. 
	 * Checks if the level of the child is greater than the level of this Node, 
	 * and if not, throws an {@link IllegalArgumentException}.
	 * 
	 * @param child The Node to set as the child of this one
	 */
	public void setChild(Node child){
		// If child's level isn't greater than this Node's level, we got an issue
		if(child != null && child.getLevel() <= level){
			throw new IllegalArgumentException("Child's level must be greater than parent's level!");
		}
		
		this.child = child;
	}
	
	/**
	 * @return The previous sibling Node to this one
	 */
	public Node getPrevSibling(){
		return prevSibling;
	}
	
	/**
	 * Sets the previous sibling Node to this one. 
	 * Checks if the level of the sibling is equal to the level of this Node, 
	 * and if not, throws an {@link IllegalArgumentException}.
	 * 
	 * @param sibling The Node to set as the previous sibling of this one
	 */
	public void setPrevSibling(Node sibling){
		// If sibling's level isn't equal to this Node's level, we got an issue
		if(sibling != null && sibling.getLevel() != level){
			throw new IllegalArgumentException("Sibling's level must be the same level!");
		}
		
		this.prevSibling = sibling;
	}
	
	/**
	 * @return The next sibling Node to this one
	 */
	public Node getNextSibling(){
		return nextSibling;
	}
	
	/**
	 * Sets the next sibling Node to this one. 
	 * Checks if the level of the sibling is equal to the level of this Node, 
	 * and if not, throws an {@link IllegalArgumentException}.
	 * 
	 * @param sibling The Node to set as the next sibling of this one
	 */
	public void setNextSibling(Node sibling){
		// If sibling's level isn't equal to this Node's level, we got an issue
		if(sibling != null && sibling.getLevel() != level){
			throw new IllegalArgumentException("Sibling's level must be the same level!");
		}
		
		this.nextSibling = sibling;
	}
	
	/**
	 * Converts this Node (and only this Node) to a String. To convert this Node 
	 * and all the children and siblings down the line to a textual version (as in 
	 * the actual files), use {@link #fullToString()}.
	 * 
	 * @return The String representation of solely this Node
	 */
	@Override
	public String toString(){
		// Add any leading space (based on level)
		return "  ".repeat(level) +
				// Add the title and (optional) data
				title + ":" + data;
	}
	
	/**
	 * Converts this Node and all the children and siblings down the line 
	 * to a textual version (as in the actual files).
	 * 
	 * @return The String representation of this Node and all its children and siblings
	 */
	public String fullToString(){
		String text = "";
		
		// Add this Node's text
		text += toString();
		
		// Add the full child's string
		if(child != null){
			text += "\n" + child.fullToString();
		}
		
		// Add the full sibling's string
		if(nextSibling != null){
			text += "\n" + nextSibling.fullToString();
		}
		
		return text;
	}
	
	/**
	 * Grabs all the titles of all the Nodes in the tree.
	 * 
	 * @return A List of all the titles in the Node tree
	 */
	public List<String> getAllTitles(){
		List<String> titles = new ArrayList<>();
		// Add this Node's title
		titles.add(title);
		
		// Add all the titles from the child
		if(child != null){
			titles.addAll(child.getAllTitles());
		}
		
		// Add all the titles from the sibling
		if(nextSibling != null){
			titles.addAll(nextSibling.getAllTitles());
		}
		
		return titles;
	}
	
	/**
	 * Grabs all the data of all the Nodes in the tree.
	 * 
	 * @return A List of all the data in the Node tree
	 */
	public List<String> getAllDatas(){
		List<String> datas = new ArrayList<>();
		// Add this Node's data
		datas.add(data);
		
		// Add all the data from the child
		if(child != null){
			datas.addAll(child.getAllDatas());
		}
		
		// Add all the data from the sibling
		if(nextSibling != null){
			datas.addAll(nextSibling.getAllDatas());
		}
		
		return datas;
	}
}
