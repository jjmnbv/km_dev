package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class PayMoney implements Serializable {
  private static final long serialVersionUID = 6241297008873181483L;
  // 总金额
  private Double moneyCount = 0d;
  // 运费
  private Double fare = 0d;
  // 满减
  private Double reductMoney = 0d;
  // 时代会员折扣率
  private BigDecimal discountMoney = BigDecimal.ZERO;
  // 使用优惠券Id
  private String couponid = "";
  // 使用优惠券金额
  private Double couponMoney = 0d;
  // 余额支付
  private Double balanceMoney = 0d;
  // 账户可使用金额
  private Double amountAvlibal;
  // 优惠券名称
  private String couponName;

  public String payMoney;

  private boolean compareFlag = false;

  // 优惠券使用限制 ， 1：不可和余额同时使用
  private String useLimitsType;
  /**
   * 加价购商品金额
   */
  private BigDecimal additionalMoney = new BigDecimal(0);

  private int score;

  private PayMoneyPresell payMoneyPresell = new PayMoneyPresell();

  /**
   * 付款金额 = 总金额-满减+运费-使用优惠券-余额支付-时代会员折扣率
   * 
   * @return
   */
  public String getPayMoney() {
    DecimalFormat format = new DecimalFormat("#0.00");
    BigDecimal result =
        new BigDecimal(moneyCount.toString()).subtract(new BigDecimal(reductMoney.toString()))
            .add(new BigDecimal(fare.toString())).subtract(new BigDecimal(couponMoney.toString()))
            .subtract(new BigDecimal(balanceMoney.toString())).add(additionalMoney)
            .subtract(discountMoney);
    return result.doubleValue() >= 0 ? format.format(result.setScale(2, BigDecimal.ROUND_HALF_UP)
        .doubleValue()) : "0.00";
  }

  public String getMinMoneyFromAmountAvlibalAndPayMoney() {
    String result = null;
    DecimalFormat formatter = new DecimalFormat("#0.00");
    BigDecimal tmpPayMoney =
        new BigDecimal(moneyCount.toString()).subtract(new BigDecimal(reductMoney.toString()))
            .add(new BigDecimal(fare.toString())).subtract(new BigDecimal(couponMoney.toString()))
            .subtract(new BigDecimal(balanceMoney.toString())).add(additionalMoney);
    BigDecimal amountAvlibal = new BigDecimal(this.amountAvlibal.toString());
    // tmpPayMoney<amountAvlibal
    if (tmpPayMoney.compareTo(amountAvlibal) < 0) {
      result =
          tmpPayMoney.doubleValue() >= 0 ? formatter.format(tmpPayMoney.setScale(2,
              BigDecimal.ROUND_HALF_UP).doubleValue()) : "0.00";
    } else {
      result =
          amountAvlibal.doubleValue() >= 0 ? formatter.format(amountAvlibal.setScale(2,
              BigDecimal.ROUND_HALF_UP).doubleValue()) : "0.00";
    }
    return result;
  }

  public boolean isCompareFlag() {
    BigDecimal tmpPayMoney =
        new BigDecimal(moneyCount.toString()).subtract(new BigDecimal(reductMoney.toString()))
            .add(new BigDecimal(fare.toString())).subtract(new BigDecimal(couponMoney.toString()))
            .subtract(new BigDecimal(balanceMoney.toString())).add(additionalMoney);
    BigDecimal amountAvlibal = new BigDecimal(this.amountAvlibal);
    if (tmpPayMoney.compareTo(amountAvlibal) > 0) {
      this.compareFlag = true;
    }
    return compareFlag;
  }

  public Double getBalanceMoney() {
    BigDecimal result =
        new BigDecimal(moneyCount.toString()).subtract(new BigDecimal(reductMoney.toString()))
            .add(new BigDecimal(fare.toString())).subtract(new BigDecimal(couponMoney.toString()))
            .subtract(new BigDecimal(balanceMoney.toString())).add(additionalMoney);
    if (balanceMoney != 0 && result.doubleValue() < 0) {
      balanceMoney =
          (new BigDecimal(balanceMoney.toString()).add(result)).setScale(2, BigDecimal.ROUND_FLOOR)
              .doubleValue();
    }
    return balanceMoney;
  }


  public Double getMoneyCount() {
    return moneyCount;
  }

  public void setMoneyCount(Double moneyCount) {
    this.moneyCount = moneyCount;
  }

  public Double getFare() {
    return fare;
  }

  public void setFare(Double fare) {
    this.fare = fare;
  }

  public Double getReductMoney() {
    return reductMoney;
  }

  public void setReductMoney(Double reductMoney) {
    this.reductMoney = reductMoney;
  }

  public Double getCouponMoney() {
    return couponMoney;
  }

  public void setCouponMoney(Double couponMoney) {
    this.couponMoney = couponMoney;
  }


  public void setBalanceMoney(Double balanceMoney) {
    this.balanceMoney = balanceMoney;
  }

  public Double getAmountAvlibal() {
    return amountAvlibal;
  }

  public void setAmountAvlibal(Double amountAvlibal) {
    this.amountAvlibal = amountAvlibal;
  }

  public String getCouponid() {
    return couponid;
  }

  public void setCouponid(String couponid) {
    this.couponid = couponid;
  }

  public String getCouponName() {
    return couponName;
  }

  public void setCouponName(String couponName) {
    this.couponName = couponName;
  }

  public BigDecimal getAdditionalMoney() {
    return additionalMoney;
  }

  public void setAdditionalMoney(BigDecimal additionalMoney) {
    this.additionalMoney = additionalMoney;
  }

  public BigDecimal getDiscountMoney() {
    return discountMoney;
  }

  public void setDiscountMoney(BigDecimal discountMoney) {
    this.discountMoney = discountMoney;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  /** 优惠券使用限制 ， 1：不可和余额同时使用 */
  public String getUseLimitsType() {
    return useLimitsType;
  }

  /** 优惠券使用限制 ， 1：不可和余额同时使用 */
  public void setUseLimitsType(String useLimitsType) {
    this.useLimitsType = useLimitsType;
  }

  public PayMoneyPresell getPayMoneyPresell() {
    return payMoneyPresell;
  }

  public void setPayMoneyPresell(PayMoneyPresell payMoneyPresell) {
    this.payMoneyPresell = payMoneyPresell;
  }


}
