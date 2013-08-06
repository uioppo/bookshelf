package com.hcctech.bookshelf.pojo;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

import org.apache.struts2.json.annotations.JSON;

/**
 * BsOrder entity. @author MyEclipse Persistence Tools
 */

public class BsOrder implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3552507126746389192L;
	private String orderId;
	private BsWebUser bsWebUser;
	private Timestamp orderTime;
	private Double orderMoney;
	private Double privileged;
	private Integer orderState;
	private Integer type;
	private Integer keyCount;
	private Set bsOrderProducts = new HashSet(0);

	// Constructors

	/** default constructor */
	public BsOrder() {
	}

	/** full constructor */
	public BsOrder(BsWebUser bsWebUser, Timestamp orderTime, Double orderMoney,
			Double privileged, Integer orderState, Integer type,Integer keyCount,
			Set bsOrderProducts) {
		this.bsWebUser = bsWebUser;
		this.orderTime = orderTime;
		this.orderMoney = orderMoney;
		this.privileged = privileged;
		this.orderState = orderState;
		this.type = type;
		this.keyCount = keyCount;
		this.bsOrderProducts = bsOrderProducts;
	}

	// Property accessors

	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@JSON(serialize=false)
	public BsWebUser getBsWebUser() {
		return this.bsWebUser;
	}
	
	public BsWebUser getBsWebUser_() {
		return this.bsWebUser;
	}
	
	public void setBsWebUser(BsWebUser bsWebUser) {
		this.bsWebUser = bsWebUser;
	}

	public Timestamp getOrderTime() {
		return this.orderTime;
	}
	
	public String getOrderTime_() {
		if(null!=this.orderTime){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return sdf.format(this.orderTime);
		}else{
			return "";
		}
	}
	
	public void setOrderTime(Timestamp orderTime) {
		this.orderTime = orderTime;
	}

	public Double getOrderMoney() {
		return this.orderMoney;
	}

	public String getOrderMoney_() {
		if(this.orderMoney!=null){
			if(orderMoney == 0.0){
				return "0.00";
			}
			DecimalFormat df = new DecimalFormat("#.00");
			return df.format(this.orderMoney);
		}else{
			return "0.00";
		}
	}
	
	public void setOrderMoney(Double orderMoney) {
		this.orderMoney = orderMoney;
	}

	public Double getPrivileged() {
		return this.privileged;
	}
	
	public String getPrivileged_() {
		if(this.privileged!=null){
			if(privileged == 0.0){
				return "0.00";
			}
			DecimalFormat df = new DecimalFormat("#.00");
			if(privileged > 0.0 && privileged < 1){
				return "0" + df.format(this.privileged);
			}
			
			return df.format(this.privileged);
		}else{
			return "";
		}
	}

	public void setPrivileged(Double privileged) {
		this.privileged = privileged;
	}

	public Integer getOrderState() {
		return this.orderState;
	}

	public void setOrderState(Integer orderState) {
		this.orderState = orderState;
	}

	public Integer getType() {
		return this.type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	
	public Integer getKeyCount() {
		return keyCount;
	}

	public void setKeyCount(Integer keyCount) {
		this.keyCount = keyCount;
	}
	@JSON(serialize=false)
	public Set getBsOrderProducts() {
		return this.bsOrderProducts;
	}

	public Set getBsOrderProducts_() {
		return this.bsOrderProducts;
	}
	
	public void setBsOrderProducts(Set bsOrderProducts) {
		this.bsOrderProducts = bsOrderProducts;
	}
	
}