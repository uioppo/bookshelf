package com.hcctech.bookshelf.actions;

import java.util.List;

import com.hcctech.bookshelf.pojo.BsRole;
import com.hcctech.bookshelf.services.RoleService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zoujunpeng
 * @date 2012-08-22
 * @version 1.0
 * 后台:角色列表、角色增删改查、角色权限、其它角色功能
 */
public class RoleAction extends ActionSupport{

	private static final long serialVersionUID = 7397846009957044138L;
	
	private RoleService roleService;
	
	private List<BsRole> rows;
	
	private BsRole bsRole;
	
	private boolean flag;
	
	private String ids;
	
	private String functionIds;
	
	private List<Integer> idlist;
	
	public String findAllRole(){
		rows = roleService.findAllRole();
		return SUCCESS;
	}
	
	public String saveRole(){
		flag = roleService.saveRole(bsRole);
		return SUCCESS;
	}
	
	public String findRoleById(){
		bsRole = roleService.findRoleById(bsRole);
		return SUCCESS;
	}
	
	public String deleteRole(){
		flag = roleService.deleteRoleByIdsString(ids);
		return SUCCESS;
	}
	
	public void updateRole(){
		roleService.updateRole(bsRole);
	}
	
	public void updateRoleFunction(){
		roleService.updateRoleFunction(bsRole, functionIds);
	}

	/**
	 * 查询角色对应的权限IDs
	 * @return
	 */
	public String findRoleFunctionIds(){
		idlist = roleService.findRoleFunctionIds(bsRole);
		return SUCCESS;
	}
	
	public List<BsRole> getRows() {
		return rows;
	}

	public void setRoleService(RoleService roleService) {
		this.roleService = roleService;
	}

	public BsRole getBsRole() {
		return bsRole;
	}

	public void setBsRole(BsRole bsRole) {
		this.bsRole = bsRole;
	}

	public boolean isFlag() {
		return flag;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}

	public List<Integer> getIdlist() {
		return idlist;
	}

}
