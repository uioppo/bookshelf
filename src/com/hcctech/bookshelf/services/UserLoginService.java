package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.pojo.BsWebUser;


public interface UserLoginService {
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public BsWebUser login(Object... values);
	
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean lockUser(String username);

}
