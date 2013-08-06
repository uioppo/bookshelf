package com.hcctech.bookshelf.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 *	获取域名
 * @author 张萍萍
 * @date 2012-08-14
 * @version 1.0
 */
public class DomainUtil {
	static Properties p = null;
	static String domainName = null;
	static String fileDomain = null ;
	static String ftpserver = null ;
	static String ftpport = null ;
	static String ftpuser = null ;
	static String ftppwd = null ;
	static {
		InputStream inputStream = DomainUtil.class.getClassLoader()
				.getResourceAsStream("domain.properties");
		p = new Properties();
		try {
			p.load(inputStream);
		} catch (IOException e1) {

		}
	}
	/**
	 * 获得域名
	 * @return
	 */
	public static String getDomainName() {
		if (domainName == null) {
			domainName = p.getProperty("domain");
		}
		return domainName;
	}
	
	/**
	 * 文件服务器  HTTP地址
	 * @return
	 */
	public static String getFileDomain() {
		if (fileDomain == null) {
			fileDomain = p.getProperty("filedomain");
		}
		return fileDomain;
	}

	public static String getFtpserver() {
		if (ftpserver == null) {
			ftpserver = p.getProperty("ftpserver");
		}
		return ftpserver;
	}

	public static String getFtpport() {
		if (ftpport == null) {
			ftpport = p.getProperty("ftpport");
		}
		return ftpport;
	}

	public static String getFtpuser() {
		if (ftpuser == null) {
			ftpuser = p.getProperty("ftpuser");
		}
		return ftpuser;
	}

	public static String getFtppwd() {
		if (ftppwd == null) {
			ftppwd = p.getProperty("ftppwd");
		}
		return ftppwd;
	}
	
}
