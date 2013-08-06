package com.hcctech.bookshelf.dao.support;

import java.util.List;

public interface HibernateEntityDaoUtil {
	public int executeSql(String sql, Object... values);
	public int executeHql(String sql, Object... values);
	
	@SuppressWarnings("rawtypes")
	public List findBySql(String sql, boolean cacheable,Class entityClass,Object... values);
	
	@SuppressWarnings("rawtypes")
	public Object findUniqueBySql(String sql,Class entityClass,Object... values);
}
