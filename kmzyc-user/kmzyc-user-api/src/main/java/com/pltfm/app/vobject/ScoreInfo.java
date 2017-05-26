package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分明细实体信息
 * 
 * @author zhl
 * 
 */
public class ScoreInfo implements Serializable {
  /**UID*/
  private static final long serialVersionUID = -8650155240585643479L;
/** 客户积分规则主键 **/
  private Integer n_scoreRuleId;
  /** 客户积分规则名称 **/
  private String scoreRuleDiscribe;
/** 积分明细主键 **/
  private Integer n_scoreInfoId;
  /** 登录账号 **/
  private Integer n_loginId;
  /** 登录账号 **/
  private String loginAccount;
  /** 登录卡号 **/
  private String cardNum;
  /** 积分明细类型 积分积累和积分消费 **/
  private Integer scoreType;
  /** 积分数 **/
  private Double scoreNumber;
  /** 积分简述 **/
  private String discribe;
  /** 创建时间 **/
  private Date d_createDate;
  /** 创建人 **/
  private Integer n_created;
  /** 修改时间 **/
  private Date d_modifyDate;
  /** 修改人 **/
  private Integer n_modified;
  /** 开始索引值 **/
  private Integer startIndex;
  /** 结束索引值 **/
  private Integer endIndex;
  /** 积分备注 **/
  private String remark;
  /** 优惠劵面值 **/
  private Integer couponValue;
  /** 消费类型1---优惠券0其它 **/
  private Integer consumeType;
  /** 积分兑换优惠劵状态;1---成功0--失败 **/
  private Integer isStauts;

  /** 记录来源 **/
  private String source;
  
  /**添加开始时间*/
  private Date d_createDateStart;
  /**添加结束时间*/
  private Date d_createDateEnd;
  

  public Date getD_createDateStart() {
    return d_createDateStart;
  }

  public void setD_createDateStart(Date d_createDateStart) {
    this.d_createDateStart = d_createDateStart;
  }

  public Date getD_createDateEnd() {
    return d_createDateEnd;
  }

  public void setD_createDateEnd(Date d_createDateEnd) {
    this.d_createDateEnd = d_createDateEnd;
  }

  public Integer getN_scoreRuleId() {
    return n_scoreRuleId;
  }

  public void setN_scoreRuleId(Integer n_scoreRuleId) {
    this.n_scoreRuleId = n_scoreRuleId;
  }
  
  public String getScoreRuleDiscribe() {
      return scoreRuleDiscribe;
  }

  public void setScoreRuleDiscribe(String scoreRuleDiscribe) {
      this.scoreRuleDiscribe = scoreRuleDiscribe;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getCouponValue() {
    return couponValue;
  }

  public void setCouponValue(Integer couponValue) {
    this.couponValue = couponValue;
  }

  public Integer getConsumeType() {
    return consumeType;
  }

  public void setConsumeType(Integer consumeType) {
    this.consumeType = consumeType;
  }

  public Integer getIsStauts() {
    return isStauts;
  }

  public void setIsStauts(Integer isStauts) {
    this.isStauts = isStauts;
  }

  public Integer getN_scoreInfoId() {
    return n_scoreInfoId;
  }

  public void setN_scoreInfoId(Integer nScoreInfoId) {
    n_scoreInfoId = nScoreInfoId;
  }

  public Integer getN_loginId() {
    return n_loginId;
  }

  public void setN_loginId(Integer nLoginId) {
    n_loginId = nLoginId;
  }

  public Integer getScoreType() {
    return scoreType;
  }

  public void setScoreType(Integer scoreType) {
    this.scoreType = scoreType;
  }

  public Double getScoreNumber() {
    return scoreNumber;
  }

  public void setScoreNumber(Double scoreNumber) {
    this.scoreNumber = scoreNumber;
  }

  public String getDiscribe() {
    return discribe;
  }

  public void setDiscribe(String discribe) {
    this.discribe = discribe;
  }

  public Date getD_createDate() {
    return d_createDate;
  }

  public void setD_createDate(Date dCreateDate) {
    d_createDate = dCreateDate;
  }

  public Integer getN_created() {
    return n_created;
  }

  public void setN_created(Integer nCreated) {
    n_created = nCreated;
  }

  public Date getD_modifyDate() {
    return d_modifyDate;
  }

  public void setD_modifyDate(Date dModifyDate) {
    d_modifyDate = dModifyDate;
  }

  public Integer getN_modified() {
    return n_modified;
  }

  public void setN_modified(Integer nModified) {
    n_modified = nModified;
  }

  public Integer getStartIndex() {
    return startIndex;
  }

  public void setStartIndex(Integer startIndex) {
    this.startIndex = startIndex;
  }

  public Integer getEndIndex() {
    return endIndex;
  }

  public void setEndIndex(Integer endIndex) {
    this.endIndex = endIndex;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public String getCardNum() {
    return cardNum;
  }

  public void setCardNum(String cardNum) {
    this.cardNum = cardNum;
  }


  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public String toString() {
    return new StringBuffer().append("{\"scoreNumber\":").append(scoreNumber)
        .append(",\"scoreType\":").append(scoreType).append(",\"discribe\":").append(discribe)
        .append(",\"d_createDate\":").append(d_createDate).append(",\"n_scoreInfoId\":")
        .append(n_scoreInfoId).append(",\"source\":").append(source).append("}").toString();
  }


}
