package com.hcctech.bookshelf.services.impl;

import java.util.Set;

import com.hcctech.bookshelf.dao.BsOrderDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsOrder;
import com.hcctech.bookshelf.pojo.BsOrderProduct;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.OrderService;

public class OrderServiceImpl implements OrderService{

	private BsOrderDao bsOrderDao;
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.OrderService#findAllOrder(int, int, java.lang.String, java.lang.String)
	 */
	public Page<BsOrder> findAllOrder(int pageNo,int pageSize,String name,String value,String orderState) {
		String hql = "from BsOrder as b where 1=1 ";
		if(name!=null && !"".equals(name) && value!=null && !"".equals(value)){
			if("orderId_".equals(name)){
				hql += " and b.orderId like '%"+value+"%'";
			}
			if("orderUserName_".equals(name)){
				hql += " and b.bsWebUser.wuEmail like '%"+value+"%'";
			}
		}
		if(orderState!=null&&!"".equals(orderState)){
			if(!("all".equals(orderState))){
				try{
					int status_=Integer.valueOf(orderState);
					hql += "and b.orderState = "+status_;
				}catch(NumberFormatException e){
				}
			}
		}
		hql += " order by b.orderId desc";
		Page<BsOrder> pageObject = bsOrderDao.pagedQuery(hql, pageNo, pageSize);
		if(pageObject!=null){
			for(BsOrder bsOrder:pageObject.getList()){
				try{
					BsWebUser bw = bsOrder.getBsWebUser();
					if(bw != null){
						bw.setBsMybooks(null);
						bw.setBsLicenseKeies(null);
						bw.setBsMybooks(null);
						bw.setBsOrders(null);
						bw.setBsUserDrms(null);
						bw.setBsUserInfo(null);
					}
				}catch(Exception e){
					bsOrder.setBsWebUser(null);
				}
				
				bsOrder.setBsOrderProducts(null);
			}
		}
		return pageObject;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.OrderService#findOrderById(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public BsOrder findOrderById(String orderId){
		final String hql = "from BsOrder where orderId = ?";
		BsOrder bsOrder = bsOrderDao.findUniqueByHql(hql, orderId);
		try{
			BsWebUser bw = bsOrder.getBsWebUser();
			bw.setBsMybooks(null);
			bw.setBsLicenseKeies(null);
			bw.setBsMybooks(null);
			bw.setBsOrders(null);
			bw.setBsUserDrms(null);
			bw.setBsUserInfo(null);
		}catch(Exception e){
			bsOrder.setBsWebUser(null);
		}
		Set<BsOrderProduct> set = (Set<BsOrderProduct>)bsOrder.getBsOrderProducts();
		for(BsOrderProduct bop:set){
			bop.setBsOrder(null);
			BsProducts bp =  bop.getBsProducts();
			bp.setBsLicenseKeies(null);
			bp.setBsMybooks(null);
			bp.setBsOrderProducts(null);
		}
		return bsOrder;
	}
	
	/**
	 * 订单列表
	 * @param wuId 用户ID
	 * @param orderId 查询的orderId
	 * @param pageSize 每页行数
	 * @param pageNumber 页数
	 */
	public Page<BsOrder> loadOrderList(Integer wuId ,String orderId,
			Integer pageSize, Integer pageNumber) {
		Page<BsOrder> page = null ;
		
		try {
			String hql = "FROM BsOrder order where order.bsWebUser.wuId = " + wuId ;
			if(orderId != null ) {
				hql += " and order.orderId = '" + orderId + "'" ;
			}
			hql += "order by order.orderTime desc" ;
			page = bsOrderDao.pagedQuery(hql, pageSize, pageNumber);
			if(page!=null){
				for(BsOrder bsOrder:page.getList()){
					BsWebUser bw = bsOrder.getBsWebUser();
					bw.setBsMybooks(null);
					bw.setBsLicenseKeies(null);
					bw.setBsMybooks(null);
					bw.setBsOrders(null);
					bw.setBsUserDrms(null);
					bw.setBsUserInfo(null);
					bsOrder.setBsOrderProducts(null);
				}
			}
		} catch(Exception e) {
		}
		
		return page;
	}
	
	/**
	 * 查询订单号
	 * @param orderId 查询的订单号
	 */
	public BsOrder loadOrderById(String orderId,Integer wuId){
		final String hql = "from BsOrder where orderId = ? and wu_id = ?";
		BsOrder bsOrder = bsOrderDao.findUniqueByHql(hql, orderId,wuId);
		if(bsOrder != null) {
			bsOrder.setBsWebUser(null);
			bsOrder.setBsOrderProducts(null);
		}
		return bsOrder;
	}
	
	/**
	 * 订单详细信息
	 * @param orderId 订单号
	 * @param wuId    用户ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public BsOrder loadDetailOrderById(String orderId,Integer wuId){
		final String hql = "from BsOrder where orderId = ? and wu_id = ?";
		BsOrder bsOrder = bsOrderDao.findUniqueByHql(hql, orderId,wuId);
		if(bsOrder != null) {
			bsOrder.setBsWebUser(null);
			Set<BsOrderProduct> set = (Set<BsOrderProduct>)bsOrder.getBsOrderProducts();
			for(BsOrderProduct bop:set){
				bop.setBsOrder(null);
				BsProducts bp =  bop.getBsProducts();
				bp.setBsLicenseKeies(null);
				bp.setBsMybooks(null);
				bp.setBsOrderProducts(null);
			}
		}
		return bsOrder;
	}
	
	/**
	 * 取消订单
	 * @param orderId 订单号
	 * @param wuId    用户ID
	 * @return
	 */
	public boolean cancelOrder(String orderId,Integer wuId) {
		final String hql = "update bs_order set order_state = 2 where order_id = ? and wu_id = ?";
		boolean flag = false ;
		try {
			bsOrderDao.executeSQL(hql, orderId,wuId);
			flag = true ;
		}catch(Exception e){
		}
		return flag ;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.OrderService#updateOrderState()
	 */
	public void updateOrderState(){
		//生成订单大于一天未付款的   状态改成取消
		final String sql = "update bs_order set order_state = 2 where order_state = 0 and date_add(order_time,interval 1 day)<=now()";
		bsOrderDao.executeSQL(sql);
	}
	public void setBsOrderDao(BsOrderDao bsOrderDao) {
		this.bsOrderDao = bsOrderDao;
	}
}
