package com.corpdata.system.org.entity;

import com.corpdata.core.base.BaseEntity;

public class OrgRole extends BaseEntity{

	private static final long serialVersionUID = 1L;

    private String roleName;

    private String roleDesc;

    private Integer sortNumber;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName == null ? null : roleName.trim();
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc == null ? null : roleDesc.trim();
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }

}