package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.kmzyc.b2b.model.PromotionInfo;

@Deprecated
public class ShopcartTotal implements Serializable {

  private static final long serialVersionUID = -5160506048258610768L;

  /** 总的产品数量 */
  private int productCount = 0;

  /** 选中的产品数量 */
  private int checkedProductCount = 0;

  /** 原始总价格 （商品无任何活动的价格总和） */
  private BigDecimal orgMoney = BigDecimal.ZERO;

  /** 总积分 */
  private int scoreCount = 0;

  /** 优惠券总金额 */
  private BigDecimal couponMoney = BigDecimal.ZERO;

  /** 总减免金额 */
  private BigDecimal reductionMoney = BigDecimal.ZERO;

  /** 加价购金额 */
  private BigDecimal additionalMoney = BigDecimal.ZERO;

  /** 计算特价打折后的商品金额总和 */
  private BigDecimal computedMoney = BigDecimal.ZERO;

  /** 最终购物车应付金额 */
  private BigDecimal lastAllMoney = BigDecimal.ZERO;

  /** 总重量 */
  private BigDecimal weight = BigDecimal.ZERO;
  /** 当前时间有效的活动 */
  private List<PromotionInfo> promotionInfoList;
  /**
   * 总体优惠金额 包括特价打折和满减
   */
  private BigDecimal privilegeData = BigDecimal.ZERO;

  /** 总的产品数量 */
  public int getProductCount() {
    return productCount;
  }

  /** 总的产品数量 */
  public void setProductCount(int productCount) {
    this.productCount = productCount;
  }

  /** 选中的产品数量 */
  public int getCheckedProductCount() {
    return checkedProductCount;
  }

  /** 选中的产品数量 */
  public void setCheckedProductCount(int checkedProductCount) {
    this.checkedProductCount = checkedProductCount;
  }

  /** 原始总价格 （商品无任何活动的价格总和） */
  public BigDecimal getOrgMoney() {
    return orgMoney;
  }

  /** 原始总价格 （商品无任何活动的价格总和） */
  public void setOrgMoney(BigDecimal orgMoney) {
    this.orgMoney = orgMoney;
  }

  /** 积分 */
  public int getScoreCount() {
    return scoreCount;
  }

  /** 积分 */
  public void setScoreCount(int scoreCount) {
    this.scoreCount = scoreCount;
  }

  /** 优惠券总金额 */
  public BigDecimal getCouponMoney() {
    return couponMoney;
  }

  /** 优惠券总金额 */
  public void setCouponMoney(BigDecimal couponMoney) {
    this.couponMoney = couponMoney;
  }

  /** 总减免金额 */
  public BigDecimal getReductionMoney() {
    return reductionMoney;
  }

  /** 总减免金额 */
  public void setReductionMoney(BigDecimal reductionMoney) {
    this.reductionMoney = reductionMoney;
  }

  /** 加价购金额 */
  public BigDecimal getAdditionalMoney() {
    return additionalMoney;
  }

  /** 加价购金额 */
  public void setAdditionalMoney(BigDecimal additionalMoney) {
    this.additionalMoney = additionalMoney;
  }

  /** 计算特价打折后的商品金额总和 */
  public BigDecimal getComputedMoney() {
    return computedMoney;
  }

  /** 计算特价打折后的商品金额总和 */
  public void setComputedMoney(BigDecimal computedMoney) {
    this.computedMoney = computedMoney;
  }

  /** 最终购物车应付金额 */
  public BigDecimal getLastAllMoney() {
    return lastAllMoney;
  }

  /** 最终购物车应付金额 */
  public void setLastAllMoney(BigDecimal lastAllMoney) {
    this.lastAllMoney = lastAllMoney;
  }

  /** 总重 */
  public BigDecimal getWeight() {
    return weight;
  }

  /** 总重 */
  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  /**
   * 总体优惠金额 包括特价打折和满减
   */
  public BigDecimal getPrivilegeData() {
    return privilegeData;
  }

  /**
   * 总体优惠金额 包括特价打折和满减
   */
  public void setPrivilegeData(BigDecimal privilegeData) {
    this.privilegeData = privilegeData;
  }

  /** 当前时间有效的活动 */
  public List<PromotionInfo> getPromotionInfoList() {
    return promotionInfoList;
  }

  /** 当前时间有效的活动 */
  public void setPromotionInfoList(List<PromotionInfo> promotionInfoList) {
    this.promotionInfoList = promotionInfoList;
  }

}
