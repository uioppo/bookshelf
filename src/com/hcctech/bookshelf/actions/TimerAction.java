package com.hcctech.bookshelf.actions;

import com.opensymphony.xwork2.ActionSupport;

public class TimerAction extends ActionSupport {

	private static final long serialVersionUID = 2906245998377325841L;
	private String random ;
	
	public String reandom() {
		random = Math.random()+"" ;
		return SUCCESS ;
	}

	public String getRandom() {
		return random;
	}
	
	
	
}
