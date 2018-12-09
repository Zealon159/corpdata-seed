package com.cpda.system.org.entity;

import com.cpda.common.base.BaseEntity;

public class OrgUser extends BaseEntity {

	private static final long serialVersionUID = 1L;

	private OrgDept orgDept;
	
	private String userid;
	
	private String userPwd;
	
	private String userName;
	
	private Integer userSex;
	
	private String phoneNumber;
	
	private String emailAddress;
	
	private String wechat;
	
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
		this.userid = userid;
	}

	public String getUserPwd() {
		return userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Integer getUserSex() {
		return userSex;
	}

	public void setUserSex(Integer userSex) {
		this.userSex = userSex;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getWechat() {
		return wechat;
	}

	public void setWechat(String wechat) {
		this.wechat = wechat;
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