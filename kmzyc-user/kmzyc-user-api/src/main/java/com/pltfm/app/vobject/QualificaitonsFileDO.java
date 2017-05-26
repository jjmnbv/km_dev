package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2014-07-07
 */
public class QualificaitonsFileDO implements Serializable {

  private static final long serialVersionUID = 140470179276945952L;

  /**
   * column QUALIFICAITONS_FILE.ID
   */
  private Integer id;

  /**
   * column QUALIFICAITONS_FILE.USER_ID
   */
  private Integer userId;

  /**
   * column QUALIFICAITONS_FILE.FILE_NAME
   */
  private String fileName;

  /**
   * column QUALIFICAITONS_FILE.FILE_URL
   */
  private String fileUrl;

  /**
   * column QUALIFICAITONS_FILE.BEGIN_DATE
   */
  private Date beginDate;

  /**
   * column QUALIFICAITONS_FILE.END_DATE
   */
  private Date endDate;

  /**
   * column QUALIFICAITONS_FILE.STATUS
   */
  private Short status;

  /**
   * column QUALIFICAITONS_FILE.DELETED
   */
  private Short deleted;


  private String userName;

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * column QUALIFICAITONS_FILE.CREATE_DATE
   */
  private Date createDate;

  /**
   * column QUALIFICAITONS_FILE.AUDITING_DATE
   */
  private Date auditingDate;



  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public Integer getUserId() {
    return userId;
  }

  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public Date getBeginDate() {
    return beginDate;
  }

  public void setBeginDate(Date beginDate) {
    this.beginDate = beginDate;
  }

  public Date getEndDate() {
    return endDate;
  }

  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }

  public Short getStatus() {
    return status;
  }

  public void setStatus(Short status) {
    this.status = status;
  }

  public Short getDeleted() {
    return deleted;
  }

  public void setDeleted(Short deleted) {
    this.deleted = deleted;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getAuditingDate() {
    return auditingDate;
  }

  public void setAuditingDate(Date auditingDate) {
    this.auditingDate = auditingDate;
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



}
