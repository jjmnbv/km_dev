package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用于缓存、活动计算,存放促销信息和价格信息
 * 
 * @author hewenfeng
 * 
 */
public class ProductSkuPrice implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3178967463991504540L;
	/**
	 * 区间单价
	 */
	private BigDecimal unitPrice;
	/**
	 * 开始时间
	 */
	private Date startTime;
	/**
	 * 结束时间
	 */
	private Date endTime;

	/**
	 * 促销活动
	 */
	private PromotionInfo salePromotionInfo;
	private PromotionInfo discountPromotionInfo;
	/** 订单级别的活动 */
	private List<PromotionInfo> promotionInfoList;

	private Integer minBuy;
	private Integer maxBuy;
	private Integer promotionStock;
	private Integer salesVolume;
	private Integer restrictionType;
	/**
	 * 活动标签
	 */
	private String promotionInfoLebal;

	public PromotionInfo getSalePromotionInfo() {
		return salePromotionInfo;
	}

	public void setSalePromotionInfo(PromotionInfo salePromotionInfo) {
		this.salePromotionInfo = salePromotionInfo;
	}

	public PromotionInfo getDiscountPromotionInfo() {
		return discountPromotionInfo;
	}

	public void setDiscountPromotionInfo(PromotionInfo discountPromotionInfo) {
		this.discountPromotionInfo = discountPromotionInfo;
	}

	/** 订单级别的活动 */
	public List<PromotionInfo> getPromotionInfoList() {
		return promotionInfoList;
	}

	/** 订单级别的活动 */
	public void setPromotionInfoList(List<PromotionInfo> promotionInfoList) {
		this.promotionInfoList = promotionInfoList;
	}

	/**
	 * 活动标签
	 */
	public String getPromotionInfoLebal() {
		return promotionInfoLebal;
	}

	/**
	 * 活动标签
	 */
	public void setPromotionInfoLebal(String promotionInfoLebal) {
		this.promotionInfoLebal = promotionInfoLebal;
	}

	/**
	 * 价格说明
	 */
	private String describe;

	/**
	 * 区间最终单价
	 */
	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	/**
	 * 区间最终单价
	 */
	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	/**
	 * 开始时间
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * 开始时间
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * 终止时间
	 */
	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * 价格说明
	 */
	public String getDescribe() {
		return describe;
	}

	/**
	 * 价格说明
	 */
	public void setDescribe(String describe) {
		this.describe = describe;
	}

	/** 购买下限 */
	public Integer getMinBuy() {
		return minBuy;
	}

	/** 购买下限 */
	public void setMinBuy(Integer minBuy) {
		this.minBuy = minBuy;
	}

	/** 购买上限 */
	public Integer getMaxBuy() {
		return maxBuy;
	}

	/** 购买上限 */
	public void setMaxBuy(Integer maxBuy) {
		this.maxBuy = maxBuy;
	}

	/** 活动库存 */
	public Integer getPromotionStock() {
		return promotionStock;
	}

	/** 活动库存 */
	public void setPromotionStock(Integer promotionStock) {
		this.promotionStock = promotionStock;
	}

	/** 活动期间销售数量 */
	public Integer getSalesVolume() {
		return salesVolume;
	}

	/** 活动期间销售数量 */
	public void setSalesVolume(Integer salesVolume) {
		this.salesVolume = salesVolume;
	}

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** 限购类型0特价；1打折；2无限购 */
	public Integer getRestrictionType() {
		return restrictionType;
	}

	/** 限购类型0特价；1打折；2无限购 */
	public void setRestrictionType(Integer restrictionType) {
		this.restrictionType = restrictionType;
	}

	public String toString() {
		String strValue = "startTime:" + sdf.format(startTime) + ",endTime:" + sdf.format(endTime) + ",unitPrice:"
				+ unitPrice + ",promotionIds:";
		String ids = "";
		if (salePromotionInfo != null) {
			ids += salePromotionInfo.getPromotionId() + ",";
		}
		if (discountPromotionInfo != null) {
			ids += discountPromotionInfo.getPromotionId() + ",";
		}
		strValue += ids + ".";
		return strValue;
	}

}
