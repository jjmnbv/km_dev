package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.util.Date;

public class CmsAppManager implements Serializable {

  /**
   * 序列化
   */
  private static final long serialVersionUID = -7967513450488966444L;

  private Long appManagerId;

  private String name;

  private String version;

  private String downLoad;

  private Integer isCoerce;

  private String terrace;// 手机平台

  private Long siteId;

  private String remark;

  private Integer stauts;

  private Date createDate;

  private Integer versionCode;

  public Integer getVersionCode() {
    return versionCode;
  }

  public void setVersionCode(Integer versionCode) {
    this.versionCode = versionCode;
  }

  public Long getAppManagerId() {
    return appManagerId;
  }

  public void setAppManagerId(Long appManagerId) {
    this.appManagerId = appManagerId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getDownLoad() {
    return downLoad;
  }

  public void setDownLoad(String downLoad) {
    this.downLoad = downLoad;
  }

  public Integer getIsCoerce() {
    return isCoerce;
  }

  public void setIsCoerce(Integer isCoerce) {
    this.isCoerce = isCoerce;
  }

  public String getTerrace() {
    return terrace;
  }

  public void setTerrace(String terrace) {
    this.terrace = terrace;
  }

  public Long getSiteId() {
    return siteId;
  }

  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getStauts() {
    return stauts;
  }

  public void setStauts(Integer stauts) {
    this.stauts = stauts;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

}
