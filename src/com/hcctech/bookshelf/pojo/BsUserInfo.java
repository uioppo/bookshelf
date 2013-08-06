package com.hcctech.bookshelf.pojo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * BsUserInfo entity. @author MyEclipse Persistence Tools
 */

public class BsUserInfo implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -2170068075461032401L;
	private Integer id;
	private String realName;
	private String nickName;
	private String mobile;
	private Integer age;
	private Integer sex;
	private Date birthday;
	private String school;
	private String sheng;
	private String shi;
	private String xian;
	private BsWebUser bsWebUser;
	private String birthdayStr;//出生日期的字符串

	// Constructors

	/** default constructor */
	public BsUserInfo() {
	}

	/** full constructor */
	public BsUserInfo(String realName, String nickName, String mobile,
			Integer age, Integer sex, Date birthday, String school,
			String sheng, String shi, String xian, BsWebUser bsWebUser) {
		this.realName = realName;
		this.nickName = nickName;
		this.mobile = mobile;
		this.age = age;
		this.sex = sex;
		this.birthday = birthday;
		this.school = school;
		this.sheng = sheng;
		this.shi = shi;
		this.xian = xian;
		this.bsWebUser = bsWebUser;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getSex() {
		return this.sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Date getBirthday() {
		return this.birthday;
	}

	public String getBirthday_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (null != this.birthday) {
			return sdf.format(this.birthday);
		} else {
			return "";
		}
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getSheng() {
		return this.sheng;
	}

	public void setSheng(String sheng) {
		this.sheng = sheng;
	}

	public String getShi() {
		return this.shi;
	}

	public void setShi(String shi) {
		this.shi = shi;
	}

	public String getXian() {
		return this.xian;
	}

	public void setXian(String xian) {
		this.xian = xian;
	}

	public BsWebUser getBsWebUser() {
		return bsWebUser;
	}

	public void setBsWebUser(BsWebUser bsWebUser) {
		this.bsWebUser = bsWebUser;
	}

	public String getBirthdayStr() {
		return birthdayStr;
	}

	public void setBirthdayStr(String birthdayStr) {
		this.birthdayStr = birthdayStr;
	}
	
}