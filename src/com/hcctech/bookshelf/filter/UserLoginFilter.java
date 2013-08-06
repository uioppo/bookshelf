package com.hcctech.bookshelf.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.util.URLUtil;

public class UserLoginFilter implements Filter {

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		HttpSession session = request.getSession();
		BsWebUser user = (BsWebUser)session.getAttribute("user");
		String rurl =  request.getRequestURI();
		rurl = rurl.substring(request.getContextPath().length());
		String orderUrl="/user/createOrder.action";
		if(user != null && user.getWuEmail() != null) {
			chain.doFilter(request, response);
		}else if(orderUrl.equals(rurl)){
			chain.doFilter(request, response);
		}else {
			String sb = request.getRequestURL().toString() ;	//浏览器地址
			String param = request.getQueryString();			//参数(?以后)
			String url = URLUtil.getURL(sb,param);	
			response.sendRedirect(request.getContextPath()+ url);
		}
		
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
