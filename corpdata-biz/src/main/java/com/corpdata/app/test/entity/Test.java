package com.corpdata.app.test.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class Test {
	
	@Id
	@GeneratedValue(generator = "UUID")
	private String id;
	
	private String title;
	
	private int total;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
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
