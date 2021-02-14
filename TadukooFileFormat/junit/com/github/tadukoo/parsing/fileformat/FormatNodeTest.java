package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FormatNodeTest{
	private final String subfolder = "target/FormatNodeTest/";
	private FormatNode node;
	
	@BeforeEach
	public void setup() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "setup.log", Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.build();
	}
	
	@Test
	public void testNullNode(){
		assertEquals("<null>", FormatNode.NULL_NODE);
	}
	
	@Test
	public void testSetName(){
		assertEquals("test", node.getName());
	}
	
	@Test
	public void testSetTitleRegex(){
		assertEquals("test(\\d)*", node.getTitleRegex());
	}
	
	@Test
	public void testSetDataRegex(){
		assertEquals("(.*)", node.getDataRegex());
	}
	
	@Test
	public void testSetLevel(){
		assertEquals(0, node.getLevel());
	}
	
	@Test
	public void testDefaultParentNames(){
		List<String> parentNames = node.getParentNames();
		assertEquals(1, parentNames.size());
		assertEquals(FormatNode.NULL_NODE, parentNames.get(0));
	}
	
	@Test
	public void testDefaultChildNames(){
		List<String> childNames = node.getChildNames();
		assertEquals(1, childNames.size());
		assertEquals(FormatNode.NULL_NODE, childNames.get(0));
	}
	
	@Test
	public void testDefaultPrevSiblingNames(){
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(1, prevSiblingNames.size());
		assertEquals(FormatNode.NULL_NODE, prevSiblingNames.get(0));
	}
	
	@Test
	public void testDefaultNextSiblingNames(){
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(1, nextSiblingNames.size());
		assertEquals(FormatNode.NULL_NODE, nextSiblingNames.get(0));
	}
	
	@Test
	public void testSetTitleFormat() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testSetTitleFormat.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleFormat("<text>").dataRegex("(.*)")
				.level(0)
				.build();
		assertEquals(".*", node.getTitleRegex());
	}
	
	@Test
	public void testSetDataFormat() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testSetDataFormat.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataFormat("<text>")
				.level(0)
				.build();
		assertEquals(".*", node.getDataRegex());
	}
	
	@Test
	public void testSetParentNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testSetParentNames.log",
				Level.FINEST));
		List<String> parentNames = ListUtil.createList("derp", "yep");
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.parentNames(parentNames)
				.build();
		assertEquals(parentNames, node.getParentNames());
	}
	
	@Test
	public void testSetSingleParentName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testSetSingleParentName.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.parentName("derp")
				.build();
		List<String> parentNames = node.getParentNames();
		assertEquals(1, parentNames.size());
		assertEquals("derp", parentNames.get(0));
	}
	
	@Test
	public void testSetNullParentName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testSetNullParentName.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.parentName("derp")
				.nullParentName()
				.build();
		List<String> parentNames = node.getParentNames();
		assertEquals(2, parentNames.size());
		assertEquals("derp", parentNames.get(0));
		assertEquals(FormatNode.NULL_NODE, parentNames.get(1));
	}
	
	@Test
	public void testSetChildNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testSetChildNames.log",
				Level.FINEST));
		List<String> childNames = ListUtil.createList("derp", "yep");
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.childNames(childNames)
				.build();
		assertEquals(childNames, node.getChildNames());
	}
	
	@Test
	public void testSetSingleChildName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testSetSingleChildName.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.childName("derp")
				.build();
		List<String> childNames = node.getChildNames();
		assertEquals(1, childNames.size());
		assertEquals("derp", childNames.get(0));
	}
	
	@Test
	public void testSetNullChildName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testSetNullChildName.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.childName("derp")
				.nullChildName()
				.build();
		List<String> childNames = node.getChildNames();
		assertEquals(2, childNames.size());
		assertEquals("derp", childNames.get(0));
		assertEquals(FormatNode.NULL_NODE, childNames.get(1));
	}
	
	@Test
	public void testSetPrevSiblingNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetPrevSiblingNames.log",
				Level.FINEST));
		List<String> prevSiblingNames = ListUtil.createList("derp", "yep");
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.prevSiblingNames(prevSiblingNames)
				.build();
		assertEquals(prevSiblingNames, node.getPrevSiblingNames());
	}
	
	@Test
	public void testSetSinglePrevSiblingName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetSinglePrevSiblingName.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.prevSiblingName("derp")
				.build();
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(1, prevSiblingNames.size());
		assertEquals("derp", prevSiblingNames.get(0));
	}
	
	@Test
	public void testSetNullPrevSiblingName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetNullPrevSiblingName.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.prevSiblingName("derp")
				.nullPrevSiblingName()
				.build();
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(2, prevSiblingNames.size());
		assertEquals("derp", prevSiblingNames.get(0));
		assertEquals(FormatNode.NULL_NODE, prevSiblingNames.get(1));
	}
	
	@Test
	public void testSetNextSiblingNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetNextSiblingNames.log",
				Level.FINEST));
		List<String> nextSiblingNames = ListUtil.createList("derp", "yep");
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.nextSiblingNames(nextSiblingNames)
				.build();
		assertEquals(nextSiblingNames, node.getNextSiblingNames());
	}
	
	@Test
	public void testSetSingleNextSiblingName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetSingleNextSiblingName.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.nextSiblingName("derp")
				.build();
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(1, nextSiblingNames.size());
		assertEquals("derp", nextSiblingNames.get(0));
	}
	
	@Test
	public void testSetNullNextSiblingName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetNullNextSiblingName.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
				.level(0)
				.nextSiblingName("derp")
				.nullNextSiblingName()
				.build();
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(2, nextSiblingNames.size());
		assertEquals("derp", nextSiblingNames.get(0));
		assertEquals(FormatNode.NULL_NODE, nextSiblingNames.get(1));
	}
	
	@Test
	public void testMissingLogger(){
		try{
			node = FormatNode.builder()
					.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
					.level(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Must specify a Logger!", e.getMessage());
		}
	}
	
	@Test
	public void testMissingName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testMissingName.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.titleRegex("test(\\d)*").dataRegex("(.*)")
					.level(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Name can't be null!", e.getMessage());
		}
	}
	
	@Test
	public void testMissingTitleFormatAndRegex() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testMissingTitleFormatAndRegex.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.name("test").dataRegex("(.*)")
					.level(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Must specify either titleFormat or titleRegex!",
					e.getMessage());
		}
	}
	
	@Test
	public void testSpecifyBothTitleFormatAndRegex() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSpecifyBothTitleFormatAndRegex.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.name("test").titleFormat("<text>").titleRegex("test(\\d)*").dataRegex("(.*)")
					.level(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify both title format and title regex!",
					e.getMessage());
		}
	}
	
	@Test
	public void testMissingDataFormatAndRegex() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testMissingDataFormatAndRegex.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.name("test").titleRegex("test(\\d)*")
					.level(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Must specify either dataFormat or dataRegex!",
					e.getMessage());
		}
	}
	
	@Test
	public void testSpecifyBothDataFormatAndRegex() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSpecifyBothTitleFormatAndRegex.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.name("test").titleRegex("test(\\d)*").dataFormat("<text>").dataRegex("(.*)")
					.level(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify both data format and data regex!",
					e.getMessage());
		}
	}
	
	@Test
	public void testMissingLevel() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testMissingLevel.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.name("test").titleRegex("test(\\d)*").dataRegex("(.*)")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Must specify level!",
					e.getMessage());
		}
	}
	
	@Test
	public void testAllMissing(){
		try{
			node = FormatNode.builder().build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
							Failed to create FormatNode: Must specify a Logger!
							Name can't be null!
							Must specify either titleFormat or titleRegex!
							Must specify either dataFormat or dataRegex!
							Must specify level!""",
					e.getMessage());
		}
	}
	
	@Test
	public void testSetText() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetText.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.text("""
						Format:test
						  Regex:test(\\d)*
						  Regex:\\(.*)
						  Level:0
						  Parents:<null>
						  Children:<null>
						  PrevSiblings:<null>
						  NextSiblings:<null>""")
				.build();
		assertEquals("test", node.getName());
		assertEquals("test(\\d)*", node.getTitleRegex());
		assertEquals("(.*)", node.getDataRegex());
		assertEquals(0, node.getLevel());
		List<String> parentNames = node.getParentNames();
		assertEquals(1, parentNames.size());
		assertEquals("<null>", parentNames.get(0));
		List<String> childNames = node.getChildNames();
		assertEquals(1, childNames.size());
		assertEquals("<null>", childNames.get(0));
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(1, prevSiblingNames.size());
		assertEquals("<null>", prevSiblingNames.get(0));
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(1, nextSiblingNames.size());
		assertEquals("<null>", nextSiblingNames.get(0));
	}
	
	@Test
	public void testSetTextTitleFormat() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextTitleFormat.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.text("""
						Format:test
						  Format:<text>
						  Regex:\\(.*)
						  Level:0
						  Parents:<null>
						  Children:<null>
						  PrevSiblings:<null>
						  NextSiblings:<null>""")
				.build();
		assertEquals("test", node.getName());
		assertEquals(".*", node.getTitleRegex());
		assertEquals("(.*)", node.getDataRegex());
		assertEquals(0, node.getLevel());
		List<String> parentNames = node.getParentNames();
		assertEquals(1, parentNames.size());
		assertEquals("<null>", parentNames.get(0));
		List<String> childNames = node.getChildNames();
		assertEquals(1, childNames.size());
		assertEquals("<null>", childNames.get(0));
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(1, prevSiblingNames.size());
		assertEquals("<null>", prevSiblingNames.get(0));
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(1, nextSiblingNames.size());
		assertEquals("<null>", nextSiblingNames.get(0));
	}
	
	@Test
	public void testSetTextDataFormat() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextDataFormat.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.text("""
						Format:test
						  Regex:test(\\d)*
						  Format:<text>
						  Level:0
						  Parents:<null>
						  Children:<null>
						  PrevSiblings:<null>
						  NextSiblings:<null>""")
				.build();
		assertEquals("test", node.getName());
		assertEquals("test(\\d)*", node.getTitleRegex());
		assertEquals(".*", node.getDataRegex());
		assertEquals(0, node.getLevel());
		List<String> parentNames = node.getParentNames();
		assertEquals(1, parentNames.size());
		assertEquals("<null>", parentNames.get(0));
		List<String> childNames = node.getChildNames();
		assertEquals(1, childNames.size());
		assertEquals("<null>", childNames.get(0));
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(1, prevSiblingNames.size());
		assertEquals("<null>", prevSiblingNames.get(0));
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(1, nextSiblingNames.size());
		assertEquals("<null>", nextSiblingNames.get(0));
	}
	
	@Test
	public void testSetTextMultipleParentNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextMultipleParentNames.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.text("""
						Format:test
						  Regex:test(\\d)*
						  Regex:\\(.*)
						  Level:0
						  Parents:<null>,test,derp
						  Children:<null>
						  PrevSiblings:<null>
						  NextSiblings:<null>""")
				.build();
		assertEquals("test", node.getName());
		assertEquals("test(\\d)*", node.getTitleRegex());
		assertEquals("(.*)", node.getDataRegex());
		assertEquals(0, node.getLevel());
		List<String> parentNames = node.getParentNames();
		assertEquals(3, parentNames.size());
		assertEquals("<null>", parentNames.get(0));
		assertEquals("test", parentNames.get(1));
		assertEquals("derp", parentNames.get(2));
		List<String> childNames = node.getChildNames();
		assertEquals(1, childNames.size());
		assertEquals("<null>", childNames.get(0));
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(1, prevSiblingNames.size());
		assertEquals("<null>", prevSiblingNames.get(0));
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(1, nextSiblingNames.size());
		assertEquals("<null>", nextSiblingNames.get(0));
	}
	
	@Test
	public void testSetTextMultipleChildNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextMultipleChildNames.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.text("""
						Format:test
						  Regex:test(\\d)*
						  Regex:\\(.*)
						  Level:0
						  Parents:<null>
						  Children:<null>,test,derp
						  PrevSiblings:<null>
						  NextSiblings:<null>""")
				.build();
		assertEquals("test", node.getName());
		assertEquals("test(\\d)*", node.getTitleRegex());
		assertEquals("(.*)", node.getDataRegex());
		assertEquals(0, node.getLevel());
		List<String> parentNames = node.getParentNames();
		assertEquals(1, parentNames.size());
		assertEquals("<null>", parentNames.get(0));
		List<String> childNames = node.getChildNames();
		assertEquals(3, childNames.size());
		assertEquals("<null>", childNames.get(0));
		assertEquals("test", childNames.get(1));
		assertEquals("derp", childNames.get(2));
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(1, prevSiblingNames.size());
		assertEquals("<null>", prevSiblingNames.get(0));
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(1, nextSiblingNames.size());
		assertEquals("<null>", nextSiblingNames.get(0));
	}
	
	@Test
	public void testSetTextMultiplePrevSiblingNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextMultiplePrevSiblingNames.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.text("""
						Format:test
						  Regex:test(\\d)*
						  Regex:\\(.*)
						  Level:0
						  Parents:<null>
						  Children:<null>
						  PrevSiblings:<null>,test,derp
						  NextSiblings:<null>""")
				.build();
		assertEquals("test", node.getName());
		assertEquals("test(\\d)*", node.getTitleRegex());
		assertEquals("(.*)", node.getDataRegex());
		assertEquals(0, node.getLevel());
		List<String> parentNames = node.getParentNames();
		assertEquals(1, parentNames.size());
		assertEquals("<null>", parentNames.get(0));
		List<String> childNames = node.getChildNames();
		assertEquals(1, childNames.size());
		assertEquals("<null>", childNames.get(0));
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(3, prevSiblingNames.size());
		assertEquals("<null>", prevSiblingNames.get(0));
		assertEquals("test", prevSiblingNames.get(1));
		assertEquals("derp", prevSiblingNames.get(2));
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(1, nextSiblingNames.size());
		assertEquals("<null>", nextSiblingNames.get(0));
	}
	
	@Test
	public void testSetTextMultipleNextSiblingNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextMultipleNextSiblingNames.log",
				Level.FINEST));
		node = FormatNode.builder()
				.logger(logger)
				.text("""
						Format:test
						  Regex:test(\\d)*
						  Regex:\\(.*)
						  Level:0
						  Parents:<null>
						  Children:<null>
						  PrevSiblings:<null>
						  NextSiblings:<null>,test,derp""")
				.build();
		assertEquals("test", node.getName());
		assertEquals("test(\\d)*", node.getTitleRegex());
		assertEquals("(.*)", node.getDataRegex());
		assertEquals(0, node.getLevel());
		List<String> parentNames = node.getParentNames();
		assertEquals(1, parentNames.size());
		assertEquals("<null>", parentNames.get(0));
		List<String> childNames = node.getChildNames();
		assertEquals(1, childNames.size());
		assertEquals("<null>", childNames.get(0));
		List<String> prevSiblingNames = node.getPrevSiblingNames();
		assertEquals(1, prevSiblingNames.size());
		assertEquals("<null>", prevSiblingNames.get(0));
		List<String> nextSiblingNames = node.getNextSiblingNames();
		assertEquals(3, nextSiblingNames.size());
		assertEquals("<null>", nextSiblingNames.get(0));
		assertEquals("test", nextSiblingNames.get(1));
		assertEquals("derp", nextSiblingNames.get(2));
	}
	
	@Test
	public void testSetTextUnknownTitleFormat() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextUnknownTitleFormat.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
						Format:test
						  Garbage:some hot garbage
						  Regex:(.*)
						  Level:0
						  Parents:<null>
						  Children:<null>
						  PrevSiblings:<null>
						  NextSiblings:<null>""")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Unknown title format type: Garbage", e.getMessage());
		}
	}
	
	@Test
	public void testSetTextUnknownDataFormat() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextUnknownDataFormat.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
						Format:test
						  Regex:test(\\d)*
						  Garbage:some hot garbage
						  Level:0
						  Parents:<null>
						  Children:<null>
						  PrevSiblings:<null>
						  NextSiblings:<null>""")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Unknown data format type: Garbage", e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndName() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndName.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.name("test")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and name!", e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndTitleFormat() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndTitleFormat.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.titleFormat("<text>")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and title format!", e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndTitleRegex() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndTitleRegex.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.titleRegex("test(\\d)*")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and title regex!", e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndDataFormat() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndDataFormat.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.dataFormat("<text>")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and data format!", e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndDataRegex() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndDataRegex.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.dataRegex("(.*)")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and data regex!", e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndLevel() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndLevel.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.level(0)
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and level!", e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndParentNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndParentNames.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.parentName("test")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and parent names!", e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndChildNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndChildNames.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.childName("test")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and child names!", e.getMessage());
		}
	}
	
	@Test
	public void setTextAndPrevSiblingNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndPrevSiblingNames.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.prevSiblingName("test")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and previous sibling names!",
					e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndNextSiblingNames() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndNextSiblingNames.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.nextSiblingName("test")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("Failed to create FormatNode: Can't specify text and next sibling names!",
					e.getMessage());
		}
	}
	
	@Test
	public void testSetTextAndEverything() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testSetTextAndEverything.log",
				Level.FINEST));
		try{
			node = FormatNode.builder()
					.logger(logger)
					.text("""
							Format:test
							  Regex:test(\\d)*
							  Regex:\\(.*)
							  Level:0
							  Parents:<null>
							  Children:<null>
							  PrevSiblings:<null>
							  NextSiblings:<null>""")
					.name("test")
					.titleFormat("<text>").titleRegex("test(\\d)*")
					.dataFormat("<text>").dataRegex("(.*)")
					.level(0)
					.parentName("test").childName("test")
					.prevSiblingName("test").nextSiblingName("test")
					.build();
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("""
					Failed to create FormatNode: Can't specify text and name!
					Can't specify text and title format!
					Can't specify text and title regex!
					Can't specify text and data format!
					Can't specify text and data regex!
					Can't specify text and level!
					Can't specify text and parent names!
					Can't specify text and child names!
					Can't specify text and previous sibling names!
					Can't specify text and next sibling names!""", e.getMessage());
		}
	}
	
	@Test
	public void testToString(){
		assertEquals("""
				Format:test
				  Regex:test(\\d)*
				  Regex:(.*)
				  Level:0
				  Parents:<null>
				  Children:<null>
				  PrevSiblings:<null>
				  NextSiblings:<null>""", node.toString());
	}
}
