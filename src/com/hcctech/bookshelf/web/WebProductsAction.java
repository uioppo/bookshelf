package com.hcctech.bookshelf.web;

import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.BsProductsService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author gaodengke
 * 商品详情
 */
public class WebProductsAction extends ActionSupport {
	private static final long serialVersionUID = -7724940749084182539L;
	private BsProductsService bsProductsService;
	private BsProducts bsProducts;
	private int id;
	private String msg;
	
	/**
	 * 验证用户是否登录
	 * @return
	 */
	public String validateLogin(){
		BsWebUser bsWebUser=(BsWebUser)ActionContext.getContext().getSession().get("user");
		if(bsWebUser!=null){
			msg="0";
		}else{
			msg="1";
		}
		return SUCCESS;
	}
	/**
	 * 根据id查询商品
	 * @return
	 */
	public String findProductsById(){
		String str="";
		bsProducts=bsProductsService.findProductsById(id);
		if(bsProducts==null||bsProducts.getStatus()==0){
			str="notFound";
		}else{
			str="success";
		}
		return str;
	}


	public void setBsProductsService(BsProductsService bsProductsService) {
		this.bsProductsService = bsProductsService;
	}
	public BsProducts getBsProducts() {
		return bsProducts;
	}
	public void setBsProducts(BsProducts bsProducts) {
		this.bsProducts = bsProducts;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
