package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;

/**
 * @author zoujunpeng
 * @date 2012-08-22
 * @version 1.0
 * 后台用户管理service
 */
public interface AdminUserService {

	/**
	 * 后台登录
	 * @param adminUser
	 * @return
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public BsAdminUser login(BsAdminUser adminUser);
	
	/**
	 * @param pageNo 当前页
	 * @param pageSize 每页记录数
	 * @return Page.List记录
	 */
	@Transactional(readOnly=true)
	public Page<BsAdminUser> findUserByParam(int pageNo, int pageSize,String n,String v);
	
	/**
	 * @param bau 添加用户
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean saveAdminUser(BsAdminUser bau);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void deleteAdminUserByIdString(String ids);
	@Transactional(readOnly=true)
	public BsAdminUser findUserById(Integer id);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void updateUser(BsAdminUser badu, Integer tag);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void updateStatusById(BsAdminUser badu);
	/**
	 * @param bsAdminUser 修改用户信息
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void editUser(BsAdminUser bsAdminUser);
}
