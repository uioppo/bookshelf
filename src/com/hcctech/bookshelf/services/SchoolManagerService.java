package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsSchool;

/**
 * 后台学校管理
 * @author apple
 *
 */
public interface SchoolManagerService {

	/**
	 * 查询学校列表
	 * @param schoolName 学校名
	 * @param page 当前页
	 * @param pageSize	每页条数
	 * @param sort	排序字段
	 * @param order 排序类型
	 * @return
	 */
	@Transactional(readOnly=true)
	Page<BsSchool> loadSchoolList(String schoolName, int page, int pageSize,
			String sort, String order);

	/**
	 * 删除学校
	 * @param ids 学校ID字符串
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void deleteSchool(String ids);

	/**
	 * 根据ID查询学校
	 * @param bsSchool 学校实体
	 * @return
	 */
	@Transactional(readOnly=true)
	BsSchool loadSchoolById(BsSchool bsSchool);
	
	/**
	 * 添加或者修改学校
	 * @param bsSchool 学校实体
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void addOrUpdate(BsSchool bsSchool);

}
