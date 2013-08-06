package com.hcctech.bookshelf.dao;

import java.util.List;

import com.hcctech.bookshelf.pojo.BsProducts;

public interface BsProductsDao extends BaseHibernateEntityDao<BsProducts>{
	public List<BsProducts> findBookByHql(String hql,int type,int start,int end) ;

}
