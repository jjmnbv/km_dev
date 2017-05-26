package com.kmzyc.b2b.model;

import java.math.BigDecimal;
import java.util.Date;

public class CmsPromotionTask {

  private Long id;

  private Long image;

  private Short status;

  private String output;

  private Date beginTime;

  private Date endTime;

  private String name;

  private Date modifyDate;

  private BigDecimal modified;

  private String p2;

  private String p3;

  private String imagesFile;

  private String imagesFile2;

  private String imagesFile3;

  private String imagesFile4;

  /**
   * 活动类型
   */
  private Integer productFilterType;
  /**
   * 活动规则
   */
  private Integer promotionTypeId;
  /**
   * 广告语
   */
  private String slogan;
  /**
   * 站点
   */
  private Integer siteId;
  /**
   * 运营端类型
   */
  private Integer operateType;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getImage() {
    return image;
  }

  public void setImage(Long image) {
    this.image = image;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public String getOutput() {
    return output;
  }

  public void setOutput(String output) {
    this.output = output;
  }

  public Date getBeginTime() {
    return beginTime;
  }

  public void setBeginTime(Date beginTime) {
    this.beginTime = beginTime;
  }

  public Date getEndTime() {
    return endTime;
  }

  public void setEndTime(Date endTime) {
    this.endTime = endTime;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public BigDecimal getModified() {
    return modified;
  }

  public void setModified(BigDecimal modified) {
    this.modified = modified;
  }

  public String getP2() {
    return p2;
  }

  public void setP2(String p2) {
    this.p2 = p2;
  }

  public String getP3() {
    return p3;
  }

  public void setP3(String p3) {
    this.p3 = p3;
  }

  public String getImagesFile() {
    return imagesFile;
  }

  public void setImagesFile(String imagesFile) {
    this.imagesFile = imagesFile;
  }

  public String getImagesFile2() {
    return imagesFile2;
  }

  public void setImagesFile2(String imagesFile2) {
    this.imagesFile2 = imagesFile2;
  }

  public String getImagesFile3() {
    return imagesFile3;
  }

  public void setImagesFile3(String imagesFile3) {
    this.imagesFile3 = imagesFile3;
  }

  public String getImagesFile4() {
    return imagesFile4;
  }

  public void setImagesFile4(String imagesFile4) {
    this.imagesFile4 = imagesFile4;
  }

  public Integer getProductFilterType() {
    return productFilterType;
  }

  public void setProductFilterType(Integer productFilterType) {
    this.productFilterType = productFilterType;
  }

  public Integer getPromotionTypeId() {
    return promotionTypeId;
  }

  public void setPromotionTypeId(Integer promotionTypeId) {
    this.promotionTypeId = promotionTypeId;
  }

  public String getSlogan() {
    return slogan;
  }

  public void setSlogan(String slogan) {
    this.slogan = slogan;
  }

  public Integer getSiteId() {
    return siteId;
  }

  public void setSiteId(Integer siteId) {
    this.siteId = siteId;
  }

  public Integer getOperateType() {
    return operateType;
  }

  public void setOperateType(Integer operateType) {
    this.operateType = operateType;
  }
}
