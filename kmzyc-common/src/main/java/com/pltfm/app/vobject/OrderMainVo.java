package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.pltfm.app.entities.OrderMain;

public class OrderMainVo extends OrderMain implements Serializable {
  private static final long serialVersionUID = -2802555755186756866L;
  // 支付方式
  private String payMethodStr;
  // 配送方式
  private String deliveryDateTypeStr;
  // 订单状态
  private String orderStatusStr;
  // 评价信息的条数
  private int assessCount;
  // 根订单
  private String rootOrderCode;
  // 处理状态
  private Short handleState;
  /**
   * 预售定金
   */
  private BigDecimal depositSum;

  /**
   * 已付定金
   * 
   */
  private BigDecimal paidDeposit;


  /**
   * 预售尾款
   */
  private BigDecimal finalPayment;

  /**
   * 已支付尾款
   */
  private BigDecimal noFinalPayment;

  /**
   * 尾款支付通知手机
   */
  private String informPayTel;

  /**
   * 尾款支付截止时间
   */
  private Date finalpayEndTime;

  /**
   * 尾款支付开始时间
   */
  private Date finalpayStartTime;

  private String productSkuCode;
  /**
   * 尾款通知短信发送标识
   */
  private int messageSendFlag;
  /**
   * 订金支付完成时间
   */
  private Date depositPayFinishTime;

  // 处理人
  private String handleAccount;

  // 是否包含药品/医疗器械
  private Long isMedicOrderFlag;

  /**
   * 订单来源
   */
  private String orderSourceStr;
  /**
   * 返佣金额
   */
  private BigDecimal orderCommissionl;

  /**
   * 风控内容
   */
  private String estimateContent;


  public String getEstimateContent() {
    return estimateContent;
  }

  public void setEstimateContent(String estimateContent) {
    this.estimateContent = estimateContent;
  }

  public BigDecimal getOrderCommissionl() {
    return orderCommissionl;
  }

  public void setOrderCommissionl(BigDecimal orderCommissionl) {
    this.orderCommissionl = orderCommissionl;
  }

  public BigDecimal getDepositSum() {
    return depositSum;
  }

  public void setDepositSum(BigDecimal depositSum) {
    this.depositSum = depositSum;
  }

  public BigDecimal getPaidDeposit() {
    return paidDeposit;
  }

  public void setPaidDeposit(BigDecimal paidDeposit) {
    this.paidDeposit = paidDeposit;
  }

  public BigDecimal getFinalPayment() {
    return finalPayment;
  }

  public void setFinalPayment(BigDecimal finalPayment) {
    this.finalPayment = finalPayment;
  }

  public BigDecimal getNoFinalPayment() {
    return noFinalPayment;
  }

  public void setNoFinalPayment(BigDecimal noFinalPayment) {
    this.noFinalPayment = noFinalPayment;
  }

  public String getInformPayTel() {
    return informPayTel;
  }

  public void setInformPayTel(String informPayTel) {
    this.informPayTel = informPayTel;
  }

  public Date getFinalpayEndTime() {
    return finalpayEndTime;
  }

  public String getFinalpayEndTimeFormartYMDHMS() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
    return sdf.format(finalpayEndTime);
  }

  public void setFinalpayEndTime(Date finalpayEndTime) {
    this.finalpayEndTime = finalpayEndTime;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  public String getDeliveryDateTypeStr() {
    return deliveryDateTypeStr;
  }

  public void setDeliveryDateTypeStr(String deliveryDateTypeStr) {
    this.deliveryDateTypeStr = deliveryDateTypeStr;
  }

  public String getPayMethodStr() {
    return payMethodStr;
  }

  public void setPayMethodStr(String payMethodStr) {
    this.payMethodStr = payMethodStr;
  }


  public String getOrderStatusStr() {
    return orderStatusStr;
  }

  public void setOrderStatusStr(String orderStatusStr) {
    this.orderStatusStr = orderStatusStr;
  }

  public int getAssessCount() {
    return assessCount;
  }

  public void setAssessCount(int assessCount) {
    this.assessCount = assessCount;
  }

  public String getRootOrderCode() {
    return rootOrderCode;
  }

  public void setRootOrderCode(String rootOrderCode) {
    this.rootOrderCode = rootOrderCode;
  }

  public Date getFinalpayStartTime() {
    return finalpayStartTime;
  }

  public void setFinalpayStartTime(Date finalpayStartTime) {
    this.finalpayStartTime = finalpayStartTime;
  }

  public Short getHandleState() {
    return handleState;
  }

  public void setHandleState(Short handleState) {
    this.handleState = handleState;
  }

  public String getOrderSourceStr() {
    return orderSourceStr;
  }

  public void setOrderSourceStr(String orderSourceStr) {
    this.orderSourceStr = orderSourceStr;
  }

  public Long getIsMedicOrderFlag() {
    return isMedicOrderFlag;
  }

  public void setIsMedicOrderFlag(Long isMedicOrderFlag) {
    this.isMedicOrderFlag = isMedicOrderFlag;
  }

  public String getHandleAccount() {
    return handleAccount;
  }

  public void setHandleAccount(String handleAccount) {
    this.handleAccount = handleAccount;
  }

  public Date getDepositPayFinishTime() {
    return depositPayFinishTime;
  }

  public void setDepositPayFinishTime(Date depositPayFinishTime) {
    this.depositPayFinishTime = depositPayFinishTime;
  }

  public String getDepositPayFinishTimeFormartMD() {
    SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
    if (null == depositPayFinishTime) {
      return "";
    } else {
      return sdf.format(depositPayFinishTime);
    }
  }

  public int getMessageSendFlag() {
    return messageSendFlag;
  }

  public void setMessageSendFlag(int messageSendFlag) {
    this.messageSendFlag = messageSendFlag;
  }


}
