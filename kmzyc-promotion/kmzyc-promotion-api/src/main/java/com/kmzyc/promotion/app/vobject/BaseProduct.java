package com.kmzyc.promotion.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.util.Constants;
import com.kmzyc.promotion.app.util.ListUtils;
import com.kmzyc.promotion.app.util.NumberUtil;
import com.kmzyc.promotion.app.util.PromotionInfoUtil;

/**
 * 商品促销信息缓存对象基类
 * 
 * @author hewenfeng
 * 
 */
public class BaseProduct implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 产品ID
   */
  private Long productID;
  /**
   * 库存数量
   */
  private Integer stockCount;
  /** 产品购买总数量 */
  private Integer amount;
  /** 市场价格 */
  private Double marketPrice;
  /** 成本价格 */
  private BigDecimal costPrice;
  /** 产品名称 */
  private String name;
  /** 产品图片路径 */
  private String imagePath;
  private String imagePath5;
  private String imagePath6;
  private String imagePath7;
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
  /** 淘宝商品数据对象 */
  private Object objectItem;
  /**
   * 商品skuId
   */
  private Long productSkuId;
  /**
   * 
   * 商品促销价格
   */
  protected BigDecimal finalPrice;

  /***
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

  /****
   * 获利百分比
   */

  private BigDecimal costIncomeRatio;

  /**
   * 获利金额
   */
  private BigDecimal costIncomeMoney;

  public BigDecimal getCostIncomeRatio() {
    return costIncomeRatio;
  }

  public void setCostIncomeRatio(BigDecimal costIncomeRatio) {
    this.costIncomeRatio = costIncomeRatio;
  }

  /**
   * 商品销售价格
   */
  protected BigDecimal price;
  /**
   * 商品促销标签
   */
  protected String[] promotionInfoLebal;

  /**
   * 产品类型0：非药品1：OTC药品2：医疗器械
   */
  private Integer productType;

  protected String tag;
  /**
   * 促销活动
   */
  protected PromotionInfo salePromotionInfo;
  protected PromotionInfo discountPromotionInfo;
  protected List<PromotionInfo> reductionPromotionInfoList;
  protected List<PromotionInfo> couponPromotionInfoList;
  /**
   * 从数据库查询得到的promotionProduct
   */
  protected PromotionProduct promotionProduct;
  /** 所有促销活动包括特价打折满减满送 */
  protected List<PromotionInfo> promotionInfoList;
  /**
   * 不能参加其他活动
   * 
   * @return
   */
  protected Boolean excludeAll = false;

  /**
   * 限购下限
   */
  private int minBuy;
  /**
   * 限购上限
   */
  private int maxBuy;
  /**
   * 活动库存
   */
  private int promotionStock;
  /**
   * 活动已售量
   */
  private int promotionSell;

  /**
   * 活动开始时间
   */
  private Date promoStartTime;
  /**
   * 活动开始时间
   */
  private Date promoEndTime;

  /**
   * 活动期间可购买量
   */
  private int promoBuyNum;
  /**
   * 限购产品购买数量
   */
  private Integer restrictionAmount = 0;

  /**
   * 限购类型0特价；1打折；2无限购
   */
  private Integer restrictionType;

  /**
   * 商户ID
   */
  private Long sellerId;

  /**
   * 商家类型
   */
  private Integer supplierType;

  /**
   * 下架
   */
  private Boolean isOutOfStock = false;
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

  /**
   * 产品状态
   */
  private Integer productStatus;
  /**
   * sku状态
   */
  private Integer skuStatus;

  /**
   * 下架
   */
  public Boolean getIsOutOfStock() {
    if (isOutOfStock == null)
      return false;
    return isOutOfStock;
  }

  /**
   * 下架
   */
  public void setIsOutOfStock(Boolean isOutOfStock) {
    this.isOutOfStock = isOutOfStock;
  }

  public PromotionInfo getSalePromotionInfo() {
    return salePromotionInfo;
  }

  public void setSalePromotionInfo(PromotionInfo salePromotionInfo) {
    this.salePromotionInfo = salePromotionInfo;
  }

  public PromotionInfo getDiscountPromotionInfo() {
    return discountPromotionInfo;
  }

  public void setDiscountPromotionInfo(PromotionInfo discountPromotionInfo) {
    this.discountPromotionInfo = discountPromotionInfo;
  }

  public List<PromotionInfo> getReductionPromotionInfoList() {
    if (reductionPromotionInfoList == null) {
      reductionPromotionInfoList = new ArrayList<PromotionInfo>();
    }
    return reductionPromotionInfoList;
  }

  public void setReductionPromotionInfoList(List<PromotionInfo> reductionPromotionInfoList) {
    this.reductionPromotionInfoList = reductionPromotionInfoList;
  }

  public List<PromotionInfo> getCouponPromotionInfoList() {
    if (couponPromotionInfoList == null) {
      couponPromotionInfoList = new ArrayList<PromotionInfo>();
    }
    return couponPromotionInfoList;
  }

  public void setCouponPromotionInfoList(List<PromotionInfo> couponPromotionInfoList) {
    this.couponPromotionInfoList = couponPromotionInfoList;
  }

  public String[] getPromotionInfoLebal() {
    return promotionInfoLebal;
  }

  public void setPromotionInfoLebal(String[] promotionInfoLebal) {
    this.promotionInfoLebal = promotionInfoLebal;
  }

  public String getMapKey() {
    return "BaseProduct:id=" + this.getProductSkuId();
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
   * 是否强制排他
   * 
   * @return
   */

  public void addPromotionInMap(PromotionInfo info) {
    this.getPromotionInfoMap().put(info.getPromotionId(), info);
  }

  public Boolean getExcludeAll() {

    return excludeAll;
  }

  /**
   * 是否强制排他
   * 
   * @return
   */
  public void setExcludeAll(Boolean excludeAll) {
    this.excludeAll = excludeAll;
  }

  /** 所有促销活动包括特价打折满减满送 */

  public List<PromotionInfo> getPromotionInfoList() {
    return promotionInfoList;
  }

  /** 所有促销活动包括特价打折满减满送 */
  public void setPromotionInfoList(List<PromotionInfo> promotionInfoList) {
    this.promotionInfoList = promotionInfoList;
  }

  /** 商品销售价格 */
  public BigDecimal getPrice() {
    return null == price ? null : NumberUtil.toBigDecimal(price);
  }

  /** 商品销售价格 */
  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  /** 右上角标签 */
  public String getTag() {
    return tag;
  }

  /** 右上角标签 */
  public void setTag(String tag) {
    this.tag = tag;
  }

  public int getMinBuy() {
    return minBuy;
  }

  public void setMinBuy(int minBuy) {
    this.minBuy = minBuy;
  }

  public void setMinBuy(Integer minBuy) {
    this.minBuy = null == minBuy ? 0 : minBuy;
  }

  public int getMaxBuy() {
    return maxBuy;
  }

  public void setMaxBuy(int maxBuy) {
    this.maxBuy = maxBuy;
  }

  public void setMaxBuy(Integer maxBuy) {
    this.maxBuy = null == maxBuy ? 0 : maxBuy;
  }

  public int getPromotionStock() {
    return promotionStock;
  }

  public void setPromotionStock(int promotionStock) {
    this.promotionStock = promotionStock;
  }

  public void setPromotionStock(Integer promotionStock) {
    this.promotionStock = null == promotionStock ? 0 : promotionStock;
  }

  public int getPromotionSell() {
    return promotionSell;
  }

  public void setPromotionSell(int promotionSell) {
    this.promotionSell = promotionSell;
  }

  public void setPromotionSell(Integer promotionSell) {
    this.promotionSell = null == promotionSell ? 0 : promotionSell;
  }

  public Date getPromoStartTime() {
    return promoStartTime;
  }

  public void setPromoStartTime(Date promoStartTime) {
    this.promoStartTime = promoStartTime;
  }

  public Date getPromoEndTime() {
    return promoEndTime;
  }

  public void setPromoEndTime(Date promoEndTime) {
    this.promoEndTime = promoEndTime;
  }

  public int getPromoBuyNum() {
    return promoBuyNum;
  }

  public void setPromoBuyNum(int promoBuyNum) {
    this.promoBuyNum = promoBuyNum;
  }

  public void setPromoBuyNum(Integer promoBuyNum) {
    this.promoBuyNum = null == promoBuyNum ? 0 : promoBuyNum;
  }

  public Integer getRestrictionType() {
    return restrictionType;
  }

  public void setRestrictionType(Integer restrictionType) {
    this.restrictionType = restrictionType;
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

  public PromotionProduct getPromotionProduct() {
    return promotionProduct;
  }

  public void setPromotionProduct(PromotionProduct promotionProduct) {
    this.promotionProduct = promotionProduct;
  }

  public Long getProductID() {
    return productID;
  }

  public void setProductID(Long productID) {
    this.productID = productID;
  }

  public Integer getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
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

  public String getSupplierCode() {
    return supplierCode;
  }

  public void setSupplierCode(String supplierCode) {
    this.supplierCode = supplierCode;
  }

  public BigDecimal getUnitWeight() {
    return unitWeight;
  }

  public void setUnitWeight(BigDecimal unitWeight) {
    this.unitWeight = unitWeight;
  }

  public String getProductDesc() {
    return productDesc;
  }

  public void setProductDesc(String productDesc) {
    this.productDesc = productDesc;
  }

  public String getProductNo() {
    return productNo;
  }

  public void setProductNo(String productNo) {
    this.productNo = productNo;
  }

  public String getProductSkuCode() {
    return productSkuCode;
  }

  public void setProductSkuCode(String productSkuCode) {
    this.productSkuCode = productSkuCode;
  }

  public Boolean getIsChecked() {
    return isChecked;
  }

  public void setIsChecked(Boolean isChecked) {
    this.isChecked = isChecked;
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

  public Object getObjectItem() {
    return objectItem;
  }

  public void setObjectItem(Object objectItem) {
    this.objectItem = objectItem;
  }

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  /**
   * 所有活动（特价打折满减满送）
   */
  private Map<Long, PromotionInfo> promotionInfoMap;

  /**
   * 所有活动（特价打折满减满送）
   */
  public Map<Long, PromotionInfo> getPromotionInfoMap() {
    if (promotionInfoMap == null) {
      promotionInfoMap = new ConcurrentHashMap<Long, PromotionInfo>();
    }
    return promotionInfoMap;
  }

  public void removePromotionInMap(PromotionInfo info) {
    this.getPromotionInfoMap().remove(info.getPromotionId());
  }

  /**
   * 按互斥id移除
   * 
   * @param type
   */
  public void removePromotionInMap(String mutualIds) {
    if (mutualIds == null || mutualIds.isEmpty()) {
      return;
    }
    String[] ids = mutualIds.split(",");
    if (ids == null || ids.length == 0) {
      return;
    }
    promotionInfoMap = this.getPromotionInfoMap();
    for (String id : ids) {
      if (id != null && !id.isEmpty())
        promotionInfoMap.remove(Long.valueOf(id));
    }
    this.setPromotionInfoMap(promotionInfoMap);
  }

  /**
   * 所有活动（特价打折满减满送）
   */
  public void setPromotionInfoMap(Map<Long, PromotionInfo> promotionInfoMap) {
    this.promotionInfoMap = promotionInfoMap;
  }

  /**
   * 按类型移除
   * 
   * @param type
   */
  public void removePromotionInMap(Integer type) {
    promotionInfoMap = this.getPromotionInfoMap();
    Set<Long> keySet = promotionInfoMap.keySet();
    Iterator<Long> iterator = keySet.iterator();
    List<Long> removeIds = new ArrayList<Long>();
    while (iterator.hasNext()) {
      Long key = iterator.next();
      PromotionInfo info = promotionInfoMap.get(key);
      if (info.getPromotionType().compareTo(type) == 0) {
        removeIds.add(info.getPromotionId());
      }
    }
    keySet.removeAll(removeIds);
    this.setPromotionInfoMap(promotionInfoMap);

  }

  /**
   * 设置活动顺序，是否排他和最终价格
   * 
   * @return
   */
  public <T extends BaseProduct> void initBaseProduct() {
    this.setExcludeAll(false);
    List<PromotionInfo> list = this.addPromotionToList();
    // 没有促销活动
    if (list == null || list.size() == 0) {
      return;
    }
    this.setPromotionInfoList(list);
    // 没有特价和打折
    PromotionInfo sale = this.getSalePromotionInfo();
    PromotionInfo disc = this.getDiscountPromotionInfo();
    if (sale != null) {
      if (null != sale.getPrivilegeData()) {
        this.setFinalPrice(sale.getPrivilegeData());
      } else if (null != sale.getPromotionData()) {
        this.setFinalPrice(sale.getPromotionData());
      } else if (null != promotionProduct) {
        this.setFinalPrice(promotionProduct.getPrice());
      }
      if ((null != sale.getMinBuy() && sale.getMinBuy() > 0)
          || (null != sale.getMaxBuy() && sale.getMaxBuy() > 0)
          || (null != sale.getPromotionStock() && sale.getPromotionStock() > 0)) {
        this.setMinBuy(sale.getMinBuy());
        this.setMaxBuy(sale.getMaxBuy());
        this.setPromotionStock(sale.getPromotionStock());
        this.setPromotionSell(sale.getPromotionSell());
        this.setPromoStartTime(sale.getStartTime());
        this.setPromoEndTime(sale.getEndTime());
      }
    }
    if (disc != null) {
      if (null != disc.getPromotionData()) {
        this.setFinalPrice(
            disc.getPromotionData().multiply(BigDecimal.valueOf(0.1).multiply(finalPrice)));
      }
      if (sale != null) {
        return;
      }
      if ((null != disc.getMinBuy() && disc.getMinBuy() > 0)
          || (null != disc.getMaxBuy() && disc.getMaxBuy() > 0)
          || (null != disc.getPromotionStock() && disc.getPromotionStock() > 0)) {
        this.setMinBuy(disc.getMinBuy());
        this.setMaxBuy(disc.getMaxBuy());
        this.setPromotionStock(disc.getPromotionStock());
        this.setPromotionSell(disc.getPromotionSell());
        this.setPromoStartTime(disc.getStartTime());
        this.setPromoEndTime(disc.getEndTime());
      }
    }
  }

  /**
   * 设置活动顺序、lebal标签 重要方法
   * 
   * @return
   */
  private List<PromotionInfo> addPromotionToList() {
    List<PromotionInfo> list = new ArrayList<PromotionInfo>();
    String saleMutualIds = "";
    String disMutualIds = "";
    if (salePromotionInfo != null) {
      saleMutualIds = salePromotionInfo.getMutexPromotionId();
    }
    if (discountPromotionInfo != null) {
      disMutualIds = discountPromotionInfo.getMutexPromotionId();
    }
    if (null == saleMutualIds) {
      saleMutualIds = "";
    }
    if (null == disMutualIds) {

      disMutualIds = "";
    }
    if (saleMutualIds.equals(Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG)) {
      excludeAll = true;
      discountPromotionInfo = null;

    } else {
      if (disMutualIds.equals(Constants.PROMOTION_MUTEX_ALL_PROMOTION_FLAG)) {
        excludeAll = true;
        salePromotionInfo = null;
      } else {
        if (discountPromotionInfo != null
            && saleMutualIds.contains(discountPromotionInfo.getPromotionId().toString())) {
          discountPromotionInfo = null;
        }
      }
    }
    StringBuffer lebal = new StringBuffer();
    if (discountPromotionInfo != null) {
      list.add(0, discountPromotionInfo);
      lebal.append(PromotionTypeEnums.DISCOUNT.getTitle()).append(',');
    }
    if (salePromotionInfo != null) {
      list.add(0, salePromotionInfo);
      lebal.append(PromotionTypeEnums.SALE.getTitle()).append(',').append(lebal);
    }
    if (excludeAll) {
      this.setPromotionInfoList(null);
    } else {
      String mutualIds = saleMutualIds + "," + disMutualIds;
      if (ListUtils.isNotEmpty(promotionInfoList)) {
        PromotionInfoUtil.sortPromotionInfoList(promotionInfoList);
        for (PromotionInfo p : promotionInfoList) {
          if (!mutualIds.contains(p.getPromotionId().toString())) {
            Integer pomotiontype = p.getPromotionType();
            String ne = PromotionInfoUtil.typeNameMap.get(pomotiontype);
            if (lebal.indexOf(ne) < 0) {
              lebal.append(',').append(ne);
            }
            list.add(p);
          }
        }
      } else {
        this.setPromotionInfoList(list);
      }
    }
    return list;
  }

  public Integer getRestrictionAmount() {
    return restrictionAmount;
  }

  public void setRestrictionAmount(Integer restrictionAmount) {
    this.restrictionAmount = restrictionAmount;
  }

  public Integer getProductType() {
    return productType;
  }

  public void setProductType(Integer productType) {
    this.productType = productType;
  }

  /**
   * 商品促销价格
   */
  public void setFinalPrice(BigDecimal finalPrice) {
    this.finalPrice = finalPrice;
  }

  /**
   * 商品促销价格
   */
  public BigDecimal getFinalPrice() {
    return null == finalPrice ? null : NumberUtil.toBigDecimal(finalPrice);
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

  public BigDecimal getPvalue() {
    return pvalue;
  }

  public void setPvalue(BigDecimal pvalue) {
    this.pvalue = pvalue;
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
}
