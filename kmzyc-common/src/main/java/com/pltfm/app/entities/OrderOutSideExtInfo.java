package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;

public class OrderOutSideExtInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long oid;
  /**
   * 订单编号
   */
  private String orderCode;
  /**
   * 活动ID
   */
  private String campaignid;
  /**
   * 反馈标签
   */
  private String feedback;

  /**
   * 来源
   */
  private String source;

  /**
   * 渠道
   */
  private String channel;

  /**
   * 佣金类型
   */
  private String commissionType;

  /**
   * 推广类型
   */
  private Integer promotionType;
  /**
   * 创建时间
   */
  private Date createdate;

  /**
   * 用户名
   */
  private String uname;
  /**
   * 用户ID
   */
  private String userid;
  /**
   * 跟踪code
   */
  private String trackingCode;

  public Long getOid() {
    return oid;
  }

  public void setOid(Long oid) {
    this.oid = oid;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  public String getCampaignid() {
    return campaignid;
  }

  public void setCampaignid(String campaignid) {
    this.campaignid = campaignid;
  }

  public String getFeedback() {
    return feedback;
  }

  public void setFeedback(String feedback) {
    this.feedback = feedback;
  }

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String getChannel() {
    return channel;
  }

  public void setChannel(String channel) {
    this.channel = channel;
  }

  public String getCommissionType() {
    return commissionType;
  }

  public void setCommissionType(String commissionType) {
    this.commissionType = commissionType;
  }

  public Date getCreatedate() {
    return createdate;
  }

  public void setCreatedate(Date createdate) {
    this.createdate = createdate;
  }

  public String getUname() {
    return uname;
  }

  public void setUname(String uname) {
    this.uname = uname;
  }

  public String getUserid() {
    return userid;
  }

  public void setUserid(String userid) {
    this.userid = userid;
  }

  public String getTrackingCode() {
    return trackingCode;
  }

  public void setTrackingCode(String trackingCode) {
    this.trackingCode = trackingCode;
  }

  public Integer getPromotionType() {
    return promotionType;
  }

  public void setPromotionType(Integer promotionType) {
    this.promotionType = promotionType;
  }
}
