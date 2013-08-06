package com.hcctech.bookshelf.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hcctech.bookshelf.dao.BsAdminUserDao;
import com.hcctech.bookshelf.dao.BsRoleDao;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsFunction;
import com.hcctech.bookshelf.pojo.BsRole;
import com.hcctech.bookshelf.services.RoleService;

public class RoleServiceImpl implements RoleService{
	
	private BsRoleDao bsRoleDao;
	private BsAdminUserDao bsAdminUserDao;
	public void setBsRoleDao(BsRoleDao bsRoleDao) {
		this.bsRoleDao = bsRoleDao;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RoleService#findAllRole()
	 */
	public List<BsRole> findAllRole(){
		final String hql = "from BsRole br";
		List<BsRole> brList = bsRoleDao.findByHql(hql);
		for(BsRole br:brList){
			br.setBsAdminUsers(null);
			br.setBsFunctions(null);
		}
		return brList;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RoleService#saveRole(com.hcctech.bookshelf.pojo.BsRole)
	 */
	public boolean saveRole(BsRole brRole){
		boolean flag = true;
		final String hql = "from BsRole where roleName = ?";
		BsRole temp = bsRoleDao.findUniqueByHql(hql, brRole.getRoleName());
		if(temp==null){
			bsRoleDao.save(brRole);
		}else{
			flag = false;
		}
		return flag;
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RoleService#deleteRoleByIdsString(java.lang.String)
	 */
	/*public void deleteRoleByIdsString(String ids){
		if(ids!=null && !"".equals(ids)){
			String sql = "delete from bs_role where role_id in("+ids+")";
			bsRoleDao.executeSQL(sql);
		}
	}*/
	public boolean deleteRoleByIdsString(String ids){
		boolean flag=false;
		if(ids!=null && !"".equals(ids)){
			int id=Integer.valueOf(ids);
			String hql="from BsAdminUser b where b.bsRole.roleId=?";
			List<BsAdminUser> list = bsAdminUserDao.findByHql(hql, id);
			if(list==null||list.size()<=0){
				String sql = "delete from bs_role where role_id ="+id;
				bsRoleDao.executeSQL(sql);
				flag=true;
			}
		}
		return flag;
	}
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RoleService#findRoleById(com.hcctech.bookshelf.pojo.BsRole)
	 */
	public BsRole findRoleById(BsRole bsRole){
		final String hql = "from BsRole where roleId = ?";
		if(bsRole!=null && null!=bsRole.getRoleId()){
			BsRole temp = bsRoleDao.findUniqueByHql(hql, bsRole.getRoleId());
			if(temp!=null){
				temp.setBsAdminUsers(null);
				temp.setBsFunctions(null);
			}
			return temp;
		}else{return null;}
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RoleService#updateRole(com.hcctech.bookshelf.pojo.BsRole)
	 */
	public void updateRole(BsRole bsRole){
		bsRoleDao.update(bsRole);
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RoleService#updateRoleFunction(com.hcctech.bookshelf.pojo.BsRole)
	 */
	public void updateRoleFunction(BsRole bsRole,String functionId){
		bsRole = bsRoleDao.get(bsRole.getRoleId());
		Set<BsFunction> set = new HashSet<BsFunction>();
		if(null!=functionId && !"".equals(functionId)){
			String[] ids = functionId.split(",");
			for(String id:ids){
				if(!id.equals("0")){
					BsFunction b = new BsFunction();
					b.setFunctionId(Integer.parseInt(id));
					set.add(b);
				}
			}
		}
		bsRole.setBsFunctions(null);
		bsRole.setBsFunctions(set);
	}

	public void setBsAdminUserDao(BsAdminUserDao bsAdminUserDao) {
		this.bsAdminUserDao = bsAdminUserDao;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.RoleService#findRoleFunctionIds(com.hcctech.bookshelf.pojo.BsRole)
	 */
	public List<Integer> findRoleFunctionIds(BsRole bsRole) {
		BsRole br=bsRoleDao.get(bsRole.getRoleId());
		List<Integer> list =null;
		if(br!=null){
			list = new ArrayList<Integer>();
			for (Object bf : br.getBsFunctions()) {
				BsFunction function = (BsFunction)bf;
				list.add(function.getFunctionId());
			}
		}
		return list;
	}
	
}
