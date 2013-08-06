package com.hcctech.bookshelf.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author randyjie
 * 后台角色菜单  -- tree 数据
 * 2012年11月29日 12:21:43
 */
public class MenuTree {

	private Integer id;
	private Integer code;
	private String name;
	private String url;
	private String description;
	private List<MenuTree> children;
	private String iconCls;
	private String state;
	
	public MenuTree() {
		super();
	}
	public MenuTree(Integer id,Integer code, String name, String url, String description) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.url = url;
		this.description = description;
	}
	
	public void addSubMenuTree(MenuTree menuTree){
		if(children==null){
			children = new ArrayList<MenuTree>();
		}
		children.add(menuTree);
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<MenuTree> getChildren() {
		return children;
	}
	public void setChildren(List<MenuTree> children) {
		this.children = children;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getState() {
		return children!=null?"opened":"";
	}
	public void setState(String state) {
		this.state = state;
	}
	
}
