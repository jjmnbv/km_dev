package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.kmzyc.promotion.app.util.NumberUtil;
import com.kmzyc.promotion.optimization.vo.PromotionProductData;

public class PromotionProduct implements Serializable {
  private static final long serialVersionUID = 1L;

  private Long promotionProductId;

  private String defaultProductSkuImgPath;

  /**
   * 状态变更时间
   */
  private Date modifyTime;
  
  /**
   * 活动产品状态：1.未上线;2.上线中;3.已下线
   */
  private Integer status;

  /**
   * 商户ID
   */
  private Long sellerId;

  /**
   * 商家类型
   */
  private Integer supplierType;

  /**
   * 活动ID
   */
  private Long promotionId;

  /**
   * 规则ID
   */
  private Long ruleDataId;

  /**
   * skuId
   */
  private Long productSkuId;
  /**
   * 自定义分类 ;-1时为例外商品;
   */
  private String category;
  
  private Integer promotionType;

  /**
   * 产品ID
   */
  private Long productId;
  private String productNo;
  /**
   * 市场价
   */
  private BigDecimal marketPrice;



  /****
   * 
   * 用于满赠满足条件最大值
   */

  private BigDecimal maxMeetData;



  /****
   * 
   * 产品PV值
   */
  private BigDecimal pvalue;

  /****
   * 外部系统编码
   */
  private String erpSysCode;

  /***
   * 外部系统产品编号
   */
  private String erpProCode;

  /**
   * 进销存CODE
   */
  private String jxcCode;
  /**
   * 获利百分比
   */
  private BigDecimal costIncomeRatio;
  /**
   * 获利金额
   */
  private BigDecimal costIncomeMoney;

  private BigDecimal finalPrice;

  /**
   * 活动价
   */
  private BigDecimal price;

  private Date createTime;

  private String productSkuIds;

  private String promotionProductIds;
  
  /**
   * 产品名称
   */
  private String productName;
  /**
   * 标题
   */
  private String productTitle;
  /**
   * 商家名称
   */
  private String shopName;
  /**
   * 规格
   */
  private String categoryAttrValue;
  /**
   * 重量
   */
  private BigDecimal unitWeight;
  /**
   * 库存
   */
  private Integer stockCount;
  /**
   * 下架 0：下架
   */
  private Integer isOutStock;
  /**
   * 选择
   */
  private Boolean isChecked;
  /**
   * 赠送数量
   */
  private Integer amount = 1;

  /**
   * 商品运费
   */
  private BigDecimal freight;

  /**
   * 是否免邮（0：不免邮；1：免邮）
   */
  private Integer freeStatus;

  private List<ProductSkuAttr> ProductSkuAttrList;

  private String productSkuCode;
  private Integer minBuy;
  private Integer maxBuy;
  private Integer promotionStock;
  private Integer promotionSell;
  // ------------ for page
  private int skip;
  private int max;
  /********产品属性**********/
  //品牌名
  private String brandName;
  //产品状态
  private Integer productStatus;
  //供应商id
  private String shopCode;

  /********附赠商品**********/
  private List<PromotionProductData> promotionProductDataList;

  

  public List<PromotionProductData> getPromotionProductDataList() {
    return promotionProductDataList;
  }

  public void setPromotionProductDataList(List<PromotionProductData> promotionProductDataList) {
    this.promotionProductDataList = promotionProductDataList;
  }

  private BigDecimal originalPrice;

