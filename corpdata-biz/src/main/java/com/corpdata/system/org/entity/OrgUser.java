package com.corpdata.system.org.entity;

import com.corpdata.core.base.BaseEntity;

public class OrgUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private OrgDept orgDept;

    private String userid;

    private String userPwd;

    private String userName;

    private String phoneNumber;

    private String emailAddress;

    private Boolean enabledState;

    private Integer sortNumber;

    public OrgDept getOrgDept() {
		return orgDept;
	}

	public void setOrgDept(OrgDept orgDept) {
		this.orgDept = orgDept;
	}

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd == null ? null : userPwd.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber == null ? null : phoneNumber.trim();
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress == null ? null : emailAddress.trim();
    }

    public Boolean getEnabledState() {
        return enabledState;
    }

    public void setEnabledState(Boolean enabledState) {
        this.enabledState = enabledState;
    }

    public Integer getSortNumber() {
        return sortNumber;
    }

    public void setSortNumber(Integer sortNumber) {
        this.sortNumber = sortNumber;
    }
}