package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * 订单风控
 * 
 * @author xlg
 * 
 */
public class OrderRiskBackList implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long bid;
  /**
   * 类型
   */
  private Integer type;
  /**
   * 类型
   */
  private String typeStr;
  /**
   * 内容
   */
  private String content;
  /**
   * 操作人
   */
  private String operator;
  /**
   * 操作时间
   */
  private Date operatDate;
  /**
   * 0无效 1 有效
   */
  private Integer valid;

  public Long getBid() {
    return bid;
  }

  public void setBid(Long bid) {
    this.bid = bid;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Date getOperatDate() {
    return operatDate;
  }

  public void setOperatDate(Date operatDate) {
    this.operatDate = operatDate;
  }

  public Integer getValid() {
    return valid;
  }

  public void setValid(Integer valid) {
    this.valid = valid;
  }

  public String getTypeStr() {
    return typeStr;
  }

  public void setTypeStr(String typeStr) {
    this.typeStr = typeStr;
  }
}
