package com.hcctech.bookshelf.services.impl;

import java.util.List;

import com.hcctech.bookshelf.dao.BsProductsDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.services.ProductService;

/**
 * @author Randyjie
 * 2012年10月30日 18:37:09
 */
public class ProductServiceImpl implements ProductService {

	BsProductsDao bsProductsDao;
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.ProductService#loadEbooksByOrg(java.lang.Integer, int, java.lang.String, java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public Page<BsProducts> loadEbooksByOrg(Integer page, int pageSize,
			String xd, String xk, String type,String order, String sort) {
//		try {
//			xk = URLDecoder.decode(xk, "UTF-8");
//			//xk = new String(xk.getBytes(),"ISO8859-1");
//		} catch (UnsupportedEncodingException e1) {
//		}
		if(order!=null) order = order.trim();
		else order="";
		
		if(sort!=null) sort = sort.trim();
		else sort="";
		
		Integer _type = 0;
		
		try {
			if(type!=null&&!type.trim().equals("0")) _type = Integer.valueOf(type);
		} catch (NumberFormatException e) {
			_type = 1;
		}
		//if(_type>1)
		//
		StringBuffer hql = new StringBuffer("FROM BsProducts AS b where b.status = 1 and  b.productType=? ");
		if(xd!=null&&!xd.trim().equals("")){
			try {
				int _xd = Integer.valueOf(xd.trim());
				if(_xd >0 && _xd<=3){
					hql.append(" and b.schoolStage = "+_xd);
				}else{
					hql.append(" and b.schoolStage = 1 ");
				}
			} catch (NumberFormatException e) {
				hql.append(" and b.schoolStage = 1 ");
			}
			if(xk!=null&&!xk.trim().equals("")){
				hql.append(" and b.subject = '"+xk.trim().replaceAll("[\\pP|~|$|^|<|>|\\||\\+|=]*", "")+"'");
			}
		}
		if(order.equals("price")){
			hql.append(" order by b.price");
		}else{
			hql.append(" order by b.publishTime");
		}
		if(sort.equals("0")){
			hql.append(" desc");
		}
		if(page==null)
			page = 0;
		page+=1;
		return bsProductsDao.pagedQuery(hql.toString(), page, pageSize,_type);
	}

	

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.ProductService#loadEbooksByOrg(java.lang.Integer, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public Page<BsProducts> loadEbooksByOrg(Integer page, int pageSize,
			String xd, String xk, String type, String order, String sort,
			String keyword) {
		
//		try {
//			xk = URLDecoder.decode(xk, "UTF-8");
//			//xk = new String(xk.getBytes(),"ISO8859-1");
//		} catch (UnsupportedEncodingException e1) {
//		}
		if(order!=null) order = order.trim();
		else order="";
		
		if(sort!=null) sort = sort.trim();
		else sort="";
		
		
		StringBuffer hql = new StringBuffer("FROM BsProducts AS b where b.status = 1 ");
		if(type!=null&&(type.trim().equals("0")||type.trim().equals("1"))){
			hql.append(" and b.productType="+type);
		}
		if(xd!=null&&!xd.trim().equals("")){
			try {
				int _xd = Integer.valueOf(xd.trim());
				if(_xd >0 && _xd<=3){
					hql.append(" and b.schoolStage = "+_xd);
				}else{
					hql.append(" and b.schoolStage = 1 ");
				}
			} catch (NumberFormatException e) {
				hql.append(" and b.schoolStage = 1 ");
			}
			if(xk!=null&&!xk.trim().equals("")){
				hql.append(" and b.subject = '"+xk.trim().replaceAll("[\\pP|~|$|^|<|>|\\||\\+|=]*", "")+"'");
			}
		}
		if(keyword!=null){
			keyword = keyword.trim().replaceAll("[\\pP|~|$|^|<|>|\\||\\+|=]*", "");
			if(!keyword.equals("")){
				String[] key = keyword.split(" ");
				for(int i = 0 ; i < key.length ; i++){
					if(!"".equals(key[i])) {
						hql.append(" and b.productName like '%"+key[i]+"%'");
					}
				}
//				keyword = keyword.replaceAll(" ", "%");
//				hql.append(" and b.productName like '%"+keyword+"%'");
			}
		}
		
		if(order.equals("price")){
			hql.append(" order by b.price");
		}else{
			hql.append(" order by b.publishTime");
		}
		if(sort.equals("0")){
			hql.append(" desc");
		}
		if(page==null)
			page = 0;
		page+=1;
		
		return bsProductsDao.pagedQuery(hql.toString(), page, pageSize);
		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.ProductService#recommendNewBook(int)
	 */
	public List<BsProducts> recommendNewBook(int type , int start ,int end){
		String hql = "select new BsProducts(productId,productName,thumbnail,price,vipPrice,downPrice) from BsProducts as products where status = 1 and productType = ? ORDER BY optionTime DESC limit 1,3";
		return bsProductsDao.findBookByHql(hql,type,start,end);
	}
	
	public void setBsProductsDao(BsProductsDao bsProductsDao) {
		this.bsProductsDao = bsProductsDao;
	}
}
