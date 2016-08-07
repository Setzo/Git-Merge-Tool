package com.wpruszak.blitzch.lib.interfaces;

import java.io.IOException;
import java.util.List;

import com.wpruszak.blitzch.controller.ConflictController;

/**
 * Default interface for finding git conflicts and listing them.
 * 
 * @author setzo
 * @since 1.0
 * @see ConflictController
 */
public interface ConflictGetter {

	/**
	 * Returns list of String representations of all the git conflicts in given
	 * String lines array. 
	 * 
	 * <p>For example for file looking like this: 
	 * 
	 * File.php<br>
	 * 
	 * <pre>
	 * 		&lt;&lt;&lt;&lt;&lt;&lt;&lt;&lt; HEAD
	 *		something
	 *		=======
	 *		something more
	 *		&gt;&gt;&gt;&gt;&gt;&gt;&gt;&gt; branch
	 *</pre>
	 *
	 *<p>Would return list of String elements like this:
	 *
	 * <pre>
	 *		something
	 *		=======
	 *		something more
	 *</pre>
	 *
	 * @param lines of the file
	 * @return List&lt;String&gt; of all the git conflicts within given lines
	 * @throws IOException if I/O is corrupted
	 */
	List<String> getAllConflicts(String[] lines) throws IOException;
}
