package com.hcctech.bookshelf.services.impl;

import com.hcctech.bookshelf.dao.BsReaderDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsReader;
import com.hcctech.bookshelf.services.BsReaderService;

public class BsReaderServiceImpl implements BsReaderService {

	private BsReaderDao bsReaderDao ;
	
	public boolean addReader(BsReader bsReader) {
		boolean flag = false ;
		try {
			bsReaderDao.save(bsReader);
			flag = true;
		}catch(Exception e){
		}
		return flag;
	}

	public Page<BsReader> loadReaderList(Integer pageSize ,Integer page) {
		String hql = "from BsReader b order by b.createTime desc";
		try {
			return bsReaderDao.pagedQuery(hql, page, pageSize);
		}catch(Exception e){
			return null;
		}
		
	}

	public void setBsReaderDao(BsReaderDao bsReaderDao) {
		this.bsReaderDao = bsReaderDao;
	}
	
	
}
