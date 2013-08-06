package com.hcctech.bookshelf.actions;

import java.util.List;
import java.util.Map;

import com.hcctech.bookshelf.pojo.BsDictionary;
import com.hcctech.bookshelf.services.DictionaryService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author 高登科
 * 字典管理：新增、修改、删除、查询
 */
public class DictionaryAction extends ActionSupport {
	
	private static final long serialVersionUID = -2570242113051114866L;
	
	private DictionaryService dictionaryService;
	
	private List<BsDictionary> rows;
	
	private BsDictionary bsDictionary;
	
	private List<BsDictionary> gradeList ;
	private List<BsDictionary> subjectList ;
	private Integer schoolStage ;
	
	private Integer type;
	/**
	 * 通过科目查询
	 * @return
	 */
	public String findDictionaryBySubject(){
		rows=dictionaryService.findDictionarybyStr(1);
		return "success";
	}
	
	/**
	 * 通过年级查询
	 * @return
	 */
	public String findDictionaryByGrade(){
		rows=dictionaryService.findDictionarybyStr(2);
		return "success";
	}
	/**
	 * 通过城市查询
	 * @return
	 */
	public String findDictionaryByCity(){
		rows=dictionaryService.findDictionarybyStr(3);
		return "success";
	}
	
	/**
	 * 根据类型查询
	 * @return
	 */
	public String findByType(){
		rows=dictionaryService.findDictionarybyStr(type);
		return "success";
	}
	
	/**
	 * 新增字典
	 * @return
	 */
	public String addDictionary(){
		dictionaryService.addDictionary(bsDictionary);
		return "success";
	}
	/**
	 * 通过ID查询单个字典记录
	 * @return
	 */
	public String findDictionaryById(){
		bsDictionary=dictionaryService.findDictionaryById(bsDictionary.getId());
		return "success";
	}
	/**
	 * 修改字典记录
	 * @return
	 */
	public String updateDictionary(){
		dictionaryService.updateDictionary(bsDictionary);
		return "success";
	}
	/**
	 * 删除 字典 记录
	 * @return
	 */
	public String deleteDictionary(){
		dictionaryService.deleteDictionary(bsDictionary);
		return "success";
	}
	
	/**
	 * 添加商品的 年级 学科
	 * @return
	 */
	public String loadGrade() {
		Map<String,List<BsDictionary>> map = dictionaryService.loadGrade(schoolStage);
		subjectList = map.get("subjectList");
		gradeList = map.get("gradeList");
		return SUCCESS;
	}
	
	public String loadSubject() {
		subjectList = dictionaryService.loadSubject(schoolStage,type);
		return SUCCESS;
	}
	
	
	public List<BsDictionary> getRows() {
		return rows;
	}
	public void setDictionaryService(DictionaryService dictionaryService) {
		this.dictionaryService = dictionaryService;
	}

	public BsDictionary getBsDictionary() {
		return bsDictionary;
	}
	public void setBsDictionary(BsDictionary bsDictionary) {
		this.bsDictionary = bsDictionary;
	}
	
	
	public void setSchoolStage(Integer schoolStage) {
		this.schoolStage = schoolStage;
	}
	public List<BsDictionary> getGradeList() {
		return gradeList;
	}
	public List<BsDictionary> getSubjectList() {
		return subjectList;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
}
