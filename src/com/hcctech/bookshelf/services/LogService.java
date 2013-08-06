package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsLog;

public interface LogService {
	
	@Transactional(readOnly=true)
	public Page<BsLog> findLogList(int pageNo,int pageSize,String name,String value);
}
