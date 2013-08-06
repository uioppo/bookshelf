package com.hcctech.bookshelf.services.impl;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.axis2.AxisFault;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hcctech.bookshelf.dao.BsAdminUserDao;
import com.hcctech.bookshelf.dao.BsProductsDao;
import com.hcctech.bookshelf.dao.BsSellSettingDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.pojo.BsSellSetting;
import com.hcctech.bookshelf.services.BsProductsService;
import com.hcctech.bookshelf.util.ThirdVelocityEmailUtil;
import com.hcctech.webservice.util.UNZipServiceImplServiceCallbackHandler;
import com.hcctech.webservice.util.UNZipServiceImplServiceStub;
import com.hcctech.webservice.util.UNZipServiceImplServiceStub.Unzip;
import com.hcctech.webservice.util.UNZipServiceImplServiceStub.UnzipE;
import com.hcctech.webservice.util.UNZipServiceImplServiceStub.UnzipResponseE;

public class BsProductsServiceImpl implements BsProductsService {
	
	private BsProductsDao bsProductsDao ;
	private BsSellSettingDao bsSellSettingDao;
	private ThirdVelocityEmailUtil thirdVelocityEmailUtil;
	private BsAdminUserDao bsAdminUserDao;
	private final static Log logger = LogFactory.getLog(BsProductsServiceImpl.class);
	
