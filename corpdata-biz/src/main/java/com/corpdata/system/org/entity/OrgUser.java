package com.corpdata.system.org.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class OrgUser implements Serializable{

	private static final long serialVersionUID = 1L;

	private OrgDept orgDept;
	
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;

    private String userid;

    private String userPwd;

    private String userName;

    private String phoneNumber;

    private String emailAddress;

    private Boolean enabledState;

    private Integer sortNumber;

    private String creater;

    private Date created;

    private Date modified;

    public String getId() {
        return id;
    }

    public OrgDept getOrgDept() {
		return orgDept;
	}

	public void setOrgDept(OrgDept orgDept) {
		this.orgDept = orgDept;
	}



	public void setId(String id) {
        this.id = id == null ? null : id.trim();
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater == null ? null : creater.trim();
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}