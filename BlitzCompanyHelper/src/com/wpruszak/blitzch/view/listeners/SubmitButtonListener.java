package com.wpruszak.blitzch.view.listeners;

/**
 * Default submit button listener interface.
 * Implemented using listener (observer) pattern.
 * 
 * @author setzo
 * @since 1.0
 * @see <a href="http://www.oodesign.com/observer-pattern.html">http://www.oodesign.com/observer-pattern.html</a>
 */
public interface SubmitButtonListener {

	/**
	 * Default action that will execute after the
	 * submit button clicked event.
	 * 
	 * @param mode currently selected mode
	 */
	public void submitButtonAction(String mode);
}
