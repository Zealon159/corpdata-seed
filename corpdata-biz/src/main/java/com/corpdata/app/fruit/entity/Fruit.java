package com.corpdata.app.fruit.entity;

import com.corpdata.core.base.BaseEntity;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 
 * 
 * @author zealon
 * @date 2018-06-05 22:20:22
 * 
 */
public class Fruit extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String fruitName;
	private BigDecimal fruitPrice;

	public void setFruitName(String fruitName) {
		this.fruitName = fruitName;
	}

	public String getFruitName() {
		return fruitName;
	}
	public void setFruitPrice(BigDecimal fruitPrice) {
		this.fruitPrice = fruitPrice;
	}

	public BigDecimal getFruitPrice() {
		return fruitPrice;
	}
}
