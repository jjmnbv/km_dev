package com.kmzyc.b2b.vo;

import java.util.List;

public class Commodity {

  private Long skuId;

  private String commodityName;

  private String imgPath;

  private Integer point;

  private String apprContent;

  private String addToContent;

  private List<AttributeValue> attrValues;

  public Long getSkuId() {
    return skuId;
  }

  public void setSkuId(Long skuId) {
    this.skuId = skuId;
  }

  public String getCommodityName() {
    return commodityName;
  }

  public void setCommodityName(String commodityName) {
    this.commodityName = commodityName;
  }

  public String getImgPath() {
    return imgPath;
  }

  public void setImgPath(String imgPath) {
    this.imgPath = imgPath;
  }

  public Integer getPoint() {
    return point;
  }

  public void setPoint(Integer point) {
    this.point = point;
  }

  public String getApprContent() {
    return apprContent;
  }

  public void setApprContent(String apprContent) {
    this.apprContent = apprContent;
  }

  public String getAddToContent() {
    return addToContent;
  }

  public void setAddToContent(String addToContent) {
    this.addToContent = addToContent;
  }

  public List<AttributeValue> getAttrValues() {
    return attrValues;
  }

  public void setAttrValues(List<AttributeValue> attrValues) {
    this.attrValues = attrValues;
  }

}
