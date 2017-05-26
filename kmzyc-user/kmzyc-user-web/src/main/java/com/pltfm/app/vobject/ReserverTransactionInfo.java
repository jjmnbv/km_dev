package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ReserverTransactionInfo implements Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -5572434991768160509L;
  // 预备金交易记录Id
  private BigDecimal transationId;
  // 预备金账户id
  private BigDecimal reserveId;
  // 流水id
  private BigDecimal accountTransactionpId;
  // 交易前金额
  private BigDecimal beforeAmount;
  // 交易后金额
  private BigDecimal afterAmount;
  // 交易金额(正数支出，负数收入)
  private BigDecimal payAmount;
  // 交易日期
  private Date payDate;
  // 交易人
  private BigDecimal createId;

  public BigDecimal getTransationId() {
    return transationId;
  }

  public void setTransationId(BigDecimal transationId) {
    this.transationId = transationId;
  }

  public BigDecimal getReserveId() {
    return reserveId;
  }

  public void setReserveId(BigDecimal reserveId) {
    this.reserveId = reserveId;
  }

  public BigDecimal getAccountTransactionpId() {
    return accountTransactionpId;
  }

  public void setAccountTransactionpId(BigDecimal accountTransactionpId) {
    this.accountTransactionpId = accountTransactionpId;
  }

  public BigDecimal getBeforeAmount() {
    return beforeAmount;
  }

  public void setBeforeAmount(BigDecimal beforeAmount) {
    this.beforeAmount = beforeAmount;
  }

  public BigDecimal getAfterAmount() {
    return afterAmount;
  }

  public void setAfterAmount(BigDecimal afterAmount) {
    this.afterAmount = afterAmount;
  }

  public BigDecimal getPayAmount() {
    return payAmount;
  }

  public void setPayAmount(BigDecimal payAmount) {
    this.payAmount = payAmount;
  }

  public Date getPayDate() {
    return payDate;
  }

  public void setPayDate(Date payDate) {
    this.payDate = payDate;
  }

  public BigDecimal getCreateId() {
    return createId;
  }

  public void setCreateId(BigDecimal createId) {
    this.createId = createId;
  }

  @Override
  public String toString() {
    return "ReserverTransactionInfo [transationId=" + transationId + ", reserveId=" + reserveId
        + ", accountTransactionpId=" + accountTransactionpId + ", beforeAmount=" + beforeAmount
        + ", afterAmount=" + afterAmount + ", payAmount=" + payAmount + ", payDate=" + payDate
        + ", createId=" + createId + "]";
  }


}
