package com.wpruszak.blitzch.lib.factories.impl;

import org.fife.rsta.ac.css.CssLanguageSupport;
import org.fife.rsta.ac.html.HtmlLanguageSupport;
import org.fife.rsta.ac.js.JavaScriptLanguageSupport;
import org.fife.rsta.ac.perl.PerlLanguageSupport;
import org.fife.rsta.ac.php.PhpLanguageSupport;
import org.fife.rsta.ac.sh.ShellLanguageSupport;
import org.fife.rsta.ac.xml.XmlLanguageSupport;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import com.wpruszak.blitzch.beans.CompleteLanguageSupportBean;
import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.lib.factories.CompleteLanguageSupportFactory;
import com.wpruszak.blitzch.view.text.SyntaxTextArea;

/**
 * Basic language support factory.
 * 
 * <p>It works by exchanging file extensions from
 * {@link FileBean} objects into {@link CompleteLanguageSupportBean} objects and then
 * putting the result into {@link SyntaxTextArea#configureLanguageSupport(String, org.fife.rsta.ac.LanguageSupport)}
 * method.
 * 
 * <p>For now it only contains support for languages that my job requires me to use with 
 * the exception of scala.
 * 
 * <p>List of currently supported languages:
 * <ul>
 * <li>PHP</li>
 * <li>Htaccess</li>
 * <li>CSS</li>
 * <li>HTML</li>
 * <li>Javascript</li>
 * <li>Shell</li>
 * <li>XML</li>
 * <li>Perl</li>
 * <li>JSON</li>
 * <li>Ruby</li>
 * <li>SQL</li>
 * <li>Scala</li>
 * </ul>
 * 
 * @author setzo
 * @since 1.0
 * @see CompleteLanguageSupportFactory
 */
public class CompleteLanguageSupportFactoryImpl implements CompleteLanguageSupportFactory {

	/**
	 * Php language support flag.
	 */
	private static final String PHP_LANGUAGE_SUPPORT = "php";
	
	/**
	 * Htaccess language support flag.
	 */
	private static final String HTACCESS_LANGUAGE_SUPPORT = "htaccess";
	
	/**
	 * CSS language support flag.
	 */
	private static final String CSS_LANGUAGE_SUPPORT = "css";
	
	/**
	 * HTML language support flag.
	 */
	private static final String HTML_LANGUAGE_SUPPORT = "html";
	
	/**
	 * Javascript language support flag.
	 */
	private static final String JAVASCRIPT_LANGUAGE_SUPPORT = "js";
	
	/**
	 * Shell language support flag.
	 */
	private static final String UNIX_SHELL_LANGUAGE_SUPPORT = "sh";
	
	/**
	 * XML language support flag.
	 */
	private static final String XML_LANGUAGE_SUPPORT = "xml";
	
	/**
	 * Perl language support flag.
	 */
	private static final String PERL_LANGUAGE_SUPPORT = "pl";
	
	/**
	 * JSON language support flag.
	 */
	private static final String JSON_LANGUAGE_SUPPORT = "json";
	
	/**
	 * Ruby language support flag.
	 */
	private static final String RUBY_LANGUAGE_SUPPORT = "rb";
	
	/**
	 * SQL language support flag.
	 */
	private static final String SQL_LANGUAGE_SUPPORT = "sql";
	
	/**
	 * Scala language support flag.
	 */
	private static final String SCALA_LANGUAGE_SUPPORT = "scala";
	
	/**
	 * @see CompleteLanguageSupportFactory
	 */
	@Override
	public CompleteLanguageSupportBean createCompleteLanguageSupport(String type) {
		
		if(type.equals(CompleteLanguageSupportFactoryImpl.PHP_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(new PhpLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_PHP);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.HTACCESS_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(null, SyntaxConstants.SYNTAX_STYLE_HTACCESS);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.CSS_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(new CssLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_CSS);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.HTML_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(new HtmlLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_HTML);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.JAVASCRIPT_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(new JavaScriptLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_JAVASCRIPT);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.UNIX_SHELL_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(new ShellLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_UNIX_SHELL);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.XML_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(new XmlLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_XML);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.PERL_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(new PerlLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_PERL);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.JSON_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(null, SyntaxConstants.SYNTAX_STYLE_JSON);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.RUBY_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(null, SyntaxConstants.SYNTAX_STYLE_RUBY);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.SQL_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(null, SyntaxConstants.SYNTAX_STYLE_SQL);
			
		} else if(type.equals(CompleteLanguageSupportFactoryImpl.SCALA_LANGUAGE_SUPPORT)) {
			return new CompleteLanguageSupportBean(null, SyntaxConstants.SYNTAX_STYLE_SCALA);
		}
		
		return new CompleteLanguageSupportBean(new PhpLanguageSupport(), SyntaxConstants.SYNTAX_STYLE_PHP);
	}
}
