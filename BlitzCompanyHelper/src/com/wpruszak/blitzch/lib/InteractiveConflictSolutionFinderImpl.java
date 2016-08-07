package com.wpruszak.blitzch.lib;

import java.io.IOException;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.controller.ConflictController;
import com.wpruszak.blitzch.lib.interfaces.ConflictSolutionFinder;
import com.wpruszak.blitzch.view.MainFrame;
import com.wpruszak.blitzch.view.OptionPanel;
import com.wpruszak.blitzch.view.listeners.FileBeanEditListener;
import com.wpruszak.blitzch.view.listeners.UserInputListener;

/**
 * Default interactive {@link ConflictSolutionFinder} implementation.
 * This class is used only for finding solution to conflicts with interactive mode.
 * 
 * @author setzo
 * @since 1.0
 * @see ConflictController
 */
public class InteractiveConflictSolutionFinderImpl implements ConflictSolutionFinder {

	/**
	 * Listener for getting user input when in conflict about what to do.
	 */
	private UserInputListener userInputListener;
	
	/**
	 * Listener changing the {@link MainFrame} title when new file is edited.
	 */
	private FileBeanEditListener fileBeanEditListener;
	
	/**
	 * @see ConflictSolutionFinder
	 */
	@Override
	public String findConflictSolution(FileBean file, String mode) throws IOException {
		
		String fileContent = file.getContent();
		String lines[] = fileContent.split("\\r?\\n");
		
		String newContent = null;
		String oldContent = null;
		
		StringBuilder sb = new StringBuilder();
		StringBuilder solutionBuilder = new StringBuilder();
		StringBuilder interactiveBuilder = new StringBuilder();
		
		boolean flag = ConflictController.SKIP;
		boolean solBuilderFlag = ConflictController.APPEND;
		
		for(int i = 0; i < lines.length; i++) {

			String line = lines[i];
			
			if(line.startsWith(ConflictController.START_LINE)) {
				flag = ConflictController.APPEND;
				solBuilderFlag = ConflictController.SKIP;
				interactiveBuilder.delete(0, interactiveBuilder.length());
				interactiveBuilder.append(line).append(ConflictController.LINE_BREAK);
				continue;
			}
			
			if(line.startsWith(ConflictController.END_LINE)) {
				
				interactiveBuilder.append(line).append(ConflictController.LINE_BREAK);
				flag = ConflictController.SKIP;
				solBuilderFlag = ConflictController.APPEND;
				oldContent = sb.toString();
				sb.delete(0, sb.length());
				
				this.fileBeanEditListener.fileBeanEditedEvent(file);
				String answer = this.userInputListener.input(newContent, oldContent);
				
				if(answer == null) {
					throw new IOException("Stop");
				}
				
				if(answer.equals(OptionPanel.MODE_ONLY_NEWER)) {
					solutionBuilder.append(newContent);
					
				} else if(answer.equals(OptionPanel.MODE_ONLY_OLDER)) {
					solutionBuilder.append(oldContent);
					
				} else if(answer.equals(OptionPanel.MODE_KEEP_BOTH_NEW_TO_OLD)) {
					solutionBuilder.append(newContent).append(oldContent);
					
				} else if(answer.equals(OptionPanel.MODE_KEEP_BOTH_OLD_TO_NEW)) {
					solutionBuilder.append(oldContent).append(newContent);
					
				} else if(answer.equals(OptionPanel.MODE_INTERACTIVE)) {
					solutionBuilder.append(interactiveBuilder.toString());
				}
				continue;
			}
			
			if(line.startsWith(ConflictController.DIVIDER)) {
				interactiveBuilder.append(line).append(ConflictController.LINE_BREAK);
				flag = ConflictController.APPEND;
				newContent = sb.toString();
				sb.delete(0, sb.length());
				continue;
			}
			
			interactiveBuilder.append(line).append(ConflictController.LINE_BREAK);
			
			if(solBuilderFlag == ConflictController.APPEND) {
				solutionBuilder.append(line);
				solutionBuilder.append(ConflictController.LINE_BREAK);
			}
			
			if(flag == ConflictController.APPEND) {
				sb.append(line);
				sb.append(ConflictController.LINE_BREAK);
			}
		}
		
		return solutionBuilder.toString();
	}

	/**
	 * Default get method for {@link InteractiveConflictSolutionFinderImpl#userInputListener}
	 * 
	 * @return UserInputListener listener
	 */
	public UserInputListener getUserInputListener() {
		return userInputListener;
	}

	/**
	 * Default set method for {@link InteractiveConflictSolutionFinderImpl#userInputListener}
	 * 
	 * @param userInputListener listener
	 */
	public void setUserInputListener(UserInputListener userInputListener) {
		this.userInputListener = userInputListener;
	}

	/**
	 * Default get method for {@link InteractiveConflictSolutionFinderImpl#fileBeanEditListener}
	 * 
	 * @return FileBeanEditListener listener
	 */
	public FileBeanEditListener getFileBeanEditListener() {
		return fileBeanEditListener;
	}

	/**
	 * Default set method for {@link InteractiveConflictSolutionFinderImpl#fileBeanEditListener}
	 * 
	 * @param fileBeanEditListener listener
	 */
	public void setFileBeanEditListener(FileBeanEditListener fileBeanEditListener) {
		this.fileBeanEditListener = fileBeanEditListener;
	}
}
