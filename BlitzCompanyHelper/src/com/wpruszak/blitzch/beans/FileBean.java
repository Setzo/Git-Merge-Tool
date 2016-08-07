package com.wpruszak.blitzch.beans;

import java.io.File;

import com.wpruszak.blitzch.lib.interfaces.FileLoader;
import com.wpruszak.blitzch.lib.interfaces.FileSaver;

/**
 * Simple file describing bean(entity) class.
 * 
 * <p>It contains all the informations needed to localize the file back
 * and keep the file content in one place.
 *
 * @author setzo
 * @since 1.0
 * @see FileLoader
 * @see FileSaver
 */
public class FileBean {

	/**
	 * Flag that tells us whether or not file has any extension.
	 */
	public static final String NO_EXTENSION = "NO_EXTENSION";
	
	/**
	 * Flag that tells us whether or not filename is ending with a dot.
	 */
	public static final String ENDS_WITH_DOT = "ENDS_WITH_DOT";
	
	/**
	 * Filename of this bean's file (with extension).
	 */
	private String filename;
	
	/**
	 * String representation of this bean's file content.
	 */
	private String content;
	
	/**
	 * This bean's file extension.
	 */
	private String extension;
	
	/**
	 * This bean's file absolute path.
	 */
	private String absolutePath;
	
	/**
	 * Flag that is true when this bean's {@link File} object is a directory.
	 */
	private boolean isDirectory;

	/**
	 * Get this file filename.
	 * 
	 * @return String filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Set this file filename.
	 * 
	 * @param filename String filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * Get this file content.
	 * 
	 * @return String content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Set this file content.
	 * 
	 * @param content String content
	 */
	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * Get this file extension.
	 * 
	 * @return String extension
	 */
	public String getExtension() {
		return extension;
	}

	/**
	 * Set this file extension.
	 * 
	 * @param extension String extension
	 */
	public void setExtension(String extension) {
		this.extension = extension;
	}

	/**
	 * Get this file absolute path.
	 * 
	 * @return String absolute path
	 */
	public String getAbsolutePath() {
		return absolutePath;
	}

	/**
	 * Set this file absolute path.
	 * 
	 * @param absolutePath String absolute path
	 */
	public void setAbsolutePath(String absolutePath) {
		this.absolutePath = absolutePath;
	}

	/**
	 * Get this file isDirectory flag.
	 * 
	 * @return boolean isDirectory
	 */
	public boolean isDirectory() {
		return isDirectory;
	}

	/**
	 * Set this file isDirectory flag.
	 * 
	 * @param isDirectory boolean isDirectory flag
	 */
	public void setIsDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
}
