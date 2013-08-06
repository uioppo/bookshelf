package com.hcctech.bookshelf.services.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import com.hcctech.bookshelf.dao.BsProductsDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.services.RecommendSetService;

/**
 * @author apple
 * 推荐电子书    热销电子书设置  
 * 2012-09-19
 */
public class RecommendSetServiceImpl implements RecommendSetService{
	private BsProductsDao bsProductsDao;
	
//	public String loadSelectBook(String path){
//		String htmlStr=null;
//		 File  file =   new File(path); 
//		 try {
//			BufferedReader bf = new BufferedReader(new FileReader(file));
//			  String content = "";
//			  StringBuilder sb = new StringBuilder();
//			  content = bf.readLine();
//			  while(content != null){
//			   sb.append(content.trim());
//			   content = bf.readLine();
//			  }
//			  htmlStr=sb.toString();
//			bf.close();
//		} catch (FileNotFoundException e) {
//			htmlStr="";
//		} catch (IOException e) {
//			htmlStr="";
//		}
//		return htmlStr;
//	}
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RecommendSetService#recommendSave(java.lang.String, int)
	 */
	public int recommendSave(String path ,String htmlStr){
		int flag=0;
			FileOutputStream os = null;
			try {
				File file = new File(path) ;
				if(!file.exists()){
					file.createNewFile();
				}
				os = new FileOutputStream(file, false);
				os.write(htmlStr.getBytes("utf-8"));
				flag=1;
			} catch (FileNotFoundException e) {
				flag=0;
			}catch (IOException e) {
				flag=0;
			}finally{
				try {
					if(os != null ) {
						os.close();
					}
				} catch (IOException e) {
					flag=0;
				}
			}
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RecommendSetService#loadProductAll(java.lang.String, int, int, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Page<BsProducts> loadProductAll(String productName, int pageNo, int pageSize, String sort, String order,String idStr,Integer productType) {
		final StringBuilder hql = new StringBuilder("from BsProducts b where b.status = 1 ");
		appendHqlProduct(productName, hql, sort, order,idStr,productType);
		Page<BsProducts> page =  bsProductsDao.pagedQuery(hql.toString(), pageNo, pageSize);
		//查看折扣优惠
//		if(page!=null&&page.getList()!=null&&page.getList().size()>0){
//			Timestamp t=new Timestamp(System.currentTimeMillis());
//			final String hql1="from BsSellSetting b where b.saleType=0 and b.startTime<=? and b.endTime>=?";
//			List<BsSellSetting> list=bsSellSettingDao.findByHql(hql1,t,t);
//			if(list!=null&&list.size()>0){
//				if(list.get(0)!=null&&list.get(0).getPriceSetting()!=null&&list.get(0).getPriceSetting()>0){
//					for (BsProducts bsProducts : page.getList()) {
//						bsProducts.setPriceSetting(list.get(0).getPriceSetting());
//					}
//				}
//			}
//		}
		return page;
	}
	/**
	 * 拼hql
	 * @param productName
	 * @param hql
	 * @param sort
	 * @param order
	 * @param idStr
	 */
	public void appendHqlProduct(String productName,
			final StringBuilder hql, String sort, String order,String idStr,Integer productType) {
		boolean flag=false;
		if (productName != null &&!"".equals(productName) && !productName.trim().equals("")) {
				hql.append("and b.productName like '%")
						.append(productName).append("%' ");
				flag=true;
		}
		if(productType!=null){
			hql.append("and b.productType = ").append(productType);
			flag=true;
		}
		if (idStr != null &&!"".equals(idStr) &&!idStr.trim().equals("")) {
			if(flag){
				hql.append(" and b.productId not in (")
				.append(idStr).append(") ");
			}else{
				hql.append(" and b.productId not in (")
				.append(idStr).append(") ");
			}
		}
		if (sort != null && !"".equals(sort.trim()) && order != null
				&& !"".equals(order.trim())) {
			hql.append(" order by b.").append(sort).append(" ").append(order);
		} else {
			hql.append(" order by b.productId asc");
		}
	}
	public void setBsProductsDao(BsProductsDao bsProductsDao) {
		this.bsProductsDao = bsProductsDao;
	}
	
}
