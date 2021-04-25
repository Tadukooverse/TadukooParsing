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

public class FileFormatSchemaTest{
	private FileFormatSchema schema;
	private final String versionString = "original";
	private final int versionNum = 1;
	private final String fileExtension = "test";
	private List<FormatNode> formatNodes;
	
	@BeforeEach
	public void setup() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				"target/logs/FileFormatSchemaTest/setup.log",
				Level.FINEST));
		formatNodes = ListUtil.createList(
				FormatNode.builder()
						.logger(logger)
						.name("test")
						.titleRegex(".*").dataRegex(".*").level(0)
						.build(),
				FormatNode.builder()
						.logger(logger)
						.name("test2")
						.titleRegex(".*").dataRegex(".*").level(1)
						.build());
		schema = new FileFormatSchema(versionString, versionNum, fileExtension, formatNodes);
	}
	
	@Test
	public void testGetVersionString(){
		assertEquals(versionString, schema.getVersionString());
	}
	
	@Test
	public void testGetVersionNum(){
		assertEquals(versionNum, schema.getVersionNum());
	}
	
	@Test
	public void testGetFileExtension(){
		assertEquals(fileExtension, schema.getFileExtension());
	}
	
	@Test
	public void testGetFormatNodes(){
		assertEquals(formatNodes, schema.getFormatNodes());
	}
}
