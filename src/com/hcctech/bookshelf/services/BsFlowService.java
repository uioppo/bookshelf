package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsFlow;

public interface BsFlowService {

	@Transactional(readOnly = true)
	public Page<BsFlow> findAdviceList(String realName, int pageNo,int pageSize,String name,String value);
}
