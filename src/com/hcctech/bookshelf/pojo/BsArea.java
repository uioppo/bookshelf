package com.hcctech.bookshelf.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * BsArea entity. @author MyEclipse Persistence Tools
 */

public class BsArea implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -935072361670830733L;
	private Integer areaId;
	private BsArea bsArea;
	private String areaName;
	private String areaCode;
	private Set bsAreas = new HashSet(0);
	private Set bsSchools = new HashSet(0);

	// Constructors

	/** default constructor */
	public BsArea() {
	}

	/** full constructor */
	public BsArea(BsArea bsArea, String areaName, Set bsAreas, Set bsSchools) {
		this.bsArea = bsArea;
		this.areaName = areaName;
		this.bsAreas = bsAreas;
		this.bsSchools = bsSchools;
	}

	// Property accessors

	public Integer getAreaId() {
		return this.areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public BsArea getBsArea() {
		return this.bsArea;
	}

	public void setBsArea(BsArea bsArea) {
		this.bsArea = bsArea;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public Set getBsAreas() {
		return this.bsAreas;
	}

	public void setBsAreas(Set bsAreas) {
		this.bsAreas = bsAreas;
	}

	public Set getBsSchools() {
		return this.bsSchools;
	}

	public void setBsSchools(Set bsSchools) {
		this.bsSchools = bsSchools;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

}