package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsProducts;

public interface ProductsSearchService {
	/**
	 * 产品的搜索
	 * @param pageNo
	 * @param pageSize
	 * @param values
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page<BsProducts> findAllBook(int pageNo, int pageSize,
			String values);
}
