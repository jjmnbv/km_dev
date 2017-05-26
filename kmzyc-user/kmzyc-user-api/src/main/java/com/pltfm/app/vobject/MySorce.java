package com.pltfm.app.vobject;

import java.io.Serializable;

/**
 * 账户信息类
 * 
 * @author cjm
 * @since 2013-7-10
 */
public class MySorce implements Serializable {
  /**
   * 登录主键
   */
  private Integer n_LoginId;
  /**
   * 可用积分
   */
  private Integer n_AvailableIntegral;
  /** 客户等级名称 **/
  private String level_Name;
  /** 下个客户等级名称 **/
  private String next_Level_Name;
  /**
   * 交易金额
   * 
   */
  private Integer amount;

  /**
   * 再消费金额升级
   * 
   */
  private Integer afterAmount;

  public Integer getAfterAmount() {
    return afterAmount;
  }

  public void setAfterAmount(Integer afterAmount) {
    this.afterAmount = afterAmount;
  }

  public Integer getN_LoginId() {
    return n_LoginId;
  }

  public void setN_LoginId(Integer n_LoginId) {
    this.n_LoginId = n_LoginId;
  }

  public Integer getN_AvailableIntegral() {
    return n_AvailableIntegral;
  }

  public void setN_AvailableIntegral(Integer n_AvailableIntegral) {
    this.n_AvailableIntegral = n_AvailableIntegral;
  }

  public String getLevel_Name() {
    return level_Name;
  }

  public void setLevel_Name(String level_Name) {
    this.level_Name = level_Name;
  }

  public String getNext_Level_Name() {
    return next_Level_Name;
  }

  public void setNext_Level_Name(String next_Level_Name) {
    this.next_Level_Name = next_Level_Name;
  }

  public double getAmount() {
    return amount;
  }

  public void setAmount(Integer amount) {
    this.amount = amount;
  }


}
