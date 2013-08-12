package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.pojo.BsWebUser;
/**
 * @author zhagnpingping
 * @date 2012-08-13
 * @version
 * web用户注册service接口
 */
public interface RegisterService {
	
	/**
	 * @param bsWebUser web用户
	 * @return flag 判断标志
	 * 
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	int registerWebUser(BsWebUser bsWebUser);
	
	/**
	 * 客户端注册用户 （不发验证邮件）
	 * @param bsWebUser web用户
	 * @return flag 判断标志 1：成功 0：系统错误 502：用户名已经存在
	 * 
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	int registerWebUser4Client(BsWebUser bsWebUser);
	
	/**
	 * @param bsWebUser web用户
	 * @return flag 判断标志
	 * 
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	int registerActivate(BsWebUser bsWebUser);

	/**
	 * @param bsWebUser web用户
	 * @return flag 判断标志
	 * 
	 */
	@Transactional(readOnly=true)
	int validateUserName(BsWebUser bsWebUser);
	
	/**
	 * @param bsWebUser web用户
	 * 
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void lostPassword(BsWebUser bsWebUser);
	
	
	/**
	 * @param bsWebUser web用户
	 * 
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	String lostPassword4Client(BsWebUser bsWebUser);
	
	/**
	 * @param bsWebUser web用户
	 * @return flag 判断标志
	 */
	@Transactional(readOnly=true)
	int validateToken(BsWebUser bsWebUser);

	/**
	 * @param bsWebUser web用户
	 * @return flag 判断标志
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void updatePassword(BsWebUser bsWebUser);
	
}
