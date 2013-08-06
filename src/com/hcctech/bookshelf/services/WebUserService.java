package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsWebUser;

public interface WebUserService {
	/**
	 * web用户管理
	 * @param wuEmail 用户名
	 * @param page当前页
	 * @param pageSize 每页条数
	 * @param sort排序字段
	 * @param order 排序方式
	 * @return Page<BsWebUser> page
	 */
	@Transactional(readOnly=true)
	Page<BsWebUser> loadWebUserList(String wuEmail, int page, int pageSize,
			String sort, String order);

	/**
	 * web用户管理   禁用启用
	 * @param bsWebUser 实体
	 * @return String msg
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	String updateWebUserStatus(BsWebUser bsWebUser);
	/**
	 * web用户管理   查询用户信息
	 * @param bsWebUser 实体
	 * @return BsWebUser bsWebUser
	 */
	@Transactional(readOnly=true)
	BsWebUser loadWebUserById(BsWebUser bsWebUser);
	/**
	 * web用户管理   查询用户信息
	 * @param bsWebUser 实体
	 * @return 
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void updateWebUser(BsWebUser bsWebUser);

	/**
	 * 定时删除未激活的用户
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void deleteNoneActiveUser();
}
