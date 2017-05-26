package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class PromotionRuleData implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 6573492773586404701L;
  private Long promotionRuleDataId;
  private Long promotionId;
  private BigDecimal meetData;
  private Integer meetDataType;
  private String prizeData;
  private Long entityId;
  private Integer num1;
  private Integer num2;
  private Integer num3;

  // 1：减免金额；2：折扣率；3：优惠券ID
  private Integer prizeDataType;
  private String prizeName;

  public String getPrizeName() {
    return this.prizeName;
  }

  public void setPrizeName(String prizeName) {
    this.prizeName = prizeName;
  }

  public Long getPromotionRuleDataId() {
    return promotionRuleDataId;
  }

  public void setPromotionRuleDataId(Long promotionRuleDataId) {
    this.promotionRuleDataId = promotionRuleDataId;
  }

  public Long getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  public BigDecimal getMeetData() {
    return meetData;
  }

  public void setMeetData(BigDecimal meetData) {
    this.meetData = meetData;
  }

  /**
   * 1：金额，2：数量 <br>
   * 默认为1
   */
  public Integer getMeetDataType() {
    return meetDataType;
  }

  /**
   * 1：金额，2：数量 <br>
   * 默认为1
   */
  public void setMeetDataType(Integer meetDataType) {
    this.meetDataType = meetDataType;
  }

  public String getPrizeData() {
    return prizeData;
  }

  public void setPrizeData(String prizeData) {
    this.prizeData = prizeData;
  }

  /**
   * 1：减免金额；2：折扣率；3：优惠券ID
   */
  public Integer getPrizeDataType() {
    return prizeDataType;
  }

  /**
   * 1：减免金额；2：折扣率；3：优惠券ID
   */
  public void setPrizeDataType(Integer prizeDataType) {
    this.prizeDataType = prizeDataType;
  }

  public Long getEntityId() {
    return entityId;
  }

  public void setEntityId(Long entityId) {
    this.entityId = entityId;
  }

  public Integer getNum1() {
    return num1;
  }

  public void setNum1(Integer num1) {
    this.num1 = num1;
  }

  public Integer getNum2() {
    return num2;
  }

  public void setNum2(Integer num2) {
    this.num2 = num2;
  }

  public Integer getNum3() {
    return num3;
  }

  public void setNum3(Integer num3) {
    this.num3 = num3;
  }

}
