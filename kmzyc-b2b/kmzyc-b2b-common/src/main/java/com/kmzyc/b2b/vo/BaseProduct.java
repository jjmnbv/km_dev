package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.json.annotations.JSON;

import com.kmzyc.b2b.model.PromotionInfo;
import com.kmzyc.b2b.model.PromotionProduct;
import com.kmzyc.b2b.util.NumberUtil;
import com.kmzyc.b2b.util.PromotionInfoUtil;
import com.kmzyc.promotion.app.enums.PromotionTypeEnums;
import com.kmzyc.promotion.app.util.ListUtils;
import com.kmzyc.promotion.optimization.vo.ProductPromotion;

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
  private static final long serialVersionUID = -2008643906551723188L;
  /**
   * 商品skuId
   */
  protected Long productSkuId;
  /**
   * 商品促销价格
   */
  protected BigDecimal finalPrice;

  /**
   * 商品销售价格
   */
  protected BigDecimal price;
  /**
   * 商品促销标签
   */
  protected transient String[] promotionInfoLebal;

  protected transient String tag;
  /**
   * 促销活动
   */
  protected transient PromotionInfo salePromotionInfo;
  protected transient PromotionInfo discountPromotionInfo;
  protected transient List<PromotionInfo> reductionPromotionInfoList;
  protected transient List<PromotionInfo> couponPromotionInfoList;
  // 从数据库查询得到的promotionProduct
  protected transient PromotionProduct promotionProduct;
  /** 所有促销活动包括特价打折满减满送 */
  protected transient List<PromotionInfo> promotionInfoList;
  /** 商品所有的 */
  protected transient ProductPromotion productPromotion;
  /**
   * 不能参加其他活动
   * 
   * @return
   */
  protected transient Boolean excludeAll = false;

  /**
   * 商品skuId
   */
  public Long getProductSkuId() {
    return this.productSkuId;
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
    return NumberUtil.toBigDecimal(finalPrice);
  }

  /**
   * 商品促销skuId
   */
  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  /**
   * 限购下限
   */
  private transient int minBuy;
  /**
   * 限购上限
   */
  private transient int maxBuy;
  /**
   * 活动库存
   */
  private transient int promotionStock;
  /**
   * 活动已售量
   */
  private transient int promotionSell;

  /**
   * 活动开始时间
   */
  private transient Date promoStartTime;
  /**
   * 活动开始时间
   */
  private transient Date promoEndTime;

  /**
   * 活动期间可购买量
   */
  private transient int promoBuyNum;

  /**
   * 限购类型0特价；1打折；2无限购
   */
  private transient Integer restrictionType;

  /**
   * 商户ID
   */
  private Long sellerId;

  /**
   * 商家类型
   */
  private Integer supplierType;

  @JSON(serialize = false)
  public PromotionInfo getSalePromotionInfo() {
    return salePromotionInfo;
  }

  public void setSalePromotionInfo(PromotionInfo salePromotionInfo) {
    this.salePromotionInfo = salePromotionInfo;
  }

  @JSON(serialize = false)
  public PromotionInfo getDiscountPromotionInfo() {
    return discountPromotionInfo;
  }

  public void setDiscountPromotionInfo(PromotionInfo discountPromotionInfo) {
    this.discountPromotionInfo = discountPromotionInfo;
  }

  @JSON(serialize = false)
  public List<PromotionInfo> getReductionPromotionInfoList() {
    if (reductionPromotionInfoList == null) {
      reductionPromotionInfoList = new ArrayList<PromotionInfo>();
    }
    return reductionPromotionInfoList;
  }

  public void setReductionPromotionInfoList(List<PromotionInfo> reductionPromotionInfoList) {
    this.reductionPromotionInfoList = reductionPromotionInfoList;
  }

  @JSON(serialize = false)
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
      saleMutualIds = salePromotionInfo.getMutualIds();
    }
    if (discountPromotionInfo != null) {
      disMutualIds = discountPromotionInfo.getMutualIds();
    }
    if (saleMutualIds.equals("all")) {
      excludeAll = true;
      discountPromotionInfo = null;

    } else {
      if (disMutualIds.equals("all")) {
        excludeAll = true;
        salePromotionInfo = null;
      } else {
        if (discountPromotionInfo != null
            && saleMutualIds.contains(discountPromotionInfo.getPromotionId().toString())) {
          discountPromotionInfo = null;
        }
      }
    }
    String lebal = "";
    if (discountPromotionInfo != null) {
      list.add(0, discountPromotionInfo);
      lebal = PromotionTypeEnums.DISCOUNT.getTitle() + ",";
    }
    if (salePromotionInfo != null) {
      list.add(0, salePromotionInfo);
      lebal = PromotionTypeEnums.SALE.getTitle() + "," + lebal;
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
            if (!lebal.contains(ne)) {
              lebal = lebal + "," + ne;
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

  /**
   * 是否强制排他
   * 
   * @return
   */
  @JSON(serialize = false)
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
  @JSON(serialize = false)
  public List<PromotionInfo> getPromotionInfoList() {
    return promotionInfoList;
  }

  /** 所有促销活动包括特价打折满减满送 */
  public void setPromotionInfoList(List<PromotionInfo> promotionInfoList) {
    this.promotionInfoList = promotionInfoList;
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
      if (null != sale.getPromotionData()) {
        this.setFinalPrice(sale.getPromotionData());
      } else {
        // TODO 设值
        if (null != promotionProduct) {
          this.setFinalPrice(promotionProduct.getPrice());
        }
      }
      if (sale.getMinBuy() > 0 || sale.getMaxBuy() > 0 || sale.getPromotionStock() > 0) {
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
        this.setFinalPrice(disc.getPromotionData().multiply(
            BigDecimal.valueOf(0.1).multiply(finalPrice)));
      }
      if (sale != null) {
        return;
      }
      if (disc.getMinBuy() > 0 || disc.getMaxBuy() > 0 || disc.getPromotionStock() > 0) {
        this.setMinBuy(disc.getMinBuy());
        this.setMaxBuy(disc.getMaxBuy());
        this.setPromotionStock(disc.getPromotionStock());
        this.setPromotionSell(disc.getPromotionSell());
        this.setPromoStartTime(disc.getStartTime());
        this.setPromoEndTime(disc.getEndTime());
      }
    }

  }

  @JSON(serialize = false)
  public String getMapKey() {
    return "BaseProduct:id=" + this.getProductSkuId();
  }

  public static void main(String[] ag) {
    String abc = "a,b,d,c,,";
    System.out.println(abc.split(",").length);
  }

  /** 商品销售价格 */
  public BigDecimal getPrice() {
    return NumberUtil.toBigDecimal(price);
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

  public Integer getMinBuy() {
    return minBuy;
  }

  public void setMinBuy(int minBuy) {
    this.minBuy = minBuy;
  }

  public void setMinBuy(Integer minBuy) {
    this.minBuy = null == minBuy ? -1 : minBuy;
  }

  public Integer getMaxBuy() {
    return maxBuy;
  }

  public void setMaxBuy(int maxBuy) {
    this.maxBuy = maxBuy;
  }

  public void setMaxBuy(Integer maxBuy) {
    this.maxBuy = null == maxBuy ? -1 : maxBuy;
  }

  public Integer getPromotionStock() {
    return promotionStock;
  }

  public void setPromotionStock(int promotionStock) {
    this.promotionStock = promotionStock;
  }

  public void setPromotionStock(Integer promotionStock) {
    this.promotionStock = null == promotionStock ? -1 : promotionStock;
  }

  public int getPromotionSell() {
    return promotionSell;
  }

  public void setPromotionSell(int promotionSell) {
    this.promotionSell = promotionSell;
  }

  public void setPromotionSell(Integer promotionSell) {
    this.promotionSell = null == promotionSell ? -1 : promotionSell;
  }

  @JSON(serialize = false)
  public Date getPromoStartTime() {
    return promoStartTime;
  }

  public void setPromoStartTime(Date promoStartTime) {
    this.promoStartTime = promoStartTime;
  }

  @JSON(serialize = false)
  public Date getPromoEndTime() {
    return promoEndTime;
  }

  public void setPromoEndTime(Date promoEndTime) {
    this.promoEndTime = promoEndTime;
  }

  /**
   * 活动期间可购买量
   */
  public int getPromoBuyNum() {
    return promoBuyNum;
  }

  /**
   * 活动期间可购买量
   */
  public void setPromoBuyNum(int promoBuyNum) {
    this.promoBuyNum = promoBuyNum;
  }

  /**
   * 活动期间可购买量
   */
  public void setPromoBuyNum(Integer promoBuyNum) {
    this.promoBuyNum = null == promoBuyNum ? -1 : promoBuyNum;
  }


  @JSON(serialize = false)
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

  public ProductPromotion getProductPromotion() {
    return productPromotion;
  }

  public void setProductPromotion(ProductPromotion productPromotion) {
    this.productPromotion = productPromotion;
  }

}
