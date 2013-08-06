package com.hcctech.bookshelf.services.impl;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.hcctech.bookshelf.dao.BsFunctionDao;
import com.hcctech.bookshelf.pojo.BsFunction;
import com.hcctech.bookshelf.services.FunctionService;
import com.hcctech.bookshelf.vo.MenuTree;

public class FunctionServiceImpl implements FunctionService{

	private BsFunctionDao bsFunctionDao;
	
	public void setBsFunctionDao(BsFunctionDao bsFunctionDao) {
		this.bsFunctionDao = bsFunctionDao;
	}

	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.FunctionService#findAllFunction()
	 */
	public Collection<MenuTree> findAllFunction(){
		final String hql = "from BsFunction order by orderIndex,funOrder";
		List<BsFunction> bsFunctionList = bsFunctionDao.findByHql(hql);
//		for(BsFunction f:bsFunctionList){
//			@SuppressWarnings("unchecked")
//			Set<BsRole> set = f.getBsRoles();
//			for(BsRole br:set){
//				br.setBsAdminUsers(null);
//				br.setBsFunctions(null);
//			}
//		}
		
		Map<String, MenuTree> map = new LinkedHashMap<String, MenuTree>();
		int i = 196280013;
		for (BsFunction bsf : bsFunctionList) {
			MenuTree superMenuTree = map.get(bsf.getParentNode());
			if(superMenuTree==null){
				MenuTree menuTree = new MenuTree(i, 0,bsf.getParentNode(), null, null);
				//
				MenuTree subMenuTree = new MenuTree(bsf.getFunctionId(),bsf.getFunctionId(), bsf.getFunctionName(), bsf.getFunctionUrl(), bsf.getDescription());
				menuTree.addSubMenuTree(subMenuTree);
				map.put(bsf.getParentNode(),menuTree);
			}else{
				if(bsf.getShortcut().equals(0)){
					superMenuTree.addSubMenuTree(new MenuTree(bsf.getFunctionId(),bsf.getFunctionId(), bsf.getFunctionName(), bsf.getFunctionUrl(), bsf.getDescription()));
				}else{
					for (MenuTree mt : superMenuTree.getChildren()) {
						if(mt.getCode().equals(bsf.getShortcut())){
							mt.addSubMenuTree(new MenuTree(bsf.getFunctionId(),bsf.getFunctionId(), bsf.getFunctionName(), bsf.getFunctionUrl(), bsf.getDescription()));
							//
						}
					}
				}
			}
			i++;
		}
//		for (MenuTree vv : map.values()) {
//			System.err.println(vv.getName());
//		}
		
		return map.values();
	}
	
	/* (non-Javadoc)
	 * @see com.hcctech.bookshelf.services.FunctionService#findFunctionByRoleId(java.lang.Integer)
	 */
	public List<BsFunction> findFunctionByRoleId(Integer roelId){
		final String hql = "select f from BsFunction as f join f.bsRoles as r where r.roleId=? order by f.orderIndex,f.funOrder";
		List<BsFunction> t = bsFunctionDao.findByHql(hql, roelId);
		for(BsFunction b:t){
			b.setBsRoles(null);
		}
		return t;
	}
}
