package com.kmzyc.b2b.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 消息中心详情实体
 * 
 * @author luoyi
 * @createDate 2013/10/11
 * */
public class SiteNoticeDetail implements Serializable {
  private static final long serialVersionUID = 799344183465301915L;
  /**
   * 消息ID
   * 
   * */
  private Integer messageId;
  /**
   * 发布消息ID
   * 
   * */
  private Integer infoPromptId;

  /**
   * 查看账户ID
   * 
   * */
  private Integer accountId;
  /**
   * 账号
   */
  private String loginAccount;
  /**
   * 登录ID
   * 
   * */
  private Integer loginId;
  /**
   * 客户类型
   */
  private Integer customerTypeId;
  /** 客户类别子类别id(只传值) **/
  private Integer customer_son_id;
  /** 客户类型名称 **/
  private String customerName;

  /**
   * 内容
   * 
   * */
  private String content;
  /**
   * 消息类型
   * 
   * */
  private Integer type;
  /**
   * 标题
   * 
   * */
  private String title;

  /**
   * 
   * 发布日期
   * */
  private Date releaseDate;
  /**
   * 消息查看日期
   * 
   * */
  private Date checkDate;

  /**
   * 消息查看状态
   * 
   * */
  private Integer status;

  public Integer getMessageId() {
    return messageId;
  }

  public void setMessageId(Integer messageId) {
    this.messageId = messageId;
  }

  public Integer getInfoPromptId() {
    return infoPromptId;
  }

  public void setInfoPromptId(Integer infoPromptId) {
    this.infoPromptId = infoPromptId;
  }

  public Integer getAccountId() {
    return accountId;
  }

  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
  }

  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  public Integer getCustomer_son_id() {
    return customer_son_id;
  }

  public void setCustomer_son_id(Integer customer_son_id) {
    this.customer_son_id = customer_son_id;
  }

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Date getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(Date releaseDate) {
    this.releaseDate = releaseDate;
  }

  public Date getCheckDate() {
    return checkDate;
  }

  public void setCheckDate(Date checkDate) {
    this.checkDate = checkDate;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "siteNoticeDetail [messageId=" + messageId + ", infoPromptId=" + infoPromptId
        + ", accountId=" + accountId + ", loginAccount=" + loginAccount + ", loginId=" + loginId
        + ", customerTypeId=" + customerTypeId + ", customer_son_id=" + customer_son_id
        + ", customerName=" + customerName + ", content=" + content + ", type=" + type + ", title="
        + title + ", releaseDate=" + releaseDate + ", checkDate=" + checkDate + ", status="
        + status + "]";
  }

}
