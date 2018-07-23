package com.corpdata.app.test.entity;

import com.corpdata.core.base.BaseEntity;

/**
 * 
 * 
 * @author zealon
 * @date 2018-07-23 11:28:57
 * 
 */
public class TbAccount extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String uid;
	private Integer account;

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUid() {
		return uid;
	}
	public void setAccount(Integer account) {
		this.account = account;
	}

	public Integer getAccount() {
		return account;
	}
}
