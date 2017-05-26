package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 安全问题答案信息类
 * 
 * @author cjm
 * @since 2013-8-6
 */
public class BnesAnswerInfo implements Serializable {

  /**
   * 问题答案ID
   */
  private Integer answerInfoId;

  /**
   * 安全问题ID
   */
  private Integer safeQuestionId;

  /**
   * 问题名称
   **/
  private String question_name;

  /**
   * 答案内容
   */
  private String answerContent;

  /**
   * 创建日期
   */
  private Date createDate;

  /**
   * 创建人
   */
  private Integer created;

  /**
   * 修改日期
   */
  private Date modifyDate;

  /**
   * 修改人
   */
  private Integer modified;

  /**
   * 登陆ID
   */
  private Integer loginId;

  /**
   * 登录账号
   */
  private String loginAccount;

  /**
   * 姓名
   */
  private String name;

  /**
   * 账户号
   */
  private String accountLogin;

  /**
   * 账户ID
   */
  private Integer accountId;

  // ------------ for page
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

  public Integer getAnswerInfoId() {
    return answerInfoId;
  }

  public void setAnswerInfoId(Integer answerInfoId) {
    this.answerInfoId = answerInfoId;
  }

  public Integer getSafeQuestionId() {
    return safeQuestionId;
  }

  public void setSafeQuestionId(Integer safeQuestionId) {
    this.safeQuestionId = safeQuestionId;
  }

  public String getAnswerContent() {
    return answerContent;
  }

  public void setAnswerContent(String answerContent) {
    this.answerContent = answerContent;
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

  public Integer getLoginId() {
    return loginId;
  }

  public void setLoginId(Integer loginId) {
    this.loginId = loginId;
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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAccountLogin() {
    return accountLogin;
  }

  public void setAccountLogin(String accountLogin) {
    this.accountLogin = accountLogin;
  }

  public String getQuestion_name() {
    return question_name;
  }

  public void setQuestion_name(String questionName) {
    question_name = questionName;
  }

}
