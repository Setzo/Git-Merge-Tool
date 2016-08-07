package com.wpruszak.blitzch.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.lib.FileLoaderImpl;
import com.wpruszak.blitzch.lib.FileSaverImpl;
import com.wpruszak.blitzch.lib.interfaces.FileLoader;
import com.wpruszak.blitzch.lib.interfaces.FileSaver;

/**
 * Input / Output controller implementation. 
 * Merges all of the on file operations in one class.
 * 
 * <p>The controller itself consist of two main interfaces implementations
 * {@link FileLoader} and {@link FileSaver}. 
 * 
 * <p>The basic implementation of these interfaces are :
 * {@link FileLoaderImpl} and {@link FileSaverImpl}.
 *
 * @author setzo
 * @since 1.0
 * @see FileLoader
 * @see FileSaver
 * @see FileLoaderImpl
 * @see FileSaverImpl
 */
public class IOController {

	/**
	 * Default file loader.
	 * 
	 * @see FileLoader
	 * @see FileLoaderImpl
	 */
	private FileLoader fileLoader;
	
	/**
	 * Default file saver.
	 * 
	 * @see FileSaver
	 * @see FileSaverImpl
	 */
	private FileSaver fileSaver;
	
	/**
	 * Basic constructor.
	 * 
	 * @param fileLoader {@link FileLoader} implementation
	 * @param fileSaver {@link FileSaver} implementation
	 */
	@Autowired
	public IOController(FileLoader fileLoader,
			FileSaver fileSaver) {
		
		this.fileLoader = fileLoader;
		this.fileSaver = fileSaver;
	}
	
	/**
	 * Load file info into {@link FileBean} object.
	 * 
	 * @param path absolute path to file
	 * @return {@link FileBean} describing file
	 * @throws IOException if I/O is corrupted
	 * @throws FileNotFoundException if file doesnt exist
	 */
	public FileBean loadFileAbsolutePath(String path) throws IOException, FileNotFoundException {
		return this.fileLoader.loadFileAbsolutePath(path);
	}
	
	/**
	 * Get {@link File} object from given path.
	 * 
	 * @param absolutePath absolute path to file
	 * @return {@link File} described file
	 */
	public File getFile(String absolutePath) {
		return this.fileLoader.getFile(absolutePath);
	}
	
	/**
	 * Load file information into {@link FileBean} given
	 * {@link File}.
	 * 
	 * @param file to load {@link FileBean} object from
	 * @return {@link FileBean} describing given file
	 * @throws IOException if I/O is corrupted
	 * @throws FileNotFoundException if file doesnt exist
	 */
	public FileBean loadFile(File file) throws IOException, FileNotFoundException {
		return this.fileLoader.loadFile(file);
	}
	
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
	public List<FileBean> loadDirectory(File[] files, List<FileBean> fileBeanList) throws IOException {
		return this.fileLoader.loadDirectory(files, fileBeanList);
	}
	
	/**
	 * Loads {@link File} directory object into {@link FileBean} object. 
	 * <p>File object given as a parameter to this method must be a directory.
	 * 
	 * @param file that is directory to load as {@link FileBean} object
	 * @return {@link FileBean} representation of the file given
	 */
	public FileBean loadFileBeanAsDirectory(File file) {
		return this.fileLoader.loadFileBeanAsDirectory(file);
	}
	
	/**
	 * Save given {@link String} into given {@link File} appending it to the end of the file.
	 * <p>This method will not erase the previous file contents. It will add given string at
	 * the end of the file.
	 * 
	 * @param file to append given string to
	 * @param newContent string to append to given file
	 * @throws IOException if I/O is corrupted
	 */
	public void saveStringToFileAppend(File file, String newContent) throws IOException {
		this.fileSaver.saveStringToFileAppend(file, newContent);
	}
	
	/**
	 * Save given {@link String} into given {@link File} overwritting the file and leaving
	 * the string as the only file content.
	 * <p>This method will erase the previous file contents.
	 * 
	 * @param file to overwrite with given string
	 * @param newContent string to be overwritten as a new file
	 * @throws IOException if I/O is corrupted
	 */
	public void saveStringToFileOverwrite(File file, String newContent) throws IOException {
		this.fileSaver.saveStringToFileOverwrite(file, newContent);
	}
	
}
