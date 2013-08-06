package com.hcctech.bookshelf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.MDC;

import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.util.IPUtil;

public class Log4jFilter implements Filter{
	
	public void destroy() {
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
			HttpServletRequest req =(HttpServletRequest)request;
			HttpSession session =  req.getSession();
			Integer userId = 0;
			String userName = "";
			String ip = "";
			Integer roleId = 0;
			String roleName = "";
			
			BsAdminUser bsAdminUser = (BsAdminUser)session.getAttribute("adminuser");
			BsWebUser bsWebUser = (BsWebUser)session.getAttribute("user");
			if(bsWebUser!=null || bsAdminUser!=null ){
				if(bsAdminUser==null){
					userId = bsWebUser.getWuId();
					userName = bsWebUser.getWuEmail();
					roleId = 0;
					roleName = "前台会员";
				}else{
					userId = bsAdminUser.getAuId();
					userName = bsAdminUser.getUserName()+"【"+bsAdminUser.getRealName()+"】";
					roleId = bsAdminUser.getBsRole().getRoleId();
					roleName = bsAdminUser.getBsRole().getRoleName();
				}
				ip = IPUtil.getIpAddr(req);
				MDC.put("userId", userId);
				MDC.put("userName", userName);
				MDC.put("ip", ip);
				MDC.put("roleId", roleId);
				MDC.put("roleName",java.net.URLDecoder.decode(roleName, "UTF-8"));
			}else{
				MDC.put("userId", "");
				MDC.put("userName", "");
				MDC.put("ip", ip);
				MDC.put("roleId", "");
				MDC.put("roleName","");
			}
			chain.doFilter(request, response);
	}
	public void init(FilterConfig Config) throws ServletException {
		
	}
}
