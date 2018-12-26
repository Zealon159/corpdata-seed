package com.cpda.system.org.entity;

import com.cpda.common.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class OrgUser extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userid;
	
	private String userPwd;
	
	private String userName;
	
	private String phoneNumber;
	
	private String post;
	
	private Boolean enabledState;
	
	private Integer sortNumber;
	
	private Long fkDept;
	
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
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

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Long getFkDept() {
		return fkDept;
	}

	public void setFkDept(Long fkDept) {
		this.fkDept = fkDept;
	}

	@Override
	public String toString() {
		return "OrgUser [userid=" + userid + ", userPwd=" + userPwd + ", userName="
				+ userName + ", phoneNumber=" + phoneNumber + ", post=" + post + ", enabledState=" + enabledState
				+ ", sortNumber=" + sortNumber + ", fkDept=" + fkDept + "]";
	}

}