package com.wpruszak.blitzch.lib;

import java.io.IOException;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.controller.ConflictController;
import com.wpruszak.blitzch.lib.interfaces.ConflictSolutionFinder;
import com.wpruszak.blitzch.view.OptionPanel;

/**
 * Default automatic {@link ConflictSolutionFinder} implementation.
 * This class is used only for finding solution to conflicts with automatic modes.
 * 
 * @author setzo
 * @since 1.0
 * @see ConflictController
 */
public class AutomaticConflictSolutionFinderImpl implements ConflictSolutionFinder {

	/**
	 * @see ConflictSolutionFinder
	 */
	@Override
	public String findConflictSolution(FileBean file, String mode) throws IOException {

		return this.automaticFindSolution(file, mode);
	}
	
	/**
	 * Find solution for conflicts in a file given to this method. Use appropriate
	 * algorithm for each mode given. 
	 * 
	 * <p>This method is only to be used with automatic modes.
	 * 
	 * @param file to find solution for conflicts for.
	 * @param mode of conflict solving
	 * @return String file content with solved conflicts
	 * @throws IOException if I/O is corrupted
	 * @see OptionPanel
	 */
	private String automaticFindSolution(FileBean file, String mode) throws IOException {
		
		String fileContent = file.getContent();
		String lines[] = fileContent.split("\\r?\\n");
		
		String out = null;
		
		if(mode.equals(OptionPanel.MODE_ONLY_NEWER)) {
			
			out = this.modeOnlyFind(lines,
					ConflictController.START_LINE,
					ConflictController.DIVIDER,
					ConflictController.END_LINE);
			
		} else if (mode.equals(OptionPanel.MODE_ONLY_OLDER)) {
		
			out = this.modeOnlyFind(lines, 
					ConflictController.DIVIDER, 
					ConflictController.START_LINE,
					ConflictController.END_LINE);
		
		} else if(mode.equals(OptionPanel.MODE_KEEP_BOTH_NEW_TO_OLD)) {
			out = this.modeBothNewToOldFind(lines);
			
		}  else if (mode.equals(OptionPanel.MODE_KEEP_BOTH_OLD_TO_NEW)) {
			out = this.modeBothOldToNewFind(lines);
		}
		
		return out;
	}
	
	/**
	 * Find solution to conflicts in a given file content.
	 * 
	 * <p>This method only works with following modes: 
	 * <ul>
	 * <li>{@link OptionPanel#MODE_ONLY_NEWER}</li>
	 * <li>{@link OptionPanel#MODE_ONLY_OLDER}</li>
	 * </ul>
	 * 
	 * @param lines of file content
	 * @param start flag
	 * @param divider flag
	 * @param end flag
	 * @return String solution
	 * @see OptionPanel
	 */
	private String modeOnlyFind(String[] lines,
			String start, String divider, String end) {
		
		StringBuilder sb = new StringBuilder();
		
		boolean flag = ConflictController.APPEND;
		
		for(String line : lines) {
			
			if(line.startsWith(start)
					|| line.startsWith(end)) {
				
				flag = ConflictController.APPEND;
				continue;
				
			} else if(line.startsWith(divider)) {
				flag = ConflictController.SKIP;
				continue;
			}
			
			if(flag == ConflictController.APPEND) {
				
				sb.append(line);
				sb.append(ConflictController.LINE_BREAK);
			}
		}
		
		return sb.toString();
	}
	
	/**
	 * Find solution to conflicts in a given file content.
	 * 
	 * <p>This method only works with following modes: 
	 * <ul>
	 * <li>{@link OptionPanel#MODE_KEEP_BOTH_NEW_TO_OLD}</li>
	 * </ul>
	 * 
	 * @param lines of file content
	 * @return String solution
	 * @see OptionPanel
	 */
	private String modeBothNewToOldFind(String[] lines) {
		
		StringBuilder sb = new StringBuilder();
		
		for(String line : lines) {
			
			if(line.startsWith(ConflictController.START_LINE)
					|| line.startsWith(ConflictController.DIVIDER)
					|| line.startsWith(ConflictController.END_LINE)) {
				
				continue;
			}
			
			sb.append(line);
			sb.append(ConflictController.LINE_BREAK);
		}
		
		return sb.toString();
	}
	
	/**
	 * Find solution to conflicts in a given file content.
	 * 
	 * <p>This method only works with following modes: 
	 * <ul>
	 * <li>{@link OptionPanel#MODE_KEEP_BOTH_OLD_TO_NEW}</li>
	 * </ul>
	 * 
	 * @param lines of file content
	 * @return String solution
	 * @see OptionPanel
	 */
	private String modeBothOldToNewFind(String[] lines) {
		
		StringBuilder sb = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		
		boolean flag = ConflictController.APPEND;
		
		for(int i = 0; i < lines.length; i++) {
			
			String line = lines[i];
			
			if(line.startsWith(ConflictController.START_LINE)) {
				
				flag = ConflictController.SKIP;
				continue;
				
			} else if(line.startsWith(ConflictController.DIVIDER)) {
				
				flag = ConflictController.APPEND;
				continue;
				
			} else if(line.startsWith(ConflictController.END_LINE)) {
				
				sb.append(sb2.toString());
				sb2.delete(0, sb2.length());
				
				flag = ConflictController.APPEND;
				continue;
			}
			
			if(flag == ConflictController.SKIP) {
				
				sb2.append(line);
				sb2.append(ConflictController.LINE_BREAK);
			}
			
			if(flag == ConflictController.APPEND) {
				
				sb.append(line);
				sb.append(ConflictController.LINE_BREAK);
			}
		}
		
		return sb.toString();
	}
}
