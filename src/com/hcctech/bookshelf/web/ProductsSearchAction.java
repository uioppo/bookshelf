package com.hcctech.bookshelf.web;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.services.ProductsSearchService;
import com.opensymphony.xwork2.ActionSupport;

/**
 *产品的搜索
 * 
 * @author yong
 * 
 */
public class ProductsSearchAction extends ActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2672561282801637336L;
	private ProductsSearchService productsSearchServiceImpl;
	private int pageNo = 1;
	private int pageSize = 5;
	private int countPage;
	private String searchkw;
	private int total;
	private List<BsProducts> rows;

	/**
	 * 电子书搜索
	 * 
	 * @return
	 *
	 */
	public String ebookSearch() {
		Page<BsProducts> page = null;
		if (pageNo < 1) {
			pageNo = 1;
		}
		try {
			page = productsSearchServiceImpl.findAllBook(pageNo, pageSize,
					java.net.URLDecoder.decode(searchkw, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			///
			page = null;
		}
		if(page==null){
			rows = null;
			total=0;
			return "list";
		}
		rows = page.getList();
		total = (int) page.getTotalCount();
		if (total % pageSize == 0) {
			countPage = total / pageSize;
		} else {
			countPage = (total / pageSize) + 1;
		}
		if (pageNo > countPage) {
			pageNo = countPage;
		}
		return "list";
	}

	public int getCountPage() {
		return countPage;
	}

	public void setProductsSearchServiceImpl(
			ProductsSearchService productsSearchServiceImpl) {
		this.productsSearchServiceImpl = productsSearchServiceImpl;
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

	public String getSearchkw() {
		String s = "";
		try {
			s = java.net.URLDecoder.decode(searchkw, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return s;
	}

	public void setSearchkw(String searchkw) {
		this.searchkw = searchkw;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<BsProducts> getRows() {
		return rows;
	}

	public void setRows(List<BsProducts> rows) {
		this.rows = rows;
	}

	public void setCountPage(int countPage) {
		this.countPage = countPage;
	}

}
