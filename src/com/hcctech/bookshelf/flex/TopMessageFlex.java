package com.hcctech.bookshelf.flex;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.hcctech.bookshelf.pojo.BsTopMessage;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.util.DomainUtil;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;


public class TopMessageFlex {
	
	public List<BsTopMessage> findTopMessages() {
		//
		List<BsTopMessage> list = new ArrayList<BsTopMessage>();
		try {
			String classPath = this.getClass().getResource("/").getPath();
			classPath=classPath.substring(1, classPath.indexOf("classes"));
			Properties prop = new Properties();
			prop.load(new FileInputStream(classPath+"topmessage.properties"));
			String domainPath = DomainUtil.getDomainName();
			String value = null;
			String[] element = null;
			BsTopMessage bsTopMessage = null;
			for (Object key : prop.keySet()) {
				value = prop.getProperty(key.toString());
				element = value.split(",");
				bsTopMessage = new BsTopMessage(element[0],domainPath+element[1],element[2]);
				list.add(bsTopMessage);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;//myBookService.addMyBookByKey(licenseKey);
	}

	
	
}
