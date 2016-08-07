package com.wpruszak.blitzch.beans;

import org.fife.rsta.ac.LanguageSupport;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;

import com.wpruszak.blitzch.lib.factories.CompleteLanguageSupportFactory;

/**
 * Bean object to keep information about currently used language in editor.
 * 
 * @author setzo
 * @since 1.0
 * @see CompleteLanguageSupportFactory
 */
public class CompleteLanguageSupportBean {

	/**
	 * Basic language support interface to allow more than one type
	 * of language support object to be put in this bean.
	 */
	private LanguageSupport support;
	
	/**
	 * Syntax constant.
	 * 
	 * @see SyntaxConstants
	 */
	private String syntaxConstant;
	
	/**
	 * Basic constructor for future spring autowiring.
	 */
	public CompleteLanguageSupportBean() {
	}

	/**
	 * Basic constructor for use in factories of CompleteLanguageSupport.
	 * 
	 * @param support language support
	 * @param syntaxConstant syntax constant
	 */
	public CompleteLanguageSupportBean(LanguageSupport support, String syntaxConstant) {
		this.support = support;
		this.syntaxConstant = syntaxConstant;
	}
	
	/**
	 * Get this beans {@link LanguageSupport} object.
	 * 
	 * @return LanguageSupport support
	 */
	public LanguageSupport getSupport() {
		return support;
	}
	
	/**
	 * Set this beans {@link LanguageSupport} object
	 * 
	 * @param support language support
	 */
	public void setSupport(LanguageSupport support) {
		this.support = support;
	}
	
	/**
	 * Get this beans {@link SyntaxConstants}
	 * 
	 * @return String SyntaxConstants
	 */
	public String getSyntaxConstant() {
		return syntaxConstant;
	}
	
	/**
	 * Set this beans {@link SyntaxConstants}
	 * 
	 * @param syntaxConstant syntax constant
	 */
	public void setSyntaxConstant(String syntaxConstant) {
		this.syntaxConstant = syntaxConstant;
	}
}
