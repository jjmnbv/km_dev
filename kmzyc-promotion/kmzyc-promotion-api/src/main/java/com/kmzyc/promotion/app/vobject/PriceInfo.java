package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 价格实体
 * 
 * @author xlg
 * 
 */
public class PriceInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * skuID
	 */
	private Long skuId;
	/**
	 * 最终价格
	 */
	private BigDecimal finalPrice;
	/**
	 * 价格
	 */
	private BigDecimal price;
	/**
	 * 市场价
	 */
	private BigDecimal marketPrice;
	/**
	 * 地区ID
	 */
	private Long areaId;
	/**
	 * 地区价格
	 */
	private BigDecimal areaPrice;
	/**
	 * 全国价格
	 */
	private BigDecimal gobalPrice;
	/**
	 * 地区建议价格
	 */
	private BigDecimal suggestPrice;
	/**
	 * 区域信息
	 */
	private String areaInfo;
	/**
	 * 建议信息
	 */
	private String suggestInfo;
	/**
	 * 扩展信息
	 */
	private String extInfo;

	public Long getSkuId() {
		return skuId;
	}

	public void setSkuId(Long skuId) {
		this.skuId = skuId;
	}

	public BigDecimal getFinalPrice() {
		return finalPrice;
	}

	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}

	public Long getAreaId() {
		return areaId;
	}

	public void setAreaId(Long areaId) {
		this.areaId = areaId;
	}

	public BigDecimal getAreaPrice() {
		return areaPrice;
	}

	public void setAreaPrice(BigDecimal areaPrice) {
		this.areaPrice = areaPrice;
	}

	public BigDecimal getSuggestPrice() {
		return suggestPrice;
	}

	public void setSuggestPrice(BigDecimal suggestPrice) {
		this.suggestPrice = suggestPrice;
	}

	public String getAreaInfo() {
		return areaInfo;
	}

	public void setAreaInfo(String areaInfo) {
		this.areaInfo = areaInfo;
	}

	public String getSuggestInfo() {
		return suggestInfo;
	}

	public void setSuggestInfo(String suggestInfo) {
		this.suggestInfo = suggestInfo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getMarketPrice() {
		return marketPrice;
	}

	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}

	public BigDecimal getGobalPrice() {
		return gobalPrice;
	}

	public void setGobalPrice(BigDecimal gobalPrice) {
		this.gobalPrice = gobalPrice;
	}

	public String getExtInfo() {
		return extInfo;
	}

	public void setExtInfo(String extInfo) {
		this.extInfo = extInfo;
	}
}