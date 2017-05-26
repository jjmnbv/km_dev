package com.pltfm.app.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 风控得分
 * 
 * @author xlg
 * 
 */
public class OrderRiskScore implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long ossid;
  /**
   * 纬度 1订单 2消费者 3商家
   */
  private Integer latitude;
  /**
   * 展示文字
   */
  private String label;
  /**
   * 检测值
   */
  private BigDecimal checkValue;
  /**
   * 得分
   */
  private Integer score;
  /**
   * 明细值
   */
  private String extValue;
  /**
   * 创建日期
   */
  private Date createDate;
  /**
   * 订单号
   */
  private String orderCode;
  
  /**
   * 1 可用 0不可用
   */
  private Integer isValid;

  public Long getOssid() {
    return ossid;
  }

  public void setOssid(Long ossid) {
    this.ossid = ossid;
  }

  public Integer getLatitude() {
    return latitude;
  }

  public void setLatitude(Integer latitude) {
    this.latitude = latitude;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public BigDecimal getCheckValue() {
    return checkValue;
  }

  public void setCheckValue(BigDecimal checkValue) {
    this.checkValue = checkValue;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public String getExtValue() {
    return extValue;
  }

  public void setExtValue(String extValue) {
    this.extValue = extValue;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public Integer getIsValid() {
    return isValid;
  }

  public void setIsValid(Integer isValid) {
    this.isValid = isValid;
  }
}
