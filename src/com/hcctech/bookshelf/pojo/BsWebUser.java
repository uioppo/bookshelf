package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

/**
 * BsWebUser entity. @author MyEclipse Persistence Tools
 */

public class BsWebUser implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -5420538345482751569L;
	private Integer wuId;
	private BsUserInfo bsUserInfo;
	private String wuEmail;
	private String wuUserName;
	private String wuPassword;
	private Integer wuActivestatus;
	private String wuToken;
	private Timestamp wuTime;
	private Timestamp wuRegTime;
	private Timestamp lastLogin;
	private Set bsUserDrms = new HashSet(0);
	private Set bsLicenseKeies = new HashSet(0);
	private Set bsOrders = new HashSet(0);
	private Set bsMybooks = new HashSet(0);

	// Constructors

	/** default constructor */
	public BsWebUser() {
	}

	/** minimal constructor */
	public BsWebUser(String wuEmail, String wuPassword) {
		this.wuEmail = wuEmail;
		this.wuPassword = wuPassword;
	}

	public BsWebUser(Integer wuId ,String wuEmail, Integer wuActivestatus, Object wuTime) {
		this.wuId = wuId ;
		this.wuEmail = wuEmail;
		this.wuActivestatus = wuActivestatus;
		if(wuTime != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				this.wuTime = new Timestamp(sdf.parse(wuTime.toString()).getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/** full constructor */
	public BsWebUser(BsUserInfo bsUserInfo, String wuEmail, String wuPassword,
			Integer wuActivestatus, String wuToken, Timestamp wuTime,
			Timestamp wuRegTime, Set bsUserDrms, Set bsLicenseKeies,
			Set bsOrders, Set bsMybooks) {
		this.bsUserInfo = bsUserInfo;
		this.wuEmail = wuEmail;
		this.wuPassword = wuPassword;
		this.wuActivestatus = wuActivestatus;
		this.wuToken = wuToken;
		this.wuTime = wuTime;
		this.wuRegTime = wuRegTime;
		this.bsUserDrms = bsUserDrms;
		this.bsLicenseKeies = bsLicenseKeies;
		this.bsOrders = bsOrders;
		this.bsMybooks = bsMybooks;
	}

	// Property accessors

	public Integer getWuId() {
		return this.wuId;
	}

	public void setWuId(Integer wuId) {
		this.wuId = wuId;
	}

	public BsUserInfo getBsUserInfo() {
		return this.bsUserInfo;
	}

	public void setBsUserInfo(BsUserInfo bsUserInfo) {
		this.bsUserInfo = bsUserInfo;
	}

	public String getWuEmail() {
		return this.wuEmail;
	}

	public void setWuEmail(String wuEmail) {
		this.wuEmail = wuEmail;
	}

	public String getWuPassword() {
		return this.wuPassword;
	}

	public void setWuPassword(String wuPassword) {
		this.wuPassword = wuPassword;
	}

	public Integer getWuActivestatus() {
		return this.wuActivestatus;
	}

	public void setWuActivestatus(Integer wuActivestatus) {
		this.wuActivestatus = wuActivestatus;
	}

	public String getWuToken() {
		return this.wuToken;
	}

	public void setWuToken(String wuToken) {
		this.wuToken = wuToken;
	}

	public Timestamp getWuTime() {
		return this.wuTime;
	}

	public void setWuTime(Timestamp wuTime) {
		this.wuTime = wuTime;
	}

	public Timestamp getWuRegTime() {
		return this.wuRegTime;
	}
	public String getWuRegTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(null!=this.wuRegTime){
			return sdf.format(this.wuRegTime);
		}else{
			return "";
		}
	}

	public void setWuRegTime(Timestamp wuRegTime) {
		this.wuRegTime = wuRegTime;
	}

	public Set getBsUserDrms() {
		return this.bsUserDrms;
	}

	public void setBsUserDrms(Set bsUserDrms) {
		this.bsUserDrms = bsUserDrms;
	}

	public Set getBsLicenseKeies() {
		return this.bsLicenseKeies;
	}

	public void setBsLicenseKeies(Set bsLicenseKeies) {
		this.bsLicenseKeies = bsLicenseKeies;
	}

	public Set getBsOrders() {
		return this.bsOrders;
	}

	public void setBsOrders(Set bsOrders) {
		this.bsOrders = bsOrders;
	}

	public Set getBsMybooks() {
		return this.bsMybooks;
	}

	public void setBsMybooks(Set bsMybooks) {
		this.bsMybooks = bsMybooks;
	}

	public Timestamp getLastLogin() {
		return lastLogin;
	}
	
	public String getLastLogin_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(null!=this.lastLogin){
			return sdf.format(this.lastLogin);
		}else{
			return "";
		}
	}

	public void setLastLogin(Timestamp lastLogin) {
		this.lastLogin = lastLogin;
	}

	public String getWuUserName() {
		return wuUserName;
	}

	public void setWuUserName(String wuUserName) {
		this.wuUserName = wuUserName;
	}
	
}