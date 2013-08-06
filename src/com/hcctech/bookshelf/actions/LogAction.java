package com.hcctech.bookshelf.actions;

import java.util.List;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsLog;
import com.hcctech.bookshelf.services.LogService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zoujunpeng
 * @date 2012-08-24
 * @version 1.0
 * 系统日志查看,查询条件...
 */
public class LogAction extends ActionSupport{
	
	private static final long serialVersionUID = 7156800286360871444L;

	private LogService logService;
	
	private List<BsLog> rows;
	
	private int pageSize = 10;
	
	private int page = 1;
	
	private int total;
	
	private String searchValue;
	
	private String searchName;

	/**
	 * 查询日志
	 * 包括搜索、分页
	 * @return
	 */
	public String findLogList(){
		Page<BsLog> pageObject = logService.findLogList(page, pageSize,searchName,searchValue);
		if(pageObject!=null){
			rows = pageObject.getList();
			total = (int)pageObject.getTotalCount();
		}else{
			rows = null;
			total = 0;
		}
		return SUCCESS;
	}
	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	public List<BsLog> getRows() {
		return rows;
	}

	public int getTotal() {
		return total;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
}
