package com.hcctech.bookshelf.flex;

import org.apache.commons.lang3.StringUtils;

import com.hcctech.bookshelf.pojo.BsUserInfo;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.RegisterService;
import com.hcctech.bookshelf.services.WebUserService;
import com.hcctech.bookshelf.util.Md5;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;

/**
 * @author randyjie
 *
 */
public class UserRegisterFlex {

	private RegisterService registerService;
	private WebUserService webUserService;
	
//	private  UserLoginService userLoginService;
	
	public int register(String email,String password,String nickname,String realName){
		FlexSession session = FlexContext.getFlexSession();
		BsWebUser bsWebUser = new BsWebUser();
		bsWebUser.setWuEmail(email);
		bsWebUser.setWuPassword(password);
		BsUserInfo userInfo = new BsUserInfo();
		userInfo.setNickName(nickname);
		userInfo.setRealName(realName);
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
	
	
	public boolean updateNickNameOrPwd(Integer userId,String nickName,String pwd) {
		try {
			if(StringUtils.isBlank(nickName) && StringUtils.isBlank(pwd)) {
				System.out.println("昵称和密码都为空！");
				return false;
			}
			BsWebUser bsWebUser = new BsWebUser();
			bsWebUser.setWuId(userId);
			bsWebUser = webUserService.loadWebUserById(bsWebUser);
			if(StringUtils.isNotBlank(pwd)) {
				bsWebUser.setWuPassword(Md5.getMD5Str(pwd));
			}
			if(StringUtils.isNotBlank(nickName)) {
				bsWebUser.getBsUserInfo().setNickName(nickName);
			}
			
			webUserService.updateWebUser(bsWebUser);
			return true;
		}catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public String lostPassword(String userEmail){
		try {
			BsWebUser user= new BsWebUser();
			user.setWuEmail(userEmail);
			return registerService.lostPassword4Client(user);
		}catch(Exception e) {
			e.printStackTrace();
			
		}
		return "-1";
	}

	public void setWebUserService(WebUserService webUserService) {
		this.webUserService = webUserService;
	} 

	
}
 