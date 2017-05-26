package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.struts2.json.annotations.JSON;

import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 购物车产品
 * 
 * @author hewenfeng
 * 
 */
public class CarProduct extends BaseProduct implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;
  /** 产品ID */
  private Long productID;
  /** 产品购买总数量 */
  private int amount;
  /** 市场价格 */
  private Double marketPrice;
  /** 成本价格 */
  private BigDecimal costPrice;
  /** 产品PV值 */
  private BigDecimal pvalue;
  /*** 获利百分比 */
  private BigDecimal costIncomeRatio;
  /*** 获利金额 */
  private BigDecimal costIncomeMoney;
  /****
   * 外部系统编码
   */
  private String erpSysCode;

  /***
   * 赠品满足最大条件
   */
  private BigDecimal maxMeetData;

  /**
   * 进销存编码
   */
  private String jxcCode;

  /***
   * 外部系统产品编号
   */
  private String erpProCode;

  /** 产品名称 */
  private String name;
  /** 产品图片路径 */
  private String imagePath;
  private String imagePath5;
  private String imagePath6;
  private String imagePath7;
  /** 产品积分 */
  // private Integer scoreNumber;
  /** 规格 */
  private String skuAttrValue;
  /** 供应商名称 */
  private String supplierName;
  /** 供应商Id */
  private String supplierCode;

  /** 重量 单位千克 */
  private BigDecimal unitWeight;
  /** 产品备注 */
  private String productDesc;

  private String productNo;

  private String productSkuCode;

  private Boolean isChecked = true;
  /** 产品标题 name+sku规格 */
  private String title;

  private String url;

  private Long brandId;
  private Long categoryId;
  private String channel;
  /**
   * 活动产品规则ID
   */
  private Long ruleDataId;

  /** 淘宝商品数据对象 */
  private Object objectItem;
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

  @JSON(serialize = false)
  public Long getProductID() {
    return productID;
  }

  public void setProductID(Long productID) {
    this.productID = productID;
  }

  public int getAmount() {
    return amount;
  }

  public void setAmount(int amount) {
    this.amount = amount;
  }

  // public double getMarketPrice() {
  // return marketPrice;
  // }
  //
  // public void setMarketPrice(double marketPrice) {
  // this.marketPrice = marketPrice;
  // }

  public BigDecimal getCostPrice() {
    if (costPrice == null) {
      costPrice = BigDecimal.ZERO;
    }
    return costPrice;
  }

  public void setCostPrice(BigDecimal costPrice) {
    this.costPrice = costPrice;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImagePath() {
    return imagePath;
  }

  public void setImagePath(String imagePath) {
    this.imagePath = imagePath;
  }

  // /** 产品积分 */
  // public Integer getScoreNumber() {
  // return scoreNumber;
  // }
  // /** 产品积分 */
  // public void setScoreNumber(Integer scoreNumber) {
  // this.scoreNumber = scoreNumber;
  // }

  /**
   * 原始总价
   * 
   * @return
   */
  @JSON(serialize = false)
  public BigDecimal getAllMoney() {
    if (price == null) {
      return new BigDecimal(0);
    }
    return price.multiply(BigDecimal.valueOf(getAmount()));
    // return allMoney;
  }

  /**
   * 最终总价
   * 
   * @return
   */
  @JSON(serialize = false)
  public BigDecimal getLastAllMoney() {
    if (getFinalPrice() != null) {
      return getFinalPrice().multiply(BigDecimal.valueOf(getAmount()));
    }
    return getPrice().multiply(BigDecimal.valueOf(amount));
    // return lastAllMoney;
  }

  @JSON(serialize = false)
  public BigDecimal getUnitWeight() {
    if (unitWeight == null) {
      unitWeight = BigDecimal.ZERO;
    }
    return unitWeight;
  }

  public void setUnitWeight(BigDecimal unitWeight) {
    this.unitWeight = unitWeight;
  }

  @JSON(serialize = false)
  public String getProductDesc() {
    return productDesc;
  }

  public void setProductDesc(String productDesc) {
    this.productDesc = productDesc;
  }

  @JSON(serialize = false)
  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  @JSON(serialize = false)
  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  /**
   * 下架
   */
  private Boolean isOutOfStock = false;

  /**
   * 库存数量
   */
  private Integer stockCount;

  /**
   * 下架
   */
  public Boolean getIsOutOfStock() {
    if (isOutOfStock == null) return false;
    return isOutOfStock;
  }

  /**
   * 下架
   */
  public void setIsOutOfStock(Boolean isOutOfStock) {
    this.isOutOfStock = isOutOfStock;
  }

  /**
   * 库存数量
   */
  public Integer getStockCount() {
    if (stockCount == null) {
      stockCount = 0;
    }
    return stockCount;
  }

  /**
   * 库存数量
   */
  public void setStockCount(Integer stockCount) {
    this.stockCount = stockCount;
  }

  /**
   * 所有活动（特价打折满减满送）
   */
  private transient Map<Long, PromotionInfo> promotionInfoMap;

  /**
   * 所有活动（特价打折满减满送）
   */
  @JSON(serialize = false)
  public Map<Long, PromotionInfo> getPromotionInfoMap() {
    if (promotionInfoMap == null) {
      promotionInfoMap = new ConcurrentHashMap<Long, PromotionInfo>();
    }
    return promotionInfoMap;
  }

  /**
   * 所有活动（特价打折满减满送）
   */
  public void setPromotionInfoMap(Map<Long, PromotionInfo> promotionInfoMap) {
    this.promotionInfoMap = promotionInfoMap;
  }

  public void addPromotionInMap(PromotionInfo info) {
    this.getPromotionInfoMap().put(info.getPromotionId(), info);
  }

  public void removePromotionInMap(PromotionInfo info) {
    this.getPromotionInfoMap().remove(info.getPromotionId());
  }

  @JSON(serialize = false)
  public String getIdkey() {
    return "CarProduct id:" + this.getProductSkuId();
  }

  public final static String cmsPagePath =
      ConfigurationUtil.getString("PRODUCT_IMG_PATH");

  @JSON(serialize = false)
  public String getAbsoluteImagePath() {
    return cmsPagePath + imagePath;
  }

  public String getImagePath5() {
    String path = getAbsoluteImagePath();
    int pointIndex = path.lastIndexOf('.');
    imagePath5 = path.substring(0, pointIndex) + "_5" + path.substring(pointIndex, path.length());
    return imagePath5;
  }

  public void setImagePath5(String imagePath5) {
    this.imagePath5 = imagePath5;
  }

  public String getImagePath6() {
    String path = getAbsoluteImagePath();
    int pointIndex = path.lastIndexOf('.');
    imagePath6 = path.substring(0, pointIndex) + "_6" + path.substring(pointIndex, path.length());
    return imagePath6;
  }

  public void setImagePath6(String imagePath6) {
    this.imagePath6 = imagePath6;
  }

  public String getImagePath7() {
    String path = getAbsoluteImagePath();
    int pointIndex = path.lastIndexOf('.');
    imagePath7 = path.substring(0, pointIndex) + "_7" + path.substring(pointIndex, path.length());
    return imagePath7;
  }

  public void setImagePath7(String imagePath7) {
    this.imagePath7 = imagePath7;
  }

  /** 多选框是否被勾选 */
  public Boolean getIsChecked() {
    if (isChecked == null) {
      isChecked = false;
    }
    return isChecked;
  }

  /** 多选框是否被勾选 */
  public void setIsChecked(Boolean isChecked) {
    this.isChecked = isChecked;
  }

  public Double getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(Double marketPrice) {
    this.marketPrice = marketPrice;
  }

  /** 限购产品购买数量 */
  private Integer restrictionAmount = 0;

  /** 限购产品购买数量 */
  @JSON(serialize = false)
  public Integer getRestrictionAmount() {
    return restrictionAmount;
  }

  /** 限购产品购买数量 */
  public void setRestrictionAmount(Integer restrictionAmount) {
    this.restrictionAmount = restrictionAmount;
  }

  @JSON(serialize = false)
  public String getSkuAttrValue() {
    return skuAttrValue;
  }

  /** 规格 */
  public void setSkuAttrValue(String skuAttrValue) {
    this.skuAttrValue = skuAttrValue;
  }

  /** 供应商名称 */
  @JSON(serialize = false)
  public String getSupplierName() {
    return supplierName;
  }

  /** 供应商名称 */
  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  /** 供应商Id */
  @JSON(serialize = false)
  public String getSupplierCode() {
    return supplierCode;
  }

  /** 供应商Id */
  public void setSupplierCode(String supplierCode) {
    this.supplierCode = supplierCode;
  }

  /** 产品标题 name+sku规格 */
  public String getTitle() {
    if (skuAttrValue != null) {
      return title + " " + skuAttrValue;
    }
    return title;
  }

  /** 产品标题 name+sku规格 */
  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  /** 淘宝商品数据对象 */

  public Object getObjectItem() {
    return objectItem;
  }

  /** 淘宝商品数据对象 */
  public void setObjectItem(Object objectItem) {
    this.objectItem = objectItem;
  }

  /** 品牌id */
  public Long getBrandId() {
    return brandId;
  }

  /** 品牌id */
  public void setBrandId(Long brandId) {
    this.brandId = brandId;
  }

  /** 分类id */
  public Long getCategoryId() {
    return categoryId;
  }

  /** 分类id */
  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  /** 渠道 （站点） */
  public String getChannel() {
    return channel;
  }

  /** 渠道 （站点） */
  public void setChannel(String channel) {
    this.channel = channel;
  }

  public Integer getProductType() {
    return productType;
  }

  public void setProductType(Integer productType) {
    this.productType = productType;
  }

  public Long getRuleDataId() {
    return ruleDataId;
  }

  public void setRuleDataId(Long ruleDataId) {
    this.ruleDataId = ruleDataId;
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

  public static long getSerialversionuid() {
    return serialVersionUID;
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
    if (maxMeetData == null) {
      maxMeetData = BigDecimal.ZERO;
    }
    return maxMeetData;
  }

  public void setMaxMeetData(BigDecimal maxMeetData) {
    this.maxMeetData = maxMeetData;
  }

  public String getImg() {
    return imagePath7;
  }

}
