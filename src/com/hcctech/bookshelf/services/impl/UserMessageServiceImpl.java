package com.hcctech.bookshelf.services.impl;

import java.util.List;

import com.hcctech.bookshelf.dao.BsAreaDao;
import com.hcctech.bookshelf.dao.BsSchoolDao;

import com.hcctech.bookshelf.dao.BsWebUserDao;
import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.pojo.BsSchool;
import com.hcctech.bookshelf.pojo.BsUserInfo;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.UserMessageService;

public class UserMessageServiceImpl implements UserMessageService {
	private BsWebUserDao bsWebUserDaoImpl;
	private BsAreaDao bsAreaDaoImpl;
	private BsSchoolDao bsSchoolDao;
	
	public List<BsSchool> findSchoolList(Integer shengId){
		final String hql="from BsSchool b where b.bsArea.areaId=?";
		List<BsSchool> schoolList=bsSchoolDao.findByHql(hql, shengId);
		for (BsSchool bsSchool : schoolList) {
			bsSchool.setBsLicenseKeies(null);
			bsSchool.setBsArea(null);
		}
		return schoolList;
	}
	
	public BsWebUser loadMsgById(Integer wuId){
		final String hql="from BsWebUser b where b.wuId=?";
		BsWebUser bsWebUser=bsWebUserDaoImpl.findUniqueByHql(hql, wuId);
		bsWebUser.getBsUserInfo().getAge();
		return bsWebUser;
	}

	public void update(BsUserInfo user) {
		bsWebUserDaoImpl.update(user);

	}

	public List<BsArea> findAllArea() {
		String hql = "FROM BsArea AS b where b.bsArea.areaId = null";
		List<BsArea> list = bsAreaDaoImpl.findByHql(hql);
		for (BsArea area : list) {
			area.setBsArea(null);
			area.setBsAreas(null);
			area.setBsSchools(null);
		}
		return list;
	}

	public List<BsArea> findAllAreaPlus(Integer aid) {
		String hql = "FROM BsArea AS b WHERE b.bsArea.areaId = ?";
		List<BsArea> list = bsAreaDaoImpl.findByHql(hql, aid);
		for(BsArea area:list){
			area.setBsArea(null);
			area.setBsAreas(null);
			area.setBsSchools(null);
		}
		return list;
	}

	public void setBsWebUserDaoImpl(BsWebUserDao bsWebUserDaoImpl) {
		this.bsWebUserDaoImpl = bsWebUserDaoImpl;
	}

	public BsAreaDao getBsAreaDaoImpl() {
		return bsAreaDaoImpl;
	}

	public void setBsAreaDaoImpl(BsAreaDao bsAreaDaoImpl) {
		this.bsAreaDaoImpl = bsAreaDaoImpl;
	}

	public void setBsSchoolDao(BsSchoolDao bsSchoolDao) {
		this.bsSchoolDao = bsSchoolDao;
	}
	
}
