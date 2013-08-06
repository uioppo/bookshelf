package com.hcctech.bookshelf.pojo;

import java.text.DecimalFormat;

/**
 * BsOrderProduct entity. @author MyEclipse Persistence Tools
 */

public class BsOrderProduct implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 308156364468723208L;
	private Integer opId;
	private BsProducts bsProducts;
	private BsOrder bsOrder;
	private Double buyPrice;
	private Double privileged;
	private Integer buyCount;

	// Constructors

	/** default constructor */
	public BsOrderProduct() {
	}

	/** minimal constructor */
	public BsOrderProduct(BsProducts bsProducts, BsOrder bsOrder) {
		this.bsProducts = bsProducts;
		this.bsOrder = bsOrder;
	}

	/** full constructor */
	public BsOrderProduct(BsProducts bsProducts, BsOrder bsOrder,
			Double buyPrice, Double privileged,Integer buyCount) {
		this.bsProducts = bsProducts;
		this.bsOrder = bsOrder;
		this.buyPrice = buyPrice;
		this.privileged = privileged;
		this.buyCount = buyCount;
	}

	// Property accessors

	public Integer getOpId() {
		return this.opId;
	}

	public void setOpId(Integer opId) {
		this.opId = opId;
	}

	public BsProducts getBsProducts() {
		return this.bsProducts;
	}

	public void setBsProducts(BsProducts bsProducts) {
		this.bsProducts = bsProducts;
	}

	public BsOrder getBsOrder() {
		return this.bsOrder;
	}

	public void setBsOrder(BsOrder bsOrder) {
		this.bsOrder = bsOrder;
	}

	public Double getBuyPrice() {
		return this.buyPrice;
	}
	
	public String getBuyPrice_() {
		if(null!=this.buyPrice){
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(this.buyPrice);
		}else{
			return "";
		}
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Double getPrivileged() {
		return this.privileged;
	}
	
	public String getPrivileged_() {
		if(null!=this.privileged){
			if(privileged == 0) {
				return "0.00";
			}
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(this.privileged);
		}else{
			return "";
		}
	}

	public void setPrivileged(Double privileged) {
		this.privileged = privileged;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	
}