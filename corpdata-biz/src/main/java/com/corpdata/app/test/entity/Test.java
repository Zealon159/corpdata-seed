package com.corpdata.app.test.entity;

import com.corpdata.core.base.BaseEntity;

public class Test extends BaseEntity{
	
	private static final long serialVersionUID = 1L;

	private String title;
	
	private int total;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
}
