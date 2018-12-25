package com.cpda.system.org.entity;

import com.cpda.common.base.BaseEntity;

import java.io.Serializable;
import java.util.Date;

public class OrgUser extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userid;
	
	private String userPwd;
	
	private String landLine;
	
	private String userName;
	
	private String phoneNumber;
	
	private String post;
	
	private Boolean enabledState;
	
	private Integer sortNumber;
	
	private Date entryTime;
	
	private Long fkDept;
	
	private Long fkDoor;
	
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

	public String getLandLine() {
		return landLine;
	}

	public void setLandLine(String landLine) {
		this.landLine = landLine;
	}

	public String getPost() {
		return post;
	}

	public void setPost(String post) {
		this.post = post;
	}

	public Long getFkDoor() {
		return fkDoor;
	}

	public void setFkDoor(Long fkDoor) {
		this.fkDoor = fkDoor;
	}

	public Long getFkDept() {
		return fkDept;
	}

	public void setFkDept(Long fkDept) {
		this.fkDept = fkDept;
	}

	@Override
	public String toString() {
		return "OrgUser [userid=" + userid + ", userPwd=" + userPwd + ", landLine=" + landLine + ", userName="
				+ userName + ", phoneNumber=" + phoneNumber + ", post=" + post + ", enabledState=" + enabledState
				+ ", sortNumber=" + sortNumber + ", fkDept=" + fkDept + ", fkDoor=" + fkDoor + "]";
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

}