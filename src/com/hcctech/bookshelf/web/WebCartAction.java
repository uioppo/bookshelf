package com.hcctech.bookshelf.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.services.BsProductsService;
import com.opensymphony.xwork2.ActionSupport;

public class WebCartAction extends ActionSupport{

	private static final long serialVersionUID = 1280247552077187423L;
	
	private List<BsProducts> cartProductList;
	
	private BsProductsService bsProductsService;
	
	private Double bestPrice;
	
	private Double total;
	
	private Double totalPrice;
	
	private String bestSaleName;
	
	private String bestDescrprion;
	
	/**
	 * 根据购物车中的商品id查询商品，以及商品的最优价格
	 * @return  cartProductList  [json]
	 */
	@SuppressWarnings("unchecked")
	public String findCartProducts(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Cookie[] cookie=request.getCookies();
		String ids=null;
		if(cookie!=null&&cookie.length>0){
			for(int i=0;i<cookie.length;i++){
				Cookie c=cookie[i];
				if(c!=null&&c.getName()!=null){
					if("the_bs_product".equals(c.getName())){
						ids=c.getValue();
					}
				}
			}
		}
		if(ids ==null||"".equals(ids.trim())){
			return SUCCESS;
		}
		try {
			ids=URLDecoder.decode(ids, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return SUCCESS;
		}
		Map<String,Object> map = bsProductsService.loadBestPrice_ProductsByIds(ids);
		if(map!=null){
			String[] id_count_arr=ids.split(",");
			cartProductList=(List<BsProducts>) map.get("list");
			bestPrice=(Double) map.get("bestPrice");
			total=(Double) map.get("total");
			totalPrice=(Double) map.get("totalPrice");
			bestSaleName=(String)map.get("bestSaleName");
			bestDescrprion=(String)map.get("bestDescrprion");
			if(cartProductList!=null&&cartProductList.size()>0){
			for(BsProducts b:cartProductList){
				b.setBsLicenseKeies(null);
				b.setBsMybooks(null);
				b.setBsOrderProducts(null);
			}
			if(cartProductList.size()!=id_count_arr.length){
				StringBuilder strb=new StringBuilder();
				//重写cookies
				StringBuilder id_str_b=new StringBuilder();
				String id_str = "";//cookies中id的字符串
				for(int s=0;s<id_count_arr.length;s++){
					String id_count_str=id_count_arr[s];
					String[] id_count_=id_count_str.split("_");
					id_str_b.append(id_count_[0]);
					id_str_b.append(",");
				}
				id_str=id_str_b.toString();
				String[] id_str_arr=id_str.split(",");
				for(int j=0;j<id_str_arr.length;j++){
					for(BsProducts b:cartProductList){
						if(Integer.valueOf(id_str_arr[j]).equals(b.getProductId())){
							strb.append(b.getProductId()+"_"+b.getBuyCount()+",");
						}
					}
				}
				try {
					String newCookieStr=strb.substring(0, strb.length()-1);
					newCookieStr=URLEncoder.encode(newCookieStr, "utf-8");
					HttpServletResponse response = ServletActionContext.getResponse();
					if(cookie!=null){
					       for(int i=0;i<cookie.length;i++){
					    	   Cookie c=cookie[i];
								if(c!=null&&c.getName()!=null){
									if("the_bs_product".equals(c.getName())){
										cookie[i].setMaxAge(-1);
										cookie[i].setPath("/");
										Cookie cookie1=new Cookie(cookie[i].getName(),newCookieStr);
										cookie1.setPath("/");
										cookie1.setMaxAge(c.getMaxAge());
										response.addCookie(cookie1);
									}
								}
					        }   
					   }
				} catch (UnsupportedEncodingException e) {
					return SUCCESS;
				}
			}
		}else{
			if(cookie!=null){  
				HttpServletResponse response = ServletActionContext.getResponse();
			       for(int i=0;i<cookie.length;i++){
			    	   Cookie c=cookie[i];
						if(c!=null&&c.getName()!=null){
							if("the_bs_product".equals(c.getName())){
								cookie[i].setMaxAge(-1);
								cookie[i].setPath("/");
								Cookie cookie1=new Cookie(cookie[i].getName(),null);
								cookie1.setValue("0");
								cookie1.setPath("/");
								cookie1.setMaxAge(0);
								response.addCookie(cookie1);
							}
						}
			        }   
			   }
			}
		}
		return SUCCESS;
	}
	/**
	 * 查询浮动购物车中商品信息
	 * @return
	 */
	public String findFloatCartProducts(){
		//从cookies中获取商品id
		HttpServletRequest request = ServletActionContext.getRequest();
		Cookie[] cookie=request.getCookies();
		String ids=null;
		if(cookie!=null&&cookie.length>0){
			for(int i=0;i<cookie.length;i++){
				Cookie c=cookie[i];
				if(c!=null&&c.getName()!=null){
					if("the_bs_product".equals(c.getName())){
						ids=c.getValue();
					}
				}
			}
		}
		if(ids ==null||"".equals(ids.trim())){
			return SUCCESS;
		}
		try {
			ids=URLDecoder.decode(ids, "utf-8");
		} catch (UnsupportedEncodingException e) {
			return SUCCESS;
		}
		List<BsProducts> cartProductList_ = bsProductsService.loadProductsByIds(ids);
		if(cartProductList_!=null&&cartProductList_.size()>0){
		String[] id_count_arr=ids.split(",");
		StringBuilder id_str_b=new StringBuilder();
		String id_str = "";//cookies中id的字符串
		for(int s=id_count_arr.length-1;s>=0;s--){
			String id_count_str=id_count_arr[s];
			String[] id_count_=id_count_str.split("_");
			id_str_b.append(id_count_[0]);
			id_str_b.append(",");
		}
		id_str=id_str_b.toString();
		String[] id_str_arr=id_str.split(",");
		cartProductList=new ArrayList<BsProducts>();
		String removeIdStr="";
		boolean flag=true;
		int tag=0;
		for(int j=0;j<id_str_arr.length;j++){
			for(BsProducts b:cartProductList_){
				if(Integer.valueOf(id_str_arr[j]).equals(b.getProductId())){
					if(tag<3){
						tag+=1;
						b.setBsLicenseKeies(null);
						b.setBsMybooks(null);
						b.setBsOrderProducts(null);
						cartProductList.add(b);
					}
					flag=false;
				}
			}
			if(flag){
				removeIdStr+=id_str_arr[j]+",";
			}
			flag=true;
		}
		if(id_count_arr.length!=cartProductList_.size()&&removeIdStr!=""){
			//cookie 中的数据有误    重写cookies
			StringBuilder id_str_b1=new StringBuilder();
			StringBuilder count_str_b1=new StringBuilder();
			String id_str1 = "";//cookies中id的字符串
			String count_str1="";//cookies中count的字符串
			for(int s=0;s<id_count_arr.length;s++){
				String id_count_str=id_count_arr[s];
				String[] id_count_=id_count_str.split("_");
				id_str_b1.append(id_count_[0]);
				id_str_b1.append(",");
				count_str_b1.append(id_count_[1]);
				count_str_b1.append(",");
			}
			id_str1=id_str_b1.toString();
			count_str1=count_str_b1.toString();
			String newCookieStr="";
			removeIdStr=removeIdStr.substring(0, removeIdStr.length()-1);
			String[] id_arr_all=id_str1.split(",");
			String[] count_arr_all=count_str1.split(",");
			String[] removeIdStr_arr=removeIdStr.split(",");
			boolean flag1=true;
			StringBuilder newCookieSb=new StringBuilder();
			for(int x=0;x<id_arr_all.length;x++){
				for(int y=0;y<removeIdStr_arr.length;y++){
					if(id_arr_all[x].equals(removeIdStr_arr[y])){
						flag1=false;
					}
				}
				if(flag1){
					newCookieSb.append(id_arr_all[x]+"_"+count_arr_all[x]+",");
				}
				flag1=true;
			}
			newCookieStr=newCookieSb.substring(0,newCookieSb.length()-1);
			try {
				newCookieStr=URLEncoder.encode(newCookieStr, "utf-8");
			} catch (UnsupportedEncodingException e) {
				return SUCCESS;
			}
			HttpServletResponse response = ServletActionContext.getResponse();
			if(cookie!=null){
			       for(int i=0;i<cookie.length;i++){
			    	   Cookie c=cookie[i];
						if(c!=null&&c.getName()!=null){
							if("the_bs_product".equals(c.getName())){
								cookie[i].setMaxAge(-1);
								cookie[i].setPath("/");
								Cookie cookie1=new Cookie(cookie[i].getName(),newCookieStr);
								cookie1.setPath("/");
								cookie1.setMaxAge(c.getMaxAge());
								response.addCookie(cookie1);
							}
						}
			        }   
			   }
		}
		}else{
			if(cookie!=null){  
				HttpServletResponse response = ServletActionContext.getResponse();
			       for(int i=0;i<cookie.length;i++){
			    	   Cookie c=cookie[i];
						if(c!=null&&c.getName()!=null){
							if("the_bs_product".equals(c.getName())){
								cookie[i].setMaxAge(-1);
								cookie[i].setPath("/");
								Cookie cookie1=new Cookie(cookie[i].getName(),null);
								cookie1.setValue("0");
								cookie1.setPath("/");
								cookie1.setMaxAge(0);
								response.addCookie(cookie1);
							}
						}
			        }   
			   }
		}
		return SUCCESS;
	}
	public void setBsProductsService(BsProductsService bsProductsService) {
		this.bsProductsService = bsProductsService;
	}

	public List<BsProducts> getCartProductList() {
		return cartProductList;
	}
	public Double getBestPrice() {
		return bestPrice;
	}
	public Double getTotal() {
		return total;
	}
	public Double getTotalPrice() {
		return totalPrice;
	}
	public String getBestSaleName() {
		return bestSaleName;
	}
	public String getBestDescrprion() {
		return bestDescrprion;
	}
	
}
