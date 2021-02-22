package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormatNodeVerificationTest{
	private EasyLogger logger;
	
	@BeforeEach
	public void setup() throws IOException{
		logger = new EasyLogger(LoggerUtil.createFileLogger("target/logs/FormatNodeVerificationTest.log",
				Level.FINEST));
	}
	
	@Test
	public void testVerifySingleNode(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(0)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(0)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertTrue(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeWithFilename(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title.png").data("some data").level(0)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("<filename>").dataRegex("some data").level(0)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.filepath("some/great/file/path/some title.png")
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertTrue(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeWithFileTitle(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(0)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("<fileTitle>").dataRegex("some data").level(0)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.filepath("some/great/file/path/some title.png")
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertTrue(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeWithFileExtension(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("png").data("some data").level(0)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("<fileExtension>").dataRegex("some data").level(0)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.filepath("some/great/file/path/some title.png")
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertTrue(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseBadTitle(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some titles").data("some data").level(0)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(0)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseBadData(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some datas").level(0)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(0)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseBadLevel(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(0)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseNonNullParent(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.parent(Node.builder()
								.title("parent").data("some data").level(0)
								.build())
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeHasParent(){
		Node parent = Node.builder()
				.title("parent").data("some data").level(0)
				.build();
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.parent(parent)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.parent(parent).nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertTrue(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseMissingParent(){
		Node parent = Node.builder()
				.title("parent").data("some data").level(0)
				.build();
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.parent(parent).nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseNonNullChild(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.child(Node.builder()
								.title("child").data("some data").level(2)
								.build())
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeHasChild(){
		Node child = Node.builder()
				.title("child").data("some data").level(2)
				.build();
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.child(child)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().child(child)
				.nullPrevSibling().nullNextSibling()
				.build();
		assertTrue(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseMissingChild(){
		Node child = Node.builder()
				.title("child").data("some data").level(2)
				.build();
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().child(child)
				.nullPrevSibling().nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseNonNullPrevSibling(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.prevSibling(Node.builder()
								.title("prev sibling").data("some data").level(1)
								.build())
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeHasPrevSibling(){
		Node prevSibling = Node.builder()
				.title("prev sibling").data("some data").level(1)
				.build();
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.prevSibling(prevSibling)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.prevSibling(prevSibling).nullNextSibling()
				.build();
		assertTrue(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseMissingPrevSibling(){
		Node prevSibling = Node.builder()
				.title("prev sibling").data("some data").level(1)
				.build();
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.prevSibling(prevSibling).nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseNonNullNextSibling(){
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.nextSibling(Node.builder()
								.title("next sibling").data("some data").level(1)
								.build())
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nullNextSibling()
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeHasNextSibling(){
		Node nextSibling = Node.builder()
				.title("next sibling").data("some data").level(1)
				.build();
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.nextSibling(nextSibling)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nextSibling(nextSibling)
				.build();
		assertTrue(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testVerifySingleNodeFalseMissingNextSibling(){
		Node nextSibling = Node.builder()
				.title("next sibling").data("some data").level(1)
				.build();
		NodeVerificationCriteria criteria = NodeVerificationCriteria.builder()
				.logger(logger)
				.node(Node.builder()
						.title("some title").data("some data").level(1)
						.build())
				.format(FormatNode.builder()
						.logger(logger)
						.name("whatevs")
						.titleRegex("some title").dataRegex("some data").level(1)
						.nullParentName().nullChildName()
						.nullPrevSiblingName().nullNextSiblingName()
						.build())
				.nullParent().nullChild()
				.nullPrevSibling().nextSibling(nextSibling)
				.build();
		assertFalse(FormatNodeVerification.verifySingleNode(criteria));
	}
	
	@Test
	public void testAssertParent(){
		Node parent = Node.builder()
				.title("parent").data("some data").level(0)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.parent(parent)
				.build();
		assertTrue(FormatNodeVerification.assertParent(logger, node, parent));
	}
	
	@Test
	public void testAssertParentFalse(){
		Node parent = Node.builder()
				.title("parent").data("some data").level(0)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.build();
		assertFalse(FormatNodeVerification.assertParent(logger, node, parent));
	}
	
	@Test
	public void testAssertNullParent(){
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.build();
		assertTrue(FormatNodeVerification.assertNullParent(logger, node));
	}
	
	@Test
	public void testAssertNullParentFalse(){
		Node parent = Node.builder()
				.title("parent").data("some data").level(0)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.parent(parent)
				.build();
		assertFalse(FormatNodeVerification.assertNullParent(logger, node));
	}
	
	@Test
	public void testAssertChild(){
		Node child = Node.builder()
				.title("child").data("some data").level(2)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.child(child)
				.build();
		assertTrue(FormatNodeVerification.assertChild(logger, node, child));
	}
	
	@Test
	public void testAssertChildFalse(){
		Node child = Node.builder()
				.title("child").data("some data").level(2)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.build();
		assertFalse(FormatNodeVerification.assertChild(logger, node, child));
	}
	
	@Test
	public void testAssertNullChild(){
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.build();
		assertTrue(FormatNodeVerification.assertNullChild(logger, node));
	}
	
	@Test
	public void testAssertNullChildFalse(){
		Node child = Node.builder()
				.title("child").data("some data").level(2)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.child(child)
				.build();
		assertFalse(FormatNodeVerification.assertNullChild(logger, node));
	}
	
	@Test
	public void testAssertPrevSibling(){
		Node prevSibling = Node.builder()
				.title("prev sibling").data("some data").level(1)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.prevSibling(prevSibling)
				.build();
		assertTrue(FormatNodeVerification.assertPrevSibling(logger, node, prevSibling));
	}
	
	@Test
	public void testAssertPrevSiblingFalse(){
		Node prevSibling = Node.builder()
				.title("prev sibling").data("some data").level(1)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.build();
		assertFalse(FormatNodeVerification.assertPrevSibling(logger, node, prevSibling));
	}
	
	@Test
	public void testAssertNullPrevSibling(){
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.build();
		assertTrue(FormatNodeVerification.assertNullPrevSibling(logger, node));
	}
	
	@Test
	public void testAssertNullPrevSiblingFalse(){
		Node prevSibling = Node.builder()
				.title("prev sibling").data("some data").level(1)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.prevSibling(prevSibling)
				.build();
		assertFalse(FormatNodeVerification.assertNullPrevSibling(logger, node));
	}
	
	@Test
	public void testAssertNextSibling(){
		Node nextSibling = Node.builder()
				.title("next sibling").data("some data").level(1)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.nextSibling(nextSibling)
				.build();
		assertTrue(FormatNodeVerification.assertNextSibling(logger, node, nextSibling));
	}
	
	@Test
	public void testAssertNextSiblingFalse(){
		Node nextSibling = Node.builder()
				.title("next sibling").data("some data").level(1)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.build();
		assertFalse(FormatNodeVerification.assertNextSibling(logger, node, nextSibling));
	}
	
	@Test
	public void testAssertNullNextSibling(){
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.build();
		assertTrue(FormatNodeVerification.assertNullNextSibling(logger, node));
	}
	
	@Test
	public void testAssertNullNextSiblingFalse(){
		Node nextSibling = Node.builder()
				.title("next sibling").data("some data").level(1)
				.build();
		Node node = Node.builder()
				.title("some title").data("some data").level(1)
				.nextSibling(nextSibling)
				.build();
		assertFalse(FormatNodeVerification.assertNullNextSibling(logger, node));
	}
}
