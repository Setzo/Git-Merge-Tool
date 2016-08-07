package com.wpruszak.blitzch.lib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.controller.IOController;
import com.wpruszak.blitzch.lib.interfaces.FileLoader;

/**
 * Default {@link FileLoader} implementation, it provides implementation of all the methods 
 * that FileLoader requires.
 * 
 * @author setzo
 * @since 1.0
 * @see IOController
 * @see FileLoader
 * @see FileSaverImpl
 */
public class FileLoaderImpl implements FileLoader {
	
	/**
	 * Flag for skippable files in UNIX systems - some editors
	 * will create backup files besides the original one. The backup 
	 * ones will have their names finished with '~' character.
	 * Since this app needs only one file to process it will skip all 
	 * the backup copies
	 */
	private static final String SKIP = "~";
	
	/**
	 * @see FileLoader
	 */
	@Override
	public FileBean loadFileAbsolutePath(String path) throws IOException, FileNotFoundException {

		File file = new File(path);

		if (file == null || !file.exists()) {
			throw new IOException("File not found");
		}

		FileBean fileBean = new FileBean();

		this.setUp(fileBean, file);
		
		return fileBean;
	}
	
	/**
	 * @see FileLoader
	 */
	@Override
	public File getFile(String absolutePath) {
		return new File(absolutePath);
	}
	
	/**
	 * Extract data from <b>file</b> to <b>fileBean</b>.
	 * 
	 * @param fileBean to extract the data from the file to
	 * @param file to extract the data from
	 * @throws IOException if I/O is corrupted
	 */
	private void setUp(FileBean fileBean, File file) throws IOException {
		
		fileBean.setFilename(file.getName());
		fileBean.setExtension(this.getExtension(file));
		fileBean.setContent(this.getFileContent(file));
		fileBean.setAbsolutePath(file.getAbsolutePath());
		fileBean.setIsDirectory(false);
	}
	
	/**
	 * @see FileLoader
	 */
	@Override
	public FileBean loadFile(File file) throws IOException, FileNotFoundException {

		return this.loadFileAbsolutePath(file.getAbsolutePath());
	}
	
	/**
	 * Get file content and return it as a String.
	 * 
	 * @param file to read content from
	 * @return {@link String} fileContent
	 * @throws IOException if I/O is corrupted
	 */
	private String getFileContent(File file) throws IOException {
		
		return FileUtils.readFileToString(file);
	}
	
	/**
	 * Get file extension and return it as a String.
	 * <p>If extension doesnt exists return {@link FileBean#NO_EXTENSION}.
	 * <p>If filename ends with a dot return {@link FileBean#ENDS_WITH_DOT}.
	 * 
	 * @param file to get extension from
	 * @return {@link String} extension
	 */
	private String getExtension(File file) {
		
		String fileName = file.getName();
		
		int point = fileName.lastIndexOf(".");
		
		if(point == -1) {
			return FileBean.NO_EXTENSION;
		}
		
		if(point == fileName.length()-1) {
			return FileBean.ENDS_WITH_DOT;
		}
		
		return fileName.substring(point + 1, fileName.length());
	}
	
	/**
	 * @see FileLoader
	 */
	@Override
	public List<FileBean> loadDirectory(File[] files, List<FileBean> fileBeanList) throws IOException {
		
		if(fileBeanList == null) {
			fileBeanList = new LinkedList<FileBean>();
		}
		
		for(File file : files) {
			
			if(file.isFile()) {
				if(file.getName().endsWith(FileLoaderImpl.SKIP)) {
					continue;
				}
				
				FileBean fileBean = new FileBean();
				this.setUp(fileBean, file);
				fileBeanList.add(fileBean);
				
			} else if(file.isDirectory()) {
				
				this.loadDirectory(file.listFiles(), fileBeanList);
			}
		}
		
		return fileBeanList;
	}
	
	/**
	 * @see FileLoader
	 */
	@Override
	public FileBean loadFileBeanAsDirectory(File file) {
		
		FileBean fileBean = new FileBean();
		
		fileBean.setAbsolutePath(file.getAbsolutePath());
		fileBean.setIsDirectory(true);
		
		return fileBean;
	}
}
