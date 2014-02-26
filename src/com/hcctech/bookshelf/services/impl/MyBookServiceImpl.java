package com.hcctech.bookshelf.services.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.hcctech.bookshelf.dao.BsEbookDao;
import com.hcctech.bookshelf.dao.BsLicenseKeyDao;
import com.hcctech.bookshelf.dao.BsMyBookDao;
import com.hcctech.bookshelf.dao.BsProductsDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsEbook;
import com.hcctech.bookshelf.pojo.BsLicenseKey;
import com.hcctech.bookshelf.pojo.BsMybook;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.MyBookService;
import com.opensymphony.xwork2.ActionContext;

/**
 * @author apple
 * 前台用户使用授权码
 */
public class MyBookServiceImpl implements MyBookService{
	
	private BsMyBookDao bsMyBookDao;
	private BsLicenseKeyDao bsLicenseKeyDao;
	private BsEbookDao ebookDao;
	private BsProductsDao bsProductsDao;
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.MyBookService#activeBookByKey(java.lang.String, int)
	 */
	public String activeBookByKey(String licenseKey, int productId){
		String msg="";
		String ret_flag = "1";
		boolean flag=false;
		BsWebUser user=(BsWebUser)ActionContext.getContext().getSession().get("user"); 
		if(user==null||user.getWuId()==null){
			msg="操作失败  请重新登录再试";
			ret_flag = "-1";
			return ret_flag;
		}
		System.out.println(licenseKey);
		Integer lkid = null ;
		if(licenseKey.length() == 16){
			lkid = keyId(licenseKey);
			String s="-";
			licenseKey = licenseKey.substring(0,4)+s+licenseKey.substring(4,8)+s+licenseKey.substring(8,12)+s+licenseKey.substring(12,16);
		}else if(licenseKey.length() == 19){
			String[] keyArr=licenseKey.split("-");
			StringBuffer sb = new StringBuffer();
			for(String str : keyArr){
				sb.append(str);
			}
			lkid = keyId(sb.toString());
		}else {
			msg="无效授权码！";
			ret_flag = "404";
			return ret_flag;
		}
		
		Timestamp t=new Timestamp(new Date().getTime());
		final String hql1="from BsLicenseKey b where b.lkid=? and b.lifeTime>=?";
		BsLicenseKey bsLicenseKey=bsLicenseKeyDao.findUniqueByHql(hql1, lkid,new Date());
		if(bsLicenseKey!=null){
			if(bsLicenseKey.getUseStatus()!=0){
				msg="授权码被使用！";
				ret_flag = "502";
				return ret_flag;
			}
			if(!bsLicenseKey.getKeyId().equals(licenseKey)){
				msg="无效授权码！";
				ret_flag = "404";
				return ret_flag;
			}
		}else{
			msg="无效授权码！";
			ret_flag = "404";
			return ret_flag;
		}
		@SuppressWarnings("unchecked")
		Set<BsProducts> set = bsLicenseKey.getBsProductses();
		if(set==null||set.size()<=0){
			msg="添加失败！";
			ret_flag = "500";
			return ret_flag;
		}
		boolean flag_=true;
		for(BsProducts bsProducts:set){
			if(bsProducts.getProductId()==productId){
				flag_=false;
			}
		}
		if(flag_){
			msg="该授权码不与本书对应，操作失败！";
			ret_flag = "500";
			return ret_flag;
		}
		final String hql2="from BsMybook b where b.bsWebUser.wuId=?";
		List<BsMybook> myBookList=bsMyBookDao.findByHql(hql2,user.getWuId());
		Calendar now = Calendar.getInstance();  
		BsWebUser bsWebUser = new BsWebUser();
		bsWebUser.setWuId(user.getWuId());
//		int key_count=getKeyCount();
		for(BsProducts bsProducts:set){
			if(myBookList!=null&&myBookList.size()>0){
				for(BsMybook bsMybook:myBookList){
					if(bsProducts.getProductId()==bsMybook.getBsProducts().getProductId()){
						if(bsMybook.getDeadline()!=null){
							if(bsMybook.getDeadline().getTime()>=t.getTime()){
								if(bsProducts.getUsePeriod()==1200){
									bsMybook.setDeadline(null);
								}else{
									now.setTime(bsMybook.getDeadline());  
								    now.set(Calendar.MONTH, now.get(Calendar.MONTH)+bsProducts.getUsePeriod());  
									bsMybook.setDeadline(new Timestamp(now.getTimeInMillis()));
								}
							}else{
								if(bsProducts.getUsePeriod()==1200){
									bsMybook.setDeadline(null);
								}else{
									now.setTime(t);  
								    now.set(Calendar.MONTH, now.get(Calendar.MONTH)+bsProducts.getUsePeriod());  
									bsMybook.setDeadline(new Timestamp(now.getTimeInMillis()));
								}
							}
						}
						bsMybook.setDownNumbers(bsMybook.getDownNumbers()+((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?3:bsProducts.getDownNumber()));
						bsMybook.setTotalNumbers(bsMybook.getTotalNumbers()+((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?3:bsProducts.getDownNumber()));
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
				bsMybook1.setBsWebUser(bsWebUser);
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
				bsMybook1.setDownNumbers((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?3:bsProducts.getDownNumber());
				bsMybook1.setTotalNumbers((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?3:bsProducts.getDownNumber());
				bsMyBookDao.save(bsMybook1);
			}
		}
		bsLicenseKey.setBsWebUser(bsWebUser);
		bsLicenseKey.setUseStatus(1);
		bsLicenseKeyDao.update(bsLicenseKey);
		return ret_flag;
	}
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.MyBookService#myBookDetail(int)
	 */
	public BsMybook myBookDetail(int myBookId){
		final String hql="from BsMybook b where b.mybookId = ?";
		BsMybook bsMybook=bsMyBookDao.findUniqueByHql(hql, myBookId);
		if(bsMybook!=null&&bsMybook.getBsProducts()!=null){
			bsMybook.getBsProducts().getProductName();
		}
		return bsMybook;
	}
	
	@SuppressWarnings("unchecked")
	public String addMyBook(int productId, BsWebUser user) {
		BsProducts product =bsProductsDao.get(productId);
		if(product==null) return "-3";
		Timestamp t=new Timestamp(new Date().getTime());
		BsMybook bsMybook1=new BsMybook();
		bsMybook1.setAddTime(t);
		bsMybook1.setBsProducts(product);
		bsMybook1.setBsWebUser(user);
		Calendar now = Calendar.getInstance();
		now.setTime(t);  
		if(product.getUsePeriod()!=null){
			if(product.getUsePeriod()==1200){
				bsMybook1.setDeadline(null);
			}else{
				now.set(Calendar.MONTH, now.get(Calendar.MONTH)+product.getUsePeriod());  
				bsMybook1.setDeadline(new Timestamp(now.getTimeInMillis()));
			}
		}else{
			now.set(Calendar.MONTH, now.get(Calendar.MONTH)+0);  
			bsMybook1.setDeadline(new Timestamp(now.getTimeInMillis()));
		}
		bsMybook1.setDownNumbers(3);
		bsMybook1.setTotalNumbers(3);
		bsMyBookDao.save(bsMybook1);
		return "1";
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.MyBookService#addMyBookByKey(java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public String addMyBookByKey(String licenseKey,BsWebUser user){
		String msg="";
		String ret_flag="1";
		boolean flag=false;
		if(user==null) {
			user=(BsWebUser)ActionContext.getContext().getSession().get("user"); 
		}
			
		if(user==null||user.getWuId()==null){
			msg="操作失败  请重新登录再试";
			ret_flag = "-1";
			return ret_flag;
		}
		if(StringUtils.isNotBlank(licenseKey)) {
			licenseKey = licenseKey.toUpperCase();
		}
		Integer lkid = null ;
		if(licenseKey.length() == 16){
			lkid = keyId(licenseKey);
			String s="-";
			licenseKey = licenseKey.substring(0,4)+s+licenseKey.substring(4,8)+s+licenseKey.substring(8,12)+s+licenseKey.substring(12,16);
		}else if(licenseKey.length() == 19){
			String[] keyArr=licenseKey.split("-");
			StringBuffer sb = new StringBuffer();
			for(String str : keyArr){
				sb.append(str);
			}
			lkid = keyId(sb.toString());
		}else {
			msg="无效授权码！";
			ret_flag = "404";
			return ret_flag;
		}
		
		
		
		Timestamp t=new Timestamp(new Date().getTime());
		final String hql1="from BsLicenseKey b where b.lkid=? and b.lifeTime>=?";
		BsLicenseKey bsLicenseKey=bsLicenseKeyDao.findUniqueByHql(hql1, lkid,new Date());
		if(bsLicenseKey!=null){
			if(bsLicenseKey.getUseStatus()!=0){
				msg="授权码被使用！";
				ret_flag = "502";
				return ret_flag;
			}
			if(!bsLicenseKey.getKeyId().equalsIgnoreCase(licenseKey)){
				msg="无效授权码！";
				ret_flag = "404";
				return ret_flag;
			}
		}else{
			msg="无效授权码！";
			ret_flag = "404";
			return ret_flag;
		}
		Set<BsProducts> set = bsLicenseKey.getBsProductses();
		if(set==null||set.size()<=0){
			msg="添加失败！";
			ret_flag = "500";
			return ret_flag;
		}
		final String hql2="from BsMybook b where b.bsWebUser.wuId=?";
		List<BsMybook> myBookList=bsMyBookDao.findByHql(hql2,user.getWuId());
		Calendar now = Calendar.getInstance();  
		BsWebUser bsWebUser = new BsWebUser();
		bsWebUser.setWuId(user.getWuId());
//		int key_count=getKeyCount();
		for(BsProducts bsProducts:set){
			if(myBookList!=null&&myBookList.size()>0){
				for(BsMybook bsMybook:myBookList){
					if(bsProducts.getProductId()==bsMybook.getBsProducts().getProductId()){
						if(bsMybook.getDeadline()!=null){
							if(bsMybook.getDeadline().getTime()>=t.getTime()){
								if(bsProducts.getUsePeriod()==1200){
									bsMybook.setDeadline(null);
								}else{
									now.setTime(bsMybook.getDeadline());  
								    now.set(Calendar.MONTH, now.get(Calendar.MONTH)+bsProducts.getUsePeriod());  
									bsMybook.setDeadline(new Timestamp(now.getTimeInMillis()));
								}
							}else{
								if(bsProducts.getUsePeriod()==1200){
									bsMybook.setDeadline(null);
								}else{
									now.setTime(t);  
								    now.set(Calendar.MONTH, now.get(Calendar.MONTH)+bsProducts.getUsePeriod());  
									bsMybook.setDeadline(new Timestamp(now.getTimeInMillis()));
								}
								
							}
						}
						bsMybook.setDownNumbers(bsMybook.getDownNumbers()+((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?3:bsProducts.getDownNumber()));
						bsMybook.setTotalNumbers(bsMybook.getTotalNumbers()+((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?3:bsProducts.getDownNumber()));
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
				bsMybook1.setBsWebUser(bsWebUser);
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
				bsMybook1.setDownNumbers(((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?3:bsProducts.getDownNumber()));
				bsMybook1.setTotalNumbers(((bsProducts.getDownNumber()==null||bsProducts.getDownNumber()<=0)?3:bsProducts.getDownNumber()));
				bsMyBookDao.save(bsMybook1);
			}
		}
		bsLicenseKey.setBsWebUser(bsWebUser);
		bsLicenseKey.setUseStatus(1);
		bsLicenseKeyDao.update(bsLicenseKey);
		return ret_flag;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.MyBookService#myBookList(int, int)
	 */
	public Page<BsMybook> myBookList(int page, int pageSize,BsWebUser bsWebUser) {
		Page<BsMybook> bookPage = null;
		if(bsWebUser!=null){
			final StringBuffer hql= new StringBuffer("from BsMybook b where b.bsWebUser.wuId=? order by b.addTime desc");
			bookPage = bsMyBookDao.pagedQuery(hql.toString(), page, pageSize,bsWebUser.getWuId());
			if(bookPage!=null&&bookPage.getList()!=null&bookPage.getList().size()>0){
				for(BsMybook bsMybook:bookPage.getList()){
					BsProducts bsProducts = bsMybook.getBsProducts();
					if(bsProducts!=null){
						bsProducts.getProductName();
					}
					bsProducts.setBsLicenseKeies(null);
					bsProducts.setBsMybooks(null);
					bsProducts.setBsOrderProducts(null);
					bsMybook.setBsUserDrms(null);
					bsMybook.setBsWebUser(null);
				}
			}
		}
		return bookPage;
	}
	
	private Integer keyId(String authCode){
		Integer lkid = null;
		StringBuffer sb = new StringBuffer();
		for(int i = 0 ; i < authCode.length() ; i++){
			if(i%2==0){
				char c = authCode.charAt(i);
				if(c >= 48 && c<= 57){
					sb.append(c);
				}
			}
		}
		lkid = Integer.parseInt(sb.toString());
		return lkid ;
	}
	public void setBsMyBookDao(BsMyBookDao bsMyBookDao) {
		this.bsMyBookDao = bsMyBookDao;
	}

	public void setBsLicenseKeyDao(BsLicenseKeyDao bsLicenseKeyDao) {
		this.bsLicenseKeyDao = bsLicenseKeyDao;
	}
	public void setEbookDao(BsEbookDao ebookDao) {
		this.ebookDao = ebookDao;
	}
	public void setBsProductsDao(BsProductsDao bsProductsDao) {
		this.bsProductsDao = bsProductsDao;
	}
	
	
}
