package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

public class MailSendTask implements Serializable {


  /**
   * 
   */
  private static final long serialVersionUID = -7766270145561819985L;

  private Long id;

  private String receiver;

  private String content;

  private String templateType;

  private Date sendTime;

  private String params;

  private Integer isSuccess;

  private Integer sendCount;

  private Date createTime;

  private Date updateTime;

  private Integer systemType;

  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;


  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }


  public String getTemplateType() {
    return templateType;
  }

  public void setTemplateType(String templateType) {
    this.templateType = templateType;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  public Integer getIsSuccess() {
    return isSuccess;
  }

  public void setIsSuccess(Integer isSuccess) {
    this.isSuccess = isSuccess;
  }

  public Integer getSendCount() {
    return sendCount;
  }

  public void setSendCount(Integer sendCount) {
    this.sendCount = sendCount;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public Integer getSystemType() {
    return systemType;
  }

  public void setSystemType(Integer systemType) {
    this.systemType = systemType;
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
