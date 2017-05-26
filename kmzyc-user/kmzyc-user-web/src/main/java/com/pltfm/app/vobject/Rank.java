package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 客户头衔
 *
 * 
 */
public class Rank implements Serializable {
  /**
   * 客户头衔ID. This field corresponds to the database column RANK.N_RANK_ID
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private Integer rankId;

  /**
   * 客户类型 This field corresponds to the database column RANK.N_CUSTOMER_TYPE_ID
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private Integer customerTypeId;
  /** 客户类别子类别id(只传值) **/
  private Integer customer_son_id;
  /** 客户类型名称 **/
  private String customerName;

  /**
   * 客户头衔编号. This field corresponds to the database column RANK.CODE
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private String code;

  /**
   * 客户头衔名称. This field corresponds to the database column RANK.RANK_NAME
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private String rankName;

  /**
   * 积分/经验值最小值. This field corresponds to the database column RANK.SCORE_MIN
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private Integer scoreMin;

  /**
   * 积分/经验值最大值. This field corresponds to the database column RANK.SCORE_MAX
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private Integer scoreMax;

  /**
   * 创建日期. This field corresponds to the database column RANK.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private Date createDate;

  /**
   * 创建人. This field corresponds to the database column RANK.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private Integer created;

  /**
   * 修改日期. This field corresponds to the database column RANK.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private Date modifyDate;

  /**
   * 修改人. This field corresponds to the database column RANK.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 14:08:27 CST 2013
   */
  private Integer modified;
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;

  public Integer getRankId() {
    return rankId;
  }

  public void setRankId(Integer rankId) {
    this.rankId = rankId;
  }

  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getRankName() {
    return rankName;
  }

  public void setRankName(String rankName) {
    this.rankName = rankName;
  }

  public Integer getScoreMin() {
    return scoreMin;
  }

  public void setScoreMin(Integer scoreMin) {
    this.scoreMin = scoreMin;
  }

  public Integer getScoreMax() {
    return scoreMax;
  }

  public void setScoreMax(Integer scoreMax) {
    this.scoreMax = scoreMax;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Integer getCreated() {
    return created;
  }

  public void setCreated(Integer created) {
    this.created = created;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getModified() {
    return modified;
  }

  public void setModified(Integer modified) {
    this.modified = modified;
  }

  public Integer getSkip() {
    return skip;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }

  public Integer getCustomer_son_id() {
    return customer_son_id;
  }

  public void setCustomer_son_id(Integer customerSonId) {
    customer_son_id = customerSonId;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }
}
