package com.hcctech.bookshelf.services;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsProducts;

public interface BsProductsService {
	@Transactional(readOnly=true)
	public Page<BsProducts> loadProductList(String columnName , String value,Integer pageSize , Integer pageNumber,Integer state) ;
	@Transactional(readOnly=true)
	public Page<BsProducts> loadDiscountList(String productType,String schoolStage,String subject ,String searchName ,Integer pageSize , Integer pageNumber,Integer state) ;
	
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean updateProductStatus(Integer id , Integer oldStatus,Integer newStatus, BsAdminUser bsAdminUser);
	
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean addProduct(BsProducts bsProducts);
	
	@Transactional(readOnly=true)
	public BsProducts loadBsProduct(Integer pId);
	
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean updateProduct(BsProducts bsProducts,String change) ;

	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean updatePrice(Integer proId, String columnName,Double specialPrice,Double accreditPrice,Date startTime,Date endTime);
	
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean updateBatchPrice(String proIds,Double specialPrice,Date startTime,Date endTime);
	
	/**
	 * zjp
	 * 根据商品id[int] 字符串查询
	 * exp:select * from bs_products where id in (1,2,34,6)
	 * @param ids
	 * @return
	 * 暂时用在购物车方面
	 */
	@Transactional(readOnly=true)
	public Map<String, Object> loadBestPrice_ProductsByIds(String ids);
	
	@Transactional(readOnly=true)
	public BsProducts findProductsById(Integer pId);
	/**
	 * 根据商品ID_STR查询商品信息
	 * @param ids 
	 * @return
	 */
	@Transactional(readOnly=true)
	List<BsProducts> loadProductsByIds(String ids);
	/**
	 * 取消商品优惠设置（自动检索）
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void cancelDiscount();
}
