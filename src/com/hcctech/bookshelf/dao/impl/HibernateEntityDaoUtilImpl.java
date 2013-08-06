package com.hcctech.bookshelf.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hcctech.bookshelf.dao.support.HibernateEntityDaoUtil;

/**
 * @author RandyJie
 *	HibernateEntityDaoUtilImpl
 */
@SuppressWarnings({"rawtypes"})
public class HibernateEntityDaoUtilImpl extends HibernateDaoSupport implements HibernateEntityDaoUtil{
	/**
	 * 执行sql
	 * @param sql
	 * @param values
	 */
	public int executeSql(String sql,Object...values){
		Query query=getSession().createSQLQuery(sql);
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		return query.executeUpdate();
	}	
	
	/**
	 * 执行hql。
	 * @param hql
	 * @param values
	 */
	public int executeHql(String hql,Object...values){
		Query query=getSession().createQuery(hql);
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		return query.executeUpdate();
		
	}
	
	/**
	 * 根据SQL取得类表。
	 * @param sql
	 * @param values
	 * @return
	 */
	public List findBySql(String sql,boolean cacheable,Class entityClass,Object...values){
		Query query=getSession().createSQLQuery(sql).addEntity(entityClass);
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		return query.setCacheable(cacheable).list();
	}
	
	/**
	 * 根据Sql取得单一属性。
	 * @param sql
	 * @param values
	 * @return
	 */
	public Object findUniqueBySql(String sql,Class entityClass,Object... values){
        Query query=getSession().createSQLQuery(sql).addEntity(entityClass);
		
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		return query.uniqueResult();
	}

}
