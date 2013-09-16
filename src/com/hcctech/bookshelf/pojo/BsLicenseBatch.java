package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * BsLicenseBatch entity. @author MyEclipse Persistence Tools
 */

public class BsLicenseBatch implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3434095748219645579L;
	private Integer batchId;
	private Integer schoolStage;
	private Timestamp createTime;
	private String operator;
	private String isLift;
	private Set<BsLicenseKey>  bsLicenseKeies = new HashSet<BsLicenseKey> (0);

	
	private String schoolName;
	private String shuliang;//界面显示  已用/总数量
	// Constructors

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/** default constructor */
	public BsLicenseBatch() {
	}

	/** full constructor */
	public BsLicenseBatch(Integer schoolStage, Timestamp createTime,
			String operator, Set<BsLicenseKey>  bsLicenseKeies) {
		this.schoolStage = schoolStage;
		this.createTime = createTime;
		this.operator = operator;
		this.bsLicenseKeies = bsLicenseKeies;
	}

	// Property accessors

	public Integer getBatchId() {
		return this.batchId;
	}

	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}

	public Integer getSchoolStage() {
		return this.schoolStage;
	}

	public void setSchoolStage(Integer schoolStage) {
		this.schoolStage = schoolStage;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}
	public String getCreateTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(null != this.createTime){
			return sdf.format(this.createTime);
		}else{
			return "";
		}
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@JSON(serialize=false)
	public Set<BsLicenseKey> getBsLicenseKeies() {
		return this.bsLicenseKeies;
	}

	public void setBsLicenseKeies(Set<BsLicenseKey>  bsLicenseKeies) {
		this.bsLicenseKeies = bsLicenseKeies;
	}

	public String getShuliang() {
		return shuliang;
	}

	public void setShuliang(String shuliang) {
		this.shuliang = shuliang;
	}

	public String getIsLift() {
		return isLift;
	}

	public void setIsLift(String isLift) {
		this.isLift = isLift;
	}
	
}