package com.hcctech.bookshelf.dao;

import java.util.List;

import com.hcctech.bookshelf.pojo.BsMybook;

public interface BsMyBookDao extends BaseHibernateEntityDao<BsMybook>{
	@SuppressWarnings("rawtypes")
	public List findMyBookList(String sql,Object...values);
}
