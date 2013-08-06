package com.hcctech.bookshelf.actions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsProducts;
import com.hcctech.bookshelf.services.BsProductsService;
import com.hcctech.bookshelf.util.DomainUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author konglingxia
 * @version 1.0
 * 商品添加 优惠设置 修改 上下架 列表 查看
 */
public class BsProductsAction extends ActionSupport {

	private static final long serialVersionUID = 2629019560992899781L;
	private BsProductsService bsProductsService ;
	private List<BsProducts> rows ;		//结果集
	private long total ;				//总行数
	private String name ;				//查询类别
	private String value ;				//关键字
	private int pageSize = 10;			//每页行数
	private int page = 1;				//当前页
	private int state ;
	
	private Integer productId ;			//更新的商品ID
	private Integer oldSatus ;			//状态
	private Integer newSatus ;			//更新后状态
	private boolean flag ;
	
	private BsProducts bsProducts ;		//添加/修改商品
	private String msg ;
	private String change ;
	
	private BsProducts loadBsProducts ;
	private Integer pId ;
	private String fileDomain ;			//ftp地址
	
	private String backspace ;
	
	private Integer proId ;
	private String proIds ;
	private String columnName ;
	private Double specialPrice ;
	private Double accreditPrice ;		//授权价
	private Date startTime ;
	private Date endTime ;
	
	private String productType ;
	private String schoolStage ;
	private String subject ;
	private String searchName ;
	
	/**
	 * 商品优惠设置
	 */
	public String updatePrice() {
		BsAdminUser bsAdminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");		
		if(bsAdminUser == null){
			flag = false;
			return SUCCESS;
		}
		flag = bsProductsService.updatePrice(proId,columnName,specialPrice,accreditPrice,startTime,endTime);
		return SUCCESS;
	}
	
	/**
	 * 商品优惠批量设置
	 */
	public String updatePriceBatch() {
		BsAdminUser bsAdminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");		
		if(bsAdminUser == null){
			flag = false;
			return SUCCESS;
		}
		flag = bsProductsService.updateBatchPrice(proIds,specialPrice,startTime,endTime);
		return SUCCESS;
	}
	
	/**
	 * 修改商品
	 */
	public String updateProduct() {
		backspace = "productList.jsp" ;
		BsAdminUser bsAdminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");
		if(bsAdminUser == null){
			return "updateProduct";
		} else {
			bsProducts.setOptionAuthor(bsAdminUser.getRealName());
			bsProducts.setOptionAuthorid(bsAdminUser.getAuId());
		}
		if(bsProducts.getStatus() == 2){
			backspace = "productBackList.jsp" ;
		}
		if(bsProductsService.updateProduct(bsProducts,change)){
			msg = "操作成功。点击“返回”,返回列表" ;
		}else {
			msg = "异常，请稍候再试。点击“返回”,返回列表" ;
		}
		return "updateProduct";
	}
	
	/**
	 * 加载上架商品
	 */
	public String loadProduct() {
		try {
			loadBsProducts = bsProductsService.loadBsProduct(pId);
			fileDomain = DomainUtil.getFileDomain();
		}catch(Exception e) {
		}
		
		return "loadProduct" ;
	}
	/**
	 * 查看商品
	 */
	public String loadDownProduct() {
		try {
			loadBsProducts = bsProductsService.loadBsProduct(pId);
			fileDomain = DomainUtil.getFileDomain();
		}catch(Exception e) {
		}
		
		return "loadDownProduct" ;
	}
	/**
	 * 添加商品
	 */
	public String addProduct() {
		backspace = "addProduct.html" ;
		BsAdminUser bsAdminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");
		if(bsAdminUser == null){
			return "addProduct";
		} else {
			bsProducts.setOptionAuthor(bsAdminUser.getRealName());
			bsProducts.setOptionAuthorid(bsAdminUser.getAuId());
		}
		bsProducts.setStatus(3);
		bsProducts.setUpdateTime(new Date());
		bsProducts.setOptionTime(new Timestamp(new Date().getTime()));
		if(bsProductsService.addProduct(bsProducts)) {
			msg = "操作成功。点击“返回”,返回添加商品" ;
		}else {
			msg = "异常，请稍候再试。点击“返回”,返回添加商品" ;
		}
		return "addProduct";
	}

	/**
	 * 商品列表
	 */
	public String loadProductsList() {
		Page<BsProducts> pages = bsProductsService.loadProductList(name,value,pageSize,page,state);
		total = 0 ;
		if(pages != null) {
			rows = pages.getList();
			total = pages.getTotalCount();
		}
		return SUCCESS;
	}
	
	/**
	 * 商品促销设置列表
	 */
	
	public String loadDiscountList() {
		Page<BsProducts> pages = bsProductsService.loadDiscountList(productType,schoolStage,subject, searchName,pageSize,page,1);
		total = 0 ;
		if(pages != null) {
			rows = pages.getList();
			total = pages.getTotalCount();
		}
		return SUCCESS;
	}
	/**
	 * 商品的更改状态
	 */
	public String updateProductStatus() {
		BsAdminUser bsAdminUser = (BsAdminUser)ActionContext.getContext().getSession().get("adminuser");
		if(bsAdminUser == null){
			flag = false;
			return SUCCESS;
		} 
		flag = bsProductsService.updateProductStatus(productId, oldSatus,newSatus,bsAdminUser);		
		return SUCCESS;
	}
	public void setBsProductsService(BsProductsService bsProductsService) {
		this.bsProductsService = bsProductsService;
	}

	public List<BsProducts> getRows() {
		return rows;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setProductId(Integer productId) {
		this.productId = productId;
	}

	public void setBsProducts(BsProducts bsProducts) {
		this.bsProducts = bsProducts;
	}

	public boolean getFlag() {
		return flag;
	}
	public BsProducts getBsProducts() {
		return bsProducts;
	}

	public String getMsg() {
		return msg;
	}

	public BsProducts getLoadBsProducts() {
		return loadBsProducts;
	}

	public void setPId(Integer pId) {
		this.pId = pId;
	}

	public void setLoadBsProducts(BsProducts loadBsProducts) {
		this.loadBsProducts = loadBsProducts;
	}

	public String getFileDomain() {
		return fileDomain;
	}

	public String getBackspace() {
		return backspace;
	}

	public void setState(int state) {
		this.state = state;
	}

	public void setChange(String change) {
		this.change = change;
	}

	public void setProId(Integer proId) {
		this.proId = proId;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setSpecialPrice(Double specialPrice) {
		this.specialPrice = specialPrice;
	}

	public void setAccreditPrice(Double accreditPrice) {
		this.accreditPrice = accreditPrice;
	}

	public void setOldSatus(Integer oldSatus) {
		this.oldSatus = oldSatus;
	}

	public void setNewSatus(Integer newSatus) {
		this.newSatus = newSatus;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public void setSchoolStage(String schoolStage) {
		this.schoolStage = schoolStage;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public void setProIds(String proIds) {
		this.proIds = proIds;
	}
	
}
