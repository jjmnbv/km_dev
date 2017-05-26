package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderMainExt implements Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 扩展ID
   */
  private Long orderExtId;
  /**
   * 订单编号
   */
  private String orderCode;
  /**
   * 延迟收货天数
   */
  private Integer delayReceipt;

  /**
   * 机构码
   */
  private String organCode;

  /**
   * 机构描述
   */
  private String organDes;

  /**
   * 推广者ID
   */
  private Long promId;

  /**
   * 外部来源
   */
  private String orderOutSource;
  /**
   * 订单佣金
   */
  private BigDecimal orderCommission;
  
  /**
   * 尾款支付短信通知标识
   */
  private Long messageSendFlag;
 

  public Long getOrderExtId() {
    return orderExtId;
  }

  public void setOrderExtId(Long orderExtId) {
    this.orderExtId = orderExtId;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Integer getDelayReceipt() {
    return delayReceipt;
  }

  public void setDelayReceipt(Integer delayReceipt) {
    this.delayReceipt = delayReceipt;
  }

  public String getOrganCode() {
    return organCode;
  }

  public void setOrganCode(String organCode) {
    this.organCode = organCode;
  }

  public String getOrganDes() {
    return organDes;
  }

  public void setOrganDes(String organDes) {
    this.organDes = organDes;
  }

  public Long getPromId() {
    return promId;
  }

  public void setPromId(Long promId) {
    this.promId = promId;
  }

  public String getOrderOutSource() {
    return orderOutSource;
  }

  public void setOrderOutSource(String orderOutSource) {
    this.orderOutSource = orderOutSource;
  }

  public BigDecimal getOrderCommission() {
    return orderCommission;
  }

  public void setOrderCommission(BigDecimal orderCommission) {
    this.orderCommission = orderCommission;
  }
  
  public Long getMessageSendFlag() {
    return messageSendFlag;
  }

  public void setMessageSendFlag(Long messageSendFlag) {
    this.messageSendFlag = messageSendFlag;
  }
}