	/**
	 * 特价设置 0优惠 ，1打折
	 * @param proId 商品ID
	 * @param columnName 列名(优惠价或者打折)
	 * @param specialPrice 对应的优惠价或者折扣
	 * @param accreditPrice 授权价
	 */
	public boolean updatePrice(Integer proId, String columnName,Double specialPrice,Double accreditPrice,Date startTime,Date endTime) {
		boolean flag = false ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer  sql  = new StringBuffer();
			sql.append("update bs_products ") ;
			if("0".equals(columnName) || "1".equals(columnName)) {
				sql.append("set temp_price = ");
				sql.append(specialPrice+",");
				sql.append("switchs = ");
				sql.append(columnName + ",") ;
			}
			sql.append("start_time = '") ;
			sql.append(sdf.format(startTime) + "',");
			sql.append("end_time = '");
			sql.append(sdf.format(endTime) + "'");
			sql.append(" where product_id = " + proId);
			bsProductsDao.executeSQL(sql.toString());
			flag = true;
		} catch(Exception e) {
		}
		return flag;
	}
	
	/**
	 * 批量特价设置 0优惠 ，1打折
	 * proIds 商品的ID
	 * specialPrice 折扣
	 * startTime 开始时间
	 * endTime 结束时间
	 */
	public boolean updateBatchPrice(String proIds,Double specialPrice,Date startTime,Date endTime) {
		boolean flag = false ;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			StringBuffer  sql  = new StringBuffer();
			sql.append("update bs_products ") ;
			sql.append("set temp_price = ");
			sql.append(specialPrice+",");
			sql.append("switchs = 1,");
			sql.append("start_time = '") ;
			sql.append(sdf.format(startTime) + "',");
			sql.append("end_time = '");
			sql.append(sdf.format(endTime) + "'");
			sql.append(" where product_id in( " + proIds + ")");
			bsProductsDao.executeSQL(sql.toString());
			flag = true;
		} catch(Exception e) {
		}
		return flag;
	}
	
	/**
	 * 修改商品
	 * 上传文件的状态 ，0 --未改变， 1-- 已改变
	 * 上架状态的商品编辑保存后为待审核（1-->3)
	 * 回退列表的商品编辑保存后状态不变
	 * @param bsProducts 修改的商品
	 * @param change 是否重新上传了试读片段
	 */
	public boolean updateProduct(BsProducts bsProducts,String change) {
		boolean flag = false ;
		try {
			if("1".equals(change)){
				String path = unzipSample(bsProducts.getTryUrl());
				if(path == null){
					return flag ;
				}
				bsProducts.setTryUrl(path);
			}
			if(bsProducts.getStatus() == 1){
				bsProducts.setStatus(3);
			}
			bsProducts.setUpdateTime(new Date());
			StringBuffer  sql  = new StringBuffer();
			sql.append("update bs_products set product_name = ?,price = ?,") ;
			sql.append("use_period = ?,publish_time = ?,thumbnail = ?,");
			sql.append("try_url =?,description = ?,catalog = ?,");
			sql.append("option_author = ? ,option_authorid = ? ,update_time = ?,");
			sql.append("status = ? ,down_number = ? " );
			sql.append("where product_id = ?" );
			bsProductsDao.executeSQL(sql.toString(),bsProducts.getProductName(),bsProducts.getPrice(),
					bsProducts.getUsePeriod(),bsProducts.getPublishTime(),bsProducts.getThumbnail(),
					bsProducts.getTryUrl(),bsProducts.getDescription(),bsProducts.getCatalog(),
					bsProducts.getOptionAuthor(),bsProducts.getOptionAuthorid(),bsProducts.getUpdateTime_(),
					bsProducts.getStatus(),bsProducts.getDownNumber(),bsProducts.getProductId()
			);
			logger.info("管理员修改电子书商品，商品名称:"+bsProducts.getProductName());
			flag = true;
		}catch(Exception e) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * 加载商品
	 * @param pId 商品ID
	 */
	public BsProducts loadBsProduct(Integer pId) {
		BsProducts bs = bsProductsDao.load(pId);
		bs.setBsLicenseKeies(null);
		bs.setBsMybooks(null);
		bs.setBsOrderProducts(null);
		return bs ;
	}
	
	/**
	 * 添加商品(状态待审核，status=3)
	 * @param bsProducts 添加的商品
	 */
	public boolean addProduct(BsProducts bsProducts) {
		boolean flag = false ;
		try {	
//			String path = unzipSample(bsProducts.getTryUrl());
//			if(path == null){
//				return flag ;
//			}
//			bsProducts.setTryUrl(path);
			bsProductsDao.save(bsProducts);
			logger.info("管理员添加电子书商品，商品名称:"+bsProducts.getProductName());
			flag = true ;
		}catch(Exception e) {
		}
		
		return flag;
	}
	
	/**
	 * 商品列表
	 * @param columnName 查询的选项（商品编号或商品名称）
	 * @param value 查询值
	 * @param pageSize 每页行数
	 * @param pageNumber 页数
	 * @param state 上架或下架(0：下架 1：上架)
	 */
	public Page<BsProducts> loadProductList(String columnName , String value,Integer pageSize , Integer pageNumber ,Integer state) {
		Page<BsProducts> page = null ;
		 String hql = "FROM BsProducts p where status = " + state ;
			if(!"".equals(value) && value != null ) {
				if("productId".equals(columnName)) {
					hql += " and p.bookCode = ? "  ;
					hql += " order by p.updateTime desc";
					page = bsProductsDao.pagedQuery(hql, pageNumber, pageSize, value);
				} else if("productName".equals(columnName)) {
					hql += " and p.productName like \'%" + value + "%\'";
					hql += " order by p.updateTime desc";
					page = bsProductsDao.pagedQuery(hql, pageNumber, pageSize);
				}				
			}else {
				hql += " order by p.updateTime desc";
				page = bsProductsDao.pagedQuery(hql, pageNumber, pageSize);
			}
		
		
		return page;
	}
	
	/**
	 * 商品促销设置列表
	 */
	public Page<BsProducts> loadDiscountList(String productType,String schoolStage,String subject ,String searchName ,Integer pageSize , Integer pageNumber,Integer state) {
		Page<BsProducts> page = null ;
		 String hql = "FROM BsProducts p where status = " + state ;
			if(!"".equals(productType) && productType != null ) {
				hql += " and p.productType =  "  +  productType;		
			}
			if(!"".equals(schoolStage) && schoolStage != null){
				hql += " and p.schoolStage = " +  schoolStage;
			}
			if(!"".equals(subject) && subject != null){
				hql += "  and p.subject =  '" +  subject + "'";
			}
			if(!"".equals(searchName) && searchName != null){
				hql += "  and p.productName like '%"  + searchName + "%'";
			}
			hql += " order by p.updateTime desc";
			try{
				page = bsProductsDao.pagedQuery(hql, pageNumber, pageSize);
			}catch(Exception e){
				e.printStackTrace();
			}
			
		return page;
	
	}
	
	/**
	 * 更改商品状态
	 * 商品的上下架0：下架 1：上架2:回退3：待审核4:待发布
	 * @param id 商品ID
	 * @param status 上下架
	 */
	public boolean updateProductStatus(Integer id , Integer oldStatus,Integer newStatus,BsAdminUser bsAdminUser) {
		boolean flag = false ;
		BsProducts bsProducts = bsProductsDao.get(id);
		
//		MDC.remove("userId");
//		MDC.remove("userName");
//		MDC.remove("ip");
//		MDC.remove("roleId");
//		MDC.remove("roleName");
	
		//MDC.put(key, o);
		
		if(oldStatus == 0) {
			bsProducts.setStatus(newStatus);
			logger.info("管理员修改商品信息，商品"+bsProducts.getProductName()+"上架");
			flag = true ;
		} else if(oldStatus == 1) {
			bsProducts.setStatus(newStatus);
			logger.info("管理员修改商品信息，商品"+bsProducts.getProductName()+"下架");
			flag = true ;
		}else if(oldStatus == 2) {
			bsProducts.setStatus(newStatus);
			logger.info("管理员修改商品信息，商品"+bsProducts.getProductName()+"待审核");
			flag = true ;
		}else if(oldStatus == 3 && newStatus == 2) {
			bsProducts.setStatus(newStatus);
			logger.info("管理员修改商品信息，商品"+bsProducts.getProductName()+"回退");
			flag = true ;
			//发邮件
			final Map<String, Object> model = new HashMap<String, Object>();
			String hql_user="from BsAdminUser where auId=?";
			BsAdminUser bsAdminUser1 = bsAdminUserDao.findUniqueByHql(hql_user,bsProducts.getOptionAuthorid());
			if(bsAdminUser1!=null){
				model.put("userName",bsProducts.getOptionAuthor());
				model.put("productName",bsProducts.getProductName());
				model.put("thisUser",bsAdminUser.getRealName());
				final String userEmail = bsAdminUser1.getEmail();
				Runnable run = new Runnable() {
					public void run() {
						thirdVelocityEmailUtil.sendEmail(model, "商品审核回退", "productEmailVM.vm",new String[] {userEmail}, null);
					}
				};
				Thread thread = new Thread(run);
				thread.start();
			}
		}else if(oldStatus == 3 && newStatus == 4) {
			bsProducts.setStatus(newStatus);
			logger.info("管理员修改商品信息，商品"+bsProducts.getProductName()+"审核通过");
			flag = true ;
		}else if(oldStatus == 4 && newStatus == 1) {
			bsProducts.setStatus(newStatus);
			logger.info("管理员修改商品信息，商品"+bsProducts.getProductName()+"发布");
			flag = true ;
		}
		return flag;
	}
	
	/**
	 * 解压
	 * @param source
	 * @return
	 */
	private String unzipSample(String source) {
		String dest = null ;
		try {
			UNZipServiceImplServiceStub implServiceStub = new UNZipServiceImplServiceStub();
			Unzip unzip = new Unzip();
			int i = source.lastIndexOf("/");
			SimpleDateFormat format = new SimpleDateFormat("yyyyMM");
			String date = format.format(new Date());
			dest = source.substring(0, i+1) + date + "/" + source.substring((i+1), source.lastIndexOf("."));
			unzip.setArg0(source);
			unzip.setArg1(dest);

			UnzipE unzipE = new UnzipE();
			unzipE.setUnzip(unzip);
			implServiceStub.startunzip(unzipE, new UNZipServiceImplServiceCallbackHandler() {
			});
			
			UnzipResponseE responseE = implServiceStub.unzip(unzipE);
			responseE.getUnzipResponse().get_return();	
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return dest ;
		
	}
	
	public void setBsProductsDao(BsProductsDao bsProductsDao) {
		this.bsProductsDao = bsProductsDao;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.BsProductsService#loadThisListByIds(java.lang.String)
	 */
	public Map<String,Object> loadBestPrice_ProductsByIds(String ids){
		double bestPrice=0d;
		double totalPrice=0d;
		String bestSaleName=null;
		String bestDescrprion=null;
		Map<String,Object> map=new HashMap<String, Object>();
		String id_str = "";
		String[] id_count_arr=ids.split(",");
		String count_str = "";
		for(int s=id_count_arr.length-1;s>=0;s--){
			String id_count_str=id_count_arr[s];
			String[] id_count_=id_count_str.split("_");
			id_str+=id_count_[0]+",";
			count_str+=id_count_[1]+",";
		}
		id_str=id_str.substring(0,id_str.length()-1);
		/*根据购物车中的商品id查询商品集合*/
		String sql = "select * from bs_products where product_id in ("+id_str+") and status=1";
		List<BsProducts> list = bsProductsDao.findBySql(sql);
		for(BsProducts bs_:list){
			String[] id_str_arr=id_str.split(",");
			int id_index=0;
			for(int i=0;i<id_str_arr.length;i++){
				if(id_str_arr[i]!=null&&id_str_arr[i].equals(bs_.getProductId()+"")){
					id_index=i;
				}
			}
			String[] buyCount1=count_str.split(",");
			int buyCount=Integer.valueOf(buyCount1[id_index]);
			bs_.setBuyCount(buyCount);
			totalPrice+=bs_.getPrice()*buyCount;
		}
		/*listTemp 是一个临时的list集合,目的在于复制查询出来的结果集【List<BsProducts> list】*/
		List<BsProducts> listTemp = new ArrayList<BsProducts>();
		listTemp.addAll(list);
		for(BsProducts bs:list){
			/*
			 * 商品本身有折扣-------不享受销售策略的优惠   暂时移除
			 * 商品本身的优惠或者折扣，优先级最高
			 * BsProducts-->price     :原价
			 * BsProducts-->downPrice :折扣价
			 * BsProducts-->vipPrice  :优惠价
			 * BsProducts-->bestPrice :没有优惠/折扣时候的最优价【销售策略计算出来的】
			 */
			if((bs.getDownPrice()!=null && bs.getDownPrice()>0d) || (bs.getVipPrice()!=null && bs.getVipPrice()>0d)){
				listTemp.remove(bs);
			}
		}
		double total = 0d;
		//double avg = 0d;
		for(BsProducts t:listTemp){
			/*total = 购物车or订单中无折扣或优惠的商品原价总和*/
			total += t.getPrice()*t.getBuyCount();
		}
		/*查询符合条件的销售策略,并选出对客户最优方案*/
		if(total>0d){
			
			String settingSql1 = "";
			settingSql1 +="select case when sale_type=1 then (?-price_setting) when sale_type=0 then (?*price_setting/10) end as v_price,sale_name,descrption ";
			settingSql1 +="from bs_sell_setting where start_time<=now() and end_time>=now() "; 
			settingSql1 +="and price_level <= ? having v_price is not null order by v_price asc limit 0,1";
			BsSellSetting bsSellSetting=bsSellSettingDao.findBestPriceSetting(settingSql1, total,total,total);
			if(bsSellSetting!=null){
				bestSaleName=bsSellSetting.getSaleName();
				bestDescrprion=bsSellSetting.getDescrption();
				bestPrice=bsSellSetting.getBestPrice();
			}
			/*String settingSql = "";
			settingSql +="select case when sale_type=1 then (?-price_setting) when sale_type=0 then (?*price_setting/10) end as v_price ";
			settingSql +="from bs_sell_setting where start_time<=now() and end_time>=now() "; 
			settingSql +="and price_level <= ? having v_price is not null order by v_price asc limit 0,1";*/
			/* bestPrice 含义是购物车/订单中 所有【无优惠价和折扣价】的商品通过筛选的销售策略节约的最大钱数*/
			/*bestPrice = bsSellSettingDao.findBestPrice(settingSql, total,total,total);*/
			/*avg 含义-->最大节约钱数/满足销售策略的商品个数 = 每个满足销售策略的商品的节约钱数*/
			//avg = (total-bestPrice)/listTemp.size();
			/*
			 * 重新组合对象【BsProducts】
			 * listTemp中给bestPrice赋值,然后再list中删除'旧'的对象
			 * 删除完成之后list中再加入'新'对象
			 */
			/*for(BsProducts t:listTemp){
				t.setBestPrice(t.getPrice()-avg);
				list.remove(t);
			}
			list.addAll(listTemp);*/
		}else{
			bestPrice=0d;
		}
		map.put("bestPrice", bestPrice);//促销商品的优惠价格
		map.put("list", list);//商品集合
		map.put("totalPrice", totalPrice);//所有商品总价
		map.put("total", total);//参加促销商品总价
		map.put("bestSaleName", bestSaleName);
		map.put("bestDescrprion", bestDescrprion);
		return map;
	}
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.BsProductsService#loadThisListByIds(java.lang.String)
	 */
	public List<BsProducts> loadProductsByIds(String ids){
		String id_str = "";
		String[] id_count_arr=ids.split(",");
		String count_str = "";
		for(int s=id_count_arr.length-1;s>=0;s--){
			String id_count_str=id_count_arr[s];
			String[] id_count_=id_count_str.split("_");
			id_str+=id_count_[0]+",";
			count_str+=id_count_[1]+",";
		}
		id_str=id_str.substring(0,id_str.length()-1);
		/*根据购物车中的商品id查询商品集合*/
		String sql = "select * from bs_products where product_id in ("+id_str+") and status=1 ";
		List<BsProducts> list = bsProductsDao.findBySql(sql);
		return list;
	}
	
	public void cancelDiscount(){
		//根据商品优惠设置的起止时间，开始或结束优惠
		Date d=new Date();
		String date=new SimpleDateFormat("yyyy-MM-dd").format(d);
		String vipPrice = "update bs_products set vip_price = temp_price,down_price = null where date(start_time) = ? and  date(end_time) >= ? and switchs = 0";
		bsProductsDao.executeSQL(vipPrice,date,date);
		String downPrice = "update bs_products set vip_price = null,down_price = temp_price where date(start_time) = ? and  date(end_time) >= ? and switchs = 1";
		bsProductsDao.executeSQL(downPrice,date,date);
		String cancelDiscount = "update bs_products set vip_price = null,down_price = null,start_time = null,end_time = null,switchs = null,temp_price=null where date(end_time) < ?";
		bsProductsDao.executeSQL(cancelDiscount,date);
	}
	
	//查询单个商品
	public BsProducts findProductsById(Integer pId) {
		String HQL="from BsProducts where productId=?";
		BsProducts bsProducts= bsProductsDao.findUniqueByHql(HQL, pId);
		return bsProducts;
	}

	public void setBsSellSettingDao(BsSellSettingDao bsSellSettingDao) {
		this.bsSellSettingDao = bsSellSettingDao;
	}

	public void setThirdVelocityEmailUtil(
			ThirdVelocityEmailUtil thirdVelocityEmailUtil) {
		this.thirdVelocityEmailUtil = thirdVelocityEmailUtil;
	}

	public void setBsAdminUserDao(BsAdminUserDao bsAdminUserDao) {
		this.bsAdminUserDao = bsAdminUserDao;
	}
	
}
