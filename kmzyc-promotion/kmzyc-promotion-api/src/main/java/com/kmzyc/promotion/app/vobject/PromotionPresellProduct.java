package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 预售商品
 * 
 * @author Administrator
 * 
 */
public class PromotionPresellProduct implements Serializable {
  private static final long serialVersionUID = 1L;
  // 预售产品ID
  private Long presellProductId;
  // 预售ID
  private Long presellId;
  // 产品SKU_ID标识
  private String productSkuId;
  // 预售价
  private BigDecimal presellPrice;
  // 定金
  private BigDecimal depositPrice;
  // 尾款
  private BigDecimal finalPrice;
  // 预售库存
  private Long presellStock;
  // 创建时间
  private Date createTime;
  // 产品标题
  private String productTitle;
  // 产品sku
  private String productSkuCode;
  // 品牌名称
  private String brandName;
  // 产品价格
  private BigDecimal price;
  // 产品实际库存
  private Long stock;
  // 预售已售数量
  private Long presellSell;
  // 预售库存
  private Long availableQuantity;

  public Long getPresellProductId() {
    return presellProductId;
  }

  public void setPresellProductId(Long presellProductId) {
    this.presellProductId = presellProductId;
  }

  public Long getPresellId() {
    return presellId;
  }

  public void setPresellId(Long presellId) {
    this.presellId = presellId;
  }

  public String getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(String productSkuId) {
    this.productSkuId = productSkuId;
  }

  public BigDecimal getPresellPrice() {
    return presellPrice;
  }

  public void setPresellPrice(BigDecimal presellPrice) {
    this.presellPrice = presellPrice;
  }

  public BigDecimal getDepositPrice() {
    return depositPrice;
  }

  public void setDepositPrice(BigDecimal depositPrice) {
    this.depositPrice = depositPrice;
  }

  public BigDecimal getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Long getPresellStock() {
    return presellStock;
  }

  public void setPresellStock(Long presellStock) {
    this.presellStock = presellStock;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Long getStock() {
    return stock;
  }

  public void setStock(Long stock) {
    this.stock = stock;
  }

  public Long getPresellSell() {
    return presellSell;
  }

  public void setPresellSell(Long presellSell) {
    this.presellSell = presellSell;
  }

  public Long getAvailableQuantity() {
    return availableQuantity;
  }

  public void setAvailableQuantity(Long availableQuantity) {
    this.availableQuantity = availableQuantity;
  }

}
