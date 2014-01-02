package com.hcctech.bookshelf.services;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.pojo.BsEbook;
import com.hcctech.bookshelf.pojo.BsWebUser;

public interface MyBookDownLoadFlexService {
	/**
	 * 下载我的电子书
	 * @param cpuIdStr 加密后的cpuId
	 * @param user  用户
	 * @param myBookId  要下载的我的电子书Id
	 * @return
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public boolean myBookDownLoadValidate(String cpuIdStr,BsWebUser user,int myBookId);

	
	@Transactional(readOnly=true)
	public int downLoadValidate(String cpuIdStr, BsWebUser user,
			int myBookId);
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Map<String, String> downloadEbook(String cpuIdStr, BsWebUser user,int myBookId,String deviceName);

	@Transactional(readOnly=true)
	public List<BsEbook> checkUpdate(Map<String, Double> map);
	
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public Map<String,String> getDownloadEbookInfo(int bookId,String deviceName);
}
