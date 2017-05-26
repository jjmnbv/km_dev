package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 订单明细扩展实体
 * 
 * @author xlg
 * 
 */
public class OrderItemExt implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 扩展ID
   */
  private Long ItemExtId;

  /**
   * 订单明细ID
   */
  private Long orderItemId;

  /**
   * 生产厂家
   */
  private String produceFactory;

  /**
   * 进销存编码
   */
  private String invoicingCode;

  /**
   * 推广者ID
   */
  private Long spreadId;

  /**
   * 推广规则ID
   */
  private Long srId;

  /**
   * 云产品ID
   */
  private Long cpId;

  /**
   * 外部返利率
   */
  private BigDecimal outRebateRate;
  /**
   * 外部返利金额
   */
  private BigDecimal outRebateMoney;

  /**
   * 返利类型
   */
  private Integer rebateType;

  public Long getItemExtId() {
    return ItemExtId;
  }

  public void setItemExtId(Long itemExtId) {
    ItemExtId = itemExtId;
  }

  public Long getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }

  public String getProduceFactory() {
    return produceFactory;
  }

  public void setProduceFactory(String produceFactory) {
    this.produceFactory = produceFactory;
  }

  public String getInvoicingCode() {
    return invoicingCode;
  }

  public void setInvoicingCode(String invoicingCode) {
    this.invoicingCode = invoicingCode;
  }

  public Long getSpreadId() {
    return spreadId;
  }

  public void setSpreadId(Long spreadId) {
    this.spreadId = spreadId;
  }

  public Long getSrId() {
    return srId;
  }

  public void setSrId(Long srId) {
    this.srId = srId;
  }

  public BigDecimal getOutRebateRate() {
    return outRebateRate;
  }

  public void setOutRebateRate(BigDecimal outRebateRate) {
    this.outRebateRate = outRebateRate;
  }

  public BigDecimal getOutRebateMoney() {
    return outRebateMoney;
  }

  public void setOutRebateMoney(BigDecimal outRebateMoney) {
    this.outRebateMoney = outRebateMoney;
  }

  public Integer getRebateType() {
    return rebateType;
  }

  public void setRebateType(Integer rebateType) {
    this.rebateType = rebateType;
  }

  public Long getCpId() {
    return cpId;
  }

  public void setCpId(Long cpId) {
    this.cpId = cpId;
  }
}
