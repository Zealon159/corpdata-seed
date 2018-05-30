package com.corpdata.system.org.entity;

import java.io.Serializable;

public class OrgUserRole implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
    private String id;

    private OrgRole orgRole;

    private String userid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

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