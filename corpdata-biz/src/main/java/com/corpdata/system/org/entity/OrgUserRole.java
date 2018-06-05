package com.corpdata.system.org.entity;

import com.corpdata.core.base.BaseEntity;

public class OrgUserRole extends BaseEntity {
	
	private static final long serialVersionUID = 1L;

    private OrgRole orgRole;

    private String userid;

	public OrgRole getOrgRole() {
		return orgRole;
	}

	public void setOrgRole(OrgRole orgRole) {
		this.orgRole = orgRole;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

}