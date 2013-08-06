package com.hcctech.bookshelf.services.impl;

import com.hcctech.bookshelf.dao.BsSchoolDao;
import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsArea;
import com.hcctech.bookshelf.pojo.BsSchool;
import com.hcctech.bookshelf.services.SchoolManagerService;

/**
 * 学校管理
 * @author apple
 *
 */
public class SchoolManagerServiceImpl implements SchoolManagerService{
	
	private BsSchoolDao bsSchoolDao;
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.SchoolManagerService#addOrUpdate(com.hcctech.bookshelf.pojo.BsSchool)
	 */
	public void addOrUpdate(BsSchool bsSchool){
		bsSchoolDao.merge(bsSchool);
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.SchoolManagerService#loadSchoolById(com.hcctech.bookshelf.pojo.BsSchool)
	 */
	public BsSchool loadSchoolById(BsSchool bsSchool){
		BsSchool bsSchool1=new BsSchool();
		if(bsSchool!=null&&bsSchool.getSchoolId()>0){
			final String hql="from BsSchool b where b.schoolId=?";
			bsSchool1=bsSchoolDao.findUniqueByHql(hql, bsSchool.getSchoolId());
			bsSchool1.setBsLicenseKeies(null);
			BsArea bsArea=bsSchool1.getBsArea();
			if(bsArea!=null){
				bsArea.setBsAreas(null);
				bsArea.setBsSchools(null);
				if(bsArea.getBsArea()!=null){
					bsArea.getBsArea().setBsAreas(null);
					bsArea.getBsArea().setBsSchools(null);
					if(bsArea.getBsArea().getBsArea()!=null){
						bsArea.getBsArea().getBsArea().setBsAreas(null);
						bsArea.getBsArea().getBsArea().setBsSchools(null);
						bsArea.getBsArea().getBsArea().setBsArea(null);
					}
				}
			}
		}
		return bsSchool1;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.SchoolManagerService#deleteSchool(java.lang.String)
	 */
	public void deleteSchool(String ids){
		String sql = "delete from bs_school where school_id in("+ids+")";
		bsSchoolDao.executeSQL(sql);
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.SchoolManagerService#loadSchoolList(java.lang.String, int, int, java.lang.String, java.lang.String)
	 */
	public Page<BsSchool> loadSchoolList(String schoolName, int page, int pageSize,
			String sort, String order){
		final StringBuilder hql=new StringBuilder("from BsSchool b ");
		if(schoolName!=null&&!"".equals(schoolName.trim())){
			hql.append("where b.schoolName like '%"+schoolName+"%'");
		}
		if (sort != null && !"".equals(sort.trim()) && order != null
				&& !"".equals(order.trim())) {
			hql.append(" order by b.").append(sort).append(" ").append(order);
		} else {
			hql.append(" order by b.schoolId asc");
		}
		Page<BsSchool> schoolPage=bsSchoolDao.pagedQuery(hql.toString(), page, pageSize);
		if(schoolPage!=null&&schoolPage.getList()!=null&&schoolPage.getList().size()>0){
			for(BsSchool bsSchool:schoolPage.getList()){
				bsSchool.setBsLicenseKeies(null);
				bsSchool.getBsArea();
				if(bsSchool.getBsArea()!=null){
					bsSchool.getBsArea().setBsArea(null);
					bsSchool.getBsArea().setBsAreas(null);
					bsSchool.getBsArea().setBsSchools(null);
				}
			}
		}
		return schoolPage;
	}

	public void setBsSchoolDao(BsSchoolDao bsSchoolDao) {
		this.bsSchoolDao = bsSchoolDao;
	}
	
	

}
