package com.hcctech.bookshelf.services;

import java.util.List;
import java.util.Map;
import org.springframework.transaction.annotation.Transactional;
import com.hcctech.bookshelf.pojo.BsDictionary;

/**
 * 字典管理
 * @author gaodengke
 *
 */
public interface DictionaryService {
	
	/**
	 * 根据id查询 单个字典
	 * @param id
	 * @return
	 */
	@Transactional(readOnly=true)
	public BsDictionary findDictionaryById(int id);
	
	/**
	 * 查询所有字典
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<BsDictionary> findAllDictionary();
	
	/**
	 * 增加字典
	 * @param bsDictionary
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void addDictionary(BsDictionary bsDictionary);
	
	/**
	 * 删除字典
	 * @param bsDictionary
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void deleteDictionary(BsDictionary bsDictionary);
	
	/**
	 * 修改字典
	 * @param bsDictionary
	 */
	@Transactional(readOnly=false,rollbackFor=Exception.class)
	public void updateDictionary(BsDictionary bsDictionary);
	
	/**
	 * 根据类型 type 查询字典
	 * @param type
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<BsDictionary> findDictionarybyStr(int type);
	
	
	@Transactional(readOnly=true)
	public Map<String,List<BsDictionary>> loadGrade(Integer schoolStage) ;
	
	@Transactional(readOnly=true)
	public List<BsDictionary> loadSubject(Integer schoolStage , Integer type) ;
}
