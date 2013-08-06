package com.hcctech.bookshelf.services.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import com.hcctech.bookshelf.dao.BsAdminUserDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsFunction;
import com.hcctech.bookshelf.services.AdminUserService;
import com.hcctech.bookshelf.util.Md5;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author randyjie
 * 后台用户
 */
public class AdminUserServiceImpl implements AdminUserService {

	private BsAdminUserDao bsAdminUserDao;
	
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.AdminUserService#editUser(com.hcctech.bookshelf.pojo.BsAdminUser)
	 */
	public void editUser(BsAdminUser bsAdminUser){
		BsAdminUser bsAdminUser1=(BsAdminUser) ActionContext.getContext().getSession().get("adminuser");
		bsAdminUser1.setPassword(Md5.getMD5Str(bsAdminUser.getPassword()));
		bsAdminUser1.setEmail(bsAdminUser.getEmail());
		bsAdminUserDao.update(bsAdminUser1);
		ActionContext.getContext().getSession().remove("adminuser");
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.AdminUserService#login(com.hcctech.bookshelf.pojo.BsAdminUser)
	 */
	public BsAdminUser login(BsAdminUser adminUser) {
//		//验证邮箱
//		if(validateUtil.checkEmail(adminUser.getEmail()))
//			return false;
		
		//--------------------------//
		final String hql = "from BsAdminUser bs where bs.userName = ? and bs.status=1";
		BsAdminUser user = bsAdminUserDao.findUniqueByHql(hql, adminUser.getUserName());
		
		if(user!=null){
			if(user.getStatus()==null||!user.getStatus().equals(1))
				return null;
			if(user.getPassword()!=null&&user.getPassword().equalsIgnoreCase(Md5.getMD5Str(adminUser.getPassword()))){
				user.getBsRole().getRoleId();
				user.getBsRole().getRoleName();
				@SuppressWarnings("unchecked")
				Set<BsFunction> list = (Set<BsFunction>)user.getBsRole().getBsFunctions();
				for (BsFunction bsFunction : list) {
					bsFunction.getFunctionUrl();
					//========
				}
				BsAdminUser user2 = user;
				user.setLastTime(new Timestamp(new Date().getTime()));
				bsAdminUserDao.update(user);
				return user2;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.AdminUserService#findUserByParam(com.hcctech.bookshelf.dao.support.Page)
	 */
	public Page<BsAdminUser> findUserByParam(int pageNo,int pageSize,String name,String value){
		String hql = "from BsAdminUser where 1=1";
		if(name!=null && "userName_".equals(name)){
			hql += " and userName like '%"+value +"%'";
		}
		if(name!=null && "realName_".equals(name)){
			hql += " and realName like '%"+value +"%'";
		}
		if(pageNo<=0){pageNo=1;}
		Page<BsAdminUser> page = bsAdminUserDao.pagedQuery(hql, pageNo, pageSize);
		if(page!=null){
			for(BsAdminUser bau:page.getList()){
				bau.getBsRole().getRoleName();
				bau.getBsRole().setBsAdminUsers(null);
				bau.getBsRole().setBsFunctions(null);
			}
		}
		return page;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.AdminUserService#saveAdminUser(com.hcctech.bookshelf.pojo.BsAdminUser)
	 */
	public boolean saveAdminUser(BsAdminUser bau){
		if(bau==null){return false;}
		boolean flag = true;
		final String hql = "from BsAdminUser where userName=?";
		BsAdminUser temp = bsAdminUserDao.findUniqueByHql(hql, bau.getUserName());
		if(temp==null){
			bau.setPassword(Md5.getMD5Str(bau.getPassword()));
			bau.setStatus(1);
			bsAdminUserDao.save(bau);
		}else{
			flag = false;
		}
		return flag;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.AdminUserService#deleteAdminUserByIdString(java.lang.String)
	 */
	public void deleteAdminUserByIdString(String ids){
		if(ids!=null && !"".equals(ids)){
			int id =Integer.valueOf(ids);
			String sql = "delete from bs_admin_user where au_id ="+id;
			bsAdminUserDao.executeSQL(sql);
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.AdminUserService#findUserById(java.lang.Integer)
	 */
	public BsAdminUser findUserById(Integer id){
		BsAdminUser badu = bsAdminUserDao.findUniqueByHql("from BsAdminUser where auId=?", id);
		badu.getBsRole().getRoleId();
		badu.getBsRole().setBsAdminUsers(null);
		badu.getBsRole().setBsFunctions(null);
		return badu;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.AdminUserService#updateUser(com.hcctech.bookshelf.pojo.BsAdminUser)
	 */
	public void updateUser(BsAdminUser badu,Integer tag){
		if(tag!=null&&tag==1){
			badu.setPassword(Md5.getMD5Str(badu.getPassword()));
		}
		bsAdminUserDao.update(badu);
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.AdminUserService#updateStatusById(com.hcctech.bookshelf.pojo.BsAdminUser)
	 */
	public void updateStatusById(BsAdminUser badu){
		String sql = "update bs_admin_user set status=? where au_id=?";
		if(badu!=null){
			if(null != badu.getStatus()){
				if(badu.getStatus()==1){
					badu.setStatus(0);
				}else{
					badu.setStatus(1);
				}
			}
		}
		bsAdminUserDao.executeSQL(sql, badu.getStatus(),badu.getAuId());
	}
	
	public void setBsAdminUserDao(BsAdminUserDao bsAdminUserDao) {
		this.bsAdminUserDao = bsAdminUserDao;
	}

}
