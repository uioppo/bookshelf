package com.hcctech.bookshelf.actions;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.json.annotations.JSON;

import com.hcctech.bookshelf.util.DomainUtil;
import com.hcctech.bookshelf.util.ThirdVelocityHtmlUtil;
import com.opensymphony.xwork2.ActionSupport;

public class BannerAction extends ActionSupport {

	private static final long serialVersionUID = 4372829975606798098L;
	private ThirdVelocityHtmlUtil thirdVelocityHtmlUtil;
	private String bannerContentType ;
	private File banner ;
	private String bannerFileName ;
	private String msg ;
	
	public String uploadBanner(){
		if(bannerFileName==null&&"".equals(bannerFileName)){
			msg = "上传失败，请稍后再试！";
			return SUCCESS;
		}
		String type=bannerFileName.substring(bannerFileName.lastIndexOf(".")+1);
		if("jpg".equals(type)||"png".equals(type)||"gif".equals(type)||"jpeg".equals(type)||"swf".equals(type)){
			/*if(!("swf".equals(type))){
				boolean flag = checkImageSize(banner,1490+"",147+"");
				if(!flag){
					msg = "请选择1490*147的图片文件";
					return SUCCESS;
				}
			}*/
			HttpServletRequest request = ServletActionContext.getRequest();
			String path = request.getSession().getServletContext().getRealPath("/") + "/images/banner" ;
			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			String basepath = "/"+format.format(new Date()) ;
			path+=basepath;
			File _file = new File(path);
			if(!_file.exists()) _file.mkdirs();
			String bannerName=getTimeRand_()+"."+ type;
			InputStream in = null ;
			OutputStream out = null;
			try {
				in = new FileInputStream(banner);
				File destFile = new File(path,bannerName);
				out = new FileOutputStream(destFile);
				byte[] buffer = new byte[1024];
				int length = 0 ;
				while((length = in.read(buffer))>0){
	                out.write(buffer, 0, length);
	            }
				try{
					//模版
					String bannerFilePath=request.getSession().getServletContext().getRealPath("/")+"hot/banner.html";
					final Map<String, Object> model = new HashMap<String, Object>();
					String bannerUrl=DomainUtil.getDomainName()+request.getContextPath() + "/images/banner"+basepath+"/"+bannerName;
					String swfjsUrl=DomainUtil.getDomainName()+request.getContextPath() +"/js/swfobject.js";
					model.put("bannerUrl", bannerUrl);
					model.put("bannerType", type);
					model.put("swfjsUrl", swfjsUrl);
					thirdVelocityHtmlUtil.generateHtml(model, "bannerVM.vm", bannerFilePath);
					msg = "上传成功！";
				}catch(Exception e){
					msg = "上传失败，请稍后再试！";
					return SUCCESS;
				}
			} catch (FileNotFoundException e) {
				msg = "上传失败，请稍后再试！";
			} catch (IOException e) {
				msg = "上传失败，请稍后再试！";
			}finally{
				try {
					if(in != null){
						in.close();
					}
				} catch (IOException e) {
					msg = "上传失败，请稍后再试！";
				}
				try {
					if(out != null) {
						out.close();
					}
				} catch (IOException e) {
					msg = "上传失败，请稍后再试！";
				}
			}
			return SUCCESS;
		}else{
			msg = "上传失败，请稍后再试！";
			return SUCCESS;
		}
	}

	private boolean checkImageSize(File file , String width ,String height) {
		boolean flag = false ;
		BufferedImage src = null;
		try {
			src = javax.imageio.ImageIO.read(file);
			int w = src.getWidth(null);
			int h = src.getHeight(null);
			if(width.equals(w+"") && height.equals(h+"")) {
				flag = true ;
			}
		} catch (IOException e) {
			return flag ;
		}
		return flag ;
	}
	public static String getTimeRand_() {
		StringBuilder temp = new StringBuilder();
		SimpleDateFormat sdf = new SimpleDateFormat("ddHHmmssSSS");
		temp.append(sdf.format(new java.util.Date()));
		Random rand = new Random();
		for (int i = 0; i < 3; i++) {
			temp.append(rand.nextInt(10)) ;
		}
		return temp.toString();
	}
	public void setBannerContentType(String bannerContentType) {
		this.bannerContentType = bannerContentType;
	}

	public void setBanner(File banner) {
		this.banner = banner;
	}

	public void setBannerFileName(String bannerFileName) {
		this.bannerFileName = bannerFileName;
	}

	public String getMsg() {
		return msg;
	}
	@JSON(serialize=false)
	public String getBannerContentType() {
		return bannerContentType;
	}
	@JSON(serialize=false)
	public String getBannerFileName() {
		return bannerFileName;
	}

	public void setThirdVelocityHtmlUtil(ThirdVelocityHtmlUtil thirdVelocityHtmlUtil) {
		this.thirdVelocityHtmlUtil = thirdVelocityHtmlUtil;
	}
	
}
