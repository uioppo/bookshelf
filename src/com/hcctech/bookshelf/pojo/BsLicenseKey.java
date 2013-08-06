package com.hcctech.bookshelf.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BsLicenseKey entity. @author MyEclipse Persistence Tools
 */

public class BsLicenseKey implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 6510082729464357781L;
	private Integer lkid;
	private String keyId;
	private BsWebUser bsWebUser;
	private BsLicenseBatch bsLicenseBatch;
	private BsSchool bsSchool;
	private Integer useStatus;
	private Date lifeTime;
	private String serialNum;
	private Set bsProductses = new HashSet(0);

	// Constructors

	/** default constructor */
	public BsLicenseKey() {
	}

	/** full constructor */
	public BsLicenseKey(BsWebUser bsWebUser, BsLicenseBatch bsLicenseBatch,
			BsSchool bsSchool, Integer useStatus, Set bsProductses) {
		this.bsWebUser = bsWebUser;
		this.bsLicenseBatch = bsLicenseBatch;
		this.bsSchool = bsSchool;
		this.useStatus = useStatus;
		this.bsProductses = bsProductses;
	}

	// Property accessors

	public String getKeyId() {
		return this.keyId;
	}

	public void setKeyId(String keyId) {
		this.keyId = keyId;
	}

	public BsWebUser getBsWebUser() {
		return this.bsWebUser;
	}

	public void setBsWebUser(BsWebUser bsWebUser) {
		this.bsWebUser = bsWebUser;
	}

	public BsLicenseBatch getBsLicenseBatch() {
		return this.bsLicenseBatch;
	}

	public void setBsLicenseBatch(BsLicenseBatch bsLicenseBatch) {
		this.bsLicenseBatch = bsLicenseBatch;
	}

	public BsSchool getBsSchool() {
		return this.bsSchool;
	}

	public void setBsSchool(BsSchool bsSchool) {
		this.bsSchool = bsSchool;
	}

	public Integer getUseStatus() {
		return this.useStatus;
	}

	public void setUseStatus(Integer useStatus) {
		this.useStatus = useStatus;
	}

	public Set getBsProductses() {
		return this.bsProductses;
	}

	public void setBsProductses(Set bsProductses) {
		this.bsProductses = bsProductses;
	}

	public Date getLifeTime() {
		return lifeTime;
	}
	
	public String getLifeTime_(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null != this.lifeTime){
			return sdf.format(this.lifeTime);
		}else{
			return "";
		}
	}
	public void setLifeTime(Date lifeTime) {
		this.lifeTime = lifeTime;
	}

	public String getSerialNum() {
		return serialNum;
	}

	public void setSerialNum(String serialNum) {
		this.serialNum = serialNum;
	}

	public Integer getLkid() {
		return lkid;
	}

	public void setLkid(Integer lkid) {
		this.lkid = lkid;
	}
	
}