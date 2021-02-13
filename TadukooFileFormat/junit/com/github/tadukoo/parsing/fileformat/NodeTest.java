package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.ListUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class NodeTest{
	private Node simpleNode;
	
	@BeforeEach
	public void setup(){
		simpleNode = new Node("some title", "some data", 1,
				null, null, null, null);
	}
	
	@Test
	public void testBasicConstructor(){
		Node parent = new Node("parent", "yep parent", 0,
				null, null, null, null);
		Node child = new Node("child", "yeah child", 2,
				null, null, null, null);
		Node prevSibling = new Node("young sibling", "they be young", 1,
				null, null, null, null);
		Node nextSibling = new Node("old sibling", "they be old", 1,
				null, null, null, null);
		Node node = new Node("some title", "some data", 1,
				parent, child, prevSibling, nextSibling);
		assertEquals("some title", node.getTitle());
		assertEquals("some data", node.getData());
		assertEquals(1, node.getLevel());
		assertEquals(parent, node.getParent());
		assertEquals(child, node.getChild());
		assertEquals(prevSibling, node.getPrevSibling());
		assertEquals(nextSibling, node.getNextSibling());
	}
	
	@Test
	public void testBasicConstructorBadParentSameLevel(){
		Node parent = new Node("parent", "they be an adult", 0,
				null, null, null, null);
		try{
			new Node("some title", "some data", 0,
					parent, null, null, null);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Parent's level must be less than child's level!", e.getMessage());
		}
	}
	
	@Test
	public void testBasicConstructorBadParentHigherLevel(){
		Node parent = new Node("parent", "they be an adult", 1,
				null, null, null, null);
		try{
			new Node("some title", "some data", 0,
					parent, null, null, null);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Parent's level must be less than child's level!", e.getMessage());
		}
	}
	
	@Test
	public void testBasicConstructorBadChildSameLevel(){
		Node child = new Node("child", "they be a baby", 0,
				null, null, null, null);
		try{
			new Node("some title", "some data", 0,
					null, child, null, null);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Child's level must be greater than parent's level!", e.getMessage());
		}
	}
	
	@Test
	public void testBasicConstructorBadChildLowerLevel(){
		Node child = new Node("child", "they be a baby", 0,
				null, null, null, null);
		try{
			new Node("some title", "some data", 1,
					null, child, null, null);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Child's level must be greater than parent's level!", e.getMessage());
		}
	}
	
	@Test
	public void testBasicConstructorBadPrevSiblingLowerLevel(){
		Node prevSibling = new Node("sibling", "they be same age?", 0,
				null, null, null, null);
		try{
			new Node("some title", "some data", 1,
					null, null, prevSibling, null);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Sibling's level must be the same level!", e.getMessage());
		}
	}
	
	@Test
	public void testBasicConstructorBadPrevSiblingHigherLevel(){
		Node prevSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		try{
			new Node("some title", "some data", 0,
					null, null, prevSibling, null);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Sibling's level must be the same level!", e.getMessage());
		}
	}
	
	@Test
	public void testBasicConstructorBadNextSiblingLowerLevel(){
		Node nextSibling = new Node("sibling", "they be same age?", 0,
				null, null, null, null);
		try{
			new Node("some title", "some data", 1,
					null, null, null, nextSibling);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Sibling's level must be the same level!", e.getMessage());
		}
	}
	
	@Test
	public void testBasicConstructorBadNextSiblingHigherLevel(){
		Node nextSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		try{
			new Node("some title", "some data", 0,
					null, null, null, nextSibling);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Sibling's level must be the same level!", e.getMessage());
		}
	}
	
	@Test
	public void testLoadFromFileWithChildAndSibling(){
		Node node = Node.loadFromFile("junit-resource/LoadNodeFromFileTest");
		// Check node
		assertEquals("test", node.getTitle());
		assertEquals("derp", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNotNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNotNull(node.getNextSibling());
		// Check child
		Node child = node.getChild();
		assertEquals("other test", child.getTitle());
		assertEquals("not derp", child.getData());
		assertEquals(1, child.getLevel());
		assertEquals(node, child.getParent());
		assertNull(child.getChild());
		assertNull(child.getPrevSibling());
		assertNull(child.getNextSibling());
		// Check sibling
		Node sibling = node.getNextSibling();
		assertEquals("beep", sibling.getTitle());
		assertEquals("boop", sibling.getData());
		assertEquals(0, sibling.getLevel());
		assertNull(sibling.getParent());
		assertNull(sibling.getChild());
		assertEquals(node, sibling.getPrevSibling());
		assertNull(sibling.getNextSibling());
	}
	
	@Test
	public void testLoadFromStringWithChildAndSibling(){
		Node node = Node.loadFromString("""
				test:derp
				  other test:not derp
				beep:boop""");
		// Check node
		assertEquals("test", node.getTitle());
		assertEquals("derp", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNotNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNotNull(node.getNextSibling());
		// Check child
		Node child = node.getChild();
		assertEquals("other test", child.getTitle());
		assertEquals("not derp", child.getData());
		assertEquals(1, child.getLevel());
		assertEquals(node, child.getParent());
		assertNull(child.getChild());
		assertNull(child.getPrevSibling());
		assertNull(child.getNextSibling());
		// Check sibling
		Node sibling = node.getNextSibling();
		assertEquals("beep", sibling.getTitle());
		assertEquals("boop", sibling.getData());
		assertEquals(0, sibling.getLevel());
		assertNull(sibling.getParent());
		assertNull(sibling.getChild());
		assertEquals(node, sibling.getPrevSibling());
		assertNull(sibling.getNextSibling());
	}
	
	@Test
	public void testLoadFromListSimple(){
		Node node = Node.loadFromList(ListUtil.createList("test:"));
		assertEquals("test", node.getTitle());
		assertEquals("", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNull(node.getNextSibling());
	}
	
	@Test
	public void testLoadFromListSimpleData(){
		Node node = Node.loadFromList(ListUtil.createList("test:derp"));
		assertEquals("test", node.getTitle());
		assertEquals("derp", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNull(node.getNextSibling());
	}
	
	@Test
	public void testLoadFromListColonsInData(){
		Node node = Node.loadFromList(ListUtil.createList("test:derp:yep"));
		assertEquals("test", node.getTitle());
		assertEquals("derp:yep", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNull(node.getNextSibling());
	}
	
	@Test
	public void testLoadFromListMultilineData(){
		Node node = Node.loadFromList(ListUtil.createList("test:(derp", "yep)"));
		assertEquals("test", node.getTitle());
		assertEquals("derp\nyep", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNull(node.getNextSibling());
	}
	
	@Test
	public void testLoadFromListEscapedParenthesis(){
		Node node = Node.loadFromList(ListUtil.createList("test:\\(derp)"));
		assertEquals("test", node.getTitle());
		assertEquals("(derp)", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNull(node.getNextSibling());
	}
	
	@Test
	public void testLoadFromListWithChild(){
		Node node = Node.loadFromList(ListUtil.createList("test:derp", "  other test:not derp"));
		assertEquals("test", node.getTitle());
		assertEquals("derp", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNotNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNull(node.getNextSibling());
		Node child = node.getChild();
		assertEquals("other test", child.getTitle());
		assertEquals("not derp", child.getData());
		assertEquals(1, child.getLevel());
		assertEquals(node, child.getParent());
		assertNull(child.getChild());
		assertNull(child.getPrevSibling());
		assertNull(child.getNextSibling());
	}
	
	@Test
	public void testLoadFromListWithSibling(){
		Node node = Node.loadFromList(ListUtil.createList("test:derp", "other test:not derp"));
		assertEquals("test", node.getTitle());
		assertEquals("derp", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNotNull(node.getNextSibling());
		Node sibling = node.getNextSibling();
		assertEquals("other test", sibling.getTitle());
		assertEquals("not derp", sibling.getData());
		assertEquals(0, sibling.getLevel());
		assertNull(sibling.getParent());
		assertNull(sibling.getChild());
		assertEquals(node, sibling.getPrevSibling());
		assertNull(sibling.getNextSibling());
	}
	
	@Test
	public void testLoadFromListWithChildAndSibling(){
		Node node = Node.loadFromList(ListUtil.createList("test:derp", "  other test:not derp", "beep:boop"));
		// Check node
		assertEquals("test", node.getTitle());
		assertEquals("derp", node.getData());
		assertEquals(0, node.getLevel());
		assertNull(node.getParent());
		assertNotNull(node.getChild());
		assertNull(node.getPrevSibling());
		assertNotNull(node.getNextSibling());
		// Check child
		Node child = node.getChild();
		assertEquals("other test", child.getTitle());
		assertEquals("not derp", child.getData());
		assertEquals(1, child.getLevel());
		assertEquals(node, child.getParent());
		assertNull(child.getChild());
		assertNull(child.getPrevSibling());
		assertNull(child.getNextSibling());
		// Check sibling
		Node sibling = node.getNextSibling();
		assertEquals("beep", sibling.getTitle());
		assertEquals("boop", sibling.getData());
		assertEquals(0, sibling.getLevel());
		assertNull(sibling.getParent());
		assertNull(sibling.getChild());
		assertEquals(node, sibling.getPrevSibling());
		assertNull(sibling.getNextSibling());
	}
	
	@Test
	public void testLoadFromListMissingColon(){
		try{
			Node.loadFromList(ListUtil.createList("test"));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Line 'test' doesn't have colon! "
					+ "Not proper Tadukoo File Format!", e.getMessage());
		}
	}
	
	@Test
	public void testLoadFromListMissingClosingParenthesis(){
		try{
			Node.loadFromList(ListUtil.createList("test:(derp", "yep"));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Reached end of lines without hitting closing parenthesis!", e.getMessage());
		}
	}
	
	@Test
	public void testLoadFromListHeadNodeNotLevel0(){
		try{
			Node.loadFromList(ListUtil.createList("  test:derp"));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Head Node should be level 0, but was level 1!", e.getMessage());
		}
	}
	
	@Test
	public void testLoadFromListSkippedLevel(){
		try{
			Node.loadFromList(ListUtil.createList("test:derp", "    other test:not derp"));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Skipped a level from 0 to 2", e.getMessage());
		}
	}
	
	@Test
	public void testSetParent(){
		Node parent = new Node("parent", "they be an adult", 0,
				null, null, null, null);
		simpleNode.setParent(parent);
		assertEquals(parent, simpleNode.getParent());
	}
	
	@Test
	public void testSetParentBadParentSameLevel(){
		try{
			simpleNode.setParent(new Node("parent", "they be an adult", 1,
					null, null, null, null));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Parent's level must be less than child's level!", e.getMessage());
		}
	}
	
	@Test
	public void testSetParentBadParentHigherLevel(){
		try{
			simpleNode.setParent(new Node("parent", "they be an adult", 2,
					null, null, null, null));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Parent's level must be less than child's level!", e.getMessage());
		}
	}
	
	@Test
	public void testSetChild(){
		Node child = new Node("child", "they be a baby", 2,
				null, null, null, null);
		simpleNode.setChild(child);
		assertEquals(child, simpleNode.getChild());
	}
	
	@Test
	public void testSetChildBadChildSameLevel(){
		try{
			simpleNode.setChild(new Node("child", "they be a baby", 1,
					null, null, null, null));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Child's level must be greater than parent's level!", e.getMessage());
		}
	}
	
	@Test
	public void testSetChildBadChildLowerLevel(){
		try{
			simpleNode.setChild(new Node("child", "they be a baby", 0,
					null, null, null, null));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Child's level must be greater than parent's level!", e.getMessage());
		}
	}
	
	@Test
	public void testSetPrevSibling(){
		Node prevSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setPrevSibling(prevSibling);
		assertEquals(prevSibling, simpleNode.getPrevSibling());
	}
	
	@Test
	public void testSetPrevSiblingBadPrevSiblingLowerLevel(){
		try{
			simpleNode.setPrevSibling(new Node("sibling", "they be same age?", 0,
					null, null, null, null));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Sibling's level must be the same level!", e.getMessage());
		}
	}
	
	@Test
	public void testSetPrevSiblingBadPrevSiblingHigherLevel(){
		try{
			simpleNode.setPrevSibling(new Node("sibling", "they be same age?", 2,
					null, null, null, null));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Sibling's level must be the same level!", e.getMessage());
		}
	}
	
	@Test
	public void testSetNextSibling(){
		Node nextSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setNextSibling(nextSibling);
		assertEquals(nextSibling, simpleNode.getNextSibling());
	}
	
	@Test
	public void testSetNextSiblingBadNextSiblingLowerLevel(){
		try{
			simpleNode.setNextSibling(new Node("sibling", "they be same age?", 0,
					null, null, null, null));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Sibling's level must be the same level!", e.getMessage());
		}
	}
	
	@Test
	public void testSetNextSiblingBadNextSiblingHigherLevel(){
		try{
			simpleNode.setNextSibling(new Node("sibling", "they be same age?", 2,
					null, null, null, null));
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Sibling's level must be the same level!", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("  some title:some data", simpleNode.toString());
	}
	
	@Test
	public void testToStringNoDataLevel0(){
		Node node = new Node("some title", null, 0,
				null, null, null, null);
		assertEquals("some title:", node.toString());
	}
	
	@Test
	public void testFullToStringNoRelations(){
		assertEquals("  some title:some data", simpleNode.fullToString());
	}
	
	@Test
	public void testFullToStringWithChild(){
		Node child = new Node("child", "a baby", 2,
				null, null, null, null);
		simpleNode.setChild(child);
		assertEquals("""
				  some title:some data
				    child:a baby\
				""", simpleNode.fullToString());
	}
	
	@Test
	public void testFullToStringWithNextSibling(){
		Node nextSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setNextSibling(nextSibling);
		assertEquals("""
				  some title:some data
				  sibling:they be same age?\
				""", simpleNode.fullToString());
	}
	
	@Test
	public void testFullToStringWithChildAndNextSibling(){
		Node child = new Node("child", "a baby", 2,
				null, null, null, null);
		simpleNode.setChild(child);
		Node nextSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setNextSibling(nextSibling);
		assertEquals("""
				  some title:some data
				    child:a baby
				  sibling:they be same age?\
				""", simpleNode.fullToString());
	}
	
	@Test
	public void testFullToStringWithParent(){
		Node parent = new Node("parent", "they be an adult", 0,
				null, null, null, null);
		simpleNode.setParent(parent);
		assertEquals("  some title:some data", simpleNode.toString());
	}
	
	@Test
	public void testFullToStringWithPrevSibling(){
		Node prevSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setPrevSibling(prevSibling);
		assertEquals("  some title:some data", simpleNode.toString());
	}
	
	@Test
	public void testGetAllTitlesNoRelations(){
		List<String> titles = simpleNode.getAllTitles();
		assertEquals(1, titles.size());
		assertEquals("some title", titles.get(0));
	}
	
	@Test
	public void testGetAllTitlesWithChild(){
		Node child = new Node("child", "a baby", 2,
				null, null, null, null);
		simpleNode.setChild(child);
		List<String> titles = simpleNode.getAllTitles();
		assertEquals(2, titles.size());
		assertEquals("some title", titles.get(0));
		assertEquals("child", titles.get(1));
	}
	
	@Test
	public void testGetAllTitlesWithNextSibling(){
		Node nextSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setNextSibling(nextSibling);
		List<String> titles = simpleNode.getAllTitles();
		assertEquals(2, titles.size());
		assertEquals("some title", titles.get(0));
		assertEquals("sibling", titles.get(1));
	}
	
	@Test
	public void testGetAllTitlesWithChildAndNextSibling(){
		Node child = new Node("child", "a baby", 2,
				null, null, null, null);
		simpleNode.setChild(child);
		Node nextSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setNextSibling(nextSibling);
		List<String> titles = simpleNode.getAllTitles();
		assertEquals(3, titles.size());
		assertEquals("some title", titles.get(0));
		assertEquals("child", titles.get(1));
		assertEquals("sibling", titles.get(2));
	}
	
	@Test
	public void testGetAllTitlesWithParent(){
		Node parent = new Node("parent", "they be an adult", 0,
				null, null, null, null);
		simpleNode.setParent(parent);
		List<String> titles = simpleNode.getAllTitles();
		assertEquals(1, titles.size());
		assertEquals("some title", titles.get(0));
	}
	
	@Test
	public void testGetAllTitlesWithPrevSibling(){
		Node prevSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setPrevSibling(prevSibling);
		List<String> titles = simpleNode.getAllTitles();
		assertEquals(1, titles.size());
		assertEquals("some title", titles.get(0));
	}
	
	@Test
	public void testGetAllDatasNoRelations(){
		List<String> datas = simpleNode.getAllDatas();
		assertEquals(1, datas.size());
		assertEquals("some data", datas.get(0));
	}
	
	@Test
	public void testGetAllDatasWithChild(){
		Node child = new Node("child", "a baby", 2,
				null, null, null, null);
		simpleNode.setChild(child);
		List<String> datas = simpleNode.getAllDatas();
		assertEquals(2, datas.size());
		assertEquals("some data", datas.get(0));
		assertEquals("a baby", datas.get(1));
	}
	
	@Test
	public void testGetAllDatasWithNextSibling(){
		Node nextSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setNextSibling(nextSibling);
		List<String> datas = simpleNode.getAllDatas();
		assertEquals(2, datas.size());
		assertEquals("some data", datas.get(0));
		assertEquals("they be same age?", datas.get(1));
	}
	
	@Test
	public void testGetAllDatasWithChildAndNextSibling(){
		Node child = new Node("child", "a baby", 2,
				null, null, null, null);
		simpleNode.setChild(child);
		Node nextSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setNextSibling(nextSibling);
		List<String> datas = simpleNode.getAllDatas();
		assertEquals(3, datas.size());
		assertEquals("some data", datas.get(0));
		assertEquals("a baby", datas.get(1));
		assertEquals("they be same age?", datas.get(2));
	}
	
	@Test
	public void testGetAllDatasWithParent(){
		Node parent = new Node("parent", "they be an adult", 0,
				null, null, null, null);
		simpleNode.setParent(parent);
		List<String> datas = simpleNode.getAllDatas();
		assertEquals(1, datas.size());
		assertEquals("some data", datas.get(0));
	}
	
	@Test
	public void testGetAllDatasWithPrevSibling(){
		Node prevSibling = new Node("sibling", "they be same age?", 1,
				null, null, null, null);
		simpleNode.setPrevSibling(prevSibling);
		List<String> datas = simpleNode.getAllDatas();
		assertEquals(1, datas.size());
		assertEquals("some data", datas.get(0));
	}
}
