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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileFormatSchemaVerificationTest{
	private static final String subfolder = "target/logs/FileFormatSchemaVerificationTest/";
	private static final String resourceFolder = "junit-resource/";
	private String filepath;
	private FileFormat fileFormat;
	
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
	
	@BeforeEach
	public void setup() throws SecurityException, IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "setup.log", Level.FINEST));
		fileFormat = new TestFileFormat(logger);
		filepath = resourceFolder + "FileFormatTest.test";
	}
	
	@Test
	public void testVerifyFileFormat() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testVerifyFileFormat.log",
				Level.FINEST));
		assertTrue(FileFormatSchemaVerification.verifyFileFormat(logger, fileFormat, schemaV1, filepath));
	}
	
	@Test
	public void testVerifyFileFormatBadExtension() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testVerifyFileFormatBadExtension.log",
				Level.FINEST));
		assertFalse(FileFormatSchemaVerification.verifyFileFormat(logger, fileFormat, schemaV1,
				resourceFolder + "FileFormatTestBadExtension.test2"));
	}
	
	@Test
	public void testVerifyFileFormatBadHeader() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testVerifyFileFormatBadHeader.log",
				Level.FINEST));
		assertFalse(FileFormatSchemaVerification.verifyFileFormat(logger, fileFormat, schemaV1,
				resourceFolder + "FileFormatTestBadHeader.test"));
	}
	
	@Test
	public void testVerifyFileFormatBadChild() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testVerifyFileFormatBadChild.log",
				Level.FINEST));
		assertFalse(FileFormatSchemaVerification.verifyFileFormat(logger, fileFormat, schemaV1,
				resourceFolder + "FileFormatTestBadChild.test"));
	}
	
	@Test
	public void testVerifyFileFormatBadSibling() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testVerifyFileFormatBadSibling.log",
				Level.FINEST));
		assertFalse(FileFormatSchemaVerification.verifyFileFormat(logger, fileFormat, schemaV1,
				resourceFolder + "FileFormatTestBadSibling.test"));
	}
}
