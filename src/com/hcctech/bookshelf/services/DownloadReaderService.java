package com.hcctech.bookshelf.services;

import org.springframework.transaction.annotation.Transactional;

import com.hcctech.bookshelf.pojo.BsReader;

public interface DownloadReaderService {

	/**
	 * 查询最新版本的阅读器下载路径
	 * @return BsReader
	 */
	@Transactional(readOnly=true)
	BsReader loadReaderPath();

}
