package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class PayMoneyPresell implements Serializable {
  private static final long serialVersionUID = 6241297008873181483L;

  private Long presellId;// 预售ID

  private BigDecimal depositPrice; // 预售订金（单价）
  private BigDecimal finalPrice;// 预售尾款（单价）

  private BigDecimal depositTotalPrice; // 预售订金 = 购买数量*预售订金（单价）
  private BigDecimal finalTotalPrice;// 预售尾款 = 购买数量 * 预售尾款（单价）

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

  public BigDecimal getDepositTotalPrice() {
    return depositTotalPrice;
  }

  public void setDepositTotalPrice(BigDecimal depositTotalPrice) {
    this.depositTotalPrice = depositTotalPrice;
  }

  public BigDecimal getFinalTotalPrice() {
    return finalTotalPrice;
  }

  public void setFinalTotalPrice(BigDecimal finalTotalPrice) {
    this.finalTotalPrice = finalTotalPrice;
  }

  public Long getPresellId() {
    return presellId;
  }

  public void setPresellId(Long presellId) {
    this.presellId = presellId;
  }


}
