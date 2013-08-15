package com.hcctech.bookshelf.flex;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.struts2.ServletActionContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import com.hcctech.bookshelf.pojo.BsTopMessage;
import com.hcctech.bookshelf.util.DomainUtil;
import flex.messaging.FlexContext;


public class TopMessageFlex {
	
	public List<BsTopMessage> findTopMessages() {
		//
		List<BsTopMessage> list = new ArrayList<BsTopMessage>();
		try {
//		    Resource resource = new ClassPathResource("WEB-INF/topmessage.properties");
//		    System.out.println("_____flex_____"+resource.getURL().getPath());
//		    Resource resource  = new ClassPathResource("/topmessage.properties");
//		    System.out.println("_____flex_____3"+resource.getURL().getPath());
//		    Resource resource = new FileSystemResource("WEB-INF/topmessage.properties");
//		    System.out.println("_____flex_____4"+resource.getURL().getPath());
//		    Properties prop = PropertiesLoaderUtils.loadProperties(resource);
			String webAppPath = FlexContext.getServletContext().getRealPath("");
            System.out.println("_____flex_____"+webAppPath);
            Properties prop = new Properties();
            prop.load(new FileInputStream(webAppPath+"/WEB-INF/topmessage.properties"));
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
