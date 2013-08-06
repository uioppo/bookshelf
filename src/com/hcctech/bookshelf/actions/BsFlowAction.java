package com.hcctech.bookshelf.actions;

import java.util.List;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsFlow;
import com.hcctech.bookshelf.services.BsFlowService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zoujunpeng
 * 审批记录
 */
public class BsFlowAction extends ActionSupport{

	private static final long serialVersionUID = -7730311165828995240L;

	private BsFlowService bsFlowService;
	
	private List<BsFlow> rows;
	
	private int total;
	
	private int pageSize = 10;
	
	private int page = 1;
	
	private String searchName;
	
	private String searchValue;

	public String findAdviceList(){
		BsAdminUser adminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");
		if(adminUser == null){
			return ERROR;
		}
		Page<BsFlow> pageObject = null;
		if("管理员".equals(adminUser.getBsRole().getRoleName())){
			pageObject = bsFlowService.findAdviceList(null,page, pageSize, searchName, searchValue);
		}else {
			pageObject = bsFlowService.findAdviceList(adminUser.getRealName(),page, pageSize, searchName, searchValue);
		}
		if(null!=pageObject){
			rows = pageObject.getList();
			total = (int)pageObject.getTotalCount();
		}else{
			rows = null;
			total = 0;
		}
		return SUCCESS;
	}
	
	public void setBsFlowService(BsFlowService bsFlowService) {
		this.bsFlowService = bsFlowService;
	}

	public List<BsFlow> getRows() {
		return rows;
	}

	public void setRows(List<BsFlow> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
}
