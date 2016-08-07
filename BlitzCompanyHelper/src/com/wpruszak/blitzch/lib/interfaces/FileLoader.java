package com.wpruszak.blitzch.lib.interfaces;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.controller.IOController;
import com.wpruszak.blitzch.lib.FileLoaderImpl;

/**
 * The main file loading interface for
 * {@link IOController} to provide an easy interchangeable
 * way of loading files.
 * 
 * <p>FileLoader provides: 
 * <ul>
 * <li>Loading file data from absolute path</li>
 * <li>Loading file from absolute path</li>
 * <li>Loading file data from {@link File} object</li>
 * <li>Loading a {@link List} of {@link FileBean} objects from an array of File objects</li>
 * </ul>
 *
 * @author setzo
 * @since 1.0
 * @see IOController
 * @see FileSaver
 * @see FileLoaderImpl
 */
public interface FileLoader {

	/**
	 * Load file info into {@link FileBean} object.
	 * 
	 * @param path absolute path to file
	 * @return {@link FileBean} describing file
	 * @throws IOException if I/O is corrupted
	 * @throws FileNotFoundException if file doesnt exist
	 */
	FileBean loadFileAbsolutePath(String path) throws IOException, FileNotFoundException;

	/**
	 * Get {@link File} object from given path.
	 * 
	 * @param absolutePath absolute path to file
	 * @return {@link File} described file
	 */
	File getFile(String absolutePath);
	
	/**
	 * Load file information into {@link FileBean} given
	 * {@link File}.
	 * 
	 * @param file to load {@link FileBean} object from
	 * @return {@link FileBean} describing given file
	 * @throws IOException if I/O is corrupted
	 * @throws FileNotFoundException if file doesnt exist
	 */
	FileBean loadFile(File file) throws IOException, FileNotFoundException;

	/**
	 * Recursive load all files of a directory into {@link List}
	 * of {@link FileBean} objects, that will contain all of the files data.
	 * 
	 * @param files to load data from into {@link FileBean}
	 * beans
	 * @param fileBeanList list of {@link FileBean} objects
	 * containing information about our files, this parameter can and should be initially null,
	 * cause the list is created after first iteration of the method anyway.
	 * @return {@link List} of {@link FileBean} objects
	 * @throws IOException if I/O is corrupted
	 */
	List<FileBean> loadDirectory(File[] files, List<FileBean> fileBeanList) throws IOException;
	
	/**
	 * Converts {@link File} object that is directory into {@link FileBean} object describing it.
	 * 
	 * @param file directory to convert into FileBean object
	 * @return FileBean representation of a directory given
	 */
	FileBean loadFileBeanAsDirectory(File file);
}
