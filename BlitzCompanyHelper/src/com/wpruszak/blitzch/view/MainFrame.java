package com.wpruszak.blitzch.view;

import java.awt.Desktop;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.wpruszak.blitzch.beans.CompleteLanguageSupportBean;
import com.wpruszak.blitzch.beans.FileBean;
import com.wpruszak.blitzch.controller.ConflictController;
import com.wpruszak.blitzch.controller.IOController;
import com.wpruszak.blitzch.lib.factories.CompleteLanguageSupportFactory;
import com.wpruszak.blitzch.lib.factories.impl.CompleteLanguageSupportFactoryImpl;
import com.wpruszak.blitzch.lib.interfaces.ConflictResolver;
import com.wpruszak.blitzch.view.text.ScrollableSyntaxTextArea;
import com.wpruszak.blitzch.view.text.SyntaxTextArea;

/**
 * Main program window. It will instantiate all of the custom objects: 
 * <ul>
 * <li>OptionPanel</li>
 * <li>ComparePanel</li>
 * <li>IOController</li>
 * <li>ScrollableSyntaxTextArea</li>
 * </ul>
 * and all of the dependencies of these objects.
 * <p>Besides that it instantiates some necessary Swing components like
 * JFileChooser.
 * 
 * @author setzo
 * @since 1.0
 * @see JFrame
 */
public class MainFrame extends JFrame {

	/**
	 * Serializable constraint.
	 * 
	 * @see Serializable
	 */
	private static final long serialVersionUID = -375946379977294773L;
	
	/**
	 * Name of the application. Instantiated in constructor.
	 */
	private final String TITLE;

	/**
	 * This window default option panel that will show up 
	 * at the left side of the window.
	 * 
	 * @see OptionPanel
	 */
	private OptionPanel optionPanel;
	
	/**
	 * This window default editor panel. For how it's built check
	 * {@link ComparePanel}. It will show up at the right side
	 * of this window.
	 * 
	 * @see ComparePanel
	 */
	private ComparePanel comparePanel;
	
	/**
	 * Default Input/Output controller.
	 * 
	 * @see IOController
	 */
	private IOController ioController;
	
	/**
	 * When in single editor mode this will show up for ComparePanel 
	 * and take its place.
	 * 
	 * @see ScrollableSyntaxTextArea
	 */
	private ScrollableSyntaxTextArea scrollableSyntaxTextArea;
	
	/**
	 * Default conflict resolver controller.
	 * 
	 * @see ConflictController
	 */
	private ConflictResolver conflictController;
	
	/**
	 * Map of possible choices in interactive conflict solving.
	 * Initialized in constructor.
	 */
	private Map<String, String> map;
	
	/**
	 * Default file chooser. Instantiated and customized in constructor.
	 * 
	 * @see JFileChooser
	 */
	private JFileChooser fileChooser;
	
	/**
	 * This window JMenuBar.
	 * 
	 * @see MainFrame#createMenu()
	 */
	private JMenuBar menuBar;
	
	/**
	 * Language support factory object. Returns {@link CompleteLanguageSupportBean} objects 
	 * from file extensions.
	 * 
	 * @see CompleteLanguageSupportFactory
	 * @see CompleteLanguageSupportFactoryImpl
	 */
	private CompleteLanguageSupportFactory languageFactory;
	
	/**
	 * Most recently used key in interactive conflict solving.
	 */
	private String key;
	
	/**
	 * List of FileBean objects of files in currently selected directory.
	 */
	private List<FileBean> currentDirList;
	
