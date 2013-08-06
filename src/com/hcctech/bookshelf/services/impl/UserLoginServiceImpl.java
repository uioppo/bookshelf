package com.hcctech.bookshelf.services.impl;

import java.sql.Timestamp;
import java.util.Date;

import com.hcctech.bookshelf.dao.BsWebUserDao;
import com.hcctech.bookshelf.pojo.BsUserInfo;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.UserLoginService;
import com.hcctech.bookshelf.util.Md5;

public class UserLoginServiceImpl implements UserLoginService{
	
	private BsWebUserDao bsWebUserDao ;
	
	/**
	 * 登录
	 */
//	public String login(Object... values) {
//		String wuEmail = null ;
//		values[1] = Md5.getMD5Str(values[1].toString());
//		String hql = "SELECT new BsWebUser(wuId,wuEmail,wuActivestatus,wuTime) FROM BsWebUser user where user.wuEmail = ? and user.wuPassword = ?";		
//		BsWebUser bsWebUser= bsWebUserDao.findUniqueByHql(hql, values);
//		if(bsWebUser != null) {
//			if(bsWebUser.getWuActivestatus() != 3) {
//				wuEmail = bsWebUser.getWuEmail();
//			}else {
//				Timestamp timestamp = bsWebUser.getWuTime();
//				long hour = ((new Date().getTime())- timestamp.getTime())/(1000*60*60);
//				if(hour == 0) {
//					wuEmail = "" ;
//					return wuEmail ;
//				} else {
//					wuEmail = bsWebUser.getWuEmail() ;
//					bsWebUser = bsWebUserDao.get(bsWebUser.getWuId());
//					bsWebUser.setWuActivestatus(1);
//				}
//			}
//			
//		}
//		
//		return wuEmail ;
//	}
	
	/**
	 * 前台用户登录
	 */
	public BsWebUser login(Object... values) {
		if(values[0]==null || values[1]==null){
			return null;
		}
		values[1] = Md5.getMD5Str(values[1].toString());
		String hql = "FROM BsWebUser user where user.wuEmail = ? and user.wuPassword = ?";		
		BsWebUser bsWebUser= bsWebUserDao.findUniqueByHql(hql, values);
		if(bsWebUser != null) {
			if(bsWebUser.getWuActivestatus() == 3) {		//如果锁定
				Timestamp timestamp = bsWebUser.getWuTime();
				long hour = ((new Date().getTime())- timestamp.getTime())/(1000*60*60l);
				if(hour != 0) {
					bsWebUser.setWuActivestatus(1);
					Timestamp lastLogin = new Timestamp(new Date().getTime());
					bsWebUser.setLastLogin(lastLogin);
					bsWebUserDao.update(bsWebUser);
				}
			}else if(bsWebUser.getWuActivestatus() == 1){
				Timestamp lastLogin = new Timestamp(new Date().getTime());
				bsWebUser.setLastLogin(lastLogin);
				bsWebUserDao.update(bsWebUser);
			}
		}else {
			return null;
		}
		bsWebUser.setBsLicenseKeies(null);
		bsWebUser.setBsMybooks(null);
		bsWebUser.setBsOrders(null);
		bsWebUser.setBsUserDrms(null);
		BsUserInfo bui = bsWebUser.getBsUserInfo();
		bui.setBsWebUser(null);
		return bsWebUser ;
	}
	
	/**
	 * 锁定用户
	 * @param username 登录账号
	 */
	public boolean lockUser(String username) {
		String hql = "FROM BsWebUser user where user.wuEmail = ?";		
		BsWebUser bsWebUser= bsWebUserDao.findUniqueByHql(hql, username);
		bsWebUser.setWuActivestatus(3);
		bsWebUser.setWuTime(new Timestamp(new Date().getTime()));
		try{
			bsWebUserDao.update(bsWebUser);
			return true ;
		}catch(Exception e) {
			return false ;
		}
	}

	public BsWebUserDao getBsWebUserDao() {
		return bsWebUserDao;
	}

	public void setBsWebUserDao(BsWebUserDao bsWebUserDao) {
		this.bsWebUserDao = bsWebUserDao;
	}

	
	
}
