package com.hcctech.bookshelf.services;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.pojo.BsRole;

/**
 * @author zoujunpeng
 *
 */
public interface RoleService {
	
	@Transactional(readOnly=true)
	public List<BsRole> findAllRole();
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean saveRole(BsRole brRole);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean deleteRoleByIdsString(String ids);
	@Transactional(readOnly=true)
	public BsRole findRoleById(BsRole bsRole);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void updateRole(BsRole bsRole);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void updateRoleFunction(BsRole bsRole,String functionId);
	@Transactional(readOnly=true)
	public List<Integer> findRoleFunctionIds(BsRole bsRole);
}
