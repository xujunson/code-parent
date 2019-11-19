package com.atu.entity.sys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * The persistent class for the role_resource database table.
 * 
 */
@Entity
@Table(name="sys_role_resource")
public class RoleResource implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name="resource_id")
	private String resourceId;

	@Column(name="role_id")
	private String roleId;

	public RoleResource() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResourceId() {
		return resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	


}