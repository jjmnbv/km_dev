package com.kmzyc.promotion.optimization.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 预售
 * 
 * @author Administrator
 * 
 */
public class PresellProductVO implements Serializable {
  private static final long serialVersionUID = 1L;
  // 预售ID
  private Long presellId;
  // 预售标题
  private String presellTitle;
  // 初始预售人数
  private Long initialPresellNum;
  // 限购：每人最多购买，0：不限制，大于0：限制并为限购数量
  private Long byLimit;
  // 定金支付开始时间
  private Date depositStartTime;
  // 定金支付截止时间
  private Date depositEndTime;
  // 尾款支付开始时间
  private Date finalpayStartTime;
  // 尾款支付截止时间
  private Date finalpayEndTime;
  // 发货开始时间
  private Date deliveryStartTime;
  // 发货截止时间
  private Date deliveryEndTime;
  // 产品Id
  private Long productSkuId;
  // 预售价格
  private BigDecimal presellPrice;
  // 定金
  private BigDecimal depositPrice;
  // 尾款
  private BigDecimal finalPrice;
  // 预售库存
  private Long prsellStock;
  // 预售可用库存
  private Long availableQuantity;

  public Long getPresellId() {
    return presellId;
  }

  public void setPresellId(Long presellId) {
    this.presellId = presellId;
  }

  public String getPresellTitle() {
    return presellTitle;
  }

  public void setPresellTitle(String presellTitle) {
    this.presellTitle = presellTitle;
  }

  public Long getInitialPresellNum() {
    return initialPresellNum;
  }

  public void setInitialPresellNum(Long initialPresellNum) {
    this.initialPresellNum = initialPresellNum;
  }

  public Long getByLimit() {
    return byLimit;
  }

  public void setByLimit(Long byLimit) {
    this.byLimit = byLimit;
  }

  public Date getDepositStartTime() {
    return depositStartTime;
  }

  public void setDepositStartTime(Date depositStartTime) {
    this.depositStartTime = depositStartTime;
  }

  public Date getDepositEndTime() {
    return depositEndTime;
  }

  public void setDepositEndTime(Date depositEndTime) {
    this.depositEndTime = depositEndTime;
  }

  public Date getFinalpayStartTime() {
    return finalpayStartTime;
  }

  public void setFinalpayStartTime(Date finalpayStartTime) {
    this.finalpayStartTime = finalpayStartTime;
  }

  public Date getFinalpayEndTime() {
    return finalpayEndTime;
  }

  public void setFinalpayEndTime(Date finalpayEndTime) {
    this.finalpayEndTime = finalpayEndTime;
  }

  public Date getDeliveryStartTime() {
    return deliveryStartTime;
  }

  public void setDeliveryStartTime(Date deliveryStartTime) {
    this.deliveryStartTime = deliveryStartTime;
  }

  public Date getDeliveryEndTime() {
    return deliveryEndTime;
  }

  public void setDeliveryEndTime(Date deliveryEndTime) {
    this.deliveryEndTime = deliveryEndTime;
  }

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public BigDecimal getPresellPrice() {
    return presellPrice;
  }

  public void setPresellPrice(BigDecimal presellPrice) {
    this.presellPrice = presellPrice;
  }

  public BigDecimal getDepositPrice() {
    return depositPrice;
  }

  public void setDepositPrice(BigDecimal depositPrice) {
    this.depositPrice = depositPrice;
  }

  public BigDecimal getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
  }

  public Long getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(Long availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

  public Long getPrsellStock() {
    return prsellStock;
  }

  public void setPrsellStock(Long prsellStock) {
    this.prsellStock = prsellStock;
  }

}
