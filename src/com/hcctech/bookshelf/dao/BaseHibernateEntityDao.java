package com.hcctech.bookshelf.dao;

import java.io.Serializable;
import java.util.List;

import com.hcctech.bookshelf.dao.support.Page;


/**
 * @author RandyJie
 * @param <T>
 */
public interface BaseHibernateEntityDao<T> {
	public void setCacheable(boolean cacheable);

	public T get(Serializable id);

	public T load(Serializable id);

	public void save(Object o);

	public void update(Object o);

	public void merge(Object o);
	
	public void remove(Serializable id);

	public List<T> findByHql(String hql, Object... values);

	public List<T> findBySql(String sql, Object... values);

	public T findUniqueByHql(String hql, Object... values);
	
	public void executeSQL(String sql,Object... values);

	public T findUniqueBySql(String sql, Object... values);

	public com.hcctech.bookshelf.dao.support.Page<T> pagedSqlQuery(String sql, int pageNo, int pageSize,
			Object... values);

	public Page<T> pagedQuery(String hql, int pageNo, int pageSize,
			Object... values);
}
