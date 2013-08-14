package com.hcctech.bookshelf.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.pojo.GalleryObj;
import com.opensymphony.xwork2.ActionSupport;

public class TopMessageAction extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3017616859312341883L;
	
	private String[] topmessageTitle;
	private String[] topmessageHref;
	private List<File> topmessage;
	private List<String> topmessageFileName;
	private List<String> topmessageContentType;
	
	private String versionName;
	private String versionKey;
	private String versionHref;
	
	private String flag;
	private String msg;
	private String key;
	
	
	public String topmessage() throws Exception{
		
		String classPath = this.getClass().getResource("/").getPath();
		classPath=classPath.substring(1, classPath.indexOf("classes"));
		Properties prop = new Properties();
		prop.load(new FileInputStream(classPath+"topmessage.properties"));
		if(topmessage!=null && topmessage.size()>0) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			HttpServletRequest request = ServletActionContext.getRequest();
			String basepath = "images/topmessage";
			basepath += "/"+format.format(new Date()) ;
			String filePath=request.getSession().getServletContext().getRealPath("/");
			filePath += basepath;
			File _file = new File(filePath);
			if(!_file.exists()) _file.mkdirs();
			for(int i=0;i<topmessage.size();i++){
				String suffixName = topmessageFileName.get(i).substring(topmessageFileName.get(i).lastIndexOf("."));
				String targetName = getTimeRand();
				String fileSrc="/" + targetName + suffixName;
				File upload = new File(filePath+ fileSrc);
				FileUtils.copyFile(topmessage.get(i), upload);
				String value = topmessageTitle[i]+",/images/topmessage"+basepath+fileSrc+","+topmessageHref[i];
				prop.setProperty(topmessageTitle[i], value);
			}
		}
		if(StringUtils.isNotBlank(versionName)) {
			prop.setProperty("version", "version,"+versionName+","+versionHref);
		}
			
		FileOutputStream out = new FileOutputStream(classPath+"topmessage.properties");  
		prop.store(out, null);
		return SUCCESS;
	}
	
	
	public static String getTimeRand() {
		StringBuilder temp = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmssSSS");
		temp.append(sdf.format(new java.util.Date()));
		Random rand = new Random();
		for (int i = 0; i < 3; i++) {
			temp.append(rand.nextInt(10)) ;
		}
		return temp.toString();
	}
	
	public String del() throws Exception{
		if(key==null || StringUtils.isBlank(key)) {
			flag = "-1";
			msg = "系统错误！请稍后再试！";
			
		}else {
			try {
			String classPath = this.getClass().getResource("/").getPath();
			classPath=classPath.substring(1, classPath.indexOf("classes"));
			Properties prop = new Properties();
			prop.load(new FileInputStream(classPath+"topmessage.properties"));
			if(prop!=null && !prop.isEmpty()) {
				prop.remove(key);
				FileOutputStream out = new FileOutputStream(classPath+"topmessage.properties");  
				prop.store(out, null);  
				flag="1";
			}
			}catch(Exception e) {
				e.printStackTrace();
				flag="-1";
				msg = "系统错误！请稍后再试！";
			}
		}
		return SUCCESS;
	}


	public String getFlag() {
		return flag;
	}


	public void setTopmessageTitle(String[] topmessageTitle) {
		this.topmessageTitle = topmessageTitle;
	}


	public void setTopmessageHref(String[] topmessageHref) {
		this.topmessageHref = topmessageHref;
	}


	public void setTopmessage(List<File> topmessage) {
		this.topmessage = topmessage;
	}


	public void setTopmessageFileName(List<String> topmessageFileName) {
		this.topmessageFileName = topmessageFileName;
	}


	public void setTopmessageContentType(List<String> topmessageContentType) {
		this.topmessageContentType = topmessageContentType;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMsg() {
		return msg;
	}


	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}


	public void setVersionKey(String versionKey) {
		this.versionKey = versionKey;
	}


	public void setVersionHref(String versionHref) {
		this.versionHref = versionHref;
	}
	
	
}
