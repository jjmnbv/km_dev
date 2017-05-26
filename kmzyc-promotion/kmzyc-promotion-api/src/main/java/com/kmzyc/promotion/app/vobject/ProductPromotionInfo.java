package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单退款调用product系统专用
 * 
 * @author hewenfeng
 * 
 */
public class ProductPromotionInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/** 满足条件的所有活动包括特价打折满减满送 */
	private Map<Long, PromotionInfo> allMeetPromotionInfoMap;
	/** 不满足条件的满减满送活动 */
	private List<PromotionInfo> missPromotions = new ArrayList<PromotionInfo>();

	/** 满足条件的满减满送活动 */
	private List<PromotionInfo> meetPromotionInfos = new ArrayList<PromotionInfo>();

	/** key为活动ID 赠品和加价购商品列表 */
	private Map<Long, Map<Long, BaseProduct>> giftProductMap;

	private Map<Long, PromotionInfo> allPromotionInfoMap;

	/** 新的购物车商品对象 */
	private Map<String, OrderProduct> orderProductMap;

	/**
	 * 生效的满送活动优惠券金额总和
	 */
	private BigDecimal couponMoney;
	/** 生效的满减金额 */
	private BigDecimal reductionMoney;
	/**
	 * 总金额
	 * 
	 * @return
	 */
	private BigDecimal allMoney;

	/**
	 * 生效的满送活动优惠券金额总和
	 */
	public BigDecimal getCouponMoney() {
		return couponMoney;
	}

	/**
	 * 生效的满送活动优惠券金额总和
	 */
	public void setCouponMoney(BigDecimal couponMoney) {
		this.couponMoney = couponMoney;
	}

	public List<PromotionInfo> getMissPromotions() {
		return missPromotions;
	}

	public void setMissPromotions(List<PromotionInfo> missPromotions) {
		this.missPromotions = missPromotions;
	}

	/**
	 * 满足条件的满减和满送活动
	 * 
	 * @return
	 */
	public List<PromotionInfo> getMeetPromotionInfos() {
		return meetPromotionInfos;
	}

	/**
	 * 满足条件的满减和满送活动
	 * 
	 * @return
	 */
	public void setMeetPromotionInfos(List<PromotionInfo> meetPromotionInfos) {
		this.meetPromotionInfos = meetPromotionInfos;
	}

	public void addMeetPromotionInfo(PromotionInfo info) {
		if (meetPromotionInfos == null) {
			meetPromotionInfos = new ArrayList<PromotionInfo>();
		}
		meetPromotionInfos.add(info);
		setMeetPromotionInfos(meetPromotionInfos);
	}

	public void addMissPromotionInfo(PromotionInfo info) {
		if (missPromotions == null) {
			missPromotions = new ArrayList<PromotionInfo>();
		}
		missPromotions.add(info);
		setMissPromotions(missPromotions);
	}

	/** 生效的满减金额总和 */
	public BigDecimal getReductionMoney() {
		return reductionMoney;
	}

	/** 生效的满减金额总和 */
	public void setReductionMoney(BigDecimal reductionMoney) {
		this.reductionMoney = reductionMoney;
	}

	public ProductPromotionInfo() {
		reductionMoney = BigDecimal.valueOf(0);
		couponMoney = BigDecimal.valueOf(0);
		allMoney = BigDecimal.valueOf(0);
	}

	/** 满足条件的所有活动包括特价打折满减满送 */
	public Map<Long, PromotionInfo> getAllMeetPromotionInfoMap() {
		if (allMeetPromotionInfoMap == null) {
			allMeetPromotionInfoMap = new HashMap<Long, PromotionInfo>();
		}
		return allMeetPromotionInfoMap;
	}

	/** 满足条件的所有活动包括特价打折满减满送 */
	public void setAllMeetPromotionInfoMap(Map<Long, PromotionInfo> allMeetPromotionInfoMap) {
		this.allMeetPromotionInfoMap = allMeetPromotionInfoMap;
	}

	public void addAllMeetPromotionInfo(PromotionInfo info) {
		if (info == null)
			return;
		this.getAllMeetPromotionInfoMap().put(info.getPromotionId(), info);
	}

	public Map<String, OrderProduct> getOrderProductMap() {
		return orderProductMap;
	}

	public void setOrderProductMap(Map<String, OrderProduct> orderProductMap) {
		this.orderProductMap = orderProductMap;
	}

	/**
	 * 总金额
	 * 
	 * @return
	 */
	public BigDecimal getAllMoney() {
		return allMoney;
	}

	/**
	 * 总金额
	 * 
	 * @return
	 */
	public void setAllMoney(BigDecimal allMoney) {
		this.allMoney = allMoney;
	}

	/**
	 * 所有活动
	 */
	public Map<Long, PromotionInfo> getAllPromotionInfoMap() {
		if (allPromotionInfoMap == null) {
			allPromotionInfoMap = new LinkedHashMap<Long, PromotionInfo>();
		}
		return allPromotionInfoMap;
	}

	/**
	 * 所有活动
	 */
	public void setAllPromotionInfoMap(Map<Long, PromotionInfo> allPromotionInfoMap) {
		this.allPromotionInfoMap = allPromotionInfoMap;
	}

	public Map<Long, Map<Long, BaseProduct>> getGiftProductMap() {
		if (giftProductMap == null) {
			giftProductMap = new LinkedHashMap<Long, Map<Long, BaseProduct>>();
		}
		return giftProductMap;
	}

	public void setGiftProductMap(Map<Long, Map<Long, BaseProduct>> giftProductMap) {
		this.giftProductMap = giftProductMap;
	}

}
