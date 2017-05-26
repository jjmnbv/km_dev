package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class OrderItemGaVo implements Serializable {
  private static final long serialVersionUID = 1L;
  private String orderCode;
  private String productName;
  private String category;
  private String commoditySku;
  private BigDecimal commodityUnitPrice;
  private Long commodityNumber;

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getCommoditySku() {
    return commoditySku;
  }

  public void setCommoditySku(String commoditySku) {
    this.commoditySku = commoditySku;
  }

  public BigDecimal getCommodityUnitPrice() {
    return commodityUnitPrice;
  }

  public void setCommodityUnitPrice(BigDecimal commodityUnitPrice) {
    this.commodityUnitPrice = commodityUnitPrice;
  }

  public Long getCommodityNumber() {
    return commodityNumber;
  }

  public void setCommodityNumber(Long commodityNumber) {
    this.commodityNumber = commodityNumber;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  @Override
  public String toString() {
    StringBuilder sb =
        new StringBuilder("{").append("'orderCode':").append("'").append(orderCode).append(
            "','commoditySku':'").append(commoditySku).append("','commodityUnitPrice':'").append(
            commodityUnitPrice).append("','commodityNumber':'").append(commodityNumber).append(
            "','productName':'").append(productName).append("','category':'").append(category)
            .append("'}");
    return sb.toString();
  }
}
