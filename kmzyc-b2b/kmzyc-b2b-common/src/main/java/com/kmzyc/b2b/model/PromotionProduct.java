package com.kmzyc.b2b.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.struts2.json.annotations.JSON;

import com.kmzyc.zkconfig.ConfigurationUtil;

/**
 * 活动商品
 * 
 * @author hewenfeng
 * 
 */
public class PromotionProduct extends ProductSku {

  /**
	 * 
	 */
  private static final long serialVersionUID = 8878921034444068867L;

  private Long promotionProductId;
  private Long promotionId;
  private Date createDate;

  private String imagePath5;
  private String imagePath6;
  private String imagePath7;
  private String imagePathIOS;
  private String imagePathAndroid;
  private String[] labelArray;
  /**
   * 限购下限
   */
  private Integer minBuy;
  /**
   * 限购上限
   */
  private Integer maxBuy;
  /**
   * 活动库存
   */
  private Integer promotionStock;
  /**
   * 活动已售量
   */
  private int promotionSell;

  @JSON(serialize = false)
  public Long getPromotionProductId() {
    return promotionProductId;
  }

  public void setPromotionProductId(Long promotionProductId) {
    this.promotionProductId = promotionProductId;
  }

  @JSON(serialize = false)
  public Long getPromotionId() {
    return promotionId;
  }

  public void setPromotionId(Long promotionId) {
    this.promotionId = promotionId;
  }

  @JSON(serialize = false)
  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  @JSON(serialize = false)
  public String getImagePath5() {
    return getDefaultPath().replace(".jpg", "_5.jpg");
  }

  public void setImagePath5(String imagePath5) {
    this.imagePath5 = imagePath5;
  }

  @JSON(serialize = false)
  public String getImagePath6() {
    return getDefaultPath().replace(".jpg", "_6.jpg");
  }

  public void setImagePath6(String imagePath6) {
    this.imagePath6 = imagePath6;
  }

  @JSON(serialize = false)
  public String getImagePath7() {
    return getDefaultPath().replace(".jpg", "_7.jpg");
  }

  public void setImagePath7(String imagePath7) {
    this.imagePath7 = imagePath7;
  }

  public String getImagePathIOS() {
    return getDefaultPath().replace(".jpg", "_3.jpg");
  }

  public void setImagePathIOS(String imagePathIOS) {
    this.imagePathIOS = imagePathIOS;
  }

  public String getImagePathAndroid() {
    return getDefaultPath().replace(".jpg", "_4.jpg");
  }

  public void setImagePathAndroid(String imagePathAndroid) {
    this.imagePathAndroid = imagePathAndroid;
  }

  @JSON(serialize = false)
  private String getDefaultPath() {
    return ConfigurationUtil.getString("PRODUCT_IMG_PATH")
        + this.getDefaultProductSkuImgPath();
  }

  @Override
  @JSON(serialize = false)
  public String getTag() {
    return super.getTag();
  }

  @Override
  @JSON(serialize = false)
  public BigDecimal getUnitWeight() {
    return super.getUnitWeight();
  }

  @Override
  @JSON(serialize = false)
  public String getDefaultProductSkuImgPath100_100() {
    return null;
  }

  @Override
  @JSON(serialize = false)
  public String getDefaultProductSkuImgPath() {
    return super.getDefaultProductSkuImgPath();
  }

  @Override
@JSON(serialize = false)
  public Long getProductId() {
    return super.getProductId();
  }

  @Override
@JSON(serialize = false)
  public BigDecimal getPrice() {
    return super.getPrice();
  }

  @Override
@JSON(serialize = false)
  public String getProductSkuCode() {
    return super.getProductSkuCode();
  }

  public String[] getLabelArray() {
    return labelArray;
  }

  public void setLabelArray(String[] labelArray) {
    this.labelArray = labelArray;
  }

 

  @Override
public int getPromotionSell() {
    return promotionSell;
  }

  @Override
public void setPromotionSell(int promotionSell) {
    this.promotionSell = promotionSell;
  }

  @Override
public Integer getMinBuy() {
    return minBuy;
  }

  @Override
public void setMinBuy(Integer minBuy) {
    this.minBuy = minBuy;
  }

  @Override
public Integer getMaxBuy() {
    return maxBuy;
  }

  @Override
public void setMaxBuy(Integer maxBuy) {
    this.maxBuy = maxBuy;
  }

  @Override
public Integer getPromotionStock() {
    return promotionStock;
  }

  @Override
public void setPromotionStock(Integer promotionStock) {
    this.promotionStock = promotionStock;
  }


}
