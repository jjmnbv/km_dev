package com.kmzyc.promotion.app.bean;

import java.io.Serializable;

public class OrderMainVo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9061294369994837538L;
	private String orderCode;
	private Long status;
	private String commoditySku;
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	
	public Long getStatus() {
		return status;
	}
	public void setStatus(Long status) {
		this.status = status;
	}
	public String getCommoditySku() {
		return commoditySku;
	}
	public void setCommoditySku(String commoditySku) {
		this.commoditySku = commoditySku;
	}
	
}
