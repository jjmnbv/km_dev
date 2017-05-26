package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * cps跳转数据
 * 
 * @author xiongliguang
 * 
 */
public class CpsTrackInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  private Long tid;
  /**
   * 数据来源
   */
  private String source;
  /**
   * 推广渠道
   */
  private String channel;
  /**
   * 活动ID
   */
  private String campaignId;
  /**
   * 反馈标签
   */
  private String feedback;
  /**
   * 目标地址
   */
  private String target;

  /**
   * 跳转日期
   */
  private Date trackDate;

  public Long getTid() {
    return tid;
  }

  public void setTid(Long tid) {
    this.tid = tid;
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

  public String getCampaignId() {
    return campaignId;
  }

  public void setCampaignId(String campaignId) {
    this.campaignId = campaignId;
  }

  public String getFeedback() {
    return feedback;
  }

  public void setFeedback(String feedback) {
    this.feedback = feedback;
  }

  public String getTarget() {
    return target;
  }

  public void setTarget(String target) {
    this.target = target;
  }

  public Date getTrackDate() {
    return trackDate;
  }

  public void setTrackDate(Date trackDate) {
    this.trackDate = trackDate;
  }
}
