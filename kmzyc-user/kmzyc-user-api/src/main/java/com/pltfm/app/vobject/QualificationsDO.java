package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2014-07-07
 */
public class QualificationsDO implements Serializable {

  private static final long serialVersionUID = 140470179476382339L;

  private Integer id;

  private Integer userId;

  private Short type;

  private String userName;
  private Short status;

  private Date createDate;

  private Date modifyDate;

  private Date validDate;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public Short getType() {
    return type;
  }

  public void setType(Short type) {
    this.type = type;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Date getValidDate() {
    return validDate;
  }

  public void setValidDate(Date validDate) {
    this.validDate = validDate;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;

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



}
