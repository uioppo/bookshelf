package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsMybook;
import com.hcctech.bookshelf.pojo.BsWebUser;

/**
 * @author 张萍萍
 * @date 2012-08-27
 * @version 1.0
 * 前台我的电子书service
 */
public interface MyBookService {

	/**
	 * @param pageNo 当前页
	 * @param pageSize 每页记录数
	 * @return Page记录
	 */
	@Transactional(readOnly=true)
	Page<BsMybook> myBookList(int page, int pageSize,BsWebUser bsWebUser);
	/**
	 * @param licenseKey 授权码
	 * @return
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	String addMyBookByKey(String licenseKey,BsWebUser user);
	
	/**
	 * @param 
	 * @return
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	String addMyBook(int bookId,BsWebUser user);
	
	/**
	 * 我的电子书   查看详细页
	 * @param myBookId 我的电子书ID
	 * @return
	 */
	@Transactional(readOnly=true)
	BsMybook myBookDetail(int myBookId);
	/**
	 * 电子书详细页面    用授权码激活电子书
	 * @param licenseKey  授权码
	 * @param productId  商品ID
	 * @return msg
	 */
	@Transactional(readOnly=false)
	String activeBookByKey(String licenseKey, int productId);

}
