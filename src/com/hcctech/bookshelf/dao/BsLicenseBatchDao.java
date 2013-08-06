package com.hcctech.bookshelf.dao;


import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsLicenseBatch;

public interface BsLicenseBatchDao extends BaseHibernateEntityDao<BsLicenseBatch>{

	public Page<BsLicenseBatch> queryPage_(String sql,int pageNo,int pageSize,Object...values);
}
