package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderPayStatement implements Cloneable, Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 17309644004015434L;

  @Override
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

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

  private BigDecimal preferentialNo;

  private String preferentialName;

  private String bankCode;

  private String bankName;

  private String platFormCode;

  private String platFormName;

  private BigDecimal preferentialGrantId;
  /**
   * 预售支付金额标识  1：支付定金   2：支付尾款
   */
  private String ysFlage;
  
  /**
   * 支付信息
   */
  private String payInfo;

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

  public String getPlatFormCode() {
    return platFormCode;
  }

  public void setPlatFormCode(String platFormCode) {
    this.platFormCode = platFormCode;
  }

  public String getPlatFormName() {
    return platFormName;
  }

  public void setPlatFormName(String platFormName) {
    this.platFormName = platFormName;
  }

  public String getPreferentialName() {
    return preferentialName;
  }

  public void setPreferentialName(String preferentialName) {
    this.preferentialName = preferentialName;
  }

  public BigDecimal getPreferentialGrantId() {
    return preferentialGrantId;
  }

  public void setPreferentialGrantId(BigDecimal preferentialGrantId) {
    this.preferentialGrantId = preferentialGrantId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.PAY_STATEMENT_NO
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.PAY_STATEMENT_NO
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Long getPayStatementNo() {
    return payStatementNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.PAY_STATEMENT_NO
   * 
   * @param payStatementNo the value for KMORDER.ORDER_PAY_STATEMENT.PAY_STATEMENT_NO
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setPayStatementNo(Long payStatementNo) {
    this.payStatementNo = payStatementNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.PAYMENT_WAY
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.PAYMENT_WAY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Long getPaymentWay() {
    return paymentWay;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.PAYMENT_WAY
   * 
   * @param paymentWay the value for KMORDER.ORDER_PAY_STATEMENT.PAYMENT_WAY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setPaymentWay(Long paymentWay) {
    this.paymentWay = paymentWay;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.STATE
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.STATE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Long getState() {
    return state;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.STATE
   * 
   * @param state the value for KMORDER.ORDER_PAY_STATEMENT.STATE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setState(Long state) {
    this.state = state;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.ACCOUNT
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.ACCOUNT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public String getAccount() {
    return account;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.ACCOUNT
   * 
   * @param account the value for KMORDER.ORDER_PAY_STATEMENT.ACCOUNT
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setAccount(String account) {
    this.account = account;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.ORDER_ID
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.ORDER_ID
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public String getOrderCode() {
    return orderCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.ORDER_ID
   * 
   * @param orderId the value for KMORDER.ORDER_PAY_STATEMENT.ORDER_ID
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.ORDER_MONEY
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.ORDER_MONEY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public BigDecimal getOrderMoney() {
    return orderMoney;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.ORDER_MONEY
   * 
   * @param orderMoney the value for KMORDER.ORDER_PAY_STATEMENT.ORDER_MONEY
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setOrderMoney(BigDecimal orderMoney) {
    this.orderMoney = orderMoney;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.CREATE_DATE
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.CREATE_DATE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.CREATE_DATE
   * 
   * @param createDate the value for KMORDER.ORDER_PAY_STATEMENT.CREATE_DATE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.END_DATE
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.END_DATE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Date getEndDate() {
    return endDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.END_DATE
   * 
   * @param endDate the value for KMORDER.ORDER_PAY_STATEMENT.END_DATE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.OUTSIDE_PAY_STATEMENT_NO
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.OUTSIDE_PAY_STATEMENT_NO
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public String getOutsidePayStatementNo() {
    return outsidePayStatementNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.OUTSIDE_PAY_STATEMENT_NO
   * 
   * @param outsidePayStatementNo the value for KMORDER.ORDER_PAY_STATEMENT.OUTSIDE_PAY_STATEMENT_NO
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setOutsidePayStatementNo(String outsidePayStatementNo) {
    this.outsidePayStatementNo = outsidePayStatementNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.FLAG
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.FLAG
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Long getFlag() {
    return flag;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.FLAG
   * 
   * @param flag the value for KMORDER.ORDER_PAY_STATEMENT.FLAG
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setFlag(Long flag) {
    this.flag = flag;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_PAY_STATEMENT.PREFERENTIAL_NO
   * 
   * @return the value of KMORDER.ORDER_PAY_STATEMENT.PREFERENTIAL_NO
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public BigDecimal getPreferentialNo() {
    return preferentialNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_PAY_STATEMENT.PREFERENTIAL_NO
   * 
   * @param preferentialNo the value for KMORDER.ORDER_PAY_STATEMENT.PREFERENTIAL_NO
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setPreferentialNo(BigDecimal preferentialNo) {
    this.preferentialNo = preferentialNo;
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
