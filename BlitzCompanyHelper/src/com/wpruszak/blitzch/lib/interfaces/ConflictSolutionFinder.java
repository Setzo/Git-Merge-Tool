package com.wpruszak.blitzch.lib.interfaces;

import java.io.IOException;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.lib.AutomaticConflictSolutionFinderImpl;
import com.wpruszak.blitzch.lib.InteractiveConflictSolutionFinderImpl;

/**
 * Conflict solution finder interface.
 * <p>Main task of this interface is to find git conflict and its solution by
 * solving it accordingly to given mode and returning the String representation of
 * the already solved conflict.
 * 
 * @author setzo
 * @since 1.0
 * @see AutomaticConflictSolutionFinderImpl
 * @see InteractiveConflictSolutionFinderImpl
 */
public interface ConflictSolutionFinder {

	/**
	 * Find conflict solution in given file accordingly to given mode and return it as
	 * a String object.
	 * 
	 * @param fileBean file to solve conflicts for
	 * @param mode of conflict solving
	 * @return String file content after solving conflicts
	 * @throws IOException if I/O is corrupted
	 */
	String findConflictSolution(FileBean fileBean, String mode) throws IOException;
}