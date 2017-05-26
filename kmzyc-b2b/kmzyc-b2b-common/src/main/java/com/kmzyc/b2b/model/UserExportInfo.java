package com.kmzyc.b2b.model;

import java.util.Date;

/**
 * 电商 和 总部会员关系 导入 失败信息记录实体
 * 
 * @author Administrator
 * 
 */
public class UserExportInfo {

  private String loginAccount;
  private String status;
  private Date exportDate;
  private String exporType;
  private String remark;

  public UserExportInfo() {

  }

  public UserExportInfo(String loginAccount, String status, Date exportDate, String remark,
      String exportType) {
    this.loginAccount = loginAccount;
    this.status = status;
    this.exportDate = exportDate;
    this.remark = remark;
    this.exporType = exportType;
  }

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getExportDate() {
    return exportDate;
  }

  public void setExportDate(Date exportDate) {
    this.exportDate = exportDate;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getExporType() {
    return exporType;
  }

  public void setExporType(String exporType) {
    this.exporType = exporType;
  }

}
