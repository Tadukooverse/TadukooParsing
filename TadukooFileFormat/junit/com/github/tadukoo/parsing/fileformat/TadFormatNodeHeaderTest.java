package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.util.ListUtil;
import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;
import com.github.tadukoo.util.map.MapUtil;
import com.github.tadukoo.util.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TadFormatNodeHeaderTest{
	private static final String logFolder = "target/logs/TadFormatNodeHeaderTest/";
	
	private FileFormat format;
	private static FileFormatSchema schemaV1;
	
	private static final String formatName = "Test Format";
	private static final String schemaVersionString = "v1";
	private static final int schemaVersionNum = 1;
	
	private static class TestFileFormat extends FileFormat{
		
		public TestFileFormat(EasyLogger logger){
			super(logger, formatName);
		}
		
		@Override
		protected Map<String, FileFormatSchema> createSchemas(EasyLogger logger){
			schemaV1 =
					new FileFormatSchema(schemaVersionString, schemaVersionNum, "test", ListUtil.createList(
							FormatNode.builder()
									.logger(logger)
									.name("content")
									.titleRegex("content").dataRegex(".*").level(0)
									.prevSiblingName(TadFormatNodeHeader.HEAD_NAME)
									.build()));
			return MapUtil.createMap(Pair.of(schemaVersionString, schemaV1));
		}
		
		@Override
		public Node updateFile(Node oldFile, String oldVersion, String newVersion){
			return null;
		}
	}
	
	@BeforeEach
	public void setup() throws IOException{
		format = new TestFileFormat(new EasyLogger(
				LoggerUtil.createFileLogger(logFolder + "setup.log", Level.FINEST)));
	}
	
	private Node createTadFormatNodeHeader(String headName, String headData,
	                                       String tadVersionName, String tadVersionData,
	                                       String formatName, String formatData,
	                                       String schemaName, String schemaData,
	                                       String versionStringName, String versionStringData,
	                                       String versionNumName, String versionNumData){
		// Create the head Tad Format Node
		Node header = Node.builder()
				.title(headName).data(headData)
				.build();
		
		// Create the Tad Format Version Node
		Node tadVersionNode = Node.builder()
				.title(tadVersionName).data(String.valueOf(tadVersionData)).level(1)
				.parent(header)
				.build();
		header.setChild(tadVersionNode);
		
		// Create the File Format Node
		Node fileFormatNode = Node.builder()
				.title(formatName).data(formatData).level(1)
				.prevSibling(tadVersionNode)
				.build();
		tadVersionNode.setNextSibling(fileFormatNode);
		
		// Create the File Format Schema Node
		Node schemaNode = Node.builder()
				.title(schemaName).data(schemaData).level(2)
				.parent(fileFormatNode)
				.build();
		fileFormatNode.setChild(schemaNode);
		
		// Create the File Format Schema Version Node
		Node versionStringNode = Node.builder()
				.title(versionStringName).data(versionStringData).level(3)
				.parent(schemaNode)
				.build();
		schemaNode.setChild(versionStringNode);
		
		// Create the File Format Schema Version Number Node
		Node versionNumNode = Node.builder()
				.title(versionNumName).data(String.valueOf(versionNumData)).level(3)
				.prevSibling(versionStringNode)
				.build();
		versionStringNode.setNextSibling(versionNumNode);
		
		// Create the content Node
		Node content = Node.builder()
				.title("content").data("derp").level(0)
				.prevSibling(header).build();
		header.setNextSibling(content);
		
		return header;
	}
	
	@Test
	public void testVersion(){
		assertEquals(1, TadFormatNodeHeader.TAD_FORMAT_VERSION_NUM);
	}
	
	@Test
	public void testHeaderName(){
		assertEquals("TadFormat", TadFormatNodeHeader.HEAD_NAME);
	}
	
	@Test
	public void testCreateHeader(){
		Node header = TadFormatNodeHeader.createHeader(format, schemaV1);
		
		// Check head node
		assertEquals(TadFormatNodeHeader.HEAD_NAME, header.getTitle());
		assertEquals("", header.getData());
		assertEquals(0, header.getLevel());
		assertNull(header.getParent());
		assertNull(header.getPrevSibling());
		assertNull(header.getNextSibling());
		assertNotNull(header.getChild());
		
		// Check Version Number Node
		Node versionNode = header.getChild();
		assertEquals("TadFormat Version Num", versionNode.getTitle());
		assertEquals(String.valueOf(TadFormatNodeHeader.TAD_FORMAT_VERSION_NUM), versionNode.getData());
		assertEquals(1, versionNode.getLevel());
		assertEquals(header, versionNode.getParent());
		assertNull(versionNode.getChild());
		assertNull(versionNode.getPrevSibling());
		assertNotNull(versionNode.getNextSibling());
		
		// Check File Format Node
		Node formatNode = versionNode.getNextSibling();
		assertEquals("File Format", formatNode.getTitle());
		assertEquals(formatName, formatNode.getData());
		assertEquals(1, formatNode.getLevel());
		assertNull(formatNode.getParent());
		assertNotNull(formatNode.getChild());
		assertEquals(versionNode, formatNode.getPrevSibling());
		assertNull(formatNode.getNextSibling());
		
		// Check File Format Schema Node
		Node schemaNode = formatNode.getChild();
		assertEquals("Schema", schemaNode.getTitle());
		assertEquals("", schemaNode.getData());
		assertEquals(2, schemaNode.getLevel());
		assertEquals(formatNode, schemaNode.getParent());
		assertNotNull(schemaNode.getChild());
		assertNull(schemaNode.getPrevSibling());
		assertNull(schemaNode.getNextSibling());
		
		// Check File Format Schema Version String Node
		Node versionStringNode = schemaNode.getChild();
		assertEquals("Version", versionStringNode.getTitle());
		assertEquals(schemaVersionString, versionStringNode.getData());
		assertEquals(3, versionStringNode.getLevel());
		assertEquals(schemaNode, versionStringNode.getParent());
		assertNull(versionStringNode.getChild());
		assertNull(versionStringNode.getPrevSibling());
		assertNotNull(versionStringNode.getNextSibling());
		
		// Check File Format Schema Version Num Node
		Node versionNumNode = versionStringNode.getNextSibling();
		assertEquals("Version Num", versionNumNode.getTitle());
		assertEquals(String.valueOf(schemaVersionNum), versionNumNode.getData());
		assertEquals(3, versionNumNode.getLevel());
		assertNull(versionNumNode.getParent());
		assertNull(versionNumNode.getChild());
		assertEquals(versionStringNode, versionNumNode.getPrevSibling());
		assertNull(versionNumNode.getNextSibling());
	}
	
	@Test
	public void testGetSchemaVersionString(){
		Node header = TadFormatNodeHeader.createHeader(format, schemaV1);
		assertEquals(schemaVersionString, TadFormatNodeHeader.getSchemaVersionString(header));
	}
	
	@Test
	public void testVerifyTadFormatNode() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				logFolder + "TestVerifyTadFormatNode.log", Level.FINEST));
		Node header = TadFormatNodeHeader.createHeader(format, schemaV1);
		Node content = Node.builder()
				.title("content").data("derp").level(0)
				.prevSibling(header).build();
		header.setNextSibling(content);
		assertTrue(TadFormatNodeHeader.verifyTadFormatNode(logger, header, format, schemaV1));
	}
	
	@Test
	public void testVerifyTadFormatNodeNoContent() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				logFolder + "TestVerifyTadFormatNodeNoContent.log", Level.FINEST));
		Node header = TadFormatNodeHeader.createHeader(format, schemaV1);
		assertFalse(TadFormatNodeHeader.verifyTadFormatNode(logger, header, format, schemaV1));
	}
	
	@Test
	public void testVerifyTadFormatNodeBadHeadNode() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				logFolder + "TestVerifyTadFormatNodeBadHeadNode.log", Level.FINEST));
		Node header = createTadFormatNodeHeader("wrong title", "dumb data",
				"TadFormat Version Num", "1",
				"File Format", format.getName(), "Schema", "",
				"Version", schemaVersionString,
				"Version Num", String.valueOf(schemaVersionNum));
		assertFalse(TadFormatNodeHeader.verifyTadFormatNode(logger, header, format, schemaV1));
	}
	
	@Test
	public void testVerifyTadFormatNodeBadVersionNode() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				logFolder + "TestVerifyTadFormatNodeBadVersionNode.log", Level.FINEST));
		Node header = createTadFormatNodeHeader(TadFormatNodeHeader.HEAD_NAME, "",
				"version", "d",
				"File Format", format.getName(), "Schema", "",
				"Version", schemaVersionString,
				"Version Num", String.valueOf(schemaVersionNum));
		assertFalse(TadFormatNodeHeader.verifyTadFormatNode(logger, header, format, schemaV1));
	}
	
	@Test
	public void testVerifyTadFormatNodeBadFormatNode() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				logFolder + "TestVerifyTadFormatNodeBadFormatNode.log", Level.FINEST));
		Node header = createTadFormatNodeHeader(TadFormatNodeHeader.HEAD_NAME, "",
				"TadFormat Version Num", "1",
				"format", "as", "Schema", "",
				"Version", schemaVersionString,
				"Version Num", String.valueOf(schemaVersionNum));
		assertFalse(TadFormatNodeHeader.verifyTadFormatNode(logger, header, format, schemaV1));
	}
	
	@Test
	public void testVerifyTadFormatNodeBadSchemaNode() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				logFolder + "TestVerifyTadFormatNodeBadSchemaNode.log", Level.FINEST));
		Node header = createTadFormatNodeHeader(TadFormatNodeHeader.HEAD_NAME, "",
				"TadFormat Version Num", "1",
				"File Format", format.getName(), "scheme", "derp",
				"Version", schemaVersionString,
				"Version Num", String.valueOf(schemaVersionNum));
		assertFalse(TadFormatNodeHeader.verifyTadFormatNode(logger, header, format, schemaV1));
	}
	
	@Test
	public void testVerifyTadFormatNodeBadVersionStringNode() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				logFolder + "TestVerifyTadFormatNodeBadVersionStringNode.log", Level.FINEST));
		Node header = createTadFormatNodeHeader(TadFormatNodeHeader.HEAD_NAME, "",
				"TadFormat Version Num", "1",
				"File Format", format.getName(), "Schema", "",
				"Version String", "version string",
				"Version Num", String.valueOf(schemaVersionNum));
		assertFalse(TadFormatNodeHeader.verifyTadFormatNode(logger, header, format, schemaV1));
	}
	
	@Test
	public void testVerifyTadFormatNodeBadVersionNumNode() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				logFolder + "TestVerifyTadFormatNodeBadVersionNumNode.log", Level.FINEST));
		Node header = createTadFormatNodeHeader(TadFormatNodeHeader.HEAD_NAME, "",
				"TadFormat Version Num", "1",
				"File Format", format.getName(), "Schema", "",
				"Version", schemaVersionString,
				"Version Number", "num");
		assertFalse(TadFormatNodeHeader.verifyTadFormatNode(logger, header, format, schemaV1));
	}
}
