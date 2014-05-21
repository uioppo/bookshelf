package com.hcctech.bookshelf.services.impl;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.dao.BsWebUserDao;
import com.hcctech.bookshelf.pojo.BsUserInfo;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.RegisterService;
import com.hcctech.bookshelf.util.PEPHttpClient;
import com.hcctech.bookshelf.util.DomainUtil;
import com.hcctech.bookshelf.util.Md5;
import com.hcctech.bookshelf.util.ThirdVelocityEmailUtil;

/**
 * @author apple
 *前台用户注册
 */
public class RegisterServiceImpl implements RegisterService{
	
	private BsWebUserDao bsWebUserDao;
	
	private ThirdVelocityEmailUtil thirdVelocityEmailUtil;
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RegisterService#updatePassword(com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public void updatePassword(BsWebUser bsWebUser){
		if(bsWebUser!=null){
			final String hql="from BsWebUser where wuEmail =?";
			BsWebUser bsWebUser1=bsWebUserDao.findUniqueByHql(hql, bsWebUser.getWuEmail());
			if(bsWebUser1!=null){
			    
				bsWebUser1.setWuPassword(Md5.getMD5Str(bsWebUser.getWuPassword()));
				bsWebUserDao.update(bsWebUser1);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RegisterService#validateToken(com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public int validateToken(BsWebUser bsWebUser){
		int flag=0;
		if(bsWebUser==null||"".equals(bsWebUser.getWuEmail())||"".equals(bsWebUser.getWuToken())){
			return flag;
		}
		String hql="from BsWebUser where wuEmail = ? and wuToken = ?";
		BsWebUser  bsWebUser1= bsWebUserDao.findUniqueByHql(hql,bsWebUser.getWuEmail(),bsWebUser.getWuToken());
		if(bsWebUser1==null){
			return flag;
		}
		flag=1;
		return flag;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RegisterService#lostPassword(com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public void lostPassword(BsWebUser bsWebUser){
		HttpServletRequest request = ServletActionContext.getRequest();
		if(bsWebUser!=null&&!("".equals(bsWebUser.getWuEmail()))&&!("".equals(bsWebUser.getWuToken()))){
		}
		final String hql = "from BsWebUser b where b.wuEmail = '"+bsWebUser.getWuEmail()+"'"; 
		BsWebUser bwu =  bsWebUserDao.findUniqueByHql(hql);
		if(bwu!=null){
			String token=Md5.getMD5Str(String.valueOf(Math.random()));
			bwu.setWuToken(token);
			bsWebUserDao.update(bwu);
			//发邮件
			final Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", bsWebUser.getWuEmail());
			model.put("activateURL",DomainUtil.getDomainName()+request.getContextPath() + "/register/registerAction_findPassword.action?bsWebUser.wuEmail="+bsWebUser.getWuEmail()+"&bsWebUser.wuToken="+token);
			final String _userName = bsWebUser.getWuEmail();
			Runnable run = new Runnable() {
				public void run() {
					thirdVelocityEmailUtil.sendEmail(model, "找回密码", "lostPassWordEmailVM.vm",new String[] {_userName}, null);
				}
			};
			Thread thread = new Thread(run);
			thread.start();
		}
	}
	
	public String lostPassword4Client(BsWebUser bsWebUser){
		final String hql = "from BsWebUser b where b.wuEmail = '"+bsWebUser.getWuEmail()+"'"; 
		BsWebUser bwu =  bsWebUserDao.findUniqueByHql(hql);
		if(bwu==null) {
			return "404";
		}
			
		if(bwu!=null){
			String token=Md5.getMD5Str(String.valueOf(Math.random()));
			bwu.setWuToken(token);
			bsWebUserDao.update(bwu);
			//发邮件
			final Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", bsWebUser.getWuEmail());
			model.put("activateURL",DomainUtil.getDomainName() + "/register/registerAction_findPassword.action?bsWebUser.wuEmail="+bsWebUser.getWuEmail()+"&bsWebUser.wuToken="+token);
			final String _userName = bsWebUser.getWuEmail();
			Runnable run = new Runnable() {
				public void run() {
					thirdVelocityEmailUtil.sendEmail(model, "找回密码", "lostPassWordEmailVM.vm",new String[] {_userName}, null);
				}
			};
			Thread thread = new Thread(run);
			thread.start();
		}
		return "1";
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RegisterService#validateUserName(com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public int validateUserName(BsWebUser bsWebUser) {
		int flag=1;
		if(bsWebUser==null||"".equals(bsWebUser.getWuEmail())||"".equals(bsWebUser.getWuToken())){
			return flag;
		}
		final String hql = "from BsWebUser b where b.wuEmail = '"+bsWebUser.getWuEmail()+"'"; 
		BsWebUser bwu =  bsWebUserDao.findUniqueByHql(hql);
		if(bwu==null){
			flag=0;
		}
		return flag;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RegisterService#registerActivate(com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public int registerActivate(BsWebUser bsWebUser) {
		int flag=0;
		if(bsWebUser==null||"".equals(bsWebUser.getWuEmail())||"".equals(bsWebUser.getWuToken())){
			return flag;
		}
		String hql="from BsWebUser where wuEmail = ? and wuToken = ?";
		BsWebUser  bsWebUser1= bsWebUserDao.findUniqueByHql(hql,bsWebUser.getWuEmail(),bsWebUser.getWuToken());
		if(bsWebUser1==null){
			return flag;
		}
		bsWebUser1.setWuActivestatus(1);
		bsWebUser1.setWuToken("");
		bsWebUserDao.update(bsWebUser1);
		flag=1;
		return flag;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RegisterService#registerWebUser(com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public int registerWebUser(BsWebUser bsWebUser) {
		HttpServletRequest request = ServletActionContext.getRequest();
		int flag=0;
		if(bsWebUser==null||bsWebUser.getWuEmail()==null||"".equals(bsWebUser.getWuEmail())){
			return flag;
		}
		String hql = "from BsWebUser where wuEmail = ?"; 
		BsWebUser bsWebUser1 =  bsWebUserDao.findUniqueByHql(hql,bsWebUser.getWuEmail());
		if(bsWebUser1!=null){
			return flag;
		}else{
		    //如果本地用户不重复，去注册人教平台，如果成功就记录数据库，如果失败则返回错误
//		    String ret = PEPHttpClient.getInstance().registerToPep(bsWebUser);
//		    if(!ret.equals("0")) {
//		        return flag;
//		    }
			flag=1;
			String token=Md5.getMD5Str(String.valueOf(Math.random()));
			bsWebUser.setWuUserName(bsWebUser.getWuEmail());
			bsWebUser.setWuPassword(Md5.getMD5Str(bsWebUser.getWuPassword()));
			bsWebUser.setWuToken(token);
			bsWebUser.setWuActivestatus(0);
			bsWebUser.setWuRegTime(new Timestamp(System.currentTimeMillis()));
			BsUserInfo bsUserInfo = new BsUserInfo();
			bsWebUserDao.save(bsUserInfo);
			bsWebUser.setBsUserInfo(bsUserInfo);
			bsWebUserDao.save(bsWebUser);
			//发邮件
			final Map<String, Object> model = new HashMap<String, Object>();
			model.put("userName", bsWebUser.getWuEmail());
			model.put("activateURL",DomainUtil.getDomainName()+request.getContextPath()+"/register/registerAction_registerActivate.action?bsWebUser.wuEmail="+bsWebUser.getWuEmail()+"&bsWebUser.wuToken="+token);
			final String _userName = bsWebUser.getWuEmail();
			Runnable run = new Runnable() {
				public void run() {
					thirdVelocityEmailUtil.sendEmail(model, "注册验证", "emailVM.vm",new String[] {_userName}, null);
				}
			};
			Thread thread = new Thread(run);
			thread.start();
		}
		
		return flag;
	}
	
	public int registerWebUser4Client(BsWebUser bsWebUser) {
		int flag=0;
		if(bsWebUser==null||bsWebUser.getWuEmail()==null||"".equals(bsWebUser.getWuEmail())){
			return flag;
		}
		String hql = "from BsWebUser where wuEmail = ?"; 
		BsWebUser bsWebUser1 =  bsWebUserDao.findUniqueByHql(hql,bsWebUser.getWuEmail());
		if(bsWebUser1!=null){
			return 502;
		}else{
		  //如果本地用户不重复，去注册人教平台，如果成功就记录数据库，如果失败则返回错误
            String ret = PEPHttpClient.getInstance().registerToPep(bsWebUser);
            if(!ret.equals("0")) {
                return flag;
            }
			flag=1;
//			String token=Md5.getMD5Str(String.valueOf(Math.random()));
			bsWebUser.setWuUserName(bsWebUser.getWuEmail());
			bsWebUser.setWuPassword(Md5.getMD5Str(bsWebUser.getWuPassword()));
			bsWebUser.setWuToken("");
			bsWebUser.setWuActivestatus(1);
			bsWebUser.setWuRegTime(new Timestamp(System.currentTimeMillis()));
			BsUserInfo bsUserInfo = bsWebUser.getBsUserInfo();
			if(bsUserInfo == null) {
				bsUserInfo = new BsUserInfo();
			}
			bsWebUserDao.save(bsUserInfo);
			bsWebUser.setBsUserInfo(bsUserInfo);
			bsWebUserDao.save(bsWebUser);
		}
		
		return flag;
	}

	public void setBsWebUserDao(BsWebUserDao bsWebUserDao) {
		this.bsWebUserDao = bsWebUserDao;
	}

	public void setThirdVelocityEmailUtil(
			ThirdVelocityEmailUtil thirdVelocityEmailUtil) {
		this.thirdVelocityEmailUtil = thirdVelocityEmailUtil;
	}

}
