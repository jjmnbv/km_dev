package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;

/**
 * 产品价格
 * 
 * @author xlg
 * 
 */
public class ProductPrice {
	private Long skuId;
	/**
	 * 建议零售价
	 */
	private BigDecimal suggestPrice;
	/**
	 * 原价
	 */
	private BigDecimal orgPrice;
	/**
	 * 全国价/基础价
	 */
	private BigDecimal basicPrice;
	/**
	 * 地区价
	 */
	private BigDecimal areaPrice;
	/**
	 * 协议价
	 */
	private BigDecimal agreePrice;
	/**
	 * 协议折扣
	 */
	private Integer agreeDisc;
	/**
	 * 促销价
	 */
	private BigDecimal purchasePrice;
	/**
	 * 促销折扣
	 */
	private Integer purchaseDisc;
	/**
	 * 最终价格
	 */
	private BigDecimal finalPrice;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(BigDecimal suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

	public BigDecimal getOrgPrice() {
		return orgPrice;
	}

	public void setOrgPrice(BigDecimal orgPrice) {
		this.orgPrice = orgPrice;
	}

	public BigDecimal getBasicPrice() {
		return basicPrice;
	}

	public void setBasicPrice(BigDecimal basicPrice) {
		this.basicPrice = basicPrice;
	}

	public BigDecimal getAreaPrice() {
		return areaPrice;
	}

	public void setAreaPrice(BigDecimal areaPrice) {
		this.areaPrice = areaPrice;
	}

	public BigDecimal getAgreePrice() {
		return agreePrice;
	}

	public void setAgreePrice(BigDecimal agreePrice) {
		this.agreePrice = agreePrice;
	}

	public Integer getAgreeDisc() {
		return agreeDisc;
	}

	public void setAgreeDisc(Integer agreeDisc) {
		this.agreeDisc = agreeDisc;
	}

	public BigDecimal getPurchasePrice() {
		return purchasePrice;
	}

	public void setPurchasePrice(BigDecimal purchasePrice) {
		this.purchasePrice = purchasePrice;
	}

	public Integer getPurchaseDisc() {
		return purchaseDisc;
	}

	public void setPurchaseDisc(Integer purchaseDisc) {
		this.purchaseDisc = purchaseDisc;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}
}