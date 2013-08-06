package com.hcctech.bookshelf.services.impl;

import com.hcctech.bookshelf.dao.BsProductsDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.services.ProductsSearchService;

public class ProductsSearchServiceImpl implements ProductsSearchService {
	private BsProductsDao bsProductsDaoImpl;

	public Page<BsProducts> findAllBook(int pageNo, int pageSize, String values) {
		String hql = "FROM BsProducts AS b WHERE  b.productName LIKE '%" + values
				+ "%'";
		Page<BsProducts> page = bsProductsDaoImpl.pagedQuery(hql, pageNo, pageSize);
		return page;
	}

	public void setBsProductsDaoImpl(BsProductsDao bsProductsDaoImpl) {
		this.bsProductsDaoImpl = bsProductsDaoImpl;
	}

}
