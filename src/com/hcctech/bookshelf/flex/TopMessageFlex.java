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
		BsTopMessage bsTopMessage1 = new BsTopMessage("测试1","http://211.144.136.253/images/menu_list03.gif","http://www.sina.com.cn");
		BsTopMessage bsTopMessage2 = new BsTopMessage("测试2","http://211.144.136.253/images/menu_list04.gif","http://www.sina.com.cn");
		BsTopMessage bsTopMessage3 = new BsTopMessage("测试3","http://211.144.136.253/images/menu_list05.gif","http://www.sina.com.cn");
		BsTopMessage bsTopMessage4 = new BsTopMessage("version","2.0","http://www.sina.com.cn");
		List<BsTopMessage> list = new ArrayList<BsTopMessage>();
		list.add(bsTopMessage);
		list.add(bsTopMessage1);
		list.add(bsTopMessage2);
		list.add(bsTopMessage3);
		list.add(bsTopMessage4);
		return list;//myBookService.addMyBookByKey(licenseKey);
	}

	
	
}
