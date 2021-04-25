package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.StringUtil;
import com.github.tadukoo.util.logger.EasyLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Criteria object used for verifying a {@link Node}. Used in {@link FormatNodeVerification}.
 *
 * @author Logan Ferree (Tadukoo)
 * @version Alpha v.0.3
 */
public class NodeVerificationCriteria{
	
	/**
	 * Node Verification Criteria Builder is a builder for {@link NodeVerificationCriteria}. It has the following
	 * parameters:
	 *
	 * <table>
	 *     <caption>Node Verification Criteria</caption>
	 *     <tr>
	 *         <th>Name</th>
	 *         <th>Description</th>
	 *         <th>Default or Required</th>
	 *     </tr>
	 *     <tr>
	 *         <td>logger</td>
	 *         <td>The {@link EasyLogger} to use in logging messages</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>filepath</td>
	 *         <td>The path to the file - used in some formatting</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>node</td>
	 *         <td>The Node to be checked</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>format</td>
	 *         <td>The FormatNode that defines the expected format</td>
	 *         <td>Required</td>
	 *     </tr>
	 *     <tr>
	 *         <td>nullParent</td>
	 *         <td>Whether to check that the parent is null or not</td>
	 *         <td>Defaults to false</td>
	 *     </tr>
	 *     <tr>
	 *         <td>nullChild</td>
	 *         <td>Whether to check that the child is null or not</td>
	 *         <td>Defaults to false</td>
	 *     </tr>
	 *     <tr>
	 *         <td>nullPrevSibling</td>
	 *         <td>Whether to check that the previous sibling is null or not</td>
	 *         <td>Defaults to false</td>
	 *     </tr>
	 *     <tr>
	 *         <td>nullNextSibling</td>
	 *         <td>Whether to check that the next sibling is null or not</td>
	 *         <td>Defaults to false</td>
	 *     </tr>
	 *     <tr>
	 *         <td>parent</td>
	 *         <td>The expected parent for the Node</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>child</td>
	 *         <td>The expected child for the Node</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>prevSibling</td>
	 *         <td>The expected previous sibling for the Node</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 *     <tr>
	 *         <td>nextSibling</td>
	 *         <td>The expected next sibling for the Node</td>
	 *         <td>Defaults to null</td>
	 *     </tr>
	 * </table>
	 *
	 * @author Logan Ferree (Tadukoo)
	 * @version Alpha v.0.3
	 */
	public static class NodeVerificationCriteriaBuilder{
		/** The {@link EasyLogger} to use in logging messages */
		private EasyLogger logger = null;
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
		private NodeVerificationCriteriaBuilder(){ }
		
		/**
		 * Sets the {@link EasyLogger} to use in logging messages
		 *
		 * @param logger The {@link EasyLogger} to use in logging messages
		 * @return This builder, to continue in building
		 */
		public NodeVerificationCriteriaBuilder logger(EasyLogger logger){
			this.logger = logger;
			return this;
		}
		
		/**
		 * Sets the path to the file to be used in some formatting checks
		 *
		 * @param filepath The path to the file
		 * @return This builder, to continue in building
		 */
		public NodeVerificationCriteriaBuilder filepath(String filepath){
			this.filepath = filepath;
			return this;
		}
		
		/**
		 * Sets the Node to be checked
		 *
		 * @param node The Node to be checked
		 * @return This builder, to continue in building
		 */
		public NodeVerificationCriteriaBuilder node(Node node){
			this.node = node;
			return this;
		}
		
		/**
		 * Sets the FormatNode, that defines the expected format
		 *
		 * @param format The FormatNode that defines the expected format
		 * @return This builder, to continue in building
		 */
		public NodeVerificationCriteriaBuilder format(FormatNode format){
			this.format = format;
			return this;
		}
		
		/**
		 * Sets to check that the parent is null
		 *
		 * @return This builder, to continue in building
		 */
		public NodeVerificationCriteriaBuilder nullParent(){
			nullParent = true;
			return this;
		}
		
		/**
		 * Sets to check that the child is null
		 *
		 * @return This builder, to continue in building
		 */
		public NodeVerificationCriteriaBuilder nullChild(){
			nullChild = true;
			return this;
		}
		
		/**
		 * Sets to check that the previous sibling is null
		 *
		 * @return This builder, to continue in building
		 */
		public NodeVerificationCriteriaBuilder nullPrevSibling(){
			nullPrevSibling = true;
			return this;
		}
		
		/**
		 * Sets to check that the next sibling is null
		 *
		 * @return This builder, to continue in building
		 */
		public NodeVerificationCriteriaBuilder nullNextSibling(){
			nullNextSibling = true;
			return this;
		}
		
		/**
		 * Sets the expected parent for the Node being checked
		 *
		 * @param parent The expected parent for the Node being checked
		 * @return This builder, to continue building
		 */
		public NodeVerificationCriteriaBuilder parent(Node parent){
			this.parent = parent;
			return this;
		}
		
