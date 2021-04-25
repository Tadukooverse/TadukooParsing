package com.github.tadukoo.parsing;

import com.github.tadukoo.util.LoggerUtil;
import com.github.tadukoo.util.logger.EasyLogger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.logging.Level;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TadFormatRegexConverterTest{
	private static final String subfolder = "target/logs/TFormatRegexConverterTest/";
	private String formatString;
	private String regexString;
	
	@BeforeEach
	public void setup(){
		formatString = "<filename>/<fileTitle>.<fileExtension>/<text>/<imagefile>"
				+ "[$<#>,<#>,<#>,<#>]<boolean><Boolean>";
		regexString = "<filename>/<fileTitle>\\.<fileExtension>/.*/.*\\.jpg"
				+ "(\\$(\\d)*,(\\d)*,(\\d)*,(\\d)*)?(true|false)(true|false|null)";
	}
	
	@Test
	public void noChangeTest() throws IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(subfolder + "noChangeTest.log",
				Level.FINEST));
		String theString = "simple string";
		assertEquals(theString, TadFormatRegexConverter.convertTadFormatToRegex(logger, theString));
		assertEquals(theString, TadFormatRegexConverter.convertRegexToTadFormat(logger, theString));
	}
	
	@Test
	public void testConvertFromTFormatToRegexAndBack() throws SecurityException, IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testConvertFromTFormatToRegexAndBack.log",
				Level.FINEST));
		String regexTest = TadFormatRegexConverter.convertTadFormatToRegex(logger, formatString);
		assertEquals(regexString, regexTest);
		String tFormatTest = TadFormatRegexConverter.convertRegexToTadFormat(logger, regexTest);
		assertEquals(formatString, tFormatTest);
	}
	
	@Test
	public void testConvertFromRegexToTFormatAndBack() throws SecurityException, IOException{
		EasyLogger logger = new EasyLogger(LoggerUtil.createFileLogger(
				subfolder + "testConvertFromRegexToTFormatAndBack.log",
				Level.FINEST));
		String tFormatTest = TadFormatRegexConverter.convertRegexToTadFormat(logger, regexString);
		assertEquals(formatString, tFormatTest);
		String regexTest = TadFormatRegexConverter.convertTadFormatToRegex(logger, tFormatTest);
		assertEquals(regexString, regexTest);
	}
}
