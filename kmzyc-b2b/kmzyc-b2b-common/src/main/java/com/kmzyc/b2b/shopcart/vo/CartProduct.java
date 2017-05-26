package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 购物车产品
 * 
 * @author luotao
 * 
 */
public class CartProduct implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 235440887292868944L;
  /** 产品ID */
  private Long productID;
  /**
   * 商户ID
   */
  private Long sellerId;

  /**
   * 店铺名称
   */
  private String shopName;

  private String categoryAttrValue;


  /**
   * tta
   * 
   * @return
   */

  private String suitName;


  private BigDecimal suitPrice;



  /**
   * 产品状态
   */
  private Integer productStatus;
  /**
   * sku状态
   */
  private Integer skuStatus;


  public String getShopName() {
    return shopName;
  }

  public void setShopName(String shopName) {
    this.shopName = shopName;
  }

  private String productNo;
  private String productSkuCode;

  private String productDesc;

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public Integer getProductStatus() {
    return productStatus;
  }

  public void setProductStatus(Integer productStatus) {
    this.productStatus = productStatus;
  }

  public Integer getSkuStatus() {
    return skuStatus;
  }

  public void setSkuStatus(Integer skuStatus) {
    this.skuStatus = skuStatus;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  /**
   * 商家类型
   */
  private Integer supplierType;

  private Long productSkuId;

  /** 市场价格 */
  private Double marketPrice;
  /** 成本价格 */
  private BigDecimal costPrice;

  /*** 获利百分比 */
  private BigDecimal costIncomeRatio;

  private BigDecimal maxMeetData;
  /**
   * 进销存编码
   */
  private String jxcCode;
  /***
   * 外部系统产品编号
   */
  private String imagePath;
  private String imagePath5;
  private String imagePath6;
  private String imagePath7;
  /** 规格 */
  private String skuAttrValue;
  /** 供应商名称 */
  private String supplierName;
  /** 供应商Id */
  private Long supplierCode;
  /** 重量 单位千克 */
  private BigDecimal unitWeight;

  /** 产品标题 name+sku规格 */
  private String title;
  private String url;
  private Long brandId;
  private Long categoryId;
  private String channel;
  /**
   * 产品类型0：非药品1：OTC药品2：医疗器械
   */
  private Integer productType;
  /**
   * 商品批次号
   */
  private String batchNo;
  /** 商品附带积分 ***/
  private Integer credits;
  /** 商品品牌 ***/
  private String brandName;
  /**
   * 商品运费
   */
  private BigDecimal freight;
  /**
   * 是否免邮（0：不免邮；1：免邮）
   */
  private Integer freeStatus;
  /** ID */
  protected Long id;

  /** 套装销售单价 */
  private BigDecimal price;
  /****
   * 外部系统编码
   */
  private String erpSysCode;
  /***
   * 外部系统产品编号
   */
  private String erpProCode;

  /** 库存数量 */
  private Integer stockCount;
  /**
   * 套装名称
   */
  private String name;
  /** 已下架 */
  private Boolean isOutOfStock;
  /**
   * 产品价格总计
   */
  private BigDecimal productsPriceSum;
  /***
   * pv值
   */
  private BigDecimal pvalue;
  /***
   * 获利百分比
   */
  private BigDecimal costIncomeRadio;
  /*** 获利金额 */
  private BigDecimal costIncomeMoney;

  private Boolean isChecked;

  /**SKU条形码*/
  private String skuBarCode;

  public Long getProductID() {
    return productID;
  }

  public void setProductID(Long productID) {
    this.productID = productID;
  }

  public Double getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(Double marketPrice) {
    this.marketPrice = marketPrice;
  }

  public BigDecimal getCostPrice() {
    return costPrice;
  }

  public void setCostPrice(BigDecimal costPrice) {
    this.costPrice = costPrice;
  }

  public BigDecimal getCostIncomeRatio() {
    return costIncomeRatio;
  }

  public void setCostIncomeRatio(BigDecimal costIncomeRatio) {
    this.costIncomeRatio = costIncomeRatio;
  }

  public BigDecimal getMaxMeetData() {
    return maxMeetData;
  }

  public void setMaxMeetData(BigDecimal maxMeetData) {
    this.maxMeetData = maxMeetData;
  }

  public String getJxcCode() {
    return jxcCode;
  }

  public void setJxcCode(String jxcCode) {
    this.jxcCode = jxcCode;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  public String getImagePath5() {
    return imagePath5;
  }

  public void setImagePath5(String imagePath5) {
    this.imagePath5 = imagePath5;
  }

  public String getImagePath6() {
    return imagePath6;
  }

  public void setImagePath6(String imagePath6) {
    this.imagePath6 = imagePath6;
  }

  public String getImagePath7() {
    return imagePath7;
  }

  public void setImagePath7(String imagePath7) {
    this.imagePath7 = imagePath7;
  }

  public String getSkuAttrValue() {
    return skuAttrValue;
  }

  public void setSkuAttrValue(String skuAttrValue) {
    this.skuAttrValue = skuAttrValue;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public Long getSupplierCode() {
    return supplierCode;
  }

  public void setSupplierCode(Long supplierCode) {
    this.supplierCode = supplierCode;
  }

  public BigDecimal getUnitWeight() {
    return unitWeight;
  }

  public void setUnitWeight(BigDecimal unitWeight) {
    this.unitWeight = unitWeight;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public Long getBrandId() {
    return brandId;
  }

  public void setBrandId(Long brandId) {
    this.brandId = brandId;
  }

  public Long getCategoryId() {
    return categoryId;
  }

  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public Integer getProductType() {
    return productType;
  }

  public void setProductType(Integer productType) {
    this.productType = productType;
  }

  public String getBatchNo() {
    return batchNo;
  }

  public void setBatchNo(String batchNo) {
    this.batchNo = batchNo;
  }

  public Integer getCredits() {
    return credits;
  }

  public void setCredits(Integer credits) {
    this.credits = credits;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
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

  public Integer getStockCount() {
    if (stockCount == null) {
      stockCount = 0;
    }
    return stockCount;
  }

  public void setStockCount(Integer stockCount) {
    this.stockCount = stockCount;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getIsOutOfStock() {
    return isOutOfStock;
  }

  public void setIsOutOfStock(Boolean isOutOfStock) {
    this.isOutOfStock = isOutOfStock;
  }

  public BigDecimal getProductsPriceSum() {
    return productsPriceSum;
  }

  public void setProductsPriceSum(BigDecimal productsPriceSum) {
    this.productsPriceSum = productsPriceSum;
  }

  public BigDecimal getPvalue() {
    if (pvalue == null) {
      pvalue = BigDecimal.ZERO;
    }
    return pvalue;
  }

  public void setPvalue(BigDecimal pvalue) {
    this.pvalue = pvalue;
  }

  public BigDecimal getCostIncomeRadio() {
    return costIncomeRadio;
  }

  public void setCostIncomeRadio(BigDecimal costIncomeRadio) {
    this.costIncomeRadio = costIncomeRadio;
  }

  public BigDecimal getCostIncomeMoney() {
    return costIncomeMoney;
  }

  public void setCostIncomeMoney(BigDecimal costIncomeMoney) {
    this.costIncomeMoney = costIncomeMoney;
  }

  public Boolean getIsChecked() {
    return isChecked;
  }

  public void setIsChecked(Boolean isChecked) {
    this.isChecked = isChecked;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  public BigDecimal getFinalPrice() {
    return finalPrice;
  }

  public void setFinalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
  }

  /** 购买数量 */
  private int amount;
  /** 套装销售单价 */
  private BigDecimal finalPrice;

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public Long getSellerId() {
    return sellerId;
  }

  public void setSellerId(Long sellerId) {
    this.sellerId = sellerId;
  }

  /** 1自营 2入驻 3代销 4康美中药城 */
  public Integer getSupplierType() {
    return supplierType;
  }

  /** 1自营 2入驻 3代销 4康美中药城 */
  public void setSupplierType(Integer supplierType) {
    this.supplierType = supplierType;
  }

  public String getSuitName() {
    return suitName;
  }

  public void setSuitName(String suitName) {
    this.suitName = suitName;
  }

  public BigDecimal getSuitPrice() {
    return suitPrice;
  }

  public void setSuitPrice(BigDecimal suitPrice) {
    this.suitPrice = suitPrice;
  }

  public String getCategoryAttrValue() {
    return categoryAttrValue;
  }

  public void setCategoryAttrValue(String categoryAttrValue) {
    this.categoryAttrValue = categoryAttrValue;
  }

  public String getProductDesc() {
    return productDesc;
  }

  public void setProductDesc(String productDesc) {
    this.productDesc = productDesc;
  }

  public String getSkuBarCode() {
    return skuBarCode;
  }

  public void setSkuBarCode(String skuBarCode) {
    this.skuBarCode = skuBarCode;
  }
}
