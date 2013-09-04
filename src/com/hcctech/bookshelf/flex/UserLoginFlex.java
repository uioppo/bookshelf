package com.hcctech.bookshelf.flex;

import com.hcctech.bookshelf.listener.MyFlexSessionListener;
import com.hcctech.bookshelf.listener.MySessionContext;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.UserLoginService;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

/**
 * @author randyjie
 *
 */
public class UserLoginFlex {

	private  UserLoginService userLoginService;
	
	public String[] login(String username,String password){
//		FlexSessionManager manager = new FlexSessionManager(broker);
//		manager.
		FlexSession session = FlexContext.getFlexSession();
//		System.out.println(username+"---"+session.getAttribute(username)+"::"+password + "---" + session.getId());
//		session.setAttribute(username, username);
//		MySessionContext context  = MySessionContext.getInstance();
//		
//		FlexSession userInfo = context.getSession(username);
//		if(userInfo != null && userInfo.isValid()) {
//			System.out.println(username + "====" + userInfo.isValid());
//			if(userInfo.getAttribute(username)!=null&&!userInfo.getAttribute(username).equals("")){
//				context.getSession(username).invalidate();
//			}
//		} 
//		context.addSession(session, username);
//		session.addSessionCreatedListener(listener);
//		session.addSessionDestroyedListener(new MyFlexSessionListener());
		//session.invalidate();
		//session.cancelTimeout()
	//	session.getId();
		BsWebUser bsWebUser = userLoginService.login(username,password);
		
		if(bsWebUser == null) {
			//用户不存在
			return new String[] {"-1","用户不存在"} ;
		}else {
			if(bsWebUser.getWuActivestatus() == 0) {
//				loginMsg = "该用户未激活！";
				return new String[] {"-1","该用户未激活！"} ;
			}else if(bsWebUser.getWuActivestatus() == 2){
//				loginMsg = "该用户已禁用！";
				return new String[] {"-1","该用户已禁用！"} ;
			}else if(bsWebUser.getWuActivestatus() == 3){
//				loginMsg = "该用户已锁定，请稍候再试！";
				return new String[] {"-1","该用户已锁定，请稍候再试！"} ;
			}
		}
		
		session.setAttribute("user", bsWebUser);
		return new String[] {String.valueOf(bsWebUser.getWuId()),bsWebUser.getBsUserInfo().getNickName(),bsWebUser.getBsUserInfo().getRealName()};
	}

	public boolean repeatLogin(String username) {
		boolean flag = false ;
		FlexSession session = FlexContext.getFlexSession();
		MySessionContext context  = MySessionContext.getInstance();
		FlexSession userInfo = context.getSession(username);
		if(userInfo != null){
			if(session.getId().equals(userInfo.getId())){
				flag = true;
			}
		}
		System.out.println(username+"---"+session.getAttribute(username) + "---" + session.getId());
		return flag ;
	}
	
	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}
	
}
 