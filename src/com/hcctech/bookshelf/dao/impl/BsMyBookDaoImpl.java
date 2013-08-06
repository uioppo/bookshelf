package com.hcctech.bookshelf.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.hcctech.bookshelf.dao.BsMyBookDao;
import com.hcctech.bookshelf.pojo.BsMybook;

public class BsMyBookDaoImpl extends BaseHibernateEntityDaoImpl<BsMybook> implements BsMyBookDao{

	@SuppressWarnings("rawtypes")
	public List findMyBookList(String sql,Object...values){
		Query query=getSession().createSQLQuery(sql);
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		return query.list();
	}
	
}
