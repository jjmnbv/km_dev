package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;

public class ProductRelationAndDetail {

	private Long relationId;// 产品关联主表 ID

	private String relationName; // 产品关联名称

	private Long mainProductId;

	private Long mainSkuId; // 主skuID

	private BigDecimal mainSkuPrice; // 主产品价格

	private BigDecimal totalRelationPrice; // 关联总价

	private Short relationType; // 关联类型

	private String remark; // 备注

	private Long relationDetailId; // 产品关联子表 主键id

	private Long relationProductId;

	private Long relationSkuId; // 被关联的skuID

	private BigDecimal relationSkuPrice; // 被关联的skuID

	private Short relationDetailType; // 关联产品的类型

	public Long getRelationId() {
		return relationId;
	}

	public void setRelationId(Long relationId) {
		this.relationId = relationId;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public BigDecimal getMainSkuPrice() {
		return mainSkuPrice;
	}

	public void setMainSkuPrice(BigDecimal mainSkuPrice) {
		this.mainSkuPrice = mainSkuPrice;
	}

	public BigDecimal getTotalRelationPrice() {
		return totalRelationPrice;
	}

	public void setTotalRelationPrice(BigDecimal totalRelationPrice) {
		this.totalRelationPrice = totalRelationPrice;
	}

	public Short getRelationType() {
		return relationType;
	}

	public void setRelationType(Short relationType) {
		this.relationType = relationType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getRelationDetailId() {
		return relationDetailId;
	}

	public void setRelationDetailId(Long relationDetailId) {
		this.relationDetailId = relationDetailId;
	}

	public Long getMainSkuId() {
		return mainSkuId;
	}

	public void setMainSkuId(Long mainSkuId) {
		this.mainSkuId = mainSkuId;
	}

	public Long getRelationSkuId() {
		return relationSkuId;
	}

	public void setRelationSkuId(Long relationSkuId) {
		this.relationSkuId = relationSkuId;
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

	public Long getMainProductId() {
		return mainProductId;
	}

	public void setMainProductId(Long mainProductId) {
		this.mainProductId = mainProductId;
	}

	public Long getRelationProductId() {
		return relationProductId;
	}

	public void setRelationProductId(Long relationProductId) {
		this.relationProductId = relationProductId;
	}

}
