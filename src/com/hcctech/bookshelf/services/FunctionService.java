package com.hcctech.bookshelf.services;

import java.util.Collection;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.pojo.BsFunction;
import com.hcctech.bookshelf.vo.MenuTree;

public interface FunctionService {

	@Transactional(readOnly=true)
	public Collection<MenuTree> findAllFunction();
	@Transactional(readOnly=true)
	public List<BsFunction> findFunctionByRoleId(Integer roelId);
}
