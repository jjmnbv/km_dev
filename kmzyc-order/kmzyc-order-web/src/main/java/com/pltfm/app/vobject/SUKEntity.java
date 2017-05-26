package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结转时按SUK统计的VO
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-8-7
 */
public class SUKEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  private String commoditySku;
  private String commodityCode;
  private String commodityName;
  private BigDecimal commodityUnitPrice;
  private BigDecimal commodityBatchNumber;
  private Long commodityNumber;
  private String commodityChannel;

  public String getCommoditySku() {
    return commoditySku;
  }

  public void setCommoditySku(String commoditySku) {
    this.commoditySku = commoditySku;
  }

  public String getCommodityCode() {
    return commodityCode;
  }

  public void setCommodityCode(String commodityCode) {
    this.commodityCode = commodityCode;
  }

  public String getCommodityName() {
    return commodityName;
  }

  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  public BigDecimal getCommodityUnitPrice() {
    return commodityUnitPrice;
  }

  public void setCommodityUnitPrice(BigDecimal commodityUnitPrice) {
    this.commodityUnitPrice = commodityUnitPrice;
  }

  public BigDecimal getCommodityBatchNumber() {
    return commodityBatchNumber;
  }

  public void setCommodityBatchNumber(BigDecimal commodityBatchNumber) {
    this.commodityBatchNumber = commodityBatchNumber;
  }

  public Long getCommodityNumber() {
    return commodityNumber;
  }

  public void setCommodityNumber(Long commodityNumber) {
    this.commodityNumber = commodityNumber;
  }

  public String getCommodityChannel() {
    return commodityChannel;
  }

  public void setCommodityChannel(String commodityChannel) {
    this.commodityChannel = commodityChannel;
  }
}
