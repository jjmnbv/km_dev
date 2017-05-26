package com.kmzyc.b2b.model;

/**
 * 邮件订阅内容信息表
 * 
 * @author luoyi
 * @createDate 2013/10/12
 */
import java.util.Date;

public class EmailRrsInfo {
  /**
   * 邮件订阅ID
   */
  private Integer emailRrsId;

  /**
   * 邮件订阅名称
   */
  private String emailRrsName;

  /**
   * 邮件订阅类别ID
   */
  private Integer emailRrsParentId;

  /**
   * 是否为邮件订阅类别1---是0--否
   */
  private Short isParent;

  /**
   * 创建日期
   */
  private Date createDate;

  /**
   * 创建人ID
   */
  private Integer createdId;

  /**
   * 修改日期
   */
  private Date modifyDate;

  /**
   * 修改ID
   */
  private Integer modifieId;

  /**
   * 描述
   */
  private String description;

  /**
   * 周刊
   */
  private String weekly;

  /**
   * 以下为set和get方法
   */
  public Integer getEmailRrsId() {
    return emailRrsId;
  }

  public void setEmailRrsId(Integer emailRrsId) {
    this.emailRrsId = emailRrsId;
  }

  public String getEmailRrsName() {
    return emailRrsName;
  }

  public void setEmailRrsName(String emailRrsName) {
    this.emailRrsName = emailRrsName;
  }

  public Integer getEmailRrsParentId() {
    return emailRrsParentId;
  }

  public void setEmailRrsParentId(Integer emailRrsParentId) {
    this.emailRrsParentId = emailRrsParentId;
  }

  public Short getIsParent() {
    return isParent;
  }

  public void setIsParent(Short isParent) {
    this.isParent = isParent;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Integer getCreatedId() {
    return createdId;
  }

  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Integer getModifieId() {
    return modifieId;
  }

  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getWeekly() {
    return weekly;
  }

  public void setWeekly(String weekly) {
    this.weekly = weekly;
  }

  @Override
  public String toString() {
    return "EmailRrsInfo [emailRrsId=" + emailRrsId + ", emailRrsName=" + emailRrsName
        + ", emailRrsParentId=" + emailRrsParentId + ", isParent=" + isParent + ", createDate="
        + createDate + ", createdId=" + createdId + ", modifyDate=" + modifyDate + ", modifieId="
        + modifieId + ", description=" + description + ", weekly=" + weekly + "]";
  }

}