  public BigDecimal getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
  }

  public Long getPromotionProductId() {
    return promotionProductId;
  }

  public void setPromotionProductId(Long promotionProductId) {
    this.promotionProductId = promotionProductId;
  }

  public String getDefaultProductSkuImgPath() {
    return defaultProductSkuImgPath;
  }

  public void setDefaultProductSkuImgPath(String defaultProductSkuImgPath) {
    this.defaultProductSkuImgPath = defaultProductSkuImgPath;
  }

  public Long getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  public Long getRuleDataId() {
    return ruleDataId;
  }

  public void setRuleDataId(Long ruleDataId) {
    this.ruleDataId = ruleDataId;
  }

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public BigDecimal getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(BigDecimal marketPrice) {
    this.marketPrice = marketPrice;
  }

  public BigDecimal getPrice() {
    return null == price ? null : NumberUtil.toBigDecimal(price);
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getProductSkuIds() {
    return productSkuIds;
  }

  public void setProductSkuIds(String productSkuIds) {
    this.productSkuIds = productSkuIds;
  }

  public String getPromotionProductIds() {
    return promotionProductIds;
  }

  public void setPromotionProductIds(String promotionProductIds) {
    this.promotionProductIds = promotionProductIds;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public List<ProductSkuAttr> getProductSkuAttrList() {
    return ProductSkuAttrList;
  }

  public void setProductSkuAttrList(List<ProductSkuAttr> productSkuAttrList) {
    ProductSkuAttrList = productSkuAttrList;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  public Integer getMinBuy() {
    return minBuy;
  }

  public void setMinBuy(Integer minBuy) {
    this.minBuy = minBuy;
  }

  public Integer getMaxBuy() {
    return maxBuy;
  }

  public void setMaxBuy(Integer maxBuy) {
    this.maxBuy = maxBuy;
  }

  public Integer getPromotionStock() {
    return promotionStock;
  }

  public void setPromotionStock(Integer promotionStock) {
    this.promotionStock = promotionStock;
  }

  public Integer getPromotionSell() {
    return promotionSell;
  }

  public void setPromotionSell(Integer promotionSell) {
    this.promotionSell = promotionSell;
  }

  public int getSkip() {
    return skip;
  }

  public void setSkip(int skip) {
    this.skip = skip;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }

  public BigDecimal getOriginalPrice() {
    return originalPrice;
  }

  public void setOriginalPrice(BigDecimal originalPrice) {
    this.originalPrice = originalPrice;
  }

  public BigDecimal getUnitWeight() {
    return unitWeight;
  }

  public void setUnitWeight(BigDecimal unitWeight) {
    this.unitWeight = unitWeight;
  }

  public Integer getStockCount() {
    return stockCount;
  }

  public void setStockCount(Integer stockCount) {
    this.stockCount = stockCount;
  }

  public Integer getIsOutStock() {
    if (null == isOutStock) {
      isOutStock = 1;
    }
    return isOutStock;
  }

  public void setIsOutStock(Integer isOutStock) {
    this.isOutStock = isOutStock;
  }

  public Boolean getIsChecked() {
    return isChecked;
  }

  public void setIsChecked(Boolean isChecked) {
    this.isChecked = isChecked;
  }

  public Integer getPromotionType() {
    return promotionType;
  }

  public void setPromotionType(Integer promotionType) {
    this.promotionType = promotionType;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }

  public String getCategoryAttrValue() {
    return categoryAttrValue;
  }

  public void setCategoryAttrValue(String categoryAttrValue) {
    this.categoryAttrValue = categoryAttrValue;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }

  public Integer getSupplierType() {
    return supplierType;
  }

  public void setSupplierType(Integer supplierType) {
    this.supplierType = supplierType;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public BigDecimal getPvalue() {
    return pvalue;
  }

  public void setPvalue(BigDecimal pvalue) {
    this.pvalue = pvalue;
  }

  public BigDecimal getCostIncomeRatio() {
    return costIncomeRatio;
  }

  public void setCostIncomeRatio(BigDecimal costIncomeRatio) {
    this.costIncomeRatio = costIncomeRatio;
  }

  public String getErpSysCode() {
    return erpSysCode;
  }

  public void setErpSysCode(String erpSysCode) {
    this.erpSysCode = erpSysCode;
  }

  public String getErpProCode() {
    return erpProCode;
  }

  public void setErpProCode(String erpProCode) {
    this.erpProCode = erpProCode;
  }

  public BigDecimal getCostIncomeMoney() {
    return costIncomeMoney;
  }

  public void setCostIncomeMoney(BigDecimal costIncomeMoney) {
    this.costIncomeMoney = costIncomeMoney;
  }

  public String getJxcCode() {
    return jxcCode;
  }

  public void setJxcCode(String jxcCode) {
    this.jxcCode = jxcCode;
  }

  public BigDecimal getFreight() {
    return freight;
  }

  public void setFreight(BigDecimal freight) {
    this.freight = freight;
  }

  public Integer getFreeStatus() {
    return freeStatus;
  }

  public void setFreeStatus(Integer freeStatus) {
    this.freeStatus = freeStatus;
  }

  public BigDecimal getMaxMeetData() {
    return maxMeetData;
  }

  public void setMaxMeetData(BigDecimal maxMeetData) {
    this.maxMeetData = maxMeetData;
  }

  public Date getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Date modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  public Integer getProductStatus() {
    return productStatus;
  }

  public void setProductStatus(Integer productStatus) {
    this.productStatus = productStatus;
  }

  public String getShopCode() {
    return shopCode;
  }

  public void setShopCode(String shopCode) {
    this.shopCode = shopCode;
  }

  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }


}
