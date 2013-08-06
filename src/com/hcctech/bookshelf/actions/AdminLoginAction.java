package com.hcctech.bookshelf.actions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsFunction;
import com.hcctech.bookshelf.services.AdminUserService;
import com.hcctech.bookshelf.util.IPUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author randyjie
 *   后台登录的action
 */
public class AdminLoginAction extends ActionSupport {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6514665023940367286L;

	private BsAdminUser adminUser;
	private AdminUserService adminUserService;
	private String msg;//返回消息
	private String validCode;
	
	private static final Log logger = LogFactory.getLog(AdminLoginAction.class);
	
	/**
	 * 后台登出
	 * @return
	 */
	public String logout(){
		ActionContext.getContext().getSession().remove("adminuser");
		return SUCCESS;
	}
	/**
	 * 后台登录
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String login(){
		if(null!=validCode && !"".equals(validCode)){
			String kaptchaExpected = String.valueOf(ActionContext.getContext().getSession().get(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY));
			if(!validCode.equalsIgnoreCase(kaptchaExpected)){
				msg="验证码错误！";
				return INPUT;
			}
		}else{
			msg="请输入验证码！";
			return INPUT;
		}
		BsAdminUser user = adminUserService.login(adminUser);
		
		if(user==null){
			msg="用户名或密码错误！";
			return INPUT;
		}
		
		ActionContext.getContext().getSession().put("adminuser", user);
		
		Set<BsFunction> bsFunctions=  user.getBsRole().getBsFunctions();
		List<String> list = new ArrayList<String>();
		for (BsFunction bsFunction : bsFunctions) {
			list.add(bsFunction.getFunctionUrl());//菜单权限  单独存储
		}
		ActionContext.getContext().getSession().put("role_function", list);
		/*记录登录日志*/
		MDC.put("userId", user.getAuId());
		if(null!=user.getRealName() && !"".equals(user.getRealName())){
			MDC.put("userName", user.getUserName()+"【"+user.getRealName()+"】");
		}else{
			MDC.put("userName", user.getUserName());
		}
        
        MDC.put("ip", IPUtil.getIpAddr(ServletActionContext.getRequest()));
        MDC.put("roleId", user.getBsRole().getRoleId());
        try {
			MDC.put("roleName",java.net.URLDecoder.decode(user.getBsRole().getRoleName(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		logger.info("登陆bookshelf系统");
		return SUCCESS;
	}

	
//	get set
	public BsAdminUser getAdminUser() {
		return adminUser;
	}
//
	public void setAdminUser(BsAdminUser adminUser) {
		this.adminUser = adminUser;
	}


	public String getMsg() {
		return msg;
	}


	public void setAdminUserService(AdminUserService adminUserService) {
		this.adminUserService = adminUserService;
	}


	public String getValidCode() {
		return validCode;
	}


	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}
	
}
