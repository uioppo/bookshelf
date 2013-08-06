package com.hcctech.bookshelf.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.hcctech.bookshelf.dao.BsMyBookDao;
import com.hcctech.bookshelf.dao.BsOrderDao;
import com.hcctech.bookshelf.dao.BsOrderProductDao;
import com.hcctech.bookshelf.dao.BsProductsDao;
import com.hcctech.bookshelf.dao.BsSellSettingDao;
import com.hcctech.bookshelf.pojo.BsMybook;
import com.hcctech.bookshelf.pojo.BsOrder;
import com.hcctech.bookshelf.pojo.BsOrderProduct;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.ProductOrderService;
import com.hcctech.bookshelf.util.BuyException;

/**
 * 购买电子书  硬件授权次数  及 购买成功支付
 * @author apple
 *
 */
public class ProductOrderServiceImpl implements ProductOrderService{
	
	private BsOrderProductDao bsOrderProductDao;
	private BsOrderDao bsOrderDao;
	private BsMyBookDao bsMyBookDao;
	private BsProductsDao bsProductsDao;
	private BsSellSettingDao bsSellSettingDao;
	
	/**
	 * 生成订单号   时间+用户ID
	 * @param userId  用户ID
	 * @return
	 */
	public static String getorderno(int userId){
	       Calendar calendar =Calendar.getInstance();
	      
	       SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
	       String nowdate = sdf.format(new Date());
	       String[] d = nowdate.split("");
	       String t1 = String.valueOf(Integer.valueOf(d[1])+Integer.valueOf(d[2]));
	      Double double1 = Math.random()*100000;
	      String userIdStr = String.valueOf(userId);
	       if(userIdStr.length()>3){
	    	   userIdStr = userIdStr.substring(userIdStr.length()-3);
	       }
	       return t1+calendar.get(Calendar.DAY_OF_YEAR)+double1.intValue()+userIdStr;
	   }
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.ProductOrderService#paySuccessForCount(java.lang.String, com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public void paySuccessForCount(String orderId, BsWebUser user){
		if(orderId!=null&&!"".equals(orderId)){
			final String hql="from BsOrder b where b.orderId=?";
			BsOrder bsOrder=bsOrderDao.findUniqueByHql(hql, orderId);
			if(bsOrder!=null){
				bsOrder.setOrderState(1);
				String hql1="from BsOrderProduct b where b.bsOrder.orderId = ?";
				List<BsOrderProduct> list=bsOrderProductDao.findByHql(hql1, orderId);
				if(list!=null&&list.size()>0){
					final String hql2="from BsMybook b where b.bsWebUser.wuId=? and b.bsProducts.productId=?";
					BsMybook myBook=bsMyBookDao.findUniqueByHql(hql2, user.getWuId(),list.get(0).getBsProducts().getProductId());
					if(myBook!=null){
						myBook.setDownNumbers(myBook.getDownNumbers()+bsOrder.getKeyCount());
						myBook.setTotalNumbers(myBook.getTotalNumbers()+bsOrder.getKeyCount());
					}
				}
			}
		}
	}
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.ProductOrderService#createOrderForCount(int, int, com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public void createOrderForCount(int myBookId, int keyCount, BsWebUser user){
		if(myBookId>0&&keyCount>0){
			BsOrder bsOrder =new BsOrder();
			bsOrder.setOrderId(getorderno(user.getWuId()));
			bsOrder.setOrderState(0);
			bsOrder.setBsWebUser(user);
			bsOrder.setOrderTime(new Timestamp(System.currentTimeMillis()));
			bsOrder.setType(2);
			bsOrder.setKeyCount(keyCount);
			bsOrder.setPrivileged(0d);
			bsOrderDao.save(bsOrder);
			String hql = "from BsProducts b where b.productId=?";
			BsProducts bsProducts = bsProductsDao.findUniqueByHql(hql, myBookId);
			BsOrderProduct bsOrderProduct=new BsOrderProduct();
			bsOrderProduct.setBsOrder(bsOrder);
			bsOrderProduct.setBsProducts(bsProducts);
			bsOrderProduct.setBuyPrice(bsProducts.getAccreditPrice()*keyCount);
			bsOrderProduct.setPrivileged(0d);
			bsOrderProductDao.save(bsOrderProduct);
			bsOrder.setOrderMoney(bsProducts.getAccreditPrice()*keyCount);
		}
	}
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.ProductOrderService#createOrder(java.lang.String, com.hcctech.bookshelf.pojo.BsWebUser)
	 */
	public BsOrder createOrder(String ids, BsWebUser user) throws BuyException{
		BsOrder bsOrder = null;
		if(ids!=null&&!"".equals(ids.trim())){
			String id_str = "";
			String count_str = "";
			String[] id_count_arr=ids.split(",");
			for(int s=0;s<id_count_arr.length;s++){
				String id_count_str=id_count_arr[s];
				String[] id_count_=id_count_str.split("_");
				id_str+=id_count_[0]+",";
				count_str+=id_count_[1]+",";
			}
			String orderid = testOrderId(user.getWuId());
			
			Double privileged = 0d;
			Double orderMoney =0d;
			DecimalFormat df = new DecimalFormat("#.00");
			bsOrder =new BsOrder();
			bsOrder.setOrderId(orderid);
			bsOrder.setOrderState(0);
			bsOrder.setBsWebUser(user);
			bsOrder.setOrderTime(new Timestamp(System.currentTimeMillis()));
			bsOrder.setType(1);
			id_str=id_str.substring(0,id_str.length()-1);
			String sql = "select * from bs_products where product_id in ("+id_str+") and status=1";
			List<BsProducts> list = new ArrayList<BsProducts>();
			try{
				list=bsProductsDao.findBySql(sql);
			}catch(Exception e){
				throw new BuyException();
			}
			if(list!=null&&list.size()!=id_count_arr.length){
				bsOrder = null;
				return bsOrder;
			}
			bsOrderDao.save(bsOrder);
			List<BsProducts> listTemp = new ArrayList<BsProducts>();
			for(BsProducts bs_:list){
				String[] id_str_arr=id_str.split(",");
				int id_index=-1;
				for(int i=0;i<id_str_arr.length;i++){
					if(id_str_arr[i]!=null&&id_str_arr[i].equals(bs_.getProductId()+"")){
						id_index=i;
					}
				}
				if(id_index==-1){
					throw new BuyException();
				}
				String[] buyCount1=count_str.split(",");
				int buyCount=Integer.valueOf(buyCount1[id_index]);
				if(buyCount<=0){
					throw new BuyException();
				}
				bs_.setBuyCount(buyCount);
			}
			listTemp.addAll(list);
			for(BsProducts bs:list){
				BsOrderProduct bsOrderProduct=new BsOrderProduct();
				if(bs.getVipPrice()!=null && bs.getVipPrice()>0d){
					orderMoney+=bs.getVipPrice()*bs.getBuyCount();
					privileged+=Double.valueOf(df.format((bs.getPrice()-bs.getVipPrice())*bs.getBuyCount()));
					bsOrderProduct.setBsOrder(bsOrder);
					bsOrderProduct.setBsProducts(bs);
					bsOrderProduct.setBuyPrice(bs.getVipPrice());
					bsOrderProduct.setPrivileged(Double.valueOf(df.format(bs.getPrice()-bs.getVipPrice())));
					bsOrderProduct.setBuyCount(bs.getBuyCount());
					bsOrderProductDao.save(bsOrderProduct);
					listTemp.remove(bs);
				}else if(bs.getDownPrice()!=null && bs.getDownPrice()>0d){
					orderMoney+=Double.valueOf(df.format((bs.getDownPrice()*bs.getPrice()/10)*bs.getBuyCount()));
					privileged+=Double.valueOf(df.format((bs.getPrice()-bs.getDownPrice()*bs.getPrice()/10)*bs.getBuyCount()));
					bsOrderProduct.setBsOrder(bsOrder);
					bsOrderProduct.setBsProducts(bs);
					bsOrderProduct.setBuyPrice(Double.valueOf(df.format(bs.getDownPrice()*bs.getPrice()/10)));
					bsOrderProduct.setPrivileged(Double.valueOf(df.format(bs.getPrice()-bs.getDownPrice()*bs.getPrice()/10)));
					bsOrderProduct.setBuyCount(bs.getBuyCount());
					bsOrderProductDao.save(bsOrderProduct);
					listTemp.remove(bs);
				}
			}
			double total = 0d;
			//double avg = 0d;
			//int totalBuyCount=0;
			for(BsProducts t:listTemp){
				total += t.getPrice()*t.getBuyCount();
				//totalBuyCount+=t.getBuyCount();
			}
			if(total>0d){
				String settingSql = "";
				settingSql +="select case when sale_type=1 then (?-price_setting) when sale_type=0 then (?*price_setting/10) end as v_price ";
				settingSql +="from bs_sell_setting where start_time<=now() and end_time>=now() "; 
				settingSql +="and price_level <= ? having v_price is not null order by v_price asc limit 0,1";
				double bestPrice = bsSellSettingDao.findBestPrice(settingSql, total,total,total);
				if(bestPrice>0d){
					orderMoney+=bestPrice;
					privileged+=total-bestPrice;
				}else{
					bestPrice=total;
					orderMoney+=total;
				}
				//avg = (total-bestPrice)/totalBuyCount;//每本书优惠的价格
					for(BsProducts t:listTemp){
						BsOrderProduct bsOrderProduct=new BsOrderProduct();
						bsOrderProduct.setBsOrder(bsOrder);
						bsOrderProduct.setBsProducts(t);
						bsOrderProduct.setBuyPrice(t.getPrice());
						bsOrderProduct.setPrivileged(0d);
						bsOrderProduct.setBuyCount(t.getBuyCount());
						bsOrderProductDao.save(bsOrderProduct);
					}
			}
			bsOrder.setPrivileged(privileged);
			bsOrder.setOrderMoney(orderMoney);
		}
		return bsOrder;
	}
	private String testOrderId(int userid) {
		String orderid = getorderno(userid);
		final String testIdSql = "select count(order_id) from bs_order where order_id = '"+orderid+"'";
		int count = bsOrderDao.executeSQLByCount(testIdSql);
		if(count>0)
			testOrderId(userid);
		return orderid;
	}
	
	/**
	 * 付款成功   更新订单状态
	 * @param orderId
	 */
	public void paySuccess(String orderId){
		if(orderId!=null&&!"".equals(orderId)){
			final String hql="from BsOrder b where b.orderId=?";
			BsOrder bsOrder=bsOrderDao.findUniqueByHql(hql, orderId);
			if(bsOrder!=null){
				if(bsOrder.getOrderState()==0){
					bsOrder.setOrderState(1);
					BsWebUser user=bsOrder.getBsWebUser();
					addMyBook(orderId,user);
				}
			}else{
				//操作失败     返回到支付宝信息    回退金额
			}
		}
	}
	
	/**
	 * 购买成功后  往我的电子书里添加数据 
	 * @param orderId  订单ID
	 * @param user   用户ID
	 */
	public void addMyBook(String orderId,BsWebUser user){
		String hql="from BsOrderProduct b where b.bsOrder.orderId = ?";
		List<BsOrderProduct> list=bsOrderProductDao.findByHql(hql, orderId);
		final String hql2="from BsMybook b where b.bsWebUser.wuId=?";
		List<BsMybook> myBookList=bsMyBookDao.findByHql(hql2,user.getWuId());
		Calendar now = Calendar.getInstance();  
		Timestamp t=new Timestamp(new Date().getTime());
		boolean flag=false;
		if(list!=null&&list.size()>0){
			String keyCountStr=null;
			try {
				InputStream inputStream = ProductOrderServiceImpl.class.getClassLoader()
				.getResourceAsStream("licenseKey.properties");
				Properties p = new Properties();
				p.load(inputStream);
				keyCountStr=p.getProperty("keyCount");
			} catch (IOException e) {
				keyCountStr="3";
			}
			int keyCount=Integer.valueOf(keyCountStr);
			for(BsOrderProduct bsOrderProduct:list){
				BsProducts bsProducts=bsOrderProduct.getBsProducts();
				if(myBookList!=null&&myBookList.size()>0){
					for(BsMybook bsMybook:myBookList){
						if(bsProducts.getProductId()==bsMybook.getBsProducts().getProductId()){
							if(bsMybook.getDeadline()!=null){
								if(bsProducts.getUsePeriod()==1200){
									bsMybook.setDeadline(null);
								}else{
									if(bsMybook.getDeadline().getTime()>=t.getTime()){
										now.setTime(bsMybook.getDeadline()); 
									}else{
										now.setTime(t);
									}
								    now.set(Calendar.MONTH, now.get(Calendar.MONTH)+bsProducts.getUsePeriod());  
									bsMybook.setDeadline(new Timestamp(now.getTimeInMillis()));
								}
							}
							bsMybook.setDownNumbers(bsMybook.getDownNumbers()+(bsOrderProduct.getBuyCount()*((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?keyCount:bsProducts.getDownNumber())));
							bsMybook.setTotalNumbers(bsMybook.getTotalNumbers()+(bsOrderProduct.getBuyCount()*((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?keyCount:bsProducts.getDownNumber())));
							flag=true;
						}
					}
				}
				if(flag){
					flag=false;
				}else{
					flag=false;
					BsMybook bsMybook1=new BsMybook();
					bsMybook1.setAddTime(t);
					bsMybook1.setBsProducts(bsProducts);
					bsMybook1.setBsWebUser(user);
					now.setTime(t);  
					if(bsProducts.getUsePeriod()!=null){
						if(bsProducts.getUsePeriod()==1200){
							bsMybook1.setDeadline(null);
						}else{
							now.set(Calendar.MONTH, now.get(Calendar.MONTH)+bsProducts.getUsePeriod());  
							bsMybook1.setDeadline(new Timestamp(now.getTimeInMillis()));
						}
					}else{
						now.set(Calendar.MONTH, now.get(Calendar.MONTH)+0);  
						bsMybook1.setDeadline(new Timestamp(now.getTimeInMillis()));
					}
					bsMybook1.setDownNumbers(bsOrderProduct.getBuyCount()*keyCount);
					bsMybook1.setTotalNumbers(bsOrderProduct.getBuyCount()*keyCount);
					bsMyBookDao.save(bsMybook1);
				}
			}
		}
	}
	public void setBsOrderProductDao(BsOrderProductDao bsOrderProductDao) {
		this.bsOrderProductDao = bsOrderProductDao;
	}

	public void setBsOrderDao(BsOrderDao bsOrderDao) {
		this.bsOrderDao = bsOrderDao;
	}
	public void setBsMyBookDao(BsMyBookDao bsMyBookDao) {
		this.bsMyBookDao = bsMyBookDao;
	}
	public void setBsProductsDao(BsProductsDao bsProductsDao) {
		this.bsProductsDao = bsProductsDao;
	}
	public void setBsSellSettingDao(BsSellSettingDao bsSellSettingDao) {
		this.bsSellSettingDao = bsSellSettingDao;
	}
}
