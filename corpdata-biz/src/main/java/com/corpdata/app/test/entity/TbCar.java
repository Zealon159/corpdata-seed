package com.corpdata.app.test.entity;

import com.corpdata.core.base.BaseEntity;

/**
 * 
 * 
 * @author zealon
 * @date 2018-07-23 11:28:57
 * 
 */
public class TbCar extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String carName;
	private Integer carPrice;
	private Integer carNumber;

	public void setCarName(String carName) {
		this.carName = carName;
	}

	public String getCarName() {
		return carName;
	}
	public void setCarPrice(Integer carPrice) {
		this.carPrice = carPrice;
	}

	public Integer getCarPrice() {
		return carPrice;
	}
	public void setCarNumber(Integer carNumber) {
		this.carNumber = carNumber;
	}

	public Integer getCarNumber() {
		return carNumber;
	}
}
