package com.wpruszak.blitzch.installer;

import javax.swing.SwingUtilities;

public class Main {

	private static InstallerFrame frame;
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(() -> {
			
			Main.frame = new InstallerFrame();
			Main.frame.setVisible(true);
		});
	}
}
