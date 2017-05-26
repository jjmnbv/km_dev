package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.kmzyc.promotion.app.vobject.OrderVo;

/**
 * 结算信息
 */
public class SettlementInfo implements Serializable {
  private static final long serialVersionUID = -810164514888491949L;
  /** 结算商品 */
  // private Map<String, ShopCartProduct> productMap;
  /** 满足的活动 */
  // private List<ShopCartItem> meetOrderPromotionList;
  /** 优惠券查询使用list */
  private List<OrderVo> orderVoList;

  private SubTotal subTotal;


  private BigDecimal amountAvlibal;

  /** 地址信息 */

  /** 结算商品 */
  // public Map<String, ShopCartProduct> getProductMap() {
  // return productMap;
  // }
  //
  // /** 结算商品 */
  // public void setProductMap(Map<String, ShopCartProduct> productMap) {
  // this.productMap = productMap;
  // }
  //
  // /** 满足的活动 */
  // public List<ShopCartItem> getMeetOrderPromotionList() {
  // return meetOrderPromotionList;
  // }
  //
  // /** 满足的活动 */
  // public void setMeetOrderPromotionList(List<ShopCartItem> meetOrderPromotionList) {
  // this.meetOrderPromotionList = meetOrderPromotionList;
  // }

  /** 优惠券查询使用list */
  public List<OrderVo> getOrderVoList() {
    return orderVoList;
  }

  /** 优惠券查询使用list */
  public void setOrderVoList(List<OrderVo> orderVoList) {
    this.orderVoList = orderVoList;
  }

  /** 汇总信息 */
  public SubTotal getSubTotal() {
    return subTotal;
  }

  /** 汇总信息 */
  public void setSubTotal(SubTotal subTotal) {
    this.subTotal = subTotal;
  }

  /** 账户可用金额 */
  public BigDecimal getAmountAvlibal() {
    return amountAvlibal;
  }

  /** 账户可用金额 */
  public void setAmountAvlibal(BigDecimal amountAvlibal) {
    this.amountAvlibal = amountAvlibal;
  }



}
