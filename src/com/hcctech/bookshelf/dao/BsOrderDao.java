package com.hcctech.bookshelf.dao;

import com.hcctech.bookshelf.pojo.BsOrder;

public interface BsOrderDao extends BaseHibernateEntityDao<BsOrder>{

	int executeSQLByCount(String testIdSql);

}
