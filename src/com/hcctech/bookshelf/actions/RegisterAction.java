package com.hcctech.bookshelf.actions;

import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.RegisterService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author 张萍萍
 * @date 2012-08-13
 * @version
 * 用户注册ACTION
 */
public class RegisterAction extends ActionSupport{
	
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -3134766790952471948L;

	private RegisterService registerService;
	
	private BsWebUser bsWebUser;
	
	private int flag;
	
	private String validCode;
	
	private String email;
	/**
	 * 忘记密码  修改密码
	 * @return
	 */
	public String updatePassword(){
		if(validCode!=null && !"".equals(validCode)){
			String kaptchaExpected = String.valueOf(ActionContext.getContext().getSession().get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
			if(!validCode.equalsIgnoreCase(kaptchaExpected)){
				return "errorPage";
			}
		}else{
			return "errorPage";
		}
		registerService.updatePassword(bsWebUser);
		return "updatePassword";
	}
	
	/**
	 * 忘记密码  验证用户token
	 * @return
	 */
	public String findPassword(){
		flag=registerService.validateToken(bsWebUser);
		if(flag!=1){
			return "errorPage";
		}
		return "findPassword";
	}
	
	/**
	 * 忘记密码  通过邮箱给用户发邮件
	 * @return
	 */
	
	public String lostPassword(){
		if(null!=validCode && !"".equals(validCode)){
			String kaptchaExpected = String.valueOf(ActionContext.getContext().getSession().get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
			if(!validCode.equalsIgnoreCase(kaptchaExpected)){
				return "errorPage";
			}
		}else{
			return "errorPage";
		}
		registerService.lostPassword(bsWebUser);
		obtainEmail();
		return "lostPassword";
	}
	
	/**
	 * 验证码  ajax 验证是否正确
	 * @return
	 */
	public String validCodeIsRight(){
		if(null!=validCode && !"".equals(validCode)){
			String kaptchaExpected = String.valueOf(ActionContext.getContext().getSession().get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
			if(!validCode.equalsIgnoreCase(kaptchaExpected)){
				flag=0;
			}else{
				flag=1;
			}
		}
		return SUCCESS;
	}
	/**
	 * 用户名 ajax 验证是否存在
	 * @return
	 */
	public String validateUserName(){
		flag=registerService.validateUserName(bsWebUser);
		return SUCCESS;
	}
	/**
	 * 用户注册后 通过邮箱激活
	 * @return
	 */
	public String registerActivate(){
		flag=registerService.registerActivate(bsWebUser);
		if(flag==1){
			return "activateSuccess";
		}else{
			return "errorPage";
		}
	}
	/**
	 * 用户注册提交
	 * @return
	 */
	public String registerWebUser(){
		if(null!=validCode && !"".equals(validCode)){
			String kaptchaExpected = String.valueOf(ActionContext.getContext().getSession().get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
			if(!validCode.equalsIgnoreCase(kaptchaExpected)){
				return "errorPage";
			}
		}else{
			return "errorPage";
		}
		flag=registerService.registerWebUser(bsWebUser);
		if(flag==1){
			obtainEmail();
			return "registerSuccess";
		}else{
			return "errorPage";
		}
	}

	public BsWebUser getBsWebUser() {
		return bsWebUser;
	}

	public void setBsWebUser(BsWebUser bsWebUser) {
		this.bsWebUser = bsWebUser;
	}

	public void setRegisterService(RegisterService registerService) {
		this.registerService = registerService;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}
	

	public String getValidCode() {
		return validCode;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getEmail() {
		return email;
	}
	
	private void obtainEmail(){
		if(bsWebUser!=null){
			email = bsWebUser.getWuEmail().substring(bsWebUser.getWuEmail().indexOf("@")+1);
		}
	}
}
