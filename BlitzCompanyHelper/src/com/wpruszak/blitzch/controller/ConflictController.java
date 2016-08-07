package com.wpruszak.blitzch.controller;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.lib.AutomaticConflictSolutionFinderImpl;
import com.wpruszak.blitzch.lib.InteractiveConflictSolutionFinderImpl;
import com.wpruszak.blitzch.lib.interfaces.ConflictGetter;
import com.wpruszak.blitzch.lib.interfaces.ConflictResolver;
import com.wpruszak.blitzch.lib.interfaces.ConflictSolutionFinder;
import com.wpruszak.blitzch.view.listeners.FileBeanEditListener;
import com.wpruszak.blitzch.view.listeners.UserInputListener;

/**
 * Controller responsible for all the operations with
 * finding and resolving git conflicts.
 * 
 * <p>This controller conflict finders: 
 * <ul>
 * <li>{@link AutomaticConflictSolutionFinderImpl}</li>
 * <li>{@link InteractiveConflictSolutionFinderImpl}</li>
 * </ul>
 * 
 * @author setzo
 * @since 1.0
 * @see ConflictResolver
 * @see ConflictGetter
 * @see AutomaticConflictSolutionFinderImpl
 * @see InteractiveConflictSolutionFinderImpl
 */
public class ConflictController implements ConflictGetter, ConflictResolver {
	
	/**
	 * Flag that indicates the start of git conflict.
	 */
	public static final transient String START_LINE = "<<<<<<";
	
	/**
	 * Flag that divides git conflict on two parts - the local one and the one that 
	 * was pulled from another branch.
	 */
	public static final transient String DIVIDER = "======";
	
	/**
	 * Flag that indicates the end of git conflict.
	 */
	public static final transient String END_LINE = ">>>>>>";
	
	/**
	 * Flag that tells StringBuilder to start appending text.
	 */
	public static final transient boolean APPEND = true;
	
	/**
	 * Flag that tells StringBuilder to skip the following text.
	 */
	public static final transient boolean SKIP = false;

	/**
	 * System default line break character.
	 */
	public static final transient String LINE_BREAK = System.getProperty("line.separator");
	
	/**
	 * This controller default IOController used for the saving
	 * of file(s) after resolving conflict(s).
	 * 
	 * @see IOController
	 */
	private IOController ioController;
	
	/**
	 * This controller solution finder for the use with automatic modes.
	 * 
	 * @see ConflictSolutionFinder
	 * @see AutomaticConflictSolutionFinderImpl
	 */
	private ConflictSolutionFinder automaticSolutionFinder;
	
	/**
	 * This controller solution finder for the use with interactive mode.
	 * 
	 * @see ConflictSolutionFinder
	 * @see InteractiveConflictSolutionFinderImpl
	 */
	private ConflictSolutionFinder interactiveSolutionFinder;
	
	/**
	 * Flag that indicates that user has stopped the interactive solution searching
	 * and we should stop the execution.
	 */
	private boolean stop = false;
	
	/**
	 * Default constructor defining this controllers IOController and both
	 * conflict solution finders.
	 * 
	 * @param ioController input/output controller
	 * @param automaticSolutionFinder conflict solution finder for automatic modes
	 * @param interactiveSolutionFinder conflict solution finder for interactive mode
	 * @see IOController
	 * @see ConflictSolutionFinder
	 */
	@Autowired
	public ConflictController(IOController ioController, 
			@Qualifier("automaticConflictSolutionFinderImpl")ConflictSolutionFinder automaticSolutionFinder,
			@Qualifier("interactiveConflictSolutionFinderImpl")ConflictSolutionFinder interactiveSolutionFinder) {

		this.ioController = ioController;
		this.automaticSolutionFinder = automaticSolutionFinder;
		this.interactiveSolutionFinder = interactiveSolutionFinder;
	}

	/**
	 * @see ConflictGetter
	 */
	@Override
	public List<String> getAllConflicts(String[] lines) throws IOException {
		
		List<String> conflictList = new LinkedList<String>();
		
		boolean flag = ConflictController.SKIP;
		
		StringBuilder sb = new StringBuilder();
		
		for(String line : lines) {

			if(line.startsWith(ConflictController.START_LINE)) {
				flag = ConflictController.APPEND;
				continue;
			}
			
			if(line.startsWith(ConflictController.END_LINE)) {
				
				conflictList.add(sb.toString());
				sb.delete(0, sb.length());
				flag = ConflictController.SKIP;
				continue;
			}
			
			if(flag == ConflictController.APPEND) {
				sb.append(line);
				sb.append(ConflictController.LINE_BREAK);
			}
		}
		
		return conflictList;
	}

	/**
	 * @see ConflictResolver
	 */
	@Override
	public void automaticResolveConflictsInFile(FileBean fileBean, String mode) throws IOException {

		String solution = this.automaticSolutionFinder.findConflictSolution(fileBean, mode);
		
		File file = this.ioController.getFile(fileBean.getAbsolutePath());
		
		this.ioController.saveStringToFileOverwrite(file, solution);
	}

	/**
	 * @see ConflictResolver
	 */
	@Override
	public void automaticResolveConflictsInDirectory(List<FileBean> fileBeanList, String mode) throws IOException {

		for(FileBean fileBean : fileBeanList) {
			this.automaticResolveConflictsInFile(fileBean, mode);
		}
	}

	/**
	 * @see ConflictResolver
	 */
	@Override
	public void interactiveResolveConflictsInFile(FileBean fileBean) throws IOException {
		
		try {
			String solution = this.interactiveSolutionFinder.findConflictSolution(fileBean, null);
			
			File file = this.ioController.getFile(fileBean.getAbsolutePath());
			
			this.ioController.saveStringToFileOverwrite(file, solution);
			
		} catch (IOException e) {
			this.stop = true;
			System.out.println("Stopped"); //TODO
		}
	}

	/**
	 * @see ConflictResolver
	 */
	@Override
	public void interactiveResolveConflictsInDirectory(List<FileBean> fileBeanList) throws IOException {

		for(FileBean fileBean : fileBeanList) {
			if(this.stop) {
				this.stop = false;
				break;
			}
			this.interactiveResolveConflictsInFile(fileBean);
		}
	}
	
	/**
	 * Sets {@link UserInputListener} for {@link ConflictController#interactiveSolutionFinder}.
	 * 
	 * @param userInputListener listener
	 */
	public void setUserInputListener(UserInputListener userInputListener) {
		((InteractiveConflictSolutionFinderImpl)this.interactiveSolutionFinder)
				.setUserInputListener(userInputListener);;
	}

	/**
	 * Sets {@link FileBeanEditListener} for {@link ConflictController#interactiveSolutionFinder}.
	 * 
	 * @param fileBeanEditListener listener
	 */
	public void setFileBeanEditListener(FileBeanEditListener fileBeanEditListener) {
		((InteractiveConflictSolutionFinderImpl)this.interactiveSolutionFinder)
				.setFileBeanEditListener(fileBeanEditListener);
	}
}
