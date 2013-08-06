package com.hcctech.bookshelf.services.impl;

import com.hcctech.bookshelf.dao.BsLogDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsLog;
import com.hcctech.bookshelf.services.LogService;

public class LogServiceImpl implements LogService{
	
	private BsLogDao bsLogDao;

	public void setBsLogDao(BsLogDao bsLogDao) {
		this.bsLogDao = bsLogDao;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.LogService#findLogList(int, int)
	 */
	public Page<BsLog> findLogList(int pageNo,int pageSize,String name,String value){
		String hql = "from BsLog where 1=1";
		if(name!=null && "userName_".equals(name) && value!=null && !"".equals(value.trim())){
			hql += " and userName like '%"+value +"%'";
		}
		if(name!=null && "operateInfo_".equals(name) && value!=null && !"".equals(value.trim())){
			hql += " and operateInfo like '%"+value +"%'";
		}
		
		if(name!=null && "operateType_".equals(name) && value!=null && !"".equals(value.trim())){
			hql += " and operateType like '%"+value +"%'";
		}
		
		if(name!=null && "roleName_".equals(name) && value!=null && !"".equals(value.trim())){
			hql += " and roleName like '%"+value +"%'";
		}
		
		hql += " order by time desc ";
		
		Page<BsLog> page = bsLogDao.pagedQuery(hql, pageNo, pageSize);
		return page;
	}
}
