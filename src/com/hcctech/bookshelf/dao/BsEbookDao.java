package com.hcctech.bookshelf.dao;


import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsEbook;

public interface BsEbookDao extends BaseHibernateEntityDao<BsEbook>{
	
	public Object executeSQLbyRe(String sql,Object... values);
	
	public Page<BsEbook> findListBySql_(String sql,int pageNo,int pageSize,Object...values);
	
	public Double getEbookVersion(String sql,String bookCode);
}
