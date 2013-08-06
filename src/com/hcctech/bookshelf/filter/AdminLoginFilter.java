package com.hcctech.bookshelf.filter;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hcctech.bookshelf.pojo.BsAdminUser;

/**
 * @author randyjie
 * 后台登录过滤器
 */
public class AdminLoginFilter implements Filter {

	public void destroy() {
		
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request=(HttpServletRequest)arg0;
		HttpServletResponse response=(HttpServletResponse)arg1;
		String rurl =  request.getRequestURI();
//		System.out.println(rurl);
		String queryString = request.getQueryString();
		rurl = rurl.substring(request.getContextPath().length()+7);
//		System.out.println(rurl);
		String suffix = "";
		
		//-----------------------
		List<String> exclude = new ArrayList<String>();
		exclude.add("ebook_manager/tips_ebook.html");
		exclude.add("ebook_manager/ebook_response.jsp");
		exclude.add("product/addsuc.jsp");
		exclude.add("user_manager/editUser.jsp");
		exclude.add("main.jsp");
		
		//----------------------------//
		
		if(!rurl.equals("") ){
			try {
				suffix = rurl.substring(rurl.lastIndexOf("."));
			} catch (Exception e) {
				//无所谓
				suffix = "";
			}
		}
		try {
			if(rurl.equals("") || suffix.equalsIgnoreCase(".jsp")||suffix.equalsIgnoreCase(".html")){
				if(rurl.equals("login.jsp")||rurl.equals("error.jsp")||rurl.equals("index.jsp")){
					arg2.doFilter(request, response);
				}else{
					if(exclude.contains(rurl)){
						arg2.doFilter(request, response);//排除的URL
					}else{
						HttpSession session = request.getSession();
						BsAdminUser user = (BsAdminUser)session.getAttribute("adminuser");
						@SuppressWarnings("unchecked")
						List<String> roleFunction = (List<String>)session.getAttribute("role_function");
//						if(queryString!=null){
//							queryString = URLDecoder.decode(queryString, "UTF-8");
//							rurl = rurl+"?"+queryString;
//						}
						if(user!=null && user.getUserName()!=null){
							if(roleFunction!=null){
								if(roleFunction.contains(rurl)){
									arg2.doFilter(request, response);
								}else{
									response.sendRedirect(request.getContextPath()+"/admin/error.jsp");
								}
							}else{
								response.sendRedirect(request.getContextPath()+"/admin/login.jsp");
							}
						}else{
							response.sendRedirect(request.getContextPath()+"/admin/login.jsp");
						}
					}
					
				}
				
			}else{
				arg2.doFilter(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.sendRedirect(request.getContextPath()+"/admin/login.jsp");
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}
