package com.pltfm.app.fobject;

import java.io.Serializable;

public class SkuCheckAttr implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6726682004037105823L;

	private String skuCheckedId;
	
	private String status;
	
	private Double markPrice;
	
	private Double price;
	
	private Double unitWeight;
	
	private Double pvValue;
	
	private String sellerSkuCode;
	
	private Long stock;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSkuCheckedId() {
		return skuCheckedId;
	}

	public void setSkuCheckedId(String skuCheckedId) {
		this.skuCheckedId = skuCheckedId;
	}

	public Double getMarkPrice() {
		return markPrice;
	}

	public void setMarkPrice(Double markPrice) {
		this.markPrice = markPrice;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getUnitWeight() {
		return unitWeight;
	}

	public void setUnitWeight(Double unitWeight) {
		this.unitWeight = unitWeight;
	}

	public Double getPvValue() {
		return pvValue;
	}

	public void setPvValue(Double pvValue) {
		this.pvValue = pvValue;
	}

	public String getSellerSkuCode() {
		return sellerSkuCode;
	}

	public void setSellerSkuCode(String sellerSkuCode) {
		this.sellerSkuCode = sellerSkuCode;
	}

	public Long getStock() {
		return stock;
	}

	public void setStock(Long stock) {
		this.stock = stock;
	}
	
	
}
