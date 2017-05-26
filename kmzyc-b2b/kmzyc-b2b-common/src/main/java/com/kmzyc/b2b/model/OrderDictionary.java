package com.kmzyc.b2b.model;

import java.io.Serializable;

public class OrderDictionary implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = -2792486547555285985L;

  /**
   * 订单字典编号
   */
  private Long orderDictionaryId;

  /**
   * 订单字典类型
   */
  private String orderDictionaryType;

  /**
   * 订单字典键
   */
  private Long orderDictionaryKey;

  /**
   * 订单字典代码
   */
  private String orderDictionaryCode;

  /**
   * 订单字典值
   */
  private String orderDictionaryValue;

  /**
   * 订单字典备注
   */
  private String orderDictionaryComment;

  public Long getOrderDictionaryId() {
    return orderDictionaryId;
  }

  public void setOrderDictionaryId(Long orderDictionaryId) {
    this.orderDictionaryId = orderDictionaryId;
  }

  public String getOrderDictionaryType() {
    return orderDictionaryType;
  }

  public void setOrderDictionaryType(String orderDictionaryType) {
    this.orderDictionaryType = orderDictionaryType;
  }

  public Long getOrderDictionaryKey() {
    return orderDictionaryKey;
  }

  public void setOrderDictionaryKey(Long orderDictionaryKey) {
    this.orderDictionaryKey = orderDictionaryKey;
  }

  public String getOrderDictionaryCode() {
    return orderDictionaryCode;
  }

  public void setOrderDictionaryCode(String orderDictionaryCode) {
    this.orderDictionaryCode = orderDictionaryCode;
  }

  public String getOrderDictionaryValue() {
    return orderDictionaryValue;
  }

  public void setOrderDictionaryValue(String orderDictionaryValue) {
    this.orderDictionaryValue = orderDictionaryValue;
  }

  public String getOrderDictionaryComment() {
    return orderDictionaryComment;
  }

  public void setOrderDictionaryComment(String orderDictionaryComment) {
    this.orderDictionaryComment = orderDictionaryComment;
  }

}
