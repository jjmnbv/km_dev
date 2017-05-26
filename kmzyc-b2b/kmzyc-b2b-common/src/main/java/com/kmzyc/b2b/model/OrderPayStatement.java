package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.kmzyc.framework.exception.ObjectTransformException;

public class OrderPayStatement implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long payStatementNo;

  private Long paymentWay;

  private Long state;

  private String account;

  private String orderCode;

  private BigDecimal orderMoney;

  private Date createDate;

  private Date endDate;

  private String outsidePayStatementNo;

  private Long flag;

  private String bankCode;

  private String bankName;

  private String platformCode;

  private String platformName;

  private BigDecimal preferentialNo;

  private String preferentialName;

  private BigDecimal preferentialGrantId;
  /**
   * 预售支付金额标识  1：支付定金   2：支付尾款
   */
  private String ysFlage;
  /**
   * 支付信息
   */
  private String payInfo;

  public com.pltfm.app.entities.OrderPayStatement transFormToRemoteOrderPayStatement()
      throws ObjectTransformException {
    com.pltfm.app.entities.OrderPayStatement orderPayStatement =
        new com.pltfm.app.entities.OrderPayStatement();
    // try {
    orderPayStatement.setPlatFormCode(platformCode);
    orderPayStatement.setPlatFormName(platformName);
    orderPayStatement.setPaymentWay(paymentWay);
    orderPayStatement.setState(state);
    orderPayStatement.setAccount(account);
    orderPayStatement.setOrderCode(orderCode);
    orderPayStatement.setOrderMoney(orderMoney);
    orderPayStatement.setCreateDate(createDate);
    orderPayStatement.setEndDate(endDate);
    orderPayStatement.setOutsidePayStatementNo(outsidePayStatementNo);
    orderPayStatement.setFlag(flag);
    orderPayStatement.setBankCode(bankCode);
    orderPayStatement.setBankName(bankName);
    orderPayStatement.setPreferentialNo(preferentialNo);
    orderPayStatement.setPreferentialName(preferentialName);
    orderPayStatement.setPreferentialGrantId(preferentialGrantId);
    orderPayStatement.setYsFlage(ysFlage);
    orderPayStatement.setPayInfo(payInfo);
    // 转换名称相同的属性
    // BeanUtilExpansionClass.copyProperties(orderPayStatement, this);
    /*
     * } catch (IllegalAccessException e) { logger.error("将本地OrderPayStatement对象转换为远程对象出错：" +
     * e.getMessage(), e); throw new ObjectTransformException("将本地OrderPayStatement对象转换为远程对象出错！"); }
     * catch (InvocationTargetException e) { logger.error("将本地OrderPayStatement对象转换为远程对象出错：" +
     * e.getMessage(), e); throw new ObjectTransformException("将本地OrderPayStatement对象转换为远程对象出错！"); }
     */

    return orderPayStatement;
  }

  public Long getPayStatementNo() {
    return payStatementNo;
  }

  public void setPayStatementNo(Long payStatementNo) {
    this.payStatementNo = payStatementNo;
  }

  public Long getPaymentWay() {
    return paymentWay;
  }

  public void setPaymentWay(Long paymentWay) {
    this.paymentWay = paymentWay;
  }

  public Long getState() {
    return state;
  }

  public void setState(Long state) {
    this.state = state;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public BigDecimal getOrderMoney() {
    return orderMoney;
  }

  public void setOrderMoney(BigDecimal orderMoney) {
    this.orderMoney = orderMoney;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public String getOutsidePayStatementNo() {
    return outsidePayStatementNo;
  }

  public void setOutsidePayStatementNo(String outsidePayStatementNo) {
    this.outsidePayStatementNo = outsidePayStatementNo;
  }

  public Long getFlag() {
    return flag;
  }

  public void setFlag(Long flag) {
    this.flag = flag;
  }

  public BigDecimal getPreferentialNo() {
    return preferentialNo;
  }

  public void setPreferentialNo(BigDecimal preferentialNo) {
    this.preferentialNo = preferentialNo;
  }

  public String getPreferentialName() {
    return preferentialName;
  }

  public void setPreferentialName(String preferentialName) {
    this.preferentialName = preferentialName;
  }

  public String getBankCode() {
    return bankCode;
  }

  public void setBankCode(String bankCode) {
    this.bankCode = bankCode;
  }

  public String getBankName() {
    return bankName;
  }

  public void setBankName(String bankName) {
    this.bankName = bankName;
  }

  public String getPlatformCode() {
    return platformCode;
  }

  public void setPlatformCode(String platformCode) {
    this.platformCode = platformCode;
  }

  public String getPlatformName() {
    return platformName;
  }

  public void setPlatformName(String platformName) {
    this.platformName = platformName;
  }

  public BigDecimal getPreferentialGrantId() {
    return preferentialGrantId;
  }

  public void setPreferentialGrantId(BigDecimal preferentialGrantId) {
    this.preferentialGrantId = preferentialGrantId;
  }
  
  public String getYsFlage() {
    return ysFlage;
  }

  public void setYsFlage(String ysFlage) {
    this.ysFlage = ysFlage;
  }

  public String getPayInfo() {
    return payInfo;
  }

  public void setPayInfo(String payInfo) {
    this.payInfo = payInfo;
  }
}
