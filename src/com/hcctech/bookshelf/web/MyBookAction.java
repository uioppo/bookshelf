package com.hcctech.bookshelf.web;

import java.util.List;


import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsMybook;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.MyBookService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 前台商城     我的电子书
 */
public class MyBookAction extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5668358383768734260L;
	
	private MyBookService myBookService;
	
	private Page<BsMybook> bsMybookPage;
	private List<BsMybook> rows;
	private BsMybook bsMybook;
	
	private int pageSize=5;
	private int page = 1;
	private long total;
	
	private String licenseKey;
	private int myBookId;
	private int productId;
	private String msg;
	/**
	 * 电子书详细页面  激活电子书
	 * licenseKey 授权码
	 * productId 商品ID
	 * @return
	 */
	public String activeBookByKey(){
		msg=myBookService.activeBookByKey(licenseKey,productId);
		return SUCCESS;
	}
	/**
	 * 购买硬件授权次数的页面信息
	 * @return
	 */
	public String myBookDetailForCount(){
		bsMybook=myBookService.myBookDetail(myBookId);
		return SUCCESS;
	}
	
	/**
	 *查看我的电子书详细信息
	 * @return
	 */
	public String myBookDetail(){
		bsMybook=myBookService.myBookDetail(myBookId);
		return SUCCESS;
	}
	/**
	 * 用授权码添加我的电子书
	 */
	public String addMyBookByKey(){
		msg=myBookService.addMyBookByKey(licenseKey);
		return SUCCESS;
	}
	/**
	 * 查询我的电子书列表
	 */
	public String myBookList(){
		BsWebUser bsWebUser=(BsWebUser)ActionContext.getContext().getSession().get("user");
		if(bsWebUser!=null){
			bsMybookPage=myBookService.myBookList(page,pageSize,bsWebUser);
		}else{
			return "denglu";
		}
		return SUCCESS;
	}
	public void setMyBookService(MyBookService myBookService) {
		this.myBookService = myBookService;
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
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public Page<BsMybook> getBsMybookPage() {
		return bsMybookPage;
	}
	public List<BsMybook> getRows() {
		return rows;
	}
	public void setLicenseKey(String licenseKey) {
		this.licenseKey = licenseKey;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public BsMybook getBsMybook() {
		return bsMybook;
	}
	public void setMyBookId(int myBookId) {
		this.myBookId = myBookId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
}
