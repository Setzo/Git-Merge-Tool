package com.wpruszak.blitzch.lib;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.wpruszak.blitzch.controller.IOController;
import com.wpruszak.blitzch.lib.interfaces.FileSaver;

/**
 * Default {@link FileSaver} implementation, it provides implementation of all the methods 
 * that FileSaver requires.
 * 
 * @author setzo
 * @since 1.0
 * @see IOController
 * @see FileSaver
 * @see FileLoaderImpl
 */
public class FileSaverImpl implements FileSaver {

	/**
	 * @see FileSaver
	 */
	public void saveStringToFileAppend(File file, String newContent) throws IOException {
		
		FileUtils.writeStringToFile(file, newContent, true);
	}
	
	/**
	 * @see FileSaver
	 */
	public void saveStringToFileOverwrite(File file, String newContent) throws IOException {
		
		FileUtils.writeStringToFile(file, newContent, false);
	}
}
