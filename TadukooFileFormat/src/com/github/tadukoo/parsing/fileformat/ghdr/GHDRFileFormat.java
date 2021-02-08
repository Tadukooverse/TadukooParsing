package com.github.tadukoo.parsing.fileformat.ghdr;

import com.github.tadukoo.parsing.fileformat.*;
import com.github.tadukoo.util.logger.EasyLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GHDRFileFormat extends FileFormat{
	
	public GHDRFileFormat(EasyLogger logger){
		super(logger, "GHDR");
	}
	
	@Override
	protected Map<String, FileFormatSchema> createSchemas(EasyLogger logger){
		Map<String, FileFormatSchema> schemas = new HashMap<>();
		// Version 1.0 File Format Schema
		String version1 = "Version 1.0";
		ArrayList<FormatNode> v1Nodes = new ArrayList<>();
		v1Nodes.add(FormatNode.builder()
								.logger(logger)
								.name("head")
								.titleFormat("<fileTitle>")
								.dataFormat("")
								.level(0)
								.childName("title")
								.prevSiblingName(TadFormatNodeHeader.HEAD_NAME)
								.build());
		v1Nodes.add(FormatNode.builder()
								.logger(logger)
								.name("title")
								.titleFormat("<text>")
								.dataFormat("<imagefile>[$<#>,<#>,<#>,<#>]")
								.level(1)
								.parentName("head")
								.nullParentName()
								.prevSiblingName("title")
								.nullPrevSiblingName()
								.nextSiblingName("title")
								.nullNextSiblingName()
								.build());
		FileFormatSchema v1 = new FileFormatSchema(version1, 1, "ghdr", v1Nodes);
		schemas.put(version1, v1);
		return schemas;
	}
	
	@Override
	public Node updateFile(Node oldFile, String oldVersion, String newVersion){
		return oldFile;
	}
}
