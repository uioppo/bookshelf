package com.hcctech.bookshelf.listener;

import flex.messaging.FlexSession;
import flex.messaging.FlexSessionListener;

public class MyFlexSessionListener implements FlexSessionListener {

	public void sessionCreated(FlexSession arg0) {
		
		// TODO Auto-generated method stub
		System.out.println("sessionCreated"+arg0.getId());
		
	}

	public void sessionDestroyed(FlexSession arg0) {
		MySessionContext context  = MySessionContext.getInstance();
		context.delSession(arg0);
		System.out.println("sessionDestroyed"+arg0.getId());
	}

}
