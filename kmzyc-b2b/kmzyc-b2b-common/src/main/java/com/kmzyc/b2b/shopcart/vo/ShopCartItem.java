package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import com.alibaba.fastjson.annotation.JSONField;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kmzyc.promotion.optimization.vo.Promotion;

public class ShopCartItem extends SubTotal implements Serializable, Comparable<ShopCartItem> {
  /**
   * 
   */
  private static final long serialVersionUID = 9177774115540323984L;
  private String id;
  private Promotion promtionInfo;
  /** 商品列表 */
  private Set<String> carProducts;
  private Set<ShopCartProduct> carProductSet;
  /** 可以选的加价购 （skuid img stock price title） */
  private Map<String, Gift> ruleGifts;

  /** 已选加价购 （skuid img stock price title） */
  private Set<Gift> gifts;

  /** 是否满足 */
  private Boolean meet = false;

  /** 当前活动梯度可以选的奖品总数量 */
  private int giftCount;
  /** 当前活动梯度的金额 */
  private BigDecimal meetData;
  /** 已经选择的奖品数量 */
  private int countChoosed;

  /** 可选赠品 */
  private Map<String, List<Gift>> rulePresents;
  /** 已选赠品 梯度值 */
  private String presents;
  /** 默认赠品 */
  private String defaultPresents;

  /** 已选赠品数量 */
  private int countPresents;

  /** 送券 */
  private Gift coupon;

  private Long time = 0l;

  public void addProduct(ShopCartProduct p) {
    if (carProducts == null) {
      carProducts = new HashSet<String>();
    }
    if (carProductSet == null) {
      carProductSet = new TreeSet<ShopCartProduct>();
    }
    // gifts.
    carProducts.add(p.getPid());
    carProductSet.add(p);
    Long ptime = p.getMap().getLong("time");
    if (ptime != null && time < ptime) {
      time = ptime;
    }
  }

  public Promotion getPromtionInfo() {
    return promtionInfo;
  }

  public void setPromtionInfo(Promotion promtionInfo) {
    this.promtionInfo = promtionInfo;
  }

  public Boolean getMeet() {
    return meet;
  }


  public void setMeet(Boolean meet) {
    this.meet = meet;
  }

  public ShopCartItem(String id) {
    this.id = id;
  }

  public ShopCartItem() {

  }

  public String getId() {
    return id;
  }


  public void setId(String id) {
    this.id = id;
  }

  public Set<String> getCarProducts() {
    return carProducts;
  }

  public void setCarProducts(Set<String> carProducts) {
    this.carProducts = carProducts;
  }

