package com.hcctech.bookshelf.dao.impl;

import org.hibernate.Query;

import com.hcctech.bookshelf.dao.BsOrderDao;
import com.hcctech.bookshelf.pojo.BsOrder;

public class BsOrderDaoImpl extends BaseHibernateEntityDaoImpl<BsOrder> implements BsOrderDao{

	public int executeSQLByCount(String testIdSql) {
		Query query_ = getSession().createSQLQuery(testIdSql);
		Object o= query_.uniqueResult();
		return Integer.valueOf(String.valueOf(o));
	}

}