		/**
		 * Sets the expected child for the Node being checked
		 *
		 * @param child The expected child for the Node being checked
		 * @return This builder, to continue building
		 */
		public NodeVerificationCriteriaBuilder child(Node child){
			this.child = child;
			return this;
		}
		
		/**
		 * Sets the expected previous sibling for the Node being checked
		 *
		 * @param prevSibling The expected previous sibling for the Node being checked
		 * @return This builder, to continue building
		 */
		public NodeVerificationCriteriaBuilder prevSibling(Node prevSibling){
			this.prevSibling = prevSibling;
			return this;
		}
		
		/**
		 * Sets the expected next sibling for the Node being checked
		 *
		 * @param nextSibling The expected next sibling for the Node being checked
		 * @return This builder, to continue building
		 */
		public NodeVerificationCriteriaBuilder nextSibling(Node nextSibling){
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
				errors.add("Can't have a null parent and one defined!");
			}
			
			// Can't check null and existing child
			if(nullChild && child != null){
				errors.add("Can't have a null child and one defined!");
			}
			
			// Can't check null and existing previous sibling
			if(nullPrevSibling && prevSibling != null){
				errors.add("Can't have a null previous sibling and one defined!");
			}
			
			// Can't check null and existing next sibling
			if(nullNextSibling && nextSibling != null){
				errors.add("Can't have a null next sibling and one defined!");
			}
			
			// If any errors, throw an exception
			if(!errors.isEmpty()){
				throw new IllegalArgumentException("Failed to create Node Verification Criteria: " +
						StringUtil.buildStringWithNewLines(errors));
			}
		}
		
		/**
		 * Actually builds the {@link NodeVerificationCriteria} object
		 *
		 * @return The created {@link NodeVerificationCriteria}
		 */
		public NodeVerificationCriteria build(){
			// Check for any problems
			checkForErrors();
			
			// Build the actual params object
			return new NodeVerificationCriteria(logger, filepath, node, format,
					nullParent, nullChild, nullPrevSibling, nullNextSibling,
					parent, child, prevSibling, nextSibling);
		}
	}
	
	/** The {@link EasyLogger} to use in logging messages */
	private final EasyLogger logger;
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
	
	/**
	 * Creates a new NodeVerificationCriteria object with the given parameters
	 *
	 * @param logger The {@link EasyLogger} to use in logging messages
	 * @param filepath The path to the file - used in some formatting
	 * @param node The Node to be checked
	 * @param format The FormatNode that defines the expected format
	 * @param nullParent Whether to check that the parent is null or not
	 * @param nullChild Whether to check that the child is null or not
	 * @param nullPrevSibling Whether to check that the previous sibling is null or not
	 * @param nullNextSibling Whether to check that the next sibling is null or not
	 * @param parent The expected parent for the Node
	 * @param child The expected child for the Node
	 * @param prevSibling The expected previous sibling for the Node
	 * @param nextSibling The expected next sibling for the Node
	 */
	private NodeVerificationCriteria(EasyLogger logger, String filepath, Node node, FormatNode format,
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
	
	/**
	 * @return A builder for the params object for verifying a single Node
	 */
	public static NodeVerificationCriteriaBuilder builder(){
		return new NodeVerificationCriteriaBuilder();
	}
	
	/**
	 * @return The {@link EasyLogger} to use in logging messages
	 */
	public EasyLogger getLogger(){
		return logger;
	}
	
	/**
	 * @return The path to the file - used in some formatting
	 */
	public String getFilepath(){
		return filepath;
	}
	
	/**
	 * @return The Node to be checked
	 */
	public Node getNode(){
		return node;
	}
	
	/**
	 * @return The FormatNode that defines the expected format
	 */
	public FormatNode getFormat(){
		return format;
	}
	
	/**
	 * @return Whether to check that the parent is null or not
	 */
	public boolean checkNullParent(){
		return nullParent;
	}
	
	/**
	 * @return Whether to check that the child is null or not
	 */
	public boolean checkNullChild(){
		return nullChild;
	}
	
	/**
	 * @return Whether to check that the previous sibling is null or not
	 */
	public boolean checkNullPrevSibling(){
		return nullPrevSibling;
	}
	
	/**
	 * @return Whether to check that the next sibling is null or not
	 */
	public boolean checkNullNextSibling(){
		return nullNextSibling;
	}
	
	/**
	 * @return The expected parent for the Node
	 */
	public Node getParent(){
		return parent;
	}
	
	/**
	 * @return The expected child for the Node
	 */
	public Node getChild(){
		return child;
	}
	
	/**
	 * @return The expected previous sibling for the Node
	 */
	public Node getPrevSibling(){
		return prevSibling;
	}
	
	/**
	 * @return The expected next sibling for the Node
	 */
	public Node getNextSibling(){
		return nextSibling;
	}
}
