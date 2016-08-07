package com.wpruszak.blitzch.installer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.EtchedBorder;

import org.apache.commons.io.FileUtils;

public class InstallerFrame extends JFrame implements ActionListener {
	
	private static final long serialVersionUID = -660130682616905176L;
	
	private static final String LABEL = "bch.jar";

	private JFileChooser fileChooser;
	
	private JButton setHomeBuildDirButton;
	private JButton submitButton;
	
	private JTextField installDirField;
	
	private boolean isInstallable = false;
	
	private File homeBuild;
	
	private void init() {
		
		this.fileChooser = new JFileChooser(new File(System.getProperty("user.home")));
		
		this.fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		this.fileChooser.setDoubleBuffered(true);
		
		this.setHomeBuildDirButton = new JButton("Set home build directory");
		this.setHomeBuildDirButton.addActionListener(this);
		
		this.submitButton = new JButton("Start installation");

		this.submitButton.setPreferredSize(this.setHomeBuildDirButton.getPreferredSize());
		
		this.installDirField = new JTextField();
		this.installDirField.setEnabled(true);
		
		this.installDirField.setBorder(BorderFactory.createCompoundBorder(
				BorderFactory.createEmptyBorder(2, 2, 2, 2), 
				BorderFactory.createEtchedBorder(EtchedBorder.LOWERED)));
		
		this.submitButton.addActionListener((e) -> {
			
			if(this.isInstallable) {
				
				String destinationPath = System.getProperty("user.home") + "/.bch/";
				
				File destDir = new File(destinationPath);
				
				try {

					for(File file : this.homeBuild.listFiles()) {
						
						if(file.isDirectory()) {
							FileUtils.moveDirectoryToDirectory(
									file, 
									destDir, 
									true);
							
						} else if(file.isFile()) {
							FileUtils.moveFileToDirectory(
									file, 
									destDir, 
									true);
						}
					}
					
					FileUtils.deleteDirectory(this.homeBuild);
					
					FileUtils.writeStringToFile(
							this.getDesktopFile(), 
							this.getDesktopFileContent(), 
							false);
					
					JOptionPane.showMessageDialog(
							this, 
							"Operation completed successfully!", 
							"Success", 
							JOptionPane.PLAIN_MESSAGE, 
							null);
					
				} catch (Exception e1) {
					
					JOptionPane.showMessageDialog(this, 
							"Operation failed, possibly lack of rights.", 
							"Error",
							JOptionPane.ERROR_MESSAGE, 
							null);
				}
				
			} else {
				JOptionPane.showMessageDialog(this, 
						"Cannot start installation without home build directory", 
						"Warning",
						JOptionPane.WARNING_MESSAGE, 
						null);
			}
		});
		
		this.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				
				dispose();
				System.gc();
			}
		});
	}
	
	public InstallerFrame() {

		try {
			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (Exception e) {
		}
		
		this.init();
		
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.fill = GridBagConstraints.BOTH;
		gc.anchor = GridBagConstraints.BASELINE;
		gc.weightx = 1;
		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		
		this.add(new JLabel(" "));
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weighty = 0.05;
		gc.gridy++;
		
		this.add(this.installDirField, gc);
		
		gc.fill = GridBagConstraints.NONE;
		gc.gridy++;

		this.add(this.setHomeBuildDirButton, gc);

		gc.gridy++;

		this.add(this.submitButton, gc);
		
		this.setTitle("Installer - Blitz Company Helper");
		this.setSize(500, 150);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(this.fileChooser.showDialog(this, "Set") == JFileChooser.APPROVE_OPTION) {
			
			this.homeBuild = this.fileChooser.getSelectedFile();
			this.installDirField.setText(this.homeBuild.getAbsolutePath());
			
			for(String file : this.homeBuild.list()) {
				
				if(file.equals(InstallerFrame.LABEL)) {
					this.isInstallable = true;
				}
			}
		}
	}
	
	private String getDesktopFileContent() {
		
		StringBuilder sb = new StringBuilder();
		
		String lineSeparator = System.getProperty("line.separator");
		
		String homeDir = System.getProperty("user.home");
		
		sb
			.append("[Desktop Entry]").append(lineSeparator)
			.append("Name=Blitz Company Helper").append(lineSeparator)
			.append("Comment=").append(lineSeparator)
			.append("Exec=java -jar ").append(homeDir).append("/.bch/bch.jar").append(lineSeparator)
			.append("Icon=").append(homeDir).append("/.bch/blitz.png").append(lineSeparator)
			.append("Terminal=false").append(lineSeparator)
			.append("Type=Application").append(lineSeparator)
			.append("StartupNotify=true");
		
		return sb.toString();
	}
	
	private File getDesktopFile() {
		
		String absolutePath = System.getProperty("user.home") + "/.local/share/applications/bch.desktop";
		
		return new File(absolutePath);
	}
}
