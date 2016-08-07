package com.wpruszak.blitzch.view.listeners;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.lib.InteractiveConflictSolutionFinderImpl;
import com.wpruszak.blitzch.view.MainFrame;

/**
 * Listener that changes {@link MainFrame} title whenever new file is being edited
 * in interactive mode.
 * 
 * @author setzo
 * @since 1.0
 * @see InteractiveConflictSolutionFinderImpl
 */
public interface FileBeanEditListener {

	/**
	 * Firing new file is being edited event and changing {@link MainFrame} title
	 * to the fileBean's object filename or absolute path.
	 * 
	 * @param fileBean file to change title accordingly to
	 */
	void fileBeanEditedEvent(FileBean fileBean);
}
