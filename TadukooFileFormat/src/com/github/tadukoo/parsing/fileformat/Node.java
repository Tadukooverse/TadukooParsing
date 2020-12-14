package com.github.tadukoo.parsing.fileformat;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
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
 * @version 0.1-Alpha-SNAPSHOT
 */
public class Node{
	/** The name for what this piece of data is */
	private String title;
	/** The data for this Node */
	private String data;
	/** The level of Node this is (basically how many times you can call .getParent in a row from here) */
	private int level;
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
	public Node(String title, String data, int level, Node parent, Node child, Node prevSibling, Node nextSibling){
		this.title = title;
		this.data = data;
		this.level = level;
		setParent(parent);
		setChild(child);
		setPrevSibling(prevSibling);
		setNextSibling(nextSibling);
	}
	
	/**
	 * Creates a Node (and any children and siblings down the line) from the given text. 
	 * The text should be formatted so lines are separated using only the newline character (\n).
	 * 
	 * @param text The text to convert into Nodes
	 */
	public Node(String text){
		List<String> lines = Arrays.asList(text.split("\n"));
		loadFromList(lines);
	}
	
	/**
	 * Creates a Node (and any children and siblings down the line) from the given text as a 
	 * List of lines.
	 * 
	 * @param lines The text (as a List of lines) to convert into Nodes
	 */
	public Node(ArrayList<String> lines){
		loadFromList(lines);
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
		// Grab the 1st line to use
		String line = lines.get(0);
		
		/*
		 * Create the head Node
		 */
		String title;
		StringBuilder data;
		// If line doesn't contain a colon, we got issues
		if(!line.contains(":")){
			throw new IllegalArgumentException("Line '" + line + "' doesn't have colon! "
					+ "Not proper Tadukoo File Format!");
		}
		// Grab title as before the 1st colon
		title = line.split(":")[0];
		// i is for what line number we're on
		int i = 1;
		if(line.split(":").length > 1){
			// If line contains at least 1 colon, set the data
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
					// Add the next line to the data
					data.append("\n").append(lines.get(i));
					// Increment what line we're on
					i++;
				}
				// Remove the closing parenthesis from the end of the data
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
		
		// Create the head Node and add it to the List of Nodes
		nodes.add(new Node(title, data.toString(), level, null, null, null, null));
		// Increment the current Node number
		nodeNum++;
		
		/*
		 * Create the child and sibling Nodes down the line
		 */
		for(; i < lines.size(); i++){
			// Grab the next line to be read
			line = lines.get(i);
			// If line doesn't contain a colon, we got issues
			if(!line.contains(":")){
				throw new IllegalArgumentException("Line '" + line + "' doesn't have colon! "
						+ "Not proper Tadukoo File Format!");
			}
			// Grab the title from the line as before the 1st colon
			title = line.split(":")[0];
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
			level = 0;
			while(title.startsWith("  ")){
				// If the Node has spaces at the start, every 2 spaces = 1 level
				level++;
				title = title.substring(2);
			}
			
			// Create the new Node and add it to the list of Nodes
			nodes.add(new Node(title, data.toString(), level, null, null, null, null));
			
			/*
			 * Determine where to attach the new Node
			 */
			// If Node has greater level than previous Node, it's a child of the previous Node
			if(level > nodes.get(nodeNum - 1).getLevel()){
				nodes.get(nodeNum - 1).setChild(nodes.get(nodeNum));
				nodes.get(nodeNum).setParent(nodes.get(nodeNum - 1));
			}else if(level == nodes.get(nodeNum - 1).getLevel()){
				// If Node has the same level as previous Node, it's the next sibling of previous
				nodes.get(nodeNum - 1).setNextSibling(nodes.get(nodeNum));
				nodes.get(nodeNum).setPrevSibling(nodes.get(nodeNum - 1));
			}else{
				// If Node has lesser level than previous Node, find where to add it
				for(int j = nodeNum - 2; j >= 0; j--){
					// Iterate through previous Nodes
					if(level > nodes.get(j).getLevel()){
						// If current level is greater than a previous Node, add as child
						nodes.get(j).setChild(nodes.get(nodeNum));
						break;
					}else if(level == nodes.get(j).getLevel()){
						// If current level is equal to a previous Node, add as sibling
						nodes.get(j).setNextSibling(nodes.get(nodeNum));
						break;
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
				title + ":" + (data == null?"":data);
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
