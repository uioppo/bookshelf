package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.pojo.BsOrder;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.util.BuyException;


/**
 * 购买电子书    硬件授权次数及支付成功操作
 * @author apple
 *
 */
public interface ProductOrderService {

	/**
	 * 生成订单
	 * @param ids 商品Id
	 * @param user 用户
	 * @return 
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	BsOrder createOrder(String ids, BsWebUser user) throws BuyException;
	
	/**
	 * 支付成功  更新订单状态    添加电子书
	 * @param orderId 订单ID
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void paySuccess(String orderId);

	/**
	 * 我的电子书   购买硬件授权次数   生成订单  
	 * @param myBookId  我的电子书Id
	 * @param keyCount 购买次数
	 * @param user 当前用户
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void createOrderForCount(int myBookId, int keyCount,BsWebUser user);

	/**
	 * 我的电子书  购买硬件授权次数   支付成功
	 * @param orderId 订单ID
	 * @param user 当前用户
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	void paySuccessForCount(String orderId, BsWebUser user);
}
