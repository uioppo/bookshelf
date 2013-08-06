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
	
	public boolean login(String username,String password){
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
			return false ;
		}
		session.setAttribute("user", bsWebUser);
		return true;
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
 