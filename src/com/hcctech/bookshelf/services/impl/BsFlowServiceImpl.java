package com.hcctech.bookshelf.services.impl;

import com.hcctech.bookshelf.dao.BsFlowDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsFlow;
import com.hcctech.bookshelf.services.BsFlowService;

/**
 * @author zoujunpeng
 *
 */
public class BsFlowServiceImpl implements BsFlowService{

	private BsFlowDao bsFlowDao;

	public Page<BsFlow> findAdviceList(String realName,int pageNo,int pageSize,String name,String value){
		String hql = "from BsFlow where isPass!=0 and isPass != 3 and (type='1' or type='2')";
		if(realName != null) {
			hql += " and (toName = '"+realName+"' or fromName = '" + realName + "') ";
		}
		if(name!=null && !"".equals(name) && value!=null && !"".equals(value.trim())){
			if("bookName_".equals(name)){
				hql += " and objectName like '%"+value+"%'";
			}
			if("toName_".equals(name)){
				hql += " and toName like '%"+value+"%'";
			}
			if("fromName_".equals(name)){
				hql += " and fromName like '%"+value+"%'";
			}
			if("ider_".equals(name)){
				hql += " and idea like '%"+value+"%'";
			}
		}
		hql += " order by time desc";
		Page<BsFlow> p = bsFlowDao.pagedQuery(hql, pageNo, pageSize);
		return p;
	}
	
	public void setBsFlowDao(BsFlowDao bsFlowDao) {
		this.bsFlowDao = bsFlowDao;
	}
}
