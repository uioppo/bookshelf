package com.hcctech.bookshelf.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.hcctech.bookshelf.dao.BsProductsDao;
import com.hcctech.bookshelf.pojo.BsProducts;
public class BsProductsDaoImpl extends BaseHibernateEntityDaoImpl<BsProducts> implements BsProductsDao{
	
	/**
	 * 限制返回条数
	 */
	@SuppressWarnings("unchecked")
	public List<BsProducts> findBookByHql(String hql,int type,int start,int end) {
		Query query=getSession().createQuery(hql);
		query.setParameter(0,type);
		query.setFirstResult(start);
		query.setMaxResults(end);
		return query.setCacheable(cacheable).list();
	}
	
}
