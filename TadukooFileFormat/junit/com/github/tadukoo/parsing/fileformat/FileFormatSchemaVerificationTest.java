package com.github.tadukoo.parsing.fileformat;

import com.github.tadukoo.parsing.fileformat.ghdr.GHDRFileFormat;
import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileFormatSchemaVerificationTest{
	private static final String subfolder = "target/logs/FileFormatSchemaVerificationTest/";
	private String filepath;
	private FileFormat fileFormat;
	
	@BeforeEach
	public void setup() throws SecurityException, IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "setup.log", Level.FINEST));
		filepath = "1959.ghdr";
		fileFormat = new GHDRFileFormat(logger);
	}
	
	@Test
	public void testVerifyFileFormat() throws SecurityException, IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "testVerifyFileFormat.log",
				Level.FINEST));
		assertTrue(FileFormatSchemaVerification.verifyFileFormat(logger, fileFormat, fileFormat.getSchema("Version 1.0"), filepath));
	}
}
