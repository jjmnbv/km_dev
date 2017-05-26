package com.kmzyc.b2b.model;

import java.util.Date;

public class SiteNoticeResponse {

  private Integer messageId;

  /**
   * 标题
   * 
   * */
  private String title;

  /**
   * 内容
   * 
   * */
  private String content;

  public Integer getMessageId() {
    return messageId;
  }

  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
  }

  /**
   * 发布日期
   * */
  private Date releaseDate;

  /**
   * 消息查看状态
   * 
   * */
  private Integer status;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

}
