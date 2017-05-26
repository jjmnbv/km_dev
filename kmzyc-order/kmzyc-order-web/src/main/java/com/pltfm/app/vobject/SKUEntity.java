package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 结转时按SKU统计的VO
 * 
 * @author lvxingxing
 * @version 1.0
 * @since 2013-8-7
 */
public class SKUEntity implements Serializable {
  private static final long serialVersionUID = 1L;
  private String commoditySku;
  private String commodityCode;
  private String commodityName;
  private String commodityTitle;
  private BigDecimal commodityUnitPrice;
  private String commodityBatchNumber;
  private Long commodityNumber;
  private String commodityChannel;
  private String commoditySkuDescription;
  /**
   * 仓库名称
   */
  private String warehouseName;
  /**
   * 供应商类型名称
   */
  private String supplierTypeName;

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

  public String getCommodityBatchNumber() {
    return commodityBatchNumber;
  }

  public void setCommodityBatchNumber(String commodityBatchNumber) {
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

  public String getCommodityTitle() {
    return commodityTitle;
  }

  public void setCommodityTitle(String commodityTitle) {
    this.commodityTitle = commodityTitle;
  }

  public String getCommoditySkuDescription() {
    return commoditySkuDescription;
  }

  public void setCommoditySkuDescription(String commoditySkuDescription) {
    this.commoditySkuDescription = commoditySkuDescription;
  }

  public String getWarehouseName() {
    return warehouseName;
  }

  public void setWarehouseName(String warehouseName) {
    this.warehouseName = warehouseName;
  }

  public String getSupplierTypeName() {
    return supplierTypeName;
  }

  public void setSupplierTypeName(String supplierTypeName) {
    this.supplierTypeName = supplierTypeName;
  }
}
