package com.wpruszak.blitzch.lib.interfaces;

import java.io.IOException;
import java.util.List;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.controller.ConflictController;

/**
 * Default git conflict resolving interface. Basic implementation of
 * this interface is: {@link ConflictController}.
 * 
 * @author setzo
 * @since 1.0
 * @see ConflictController
 */
public interface ConflictResolver {

	/**
	 * Automatically resolves all of the git conflicts in a given file accordingly to given mode.
	 * 
	 * <p>This method will not ask for any permission, it will solve the conflicts itself and
	 * overwrite the file with the final result. Depending on mode given to it as a parameter the results
	 * will differ (or may differ).
	 * 
	 * 
	 * @param fileBean file to solve conflicts for
	 * @param mode of conflict solving
	 * @throws IOException if I/O is corrupted
	 */
	void automaticResolveConflictsInFile(FileBean fileBean, String mode) throws IOException;

	/**
	 * Automatically resolves all of the git conflicts in a given directory accordingly to given mode.
	 * 
	 * <p>This method will not ask for any permission, it will solve the conflicts itself and
	 * overwrite the files with the final result. Depending on mode given to it as a parameter the results
	 * will differ (or may differ).
	 * 
	 * @param fileBeanList list of files in a directory
	 * @param mode of conflict solving
	 * @throws IOException if I/O is corrupted
	 */
	void automaticResolveConflictsInDirectory(List<FileBean> fileBeanList, String mode) throws IOException;

	/**
	 * Interactive conflict resolving in file, whenever this method encounters git conflict it 
	 * will ask what to do. Mode given to this method is interactive by default. 
	 * 
	 * <p>After encountering conflict there will be these options:
	 * 
	 * <ul>
	 * <li>Keep newer conflict version</li>
	 * <li>Keep older conflict version</li>
	 * <li>Keep both conflict versions, first new then old</li>
	 * <li>Keep both conflict versions, first old then new</li>
	 * <li>Manually fix the conflict</li>
	 * </ul>
	 * 
	 * @param fileBean file to solve conflicts for
	 * @throws IOException if I/O is corrupted
	 */
	void interactiveResolveConflictsInFile(FileBean fileBean) throws IOException;

	/**
	 * Interactive conflict resolving in directory, whenever this method encounters git conflict it 
	 * will ask what to do. Mode given to this method is interactive by default. 
	 * 
	 * <p>After encountering conflict there will be these options:
	 * 
	 * <ul>
	 * <li>Keep newer conflict version</li>
	 * <li>Keep older conflict version</li>
	 * <li>Keep both conflict versions, first new then old</li>
	 * <li>Keep both conflict versions, first old then new</li>
	 * <li>Manually fix the conflict</li>
	 * </ul>
	 * 
	 * @param fileBeanList list of files in a directory
	 * @throws IOException if I/O is corrupted
	 */
	void interactiveResolveConflictsInDirectory(List<FileBean> fileBeanList) throws IOException;
}
