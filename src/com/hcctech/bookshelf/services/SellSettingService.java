package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsSellSetting;

public interface SellSettingService {
	/**
	 * 列出所有的折扣
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page<BsSellSetting> findAllSell(Integer pageNo, Integer pageSize);

	/**
	 * 添加折扣信息
	 * 
	 * @param vo
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean saveSell(BsSellSetting vo);

	@Transactional(readOnly = false)
	public void updateSell(BsSellSetting vo);

	@Transactional(readOnly = false)
	public void deleteSell(String id);

	@Transactional(readOnly = true)
	public BsSellSetting findById(Integer id);

}
