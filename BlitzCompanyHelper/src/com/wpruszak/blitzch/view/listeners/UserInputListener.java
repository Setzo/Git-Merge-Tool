package com.wpruszak.blitzch.view.listeners;

import com.wpruszak.blitzch.lib.InteractiveConflictSolutionFinderImpl;
import com.wpruszak.blitzch.view.ComparePanel;
import com.wpruszak.blitzch.view.text.ScrollableSyntaxTextArea;

/**
 * Listener that waits for user decision about what to do in a conflict.
 * 
 * <p>After getting user input {@link InteractiveConflictSolutionFinderImpl} will act accordingly
 * to it.
 * 
 * @author setzo
 * @since 1.0
 * @see InteractiveConflictSolutionFinderImpl
 */
public interface UserInputListener {

	/**
	 * Method returning user input and setting {@link ComparePanel} {@link ScrollableSyntaxTextArea}s
	 * texts to newContent and oldContent given as a parameter.
	 * 
	 * @param newContent new conflict file version
	 * @param oldContent old conflict file version
	 * @return String mode
	 */
	String input(String newContent, String oldContent);
}
