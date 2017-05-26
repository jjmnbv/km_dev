package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;

public class ProductRelationDetail {
	private Long relationDetailId;
	private Long relationId;

	private Long relationSkuId;

	private BigDecimal relationSkuPrice;

	private Short relationDetailType;

	public Long getRelationDetailId() {
		return relationDetailId;
	}

	public void setRelationDetailId(Long relationDetailId) {
		this.relationDetailId = relationDetailId;
	}

	public BigDecimal getRelationSkuPrice() {
		return relationSkuPrice;
	}

	public void setRelationSkuPrice(BigDecimal relationSkuPrice) {
		this.relationSkuPrice = relationSkuPrice;
	}

	public Short getRelationDetailType() {
		return relationDetailType;
	}

	public void setRelationDetailType(Short relationDetailType) {
		this.relationDetailType = relationDetailType;
	}

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public Long getRelationSkuId() {
		return relationSkuId;
	}

	public void setRelationSkuId(Long relationSkuId) {
		this.relationSkuId = relationSkuId;
	}

}