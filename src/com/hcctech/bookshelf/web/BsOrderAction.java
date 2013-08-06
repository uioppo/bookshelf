package com.hcctech.bookshelf.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsOrder;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.OrderService;
import com.hcctech.bookshelf.util.URLUtil;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 
 * @author konglingxia
 * @version 1.0
 *
 */
public class BsOrderAction extends ActionSupport {

	private static final long serialVersionUID = -814852656506895821L;
	private OrderService orderService ;
	private List<BsOrder> orderList ;
	private long total ;
	private Integer pageNumber = 10;
	private Integer pageSize = 1;
	private String orderId ;
	private String url;
	
	private String ordId ;
	private BsOrder bsOrder ;
	
	private boolean flag ;
	
	/**
	 * 我的订单
	 */
	public String orderList() {
		HttpServletRequest request = ServletActionContext.getRequest();
		BsWebUser bsWebUser = (BsWebUser)request.getSession().getAttribute("user");
		if(bsWebUser == null) {
			String sb = request.getRequestURL().toString() ;	//浏览器地址
			String param = request.getQueryString();			//参数(?以后)
			url = URLUtil.getURL(sb,param);	
			return SUCCESS ;
		}
		Page<BsOrder> page = orderService.loadOrderList(bsWebUser.getWuId(), orderId, pageSize, pageNumber);
		orderList = page.getList() ;
		total = page.getTotalCount();
		return SUCCESS ;
	}
	
	/**
	 * 查询订单
	 */
	public String loadOrderById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		BsWebUser bsWebUser = (BsWebUser)request.getSession().getAttribute("user");
		
		if(bsWebUser == null) {
			url = request.getRequestURL().toString() ;
			return SUCCESS ;
		}
		bsOrder = orderService.loadOrderById(orderId,bsWebUser.getWuId());
		return SUCCESS;
	}
	
	/**
	 * 订单详细
	 */
	public String loadDetailOrderById() {
		HttpServletRequest request = ServletActionContext.getRequest();
		BsWebUser bsWebUser = (BsWebUser)request.getSession().getAttribute("user");
		if(bsWebUser == null) {
			String sb = request.getRequestURL().toString() ;	//浏览器地址
			String param = request.getQueryString();			//参数(?以后)
			url = URLUtil.getURL(sb,param);		
			return INPUT ;
		}
		bsOrder = orderService.loadDetailOrderById(ordId,bsWebUser.getWuId());
		if(bsOrder == null) {
			return "noOrderId";
		}
		return "loadDetailOrderById";
	}
	
	public String cancelOrder(){
		HttpServletRequest request = ServletActionContext.getRequest();
		BsWebUser bsWebUser = (BsWebUser)request.getSession().getAttribute("user");
		if(bsWebUser == null) {
			String sb = request.getRequestURL().toString() ;	//浏览器地址
			String param = request.getQueryString();			//参数(?以后)
			url = URLUtil.getURL(sb,param);		
			return INPUT ;
		}
		flag = orderService.cancelOrder(orderId, bsWebUser.getWuId());
		return SUCCESS;
	}
	
	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public List<BsOrder> getOrderList() {
		return orderList;
	}

	public long getTotal() {
		return total;
	}

	public BsOrder getBsOrder() {
		return bsOrder;
	}

	public String getUrl() {
		return url;
	}

	public void setOrdId(String ordId) {
		this.ordId = ordId;
	}

	public boolean getFlag() {
		return flag;
	}

	

	
	
}
