package com.hcctech.bookshelf.actions;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsSellSetting;
import com.hcctech.bookshelf.services.SellSettingService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 促销的设置
 * @author yong
 *
 */
public class SellSettingAction extends ActionSupport {
	
	private static final long serialVersionUID = -6228558888308188046L;
	
	private SellSettingService sellSettingServiceImpl;
	
	private int page = 1;
	
	private int pageSize = 10;
	
	private List<BsSellSetting> rows;
	
	private int total;
	
	private String id;
	
	private BsSellSetting sell;
	/**
	 * 列表
	 * @return
	 */
	public String list() {
		Page<BsSellSetting> pages = sellSettingServiceImpl.findAllSell(page,
				pageSize);
		rows = pages.getList();
		total = (int) pages.getTotalCount();
		return SUCCESS;
	}
	/**
	 * 删除
	 */
	public void delete() {
		sellSettingServiceImpl.deleteSell(id);
	}
	/**
	 * 添加
	 * @return
	 */
	public String save() {
		BsAdminUser admin = (BsAdminUser) ActionContext.getContext()
				.getSession().get("adminuser");
		sell.setCreateTime(new Timestamp(new Date().getTime()));
		sell.setOperator(admin.getRealName()+"【"+admin.getUserName()+"】");
		sellSettingServiceImpl.saveSell(sell);
		return SUCCESS;
	}
	/**
	 * 更新
	 * @return
	 */
	public String update() {
		BsAdminUser admin = (BsAdminUser) ActionContext.getContext()
				.getSession().get("adminuser");
		sell.setCreateTime(new Timestamp(new Date().getTime()));
		sell.setOperator(admin.getRealName()+"【"+admin.getUserName()+"】");
		sellSettingServiceImpl.updateSell(sell);
		return SUCCESS;
	}

	public String findById() {
		sell = sellSettingServiceImpl.findById(sell.getId());
		return SUCCESS;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<BsSellSetting> getRows() {
		return rows;
	}

	public void setRows(List<BsSellSetting> rows) {
		this.rows = rows;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setSellSettingServiceImpl(
			SellSettingService sellSettingServiceImpl) {
		this.sellSettingServiceImpl = sellSettingServiceImpl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public BsSellSetting getSell() {
		return sell;
	}

	public void setSell(BsSellSetting sell) {
		this.sell = sell;
	}

}
