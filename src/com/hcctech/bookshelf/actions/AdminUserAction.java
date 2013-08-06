package com.hcctech.bookshelf.actions;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.services.AdminUserService;
import com.hcctech.bookshelf.util.Md5;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zoujunpeng
 * @date 2012-08-22
 * @version 1.0
 * 后台:用户列表、用户增删改查、其它后台用户相关
 */
public class AdminUserAction extends ActionSupport{

	private static final long serialVersionUID = 3290200363056942166L;
	
	private AdminUserService adminUserService;
	
	private int pageSize = 10;
	
	private int page = 1;
	
	private List<BsAdminUser> rows;
	
	private int total;
	
	private BsAdminUser bsAdminUser;
	
	private String searchValue;
	
	private String searchName;
	
	private boolean flag;
	
	private String ids;
	
	private String password;
	
	private Integer tag;
	
	private static final Log logger = LogFactory.getLog(AdminUserAction.class);
	/**
	 * 账户信息的提交
	 * @return
	 */
	public String editUser(){
		adminUserService.editUser(bsAdminUser);
		logger.info("用户修改了账户信息");
		return SUCCESS;
	}
	/**
	 * 账户信息的验证密码
	 * @return
	 */
	public String validatePassword(){
		if(password!=null&&!"".equals(password)){
			password=Md5.getMD5Str(password);
			BsAdminUser bsAdminUser1=(BsAdminUser) ActionContext.getContext().getSession().get("adminuser");
			if(bsAdminUser1!=null&&bsAdminUser1.getPassword()!=null&&!"".equals(bsAdminUser1.getPassword())&&bsAdminUser1.getPassword().equals(password)){
				flag=true;
			}else{
				flag=false;
			}
		}else{
			flag=false;
		}
		return SUCCESS;
	}
	public String findAllUser(){
		Page<BsAdminUser> pageT = adminUserService.findUserByParam(page,pageSize,searchName,searchValue);
		if(pageT!=null){
			rows = pageT.getList();
			total = (int)pageT.getTotalCount();
		}else{
			rows = null;
			total = 0;
		}
		
		return SUCCESS;
	}
	
	public String saveUser(){
		flag = adminUserService.saveAdminUser(bsAdminUser);
		return SUCCESS;
	}

	public void deleteUser(){
		adminUserService.deleteAdminUserByIdString(ids);
	}
	
	public String findUserById(){
		bsAdminUser = adminUserService.findUserById(bsAdminUser.getAuId());
		return SUCCESS;
	}
	
	public void updateUser(){
		adminUserService.updateUser(bsAdminUser,tag);
	}
	
	public void updateStatusById(){
		adminUserService.updateStatusById(bsAdminUser);
	}
	
	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}


	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<BsAdminUser> getRows() {
		return rows;
	}

	public int getTotal() {
		return total;
	}

	public void setBsAdminUser(BsAdminUser bsAdminUser) {
		this.bsAdminUser = bsAdminUser;
	}

	public boolean isFlag() {
		return flag;
	}

	public BsAdminUser getBsAdminUser() {
		return bsAdminUser;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getSearchValue() {
		return searchValue;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setTag(Integer tag) {
		this.tag = tag;
	}
	
}