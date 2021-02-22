package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

public class NodeVerificationCriteriaTest{
	private NodeVerificationCriteria crit;
	private EasyLogger logger;
	private Node node;
	private FormatNode format;
	
	@BeforeEach
	public void setup() throws IOException{
		logger = new EasyLogger(LoggerUtil.createFileLogger("target/logs/NodeVerificationCriteriaTest.log",
				Level.FINEST));
		node = Node.builder()
				.title("some title").data("some data").level(0)
				.build();
		format = FormatNode.builder()
				.logger(logger)
				.name("test")
				.titleRegex(".*").dataRegex(".*").level(0)
				.build();
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(node).format(format)
				.build();
	}
	
	@Test
	public void testDefaultFilepath(){
		assertNull(crit.getFilepath());
	}
	
	@Test
	public void testDefaultNullParent(){
		assertFalse(crit.checkNullParent());
	}
	
	@Test
	public void testDefaultNullChild(){
		assertFalse(crit.checkNullChild());
	}
	
	@Test
	public void testDefaultNullPrevSibling(){
		assertFalse(crit.checkNullPrevSibling());
	}
	
	@Test
	public void testDefaultNullNextSibling(){
		assertFalse(crit.checkNullNextSibling());
	}
	
	@Test
	public void testDefaultParent(){
		assertNull(crit.getParent());
	}
	
	@Test
	public void testDefaultChild(){
		assertNull(crit.getChild());
	}
	
	@Test
	public void testDefaultPrevSibling(){
		assertNull(crit.getPrevSibling());
	}
	
	@Test
	public void testDefaultNextSibling(){
		assertNull(crit.getNextSibling());
	}
	
	@Test
	public void testSetLogger(){
		assertEquals(logger, crit.getLogger());
	}
	
	@Test
	public void testSetFilepath(){
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.filepath("some/great/path.txt")
				.node(node).format(format)
				.build();
		assertEquals("some/great/path.txt", crit.getFilepath());
	}
	
	@Test
	public void testSetNode(){
		assertEquals(node, crit.getNode());
	}
	
	@Test
	public void testSetFormat(){
		assertEquals(format, crit.getFormat());
	}
	
	@Test
	public void testSetNullParent(){
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(node).format(format)
				.nullParent()
				.build();
		assertTrue(crit.checkNullParent());
	}
	
	@Test
	public void testSetNullChild(){
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(node).format(format)
				.nullChild()
				.build();
		assertTrue(crit.checkNullChild());
	}
	
	@Test
	public void testSetNullPrevSibling(){
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(node).format(format)
				.nullPrevSibling()
				.build();
		assertTrue(crit.checkNullPrevSibling());
	}
	
	@Test
	public void testSetNullNextSibling(){
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(node).format(format)
				.nullNextSibling()
				.build();
		assertTrue(crit.checkNullNextSibling());
	}
	
	@Test
	public void testSetParent(){
		Node parent = Node.builder()
				.title("parent").data("some data").level(0)
				.build();
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(node).format(format)
				.parent(parent)
				.build();
		assertEquals(parent, crit.getParent());
	}
	
	@Test
	public void testSetChild(){
		Node child = Node.builder()
				.title("child").data("some data").level(0)
				.build();
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(node).format(format)
				.child(child)
				.build();
		assertEquals(child, crit.getChild());
	}
	
	@Test
	public void testSetPrevSibling(){
		Node prevSibling = Node.builder()
				.title("prev sibling").data("some data").level(0)
				.build();
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(node).format(format)
				.prevSibling(prevSibling)
				.build();
		assertEquals(prevSibling, crit.getPrevSibling());
	}
	
	@Test
	public void testSetNextSibling(){
		Node nextSibling = Node.builder()
				.title("next sibling").data("some data").level(0)
				.build();
		crit = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(node).format(format)
				.nextSibling(nextSibling)
				.build();
		assertEquals(nextSibling, crit.getNextSibling());
	}
	
	@Test
	public void testMissingLogger(){
		try{
			crit = NodeVerificationCriteria.builder()
					.node(node).format(format)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create Node Verification Criteria: Logger can't be null!",
					e.getMessage());
		}
	}
	
	@Test
	public void testMissingNode(){
		try{
			crit = NodeVerificationCriteria.builder()
					.logger(logger)
					.format(format)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create Node Verification Criteria: Node can't be null!",
					e.getMessage());
		}
	}
	
	@Test
	public void testMissingFormat(){
		try{
			crit = NodeVerificationCriteria.builder()
					.logger(logger)
					.node(node)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create Node Verification Criteria: Format (Node) can't be null!",
					e.getMessage());
		}
	}
	
	@Test
	public void testNullAndSpecifiedParent(){
		try{
			Node parent = Node.builder()
					.title("parent").data("some data").level(0)
					.build();
			crit = NodeVerificationCriteria.builder()
					.logger(logger)
					.node(node).format(format)
					.nullParent().parent(parent)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals(
					"Failed to create Node Verification Criteria: Can't have a null parent and one defined!",
					e.getMessage());
		}
	}
	
	@Test
	public void testNullAndSpecifiedChild(){
		try{
			Node child = Node.builder()
					.title("child").data("some data").level(2)
					.build();
			crit = NodeVerificationCriteria.builder()
					.logger(logger)
					.node(node).format(format)
					.nullChild().child(child)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals(
					"Failed to create Node Verification Criteria: Can't have a null child and one defined!",
					e.getMessage());
		}
	}
	
	@Test
	public void testNullAndSpecifiedPrevSibling(){
		try{
			Node prevSibling = Node.builder()
					.title("prev sibling").data("some data").level(1)
					.build();
			crit = NodeVerificationCriteria.builder()
					.logger(logger)
					.node(node).format(format)
					.nullPrevSibling().prevSibling(prevSibling)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals(
					"Failed to create Node Verification Criteria: Can't have a null previous sibling and one defined!",
					e.getMessage());
		}
	}
	
	@Test
	public void testNullAndSpecifiedNextSibling(){
		try{
			Node nextSibling = Node.builder()
					.title("next sibling").data("some data").level(1)
					.build();
			crit = NodeVerificationCriteria.builder()
					.logger(logger)
					.node(node).format(format)
					.nullNextSibling().nextSibling(nextSibling)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals(
					"Failed to create Node Verification Criteria: Can't have a null next sibling and one defined!",
					e.getMessage());
		}
	}
	
	@Test
	public void testAllProblems(){
		try{
			Node parent = Node.builder()
					.title("parent").data("some data").level(0)
					.build();
			Node child = Node.builder()
					.title("child").data("some data").level(2)
					.build();
			Node prevSibling = Node.builder()
					.title("prev sibling").data("some data").level(1)
					.build();
			Node nextSibling = Node.builder()
					.title("next sibling").data("some data").level(1)
					.build();
			crit = NodeVerificationCriteria.builder()
					.nullParent().parent(parent)
					.nullChild().child(child)
					.nullPrevSibling().prevSibling(prevSibling)
					.nullNextSibling().nextSibling(nextSibling)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Failed to create Node Verification Criteria: Logger can't be null!
					Node can't be null!
					Format (Node) can't be null!
					Can't have a null parent and one defined!
					Can't have a null child and one defined!
					Can't have a null previous sibling and one defined!
					Can't have a null next sibling and one defined!""", e.getMessage());
		}
	}
}
