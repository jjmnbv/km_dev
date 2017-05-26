package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 优惠套装
 * 
 * @author hewenfeng
 * 
 */
public class ProductRelation implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long relationId;

  private String relationName;

  private Long mainSkuId;

  private BigDecimal mainSkuPrice;

  private BigDecimal totalRelationPrice;

  private Short relationType;

  private String remark;
  /**
   * 简介
   */
  private String relationIntroduce;
  /**
   * 视频地址
   */
  private String relationVideo;
  /**
   * 创建时间
   */
  private Date createDate;
  /**
   * 状态(0:无效 1：有效 2：新增 3：上架 4：下架)
   */
  private Integer status;
  /**
   * 套餐详情
   */
  private List<ProductRelationDetailAll> prdList;

  public String getRelationName() {
    return relationName;
  }

  public void setRelationName(String relationName) {
    this.relationName = relationName;
  }

  public BigDecimal getMainSkuPrice() {
    return mainSkuPrice;
  }

  public void setMainSkuPrice(BigDecimal mainSkuPrice) {
    this.mainSkuPrice = mainSkuPrice;
  }

  public BigDecimal getTotalRelationPrice() {
    return totalRelationPrice;
  }

  public void setTotalRelationPrice(BigDecimal totalRelationPrice) {
    this.totalRelationPrice = totalRelationPrice;
  }

  public Short getRelationType() {
    return relationType;
  }

  public void setRelationType(Short relationType) {
    this.relationType = relationType;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Long getRelationId() {
    return relationId;
  }

  public void setRelationId(Long relationId) {
    this.relationId = relationId;
  }

  public Long getMainSkuId() {
    return mainSkuId;
  }

  public void setMainSkuId(Long mainSkuId) {
    this.mainSkuId = mainSkuId;
  }

  /** 状态(0:无效 1：有效 2：新增 3：上架 4：下架) */
  public Integer getStatus() {
    return status;
  }

  /** 状态(0:无效 1：有效 2：新增 3：上架 4：下架) */
  public void setStatus(Integer status) {
    this.status = status;
  }

  public List<ProductRelationDetailAll> getPrdList() {
    return prdList;
  }

  public void setPrdList(List<ProductRelationDetailAll> prdList) {
    this.prdList = prdList;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getRelationIntroduce() {
    return relationIntroduce;
  }

  public void setRelationIntroduce(String relationIntroduce) {
    this.relationIntroduce = relationIntroduce;
  }

  public String getRelationVideo() {
    return relationVideo;
  }

  public void setRelationVideo(String relationVideo) {
    this.relationVideo = relationVideo;
  }
}
