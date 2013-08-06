package com.hcctech.bookshelf.dao.impl;

import java.util.List;

import org.hibernate.Query;

import com.hcctech.bookshelf.dao.BsLicenseBatchDao;
import com.hcctech.bookshelf.dao.support.Assert;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsLicenseBatch;

public class BsLicenseBatchDaoImpl extends BaseHibernateEntityDaoImpl<BsLicenseBatch> implements BsLicenseBatchDao{
	
	@SuppressWarnings("unchecked")
	public Page<BsLicenseBatch> queryPage_(String sql,int pageNo,int pageSize,Object...values){
		Assert.hasText(sql);
		Assert.isTrue(pageNo>=1);
		String removeSelect = "";
		int beginPos = sql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos!=-1,sql);
		removeSelect = sql.substring(beginPos);
		String countQueryString = "select count(*) " + removeSelect;
		Query query_ = getSession().createQuery(countQueryString);
		long totalCount =  (long)(query_.list().size());
		if(totalCount<1){
			return new Page<BsLicenseBatch>();
		}
		Query query = createQuery(sql,values);
		List<BsLicenseBatch> list = query.setCacheable(cacheable).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		return new Page<BsLicenseBatch>(totalCount,list);
	}
}
