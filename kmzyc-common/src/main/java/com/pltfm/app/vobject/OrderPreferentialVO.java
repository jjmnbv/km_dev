package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 优惠明细VO
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-8-19
 */
public class OrderPreferentialVO implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 6990834049624506193L;
  private Long orderPreferentialType;
  private Long orderPreferentialSource;
  private String orderPreferentialCode;
  private BigDecimal orderPreferentialSum;
  private String couponId;
  private String PreferentialSKU;

  public String getPreferentialSKU() {
    return PreferentialSKU;
  }

  public void setPreferentialSKU(String preferentialSKU) {
    PreferentialSKU = preferentialSKU;
  }

  private Long clientNO;

  public Long getOrderPreferentialType() {
    return orderPreferentialType;
  }

  public void setOrderPreferentialType(Long orderPreferentialType) {
    this.orderPreferentialType = orderPreferentialType;
  }

  public Long getOrderPreferentialSource() {
    return orderPreferentialSource;
  }

  public void setOrderPreferentialSource(Long orderPreferentialSource) {
    this.orderPreferentialSource = orderPreferentialSource;
  }

  public String getOrderPreferentialCode() {
    return orderPreferentialCode;
  }

  public void setOrderPreferentialCode(String orderPreferentialCode) {
    this.orderPreferentialCode = orderPreferentialCode;
  }

  public BigDecimal getOrderPreferentialSum() {
    return orderPreferentialSum;
  }

  public void setOrderPreferentialSum(BigDecimal orderPreferentialSum) {
    this.orderPreferentialSum = orderPreferentialSum;
  }

  public Long getClientNO() {
    return clientNO;
  }

  public void setClientNO(Long clientNO) {
    this.clientNO = clientNO;
  }

  public String getCouponId() {
    return couponId;
  }

  public void setCouponId(String couponId) {
    this.couponId = couponId;
  }

}
