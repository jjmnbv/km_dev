/*删除微商业务 package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pltfm.app.entities.OrderItem;

*//**
 * 推广影响
 * 
 * @author weijl
 * 
 *//*
public class SpreadEffect implements Serializable {

  private static final long serialVersionUID = 7934183375088958200L;
  private BigDecimal seid;
  *//**
   * 推广者ID
   *//*
  private BigDecimal spreadId;
  *//**
   * 消费者ID
   *//*
  private BigDecimal consumerId;
  *//**
   * 微商引荐人ID
   *//*
  private BigDecimal recommenders;
  *//**
   * 订单号
   *//*
  private String orderCode;
  *//**
   * 订单明细ID
   *//*
  private Long orderItemId;
  *//**
   * 商品SKU编号
   *//*
  private String commoditySku;
  *//**
   * 套餐ID
   *//*
  private BigDecimal suitId;
  *//**
   * 购买数量
   *//*
  private Long commodityNumber;
  *//**
   * 单品实收
   *//*
  private BigDecimal commodityUnitIncoming;
  *//**
   * 可获得销售返佣率
   *//*
  private BigDecimal saleRebateRate;
  *//**
   * 分销返佣率
   *//*
  private BigDecimal distriRebackRate;
  *//**
   * 对应推广规则ID
   *//*
  private BigDecimal srid;
  *//**
   * 消费返利率
   *//*
  private BigDecimal consumptionRate;
  *//**
   * 订单支付时间
   *//*
  private Date payTime;
  *//**
   * 订单完成时间
   *//*
  private Date finishTime;
  *//**
   * 结算时间
   *//*
  private Date settTime;
  *//**
   * 退货数量
   *//*
  private BigDecimal refundNumber;
  *//**
   * 可结算金额
   *//*
  private BigDecimal settAmount;
  *//**
   * 消费返利金额
   *//*
  private BigDecimal consumptionAmount;
  *//**
   * 销售佣金
   *//*
  private BigDecimal saleRebateAmount;
  *//**
   * 分销佣金
   *//*
  private BigDecimal distriRebackAmount;
  *//**
   * 状态:1准备数据|2订单支付|3订单完成|0订单取消|4已结算|5禁止结算
   *//*
  private Short status;
  *//**
   * 备注
   *//*
  private String remark;

  private List<OrderItem> orderItemList;

  private String accountLogin;// 分销员

  public BigDecimal getSeid() {
    return seid;
  }

  public void setSeid(BigDecimal seid) {
    this.seid = seid;
  }

  public BigDecimal getSpreadId() {
    return spreadId;
  }

  public void setSpreadId(BigDecimal spreadId) {
    this.spreadId = spreadId;
  }

  public BigDecimal getConsumerId() {
    return consumerId;
  }

  public void setConsumerId(BigDecimal consumerId) {
    this.consumerId = consumerId;
  }

  public BigDecimal getRecommenders() {
    return recommenders;
  }

  public void setRecommenders(BigDecimal recommenders) {
    this.recommenders = recommenders;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Long getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }

  public String getCommoditySku() {
    return commoditySku;
  }

  public void setCommoditySku(String commoditySku) {
    this.commoditySku = commoditySku;
  }

  public BigDecimal getSuitId() {
    return suitId;
  }

  public void setSuitId(BigDecimal suitId) {
    this.suitId = suitId;
  }

  public Long getCommodityNumber() {
    return commodityNumber;
  }

  public void setCommodityNumber(Long commodityNumber) {
    this.commodityNumber = commodityNumber;
  }

  public BigDecimal getCommodityUnitIncoming() {
    return commodityUnitIncoming;
  }

  public void setCommodityUnitIncoming(BigDecimal commodityUnitIncoming) {
    this.commodityUnitIncoming = commodityUnitIncoming;
  }

  public BigDecimal getSaleRebateRate() {
    return saleRebateRate;
  }

  public void setSaleRebateRate(BigDecimal saleRebateRate) {
    this.saleRebateRate = saleRebateRate;
  }

  public BigDecimal getDistriRebackRate() {
    return distriRebackRate;
  }

  public void setDistriRebackRate(BigDecimal distriRebackRate) {
    this.distriRebackRate = distriRebackRate;
  }

  public BigDecimal getSrid() {
    return srid;
  }

  public void setSrid(BigDecimal srid) {
    this.srid = srid;
  }

  public BigDecimal getConsumptionRate() {
    return consumptionRate;
  }

  public void setConsumptionRate(BigDecimal consumptionRate) {
    this.consumptionRate = consumptionRate;
  }

  public Date getPayTime() {
    return payTime;
  }

  public void setPayTime(Date payTime) {
    this.payTime = payTime;
  }

  public Date getFinishTime() {
    return finishTime;
  }

  public void setFinishTime(Date finishTime) {
    this.finishTime = finishTime;
  }

  public Date getSettTime() {
    return settTime;
  }

  public void setSettTime(Date settTime) {
    this.settTime = settTime;
  }

  public BigDecimal getRefundNumber() {
    return refundNumber;
  }

  public void setRefundNumber(BigDecimal refundNumber) {
    this.refundNumber = refundNumber;
  }

  public BigDecimal getSettAmount() {
    return settAmount;
  }

  public void setSettAmount(BigDecimal settAmount) {
    this.settAmount = settAmount;
  }

  public BigDecimal getConsumptionAmount() {
    return consumptionAmount;
  }

  public void setConsumptionAmount(BigDecimal consumptionAmount) {
    this.consumptionAmount = consumptionAmount;
  }

  public BigDecimal getSaleRebateAmount() {
    return saleRebateAmount;
  }

  public void setSaleRebateAmount(BigDecimal saleRebateAmount) {
    this.saleRebateAmount = saleRebateAmount;
  }

  public BigDecimal getDistriRebackAmount() {
    return distriRebackAmount;
  }

  public void setDistriRebackAmount(BigDecimal distriRebackAmount) {
    this.distriRebackAmount = distriRebackAmount;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public List<OrderItem> getOrderItemList() {
    return orderItemList;
  }

  public void setOrderItemList(List<OrderItem> orderItemList) {
    this.orderItemList = orderItemList;
  }

  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

}
*/