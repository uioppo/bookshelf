package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;

/**
 * BsUserDrm entity. @author MyEclipse Persistence Tools
 */

public class BsUserDrm implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -7396980320811762749L;
	private Integer id;
	private BsMybook bsMybook;
	private BsWebUser bsWebUser;
	private String cpuId;
	private Timestamp operateTime;

	// Constructors

	/** default constructor */
	public BsUserDrm() {
	}

	/** full constructor */
	public BsUserDrm(BsMybook bsMybook, BsWebUser bsWebUser, String cpuId,
			Timestamp operateTime) {
		this.bsMybook = bsMybook;
		this.bsWebUser = bsWebUser;
		this.cpuId = cpuId;
		this.operateTime = operateTime;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BsMybook getBsMybook() {
		return this.bsMybook;
	}

	public void setBsMybook(BsMybook bsMybook) {
		this.bsMybook = bsMybook;
	}

	public BsWebUser getBsWebUser() {
		return this.bsWebUser;
	}

	public void setBsWebUser(BsWebUser bsWebUser) {
		this.bsWebUser = bsWebUser;
	}

	public String getCpuId() {
		return this.cpuId;
	}

	public void setCpuId(String cpuId) {
		this.cpuId = cpuId;
	}

	public Timestamp getOperateTime() {
		return this.operateTime;
	}

	public void setOperateTime(Timestamp operateTime) {
		this.operateTime = operateTime;
	}

}