  @JSONField(serialize = false)
  public String getTag() {
    if (promtionInfo == null) {
      return null;
    }
    if (!meet) {
      return null;
    }
    switch (promtionInfo.getType()) {
      case 6:// 满减
        return null;
      case 5:// 加价购
        return "换购商品";
      case 4:// 送券
        return null;
      case 3:// 满赠
        return "选择赠品";
    }
    return null;
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof ShopCartItem) {
      ShopCartItem item = (ShopCartItem) obj;
      return id.equals(item.getId());
    }
    return false;
  }



  // (已满足，还可换购2件)
  // (已减¥10.00元)
  // 已满足
  public String getDescribe() {
    if (promtionInfo == null) {
      return null;
    }

    if (!meet) {
      return "";
    }

    switch (promtionInfo.getType()) {
      case 6:// 满减
        StringBuilder sbm = new StringBuilder();
        sbm.append("(已减").append(this.getReductionMoney().toString()).append("元)");
        return sbm.toString();
      case 5:// 加价购
        StringBuilder sbm2 = new StringBuilder("(已满足,还可以换购");
        sbm2.append((this.giftCount - this.countChoosed) + "件)");
        return sbm2.toString();
      case 4:// 送券
        // StringBuilder sbm2 = new StringBuilder();
        // sbm2.append("(已满足，可得券:").append(this.getCoupon().getName()).append(".)");
        return "(已满足)";
      case 3:// 满赠
        return "(已满足)";
    }

    return null;
  }

  /** 可以选的加价购 */
  public Map<String, Gift> getRuleGifts() {
    return ruleGifts;
  }


  /** 可以选的加价购 */
  public void setRuleGifts(Map<String, Gift> ruleGifts) {
    this.ruleGifts = ruleGifts;
  }

  /** 可以选的加价购 */
  public void addRuleGift(Gift gift) {
    if (this.ruleGifts == null) {
      this.ruleGifts = Maps.newTreeMap();
    }
    this.ruleGifts.put(gift.getDataId(), gift);
  }

  /** 已选加价购 */
  public Set<Gift> getGifts() {
    return gifts;
  }


  /** 已选加价购 */
  public void setGifts(Set<Gift> gifts) {
    this.gifts = gifts;
  }


  /** 当前活动梯度可以选的奖品总数量 */
  public int getGiftCount() {
    return giftCount;
  }


  /** 当前活动梯度可以选的奖品总数量 */
  public void setGiftCount(int giftCount) {
    this.giftCount = giftCount;
  }


  /** 当前活动梯度的金额 或者数量 */
  public BigDecimal getMeetData() {
    return meetData;
  }


  /** 当前活动梯度的金额 或者数量 */
  public void setMeetData(BigDecimal meetData) {
    this.meetData = meetData;
  }


  /** 已经选择的奖品数量 */
  public int getCountChoosed() {
    return countChoosed;
  }


  /** 已经选择的奖品数量 */
  public void setCountChoosed(int countChoosed) {
    this.countChoosed = countChoosed;
  }


  /*** 可选赠品 key为梯度值 */
  public Map<String, List<Gift>> getRulePresents() {
    return rulePresents;
  }

  /*** 可选赠品 key为梯度值 */
  public void setRulePresents(Map<String, List<Gift>> rulePresents) {
    this.rulePresents = rulePresents;
  }

  /*** 可选赠品 key为梯度值 */
  public void addRulePresents(String meetDataKey, Gift present) {
    if (rulePresents == null) {
      rulePresents = Maps.newHashMap();
    }
    List<Gift> list = rulePresents.get(meetDataKey);
    if (list == null) {
      list = Lists.newArrayList();
      rulePresents.put(meetDataKey, list);
    }
    list.add(present);
  }

  /** 已选赠品 */
  public String getPresents() {
    return presents;
  }

  /** 已选赠品 */
  public void setPresents(String presents) {
    this.presents = presents;
  }

  /** 已送优惠券 */
  public Gift getCoupon() {
    return coupon;
  }

  /** 已送优惠券 */
  public void setCoupon(Gift coupon) {
    this.coupon = coupon;
  }

  /** 默认赠品 自选后默认赠品会修改 */
  public String getDefaultPresents() {
    return defaultPresents;
  }

  /** 默认赠品 自选后默认赠品会修改 */
  public void setDefaultPresents(String defaultPresents) {
    this.defaultPresents = defaultPresents;
  }

  /** 活动名称 */
  public String getPromotionName() {
    if (promtionInfo == null) {
      return null;
    }
    return promtionInfo.getName();
  }

  /** 活动类型 */
  public Integer getPromotionType() {
    if (promtionInfo == null) {
      return null;
    }
    return promtionInfo.getType();
  }

  /** 活动名称 */
  public String getPromotionTypeName() {
    if (promtionInfo == null) {
      return null;
    }
    return promtionInfo.getTypeName();
  }

  public Long getTime() {
    return time;
  }

  @Override
  public int compareTo(ShopCartItem o) {
    int compare = this.time.compareTo(o.getTime());
    if (compare == 0) {
      compare = this.id.compareTo(o.getId());
    }
    return compare * -1;
  }

  public Set<ShopCartProduct> getCarProductSet() {
    return carProductSet;
  }

  public int getCountPresents() {
    return countPresents;
  }

  public void setCountPresents(int countPresents) {
    this.countPresents = countPresents;
  }



}
