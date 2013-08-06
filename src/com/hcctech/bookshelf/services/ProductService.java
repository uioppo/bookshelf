package com.hcctech.bookshelf.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsProducts;

/**
 * 商品接口
 * 
 * @author randyjie
 * 2012年10月30日 18:32:44
 */
public interface ProductService {

	/**
	 * 主要用于  前台分类显示。
	 * @param page
	 * @param pageSize
	 * @param xd
	 * @param xk
	 * @param type 
	 * @param sort 
	 * @param order 
	 * @return
	 */
	@Transactional(readOnly=true)
	Page<BsProducts> loadEbooksByOrg(Integer page, int pageSize, String xd,
			String xk, String type, String order, String sort);

	/**
	 * 用于搜索
	 * @param page
	 * @param pageSize
	 * @param xd
	 * @param xk
	 * @param type
	 * @param order
	 * @param sort
	 * @param keyword
	 * @return
	 */
	@Transactional(readOnly=true)
	Page<BsProducts> loadEbooksByOrg(Integer page, int pageSize, String xd,
			String xk, String type, String order, String sort, String keyword);
	
	/**
	 * 首页 -- 新书推荐
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=true)
	List<BsProducts> recommendNewBook(int type, int start ,int end);

}
