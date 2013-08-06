package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * BsSellSetting entity. @author MyEclipse Persistence Tools
 */

public class BsSellSetting implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = -374914347963619468L;
	private Integer id;
	private String saleName;
	private Integer saleType;
	private Timestamp startTime;
	private Timestamp endTime;
	private Timestamp createTime;
	private String descrption;
	private Double priceSetting;
	private Double priceLevel;
	private String operator;
	
	private Double bestPrice;//查询最优价格时用到

	// Constructors

	/** default constructor */
	public BsSellSetting() {
	}

	/** full constructor */
	public BsSellSetting(String saleName, Integer saleType,
			Timestamp startTime, Timestamp endTime, Timestamp createTime,
			String descrption, Double priceSetting, Double priceLevel,
			String operator) {
		this.saleName = saleName;
		this.saleType = saleType;
		this.startTime = startTime;
		this.endTime = endTime;
		this.createTime = createTime;
		this.descrption = descrption;
		this.priceSetting = priceSetting;
		this.priceLevel = priceLevel;
		this.operator = operator;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSaleName() {
		return this.saleName;
	}

	public void setSaleName(String saleName) {
		this.saleName = saleName;
	}

	public Integer getSaleType() {
		return this.saleType;
	}

	public void setSaleType(Integer saleType) {
		this.saleType = saleType;
	}

	public Timestamp getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Timestamp startTime) {
		this.startTime = startTime;
	}

	public Timestamp getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Timestamp endTime) {
		this.endTime = endTime;
	}

	public Timestamp getCreateTime() {
		return this.createTime;
	}
	
	public String getCreateTime_() {
		if (null != this.createTime) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(this.createTime);
		} else {
			return "";
		}
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getDescrption() {
		return this.descrption;
	}

	public void setDescrption(String descrption) {
		this.descrption = descrption;
	}

	public Double getPriceSetting() {
		return this.priceSetting;
	}

	public void setPriceSetting(Double priceSetting) {
		this.priceSetting = priceSetting;
	}

	public Double getPriceLevel() {
		return this.priceLevel;
	}

	public void setPriceLevel(Double priceLevel) {
		this.priceLevel = priceLevel;
	}

	public String getOperator() {
		return this.operator;
	}

	public String getStartTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (null != this.startTime) {
			return sdf.format(this.startTime);
		} else {
			return "";
		}
	}

	public String getEndTime_() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if (null != this.endTime) {
			return sdf.format(this.endTime);
		} else {
			return "";
		}
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Double getBestPrice() {
		return bestPrice;
	}

	public void setBestPrice(Double bestPrice) {
		this.bestPrice = bestPrice;
	}
	
}