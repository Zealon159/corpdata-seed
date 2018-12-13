package com.cpda.app.test.entity;

import com.cpda.common.base.BaseEntity;
import java.io.Serializable;

/**
 * 
 * 
 * @author zealon
 * @date 2018-12-13 15:39:31
 * 
 */
public class Test extends BaseEntity implements Serializable  {
	private static final long serialVersionUID = 1L;
	private String title;

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}
}
