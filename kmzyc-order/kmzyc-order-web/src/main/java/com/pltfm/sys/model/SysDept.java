package com.pltfm.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SysDept implements Serializable {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.DEPT_ID
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private Integer deptId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.DEPT_CD
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private String deptCd;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.DEPT_NAME
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private String deptName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.UP_DEPTID
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private Integer upDeptid;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.DEPT_LV
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private String deptLv;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.DEPT_REMARK
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private String deptRemark;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.IS_ENABLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private String isEnable;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.CREATE_DATE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private Date createDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.CREATE_USER
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private Integer createUser;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.UPDATE_DATE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private Date updateDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_DEPT.UPDATE_USER
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private Integer updateUser;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * SYS_DEPT
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  private static final long serialVersionUID = 1L;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.DEPT_ID
   * 
   * @return the value of SYS_DEPT.DEPT_ID
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public Integer getDeptId() {
    return deptId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.DEPT_ID
   * 
   * @param deptId the value for SYS_DEPT.DEPT_ID
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.DEPT_CD
   * 
   * @return the value of SYS_DEPT.DEPT_CD
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public String getDeptCd() {
    return deptCd;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.DEPT_CD
   * 
   * @param deptCd the value for SYS_DEPT.DEPT_CD
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setDeptCd(String deptCd) {
    this.deptCd = deptCd == null ? null : deptCd.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.DEPT_NAME
   * 
   * @return the value of SYS_DEPT.DEPT_NAME
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public String getDeptName() {
    return deptName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.DEPT_NAME
   * 
   * @param deptName the value for SYS_DEPT.DEPT_NAME
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setDeptName(String deptName) {
    this.deptName = deptName == null ? null : deptName.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.UP_DEPTID
   * 
   * @return the value of SYS_DEPT.UP_DEPTID
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public Integer getUpDeptid() {
    return upDeptid;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.UP_DEPTID
   * 
   * @param upDeptid the value for SYS_DEPT.UP_DEPTID
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setUpDeptid(Integer upDeptid) {
    this.upDeptid = upDeptid;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.DEPT_LV
   * 
   * @return the value of SYS_DEPT.DEPT_LV
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public String getDeptLv() {
    return deptLv;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.DEPT_LV
   * 
   * @param deptLv the value for SYS_DEPT.DEPT_LV
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setDeptLv(String deptLv) {
    this.deptLv = deptLv == null ? null : deptLv.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.DEPT_REMARK
   * 
   * @return the value of SYS_DEPT.DEPT_REMARK
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public String getDeptRemark() {
    return deptRemark;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.DEPT_REMARK
   * 
   * @param deptRemark the value for SYS_DEPT.DEPT_REMARK
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setDeptRemark(String deptRemark) {
    this.deptRemark = deptRemark == null ? null : deptRemark.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.IS_ENABLE
   * 
   * @return the value of SYS_DEPT.IS_ENABLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public String getIsEnable() {
    return isEnable;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.IS_ENABLE
   * 
   * @param isEnable the value for SYS_DEPT.IS_ENABLE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setIsEnable(String isEnable) {
    this.isEnable = isEnable == null ? null : isEnable.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.CREATE_DATE
   * 
   * @return the value of SYS_DEPT.CREATE_DATE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.CREATE_DATE
   * 
   * @param createDate the value for SYS_DEPT.CREATE_DATE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.CREATE_USER
   * 
   * @return the value of SYS_DEPT.CREATE_USER
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public Integer getCreateUser() {
    return createUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.CREATE_USER
   * 
   * @param createUser the value for SYS_DEPT.CREATE_USER
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setCreateUser(Integer createUser) {
    this.createUser = createUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.UPDATE_DATE
   * 
   * @return the value of SYS_DEPT.UPDATE_DATE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public Date getUpdateDate() {
    return updateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.UPDATE_DATE
   * 
   * @param updateDate the value for SYS_DEPT.UPDATE_DATE
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_DEPT.UPDATE_USER
   * 
   * @return the value of SYS_DEPT.UPDATE_USER
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public Integer getUpdateUser() {
    return updateUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_DEPT.UPDATE_USER
   * 
   * @param updateUser the value for SYS_DEPT.UPDATE_USER
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  public void setUpdateUser(Integer updateUser) {
    this.updateUser = updateUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_DEPT
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof SysDept)) {
      return false;
    }
    SysDept other = (SysDept) that;
    return this.getDeptId() == null ? other == null : this.getDeptId().equals(other.getDeptId())
        && this.getDeptCd() == null ? other == null : this.getDeptCd().equals(other.getDeptCd())
        && this.getDeptName() == null ? other == null : this.getDeptName().equals(
        other.getDeptName())
        && this.getUpDeptid() == null ? other == null : this.getUpDeptid().equals(
        other.getUpDeptid())
        && this.getDeptLv() == null ? other == null : this.getDeptLv().equals(other.getDeptLv())
        && this.getDeptRemark() == null ? other == null : this.getDeptRemark().equals(
        other.getDeptRemark())
        && this.getIsEnable() == null ? other == null : this.getIsEnable().equals(
        other.getIsEnable())
        && this.getCreateDate() == null ? other == null : this.getCreateDate().equals(
        other.getCreateDate())
        && this.getCreateUser() == null ? other == null : this.getCreateUser().equals(
        other.getCreateUser())
        && this.getUpdateDate() == null ? other == null : this.getUpdateDate().equals(
        other.getUpdateDate())
        && this.getUpdateUser() == null ? other == null : this.getUpdateUser().equals(
        other.getUpdateUser());
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_DEPT
   * 
   * @ibatorgenerated Thu Nov 24 14:28:04 CST 2011
   */
  @Override
  public int hashCode() {
    int hash = 23;
    if (getDeptId() != null) {
      hash *= getDeptId().hashCode();
    }
    if (getDeptCd() != null) {
      hash *= getDeptCd().hashCode();
    }
    if (getDeptName() != null) {
      hash *= getDeptName().hashCode();
    }
    if (getUpDeptid() != null) {
      hash *= getUpDeptid().hashCode();
    }
    if (getDeptLv() != null) {
      hash *= getDeptLv().hashCode();
    }
    if (getDeptRemark() != null) {
      hash *= getDeptRemark().hashCode();
    }
    if (getIsEnable() != null) {
      hash *= getIsEnable().hashCode();
    }
    if (getCreateDate() != null) {
      hash *= getCreateDate().hashCode();
    }
    if (getCreateUser() != null) {
      hash *= getCreateUser().hashCode();
    }
    if (getUpdateDate() != null) {
      hash *= getUpdateDate().hashCode();
    }
    if (getUpdateUser() != null) {
      hash *= getUpdateUser().hashCode();
    }
    return hash;
  }
}
