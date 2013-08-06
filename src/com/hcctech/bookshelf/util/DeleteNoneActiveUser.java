package com.hcctech.bookshelf.util;
import com.hcctech.bookshelf.services.BsProductsService;
import com.hcctech.bookshelf.services.OrderService;
import com.hcctech.bookshelf.services.WebUserService;

public class DeleteNoneActiveUser{
	
	private WebUserService webUserService;
	private OrderService orderService;
	private BsProductsService bsProductsService ;

	public void deleteNoneActiveUser(){
		webUserService.deleteNoneActiveUser();
	}
	
	public void updateOrderState(){
		orderService.updateOrderState();
	}
	
	public void cancelDiscount(){
		bsProductsService.cancelDiscount();
	}

	public void setWebUserService(WebUserService webUserService) {
		this.webUserService = webUserService;
	}

	public void setOrderService(OrderService orderService) {
		this.orderService = orderService;
	}

	public void setBsProductsService(BsProductsService bsProductsService) {
		this.bsProductsService = bsProductsService;
	}

	
	
}
