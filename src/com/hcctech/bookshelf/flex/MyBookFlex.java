package com.hcctech.bookshelf.flex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hcctech.bookshelf.dao.BsMyBookDao;
import com.hcctech.bookshelf.flex.vo.FMyBook;
import com.hcctech.bookshelf.pojo.BsDictionary;
import com.hcctech.bookshelf.pojo.BsMybook;
import com.hcctech.bookshelf.pojo.BsWebUser;
import com.hcctech.bookshelf.services.DictionaryService;

import flex.messaging.FlexContext;
import flex.messaging.FlexSession;


public class MyBookFlex {
	
	private BsMyBookDao bsMyBookDao;
	private DictionaryService dictionaryService;

	public List<FMyBook> findEBookList() {
		//
		FlexSession session = FlexContext.getFlexSession();
		BsWebUser user=(BsWebUser)session.getAttribute("user");
		//,mybook.totalNumbers,mybook.bsProducts.bookCode,mybook.bsProducts.productName,mybook.bsProducts.schoolStage,mybook.bsProducts.thumbnail,mybook.bsProducts.subject,be.version,be.bookSize
		final String hql = "SELECT new BsMybook(mybook.mybookId,mybook.bsProducts.productId,mybook.downNumbers,mybook.addTime,mybook.deadline,mybook.totalNumbers,mybook.bsProducts.bookCode,mybook.bsProducts.productName,mybook.bsProducts.schoolStage,mybook.bsProducts.thumbnail,mybook.bsProducts.subject,be.version,be.bookSize,be.bookType,be.realFileName,be.grade) from BsMybook mybook,BsEbook be,BsFlow flow where mybook.bsWebUser.wuId = ? and mybook.bsProducts.bookCode=be.bookCode and be.id=flow.objectId and flow.type in (1,2) and flow.isPass = 1 order by be.bookCode,be.version";
		List<BsMybook> mybooks = bsMyBookDao.findByHql(hql, user.getWuId());
		//System.out.println(mybooks);
		Map<String, FMyBook> map =new HashMap<String, FMyBook>();
		for (BsMybook bsMybook : mybooks) {
			try {
				System.out.println(bsMybook.getfMyBook().getBookCode()+"idx__"+bsMybook.getfMyBook().getGoodsId());
				bsMybook.getfMyBook().setGrade_code(getGradeCode(bsMybook.getfMyBook().getGrade()));
			}catch(Exception e) {
				e.printStackTrace();
				continue;
			}
			map.put(bsMybook.getfMyBook().getBookCode()+"idx__"+bsMybook.getfMyBook().getGoodsId(), bsMybook.getfMyBook());
		}
		
		List<FMyBook> bookList = new ArrayList<FMyBook>(map.values());

		return bookList;
	}
	private static List<BsDictionary> bsDictionaryList = null;
	private String getGradeCode(String grade) {
		String ret = "";
		synchronized (bsDictionaryList) {
			if(bsDictionaryList==null) {
				bsDictionaryList = dictionaryService.findDictionarybyStr(2);
			}
		}
		if(bsDictionaryList!=null) {
			for (BsDictionary bsDictionary : bsDictionaryList) {
				if(bsDictionary.getName().equalsIgnoreCase(grade)) {
					ret = bsDictionary.getDicCode();
					break;
				}
			}
		}
		
		return ret;
	}
	
	
	public void setBsMyBookDao(BsMyBookDao bsMyBookDao) {
		this.bsMyBookDao = bsMyBookDao;
	}

	
	
	
}
