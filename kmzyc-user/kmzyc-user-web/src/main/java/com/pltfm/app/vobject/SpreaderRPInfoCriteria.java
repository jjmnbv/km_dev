package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 微商用户红包 条件查询
 * 
 * @author xys
 *
 */
public class SpreaderRPInfoCriteria implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  /**
   * 微商活动开始时间
   */
  private Date activeStartDate;
  /**
   * 微商活动结束时间
   */
  private Date activeEndDate;
  /**
   * 红包清除时间
   */
  private Date rpEndDate;


  public Date getActiveStartDate() {
    return activeStartDate;
  }

  public void setActiveStartDate(Date activeStartDate) {
    this.activeStartDate = activeStartDate;
  }

  public Date getActiveEndDate() {
    return activeEndDate;
  }

  public void setActiveEndDate(Date activeEndDate) {
    this.activeEndDate = activeEndDate;
  }

  public Date getRpEndDate() {
    return rpEndDate;
  }

  public void setRpEndDate(Date rpEndDate) {
    this.rpEndDate = rpEndDate;
  }


  @Override
  public String toString() {
    return "SpreaderRPInfoCriteria [activeStartDate=" + activeStartDate + ", activeEndDate="
        + activeEndDate + ", rpEndDate=" + rpEndDate + "]";

  }

}
