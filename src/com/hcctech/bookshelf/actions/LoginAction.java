package com.hcctech.bookshelf.actions;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.axiom.util.base64.Base64Utils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.MDC;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.UserLoginService;
import com.hcctech.bookshelf.util.IPUtil;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author konglingxia
 * @version 1.0
 */
public class LoginAction extends ActionSupport implements ServletRequestAware,ServletResponseAware{

	private static final long serialVersionUID = 4438310318456424164L;
	
	private String username ;		//用户名
	private String password ;		//密码
	private UserLoginService userLoginService ;
	private String loginMsg ;		//返回消息
	private String validCode;		//验证码
	private String url ;			
	private String forward ;		//登录成功后,跳转的页面
	private final static Log logger = LogFactory.getLog(LoginAction.class);
	
	private String email ;
	
	private HttpServletResponse response;
	private HttpServletRequest request;
	
	private String name ;
	/**
	 * web 登录
	 * @return
	 */
	public String userLogin() {
		String uName  = Base64Utils.encode(username.getBytes()).substring(0, 8).replace("=", "_");
		if(null!=validCode && !"".equals(validCode)){
			String kaptchaExpected = (String)request.getSession().getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
			if(!validCode.equalsIgnoreCase(kaptchaExpected)){
				loginMsg = "验证码输入错误!";
				return INPUT;
			}
		}
		Cookie[] cookies = request.getCookies() ;
		BsWebUser bsWebUser = userLoginService.login(username,password);
		if(bsWebUser == null) {
			boolean flag = true ;
			if(cookies != null) {
				for(Cookie cookie:cookies){
					if(null!=cookie && cookie.getName()!=null){
						String xx = cookie.getName();
						if(xx.equals(uName)){
							String v = cookie.getValue() ;
							String count = v.substring(v.length()-1);
							int num = 0 ;
							try {
								num = Integer.parseInt(count);
								if(num == 9) {//十次登录错误，锁定用户1小时
									boolean b = userLoginService.lockUser(username);
									if(b) {
										loginMsg = "该用户已锁定！" ;
										return INPUT ;
									} else {
										loginMsg = "请稍候再试！" ;
										return INPUT ;
									}
								}
								++num;
							}catch(Exception e) {
							}
							cookie.setValue(num+"");
							cookie.setPath("/");
							cookie.setMaxAge(600);
							response.addCookie(cookie);
							flag = false ;
						}
					}
				}
			}
			
			
			if(flag) {
				if(uName != null && !"".equals(uName)) {//第一次登录错误时，添加该用户的cookie
					Cookie cookie = new Cookie(uName,"1");
					cookie.setMaxAge(600);
					cookie.setPath("/");
					response.addCookie(cookie);
				}
			}
			
			loginMsg = "用户名或密码不正确！";
			return INPUT ;
		}else {
			if(bsWebUser.getWuActivestatus() == 0) {
				loginMsg = "该用户未激活！";
				return INPUT ;
			}else if(bsWebUser.getWuActivestatus() == 2){
				loginMsg = "该用户已禁用！";
				return INPUT ;
			}else if(bsWebUser.getWuActivestatus() == 3){
				loginMsg = "该用户已锁定，请稍候再试！";
				return INPUT ;
			}else {
				request.getSession().setAttribute("user", bsWebUser);
				//登录成功，清除该用户的cookie
				if(cookies != null) {
					for(Cookie cookie:cookies){
						if(null!=cookie && cookie.getName()!=null){
							String xx = cookie.getName();
							if(xx.equals(uName)){
								cookie.setValue("0");
								cookie.setPath("/");
								cookie.setMaxAge(0);
								response.addCookie(cookie);
							}
						}
					}
				}
				
				/*记录登录日志*/
				MDC.put("userId", bsWebUser.getWuId());
				if(null!=bsWebUser.getBsUserInfo()){
					String s = bsWebUser.getBsUserInfo().getRealName();
					if(s!=null && !"".equals(s)){
						MDC.put("userName", bsWebUser.getWuEmail()+"【"+s+"】");
					}else{
						MDC.put("userName", bsWebUser.getWuEmail());
					}
				}else{
					MDC.put("userName", bsWebUser.getWuEmail());
				}
		        MDC.put("ip", IPUtil.getIpAddr(request));
		        MDC.put("roleId", 0);
		        try {
					MDC.put("roleName",java.net.URLDecoder.decode("前台会员", "UTF-8"));
				} catch (UnsupportedEncodingException e) {
				}
				logger.info("登陆bookshelf系统");
			}
			if(url != null && !"".equals(url) && !"null".equals(url)) {
				forward = url;
			}else{
				forward = "index.html";
			}
		}
		return SUCCESS;
	}
	
	public String checkLogin() {
		BsWebUser bsWebUser = (BsWebUser)request.getSession().getAttribute("user");
		if(bsWebUser != null) {
			email = bsWebUser.getWuEmail();
		}
		return SUCCESS;
	}
	
	public String loginOut() {
		try{
			request.getSession().removeAttribute("user");
		}catch(IllegalStateException e){
		}
		return SUCCESS;
	}
	
	public String userEncode(){
		name =  Base64Utils.encode(name.getBytes()).substring(0,8).replace("=", "_");
		return SUCCESS;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	public String getLoginMsg() {
		return loginMsg;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getForward() {
		return forward;
	}

	public String getEmail() {
		return email;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		 this.response =arg0;
		
	}

	public void setServletRequest(HttpServletRequest arg0) {
		this.request = arg0; 
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
	

}
