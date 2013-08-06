package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.hcctech.bookshelf.flex.vo.FMyBook;

/**
 * BsMybook entity. @author MyEclipse Persistence Tools
 */

/**
 * @author randyjie
 *
 */
public class BsMybook implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8006385448122836348L;
	private Integer mybookId;
	private BsProducts bsProducts;
	private BsWebUser bsWebUser;
	private Integer downNumbers;
	private Timestamp addTime;
	private Timestamp deadline;
	private Integer totalNumbers;
	private Set bsUserDrms = new HashSet(0);

	// Constructors

	/** default constructor */
	public BsMybook() {
	}

	/** full constructor */
	public BsMybook(BsProducts bsProducts, BsWebUser bsWebUser,
			Integer downNumbers, Timestamp addTime, Timestamp deadline,Integer totalNumbers,
			Set bsUserDrms) {
		this.bsProducts = bsProducts;
		this.bsWebUser = bsWebUser;
		this.downNumbers = downNumbers;
		this.addTime = addTime;
		this.deadline = deadline;
		this.totalNumbers = totalNumbers;
		this.bsUserDrms = bsUserDrms;
	}

	/**
	 * flex 电子书列表
	 */
	private FMyBook fMyBook;
	public BsMybook(Integer mybookId,Integer goodsId, Integer downNumbers, Date addTime,
			Date deadline, Integer totalNumbers, String bookCode,
			String bookName, Integer schoolStage, String thumbnail,
			String subject, Double version, Integer bookSize,String bookType,String realFileName) {
		this.fMyBook = new FMyBook();
		this.fMyBook.setMybookId(mybookId);
		this.fMyBook.setGoodsId(goodsId);
		this.fMyBook.setAddTime(addTime);
		this.fMyBook.setBookCode(bookCode);
		this.fMyBook.setBookName(bookName);
		this.fMyBook.setBookSize(bookSize);
		this.fMyBook.setDeadline(deadline);
		this.fMyBook.setDownNumbers(downNumbers);
		this.fMyBook.setSchoolStage(schoolStage);
		this.fMyBook.setSubject(subject);
		this.fMyBook.setThumbnail(thumbnail);
		this.fMyBook.setTotal(totalNumbers);
		this.fMyBook.setVersion(version);
		this.fMyBook.setBookType(bookType);
		this.fMyBook.setRealFileName(realFileName);
	}


	public Integer getMybookId() {
		return this.mybookId;
	}

	public void setMybookId(Integer mybookId) {
		this.mybookId = mybookId;
	}

	public BsProducts getBsProducts() {
		return this.bsProducts;
	}

	public void setBsProducts(BsProducts bsProducts) {
		this.bsProducts = bsProducts;
	}

	public BsWebUser getBsWebUser() {
		return this.bsWebUser;
	}

	public void setBsWebUser(BsWebUser bsWebUser) {
		this.bsWebUser = bsWebUser;
	}

	public Integer getDownNumbers() {
		return this.downNumbers;
	}

	public void setDownNumbers(Integer downNumbers) {
		this.downNumbers = downNumbers;
	}

	public Timestamp getAddTime() {
		return this.addTime;
	}
	public String getAddTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null != this.addTime){
			return sdf.format(this.addTime);
		}else{
			return "";
		}
	}

	public void setAddTime(Timestamp addTime) {
		this.addTime = addTime;
	}

	public Timestamp getDeadline() {
		return this.deadline;
	}
	public String getDeadline_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null != this.deadline){
			return sdf.format(this.deadline);
		}else{
			return "永久";
		}
	}
	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public Set getBsUserDrms() {
		return this.bsUserDrms;
	}

	public void setBsUserDrms(Set bsUserDrms) {
		this.bsUserDrms = bsUserDrms;
	}

	public Integer getTotalNumbers() {
		return totalNumbers;
	}

	public void setTotalNumbers(Integer totalNumbers) {
		this.totalNumbers = totalNumbers;
	}

	public FMyBook getfMyBook() {
		return fMyBook;
	}
	
}