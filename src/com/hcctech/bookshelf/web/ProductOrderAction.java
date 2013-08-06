package com.hcctech.bookshelf.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.pojo.BsOrder;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.ProductOrderService;
import com.hcctech.bookshelf.util.BuyException;
import com.hcctech.bookshelf.util.DomainUtil;
import com.hcctech.bookshelf.util.URLUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 生成订单  购买商品
 * @author apple
 *
 */
public class ProductOrderAction extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2794824529962087836L;
	
	private ProductOrderService productOrderService;
	private String url;
	private String orderId;
	private int myBookId;
	private int keyCount;
	
	private BsOrder bsOrder;
	/**
	 * 购买授权码     支付成功
	 * @return
	 */
	public String paySuccessForCount(){
		HttpServletRequest request = ServletActionContext.getRequest();
		BsWebUser user=(BsWebUser)ActionContext.getContext().getSession().get("user"); 
		if(user==null||user.getWuId()==null){
			url=URLUtil.getURL(request.getRequestURL().toString(), request.getQueryString());
			return "login";
		}
		productOrderService.paySuccessForCount(orderId, user);
		return SUCCESS;
	}
	/**
	 * 购买授权码   生成订单
	 * @return
	 */
	public String createOrderForCount(){
		HttpServletRequest request = ServletActionContext.getRequest();
		BsWebUser user=(BsWebUser)ActionContext.getContext().getSession().get("user"); 
		if(user==null||user.getWuId()==null){
			StringBuffer sb = new StringBuffer();
			sb.append(request.getRequestURL());
			if(request.getQueryString() != null) {
				sb.append("?");
				sb.append(request.getQueryString());
			}
			try {
				url = URLEncoder.encode(sb.toString(), "utf-8");
				url = "/login.jsp?url=" + url;
			} catch (UnsupportedEncodingException e) {
				url = "/login.jsp";
			}
			return "login";
		}
		productOrderService.createOrderForCount(myBookId,keyCount,user);
		return SUCCESS;
	}
	/**
	 * 购买电子书    支付成功
	 * @return
	 */
	public String paySuccess(){
		if(orderId!=null&&!("".equals(orderId))){
			productOrderService.paySuccess(orderId);
			return SUCCESS;
		}
		return ERROR;
	}
	/**
	 * 生成订单
	 * @return
	 */
	public String createOrder(){
		HttpServletRequest request = ServletActionContext.getRequest();
		BsWebUser user=(BsWebUser)ActionContext.getContext().getSession().get("user"); 
		if(user==null||user.getWuId()==null){
			String url1=DomainUtil.getDomainName()+request.getContextPath() + "/shopCart/shopCart.jsp";
			try {
				url = URLEncoder.encode(url1, "utf-8");
				url = "/login.jsp?url=" + url;
			} catch (UnsupportedEncodingException e) {
				url = "/login.jsp";
			}
			return "login";
		}
		Cookie[] cookie=request.getCookies();
		String ids=null;
		if(cookie!=null&&cookie.length>0){
			for(int i=0;i<cookie.length;i++){
				Cookie c=cookie[i];
				if(c!=null&&c.getName()!=null){
					if("the_bs_product".equals(c.getName())){
						ids=c.getValue();
					}
				}
			}
		}
		if(ids ==null||"".equals(ids.trim())){
			return INPUT;
		}
		try {
			ids=URLDecoder.decode(ids, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return INPUT;
		}
		try {
			 bsOrder=productOrderService.createOrder(ids,user);
			 if(bsOrder==null){
				 return INPUT;
			 }
			//清除购物车
			HttpServletResponse response = ServletActionContext.getResponse();
			//Cookie[] cookies=request.getCookies();  
			if(cookie!=null){   
			       for(int i=0;i<cookie.length;i++){
			    	   Cookie c=cookie[i];
						if(c!=null&&c.getName()!=null){
							if("the_bs_product".equals(c.getName())){
								cookie[i].setMaxAge(-1);
								cookie[i].setPath("/");
								Cookie cookie1=new Cookie(cookie[i].getName(),null);
								cookie1.setValue("0");
								cookie1.setPath("/");
								cookie1.setMaxAge(0);
								response.addCookie(cookie1);
							}
						}
			        }   
			   }
		} catch (BuyException e) {
			return INPUT;
		}
		return SUCCESS;
	}
	public void setProductOrderService(ProductOrderService productOrderService) {
		this.productOrderService = productOrderService;
	}
	public String getUrl() {
		return url;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public void setMyBookId(int myBookId) {
		this.myBookId = myBookId;
	}
	public void setKeyCount(int keyCount) {
		this.keyCount = keyCount;
	}
	public BsOrder getBsOrder() {
		return bsOrder;
	}
	
}
