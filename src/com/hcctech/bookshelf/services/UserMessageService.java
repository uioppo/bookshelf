package com.hcctech.bookshelf.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;


import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.pojo.BsSchool;
import com.hcctech.bookshelf.pojo.BsUserInfo;
import com.hcctech.bookshelf.pojo.BsWebUser;


public interface UserMessageService {

	/**
	 * 更新用户信息
	 * 
	 * @param user
	 * @return
	 */
	@Transactional(readOnly = false, rollbackFor = Exception.class)
	public void update(BsUserInfo user);

	/**
	 *查取省份列表
	 * 
	 * @param aid
	 * @return
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<BsArea> findAllArea();

	/**
	 * 查找市的列表
	 * 
	 * @param aid
	 * @return
	 */
	@Transactional(readOnly = true, rollbackFor = Exception.class)
	public List<BsArea> findAllAreaPlus(Integer aid);

	/**
	 * 账户信息
	 * @param wuId  用户ID
	 * @return user
	 */
	@Transactional(readOnly = true)
	public BsWebUser loadMsgById(Integer wuId);

	/**
	 * @param shengId //查询学校的市ID 或县ID
	 * @return schoolList
	 */
	@Transactional(readOnly = true)
	public List<BsSchool> findSchoolList(Integer shengId);
	
}
