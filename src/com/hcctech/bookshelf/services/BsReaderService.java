package com.hcctech.bookshelf.services;


import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsReader;

public interface BsReaderService {
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean addReader(BsReader bsReader);
	
	@Transactional(readOnly=true)
	public Page<BsReader> loadReaderList(Integer pageSize ,Integer page);

}
