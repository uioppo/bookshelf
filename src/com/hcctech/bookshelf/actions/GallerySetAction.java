package com.hcctech.bookshelf.actions;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;
import com.hcctech.bookshelf.pojo.GalleryObj;
import com.hcctech.bookshelf.util.ThirdVelocityHtmlUtil;
import com.opensymphony.xwork2.ActionSupport;

public class GallerySetAction extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 3017616859312341883L;
	
	private String[] galleryTitle;
	private String[] galleryHref;
	private List<File> gallery;
	private List<String> galleryFileName;
	private List<String> galleryContentType;
	private ThirdVelocityHtmlUtil thirdVelocityHtmlUtil;
	private String msg;
	private String galleryStr;
	private String newBook1;
	private String newBook2;
	public String gallerySet(){
			try {
				SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
				HttpServletRequest request = ServletActionContext.getRequest();
				String basepath = "images/gallery";
				basepath += "/"+format.format(new Date()) ;
				String filePath=request.getSession().getServletContext().getRealPath("/");
				filePath += basepath;
				File _file = new File(filePath);
				if(!_file.exists()) _file.mkdirs();
				List<GalleryObj> list=new ArrayList<GalleryObj>();
			   for(int i=0;i<gallery.size();i++){
				   String suffixName = galleryFileName.get(i).substring(galleryFileName.get(i).lastIndexOf("."));
				   String fileSrc="/" + getTimeRand() + suffixName;
				   File upload = new File(filePath+ fileSrc);
				   FileUtils.copyFile(gallery.get(i), upload);
				   GalleryObj galleryObj=new GalleryObj();
				   galleryObj.setGalleryTitle(galleryTitle[i]);
				   galleryObj.setGallerySrc(basepath+fileSrc);
				   galleryObj.setGalleryHref(galleryHref[i]);
				   list.add(galleryObj);
			   }
			   if(list!=null&&list.size()>0){
					final Map<String, Object> model = new HashMap<String, Object>();
					model.put("galleryList", list);
					model.put("galleryStr", "");
					newBook1 = StringUtils.trimToEmpty(newBook1);
					newBook2 = StringUtils.trimToEmpty(newBook2);
					model.put("newBook1", newBook1);
					model.put("newBook2", newBook2);
					String indexFilePath=request.getSession().getServletContext().getRealPath("/")+"index.html";
					thirdVelocityHtmlUtil.generateHtml(model, "indexVM.vm", indexFilePath);
					thirdVelocityHtmlUtil.generateHtml(model, "newbookVM.vm", request.getSession().getServletContext().getRealPath("/")+"hot/aa.html");
				}
			   msg="上传成功！";
			  } catch (IOException e) {
				  msg="上传失败！";
			  }
		return SUCCESS;
	}
	public String newBookSave() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String indexFilePath=request.getSession().getServletContext().getRealPath("/")+"index.html";
		final Map<String, Object> model = new HashMap<String, Object>();
		galleryStr = StringUtils.trimToEmpty(galleryStr);
		newBook1 = StringUtils.trimToEmpty(newBook1);
		newBook2 = StringUtils.trimToEmpty(newBook2);
		model.put("newBook1", newBook1);
		model.put("newBook2", newBook2);
		model.put("galleryList", null);
		model.put("galleryStr",galleryStr);
		thirdVelocityHtmlUtil.generateHtml(model, "indexVM.vm", indexFilePath);
		thirdVelocityHtmlUtil.generateHtml(model, "newbookVM.vm", request.getSession().getServletContext().getRealPath("/")+"hot/aa.html");
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
	public void setGalleryTitle(String[] galleryTitle) {
		this.galleryTitle = galleryTitle;
	}
	public void setGalleryHref(String[] galleryHref) {
		this.galleryHref = galleryHref;
	}
	public void setGallery(List<File> gallery) {
		this.gallery = gallery;
	}
	public void setGalleryFileName(List<String> galleryFileName) {
		this.galleryFileName = galleryFileName;
	}
	public void setGalleryContentType(List<String> galleryContentType) {
		this.galleryContentType = galleryContentType;
	}
	@JSON(serialize=false)
	public List<String> getGalleryFileName() {
		return galleryFileName;
	}
	@JSON(serialize=false)
	public List<String> getGalleryContentType() {
		return galleryContentType;
	}
	public void setThirdVelocityHtmlUtil(ThirdVelocityHtmlUtil thirdVelocityHtmlUtil) {
		this.thirdVelocityHtmlUtil = thirdVelocityHtmlUtil;
	}
	public String getMsg() {
		return msg;
	}
	public void setNewBook1(String newBook1) {
		this.newBook1 = newBook1;
	}
	public void setNewBook2(String newBook2) {
		this.newBook2 = newBook2;
	}
	public void setGalleryStr(String galleryStr) {
		this.galleryStr = galleryStr;
	}
	
}
