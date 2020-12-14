package com.github.tadukoo.parsing.fileformat;

import java.io.File;
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerUtil{
	
	public static Logger setupLogger(String subfolder, String file) throws SecurityException, IOException{
		String filepath = "logs/junit/TFileFormat/" + subfolder + "/";
		new File(filepath).mkdirs();
		FileHandler fh = new FileHandler(filepath + file + ".log");
		SimpleFormatter formatter = new SimpleFormatter();
		fh.setFormatter(formatter);
		Logger logger = Logger.getLogger(file);
		logger.setLevel(Level.FINEST);
		logger.addHandler(fh);
		return logger;
	}
}
