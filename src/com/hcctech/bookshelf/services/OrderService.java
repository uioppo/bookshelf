package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsOrder;

/**
 * @author zoujunpeng
 * 订单管理
 * 查询
 * 添加
 * 删除
 */
public interface OrderService {
	
	/**
	 * 分页查询订单列表【搜索】
	 * @param pageNo
	 * @param pageSize
	 * @param name
	 * @param value
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<BsOrder> findAllOrder(int pageNo,int pageSize,String name,String value,String orderState);
	@Transactional(readOnly = true)
	public BsOrder findOrderById(String orderId);
	
	@Transactional(readOnly=true)
	public Page<BsOrder> loadOrderList(Integer wuId ,String orderId,Integer pageSize , Integer pageNumber) ;
	@Transactional(readOnly=true)
	public BsOrder loadOrderById(String orderId,Integer wuId);
	@Transactional(readOnly=true)
	public BsOrder loadDetailOrderById(String orderId,Integer wuId);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean cancelOrder(String orderId,Integer wuId);
	/**
	 * 定期更改订单状态
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void updateOrderState();
}
