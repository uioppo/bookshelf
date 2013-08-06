package com.hcctech.bookshelf.services;


import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.dao.support.Page;
import com.hcctech.bookshelf.pojo.BsAdminUser;
import com.hcctech.bookshelf.pojo.BsEbook;

/**
 * @author zoujunpeng
 *
 */
public interface EBookService {

	/**
	 * @param bsEbook
	 * @return
	 * 电子书列表【审核、未审核】
	 */
	@Transactional(readOnly = true)
	public Page<BsEbook> findEBookList(String subject ,Integer status,int pageNo,int pageSize,String name,String value);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void updateEbookStatus(Integer bookId,Integer status,String advice,BsAdminUser adminUser);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void deleteEbookById(Integer bookId);

	/**
	 * 添加电子书
	 * @param ebook
	 * @param adminUser
	 * @throws Exception 
	 */
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public void addEbook(BsEbook ebook, BsAdminUser adminUser,boolean isSecret) throws Exception;
	/**
	 * 获取电子书信息
	 * @param bookId
	 * @return
	 */
	@Transactional(readOnly = true)
	public BsEbook loadEbook(Integer bookId);
	@Transactional(readOnly = true)
	public BsEbook loadEbookByUpdate(Integer bookId);
	@Transactional(readOnly = true)
	public Page<BsEbook> findUpdateEBookList(int pageNo,int pageSize,String name,String value);
	/**
	 * 编辑电子书属性
	 * @param adminUser
	 * @param bsEbook
	 * @return
	 */
	@Transactional(readOnly = false,rollbackFor=Exception.class)
	public boolean editEbookAttr(BsAdminUser adminUser , BsEbook bsEbook);
	
	@Transactional(readOnly = true)
	public String getEbookVersion(String bookCode);
	/**
	 * @param bookName 电子书名称
	 * @return flag
	 */
	@Transactional(readOnly = true)
	public boolean checkName(String bookName);
}
