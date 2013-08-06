package com.hcctech.bookshelf.util;

public class TreeJson {
	
	private int id;
	
	private String name;
	
	private Integer _parentId;
	
	private String state;
	
	private String iconCls;
	
	private String areaCode;
	
	public String getState() {
		if(this.state == null || "".equals(this.state)){
			return "closed";
		}else{
			return state;
		}
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer get_parentId() {
		return _parentId;
	}
	public void set_parentId(Integer _parentId) {
		this._parentId = _parentId;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getAreaCode() {
		return areaCode;
	}
	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}
}
