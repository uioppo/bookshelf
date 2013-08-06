package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * BsReader entity. @author MyEclipse Persistence Tools
 */

public class BsReader implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6135781209507610070L;
	private Integer id;
	private String exePath;
	private String version;
	private Integer lastest;
	private Timestamp createTime;
	private String operator;
	private String changeLog;

	// Constructors

	/** default constructor */
	public BsReader() {
	}

	/** full constructor */
	public BsReader(String exePath, String version, Integer lastest,
			Timestamp createTime, String operator, String changeLog) {
		this.exePath = exePath;
		this.version = version;
		this.lastest = lastest;
		this.createTime = createTime;
		this.operator = operator;
		this.changeLog = changeLog;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getExePath() {
		return this.exePath;
	}

	public void setExePath(String exePath) {
		this.exePath = exePath;
	}

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Integer getLastest() {
		return this.lastest;
	}

	public void setLastest(Integer lastest) {
		this.lastest = lastest;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}
	public String getCreateTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null!=this.createTime){
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

	public String getChangeLog() {
		return changeLog;
	}

	public void setChangeLog(String changeLog) {
		this.changeLog = changeLog;
	}
	
}