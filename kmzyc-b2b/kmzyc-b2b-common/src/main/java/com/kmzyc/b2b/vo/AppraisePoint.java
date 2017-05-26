package com.kmzyc.b2b.vo;

/**
 * 
 * 展示在cms产品明细页的
 * 
 * @author Administrator
 * 
 */
public class AppraisePoint {

  /**
   * SKUID
   */
  private Integer skuId;

  /**
   * 对应的星级标示
   */
  private Integer starId;

  /**
   * 对应的SKUID人数
   */
  private int countPersonBySkuId;

  /**
   * 总分数
   */
  private long totlePoint;

  /**
   * 平均分数
   */
  private float averageTotlePoint;

  /**
   * 平均星级
   */
  private int averageTotleStar;

  /**
   * 对应的星级,Skuid的总人数
   */
  private int conutPersonByStar;

  /**
   * 对应星级的总分数
   */
  private int totlePointByStar;

  /**
   * 对应的星级，skuid的人数百分比
   */
  private float personAccountByStar;

  public Integer getSkuId() {
    return skuId;
  }

  public void setSkuId(Integer skuId) {
    this.skuId = skuId;
  }

  public Integer getStarId() {
    return starId;
  }

  public void setStarId(Integer starId) {
    this.starId = starId;
  }

  public int getCountPersonBySkuId() {
    return countPersonBySkuId;
  }

  public void setCountPersonBySkuId(int countPersonBySkuId) {
    this.countPersonBySkuId = countPersonBySkuId;
  }

  public long getTotlePoint() {
    return totlePoint;
  }

  public void setTotlePoint(long totlePoint) {
    this.totlePoint = totlePoint;
  }

  public float getAverageTotlePoint() {
    return averageTotlePoint;
  }

  public void setAverageTotlePoint(float averageTotlePoint) {
    this.averageTotlePoint = averageTotlePoint;
  }

  public int getAverageTotleStar() {
    return averageTotleStar;
  }

  public void setAverageTotleStar(int averageTotleStar) {
    this.averageTotleStar = averageTotleStar;
  }

  public int getConutPersonByStar() {
    return conutPersonByStar;
  }

  public void setConutPersonByStar(int conutPersonByStar) {
    this.conutPersonByStar = conutPersonByStar;
  }

  public int getTotlePointByStar() {
    return totlePointByStar;
  }

  public void setTotlePointByStar(int totlePointByStar) {
    this.totlePointByStar = totlePointByStar;
  }

  public float getPersonAccountByStar() {
    return personAccountByStar;
  }

  public void setPersonAccountByStar(float personAccountByStar) {
    this.personAccountByStar = personAccountByStar;
  }

}
