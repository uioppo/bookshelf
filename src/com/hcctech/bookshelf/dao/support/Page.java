package com.hcctech.bookshelf.dao.support;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页对象
 */
public class Page<T> implements Serializable{

	private static final long serialVersionUID = 2724312986586722942L;


	private List<T> list; // 当前页中存放的记录,类型一般为List

	private long totalCount; // 总记录数
	

	public Page() {
		this(0,new ArrayList<T>());
	}

	public Page(long totalCount, List<T> list) {
		this.totalCount = totalCount;
		this.list = list;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}
	
}
