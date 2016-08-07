package com.wpruszak.blitzch.lib.interfaces;

import java.io.File;
import java.io.IOException;

import com.wpruszak.blitzch.controller.IOController;
import com.wpruszak.blitzch.lib.FileSaverImpl;

/**
 * The main file saving interface for
 * {@link IOController} to provide an easy interchangeable
 * way of saving files.
 * 
 * <p>FileSaver provides: 
 * <ul>
 * <li>Saving {@link String} at the end of the {@link File} without erasing prrevious
 * file content</li>
 * <li>Saving {@link String} to the {@link File} and erasing all of the previous file
 * content</li>
 * </ul>
 * 
 * @author setzo
 * @since 1.0
 * @see IOController
 * @see FileLoader
 * @see FileSaverImpl
 */
public interface FileSaver {

	/**
	 * Save given {@link String} into given {@link File} appending it to the end of the file.
	 * <p>This method will not erase the previous file contents. It will add given string at
	 * the end of the file.
	 * 
	 * @param file to append given string to
	 * @param newContent string to append to given file
	 * @throws IOException if is I/O corrupted
	 */
	void saveStringToFileAppend(File file, String newContent) throws IOException;
	
	/**
	 * Save given {@link String} into given {@link File} overwritting the file and leaving
	 * the string as the only file content.
	 * <p>This method will erase the previous file contents.
	 * 
	 * @param file to overwrite with given string
	 * @param newContent string to be overwritten as a new file
	 * @throws IOException if I/O is corrupted
	 */
	public void saveStringToFileOverwrite(File file, String newContent) throws IOException;
}
