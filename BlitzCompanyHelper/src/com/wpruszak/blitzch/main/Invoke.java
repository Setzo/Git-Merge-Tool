package com.wpruszak.blitzch.main;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.wpruszak.blitzch.view.MainFrame;

/**
 * Main program class, creation of a new MainFrame and running it
 * as its own thread.
 * 
 * @author setzo
 * @since 1.0
 * @see com.wpruszak.blitzch.view.MainFrame
 * @see javax.swing.SwingUtilities
 * @see org.springframework.context.ApplicationContext
 */
public class Invoke {

	/**
	 * Main program window - the only element that derives from {@link JFrame}.
	 */
	private static MainFrame mainFrame;
	
	/**
	 * Spring default <em>ApplicationContext</em> interface.
	 * 
	 * @see org.springframework.context.ApplicationContext
	 */
	private static ApplicationContext appContext;
	
	/**
	 * Main program method.
	 * <p>Starting new thread with newly created {@link MainFrame}.
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel("de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Invoke.appContext = new ClassPathXmlApplicationContext("com/wpruszak/blitzch/config/beans.xml");
		//classpath*:**/applicationContext*.xml
		//com/wpruszak/blitzch/config/beans.xml
		
		SwingUtilities.invokeLater(() -> {
			Invoke.mainFrame = (MainFrame)Invoke.appContext.getBean(MainFrame.class);
			Invoke.mainFrame.setVisible(true);
			Invoke.mainFrame.repaint();
			Invoke.mainFrame.revalidate();
			
			Timer timer = new Timer(200, (e) -> {
				Invoke.mainFrame.changeMainTheme("de.javasoft.plaf.synthetica.SyntheticaPlainLookAndFeel");
			});
			
			timer.setRepeats(false);
			timer.start();
		});
	}

	/**
	 * Default get method for {@link Invoke#appContext}
	 * @return {@link Invoke#appContext}
	 */
	public static ApplicationContext getAppContext() {
		return appContext;
	}

	/**
	 * Default set method for {@link Invoke#appContext}
	 * @param appContext application context
	 */
	public static void setAppContext(ApplicationContext appContext) {
		Invoke.appContext = appContext;
	}
}
