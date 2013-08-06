package com.hcctech.bookshelf.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.util.StringUtils;

import com.hcctech.bookshelf.dao.BsAreaDao;
import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.services.BsAreaService;
import com.hcctech.bookshelf.util.TreeJson;

/**
 * @author zoujunpeng
 *
 */
public class BsAreaServiceImpl implements BsAreaService{

	private BsAreaDao bsAreaDao;
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.BsAreaService#findAreaByParentId(int, int, java.lang.Integer)
	 */
//	public Page<BsArea> findAreaByParentId(int pageNo,int pageSize,Integer id){
//		String hql = "";
//		
//		Page<BsArea> pageObject = null;
//		if(id!=null && id !=0 ){
//			hql = "from BsArea ba where ba.bsArea.areaId = ?";
//			pageObject = bsAreaDao.pagedQuery(hql, pageNo, pageSize,id);
//		}else{
//			hql = "from BsArea ba where ba.bsArea.areaId is null";
//			pageObject = bsAreaDao.pagedQuery(hql, pageNo, pageSize);
//		}
//		
//		if(pageObject!=null){
//			List<BsArea> list = pageObject.getList();
//			for(BsArea ba:list){
//				ba.setBsSchools(null);
//				ba.setBsAreas(null);
//				ba.setBsArea(null);
//			}
//		}
//		
//		return pageObject;
//	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.BsAreaService#findAreaAll()
	 */
	@SuppressWarnings("unchecked")
	public List<TreeJson> findAreaAll(BsArea bsArea){
//		final String s = "from BsArea where bsArea.areaId is null order by areaId";
		StringBuilder sb = new StringBuilder("select * from bs_area where ");
		if(bsArea==null){
			sb.append("BS__area_id is null ");
		}else{
			sb.append("area_id is not null ");
			if(bsArea.getAreaName() !=null && !bsArea.getAreaName().trim().equals("")){
				sb.append(" and area_name like ").append("'%"+bsArea.getAreaName()+"%' ");
			}
			if(bsArea.getAreaCode() !=null && !bsArea.getAreaCode().trim().equals("")){
				sb.append(" and area_code = '").append(bsArea.getAreaCode()).append("' ");
			}
		}
		
		
		sb.append(" order by area_id");
		List<BsArea> sList = bsAreaDao.findBySql(sb.toString());
		List<TreeJson> list = new ArrayList<TreeJson>();
		for(BsArea s_:sList){
			s_.setBsArea(null);
			s_.setBsSchools(null);
			TreeJson sheng = new TreeJson();
			sheng.setId(s_.getAreaId());
			sheng.setName(s_.getAreaName());
			sheng.setAreaCode(s_.getAreaCode());
			//sheng.setIconCls("icon-map");
			Set<BsArea> shiList = (Set<BsArea>)s_.getBsAreas();
			if(shiList==null || shiList.size()==0){
				sheng.setState("open");
			}
			list.add(sheng);
			for(BsArea shi_:shiList){
				shi_.setBsArea(null);
				shi_.setBsSchools(null);
				TreeJson shi_t = new TreeJson();
				shi_t.setId(shi_.getAreaId());
				shi_t.setName(shi_.getAreaName());
				shi_t.set_parentId(s_.getAreaId());
				shi_t.setAreaCode(shi_.getAreaCode());
				//shi_t.setIconCls("icon-map");
				Set<BsArea> xianList = (Set<BsArea>)shi_.getBsAreas();
				if(xianList==null || xianList.size()==0){
					shi_t.setState("open");
				}
				list.add(shi_t);
				for(BsArea xian_:xianList){
					xian_.setBsArea(null);
					xian_.setBsSchools(null);
					TreeJson xian_t = new TreeJson();
					xian_t.setId(xian_.getAreaId());
					xian_t.setAreaCode(xian_.getAreaCode());
					xian_t.setName(xian_.getAreaName());
					xian_t.set_parentId(shi_.getAreaId());
					xian_t.setState("open");
					//xian_t.setIconCls("icon-map");
					list.add(xian_t);
				}
			}
		}
		return list;
	}
	
	public BsArea findAreaById(Integer id){
		final String hql = "from BsArea where areaId = ?";
		BsArea ba = bsAreaDao.findUniqueByHql(hql, id);
		ba.setBsSchools(null);
		ba.setBsAreas(null);
		ba.setBsArea(null);
		return ba;
	}
	
	public void saveArea(BsArea bsArea){
		if(bsArea!=null && bsArea.getAreaId()!=null){
			BsArea b = new BsArea();
			b.setAreaId(bsArea.getAreaId());
			bsArea.setBsArea(b);
		}
		bsAreaDao.save(bsArea);
	}
	
	public void updateArea(BsArea bsArea){
		final String hql = "from BsArea where areaId = ?";
		BsArea ba = bsAreaDao.findUniqueByHql(hql, bsArea.getAreaId());
		ba.setAreaName(bsArea.getAreaName());
		ba.setAreaCode(bsArea.getAreaCode());
	}
	
	public void deleteArea(BsArea bsArea){
		bsAreaDao.remove(bsArea.getAreaId());
	}

	public void setBsAreaDao(BsAreaDao bsAreaDao) {
		this.bsAreaDao = bsAreaDao;
	}
}