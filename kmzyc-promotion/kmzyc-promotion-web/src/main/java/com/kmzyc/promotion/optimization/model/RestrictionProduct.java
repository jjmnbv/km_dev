package com.kmzyc.promotion.optimization.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class RestrictionProduct implements Serializable {

  private static final long serialVersionUID = -7255097655005366451L;
  private Integer minBuy;
  private Integer maxBuy;
  private Integer promotionStock;
  private Integer promotionSell;
  private BigDecimal salePrice;
  private int type;



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

  public BigDecimal getSalePrice() {
    return salePrice;
  }

  public void setSalePrice(BigDecimal salePrice) {
    this.salePrice = salePrice;
  }

  /**
   * 卖光恢复原价1，卖光停止销售0或空
   */
  public int getType() {
    return type;
  }

  /**
   * 卖光恢复原价1，卖光停止销售0或空
   */
  public void setType(int type) {
    this.type = type;
  }



}
