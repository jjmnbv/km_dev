package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.kmzyc.b2b.util.NumberUtil;

/**
 * @author Administrator
 * 
 */
public class ProductsOrderBySale  implements Serializable  {
  private static final long serialVersionUID = 1L;
  // 产品id
  private Long productId;
  // 商品编码
  private String productSkuCode;
  // 商品sku表状态
  private String status;
  // 商品重量
  private BigDecimal unitWeight;

  /** 扩展属性 */
  // 商品默认图片170*170
  private String defaultProductSkuImgPath;
  // 商品图片100*100
  private String defaultProductSkuImgPath100_100;
  // 商品名称
  private String productName;
  // 商品名称
  private String productTitle;
  // 市场价
  private BigDecimal marketPrice;
  // 成本价
  private BigDecimal costPrice;


  private String url;

  private String CategoryAttrName;
  // 商品规格
  private String col;

  // 产品销量
  private Long productSaleCount;

  // 产品收藏数量
  private Long productFavorCount;
  /** 产品PV值 */
  private BigDecimal pvalue;
  // 产品库存
  private BigDecimal stock;

  /**
   * 单品运费 20151008 add mlq
   */
  private BigDecimal freight;

  /**
   * 单品是否免邮 2151111 add 0: 不免邮 1:免邮
   */
  private String freeStatus;


  /**
   * 20151222 add 返利相关需求 begin
   */
  private List<ProductImage> imageList; // sku图片

  // 三级类目名称
  private String minCategoryName;
  // 二级类目名称
  private String middleCategoryName;
  // 一级类目名称
  private String topCategoryName;
  // 产品介绍
  private String introduce;
  // 促销价
  private BigDecimal promotionPrice;
  /**
   * 商品销售价格
   */
  protected BigDecimal price;
  /**
   * 商品skuId
   */
  protected Long productSkuId;
  private Date upTime;

  /**
   * end
   * 
   * @return
   */


  public String getDefaultProductSkuImgPath100_100() {
    return defaultProductSkuImgPath100_100;
  }

  public void setDefaultProductSkuImgPath100_100(String defaultProductSkuImgPath100_100) {
    this.defaultProductSkuImgPath100_100 = defaultProductSkuImgPath100_100;
  }

  public BigDecimal getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(BigDecimal marketPrice) {
    this.marketPrice = marketPrice;
  }

  public String getDefaultProductSkuImgPath() {
    return defaultProductSkuImgPath;
  }

  public void setDefaultProductSkuImgPath(String defaultProductSkuImgPath) {
    this.defaultProductSkuImgPath = defaultProductSkuImgPath;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  @JSON(serialize = false)
  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public BigDecimal getUnitWeight() {
    return unitWeight;
  }

  public void setUnitWeight(BigDecimal unitWeight) {
    this.unitWeight = unitWeight;
  }

  @JSON(serialize = false)
  public BigDecimal getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(BigDecimal costPrice) {
    this.costPrice = costPrice;
  }
  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getCategoryAttrName() {
    return CategoryAttrName;
  }

  public void setCategoryAttrName(String categoryAttrName) {
    CategoryAttrName = categoryAttrName;
  }

  public String getCol() {
    return col;
  }

  public void setCol(String col) {
    this.col = col;
  }

  public Long getProductSaleCount() {
    return productSaleCount;
  }

  public void setProductSaleCount(Long productSaleCount) {
    this.productSaleCount = productSaleCount;
  }

  public Long getProductFavorCount() {
    return productFavorCount;
  }

  public void setProductFavorCount(Long productFavorCount) {
    this.productFavorCount = productFavorCount;
  }

  public BigDecimal getPvalue() {
    return pvalue;
  }

  public void setPvalue(BigDecimal pvalue) {
    this.pvalue = pvalue;
  }

  public BigDecimal getStock() {
    return stock;
  }

  public void setStock(BigDecimal stock) {
    this.stock = stock;
  }

  public BigDecimal getFreight() {
    return freight;
  }

  public void setFreight(BigDecimal freight) {
    this.freight = freight;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public String getFreeStatus() {
    return freeStatus;
  }

  public void setFreeStatus(String freeStatus) {
    this.freeStatus = freeStatus;
  }

  public List<ProductImage> getImageList() {
    return imageList;
  }

  public void setImageList(List<ProductImage> imageList) {
    this.imageList = imageList;
  }

  public String getMinCategoryName() {
    return minCategoryName;
  }

  public void setMinCategoryName(String minCategoryName) {
    this.minCategoryName = minCategoryName;
  }

  public String getMiddleCategoryName() {
    return middleCategoryName;
  }

  public void setMiddleCategoryName(String middleCategoryName) {
    this.middleCategoryName = middleCategoryName;
  }

  public String getTopCategoryName() {
    return topCategoryName;
  }

  public void setTopCategoryName(String topCategoryName) {
    this.topCategoryName = topCategoryName;
  }

  public String getIntroduce() {
    return introduce;
  }

  public void setIntroduce(String introduce) {
    this.introduce = introduce;
  }

  public BigDecimal getPromotionPrice() {
    return promotionPrice;
  }

  public void setPromotionPrice(BigDecimal promotionPrice) {
    this.promotionPrice = promotionPrice;
  }
  
  /** 商品销售价格 */
  public BigDecimal getPrice() {
    return NumberUtil.toBigDecimal(price);
  }

  /** 商品销售价格 */
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  /**
   * 商品skuId
   */
  public Long getProductSkuId() {
    return this.productSkuId;
  }
  
  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }
  
  public Date getUpTime() {
    return upTime;
  }

  public void setUpTime(Date upTime) {
    this.upTime = upTime;
  }
}
