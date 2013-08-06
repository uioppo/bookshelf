package com.hcctech.bookshelf.actions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsReader;
import com.hcctech.bookshelf.services.BsReaderService;
import com.hcctech.bookshelf.util.DomainUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BsReaderAction extends ActionSupport {
	
	private static final long serialVersionUID = 1146418799059235879L;
	private BsReaderService bsReaderService ;
	private BsReader bsReader ;
	private String msg ;
	
	private int pageSize = 10;			//每页行数
	private int page = 1;				//当前页
	private List<BsReader> rows ;		//结果集
	private long total ;				//总行数
	
	private String readerPath;
	
	public String addReader() {
		BsAdminUser bsAdminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");
		if(bsAdminUser == null){
			return "error";
		}
		bsReader.setCreateTime(new Timestamp(new Date().getTime()));
		bsReader.setOperator(bsAdminUser.getRealName());
		boolean flag = bsReaderService.addReader(bsReader);
		if(!flag) {
			msg = "异常，请稍候再试!点击“返回”，返回添加阅读器";
			return "input";
		}
		msg = "操作成功 。点击“返回”，返回添加阅读器";
		return "addReader";
	}
	
	public String loadReaderList() {
		Page<BsReader> pages = bsReaderService.loadReaderList(pageSize,page);
		if(pages != null) {
			rows = pages.getList();
			total = pages.getTotalCount();
		}
		return SUCCESS;
	}
	
	/**
	 * 下载阅读器
	 * @return
	 */
	public String downloadThisReader(){
		if(readerPath!=null&&!("".equals(readerPath))){
			readerPath= DomainUtil.getFileDomain() + readerPath;
			return SUCCESS;
		}else{
			return INPUT;
		}
	}
	public void setBsReaderService(BsReaderService bsReaderService) {
		this.bsReaderService = bsReaderService;
	}

	public void setBsReader(BsReader bsReader) {
		this.bsReader = bsReader;
	}
	public String getMsg() {
		return msg;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<BsReader> getRows() {
		return rows;
	}

	public long getTotal() {
		return total;
	}

	public String getReaderPath() {
		return readerPath;
	}

	public void setReaderPath(String readerPath) {
		this.readerPath = readerPath;
	}
	public BsReader getBsReader() {
		return bsReader;
	}
	

}
