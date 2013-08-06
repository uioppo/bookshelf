package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * BsFlow entity. @author MyEclipse Persistence Tools
 */

public class BsFlow implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3281042915598669274L;
	private Integer flowId;
	private Timestamp time;
	private String objectId;
	private String objectName;
	private String type;
	private String toName;
	private String toRoleName;
	private String fromName;
	private String fromRoleName;
	private Integer isPass;
	private String idea;

	// Constructors

	/** default constructor */
	public BsFlow() {
	}

	/** full constructor */
	public BsFlow(Timestamp time, String objectId, String objectName,
			String type, String toName, String toRoleName, String fromName,
			String fromRoleName, Integer isPass, String idea) {
		this.time = time;
		this.objectId = objectId;
		this.objectName = objectName;
		this.type = type;
		this.toName = toName;
		this.toRoleName = toRoleName;
		this.fromName = fromName;
		this.fromRoleName = fromRoleName;
		this.isPass = isPass;
		this.idea = idea;
	}

	// Property accessors

	public Integer getFlowId() {
		return this.flowId;
	}

	public void setFlowId(Integer flowId) {
		this.flowId = flowId;
	}

	public Timestamp getTime() {
		return this.time;
	}
	
	public String getTime_(){
		if(null!=this.time){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(this.time);
		}else{
			return "";
		}
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getObjectId() {
		return this.objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getToName() {
		return this.toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getToRoleName() {
		return this.toRoleName;
	}

	public void setToRoleName(String toRoleName) {
		this.toRoleName = toRoleName;
	}

	public String getFromName() {
		return this.fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromRoleName() {
		return this.fromRoleName;
	}

	public void setFromRoleName(String fromRoleName) {
		this.fromRoleName = fromRoleName;
	}

	public Integer getIsPass() {
		return this.isPass;
	}

	public void setIsPass(Integer isPass) {
		this.isPass = isPass;
	}

	public String getIdea() {
		return this.idea;
	}

	public void setIdea(String idea) {
		this.idea = idea;
	}

}