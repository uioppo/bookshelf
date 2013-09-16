package com.hcctech.bookshelf.flex;

import org.apache.commons.lang3.StringUtils;

import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.MyBookService;
import com.opensymphony.xwork2.ActionContext;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;


public class LicenseKeyFlex {
	
	private MyBookService myBookService;

	public String checkLicenseKey(String licenseKey) {
		//
		try {
		System.out.println("start...............");
		FlexSession session = FlexContext.getFlexSession();
		BsWebUser user=(BsWebUser)session.getAttribute("user");
		if(myBookService==null)
			System.out.println("server="+myBookService);
		return myBookService.addMyBookByKey(licenseKey.trim(),user);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return "-1";
	}

	
	public void setMyBookService(MyBookService myBookService) {
		this.myBookService = myBookService;
	}
	
}
