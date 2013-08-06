package com.hcctech.bookshelf.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.springframework.ui.velocity.VelocityEngineUtils;


/**
 * @author randyjie
 * Velocity 生成静态页面
 */
public class ThirdVelocityHtmlUtil {
	private VelocityEngine velocityEngine;

	
	/**
	 * @param model  
	 * 参数：Map<String, Object>
	 * @param vmfile
	 * 模板文件
	 * @param htmlFile
	 * 要生成的网页路径
	 */
	public void generateHtml(final Map<String, Object> model, final String vmfile, final String htmlFile) {
		FileOutputStream outStream = null;
		 OutputStreamWriter writer = null;
		 try {
			 outStream = new FileOutputStream(new File(htmlFile));//生成静态文件名为index.html    
			 writer = new OutputStreamWriter(outStream,"UTF-8");             
			// BufferedWriter sw = new BufferedWriter(writer);    
			 VelocityEngineUtils.mergeTemplate(velocityEngine, vmfile, "UTF-8", model, writer);
			
		} catch (FileNotFoundException e) {
			
		} catch (UnsupportedEncodingException e) {
			
		} catch (VelocityException e) {
			
		} finally{
			try {
				if(writer!=null){
					 writer.flush();    
					 writer.close();  
				}
				if(outStream!=null)
					 outStream.close();
			} catch (IOException e) {
				
			}
		}
		
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}
