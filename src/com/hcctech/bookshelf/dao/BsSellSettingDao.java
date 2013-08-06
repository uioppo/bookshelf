package com.hcctech.bookshelf.dao;


import com.hcctech.bookshelf.pojo.BsSellSetting;

public interface BsSellSettingDao extends BaseHibernateEntityDao<BsSellSetting>{
	public double findBestPrice(String sql,Object...values);
	public BsSellSetting findBestPriceSetting(String sql,Object...values);
}
