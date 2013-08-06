package com.hcctech.bookshelf.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import flex.messaging.FlexSession;

/**
 * @author randyjie
 * 
 */
public class InitFlex implements ServletContextListener {


	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		FlexSession.addSessionCreatedListener(new MyFlexSessionListener());
	}
	
}
