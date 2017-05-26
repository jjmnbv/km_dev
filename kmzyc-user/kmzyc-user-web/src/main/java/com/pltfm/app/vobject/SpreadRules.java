package com.pltfm.app.vobject;

import java.math.BigDecimal;
import java.util.Date;

public class SpreadRules {
  // 推广规则ID
  private BigDecimal srid;
  // 推广者申请须审核,0否，1是
  private Short isApplicateAudit;
  // 推广者修改信息须审核,0否，1是
  private Short isAlterAudit;
  // 推广者申请须验证手机,0否，1是
  private Short isAppVerMobile;
  // 推广返佣结算冻结天数
  private Short amountSettleWaitDay;
  // 默认消费返利率
  private BigDecimal rebackRate;
  // 创建时间
  private Date createDate;

  public BigDecimal getSrid() {
    return srid;
  }

  public void setSrid(BigDecimal srid) {
    this.srid = srid;
  }

  public Short getIsApplicateAudit() {
    return isApplicateAudit;
  }

  public void setIsApplicateAudit(Short isApplicateAudit) {
    this.isApplicateAudit = isApplicateAudit;
  }

  public Short getIsAlterAudit() {
    return isAlterAudit;
  }

  public void setIsAlterAudit(Short isAlterAudit) {
    this.isAlterAudit = isAlterAudit;
  }

  public Short getIsAppVerMobile() {
    return isAppVerMobile;
  }

  public void setIsAppVerMobile(Short isAppVerMobile) {
    this.isAppVerMobile = isAppVerMobile;
  }

  public Short getAmountSettleWaitDay() {
    return amountSettleWaitDay;
  }

  public void setAmountSettleWaitDay(Short amountSettleWaitDay) {
    this.amountSettleWaitDay = amountSettleWaitDay;
  }

  public BigDecimal getRebackRate() {
    return rebackRate;
  }

  public void setRebackRate(BigDecimal rebackRate) {
    this.rebackRate = rebackRate;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @Override
  public String toString() {
    return "SpreadRules [srid=" + srid + ", isApplicateAudit=" + isApplicateAudit
        + ", isAlterAudit=" + isAlterAudit + ", isAppVerMobile=" + isAppVerMobile
        + ", amountSettleWaitDay=" + amountSettleWaitDay + ", rebackRate=" + rebackRate
        + ", createDate=" + createDate + "]";
  }


}
