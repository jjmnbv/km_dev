package com.kmzyc.b2b.model;

import java.io.Serializable;

public class PayCommonObject implements Serializable {
  private static final long serialVersionUID = -7684425321409960653L;
  // 第三方支付流水号
  private String transitionNO = null;
  // 订单编码
  private String orderCode = null;
  // 交易日期
  private String payDateStr = null;
  // 交易金额
  private String moneyStr = null;
  // 交易币种
  private String currencyCode = null;
  // 支付状态码
  private String payStateCode = null;
  // 扩展信息 1:充值业务 2：订单业务
  private String extInfo = null;
  // 银行id
  private String bankId = null;
  // 银行中文名
  private String bankChineseName = null;
  // 银行订单号
  private String bankOrderId = null;
  // 交易结果通知时间
  private String trxTime = null;
  // 同步回调
  private boolean synchCall = false;
  //支付来源 wap ,pc ,app ,ys ...
  private String paySource;

  public String getTransitionNO() {
    return transitionNO;
  }

  public void setTransitionNO(String transitionNO) {
    this.transitionNO = transitionNO;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getPayDateStr() {
    return payDateStr;
  }

  public void setPayDateStr(String payDateStr) {
    this.payDateStr = payDateStr;
  }

  public String getMoneyStr() {
    return moneyStr;
  }

  public void setMoneyStr(String moneyStr) {
    this.moneyStr = moneyStr;
  }

  public String getCurrencyCode() {
    return currencyCode;
  }

  public void setCurrencyCode(String currencyCode) {
    this.currencyCode = currencyCode;
  }

  public String getPayStateCode() {
    return payStateCode;
  }

  public void setPayStateCode(String payStateCode) {
    this.payStateCode = payStateCode;
  }

  public String getExtInfo() {
    return extInfo;
  }

  public void setExtInfo(String extInfo) {
    this.extInfo = extInfo;
  }

  public String getBankId() {
    return bankId;
  }

  public void setBankId(String bankId) {
    this.bankId = bankId;
  }

  public String getBankChineseName() {
    return bankChineseName;
  }

  public void setBankChineseName(String bankChineseName) {
    this.bankChineseName = bankChineseName;
  }

  public String getBankOrderId() {
    return bankOrderId;
  }

  public void setBankOrderId(String bankOrderId) {
    this.bankOrderId = bankOrderId;
  }

  public String getTrxTime() {
    return trxTime;
  }

  public void setTrxTime(String trxTime) {
    this.trxTime = trxTime;
  }

  public boolean isSynchCall() {
    return synchCall;
  }

  public void setSynchCall(boolean synchCall) {
    this.synchCall = synchCall;
  }
  
  public String getPaySource() {
    return paySource;
  }

  public void setPaySource(String paySource) {
    this.paySource = paySource;
  }
}
