package com.kmzyc.b2b.model;

/**
 * 客户订阅邮件记录信息表实体类
 * 
 * @author luoyi
 * @createDate 2013/10/12
 */
import java.util.Date;

public class EmailRrsHis {
  // 邮件订阅记录ID
  private Long emailRrsHisId;

  // 邮件订阅ID
  private Long emailRrsId;

  // 用户ID
  private Long nLoginId;

  // 创建日期
  private Date createDate;

  // 订阅人ID
  private Long createdId;

  // 修改日期
  private Date modifyDate;

  // 修改ID
  private Long modifieId;

  /**
   * 以下为set和get方法
   * 
   * @return
   */

  public Long getEmailRrsHisId() {
    return emailRrsHisId;
  }

  public void setEmailRrsHisId(Long emailRrsHisId) {
    this.emailRrsHisId = emailRrsHisId;
  }

  public Long getEmailRrsId() {
    return emailRrsId;
  }

  public void setEmailRrsId(Long emailRrsId) {
    this.emailRrsId = emailRrsId;
  }

  public Long getnLoginId() {
    return nLoginId;
  }

  public void setnLoginId(Long nLoginId) {
    this.nLoginId = nLoginId;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Long getCreatedId() {
    return createdId;
  }

  public void setCreatedId(Long createdId) {
    this.createdId = createdId;
  }

  public Date getModifyDate() {
    return modifyDate;
  }

  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  public Long getModifieId() {
    return modifieId;
  }

  public void setModifieId(Long modifieId) {
    this.modifieId = modifieId;
  }

  @Override
  public String toString() {
    return "EmailRrsHis [emailRrsHisId=" + emailRrsHisId + ", emailRrsId=" + emailRrsId
        + ", nLoginId=" + nLoginId + ", createDate=" + createDate + ", createdId=" + createdId
        + ", modifyDate=" + modifyDate + ", modifieId=" + modifieId + "]";
  }
}
