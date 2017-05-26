package com.kmzyc.b2b.shopcart.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class SubTotal implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 847722896487294449L;

  /** 总的产品数量 */
  protected int productCount = 0;

  /** 选中的产品数量 */
  protected int checkedProductCount = 0;

  /*** 时代会员打折金额 ***/
  protected BigDecimal discountMoney = BigDecimal.ZERO;

  /**** pv总计 ****/
  protected BigDecimal productPvalue = BigDecimal.ZERO;
  /** 选中商品的id */
  protected transient StringBuilder checkIdsBuilder;
  /** 未选中商品的id */
  protected transient StringBuilder noneCheckIdsBuilder;
  /** 总减免金额 选中 */
  protected BigDecimal reductionMoney = BigDecimal.ZERO;

  /** 加价购金额 选中 */
  protected BigDecimal additionalMoney = BigDecimal.ZERO;

  /** 没有计算活动金额选中商品 */
  protected BigDecimal uncalculateMoney = BigDecimal.ZERO;

  /** 小计总金额 选中 */
  protected BigDecimal checkTotalMoney = BigDecimal.ZERO;

  /** 所有总计 */
  protected BigDecimal allMoney = BigDecimal.ZERO;

  /** 重量 */
  protected transient BigDecimal weight = BigDecimal.ZERO;
  /**
   * 商家首重运费
   */
  public BigDecimal firstHeavyFreight = BigDecimal.ZERO;

  /** 运费 */
  protected BigDecimal freight = BigDecimal.ZERO;

  /** 商品免运费金额 */
  protected BigDecimal freePrice = BigDecimal.ZERO;



  public BigDecimal getFirstHeavyFreight() {
    return firstHeavyFreight;
  }

  public void setFirstHeavyFreight(BigDecimal firstHeavyFreight) {
    this.firstHeavyFreight = firstHeavyFreight;
  }

  public BigDecimal getFreePrice() {
    return freePrice;
  }

  public void setFreePrice(BigDecimal freePrice) {
    this.freePrice = freePrice;
  }

  public int getProductCount() {
    return productCount;
  }

  public int getCheckedProductCount() {
    return checkedProductCount;
  }

  public void addProductCount(int productCount) {
    this.productCount += productCount;
  }

  public void addCheckedProductCount(int checkedProductCount) {
    this.checkedProductCount = checkedProductCount + this.checkedProductCount;
  }

  /** 总减免金额 选中 */
  public BigDecimal getReductionMoney() {
    return reductionMoney;
  }

  /** 总减免金额 选中 */
  public BigDecimal getAdditionalMoney() {
    return additionalMoney;
  }

  /**
   * 选中小计金额 计算了活动
   */
  public void addCheckTotalMoney(BigDecimal money) {
    if (money.compareTo(BigDecimal.ZERO) == 0) {
      return;
    }
    checkTotalMoney = checkTotalMoney.add(money);
  }

  /** 小计总金额 选中 */
  public void setCheckTotalMoney(BigDecimal checkTotalMoney) {
    this.checkTotalMoney = checkTotalMoney;
  }

  /*** 时代会员打折金额 ****/
  public void addDiscountMoney(BigDecimal money) {
    discountMoney = discountMoney.add(money);
  }

  /*** 时代会员打折金额 ***/
  public BigDecimal getDiscountMoney() {
    return discountMoney;
  }

  /*** 时代会员打折金额 ***/
  public void setDiscountMoney(BigDecimal discountMoney) {
    this.discountMoney = discountMoney;
  }

  /**** pv总值设置 ****/
  public void addTotalProductPvalue(BigDecimal pvalue) {
    productPvalue = productPvalue.add(pvalue);
  }

  /** 选中 满减金额 */
  public void setReductionMoney(BigDecimal money) {
    this.reductionMoney = money;
  }

  /** 选中 满减金额 */
  public void addReductionMoney(BigDecimal money) {
    if (money.compareTo(BigDecimal.ZERO) == 0) {
      return;
    }
    reductionMoney = reductionMoney.add(money);
  }

  /** 选中 加价购金额 */
  public void addItionalMoney(BigDecimal money) {
    if (money.compareTo(BigDecimal.ZERO) == 0) {
      return;
    }
    additionalMoney = additionalMoney.add(money);
  }

  /** 选中 总计 未计算活动 */
  public void addUncalculateMoney(BigDecimal money) {
    if (money.compareTo(BigDecimal.ZERO) == 0) {
      return;
    }
    uncalculateMoney = uncalculateMoney.add(money);
  }

  /** 所有总计 包括未选择中的 */
  public void addAllMoney(BigDecimal money) {
    if (money.compareTo(BigDecimal.ZERO) == 0) {
      return;
    }
    allMoney = allMoney.add(money);
  }

  /** checked为true 添加选择，false添加未选择 */
  public void addCheckProductId(String id, boolean checked) {
    if (checked) {
      if (checkIdsBuilder == null) {
        checkIdsBuilder = new StringBuilder();
      }
      if (checkIdsBuilder.indexOf(id) < 0) {
        checkIdsBuilder.append(",").append(id);
      }
    } else {
      if (noneCheckIdsBuilder == null) {
        noneCheckIdsBuilder = new StringBuilder();
      }
      if (noneCheckIdsBuilder.indexOf(id) < 0) {
        noneCheckIdsBuilder.append(",").append(id);
      }
    }

  }

  public String getCheckIds() {
    if (checkIdsBuilder == null) {
      return null;
    }
    return checkIdsBuilder.substring(1, checkIdsBuilder.length());
  }

  public String getNoneCheckIds() {
    if (noneCheckIdsBuilder == null) {
      return null;
    }
    return noneCheckIdsBuilder.substring(1, noneCheckIdsBuilder.length());
  }

  /**
   * 没有计算加价购和满减的金额选中
   */
  public BigDecimal getUncalculateMoney() {
    return uncalculateMoney;
  }

  /**
   * 选中小计金额 计算了活动
   */
  public BigDecimal getCheckTotalMoney() {
    return checkTotalMoney;
  }

  public BigDecimal getAllMoney() {
    return allMoney;
  }

  public BigDecimal getProductPvalue() {
    return productPvalue;
  }

  public void setProductPvalue(BigDecimal productPvalue) {
    this.productPvalue = productPvalue;
  }

  public void setProductCount(int productCount) {
    this.productCount = productCount;
  }

  /** 加价购 金额 */
  public void setAdditionalMoney(BigDecimal additionalMoney) {
    this.additionalMoney = additionalMoney;
  }

  /** 重量 */
  public BigDecimal getWeight() {
    return weight;
  }

  /** 重量 */
  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  /** 重量 */
  public void addWeight(BigDecimal weight) {
    if (weight.compareTo(BigDecimal.ZERO) == 0) {
      return;
    }
    this.weight = this.weight.add(weight);
  }

  /** 运费 */
  public BigDecimal getFreight() {
    return freight;
  }

  /** 运费 */
  public void setFreight(BigDecimal freight) {
    this.freight = freight;
  }

  /** 运费 */
  public void addFreight(BigDecimal freight) {
    if (freight.compareTo(BigDecimal.ZERO) == 0) {
      return;
    }
    this.freight = this.freight.add(freight);
  }

  public void setAllMoney(BigDecimal allMoney) {
    this.allMoney = allMoney;
  }


}
