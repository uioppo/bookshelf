package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsProducts;


/**
 * @author apple
 * 推荐电子书    热销电子书设置  
 * 2012-09-19
 */
public interface RecommendSetService {

	/**
	 * 推荐  热销  商品设置    查询所有商品
	 * @param order 排序类型
	 * @param sort  排序字段
	 * @param pageSize 每页条数
	 * @param productType 
	 * @param pageNo 当前页
	 * @return Page<BsProducts> page
	 */
	@Transactional(readOnly=true)
	Page<BsProducts> loadProductAll(String productName, int page, int pageSize,
			String sort, String order, String idStr, Integer productType);

	/**
	 * 保存推荐电子书设置
	 * @param htmlStr 静态html
	 * @param type 推荐热销区分
	 * @return
	 */
	@Transactional(readOnly=true)
	int recommendSave(String path ,String htmlStr);
//	/**
//	 * 查询已经设置的热销或推荐电子书
//	 * 
//	 * @return htmlStr
//	 */
//	@Transactional(readOnly=true)
//	String loadSelectBook(String path);

}
