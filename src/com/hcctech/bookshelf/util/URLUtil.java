package com.hcctech.bookshelf.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class URLUtil {
	
	/**
	 * 获取当前地址栏完整路径及跳转到登录页面
	 * @param url
	 * @param param
	 * @return
	 */
	public static String getURL(String url ,String param) {
		if(param != null) {
			url += "?" + param ;
		}
		try {
			url = URLEncoder.encode(url, "utf-8");
			url = "/login.jsp?url=" + url;
		} catch (UnsupportedEncodingException e) {
			url = "/login.jsp?";
		}
		return url;
	}

}
