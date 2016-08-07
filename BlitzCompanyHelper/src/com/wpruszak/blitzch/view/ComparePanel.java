package com.wpruszak.blitzch.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.Serializable;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.view.text.ScrollableSyntaxTextArea;
import com.wpruszak.blitzch.view.text.SyntaxTextArea;

/**
 * Panel that compares two git versions of the given fragment in the file.
 * On the left side there's newer git version of the file, while on the right
 * side there's older version.
 * 
 * @author setzo
 * @since 1.0
 * @see JPanel
 * @see ScrollableSyntaxTextArea
 */
public class ComparePanel extends JPanel {

	/**
	 * Serializable constraint.
	 * 
	 * @see Serializable
	 */
	private static final long serialVersionUID = 3723904420335926462L;
	
	/**
	 * Label for new version of a given fragment of code.
	 * 
	 * @see JLabel
	 */
	private JLabel newTextLabel;
	
	/**
	 * Label for old version of a given fragment of code.
	 * 
	 * @see JLabel
	 */
	private JLabel oldTextLabel;
	
	/**
	 * Editor for new version of a given fragment of code.
	 * 
	 * @see ScrollableSyntaxTextArea
	 * @see SyntaxTextArea
	 */
	private ScrollableSyntaxTextArea scrollableSyntaxTextAreaNew;
	
	/**
	 * Editor for old version of a given fragment of code.
	 * 
	 * @see ScrollableSyntaxTextArea
	 * @see SyntaxTextArea
	 */
	private ScrollableSyntaxTextArea scrollableSyntaxTextAreaOld;
	
	/**
	 * Default constructor. Sets up the two editors for each git version of the file.
	 * 
	 * @param scrollableSyntaxTextAreaNew editor of new git file version
	 * @param scrollableSyntaxTextAreaOld editor of old git file version
	 * @see ComparePanel#scrollableSyntaxTextAreaNew
	 * @see ComparePanel#scrollableSyntaxTextAreaOld
	 * @see Autowired
	 */
	@Autowired
	public ComparePanel(ScrollableSyntaxTextArea scrollableSyntaxTextAreaNew,
			ScrollableSyntaxTextArea scrollableSyntaxTextAreaOld) {
		
		super(true);

		this.scrollableSyntaxTextAreaNew = scrollableSyntaxTextAreaNew;
		this.scrollableSyntaxTextAreaOld = scrollableSyntaxTextAreaOld;
		
		this.newTextLabel = new JLabel("New");
		this.oldTextLabel = new JLabel("Old");
		
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1;
		gc.weighty = 0.01;
		gc.gridx = 0;
		gc.gridy = 0;
		
		add(this.newTextLabel, gc);
        gc.gridx++;
        add(this.oldTextLabel, gc);
        
        gc.weighty = 1;

        gc.gridx = 0;
        gc.gridy++;
        add(this.scrollableSyntaxTextAreaNew, gc);
        gc.gridx++;
        add(this.scrollableSyntaxTextAreaOld, gc);
	}
	
	/**
	 * Default setText method for {@link ComparePanel#scrollableSyntaxTextAreaNew}
	 * 
	 * @param text to set on {@link ComparePanel#scrollableSyntaxTextAreaNew}
	 */
	public void setNewText(String text) {
		this.scrollableSyntaxTextAreaNew.getTextArea().setText(text);
	}
	
	/**
	 * Default setText method for {@link ComparePanel#scrollableSyntaxTextAreaOld}
	 * 
	 * @param text to set on {@link ComparePanel#scrollableSyntaxTextAreaOld}
	 */
	public void setOldText(String text) {
		this.scrollableSyntaxTextAreaOld.getTextArea().setText(text);
	}
	
	/**
	 * Default getText method for {@link ComparePanel#scrollableSyntaxTextAreaNew}
	 * 
	 * @return String text gotten from {@link ComparePanel#scrollableSyntaxTextAreaNew}
	 */
	public String getNewText() {
		return this.scrollableSyntaxTextAreaNew.getTextArea().getText();
	}
	
	/**
	 * Default getText method for {@link ComparePanel#scrollableSyntaxTextAreaOld}
	 * 
	 * @return String text gotten from {@link ComparePanel#scrollableSyntaxTextAreaOld}
	 */
	public String getOldText() {
		return this.scrollableSyntaxTextAreaOld.getTextArea().getText();
	}

	/**
	 * Default setCurrentFile method for {@link ComparePanel#scrollableSyntaxTextAreaNew}
	 * 
	 * @param fileBean file info to set
	 */
	public void setCurrentFileNew(FileBean fileBean) {
		this.scrollableSyntaxTextAreaNew.setCurrentlyUsedFile(fileBean);
	}

	/**
	 * Default setCurrentFile method for {@link ComparePanel#scrollableSyntaxTextAreaOld}
	 * 
	 * @param fileBean file info to set
	 */
	public void setCurrentFileOld(FileBean fileBean) {
		this.scrollableSyntaxTextAreaOld.setCurrentlyUsedFile(fileBean);
	}

	/**
	 * Default getCurrentFile method for {@link ComparePanel#scrollableSyntaxTextAreaNew}
	 * 
	 * @return FileBean current file info from {@link ComparePanel#scrollableSyntaxTextAreaNew}
	 */
	public FileBean getCurrentFileNew() {
		return this.scrollableSyntaxTextAreaNew.getCurrentlyUsedFile();
	}

	/**
	 * Default getCurrentFile method for {@link ComparePanel#scrollableSyntaxTextAreaOld}
	 * 
	 * @return FileBean current file info from {@link ComparePanel#scrollableSyntaxTextAreaOld}
	 */
	public FileBean getCurrentFileOld() {
		return this.scrollableSyntaxTextAreaOld.getCurrentlyUsedFile();
	}

	/**
	 * Default get method for scrollableSyntaxTextAreaNew.
	 * 
	 * @return ScrollableSyntaxTextArea scrollableSyntaxTextAreaNew
	 */
	public ScrollableSyntaxTextArea getScrollableSyntaxTextAreaNew() {
		return scrollableSyntaxTextAreaNew;
	}

	/**
	 * Default set method for scrollableSyntaxTextAreaNew.
	 * 
	 * @param scrollableSyntaxTextAreaNew new ScrollableSyntaxTextArea
	 */
	public void setScrollableSyntaxTextAreaNew(ScrollableSyntaxTextArea scrollableSyntaxTextAreaNew) {
		this.scrollableSyntaxTextAreaNew = scrollableSyntaxTextAreaNew;
	}

	/**
	 * Default get method for scrollableSyntaxTextAreaOld.
	 * 
	 * @return ScrollableSyntaxTextArea scrollableSyntaxTextAreaOld
	 */
	public ScrollableSyntaxTextArea getScrollableSyntaxTextAreaOld() {
		return scrollableSyntaxTextAreaOld;
	}

	/**
	 * Default set method for scrollableSyntaxTextAreaOld.
	 * 
	 * @param scrollableSyntaxTextAreaOld new ScrollableSyntaxTextArea
	 */
	public void setScrollableSyntaxTextAreaOld(ScrollableSyntaxTextArea scrollableSyntaxTextAreaOld) {
		this.scrollableSyntaxTextAreaOld = scrollableSyntaxTextAreaOld;
	}
	
}
