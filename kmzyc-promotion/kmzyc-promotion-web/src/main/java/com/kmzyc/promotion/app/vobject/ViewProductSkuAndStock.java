package com.kmzyc.promotion.app.vobject;

import java.math.BigDecimal;
import java.util.List;

/**
 * 产品SKU+库存数量
 * 
 * @author luoyi
 * @createDate 20130923
 */
public class ViewProductSkuAndStock implements java.io.Serializable {
  private static final long serialVersionUID = 1221127634778556968L;

  private Long productId;

  private String procuctName;

  private String productTitle;

  private String productNo;

  private String status;

  private BigDecimal marketPrice;

  private BigDecimal costPrice;

  private Long categoryId;

  private String categoryName;

  private Long productSkuId;

  private String productSkuCode;

  private String skuStatus;

  private BigDecimal price;

  private List<ViewSkuAttr> viewSkuAttrs;

  private Integer stockQuality;// 库存数量

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProcuctName() {
    return procuctName;
  }

  public void setProcuctName(String procuctName) {
    this.procuctName = procuctName;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BigDecimal getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(BigDecimal marketPrice) {
    this.marketPrice = marketPrice;
  }

  public BigDecimal getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(BigDecimal costPrice) {
    this.costPrice = costPrice;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getCategoryName() {
    return categoryName;
  }

  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  public List<ViewSkuAttr> getViewSkuAttrs() {
    return viewSkuAttrs;
  }

  public void setViewSkuAttrs(List<ViewSkuAttr> viewSkuAttrs) {
    this.viewSkuAttrs = viewSkuAttrs;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getSkuStatus() {
    return skuStatus;
  }

  public void setSkuStatus(String skuStatus) {
    this.skuStatus = skuStatus;
  }

  public Integer getStockQuality() {
    return stockQuality;
  }

  public void setStockQuality(Integer stockQuality) {
    this.stockQuality = stockQuality;
  }

  @Override
  public String toString() {
    return "ViewProductSkuAndStock [productId=" + productId + ", procuctName=" + procuctName
        + ", productNo=" + productNo + ", status=" + status + ", marketPrice=" + marketPrice
        + ", costPrice=" + costPrice + ", categoryId=" + categoryId + ", categoryName="
        + categoryName + ", productSkuId=" + productSkuId + ", productSkuCode=" + productSkuCode
        + ", skuStatus=" + skuStatus + ", price=" + price + ", viewSkuAttrs=" + viewSkuAttrs
        + ", stockQuality=" + stockQuality + "]";
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

}
