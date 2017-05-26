package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class SupplierNews implements Serializable {
  private static final long serialVersionUID = 1L;
  // 资讯id
  private Long newsId;
  // 资讯类别
  private BigDecimal newsCategoryId;
  // 资讯标题
  private String newsTitle;
  // SEO关键字
  private String newsSeo;
  // 访问数
  private Integer visitNum;
  // 评论数
  private Integer appraiseNum;
  // 状态
  private String status;
  // 审核状态
  private String auditStatus;
  // 审核人
  private BigDecimal auditUser;
  // 审核时间
  private Date auditTime;
  // 创建人
  private BigDecimal createUser;
  // 创建时间
  private Date createTime;
  // 资讯内容
  private String newsContent;

  public Long getNewsId() {
    return newsId;
  }

  public void setNewsId(Long newsId) {
    this.newsId = newsId;
  }

  public BigDecimal getNewsCategoryId() {
    return newsCategoryId;
  }

  public void setNewsCategoryId(BigDecimal newsCategoryId) {
    this.newsCategoryId = newsCategoryId;
  }

  public String getNewsTitle() {
    return newsTitle;
  }

  public void setNewsTitle(String newsTitle) {
    this.newsTitle = newsTitle;
  }

  public String getNewsSeo() {
    return newsSeo;
  }

  public void setNewsSeo(String newsSeo) {
    this.newsSeo = newsSeo;
  }

  public Integer getVisitNum() {
    return visitNum;
  }

  public void setVisitNum(Integer visitNum) {
    this.visitNum = visitNum;
  }

  public Integer getAppraiseNum() {
    return appraiseNum;
  }

  public void setAppraiseNum(Integer appraiseNum) {
    this.appraiseNum = appraiseNum;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getAuditStatus() {
    return auditStatus;
  }

  public void setAuditStatus(String auditStatus) {
    this.auditStatus = auditStatus;
  }

  public BigDecimal getAuditUser() {
    return auditUser;
  }

  public void setAuditUser(BigDecimal auditUser) {
    this.auditUser = auditUser;
  }

  public Date getAuditTime() {
    return auditTime;
  }

  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }

  public BigDecimal getCreateUser() {
    return createUser;
  }

  public void setCreateUser(BigDecimal createUser) {
    this.createUser = createUser;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public String getNewsContent() {
    return newsContent;
  }

  public void setNewsContent(String newsContent) {
    this.newsContent = newsContent;
  }

  @Override
  public String toString() {
    return "SupplierNews [newsId=" + newsId + ", newsCategoryId=" + newsCategoryId + ", newsTitle="
        + newsTitle + ", newsSeo=" + newsSeo + ", visitNum=" + visitNum + ", appraiseNum="
        + appraiseNum + ", status=" + status + ", auditStatus=" + auditStatus + ", auditUser="
        + auditUser + ", auditTime=" + auditTime + ", createUser=" + createUser + ", createTime="
        + createTime + ", newsContent=" + newsContent + "]";
  }

}
