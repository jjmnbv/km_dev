package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import com.kmzyc.supplier.model.SupplierFare;

/**
 * 商家价格信息
 * 
 * @author xlg
 * 
 */
public class SellerPriceInfo implements Serializable {
  private static final long serialVersionUID = 1589172241418977901L;
  /**
   * 产品总数
   */
  private Integer productCount;
  /**
   * 金额
   */
  private BigDecimal sumMoney = BigDecimal.ZERO;
  /**
   * 支付金额
   */
  private BigDecimal payMoney = BigDecimal.ZERO;
  /**
   * 积分
   */
  private Integer score;
  /**
   * 优惠券金额
   */
  private BigDecimal couponMoney = BigDecimal.ZERO;
  /**
   * 总减免金额
   */
  private BigDecimal reductionMoney = BigDecimal.ZERO;

  /**
   * 加价购金额
   */
  private BigDecimal additionalMoney = BigDecimal.ZERO;

  /**
   * 总重量
   */
  private BigDecimal weight;

  /****
   * pv值
   */

  private BigDecimal pvalue = BigDecimal.ZERO;
  /**
   * 包邮
   */
  private SupplierFare freeFare;
  /***
   * 
   * 
   * 时代等级折扣率百分比
   */
  private BigDecimal eraGradeRate = BigDecimal.ZERO;

  /***
   * 时代会员优惠金额
   * 
   * @return
   */
  private BigDecimal discountMoney = BigDecimal.ZERO;

  /****
   * 
   * 时代促销政策信息字段
   */

  private String info;

  /****
   * 
   * 时代促销详情url
   */

  private String url;

  /****
   * 
   * 
   * 折扣PV
   * 
   */
  private BigDecimal discountPv = BigDecimal.ZERO;

  /****
   * 
   * 时代等级名称
   */

  private String eraGradeName;

  /***
   * 商家类型
   * 
   */
  private short supplierType;

  public short getSupplierType() {
    return supplierType;
  }

  public void setSupplierType(short supplierType) {
    this.supplierType = supplierType;
  }

  public BigDecimal getDiscountMoney() {
    return discountMoney;
  }

  public void setDiscountMoney(BigDecimal discountMoney) {
    this.discountMoney = discountMoney;
  }

  public Integer getProductCount() {
    return productCount;
  }

  public void setProductCount(Integer productCount) {
    this.productCount = productCount;
  }

  public BigDecimal getSumMoney() {
    return sumMoney;
  }

  public void setSumMoney(BigDecimal sumMoney) {
    this.sumMoney = sumMoney;
  }

  public BigDecimal getPayMoney() {
    return payMoney;
  }

  public void setPayMoney(BigDecimal payMoney) {
    this.payMoney = payMoney;
  }

  public Integer getScore() {
    return score;
  }

  public void setScore(Integer score) {
    this.score = score;
  }

  public BigDecimal getCouponMoney() {
    return couponMoney;
  }

  public void setCouponMoney(BigDecimal couponMoney) {
    this.couponMoney = couponMoney;
  }

  public BigDecimal getReductionMoney() {
    return reductionMoney;
  }

  public void setReductionMoney(BigDecimal reductionMoney) {
    this.reductionMoney = reductionMoney;
  }

  public BigDecimal getAdditionalMoney() {
    return additionalMoney;
  }

  public void setAdditionalMoney(BigDecimal additionalMoney) {
    this.additionalMoney = additionalMoney;
  }

  public BigDecimal getWeight() {
    return weight;
  }

  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

  public SupplierFare getFreeFare() {
    return freeFare;
  }

  public void setFreeFare(SupplierFare freeFare) {
    this.freeFare = freeFare;
  }

  public BigDecimal getLastAllMoney() {
    return additionalMoney.subtract(reductionMoney).add(sumMoney);
  }

  public BigDecimal getPvalue() {
    return pvalue;
  }

  public void setPvalue(BigDecimal pvalue) {
    this.pvalue = pvalue;
  }

  public BigDecimal getEraGradeRate() {
    return eraGradeRate;
  }

  public void setEraGradeRate(BigDecimal eraGradeRate) {
    this.eraGradeRate = eraGradeRate;
  }

  public String getInfo() {
    return info;
  }

  public void setInfo(String info) {
    this.info = info;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getEraGradeName() {
    return eraGradeName;
  }

  public void setEraGradeName(String eraGradeName) {
    this.eraGradeName = eraGradeName;
  }

  public BigDecimal getDiscountPv() {
    return discountPv;
  }

  public void setDiscountPv(BigDecimal discountPv) {
    this.discountPv = discountPv;
  }

}
