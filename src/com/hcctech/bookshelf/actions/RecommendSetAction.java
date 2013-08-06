package com.hcctech.bookshelf.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.services.RecommendSetService;
import com.opensymphony.xwork2.ActionSupport;


/**
 * @author apple
 *推荐电子书    热销电子书设置
 *2012-09-19
 */
public class RecommendSetAction extends ActionSupport{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7828993147113596958L;
	private RecommendSetService recommendSetService;
	private List<BsProducts> rows;
	private Page<BsProducts> bsProductsPage;
	private String productName;
	private String idStr;
	private String htmlStr;
	private int flag;
	private int pageNo=1;
	private int pageSize=10;
	private int page = 1;
	private long total;
	
	private String sort; // 排序字段
	private String order;// 排序类型
	
	private String path ;
	
	private Integer productType;
	
	/**
	 * 查询已经设置的推荐热销电子书
	 * @return
	 */
	public String loadSelectBook(){
		HttpServletRequest request = ServletActionContext.getRequest();
		path = "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath() + "/hot/recommendBook.html";
		return SUCCESS;
	}
	/**
	 * 保存推荐或热销电子书设置  
	 * @return
	 */
	public String recommendSave(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String path = request.getSession().getServletContext().getRealPath("/") + "/hot/recommendBook.html";
		flag=recommendSetService.recommendSave(path,htmlStr);
		return SUCCESS;
	}
	/**
	 * @return
	 * 查询电子书列表供选择
	 */
	public String loadProductAll(){
		bsProductsPage=recommendSetService.loadProductAll(productName,page,pageSize,sort,order,idStr,productType);
		rows=bsProductsPage.getList();
		total=bsProductsPage.getTotalCount();
		return SUCCESS;
	}
	public void setRecommendSetService(RecommendSetService recommendSetService) {
		this.recommendSetService = recommendSetService;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
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
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public List<BsProducts> getRows() {
		return rows;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public void setIdStr(String idStr) {
		this.idStr = idStr;
	}
	public int getFlag() {
		return flag;
	}
	public void setHtmlStr(String htmlStr) {
		this.htmlStr = htmlStr;
	}
	public String getHtmlStr() {
		return htmlStr;
	}
	public String getPath() {
		return path;
	}
	public void setProductType(Integer productType) {
		this.productType = productType;
	}
	
}
