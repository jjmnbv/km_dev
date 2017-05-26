package com.pltfm.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SysRoleCatgroup implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 5291492821907089355L;
  private Integer xrpId;
  private Integer roleId;
  private Integer type;
  private Integer optionId;
  private String remark;
  private Integer isEnable;
  private Date createDate;
  private String createUser;
  private Date updateDate;
  private String updateUser;

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Integer getOptionId() {
    return optionId;
  }

  public void setOptionId(Integer optionId) {
    this.optionId = optionId;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Integer getIsEnable() {
    return isEnable;
  }

  public void setIsEnable(Integer isEnable) {
    this.isEnable = isEnable;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getCreateUser() {
    return createUser;
  }

  public void setCreateUser(String createUser) {
    this.createUser = createUser;
  }

  public Date getUpdateDate() {
    return updateDate;
  }

  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  public String getUpdateUser() {
    return updateUser;
  }

  public void setUpdateUser(String updateUser) {
    this.updateUser = updateUser;
  }

  @Override
  public String toString() {
    return "Test [roleId=" + roleId + ", type=" + type + ", optionId=" + optionId + ", remark="
        + remark + ", isEnable=" + isEnable + ", createDate=" + createDate + ", createUser="
        + createUser + ", updateDate=" + updateDate + ", updateUser=" + updateUser + "]";
  }

  /**
   * @param xrpId the xrpId to set
   */
  public void setXrpId(Integer xrpId) {
    this.xrpId = xrpId;
  }

  /**
   * @return the xrpId
   */
  public Integer getXrpId() {
    return xrpId;
  }
}
