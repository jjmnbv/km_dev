package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderAlterOperateStatementSaveVo implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -6555865713625533476L;

  private String orderAlterCode;// 订单号
  private Long orderItemId;// 订单项编号
  private Long status; // 状态
  private String operator; // 操作
  private Date date; // 日期
  private Long type; // 类型
  private BigDecimal orderSum;// 订单金额
  private String info;// 订单信息

  public String getOrderAlterCode() {
    return orderAlterCode;
  }

  public void setOrderAlterCode(String orderAlterCode) {
    this.orderAlterCode = orderAlterCode;
  }

  public Long getOrderItemId() {
    return orderItemId;
  }

  public void setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;
  }

  public Long getStatus() {
    return status;
  }

  public void setStatus(Long status) {
    this.status = status;
  }

  public String getOperator() {
    return operator;
  }

  public void setOperator(String operator) {
    this.operator = operator;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Long getType() {
    return type;
  }

  public void setType(Long type) {
    this.type = type;
  }

  public BigDecimal getOrderSum() {
    return orderSum;
  }

  public void setOrderSum(BigDecimal orderSum) {
    this.orderSum = orderSum;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

}
