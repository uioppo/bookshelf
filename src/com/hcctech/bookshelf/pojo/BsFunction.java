package com.hcctech.bookshelf.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * BsFunction entity. @author MyEclipse Persistence Tools
 */

public class BsFunction implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3457801562593124327L;
	private Integer functionId;
	private String functionName;
	private String functionUrl;
	private String parentNode;
	private Integer shortcut;
	private String description;
	private Integer orderIndex;
	private Integer funOrder;
	private Set bsRoles = new HashSet(0);

	// Constructors

	/** default constructor */
	public BsFunction() {
	}
	
	/** full constructor */
	public BsFunction(String functionName, String functionUrl,
			String description, Set bsRoles) {
		this.functionName = functionName;
		this.functionUrl = functionUrl;
		this.description = description;
		this.bsRoles = bsRoles;
	}

	// Property accessors

	public Integer getFunctionId() {
		return this.functionId;
	}

	public void setFunctionId(Integer functionId) {
		this.functionId = functionId;
	}

	public String getFunctionName() {
		return this.functionName;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public String getFunctionUrl() {
		return this.functionUrl;
	}

	public void setFunctionUrl(String functionUrl) {
		this.functionUrl = functionUrl;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getBsRoles() {
		return this.bsRoles;
	}

	public void setBsRoles(Set bsRoles) {
		this.bsRoles = bsRoles;
	}

	public String getParentNode() {
		return parentNode;
	}

	public void setParentNode(String parentNode) {
		this.parentNode = parentNode;
	}

	public Integer getShortcut() {
		return shortcut;
	}

	public void setShortcut(Integer shortcut) {
		this.shortcut = shortcut;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Integer getFunOrder() {
		return funOrder;
	}

	public void setFunOrder(Integer funOrder) {
		this.funOrder = funOrder;
	}

}