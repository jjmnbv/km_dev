package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.model.PromotionInfo;

public class ProductPromotionInfo implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  /**
   * 所有满足的活动
   */
  private Map<Long, PromotionInfo> allMeetPromotionInfoMap;
  /**
   * 不满足条件的活动
   */
  private List<PromotionInfo> missPromotions = new ArrayList<PromotionInfo>();

  /**
   * 满足条件的活动
   */
  private List<PromotionInfo> meetPromotionInfos = new ArrayList<PromotionInfo>();

  /**
   * 生效的满送活动优惠券金额总和
   */
  private BigDecimal couponMoney;
  /**
   * 生效的满减金额
   */
  private BigDecimal reductionMoney;

  private Map<Long, PromotionInfo> allPromotionInfoMap;

  /** key为活动ID 赠品和加价购商品列表 */
  private Map<Long, Map<Long, CarProduct>> giftProductMap;

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
  }

  public void addMissPromotionInfo(PromotionInfo info) {
    if (missPromotions == null) {
      missPromotions = new ArrayList<PromotionInfo>();
    }
    missPromotions.add(info);
  }

  /**
   * 生效的满减金额总和
   */
  public BigDecimal getReductionMoney() {
    return reductionMoney;
  }

  /**
   * 生效的满减金额总和
   */
  public void setReductionMoney(BigDecimal reductionMoney) {
    this.reductionMoney = reductionMoney;
  }

  public ProductPromotionInfo() {
    reductionMoney = BigDecimal.ZERO;
    couponMoney = BigDecimal.ZERO;
  }

  /**
   * 获取所有有效活动
   * 
   * @return
   */
  public Map<Long, PromotionInfo> getAllMeetPromotionInfoMap() {
    if (allMeetPromotionInfoMap == null) {
      allMeetPromotionInfoMap = new LinkedHashMap<Long, PromotionInfo>();
    }
    return allMeetPromotionInfoMap;
  }

  /**
   * 获取所有有效活动
   * 
   * @return
   */
  public void setAllMeetPromotionInfoMap(Map<Long, PromotionInfo> allMeetPromotionInfoMap) {
    this.allMeetPromotionInfoMap = allMeetPromotionInfoMap;
  }

  public void addAllMeetPromotionInfo(PromotionInfo info) {
    if (info == null) return;
    this.getAllMeetPromotionInfoMap().put(info.getPromotionId(), info);
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

  public Map<Long, Map<Long, CarProduct>> getGiftProductMap() {
    if (giftProductMap == null) {
      giftProductMap = new LinkedHashMap<Long, Map<Long, CarProduct>>();
    }
    return giftProductMap;
  }

  public void setGiftProductMap(Map<Long, Map<Long, CarProduct>> giftProductMap) {
    this.giftProductMap = giftProductMap;
  }

}
