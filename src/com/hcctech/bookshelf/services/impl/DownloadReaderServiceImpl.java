package com.hcctech.bookshelf.services.impl;

import com.hcctech.bookshelf.dao.BsReaderDao;
import com.hcctech.bookshelf.pojo.BsReader;
import com.hcctech.bookshelf.services.DownloadReaderService;

public class DownloadReaderServiceImpl implements DownloadReaderService {
	
	private BsReaderDao bsReaderDao;
	
	public BsReader loadReaderPath(){
		String sql="select * from bs_reader where create_time=(select max(create_time) from bs_reader)";
		BsReader bsReader = bsReaderDao.findUniqueBySql(sql);
		return bsReader;
	}

	public void setBsReaderDao(BsReaderDao bsReaderDao) {
		this.bsReaderDao = bsReaderDao;
	}
}
