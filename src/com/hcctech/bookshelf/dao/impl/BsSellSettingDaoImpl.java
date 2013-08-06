package com.hcctech.bookshelf.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;

import com.hcctech.bookshelf.dao.BsSellSettingDao;
import com.hcctech.bookshelf.dao.support.Assert;
import com.hcctech.bookshelf.pojo.BsSellSetting;

public class BsSellSettingDaoImpl extends BaseHibernateEntityDaoImpl<BsSellSetting> implements BsSellSettingDao{
	
	public double findBestPrice(String sql,Object...values){
		double d = 0d;
		Assert.hasText(sql);
		Query query = createSqlQuery(sql,values);
		@SuppressWarnings("rawtypes")
		List list = query.list();
		try {
			if(list!=null && list.size()>0){
				d = Double.valueOf(list.get(0).toString());
			}
		} catch (NumberFormatException e) {
			
		}
		return d;
	}
	public BsSellSetting findBestPriceSetting(String sql,Object...values){
		BsSellSetting bsSellSetting=new BsSellSetting();
		Assert.hasText(sql);
		Query query = createSqlQuery(sql,values);
		@SuppressWarnings("rawtypes")
		List list = new ArrayList<Object>();
		list = query.list();
		try {
			if(list!=null && list.size()>0){
				Object[] arr= list.toArray();
				Object[] arr1=(Object[]) arr[0];
				bsSellSetting.setBestPrice(Double.valueOf(arr1[0].toString()));
				bsSellSetting.setSaleName(arr1[1].toString());
				bsSellSetting.setDescrption(arr1[2].toString());
			}else{
				bsSellSetting=null;
			}
		} catch (NumberFormatException e) {
			bsSellSetting=null;
		}
		return bsSellSetting;
	}
}
