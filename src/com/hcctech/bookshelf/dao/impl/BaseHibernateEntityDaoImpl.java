package com.hcctech.bookshelf.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.hcctech.bookshelf.dao.BaseHibernateEntityDao;
import com.hcctech.bookshelf.dao.support.Assert;
import com.hcctech.bookshelf.dao.support.Page;


/**
 * @author RandyJie
 *
 * @param <T>
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class BaseHibernateEntityDaoImpl<T> extends HibernateDaoSupport implements BaseHibernateEntityDao<T>  {
    private Class<T> entityClass;
	
	public boolean cacheable;
	
	public void setCacheable(boolean cacheable){
		this.cacheable=cacheable;
	}

	public BaseHibernateEntityDaoImpl() {
		super();
		entityClass =(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	/**
	 * 利用get取得对象。
	 * @param id
	 * @return
	 */
	public T get(Serializable id) {
		return (T)getHibernateTemplate().get(entityClass, id);
	}
	/**
	 * 利用load取得对象。
	 * @param id
	 * @return
	 */
	public T load(Serializable id) {
		return (T)getHibernateTemplate().load(entityClass, id);
	}
	/**
	 * 删除对象。
	 * @param o
	 */
	public void remove(Serializable id) {
		getHibernateTemplate().delete(get(id));		
	}
	
	/**
	 * 保存对象。
	 * @param o
	 */
	public void save(Object o) {
		getHibernateTemplate().save(o);
	}	
	/**
	 * 更新对象。
	 * @param o
	 */
	public void update(Object o) {
		getHibernateTemplate().update(o);
	}
	
	/**
	 * 合并
	 * @param o
	 */
	public void merge(Object o){
		getHibernateTemplate().merge(o);
	}
	
	/**
	 * 根据hql取得列表。
	 * @param hql
	 * @param values
	 * @return
	 */
	public List<T> findByHql(String hql, Object... values) {
		Query query=getSession().createQuery(hql);
		
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		
		return query.setCacheable(cacheable).list();
	}
	
	/**
	 * 根据SQL取得类表。
	 * @param sql
	 * @param values
	 * @return
	 */
	public List<T> findBySql(String sql,Object...values){
		Query query=getSession().createSQLQuery(sql).addEntity(entityClass);
		
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		
		return query.setCacheable(cacheable).list();
		
	}
	
	/**
	 * 根据hql取得唯一对象。
	 * @param hql
	 * @param values
	 * @return
	 */
	public T findUniqueByHql(String hql, Object... values) {
		Query query=getSession().createQuery(hql);
		
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		
		return (T) query.uniqueResult();
	}
	
	/**
	 * 根据Sql取得单一属性。
	 * @param sql
	 * @param values
	 * @return
	 */
	public T findUniqueBySql(String sql,Object... values){
        Query query=getSession().createSQLQuery(sql).addEntity(entityClass);
		
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		
		return (T)query.uniqueResult();
	}
	
	/**
	 * 执行sql，更新、删除
	 * @param sql
	 * @param values
	 */
	public void executeSQL(String sql,Object... values){
		Query query=getSession().createSQLQuery(sql);
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		query.executeUpdate();
	}	
	/**
	 * 分页 通过hql进行
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public Page<T> pagedSqlQuery(String sql,int pageNo,int pageSize,Object...values){
		Assert.hasText(sql);
		Assert.isTrue(pageNo>=1);
		String countQueryString = "select count(*) " + removeSelect(removeOrders(sql));
		List<T> countList = findBySql(countQueryString, values);
		long totalCount =  (Long) countList.get(0);
		if(totalCount<1){
			return new Page();
		}
		Query query = createSqlQuery(sql,values);
		List<T> list = query.setCacheable(cacheable).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		return new Page(totalCount,list);
	}
	
	/**
	 * 分页 通过hql进行
	 * @param hql
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return
	 */
	public Page<T> pagedQuery(String hql,int pageNo,int pageSize,Object...values){
		Assert.hasText(hql);
		Assert.isTrue(pageNo>=1);
		String countQueryString = "select count(*) " + removeSelect(removeOrders(hql));
		
		List<T> countList = findByHql(countQueryString, values);
		long totalCount =  (Long) countList.get(0);
		if(totalCount<1){
			return new Page();
		}
		//int startIndex = Page.getStartOfPage(pageNo, pageSize);
		Query query = createQuery(hql,values);
		List<T> list = query.setCacheable(cacheable).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		return new Page(totalCount,list);
	}
	
	/** 
     * 创建Criteria对象. 
     * @param criterions 可变的Restrictions条件列表 
     */ 
    public  Criteria createCriteria(Criterion... criterions) { 
        Criteria criteria = getSession().createCriteria(entityClass); 
        for (Criterion c : criterions) { 
            criteria.add(c); 
        } 
        return criteria; 
    }    
    /** 
     * 创建Criteria对象，带排序字段与升降序字段. 
     */ 
	public  Criteria createCriteria(String orderBy, boolean isAsc, Criterion... criterions) { 
        
        Criteria criteria = createCriteria(criterions); 
        if (isAsc) 
            criteria.addOrder(Order.asc(orderBy)); 
        else 
            criteria.addOrder(Order.desc(orderBy)); 
        return criteria; 
    }
	
	/**
	 * 创建一个Query对象。
	 * @param hql
	 * @param values
	 * @return
	 */
	public Query createQuery(String hql,Object...values){
		Assert.hasText(hql);
		Query query = getSession().createQuery(hql);
		for(int i = 0;i<values.length;i++){
			query.setParameter(i, values[i]);
		}
		return query;
	}
	
	/**
	 * 创建一个Query对象。
	 * @param hql
	 * @param values
	 * @return
	 */
	public Query createSqlQuery(String sql,Object...values){
		Assert.hasText(sql);
		Query query = getSession().createSQLQuery(sql);
		for(int i = 0;i<values.length;i++){
			query.setParameter(i, values[i]);
		}
		return query;
	}
	
	/**
	 * 去除hql的select子句。
	 * @param hql
	 * @return
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeSelect(String hql){
		Assert.hasText(hql);
		int beginPos = hql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos!=-1,hql);
		return hql.substring(beginPos);
	}
	
	/**
	 * 去除hql的orderBy子句。
	 * @param hql
	 * @return
	 * @see #pagedQuery(String,int,int,Object[])
	 */
	private static String removeOrders(String hql) {
		Assert.hasText(hql);
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(hql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
}
