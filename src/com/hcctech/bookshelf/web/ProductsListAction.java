package com.hcctech.bookshelf.web;

import java.util.List;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.services.ProductService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 前台的商品 分类列表
 * @author randyjie
 * 2012年10月30日 18:22:04
 */
public class ProductsListAction  extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2641071642132296419L;
	//参数
	private String type;
	private String xd;
	private String xk;
	private Integer page;
	private Long total;
	private String order;
	private String sort;
	private List<BsProducts> products;
	//service
	private ProductService productService;
	
	private String keyword;
	
	private int productType ;
	private BsProducts bsProducts;
	private List<BsProducts> newBook;
	/**
	 * 根据条件获得所有的商品
	 * @return
	 */
	public String ebooks(){
		
		final int pageSize = 12;
		
		Page<BsProducts> ps = null; 
		ps = productService.loadEbooksByOrg(page,pageSize,xd,xk,type,order,sort);
		products = ps.getList();
		total = ps.getTotalCount();
		return SUCCESS;
	}
	
	/**
	 * 搜索商品
	 * @return
	 */
	public String search(){
		
		final int pageSize = 12;
		
		Page<BsProducts> ps = null; 
		ps = productService.loadEbooksByOrg(page,pageSize,xd,xk,type,order,sort,keyword);
		products = ps.getList();
		total = ps.getTotalCount();
		
		return SUCCESS;
	}
	
	public String newBook(){
		newBook = productService.recommendNewBook(productType,0,10);
		return SUCCESS;
	}
	
	public List<BsProducts> getProducts() {
		return products;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setXd(String xd) {
		this.xd = xd;
	}
	public void setXk(String xk) {
		this.xk = xk;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}

	public Long getTotal() {
		return total==null?0:total;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public BsProducts getBsProducts() {
		return bsProducts;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public List<BsProducts> getNewBook() {
		return newBook;
	}
	
	
}
