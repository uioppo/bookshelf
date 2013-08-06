package com.hcctech.bookshelf.web;

import com.hcctech.bookshelf.pojo.BsReader;
import com.hcctech.bookshelf.services.DownloadReaderService;
import com.hcctech.bookshelf.util.DomainUtil;
import com.opensymphony.xwork2.ActionSupport;

public class DownloadReaderAction extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8845121164732503509L;
	
	private DownloadReaderService downloadReaderService;
	private String readerPath;
	
	/**
	 * 查询最新版本的阅读器下载路径
	 * @return
	 */
	public String downloadReader(){
		BsReader bsReader=downloadReaderService.loadReaderPath();
		if(bsReader!=null&&bsReader.getExePath()!=null&&!("".equals(bsReader.getExePath()))){
			readerPath= DomainUtil.getFileDomain() + bsReader.getExePath();
			return SUCCESS;
		}else{
			return INPUT;
		}
	}

	public void setDownloadReaderService(DownloadReaderService downloadReaderService) {
		this.downloadReaderService = downloadReaderService;
	}

	public String getReaderPath() {
		return readerPath;
	}
}
