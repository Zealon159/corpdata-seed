package com.corpdata.system.org.entity;

import java.io.Serializable;

public class OrgRolePermission implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
    private OrgRole role;
    private OrgPermission permission;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public OrgRole getRole() {
		return role;
	}
	public void setRole(OrgRole role) {
		this.role = role;
	}
	public OrgPermission getPermission() {
		return permission;
	}
	public void setPermission(OrgPermission permission) {
		this.permission = permission;
	}
    
}