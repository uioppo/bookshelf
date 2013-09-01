package com.hcctech.bookshelf.flex;

import java.util.List;

import com.hcctech.bookshelf.dao.BsReaderDao;
import com.hcctech.bookshelf.pojo.BsReader;


public class ReaderFlex {
	
	private BsReaderDao bsReaderDao ;

	public BsReader findLastReader(String deviceName) {
		try {
			String category = "01";//
			if(deviceName.equalsIgnoreCase("pc")) {
				category = "01";
			}else {
				category = "02";
			}
			String hql = "select * from bs_reader b where b.category='"+category+"' order by b.create_time desc limit 1";
			List<BsReader> retList =  bsReaderDao.findByHql(hql, null);
			if(retList!=null && retList.size()>0) {
				return retList.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return null;
		
	}

	public void setBsReaderDao(BsReaderDao bsReaderDao) {
		this.bsReaderDao = bsReaderDao;
	}
	
	
	

	
	
	
}
