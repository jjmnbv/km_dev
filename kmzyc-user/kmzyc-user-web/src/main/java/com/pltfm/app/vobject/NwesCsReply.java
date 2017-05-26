package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/***
 * 
 * 服务回复信息
 */
public class NwesCsReply implements Serializable {
  /***
   * 
   * 服务回复信息主键
   */
  private Integer replyId;

  /***
   * 
   * 服务回复信息外键
   */
  private Integer customerSurveyId;

  /***
   * 
   * 服务回复信息内容
   */
  private String replyContent;

  /***
   * 
   * 服务回复时间
   */
  private Date replyRdate;
  /***
   * 
   * 回复父编号
   */
  private Integer replyParen;
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;

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

  public Integer getReplyId() {
    return replyId;
  }

  public void setReplyId(Integer replyId) {
    this.replyId = replyId;
  }

  public Integer getCustomerSurveyId() {
    return customerSurveyId;
  }

  public void setCustomerSurveyId(Integer customerSurveyId) {
    this.customerSurveyId = customerSurveyId;
  }

  public String getReplyContent() {
    return replyContent;
  }

  public void setReplyContent(String replyContent) {
    this.replyContent = replyContent;
  }

  public Date getReplyRdate() {
    return replyRdate;
  }

  public void setReplyRdate(Date replyRdate) {
    this.replyRdate = replyRdate;
  }

  public Integer getReplyParen() {
    return replyParen;
  }

  public void setReplyParen(Integer replyParen) {
    this.replyParen = replyParen;
  }
}
