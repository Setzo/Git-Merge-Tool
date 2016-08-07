package com.wpruszak.blitzch.view.text;

import java.io.IOException;
import java.io.Serializable;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.rsta.ac.php.PhpLanguageSupport;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rsyntaxtextarea.Theme;
import org.fife.ui.rtextarea.RTextScrollPane;

/**
 * Default code editor window implementation. Derivative of RSyntaxTextArea.
 * 
 * @author setzo
 * @since 1.0
 * @see RSyntaxTextArea
 * @see RTextScrollPane
 * @see <a href="http://fifesoft.com/">http://fifesoft.com/</a>
 * @see <a href="https://github.com/ghostandthemachine/rsyntaxtextarea">https://github.com/ghostandthemachine/rsyntaxtextarea</a>
 */
public class SyntaxTextArea extends RSyntaxTextArea {

	/**
	 * Serializable constraint.
	 * 
	 * @see Serializable
	 */
	private static final long serialVersionUID = -8526044800701858786L;
	
	/**
	 * Dark theme flag.
	 */
	public static final String DARK_THEME = "/rsta/themes/dark.xml";
	
	/**
	 * Eclipse theme flag.
	 */
	public static final String ECLIPSE_THEME = "/rsta/themes/eclipse.xml";
	
	/**
	 * Idea theme flag.
	 */
	public static final String IDEA_THEME = "/rsta/themes/idea.xml";
	
	/**
	 * Visual Studio theme flag.
	 */
	public static final String VS_THEME = "/rsta/themes/vs.xml";
	
	/**
	 * Whitez theme flag.
	 */
	public static final String WHITEZ_THEME = "/rsta/themes/default.xml";

	/**
	 * Customizing this RSyntaxTextArea.
	 * 
	 * <p>Changes made: 
	 * <ul>
	 * <li>Code folding enabled</li>
	 * <li>Auto indent enabled</li>
	 * <li>Tab size set to 4 (PSR-2 standard)</li>
	 * <li>Marking occurences enabled</li>
	 * <li>Setting rounded edges of selected braces</li>
	 * <li>Theme set to default</li>
	 * </ul>
	 */
	private void doInit() {

		this.setCodeFoldingEnabled(true);
		this.setAutoIndentEnabled(true);
		this.setAntiAliasingEnabled(true);
		this.setName("Editor");
		this.setTabSize(4);
		this.setMarkOccurrences(true);
		this.setRoundedSelectionEdges(true);
		this.changeTheme(SyntaxTextArea.DARK_THEME);

		this.configureLanguageSupport(SyntaxConstants.SYNTAX_STYLE_PHP, new PhpLanguageSupport());
	}

	/**
	 * Default constructor.
	 * <p>Initializing this RSyntaxTextArea customization.
	 */
	public SyntaxTextArea() {
		this.doInit();
	}

	/**
	 * Constructor taking default text to start editor with
	 * as a parameter.
	 * 
	 * @param text to start editor with
	 */
	public SyntaxTextArea(String text) {

		super(text);
		this.doInit();
	}

	/**
	 * Adding / changing language support to this component.
	 * 
	 * @param syntaxStyle new syntax constant
	 * @param newSupport new language support
	 */
	public void configureLanguageSupport(final String syntaxStyle, LanguageSupport newSupport) {

		this.setSyntaxEditingStyle(syntaxStyle);
		
		LanguageSupport support = 
				(LanguageSupport) this.getClientProperty("org.fife.rsta.ac.LanguageSupport");
		if (support != null) {
			if(newSupport == null) {
				support.setAutoCompleteEnabled(false);
				support.setAutoActivationEnabled(false);
				support.setParameterAssistanceEnabled(false);
			}
			support.uninstall(this);
		}
		
		if(newSupport != null) {
			newSupport.install(this);
		}
	}

	/**
	 * Change this editor theme to the theme at the given path.
	 * 
	 * @param path to the XML theme file
	 */
	public void changeTheme(String path) {

		try {
			Theme theme = Theme.load(getClass().getResourceAsStream(path));
			theme.apply(this);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
