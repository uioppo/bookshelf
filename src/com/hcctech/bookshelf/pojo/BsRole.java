package com.hcctech.bookshelf.pojo;

import java.util.HashSet;
import java.util.Set;

/**
 * BsRole entity. @author MyEclipse Persistence Tools
 */

public class BsRole implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 3072516584301586957L;
	private Integer roleId;
	private String roleName;
	private String description;
	private Set bsFunctions = new HashSet(0);
	private Set bsAdminUsers = new HashSet(0);

	// Constructors

	/** default constructor */
	public BsRole() {
	}

	public BsRole(Integer roleId) {
		this.roleId = roleId;
	}
	
	/** full constructor */
	public BsRole(String roleName, String description, Set bsFunctions,
			Set bsAdminUsers) {
		this.roleName = roleName;
		this.description = description;
		this.bsFunctions = bsFunctions;
		this.bsAdminUsers = bsAdminUsers;
	}

	// Property accessors

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set getBsFunctions() {
		return this.bsFunctions;
	}

	public void setBsFunctions(Set bsFunctions) {
		this.bsFunctions = bsFunctions;
	}

	public Set getBsAdminUsers() {
		return this.bsAdminUsers;
	}

	public void setBsAdminUsers(Set bsAdminUsers) {
		this.bsAdminUsers = bsAdminUsers;
	}

}