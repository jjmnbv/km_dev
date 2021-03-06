package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 退款明细
 * 
 * @author weijl
 * 
 */
public class SettlementRefund implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -7866179427005576250L;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.SETTLEMENT_REFUND_ID
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private Long settlementRefundId;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.SETTLEMENT_NO
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private String settlementNo;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.ORDER_CODE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private String orderCode;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.ORDER_ALTER_CODE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private String orderAlterCode;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.SERVICE_TYPE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private Short serviceType;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.SKU_NO
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private String skuNo;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.PRODUCT_TITLE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private String productTitle;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.DEAL_NUMBER
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private Short dealNumber;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.REFUND_MONEY
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private BigDecimal refundMoney;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.COMMISSION_RATE
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private BigDecimal commissionRate;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.COMMISSION
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private BigDecimal commission;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.SETTLE_ACCOUNTS
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private BigDecimal settleAccounts;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SETTLEMENT_REFUND.SETTLEMENT_TIME
   * 
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  private Date settlementTime;
  
  private BigDecimal refundFare;

  /**
   * 退款返市场推广费
   */
  private BigDecimal refundCommodityPvSum;
  
  public BigDecimal getRefundCommodityPvSum() {
    return refundCommodityPvSum;
}

public void setRefundCommodityPvSum(BigDecimal refundCommodityPvSum) {
    this.refundCommodityPvSum = refundCommodityPvSum;
}

public BigDecimal getRefundFare() {
    return refundFare;
  }

  public void setRefundFare(BigDecimal refundFare) {
    this.refundFare = refundFare;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.SETTLEMENT_REFUND_ID
   * 
   * @return the value of SETTLEMENT_REFUND.SETTLEMENT_REFUND_ID
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public Long getSettlementRefundId() {
    return settlementRefundId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.SETTLEMENT_REFUND_ID
   * 
   * @param settlementRefundId the value for SETTLEMENT_REFUND.SETTLEMENT_REFUND_ID
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setSettlementRefundId(Long settlementRefundId) {
    this.settlementRefundId = settlementRefundId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.SETTLEMENT_NO
   * 
   * @return the value of SETTLEMENT_REFUND.SETTLEMENT_NO
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public String getSettlementNo() {
    return settlementNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.SETTLEMENT_NO
   * 
   * @param settlementNo the value for SETTLEMENT_REFUND.SETTLEMENT_NO
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setSettlementNo(String settlementNo) {
    this.settlementNo = settlementNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.ORDER_CODE
   * 
   * @return the value of SETTLEMENT_REFUND.ORDER_CODE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public String getOrderCode() {
    return orderCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.ORDER_CODE
   * 
   * @param orderCode the value for SETTLEMENT_REFUND.ORDER_CODE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.ORDER_ALTER_CODE
   * 
   * @return the value of SETTLEMENT_REFUND.ORDER_ALTER_CODE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public String getOrderAlterCode() {
    return orderAlterCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.ORDER_ALTER_CODE
   * 
   * @param orderAlterCode the value for SETTLEMENT_REFUND.ORDER_ALTER_CODE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setOrderAlterCode(String orderAlterCode) {
    this.orderAlterCode = orderAlterCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.SERVICE_TYPE
   * 
   * @return the value of SETTLEMENT_REFUND.SERVICE_TYPE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public Short getServiceType() {
    return serviceType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.SERVICE_TYPE
   * 
   * @param serviceType the value for SETTLEMENT_REFUND.SERVICE_TYPE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setServiceType(Short serviceType) {
    this.serviceType = serviceType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.SKU_NO
   * 
   * @return the value of SETTLEMENT_REFUND.SKU_NO
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public String getSkuNo() {
    return skuNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.SKU_NO
   * 
   * @param skuNo the value for SETTLEMENT_REFUND.SKU_NO
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setSkuNo(String skuNo) {
    this.skuNo = skuNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.PRODUCT_TITLE
   * 
   * @return the value of SETTLEMENT_REFUND.PRODUCT_TITLE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public String getProductTitle() {
    return productTitle;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.PRODUCT_TITLE
   * 
   * @param productTitle the value for SETTLEMENT_REFUND.PRODUCT_TITLE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.DEAL_NUMBER
   * 
   * @return the value of SETTLEMENT_REFUND.DEAL_NUMBER
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public Short getDealNumber() {
    return dealNumber;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.DEAL_NUMBER
   * 
   * @param dealNumber the value for SETTLEMENT_REFUND.DEAL_NUMBER
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setDealNumber(Short dealNumber) {
    this.dealNumber = dealNumber;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.REFUND_MONEY
   * 
   * @return the value of SETTLEMENT_REFUND.REFUND_MONEY
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public BigDecimal getRefundMoney() {
    return refundMoney;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.REFUND_MONEY
   * 
   * @param refundMoney the value for SETTLEMENT_REFUND.REFUND_MONEY
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setRefundMoney(BigDecimal refundMoney) {
    this.refundMoney = refundMoney;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.COMMISSION_RATE
   * 
   * @return the value of SETTLEMENT_REFUND.COMMISSION_RATE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public BigDecimal getCommissionRate() {
    return commissionRate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.COMMISSION_RATE
   * 
   * @param commissionRate the value for SETTLEMENT_REFUND.COMMISSION_RATE
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setCommissionRate(BigDecimal commissionRate) {
    this.commissionRate = commissionRate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.COMMISSION
   * 
   * @return the value of SETTLEMENT_REFUND.COMMISSION
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public BigDecimal getCommission() {
    return commission;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.COMMISSION
   * 
   * @param commission the value for SETTLEMENT_REFUND.COMMISSION
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setCommission(BigDecimal commission) {
    this.commission = commission;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.SETTLE_ACCOUNTS
   * 
   * @return the value of SETTLEMENT_REFUND.SETTLE_ACCOUNTS
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public BigDecimal getSettleAccounts() {
    return settleAccounts;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.SETTLE_ACCOUNTS
   * 
   * @param settleAccounts the value for SETTLEMENT_REFUND.SETTLE_ACCOUNTS
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setSettleAccounts(BigDecimal settleAccounts) {
    this.settleAccounts = settleAccounts;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SETTLEMENT_REFUND.SETTLEMENT_TIME
   * 
   * @return the value of SETTLEMENT_REFUND.SETTLEMENT_TIME
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public Date getSettlementTime() {
    return settlementTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SETTLEMENT_REFUND.SETTLEMENT_TIME
   * 
   * @param settlementTime the value for SETTLEMENT_REFUND.SETTLEMENT_TIME
   * @ibatorgenerated Wed Apr 15 10:27:20 CST 2015
   */
  public void setSettlementTime(Date settlementTime) {
    this.settlementTime = settlementTime;
  }
}
