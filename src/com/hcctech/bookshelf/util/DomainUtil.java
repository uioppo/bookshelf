package com.hcctech.bookshelf.util;

import java.io.InputStream;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;


/**
 *	获取域名
 * @author 张萍萍
 * @date 2012-08-14
 * @version 1.0
 */
public class DomainUtil {
//	static Properties p = null;
//	static String domainName = null;
//	static String fileDomain = null ;
//	static String ftpserver = null ;
//	static String ftpport = null ;
//	static String ftpuser = null ;
//	static String ftppwd = null ;
//	static {
//		InputStream inputStream = DomainUtil.class.getClassLoader()
//				.getResourceAsStream("domain.properties");
//		p = new Properties();
//		try {
//			p.load(inputStream);
//		} catch (IOException e1) {
//
//		}
//	}
	private final static byte[] lock = new byte[0];
    private static PropertiesConfiguration config = null;//new PropertiesConfiguration();
    static {
        InputStream inputStream = DomainUtil.class.getClassLoader()
                .getResourceAsStream("domain.properties");
        synchronized(lock)
        {
            try {
                config = new PropertiesConfiguration();
                config.setListDelimiter((char)0);
                config.load(inputStream);
                config.setReloadingStrategy(new FileChangedReloadingStrategy());
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    
    public DomainUtil() {
        
    }
	/**
	 * 获得域名
	 * @return
	 */
	public static String getDomainName() {
//		if (domainName == null) {
//			domainName = p.getProperty("domain");
//		}
//		return domainName;
	    return config.getString("domain");
	}
	
	/**
	 * 文件服务器  HTTP地址
	 * @return
	 */
	public static String getFileDomain() {
//		if (fileDomain == null) {
//			fileDomain = p.getProperty("filedomain");
//		}
//		return fileDomain;
	    return config.getString("filedomain");
	}

	public static String getFtpserver() {
//		if (ftpserver == null) {
//			ftpserver = p.getProperty("ftpserver");
//		}
//		return ftpserver;
	    return config.getString("ftpserver");
	}

	public static String getFtpport() {
//		if (ftpport == null) {
//			ftpport = p.getProperty("ftpport");
//		}
//		return ftpport;
	    return config.getString("ftpport");
	}

	public static String getFtpuser() {
//		if (ftpuser == null) {
//			ftpuser = p.getProperty("ftpuser");
//		}
//		return ftpuser;
	    return config.getString("ftpuser");
	}

	public static String getFtppwd() {
//		if (ftppwd == null) {
//			ftppwd = p.getProperty("ftppwd");
//		}
//		return ftppwd;
	    return config.getString("ftppwd");
	}
	
	public static String getPepPlatformUrl() {
	    boolean isTest = config.getBoolean("istest");
	    if(isTest) {
	        return config.getString("testurl");
	    }
	    return config.getString("url");
	}
	
}
