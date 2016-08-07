package com.wpruszak.blitzch.lib.factories;

import com.wpruszak.blitzch.beans.CompleteLanguageSupportBean;
import com.wpruszak.blitzch.lib.factories.impl.CompleteLanguageSupportFactoryImpl;

/**
 * Default CompleteLanguageSupport factory interface.
 * 
 * @author setzo
 * @since 1.0
 * @see CompleteLanguageSupportFactoryImpl
 */
public interface CompleteLanguageSupportFactory {

	/**
	 * Default factory create method for {@link CompleteLanguageSupportBean} objects.
	 * 
	 * @param type extension of the file
	 * @return CompleteLanguageSupportBean object representing language support
	 * for this extension
	 * @see CompleteLanguageSupportFactoryImpl
	 */
	CompleteLanguageSupportBean createCompleteLanguageSupport(String type);
}