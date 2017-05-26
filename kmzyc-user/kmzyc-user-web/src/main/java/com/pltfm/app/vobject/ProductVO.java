package com.pltfm.app.vobject;

import java.math.BigDecimal;

public class ProductVO {
  private BigDecimal warehoushId;// WAREHOUSE_ID
  private BigDecimal productId;// PRODUCT_ID;
  private String skuAttValue;// SKU_ATT_VALUE;sku code
  private BigDecimal stockQuality;// STOCK_QUALITY库存数量
  private BigDecimal orderQuality;// ORDER_QUALITY订购数量

  public BigDecimal getWarehoushId() {
    return warehoushId;
  }

  public void setWarehoushId(BigDecimal warehoushId) {
    this.warehoushId = warehoushId;
  }

  public BigDecimal getProductId() {
    return productId;
  }

  public void setProductId(BigDecimal productId) {
    this.productId = productId;
  }

  public String getSkuAttValue() {
    return skuAttValue;
  }

  public void setSkuAttValue(String skuAttValue) {
    this.skuAttValue = skuAttValue;
  }

  public BigDecimal getStockQuality() {
    return stockQuality;
  }

  public void setStockQuality(BigDecimal stockQuality) {
    this.stockQuality = stockQuality;
  }

  public BigDecimal getOrderQuality() {
    return orderQuality;
  }

  public void setOrderQuality(BigDecimal orderQuality) {
    this.orderQuality = orderQuality;
  }


}
