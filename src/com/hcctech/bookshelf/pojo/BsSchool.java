package com.hcctech.bookshelf.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * BsSchool entity. @author MyEclipse Persistence Tools
 */

public class BsSchool implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -6196438513243604138L;
	private Integer schoolId;
	private BsArea bsArea;
	private String schoolName;
	private Set bsLicenseKeies = new HashSet(0);

	// Constructors

	/** default constructor */
	public BsSchool() {
	}

	/** full constructor */
	public BsSchool(BsArea bsArea, String schoolName, Set bsLicenseKeies) {
		this.bsArea = bsArea;
		this.schoolName = schoolName;
		this.bsLicenseKeies = bsLicenseKeies;
	}

	// Property accessors

	public Integer getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public BsArea getBsArea() {
		return this.bsArea;
	}

	public void setBsArea(BsArea bsArea) {
		this.bsArea = bsArea;
	}

	public String getSchoolName() {
		return this.schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Set getBsLicenseKeies() {
		return this.bsLicenseKeies;
	}

	public void setBsLicenseKeies(Set bsLicenseKeies) {
		this.bsLicenseKeies = bsLicenseKeies;
	}

}