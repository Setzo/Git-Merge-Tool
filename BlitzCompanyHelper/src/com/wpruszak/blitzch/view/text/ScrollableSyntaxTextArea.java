package com.wpruszak.blitzch.view.text;

import java.io.Serializable;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.springframework.beans.factory.annotation.Autowired;

import com.wpruszak.blitzch.beans.FileBean;

/**
 * {@link RTextScrollPane} custom implementation.
 * This class also contains information about file that is currently
 * being edited in the RSyntaxTextArea.
 * 
 * @author setzo
 * @since 1.0
 * @see RSyntaxTextArea
 * @see RTextScrollPane
 * @see <a href="http://fifesoft.com/">http://fifesoft.com/</a>
 * @see <a href="https://github.com/ghostandthemachine/rsyntaxtextarea">https://github.com/ghostandthemachine/rsyntaxtextarea</a>
 */
public class ScrollableSyntaxTextArea extends RTextScrollPane {
	
	/**
	 * Serializable constraint.
	 * 
	 * @see Serializable
	 */
	private static final long serialVersionUID = 2171177183129453002L;

	/**	
	 * Informations about file that is currently being edited.
	 */
	private FileBean currentlyUsedFile;
	
	/**
	 * Text area for this component.
	 */
	private SyntaxTextArea syntaxTextArea;
	
	/**
	 * Default constructor defining the editor window.
	 * 
	 * @param syntaxTextArea editor window
	 */
	@Autowired
	public ScrollableSyntaxTextArea(RSyntaxTextArea syntaxTextArea) {
		
		super(syntaxTextArea);
		
		if(syntaxTextArea instanceof SyntaxTextArea) {
			this.syntaxTextArea = (SyntaxTextArea) syntaxTextArea;
		}
	}

	/**
	 * Default get method for currently edited file.
	 * 
	 * @return FileBean currently edited file
	 */
	public FileBean getCurrentlyUsedFile() {
		return currentlyUsedFile;
	}

	/**
	 * Default set method for currently edited file.
	 * 
	 * @param currentlyUsedFile file that is currently used in editor.
	 */
	public void setCurrentlyUsedFile(FileBean currentlyUsedFile) {
		this.currentlyUsedFile = currentlyUsedFile;
	}
	
	/**
	 * Change theme method for this component text area.
	 * 
	 * @param path to the theme file
	 * @see SyntaxTextArea#changeTheme(String)
	 */
	public void changeTheme(String path) {
		this.syntaxTextArea.changeTheme(path);
	}
	
	/**
	 * Configuring syntaxTextArea's language support.
	 * 
	 * @param syntaxStyle syntax constant
	 * @param newSupport language support
	 */
	public void configureLanguageSupport(final String syntaxStyle, LanguageSupport newSupport) {
		this.syntaxTextArea.configureLanguageSupport(syntaxStyle, newSupport);
	}
}
