package com.hcctech.bookshelf.actions;

import java.util.List;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsOrder;
import com.hcctech.bookshelf.services.OrderService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author zoujunpeng
 * 订单管理
 * 2012-08-28
 *
 */
public class OrderAction extends ActionSupport{
	
	private static final long serialVersionUID = 1339567059944682503L;
	
	private OrderService orderService;
	
	private int pageSize = 10;
	
	private int page = 1;
	
	private List<BsOrder> rows;
	
	private int total;
	
	private String searchValue;
	
	private String searchName;
	
	private String orderId;
	
	private BsOrder bsOrder;
	
	private String orderState;

	public String findAllOrder(){
		Page<BsOrder> pageObject = orderService.findAllOrder(page, pageSize, searchName, searchValue,orderState);
		if(null!=pageObject){
			rows = pageObject.getList();
			total = (int)pageObject.getTotalCount();
		}else{
			rows = null;
			total = 0;
		}
		return SUCCESS;
	}
	
	public String findOrderById(){
		bsOrder = orderService.findOrderById(orderId);
		return SUCCESS;
	}
	
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<BsOrder> getRows() {
		return rows;
	}

	public void setRows(List<BsOrder> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BsOrder getBsOrder() {
		return bsOrder;
	}

	public void setBsOrder(BsOrder bsOrder) {
		this.bsOrder = bsOrder;
	}

	public void setOrderState(String orderState) {
		this.orderState = orderState;
	}
	
}