	/**
	 * Default constructor defining: 
	 * <ul>
	 * <li>Name of this application</li>
	 * <li>This window width</li>
	 * <li>This window height</li>
	 * <li>Default option panel</li>
	 * <li>Default comparing panel (double mode editor)</li>
	 * <li>Default Input/Output controller</li>
	 * <li>Default single mode editor</li>
	 * <li>Default conflict controller</li>
	 * <li>Default language support factory</li>
	 * </ul>
	 * 
	 * @param title name of this application
	 * @param width this window width
	 * @param height this window height
	 * @param optionPanel option panel
	 * @param comparePanel double mode editor
	 * @param ioController I/O controller
	 * @param scrollableSyntaxTextArea single mode editor
	 * @param conflictController conflict controller
	 * @param languageFactory language support factory
	 * @see Autowired
	 */
	@Autowired
	public MainFrame(@Value("Blitz Company Helper")String title,
			@Value("1500")int width,
			@Value("700")int height,
			OptionPanel optionPanel,
			ComparePanel comparePanel,
			IOController ioController,
			ScrollableSyntaxTextArea scrollableSyntaxTextArea,
			ConflictResolver conflictController,
			CompleteLanguageSupportFactory languageFactory) {

		super(title);
		
		/**
		 * Setting up the looks.
		 */
		SwingUtilities.updateComponentTreeUI(this);
		
		URL url = ClassLoader.getSystemResource("com/wpruszak/blitzch/images/blitz.png");
		Toolkit kit = Toolkit.getDefaultToolkit();
		Image img = kit.createImage(url);

		this.setIconImage(img);

		this.TITLE = title;

		this.optionPanel = optionPanel;
		this.comparePanel = comparePanel;
		this.ioController = ioController;
		this.scrollableSyntaxTextArea = scrollableSyntaxTextArea;
		this.conflictController = conflictController;
		this.languageFactory = languageFactory;
		
		this.map = new LinkedHashMap<String, String>();

		this.key = "Keep newer";
		
		this.map.put(key, OptionPanel.MODE_ONLY_NEWER);
		this.map.put("Keep older", OptionPanel.MODE_ONLY_OLDER);
		this.map.put("Keep both N->O", OptionPanel.MODE_KEEP_BOTH_NEW_TO_OLD);
		this.map.put("Keep both O->N", OptionPanel.MODE_KEEP_BOTH_OLD_TO_NEW);
		this.map.put("Don't touch", OptionPanel.MODE_INTERACTIVE);
		
		SwingUtilities.updateComponentTreeUI(this.scrollableSyntaxTextArea);
		SwingUtilities.updateComponentTreeUI(this.comparePanel);
		SwingUtilities.updateComponentTreeUI(this.optionPanel);
		
		// ***************		FILE CHOOSER		***************
		
		this.fileChooser = 
				new JFileChooser(this.ioController.getFile(System.getProperty("user.home")));
		
		SwingUtilities.updateComponentTreeUI(this.fileChooser);
		
		this.fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		this.fileChooser.setMultiSelectionEnabled(true);
		this.fileChooser.setDoubleBuffered(true);
		this.fileChooser.setFileHidingEnabled(false);
		
		// ***************		END FILE CHOOSER		***************
		
		this.addListeners();

		this.setSize(width, height);

		this.setLayout(new GridBagLayout());
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.menuBar = this.createMenu();
		
		this.setJMenuBar(this.menuBar);

		GridBagConstraints gc = new GridBagConstraints();

		gc.weightx = 1;
		gc.weighty = 0.05;
		gc.fill = GridBagConstraints.BOTH;

		gc.gridx = 0;
		gc.gridy = 0;
		this.add(this.optionPanel, gc);

		gc.weightx = 10;
		gc.gridx++;
		this.comparePanel.setVisible(false);
		this.add(this.comparePanel, gc);
		this.scrollableSyntaxTextArea.setVisible(true);
		this.add(this.scrollableSyntaxTextArea, gc);
	}
	
