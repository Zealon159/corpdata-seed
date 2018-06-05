package com.corpdata.system.org.entity;

import com.corpdata.core.base.BaseEntity;

public class OrgRolePermission extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
    private OrgRole role;
    private OrgPermission permission;
    
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