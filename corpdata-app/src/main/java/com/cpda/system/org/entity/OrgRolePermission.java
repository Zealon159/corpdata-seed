package com.cpda.system.org.entity;

import com.cpda.system.menu.entity.SysMenu;

import java.io.Serializable;

/**
 * 角色权限映射
 */
public class OrgRolePermission implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
    private OrgRole role;
    private SysMenu permission;

	public OrgRolePermission(){

	}

	public OrgRolePermission(OrgRole role, SysMenu permission) {
		this.role = role;
		this.permission = permission;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public OrgRole getRole() {
		return role;
	}
	public void setRole(OrgRole role) {
		this.role = role;
	}
	public SysMenu getPermission() {
		return permission;
	}
	public void setPermission(SysMenu permission) {
		this.permission = permission;
	}
    
}