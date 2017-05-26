package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class RefundRequest implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * 退款请求ID
   */
  private Long rrid;
  /**
   * 订单号
   */
  private String orderCode;
  /**
   * 渠道
   */
  private String channel;
  /**
   * 售类型1自营，2入驻
   */
  private Integer sellerType;
  /**
   * 商家名称
   */
  private String sellerShop;
  /**
   * 支付平台代码
   */
  private String platformCode;
  /**
   * 创建时间
   */
  private Date createDate;
  /**
   * 完成时间
   */
  private Date finishDate;
  /**
   * 账号
   */
  private String menberName;
  /**
   * 外部流水号
   */
  private String outBatchNo;
  /**
   * 交易金额
   */
  private BigDecimal refurnMoney;
  /**
   * 退款批次号
   */
  private String refundNo;
  /**
   * 状态0待处理，1已完成
   */
  private Integer status;
  /**
   * 退款请求日期
   */
  private Date requestDate;
  /**
   * 退款类型1订单、2退换货
   */
  private Integer refundType;

  /**
   * 操作人
   */
  private String operater;

  /**
   * 退换货号
   */
  private String orderAlterCode;

  /**
   * 退款金额明细
   */
  private String refundDetail;
  
  /**
   * 预售标识
   */
  private String ysflag;

  public Long getRrid() {
    return rrid;
  }

  public void setRrid(Long rrid) {
    this.rrid = rrid;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public Integer getSellerType() {
    return sellerType;
  }

  public void setSellerType(Integer sellerType) {
    this.sellerType = sellerType;
  }

  public String getSellerShop() {
    return sellerShop;
  }

  public void setSellerShop(String sellerShop) {
    this.sellerShop = sellerShop;
  }

  public String getPlatformCode() {
    return platformCode;
  }

  public void setPlatformCode(String platformCode) {
    this.platformCode = platformCode;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getFinishDate() {
    return finishDate;
  }

  public void setFinishDate(Date finishDate) {
    this.finishDate = finishDate;
  }

  public String getMenberName() {
    return menberName;
  }

  public void setMenberName(String menberName) {
    this.menberName = menberName;
  }

  public String getOutBatchNo() {
    return outBatchNo;
  }

  public void setOutBatchNo(String outBatchNo) {
    this.outBatchNo = outBatchNo;
  }

  public BigDecimal getRefurnMoney() {
    return refurnMoney;
  }

  public void setRefurnMoney(BigDecimal refurnMoney) {
    this.refurnMoney = refurnMoney;
  }

  public String getRefundNo() {
    return refundNo;
  }

  public void setRefundNo(String refundNo) {
    this.refundNo = refundNo;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public Date getRequestDate() {
    return requestDate;
  }

  public void setRequestDate(Date requestDate) {
    this.requestDate = requestDate;
  }

  public Integer getRefundType() {
    return refundType;
  }

  public void setRefundType(Integer refundType) {
    this.refundType = refundType;
  }

  public String getOperater() {
    return operater;
  }

  public void setOperater(String operater) {
    this.operater = operater;
  }

  public String getOrderAlterCode() {
    return orderAlterCode;
  }

  public void setOrderAlterCode(String orderAlterCode) {
    this.orderAlterCode = orderAlterCode;
  }

  public String getRefundDetail() {
    return refundDetail;
  }

  public void setRefundDetail(String refundDetail) {
    this.refundDetail = refundDetail;
  }

  public String getYsflag() {
    return ysflag;
  }

  public void setYsflag(String ysflag) {
    this.ysflag = ysflag;
  }
  
  
}
