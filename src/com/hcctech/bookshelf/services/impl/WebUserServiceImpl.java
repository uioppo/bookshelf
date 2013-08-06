package com.hcctech.bookshelf.services.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.hcctech.bookshelf.dao.BsUserInfoDao;
import com.hcctech.bookshelf.dao.BsWebUserDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsUserInfo;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.WebUserService;

/**
 * @author apple
 *前台用户会员service
 */
public class WebUserServiceImpl implements WebUserService{
	private BsWebUserDao bsWebUserDao;
	private BsUserInfoDao bsUserInfoDao;
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.WebUserService#updateWebUser(com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public void updateWebUser(BsWebUser bsWebUser){
		if(bsWebUser!=null&&bsWebUser.getWuId()>0){
			final String hql="from BsWebUser b where b.wuId = ?";
			BsWebUser bsWebUser1=bsWebUserDao.findUniqueByHql(hql, bsWebUser.getWuId());
			if(bsWebUser1!=null){
				if(bsWebUser.getBsUserInfo().getBirthday()!=null){
					Date dd=bsWebUser.getBsUserInfo().getBirthday();
					Date today=new Date(); 
					Calendar calendar = GregorianCalendar.getInstance(); 
					Calendar calendar1 = GregorianCalendar.getInstance(); 
					calendar.setTime(dd);
					calendar1.setTime(today);
					bsWebUser.getBsUserInfo().setAge(calendar1.get(Calendar.YEAR)-calendar.get(Calendar.YEAR));
				}
				BsUserInfo bsUserInfo=bsWebUser.getBsUserInfo();
				bsUserInfo.setBsWebUser(bsWebUser1);
				bsUserInfoDao.update(bsUserInfo);
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.WebUserService#loadWebUserById(com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public BsWebUser loadWebUserById(BsWebUser bsWebUser){
		BsWebUser bsWebUser1=null;
		if(bsWebUser!=null&&bsWebUser.getWuId()>0){
			final String hql="from BsWebUser b where b.wuId = ?";
			bsWebUser1=bsWebUserDao.findUniqueByHql(hql, bsWebUser.getWuId());
			if(bsWebUser1!=null){
				bsWebUser1.getBsUserInfo();
				if(bsWebUser1.getBsUserInfo().getBirthday()!=null){
				SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd");
				String str=s.format(bsWebUser1.getBsUserInfo().getBirthday());
				bsWebUser1.getBsUserInfo().setBirthdayStr(str);
				}
			}
		}
		return bsWebUser1;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.WebUserService#updateWebUserStatus(com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public String updateWebUserStatus(BsWebUser bsWebUser){
		String msg="";
		if(bsWebUser!=null&&bsWebUser.getWuId()>0&&bsWebUser.getWuActivestatus()>=0){
			final String hql="from BsWebUser b where b.wuId = ?";
			BsWebUser bsWebUser1=bsWebUserDao.findUniqueByHql(hql, bsWebUser.getWuId());
			if(bsWebUser1!=null){
				bsWebUser1.setWuActivestatus(bsWebUser.getWuActivestatus());
			}else{
				msg="操作失败";
			}
		}else{
			msg="操作失败";
		}
		return msg;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.WebUserService#loadWebUserList(java.lang.String, int, int, java.lang.String, java.lang.String)
	 */
	public Page<BsWebUser> loadWebUserList(String wuEmail, int page, int pageSize,
			String sort, String order){
		final StringBuilder hql=new StringBuilder("from BsWebUser b ");
		if(wuEmail!=null&&!"".equals(wuEmail)){
			hql.append("where wuEmail like '%"+wuEmail+"%'");
		}
		if (sort != null && !"".equals(sort.trim()) && order != null
				&& !"".equals(order.trim())) {
			hql.append(" order by b.").append(sort).append(" ").append(order);
		} else {
			hql.append(" order by b.wuId asc");
		}
		Page<BsWebUser> userPage=bsWebUserDao.pagedQuery(hql.toString(), page, pageSize);
		BsUserInfo bsUserInfo=null;
		if(userPage!=null&&userPage.getList()!=null&&userPage.getList().size()>0){
			for(BsWebUser bsWebUser:userPage.getList()){
				bsUserInfo=bsWebUser.getBsUserInfo();
				bsUserInfo.setBsWebUser(null);
				bsWebUser.setBsLicenseKeies(null);
				bsWebUser.setBsMybooks(null);
				bsWebUser.setBsOrders(null);
				bsWebUser.setBsUserDrms(null);
			}
		}
		return userPage;
	}
	
	public void deleteNoneActiveUser(){
		//注册时间之后2俩天未激活，系统删除
		final String sql = "delete from bs_web_user where wu_activestatus=0 and date_add(wu_reg_time,interval 2 day)<=now()";
		bsWebUserDao.executeSQL(sql);
//		final String hql = "from BsWebUser where wuActivestatus = ?";
//		List<BsWebUser> list = bsWebUserDao.findByHql(hql, 0);
//		for(BsWebUser bs:list){
//			bsWebUserDao.remove(bs.getWuId());
//		}
	}
	
	public void setBsWebUserDao(BsWebUserDao bsWebUserDao) {
		this.bsWebUserDao = bsWebUserDao;
	}
	public void setBsUserInfoDao(BsUserInfoDao bsUserInfoDao) {
		this.bsUserInfoDao = bsUserInfoDao;
	}
	
}
