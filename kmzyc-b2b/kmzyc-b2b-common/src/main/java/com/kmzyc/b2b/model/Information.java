package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.util.Date;

public class Information implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 1L;

  private Long infoId;

  private Long typeId;

  private String name;

  private String content;

  private String title;

  private String key;

  private String description;

  private Integer status;

  private Date createDate;

  private Integer created;

  private Date modifyDate;

  private Integer modified;

  private Integer orders;

  private Long siteId;

  public Long getInfoId() {
    return infoId;
  }

  public void setInfoId(Long infoId) {
    this.infoId = infoId;
  }

  public Long getTypeId() {
    return typeId;
  }

  public void setTypeId(Long typeId) {
    this.typeId = typeId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getKey() {
    return key;
  }

  public void setKey(String key) {
    this.key = key;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
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

  public Integer getOrders() {
    return orders;
  }

  public void setOrders(Integer orders) {
    this.orders = orders;
  }

  public Long getSiteId() {
    return siteId;
  }

  public void setSiteId(Long siteId) {
    this.siteId = siteId;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }


}
