package com.wpruszak.blitzch.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.Serializable;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import com.wpruszak.blitzch.view.listeners.SubmitButtonListener;

/**
 * Default option panel. 
 * <p>For now it provides option to choose between
 * which mode we want to use this.
 * 
 * @author setzo
 * @since 1.0
 * @see JPanel
 * @see JRadioButton
 * @see SubmitButtonListener
 */
public class OptionPanel extends JPanel {

	/**
	 * Serializable constraint.
	 * 
	 * @see Serializable
	 */
	private static final long serialVersionUID = -1540727554739852579L;

	/**
	 * Flag to keep only newer version of the git conflict.
	 */
	public static final String MODE_ONLY_NEWER = "NEWER";
	
	/**
	 * Flag to keep only older version of the git conflict.
	 */
	public static final String MODE_ONLY_OLDER = "OLDER";
	
	/**
	 * Flag to keep both versions of the git conflict.
	 * Keeping new version first, then appending old version.
	 */
	public static final String MODE_KEEP_BOTH_NEW_TO_OLD = "BOTH_NO";
	
	/**
	 * Flag to keep both versions of the git conflict.
	 * Keeping old version first, then appending new version.
	 */
	public static final String MODE_KEEP_BOTH_OLD_TO_NEW = "BOTH_ON";
	
	/**
	 * Flag to ask what to do on each conflict.
	 */
	public static final String MODE_INTERACTIVE = "INTERACTIVE";
	
	/**
	 * Confirm change config button.
	 */
	private JButton submitButton;
	
	/**
	 * Listener for {@link OptionPanel#submitButton}.
	 */
	private SubmitButtonListener submitButtonListener;
	
	/**
	 * Button group for all modes.
	 * 
	 * @see ButtonGroup
	 * @see JRadioButton
	 */
	private ButtonGroup modeGroup;
	
	/**
	 * Button indicating {@link OptionPanel#MODE_ONLY_NEWER} flag.
	 */
	
	private JRadioButton onlyNewerMode;
	/**
	 * Button indicating {@link OptionPanel#MODE_ONLY_OLDER} flag.
	 */
	private JRadioButton onlyOlderMode;
	
	/**
	 * Button indicating {@link OptionPanel#MODE_KEEP_BOTH_NEW_TO_OLD} flag.
	 */
	private JRadioButton keepBothModeNO;
	
	/**
	 * Button indicating {@link OptionPanel#MODE_KEEP_BOTH_OLD_TO_NEW} flag.
	 */
	private JRadioButton keepBothModeON;
	
	/**
	 * Button indicating {@link OptionPanel#MODE_INTERACTIVE} flag.
	 */
	private JRadioButton interactiveMode;
	
	/**
	 * Initialize components method.
	 */
	private void init() {
		
		this.onlyNewerMode = new JRadioButton("Keep only newer");
		this.onlyNewerMode.setActionCommand(OptionPanel.MODE_ONLY_NEWER);
		
		this.onlyOlderMode = new JRadioButton("Keep only older");
		this.onlyOlderMode.setActionCommand(OptionPanel.MODE_ONLY_OLDER);
		
		this.keepBothModeNO = new JRadioButton("Keep both N->O");
		this.keepBothModeNO.setActionCommand(OptionPanel.MODE_KEEP_BOTH_NEW_TO_OLD);
		
		this.keepBothModeON = new JRadioButton("Keep both O->N");
		this.keepBothModeON.setActionCommand(OptionPanel.MODE_KEEP_BOTH_OLD_TO_NEW);
		
		this.interactiveMode = new JRadioButton("Interactive");
		this.interactiveMode.setActionCommand(OptionPanel.MODE_INTERACTIVE);

		this.modeGroup = new ButtonGroup();
		
		this.onlyNewerMode.setSelected(true);
		
		this.modeGroup.add(this.onlyNewerMode);
		this.modeGroup.add(this.onlyOlderMode);
		this.modeGroup.add(this.keepBothModeNO);
		this.modeGroup.add(this.keepBothModeON);
		this.modeGroup.add(this.interactiveMode);
		
		this.submitButton = new JButton("Start");
	}
	
	/**
	 * Adding listeners.
	 * 
	 * @see <a href="http://www.oodesign.com/observer-pattern.html">http://www.oodesign.com/observer-pattern.html</a>
	 */
	private void addListeners() {
		
		this.submitButton.addActionListener((e) -> {
			
			if(this.submitButtonListener != null) {
				
				String mode = this.modeGroup.getSelection().getActionCommand();
				this.submitButtonListener.submitButtonAction(mode);
			}
		});
	}
	
	/**
	 * Create border for this panel.
	 * 
	 * @return Border border
	 */
	private Border createBorder() {
		
		Border innerBorder = BorderFactory.createTitledBorder("Options");
		Border outerBorder = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		return BorderFactory.createCompoundBorder(outerBorder, innerBorder);
	}
	
	/**
	 * Default constructor.
	 */
	public OptionPanel() {
		
		super(true);

		this.init();
		
		this.addListeners();
		
		this.setBorder(this.createBorder());
		
		this.setLayout(new GridBagLayout());
		this.setVisible(true);
		
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.NONE;
		
		gc.weightx = 1;
		gc.weighty = 1;
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.FIRST_LINE_START;
		
		this.add(this.onlyNewerMode, gc);
		
		gc.gridy++;
		
		this.add(this.onlyOlderMode, gc);

		gc.gridy++;
		
		this.add(this.keepBothModeNO, gc);

		gc.gridy++;
		
		this.add(this.keepBothModeON, gc);
		
		gc.gridy++;

		this.add(this.interactiveMode, gc);
		
		gc.anchor = GridBagConstraints.BASELINE;
		gc.weighty = 100;
		gc.gridy++;

		this.add(this.submitButton, gc);
	}
	
	/**
	 * Get currently used mode.
	 * 
	 * @return String mode
	 */
	public String getMode() {
		
		return this.modeGroup.getSelection().getActionCommand();
	}
	
	/**
	 * Set method for {@link OptionPanel#submitButtonListener}.
	 * 
	 * @param submitButtonListener to set to listen to {@link OptionPanel#submitButton}
	 * @see SubmitButtonListener
	 */
	public void setSubmitButtonListener(SubmitButtonListener submitButtonListener) {
		this.submitButtonListener = submitButtonListener;
	}
}
