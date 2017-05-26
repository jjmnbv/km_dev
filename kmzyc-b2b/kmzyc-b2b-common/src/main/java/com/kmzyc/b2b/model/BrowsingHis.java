package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.kmzyc.b2b.vo.BaseProduct;

@SuppressWarnings( {"serial", "unused"})
public class BrowsingHis extends BaseProduct implements Serializable {
  /**
   * 登陆Id
   */
  private Integer loginId;
  /**
   * 浏览Id
   */
  private Long browsingHisId;
  /**
   * 商品code
   */
  private String contentCode;
  /**
   * 创建日期
   */
  private Date createDate;
  /**
   * 修改日期
   */
  private Date updateDate;

  /**
   * 商品名称
   */
  private String procuctName;
  /**
   * 商品市场价格
   */
  private Double marketPrice;
  /**
   * 商品图片路径
   */
  private String imgPath;
  /**
   * 商品图片路径
   */
  private String imgPath5;
  /**
   * 商品图片路径
   */
  private String imgPath6;
  /**
   * 商品图片路径
   */
  private String imgPath7;
  /**
   * skuId
   */
  private Long productSkuId;
  /**
   * 商品id
   */
  private Long productId;

  private Long pvValue;

  private List<Map<String, String>> categoryAttrValueList;

  private String categoryAttrValueStr;

  /**
   * 浏览记录类型
   * 
   * @return
   */
  public Integer browsingType;

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public Integer getBrowsingType() {
    return browsingType;
  }

  public void setBrowsingType(Integer browsingType) {
    this.browsingType = browsingType;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public Long getBrowsingHisId() {
    return browsingHisId;
  }

  public void setBrowsingHisId(Long browsingHisId) {
    this.browsingHisId = browsingHisId;
  }

  public String getContentCode() {
    return contentCode;
  }

  public void setContentCode(String contentCode) {
    this.contentCode = contentCode;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getProcuctName() {
    return procuctName;
  }

  public void setProcuctName(String procuctName) {
    this.procuctName = procuctName;
  }

  public Double getMarketPrice() {
    return marketPrice;
  }

  public void setMarketPrice(Double marketPrice) {
    this.marketPrice = marketPrice;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public Long getProductSkuId() {
    return productSkuId;
  }

  public void setProductSkuId(Long productSkuId) {
    this.productSkuId = productSkuId;
  }

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public String getImgPath6() {
    return imgPath6;
  }

  public void setImgPath6(String imgPath6) {
    this.imgPath6 = imgPath6;
  }

  public String getImgPath7() {
    return imgPath7;
  }

  public void setImgPath7(String imgPath7) {
    this.imgPath7 = imgPath7;
  }

  public String getImgPath5() {
    return imgPath5;
  }

  public void setImgPath5(String imgPath5) {
    this.imgPath5 = imgPath5;
  }

  public Long getPvValue() {
    return pvValue;
  }

  public void setPvValue(Long pvValue) {
    this.pvValue = pvValue;
  }

  @Override
  public String toString() {
    return "BrowsingHis [loginId=" + loginId + ", browsingHisId=" + browsingHisId
        + ", contentCode=" + contentCode + ", createDate=" + createDate + ", updateDate="
        + updateDate + ", procuctName=" + procuctName + ", marketPrice=" + marketPrice
        + ", imgPath=" + imgPath + ", productSkuId=" + productSkuId + ", productId=" + productId
        + ", browsingType=" + browsingType + "]";
  }

  public List<Map<String, String>> getCategoryAttrValueList() {
    return categoryAttrValueList;
  }

  public void setCategoryAttrValueList(List<Map<String, String>> categoryAttrValueList) {
    this.categoryAttrValueList = categoryAttrValueList;
  }

  public String getCategoryAttrValueStr() {
    return categoryAttrValueStr;
  }

  public void setCategoryAttrValueStr(String categoryAttrValueStr) {
    this.categoryAttrValueStr = categoryAttrValueStr;
  }

}
