package com.hcctech.bookshelf.services.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hcctech.bookshelf.dao.BsDictionaryDao;
import com.hcctech.bookshelf.pojo.BsDictionary;
import com.hcctech.bookshelf.services.DictionaryService;

/**
 * 字典管理
 * @author gaodengke
 *
 */
public class DictionaryServiceImpl implements DictionaryService {
	
	private BsDictionaryDao bsDictionaryDao;
	
	/**
	 * 查找所有字典记录
	 */
	public List<BsDictionary> findAllDictionary() {
		String HQL="from BsDictionary";
		return bsDictionaryDao.findByHql(HQL);
	}
	
	 /**
	  * 增加字典
	  */
	public void addDictionary(BsDictionary bsDictionary) {
		bsDictionaryDao.save(bsDictionary);
	}
	
	/**
	 * 删除字典
	 */
	public void deleteDictionary(BsDictionary bsDictionary) {
		if(bsDictionary!=null && bsDictionary.getId()!=null){
			String sql="delete from bs_dictionary where id = ?";
			bsDictionaryDao.executeSQL(sql,bsDictionary.getId());
		}
	}
	
	/**
	 * 修改字典
	 */
	public void updateDictionary(BsDictionary bsDictionary) {
		bsDictionaryDao.update(bsDictionary);
	}
	
	/**
	 * 通过id查询字典
	 */
	public BsDictionary findDictionaryById(int id) {
		String HQL="from BsDictionary bsd where bsd.id=?";
		BsDictionary bsDictionary=bsDictionaryDao.findUniqueByHql(HQL, id);
		return bsDictionary;
	}
	
	/**
	 * 通过类型  type 查询字典
	 */
	public List<BsDictionary> findDictionarybyStr(int type) {
		String HQL="from BsDictionary bsd where bsd.type = ?";
		List<BsDictionary> list=bsDictionaryDao.findByHql(HQL,type);
		return list;
	}
	
	public void setBsDictionaryDao(BsDictionaryDao bsDictionaryDao) {
		this.bsDictionaryDao = bsDictionaryDao;
	}
	/**
	 * 添加商品 年级 学科
	 */
	public Map<String,List<BsDictionary>> loadGrade(Integer schoolStage) {
		Map<String,List<BsDictionary>> map = new HashMap<String,List<BsDictionary>>();
		String hql = "From BsDictionary bsDictionary where bsDictionary.type = 2 and bsDictionary.schoolStage = " +  schoolStage ;
		List<BsDictionary> gradeList = bsDictionaryDao.findByHql(hql);
		hql = "From BsDictionary bsDictionary where bsDictionary.type = 1 and bsDictionary.schoolStage = " +  schoolStage ;
		List<BsDictionary> subjectList = bsDictionaryDao.findByHql(hql);
		map.put("gradeList", gradeList);
		map.put("subjectList", subjectList);
		return map;
	}
	
	public List<BsDictionary> loadSubject(Integer schoolStage , Integer type) {
		String hql = "From BsDictionary bsDictionary where bsDictionary.type = ? and bsDictionary.schoolStage = ?"  ;
		List<BsDictionary> gradeList = bsDictionaryDao.findByHql(hql,type,schoolStage);
		return gradeList ;
	}
	
}
