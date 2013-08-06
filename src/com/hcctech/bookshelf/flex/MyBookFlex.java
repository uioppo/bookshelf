package com.hcctech.bookshelf.flex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hcctech.bookshelf.dao.BsMyBookDao;
import com.hcctech.bookshelf.flex.vo.FMyBook;
import com.hcctech.bookshelf.pojo.BsMybook;
import com.hcctech.bookshelf.pojo.BsWebUser;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;


public class MyBookFlex {
	
	private BsMyBookDao bsMyBookDao;

	public List<FMyBook> findEBookList() {
		//
		FlexSession session = FlexContext.getFlexSession();
		BsWebUser user=(BsWebUser)session.getAttribute("user");
		//,mybook.totalNumbers,mybook.bsProducts.bookCode,mybook.bsProducts.productName,mybook.bsProducts.schoolStage,mybook.bsProducts.thumbnail,mybook.bsProducts.subject,be.version,be.bookSize
		final String hql = "SELECT new BsMybook(mybook.mybookId,mybook.bsProducts.productId,mybook.downNumbers,mybook.addTime,mybook.deadline,mybook.totalNumbers,mybook.bsProducts.bookCode,mybook.bsProducts.productName,mybook.bsProducts.schoolStage,mybook.bsProducts.thumbnail,mybook.bsProducts.subject,be.version,be.bookSize,be.bookType,be.realFileName) from BsMybook mybook,BsEbook be,BsFlow flow where mybook.bsWebUser.wuId = ? and mybook.bsProducts.bookCode=be.bookCode and be.id=flow.objectId and flow.type in (1,2) and flow.isPass = 1 order by be.bookCode,be.version";
		List<BsMybook> mybooks = bsMyBookDao.findByHql(hql, user.getWuId());
		//System.out.println(mybooks);
		Map<String, FMyBook> map =new HashMap<String, FMyBook>();
		for (BsMybook bsMybook : mybooks) {
			System.out.println(bsMybook.getfMyBook().getBookCode()+"idx__"+bsMybook.getfMyBook().getGoodsId());
			map.put(bsMybook.getfMyBook().getBookCode()+"idx__"+bsMybook.getfMyBook().getGoodsId(), bsMybook.getfMyBook());
		}
		
		List<FMyBook> bookList = new ArrayList<FMyBook>(map.values());

		return bookList;
	}
	
	
	public void setBsMyBookDao(BsMyBookDao bsMyBookDao) {
		this.bsMyBookDao = bsMyBookDao;
	}

	
	
	
}
