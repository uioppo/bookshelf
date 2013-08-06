package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * BsLog entity. @author MyEclipse Persistence Tools
 */

public class BsLog implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 8764992955708786575L;
	private Integer logId;
	private Integer userId;
	private String userName;
	private Integer roleId;
	private String roleName;
	private String operateInfo;
	private String operateType;
	private String ip;
	private String class_;
	private String method;
	private Timestamp time;

	// Constructors

	/** default constructor */
	public BsLog() {
	}

	/** full constructor */
	public BsLog(Integer userId, String userName, Integer roleId,
			String roleName, String operateInfo, String operateType, String ip,
			String class_, String method, Timestamp time) {
		this.userId = userId;
		this.userName = userName;
		this.roleId = roleId;
		this.roleName = roleName;
		this.operateInfo = operateInfo;
		this.operateType = operateType;
		this.ip = ip;
		this.class_ = class_;
		this.method = method;
		this.time = time;
	}

	// Property accessors

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOperateInfo() {
		return this.operateInfo;
	}

	public void setOperateInfo(String operateInfo) {
		this.operateInfo = operateInfo;
	}

	public String getOperateType() {
		return this.operateType;
	}

	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getClass_() {
		return this.class_;
	}

	public void setClass_(String class_) {
		this.class_ = class_;
	}

	public String getMethod() {
		return this.method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Timestamp getTime() {
		return this.time;
	}
	
	public String getTime_() {
		if(null != this.time){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(this.time);
		}else{
			return "";
		}
	}
	
	public void setTime(Timestamp time) {
		this.time = time;
	}

}