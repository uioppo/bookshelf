package com.hcctech.bookshelf.flex;

import java.util.ArrayList;
import java.util.List;

import com.hcctech.bookshelf.pojo.BsTopMessage;
import com.hcctech.bookshelf.pojo.BsWebUser;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;


public class TopMessageFlex {
	
	public List<BsTopMessage> findTopMessages() {
		//
		FlexSession session = FlexContext.getFlexSession();
		BsWebUser user=(BsWebUser)session.getAttribute("user");
		BsTopMessage bsTopMessage = new BsTopMessage("测试","http://211.144.136.253/images/menu_list02.gif","http://www.sina.com.cn");
		List<BsTopMessage> list = new ArrayList<BsTopMessage>();
		list.add(bsTopMessage);
		return list;//myBookService.addMyBookByKey(licenseKey);
	}

	
	
}
