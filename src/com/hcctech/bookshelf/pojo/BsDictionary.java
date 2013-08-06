package com.hcctech.bookshelf.pojo;

/**
 * BsDictionary entity. @author MyEclipse Persistence Tools
 */

public class BsDictionary implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -8678452900868755969L;
	private Integer id;
	private String dicCode;
	private Integer type;
	private String name;
	private Integer schoolStage;

	// Constructors

	/** default constructor */
	public BsDictionary() {
	}

	/** full constructor */
	public BsDictionary(String dicCode, Integer type, String name,
			Integer schoolStage) {
		this.dicCode = dicCode;
		this.type = type;
		this.name = name;
		this.schoolStage = schoolStage;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDicCode() {
		return this.dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSchoolStage() {
		return this.schoolStage;
	}

	public void setSchoolStage(Integer schoolStage) {
		this.schoolStage = schoolStage;
	}

}