package com.hcctech.bookshelf.services.impl;

import com.hcctech.bookshelf.dao.BsSellSettingDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsSellSetting;
import com.hcctech.bookshelf.services.SellSettingService;

public class SellSettingServiceImpl implements SellSettingService {
	
	private BsSellSettingDao sellSettingDaoImpl;

	public void deleteSell(String id) {
		if (id != null || !"".equals(id)) {
			String sql = "delete from bs_sell_setting where id in(" + id + ")";
			sellSettingDaoImpl.executeSQL(sql);
		}
	}

	public Page<BsSellSetting> findAllSell(Integer pageNo, Integer pageSize) {
		String hql = "FROM BsSellSetting";
		Page<BsSellSetting> page = sellSettingDaoImpl.pagedQuery(hql, pageNo,
				pageSize);
		return page;
	}

	public boolean saveSell(BsSellSetting vo) {
		boolean flag = false;
		try {
			sellSettingDaoImpl.save(vo);
			flag = true;
		} catch (Exception e) {

		}
		return flag;
	}

	public void updateSell(BsSellSetting vo) {
		sellSettingDaoImpl.update(vo);

	}

	public BsSellSetting findById(Integer id) {

		return sellSettingDaoImpl.get(id);
	}

	public void setSellSettingDaoImpl(BsSellSettingDao sellSettingDaoImpl) {
		this.sellSettingDaoImpl = sellSettingDaoImpl;
	}

}
