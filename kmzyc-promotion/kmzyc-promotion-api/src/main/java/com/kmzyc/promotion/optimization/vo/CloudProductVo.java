package com.kmzyc.promotion.optimization.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 云产品VO
 * 
 * @author weijl
 *
 */
public class CloudProductVo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -1625413395168259044L;

  private BigDecimal cpid;
  private Date startTime;
  private Date endTime;
  private BigDecimal enableRebackRate;
  private BigDecimal distriRebackRate;
  private BigDecimal consumeRebackRate;
  private BigDecimal activityLabel;
  private BigDecimal productId;
  private BigDecimal productSkuId;
  private String productTitle;
  private String skuAttr;
  private BigDecimal price;
  private String imgPath;
  private Long stockCount = 0l;
  private String spreadRuleKey;
  private Long spreadId;
  private String viewPath;
  /**
   * 促销活动标签
   */
  private List<String> promotionLabelList;
  /**
   * 促销活动标签
   */
  private String promotionLabel;
  /**
   * 消费返利金额
   */
  private BigDecimal consumeRebackAmt;

  public BigDecimal getCpid() {
    return cpid;
  }

  public void setCpid(BigDecimal cpid) {
    this.cpid = cpid;
  }

  public Date getStartTime() {
    return startTime;
  }

  public void setStartTime(Date startTime) {
    this.startTime = startTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public BigDecimal getEnableRebackRate() {
    return enableRebackRate;
  }

  public void setEnableRebackRate(BigDecimal enableRebackRate) {
    this.enableRebackRate = enableRebackRate;
  }

  public BigDecimal getDistriRebackRate() {
    return distriRebackRate;
  }

  public void setDistriRebackRate(BigDecimal distriRebackRate) {
    this.distriRebackRate = distriRebackRate;
  }

  public BigDecimal getConsumeRebackRate() {
    return consumeRebackRate;
  }

  public void setConsumeRebackRate(BigDecimal consumeRebackRate) {
    this.consumeRebackRate = consumeRebackRate;
  }

  public BigDecimal getActivityLabel() {
    return activityLabel;
  }

  public void setActivityLabel(BigDecimal activityLabel) {
    this.activityLabel = activityLabel;
  }

  public BigDecimal getProductId() {
    return productId;
  }

  public void setProductId(BigDecimal productId) {
    this.productId = productId;
  }

  public BigDecimal getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(BigDecimal productSkuId) {
    this.productSkuId = productSkuId;
  }

  public String getProductTitle() {
    return productTitle;
  }

  public void setProductTitle(String productTitle) {
    this.productTitle = productTitle;
  }

  public String getSkuAttr() {
    return skuAttr;
  }

  public void setSkuAttr(String skuAttr) {
    this.skuAttr = skuAttr;
  }

  public BigDecimal getPrice() {
    return toBigDecimal(this.price);
  }

  public void setPrice(BigDecimal price) {
    this.price = toBigDecimal(price);
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public Long getStockCount() {
    if (null == stockCount) {
      return 0l;
    }
    return stockCount;
  }

  public void setStockCount(Long stockCount) {
    this.stockCount = stockCount;
  }

  public List<String> getPromotionLabelList() {
    return promotionLabelList;
  }

  public void setPromotionLabelList(List<String> promotionLabelList) {
    this.promotionLabelList = promotionLabelList;
  }

  public String getPromotionLabel() {
    return promotionLabel;
  }

  public void setPromotionLabel(String promotionLabel) {
    this.promotionLabel = promotionLabel;
  }

  public BigDecimal getConsumeRebackAmt() {
    return toBigDecimal(this.consumeRebackAmt);
  }

  public void setConsumeRebackAmt(BigDecimal consumeRebackAmt) {
    this.consumeRebackAmt = toBigDecimal(consumeRebackAmt);

  }

  public String getSpreadRuleKey() {
    return spreadRuleKey;
  }

  public void setSpreadRuleKey(String spreadRuleKey) {
    this.spreadRuleKey = spreadRuleKey;
  }

  public Long getSpreadId() {
    return spreadId;
  }

  public void setSpreadId(Long spreadId) {
    this.spreadId = spreadId;
  }

  public String getViewPath() {
    return viewPath;
  }

  public void setViewPath(String viewPath) {
    this.viewPath = viewPath;
  }

  public String getFormatEndTime() {
    return formatDate1(this.endTime);
  }

  /**
   * 格式化日期 yyyy-MM-dd HH:mm:ss
   * 
   * @param date
   * @return
   */
  public static String formatDate1(Date date) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return sdf.format(date);
  }

  /**
   * 格式化保留两位小数
   * 
   * @param number
   * @return
   */
  public static BigDecimal toBigDecimal(BigDecimal number) {
    if (number == null) return null;
    BigDecimal af3 = number.setScale(3, BigDecimal.ROUND_HALF_UP);
    BigDecimal af2 = number.setScale(2, BigDecimal.ROUND_HALF_UP);
    if (af3.compareTo(af2) > 0) {
      return af2.add(BigDecimal.valueOf(0.01));
    }
    return af2;
  }
}
