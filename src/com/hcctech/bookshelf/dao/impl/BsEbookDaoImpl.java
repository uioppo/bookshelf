package com.hcctech.bookshelf.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;

import com.hcctech.bookshelf.dao.BsEbookDao;
import com.hcctech.bookshelf.dao.support.Assert;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsEbook;

public class BsEbookDaoImpl extends BaseHibernateEntityDaoImpl<BsEbook> implements BsEbookDao{

	public Object executeSQLbyRe(String sql,Object... values){
		Query query= getSession().createSQLQuery(sql);
		int i=0;
		for(Object obj:values){
			query.setParameter(i,obj);
			i++;
		}
		return query.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public Page<BsEbook> findListBySql_(String sql,int pageNo,int pageSize,Object...values){
		Assert.hasText(sql);
		Assert.isTrue(pageNo>=1);
		String removeSelect = "";
		int beginPos = sql.toLowerCase().indexOf("from");
		Assert.isTrue(beginPos!=-1,sql);
		removeSelect = sql.substring(beginPos);
		String countQueryString = "select count(distinct book_code) " + removeSelect;
		SQLQuery query_ = getSession().createSQLQuery(countQueryString);
//		for(int i = 0;i<values.length;i++){
//			query_.setParameter(i, values[i]);
//		}
		int totalCount =  Integer.parseInt(query_.list().get(0) + "");
		if(totalCount<1){
			return new Page<BsEbook>();
		}
		SQLQuery query = getSession().createSQLQuery(sql);
//		for(int i = 0;i<values.length;i++){
//			query.setParameter(i, values[i]);
//		}
		List<Object[]> list = query.setCacheable(cacheable).setFirstResult((pageNo-1)*pageSize).setMaxResults(pageSize).list();
		List<BsEbook> list_ = new ArrayList<BsEbook>();
		try {
			for(Object[] o:list){
				BsEbook be = new BsEbook();
				be.setId(Integer.parseInt(o[0].toString()));
				be.setBookCode(o[1].toString());
				be.setBookName(o[2].toString());
				be.setBookPath(o[3].toString());
				be.setBookSize(Integer.parseInt(o[4].toString()));
				be.setCreateTime((Timestamp)o[5]);
				be.setUploadTime((Timestamp)o[6]);
				be.setVersion(Double.parseDouble(o[7].toString()));
				be.setBookKey(o[8].toString());
				be.setBookUrl(o[9].toString());
				be.setOperator(o[10].toString());
				be.setVersionStr(o[11].toString());
				list_.add(be);
			}
		} catch (NumberFormatException e) {
			
		}
		return new Page<BsEbook>(totalCount,list_);
	}
	
	public Double getEbookVersion(String sql,String bookCode){
		Query query= getSession().createSQLQuery(sql);
		query.setParameter(0, bookCode);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		Double d = null;
		try {
			if(list!=null && list.size()>0){
				d = Double.valueOf(list.get(0).toString());
			}
		} catch (NumberFormatException e) {
			
		}
		return d;
	}

}
