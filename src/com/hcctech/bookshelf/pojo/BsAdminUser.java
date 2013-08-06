package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * BsAdminUser entity. @author MyEclipse Persistence Tools
 */

public class BsAdminUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5109640262461660759L;
	private Integer auId;
	private BsRole bsRole;
	private String userName;
	private String realName;
	private String password;
	private String email;
	private Integer status;
	private Timestamp lastTime;

	// Constructors

	/** default constructor */
	public BsAdminUser() {
	}

	public BsAdminUser(String userName,String password,String email,String realName,Integer roleId) {
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.realName = realName;
		this.bsRole = new BsRole(roleId);
	}
	
	/** full constructor */
	public BsAdminUser(BsRole bsRole, String userName, String realName,
			String password, String email, Integer status, Timestamp lastTime) {
		this.bsRole = bsRole;
		this.userName = userName;
		this.realName = realName;
		this.password = password;
		this.email = email;
		this.status = status;
		this.lastTime = lastTime;
	}

	// Property accessors

	public Integer getAuId() {
		return this.auId;
	}

	public void setAuId(Integer auId) {
		this.auId = auId;
	}

	public BsRole getBsRole() {
		return this.bsRole;
	}

	public void setBsRole(BsRole bsRole) {
		this.bsRole = bsRole;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Timestamp getLastTime() {
		return lastTime;
	}

	public String getLastTimeString() {
		if(this.lastTime!=null){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			return sdf.format(this.lastTime);
		}else{
			return "";
		}
	}

	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}

}