	/**
	 * Sets up all the needed listeners for this class instance variables.
	 */
	private void addListeners() {
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				
				dispose();
				System.gc();
			}
		});
		
		((ConflictController)this.conflictController).setFileBeanEditListener((fileBean) -> {
			
			this.setTitle(fileBean.getFilename() + " " + this.TITLE);
		});
		
		((ConflictController)this.conflictController).setUserInputListener((newContent, oldContent) -> {
			
			if(this.scrollableSyntaxTextArea.isVisible()) {
				this.scrollableSyntaxTextArea.setVisible(false);
				this.comparePanel.setVisible(true);
			}
			
			this.comparePanel.getScrollableSyntaxTextAreaNew().getTextArea().setText(newContent);
			this.comparePanel.getScrollableSyntaxTextAreaOld().getTextArea().setText(oldContent);

			String key = (String)JOptionPane.showInputDialog(this, "Choose mode", "Options",
					JOptionPane.PLAIN_MESSAGE, null,
					this.map.keySet().toArray(new String[this.map.keySet().size()]), 
					this.key);
			
			this.key = key;
			
			return key == null ? null : this.map.get(key);
		});
		
		this.optionPanel.setSubmitButtonListener((mode) -> {
			
			if(this.comparePanel.getCurrentFileNew() != null 
					&& this.fileChooser.getSelectedFile() != null) {
				
				File file = this.fileChooser.getSelectedFile();
				
				if(file.isDirectory() && this.currentDirList != null) {
					
					if(!mode.equals(OptionPanel.MODE_INTERACTIVE)) {
						
						try {
							this.conflictController
								.automaticResolveConflictsInDirectory(this.currentDirList, mode);
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					} else {
						
						try {
							this.conflictController
								.interactiveResolveConflictsInDirectory(this.currentDirList);
							
							this.switchVisibility();
							
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				} else if (file.isFile()) {
				
					FileBean fileBean = this.comparePanel.getCurrentFileNew();
					
					if(!mode.equals(OptionPanel.MODE_INTERACTIVE)) {
						
						try {
							this.conflictController
								.automaticResolveConflictsInFile(fileBean, mode);
							fileBean = this.ioController
								.loadFileAbsolutePath(fileBean.getAbsolutePath());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
						
					} else {
						
						try {
							this.conflictController
								.interactiveResolveConflictsInFile(fileBean);
							
							this.switchVisibility();
							
							fileBean = this.ioController
								.loadFileAbsolutePath(fileBean.getAbsolutePath());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
					
					this.updateRSTA(fileBean, false);
				}
			}
		});
	}

	/**
	 * Creates JMenuBar for this window to use.
	 * 
	 * @return JMenuBar this window menu bar
	 */
	private JMenuBar createMenu() {

		JMenuBar menuBar = new JMenuBar();

		// ***************		FILE MENU		***************

		JMenu fileMenu = new JMenu("File");

		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem saveAsItem = new JMenuItem("Save As");
		
		JMenuItem importItem = new JMenuItem("Import");
		JMenuItem exitItem = new JMenuItem("Exit");

		// ***************		LISTENERS		***************
		
		saveItem.addActionListener((e) -> {
			
			File file = this.ioController
					.getFile(
							this.scrollableSyntaxTextArea
							.getCurrentlyUsedFile()
							.getAbsolutePath()
					);
			
			if(this.scrollableSyntaxTextArea.isVisible()) {
				try {
					this.ioController.saveStringToFileOverwrite(
								file,
								this.scrollableSyntaxTextArea.getTextArea().getText());
					
					this.showSuccessAction("File Saved", "Saving successful");
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				
			} else {
				try {
					this.ioController.saveStringToFileOverwrite(
							file,
							this.comparePanel.getNewText());
					
					this.showSuccessAction("File Saved", "Saving successful");
					
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		saveAsItem.addActionListener((e) -> {
			
			this.fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			if(this.fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
				
				File file = this.fileChooser.getSelectedFile();
				
				if(this.scrollableSyntaxTextArea.isVisible()) {
					try {
						this.ioController
							.saveStringToFileOverwrite(
									file,
									this.scrollableSyntaxTextArea.getTextArea().getText()
							);
						
						this.showSuccessAction("File Saved", "Saving successful");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						this.ioController
							.saveStringToFileOverwrite(
									file,
									this.comparePanel.getNewText()
							);
						
						this.showSuccessAction("File Saved", "Saving successful");
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
			
			this.fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		});
		
		importItem.addActionListener((e) -> {
			if(this.fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
				
				File file = this.fileChooser.getSelectedFile();
				
				if(file.isFile()) {
				
					try {
						FileBean fb = this.ioController.loadFileAbsolutePath(file.getAbsolutePath());
						this.updateRSTA(fb, false);
						
						CompleteLanguageSupportBean languageSupport = 
								this.languageFactory.createCompleteLanguageSupport(fb.getExtension());
						
						this.scrollableSyntaxTextArea
								.configureLanguageSupport(languageSupport.getSyntaxConstant(), 
										languageSupport.getSupport());
						
					} catch (FileNotFoundException e1) {
						JOptionPane.showMessageDialog(
								MainFrame.this, 
								"File not found.", 
								"Error", 
								JOptionPane.ERROR_MESSAGE);
						
					} catch (IOException e1) {
						JOptionPane.showMessageDialog(
								MainFrame.this, 
								"Couldn't open file.", 
								"Error", 
								JOptionPane.ERROR_MESSAGE);
					}
					
				} else if (file.isDirectory()) {
					
					try {
						this.currentDirList = this.ioController.loadDirectory(file.listFiles(), null);
						FileBean fb = this.ioController.loadFileBeanAsDirectory(file);
						this.updateRSTA(fb, true);
						
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
	
		exitItem.addActionListener((e) -> {
			
			for(WindowListener windowListener : this.getWindowListeners()) {
				windowListener.windowClosing(new WindowEvent(MainFrame.this, 0));
			}
		});
		
		// ***************		END OF LISTENERS		***************
		//
		// ***************		MNEMONICS & ACCELERATORS		***************

		fileMenu.setMnemonic(KeyEvent.VK_F);
		
		importItem.setMnemonic(KeyEvent.VK_I);
		exitItem.setMnemonic(KeyEvent.VK_W);

		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveAsItem.setAccelerator(KeyStroke.getKeyStroke("control shift S"));
		importItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
		exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, ActionEvent.CTRL_MASK));
		
		// ***************		END OF MNEMONICS & ACCELERATORS		***************
		
		fileMenu.add(importItem);
		fileMenu.add(saveItem);
		fileMenu.add(saveAsItem);
		fileMenu.addSeparator();
		fileMenu.add(exitItem);

		// ***************		WINDOW MENU		***************

		JMenu windowMenu = new JMenu("Window");

		JCheckBoxMenuItem singleEditorModeItem = new JCheckBoxMenuItem("Single Editor Mode");
		singleEditorModeItem.setSelected(true);
		
		JMenu showViewMenu = new JMenu("Show View");
		
		JCheckBoxMenuItem showOptionPanelItem = new JCheckBoxMenuItem("Option Panel");
		showOptionPanelItem.setSelected(true);
		
		showViewMenu.add(showOptionPanelItem);
		
		ButtonGroup themeGroup = new ButtonGroup();
		JMenu themeMenu = new JMenu("Editor Theme");
		
		JRadioButtonMenuItem darkThemeItem = new JRadioButtonMenuItem("Dark");
		JRadioButtonMenuItem eclipseThemeItem = new JRadioButtonMenuItem("Eclipse");
		JRadioButtonMenuItem ideaThemeItem = new JRadioButtonMenuItem("Idea");
		JRadioButtonMenuItem visualStudioThemeItem = new JRadioButtonMenuItem("Visual Studio");
		JRadioButtonMenuItem whitezThemeItem = new JRadioButtonMenuItem("Whitez");
		
		darkThemeItem.setSelected(true);
		
		themeGroup.add(darkThemeItem);
		themeGroup.add(eclipseThemeItem);
		themeGroup.add(ideaThemeItem);
		themeGroup.add(visualStudioThemeItem);
		themeGroup.add(whitezThemeItem);
		
		themeMenu.add(darkThemeItem);
		themeMenu.add(eclipseThemeItem);
		themeMenu.add(ideaThemeItem);
		themeMenu.add(visualStudioThemeItem);
		themeMenu.add(whitezThemeItem);
		
		ButtonGroup mainThemeGroup = new ButtonGroup();
		JMenu changeThemeMenu = new JMenu("Main Theme");
		
		JRadioButtonMenuItem ubuntuTheme = new JRadioButtonMenuItem("Ubuntu");
		JRadioButtonMenuItem defaultTheme = new JRadioButtonMenuItem("Plain");
		JRadioButtonMenuItem whiteTheme = new JRadioButtonMenuItem("Alu");
		JRadioButtonMenuItem blackEyeTheme = new JRadioButtonMenuItem("Blue");
		JRadioButtonMenuItem blackStarTheme = new JRadioButtonMenuItem("Simple");
		
		defaultTheme.setSelected(true);
		
		mainThemeGroup.add(ubuntuTheme);
		mainThemeGroup.add(defaultTheme);
		mainThemeGroup.add(whiteTheme);
		mainThemeGroup.add(blackEyeTheme);
		mainThemeGroup.add(blackStarTheme);
		
		changeThemeMenu.add(ubuntuTheme);
		changeThemeMenu.add(defaultTheme);
		changeThemeMenu.add(whiteTheme);
		changeThemeMenu.add(blackEyeTheme);
		changeThemeMenu.add(blackStarTheme);
		
		JMenu helpMenu = new JMenu("About");
		
		JMenuItem javadocItem = new JMenuItem("Javadoc");
		JMenuItem structureItem = new JMenuItem("Application Structure");
		JMenuItem siteItem = new JMenuItem("Website");
		
		helpMenu.add(javadocItem);
		helpMenu.add(structureItem);
		helpMenu.add(siteItem);
		
		// ***************		LISTENERS		***************

		ubuntuTheme.addActionListener((e) -> {
			this.changeMainTheme("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
		});
		
		defaultTheme.addActionListener((e) -> {
			this.changeMainTheme("de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel");
		});
		
		whiteTheme.addActionListener((e) -> {
			this.changeMainTheme("de.javasoft.plaf.synthetica.SyntheticaAluOxideLookAndFeel");
		});
		
		blackEyeTheme.addActionListener((e) -> {
			this.changeMainTheme("de.javasoft.plaf.synthetica.SyntheticaBlueLightLookAndFeel");
		});
		
		blackStarTheme.addActionListener((e) -> {
			this.changeMainTheme("de.javasoft.plaf.synthetica.SyntheticaSimple2DLookAndFeel");
		});
		
		darkThemeItem.addActionListener((e) -> {
			this.changeEditorTheme(SyntaxTextArea.DARK_THEME);
		});
		
		eclipseThemeItem.addActionListener((e) -> {
			this.changeEditorTheme(SyntaxTextArea.ECLIPSE_THEME);
		});
		
		ideaThemeItem.addActionListener((e) -> {
			this.changeEditorTheme(SyntaxTextArea.IDEA_THEME);
		});
		
		visualStudioThemeItem.addActionListener((e) -> {
			this.changeEditorTheme(SyntaxTextArea.VS_THEME);
		});
		
		whitezThemeItem.addActionListener((e) -> {
			this.changeEditorTheme(SyntaxTextArea.WHITEZ_THEME);
		});
		
		singleEditorModeItem.addActionListener((e) -> {
			this.switchVisibility();
		});
		
		showOptionPanelItem.addActionListener((e) -> {
			
			this.optionPanel.setVisible(!this.optionPanel.isVisible());
		});

		javadocItem.addActionListener((e) -> {
			
			try {
				Desktop.getDesktop()
					.browse(
					new URL("https://cdn.rawgit.com/Setzo/Git-Merge-Tool/master/BlitzCompanyHelper/doc/index.html")
					.toURI());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		structureItem.addActionListener((e) -> {
			
			try {
				Desktop.getDesktop()
					.browse(
					new URL("http://imgur.com/a/cg6Kp")
					.toURI());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		siteItem.addActionListener((e) -> {
			
			try {
				Desktop.getDesktop()
					.browse(
					new URL("http://wpruszak.com/")
					.toURI());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});
		
		// ***************		END OF LISTENERS		***************
		//
		// ***************		MNEMONICS & ACCELERATORS		***************
		
		windowMenu.setMnemonic(KeyEvent.VK_N);
		
		showViewMenu.setMnemonic(KeyEvent.VK_S);
		
		//showViewItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));

		// ***************		END OF MNEMONICS & ACCELERATORS		***************

		windowMenu.add(singleEditorModeItem);
		windowMenu.add(showViewMenu);
		windowMenu.add(themeMenu);
		windowMenu.add(changeThemeMenu);

		// ***************		MENU BAR		***************

		menuBar.add(fileMenu);
		menuBar.add(windowMenu);
		menuBar.add(helpMenu);

		SwingUtilities.updateComponentTreeUI(menuBar);
		return menuBar;
	}
	
	/**
	 * Switch between single editor mode / dual editor mode.
	 */
	private void switchVisibility() {
		this.comparePanel.setVisible(!this.comparePanel.isVisible());
		this.scrollableSyntaxTextArea.setVisible(!this.scrollableSyntaxTextArea.isVisible());
	}
	
	/**
	 * Update RSyntaxTextAreas with currently edited file information.
	 * 
	 * @param fileBean bean describing currently edited file
	 * @param isDirectory flag telling whether or not file is a directory
	 */
	private void updateRSTA(FileBean fileBean, boolean isDirectory) {
		
		if(fileBean != null && !isDirectory) {
			
			this.scrollableSyntaxTextArea.setCurrentlyUsedFile(fileBean);
			this.comparePanel.setCurrentFileNew(fileBean);
			this.comparePanel.setCurrentFileOld(fileBean);
			
			this.setTitle(fileBean.getFilename() + " - " + this.TITLE);
//			this.comparePanel.setNewText(fileBean.getContent());
			this.scrollableSyntaxTextArea.getTextArea().setText(fileBean.getContent());
			
		} else if (fileBean != null && isDirectory) {
			
			this.scrollableSyntaxTextArea.setCurrentlyUsedFile(fileBean);
			this.comparePanel.setCurrentFileNew(fileBean);
			this.comparePanel.setCurrentFileOld(fileBean);
		}
	}
	
	/**
	 * Change theme of this JFrame.
	 * 
	 * @param theme fully qualified path to the LookAndFeel class.
	 */
	public void changeMainTheme(String theme) {
		
		try {
			UIManager.setLookAndFeel(theme);
			SwingUtilities.updateComponentTreeUI(this);
			SwingUtilities.updateComponentTreeUI(this.optionPanel);
			SwingUtilities.updateComponentTreeUI(this.scrollableSyntaxTextArea);
			SwingUtilities.updateComponentTreeUI(this.fileChooser);
			SwingUtilities.updateComponentTreeUI(this.comparePanel);
			SwingUtilities.updateComponentTreeUI(this.menuBar);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	/**
	 * Change theme of the editor.
	 * 
	 * @param theme classpath path to the theme file.
	 */
	private void changeEditorTheme(String theme) {
		
		this.scrollableSyntaxTextArea.changeTheme(theme);
		this.comparePanel.getScrollableSyntaxTextAreaNew().changeTheme(theme);
		this.comparePanel.getScrollableSyntaxTextAreaOld().changeTheme(theme);
	}
	
	/**
	 * Create pop up dialog with message and title given by the user.
	 * 
	 * @param message to show
	 * @param title to show
	 */
	private void showSuccessAction(String message, String title) {
		JOptionPane.showMessageDialog(this, message, title, JOptionPane.INFORMATION_MESSAGE);
	}
}
