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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

public class FileFormatTest{
	private static FileFormatSchema schemaV1;
	
	private static class TestFileFormat extends FileFormat{
		
		public TestFileFormat(EasyLogger logger){
			super(logger, "Test Format");
		}
		
		@Override
		protected Map<String, FileFormatSchema> createSchemas(EasyLogger logger){
			schemaV1 =
					new FileFormatSchema("v1", 1, "test", ListUtil.createList(
							FormatNode.builder()
									.logger(logger)
									.name("content")
									.titleRegex("content").dataRegex(".*").level(0)
									.prevSiblingName(TadFormatNodeHeader.HEAD_NAME)
									.build()));
			return MapUtil.createMap(Pair.of("v1", schemaV1));
		}
		
		@Override
		public Node updateFile(Node oldFile, String oldVersion, String newVersion){
			return null;
		}
	}
	
	private FileFormat format;
	
	@BeforeEach
	public void setup() throws IOException{
		format = new TestFileFormat(new EasyLogger(
				LoggerUtil.createFileLogger("target/logs/FileFormatTest/setup.log", Level.FINEST)));
	}
	
	@Test
	public void testGetName(){
		assertEquals("Test Format", format.getName());
	}
	
	@Test
	public void testGetSchema(){
		assertEquals(schemaV1, format.getSchema("v1"));
	}
	
	@Test
	public void testLoadFile() throws IOException{
		EasyLogger logger = new EasyLogger(
				LoggerUtil.createFileLogger("target/logs/FileFormatTest/testLoadFile.log", Level.FINEST));
		Node headNode = format.loadFile(logger, "junit-resource/FileFormatTest.test");
		assertEquals("content", headNode.getTitle());
		assertEquals("derp", headNode.getData());
		assertEquals(0, headNode.getLevel());
		assertNull(headNode.getParent());
		assertNull(headNode.getChild());
		assertNotNull(headNode.getPrevSibling());
		assertNull(headNode.getNextSibling());
	}
	
	@Test
	public void testLoadFileBadFormat() throws IOException{
		try{
			EasyLogger logger = new EasyLogger(
					LoggerUtil.createFileLogger("target/logs/FileFormatTest/testLoadFileBadFormat.log",
							Level.FINEST));
			format.loadFile(logger, "junit-resource/FileFormatBadTest.test");
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The file doesn't match the expected file format!", e.getMessage());
		}
	}
	
	@Test
	public void testSaveFile() throws IOException{
		EasyLogger logger = new EasyLogger(
				LoggerUtil.createFileLogger("target/logs/FileFormatTest/testSaveFile.log", Level.FINEST));
		Node headNode = Node.builder()
				.title("content").data("derp").level(0)
				.build();
		format.saveFile(logger, "target/test/FileFormatTest.test", headNode, schemaV1);
	}
	
	@Test
	public void testSaveFileBadNodes() throws IOException{
		try{
			EasyLogger logger = new EasyLogger(
					LoggerUtil.createFileLogger("target/logs/FileFormatTest/testSaveFileBadNodes.log",
							Level.FINEST));
			Node headNode = Node.builder()
					.title("derp").data("content").level(0)
					.build();
			format.saveFile(logger, "target/test/FileFormatBadTest.test", headNode, schemaV1);
			fail();
		}catch(IllegalArgumentException e){
			assertEquals("The given Nodes do not match the format!", e.getMessage());
		}
	}
}
