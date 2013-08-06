package com.hcctech.bookshelf.actions;

import java.util.Collection;
import java.util.List;

import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsFunction;
import com.hcctech.bookshelf.services.FunctionService;
import com.hcctech.bookshelf.vo.MenuTree;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class FunctionAction extends ActionSupport{

	private static final long serialVersionUID = -6120351689285220187L;
	
	private FunctionService functionService;
	
	private List<BsFunction> rows;

	private Collection<MenuTree> trees;
	
	/**
	 * 查询所有权限列表
	 * @return
	 */
	public String findAllFunction(){
		trees = functionService.findAllFunction();
		return SUCCESS;
	}
	
	public String findFunctionByRoleId(){
		BsAdminUser adminUser = (BsAdminUser)(ActionContext.getContext().getSession().get("adminuser"));
		if(null!=adminUser){
			rows = functionService.findFunctionByRoleId(adminUser.getBsRole().getRoleId());
		}else{
			rows = null;
		}
		return SUCCESS;
	}
	
	public void setFunctionService(FunctionService functionService) {
		this.functionService = functionService;
	}

	public List<BsFunction> getRows() {
		return rows;
	}

	public Collection<MenuTree> getTrees() {
		return trees;
	}
}
