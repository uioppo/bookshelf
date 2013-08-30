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
	
	//返回1表示成功，返回404表示无此用户，-1表示系统错误,返回500表示旧密码不对
	public String updateNickNameOrPwd(Integer userId,String nickName,String pwd,String oldPwd) {
		try {
			if(StringUtils.isBlank(nickName) && StringUtils.isBlank(pwd)) {
				System.out.println("昵称和密码都为空！");
				return "404";
			}
			BsWebUser bsWebUser = new BsWebUser();
			bsWebUser.setWuId(userId);
			bsWebUser = webUserService.loadWebUserById(bsWebUser);
			if(StringUtils.isNotBlank(nickName)) {
				bsWebUser.getBsUserInfo().setNickName(nickName);
				webUserService.updateWebUser(bsWebUser);
			}
			if(StringUtils.isNotBlank(pwd)) {
				if(StringUtils.isBlank(oldPwd)) {
					System.out.println("++++密码为空++++");
					return "404";
				}
				if(!bsWebUser.getWuPassword().equals(Md5.getMD5Str(oldPwd))) {
					System.out.println("+++++密码不正确++++++");
					return "500";
				}
				bsWebUser.setWuPassword(pwd);
				registerService.updatePassword(bsWebUser);
			}
			return "1";
		}catch(Exception e) {
			e.printStackTrace();
			return "-1";
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
 