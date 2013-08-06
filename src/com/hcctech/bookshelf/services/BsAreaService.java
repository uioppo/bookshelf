package com.hcctech.bookshelf.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.util.TreeJson;

/**
 * @author zoujunpeng
 *
 */
public interface BsAreaService {
	/**
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<TreeJson> findAreaAll(BsArea bsArea);
//	@Transactional(readOnly = true)
//	public Page<BsArea> findAreaByParentId(int pageNo,int pageSize,Integer id);
	@Transactional(readOnly = true)
	public BsArea findAreaById(Integer id);
	/**
	 * 保存地区
	 * 如果bsArea的areaId不是null，添加的是城市或者县，否则添加的省
	 * @param bsArea
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void saveArea(BsArea bsArea);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void updateArea(BsArea bsArea);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void deleteArea(BsArea bsArea);
}
