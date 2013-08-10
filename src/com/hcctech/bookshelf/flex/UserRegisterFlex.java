package com.hcctech.bookshelf.flex;

import com.hcctech.bookshelf.pojo.BsUserInfo;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.RegisterService;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

/**
 * @author randyjie
 *
 */
public class UserRegisterFlex {

	private RegisterService registerService;
	
//	private  UserLoginService userLoginService;
	
	public int register(String email,String password,String nickname){
		FlexSession session = FlexContext.getFlexSession();
		BsWebUser bsWebUser = new BsWebUser();
		bsWebUser.setWuEmail(email);
		bsWebUser.setWuPassword(password);
		BsUserInfo userInfo = new BsUserInfo();
		userInfo.setNickName(nickname);
		bsWebUser.setBsUserInfo(userInfo);
//		System.out.println("----------------"+email+"+++"+password);
		int flag = 0;
		try {
			flag = registerService.registerWebUser4Client(bsWebUser);
		}catch(Exception e) {
			e.printStackTrace();
		}
//		System.out.println("|||||||||||"+flag);
		return flag;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	
	
}
 