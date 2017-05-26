package com.pltfm.sys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@SuppressWarnings("unchecked")
public class SysUser implements Serializable {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_ID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Integer userId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.DEPT_ID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Integer deptId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_NAME
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_PWD
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userPwd;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_REAL
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userReal;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_SEX
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userSex;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_BIRTHDAY
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Date userBirthday;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_CARDNO
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userCardno;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_PHONE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userPhone;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_MPHONE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userMphone;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_IP
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userIp;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_LASTTIME
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Date userLasttime;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_ST
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userSt;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_REMARK
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userRemark;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.IS_ENABLE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String isEnable;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.CREATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Date createDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.CREATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Integer createUser;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.UPDATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Date updateDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.UPDATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Integer updateUser;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_USER.USER_ALIAS
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String userAlias;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * SYS_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private static final long serialVersionUID = 1L;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_ID
   * 
   * @return the value of SYS_USER.USER_ID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Integer getUserId() {
    return userId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_ID
   * 
   * @param userId the value for SYS_USER.USER_ID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserId(Integer userId) {
    this.userId = userId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.DEPT_ID
   * 
   * @return the value of SYS_USER.DEPT_ID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Integer getDeptId() {
    return deptId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.DEPT_ID
   * 
   * @param deptId the value for SYS_USER.DEPT_ID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setDeptId(Integer deptId) {
    this.deptId = deptId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_NAME
   * 
   * @return the value of SYS_USER.USER_NAME
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserName() {
    return userName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_NAME
   * 
   * @param userName the value for SYS_USER.USER_NAME
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserName(String userName) {
    this.userName = userName == null ? null : userName.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_PWD
   * 
   * @return the value of SYS_USER.USER_PWD
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserPwd() {
    return userPwd;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_PWD
   * 
   * @param userPwd the value for SYS_USER.USER_PWD
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserPwd(String userPwd) {
    this.userPwd = userPwd == null ? null : userPwd.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_REAL
   * 
   * @return the value of SYS_USER.USER_REAL
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserReal() {
    return userReal;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_REAL
   * 
   * @param userReal the value for SYS_USER.USER_REAL
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserReal(String userReal) {
    this.userReal = userReal == null ? null : userReal.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_SEX
   * 
   * @return the value of SYS_USER.USER_SEX
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserSex() {
    return userSex;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_SEX
   * 
   * @param userSex the value for SYS_USER.USER_SEX
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserSex(String userSex) {
    this.userSex = userSex == null ? null : userSex.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_BIRTHDAY
   * 
   * @return the value of SYS_USER.USER_BIRTHDAY
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Date getUserBirthday() {
    return userBirthday;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_BIRTHDAY
   * 
   * @param userBirthday the value for SYS_USER.USER_BIRTHDAY
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserBirthday(Date userBirthday) {
    this.userBirthday = userBirthday;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_CARDNO
   * 
   * @return the value of SYS_USER.USER_CARDNO
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserCardno() {
    return userCardno;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_CARDNO
   * 
   * @param userCardno the value for SYS_USER.USER_CARDNO
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserCardno(String userCardno) {
    this.userCardno = userCardno == null ? null : userCardno.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_PHONE
   * 
   * @return the value of SYS_USER.USER_PHONE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserPhone() {
    return userPhone;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_PHONE
   * 
   * @param userPhone the value for SYS_USER.USER_PHONE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserPhone(String userPhone) {
    this.userPhone = userPhone == null ? null : userPhone.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_MPHONE
   * 
   * @return the value of SYS_USER.USER_MPHONE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserMphone() {
    return userMphone;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_MPHONE
   * 
   * @param userMphone the value for SYS_USER.USER_MPHONE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserMphone(String userMphone) {
    this.userMphone = userMphone == null ? null : userMphone.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_IP
   * 
   * @return the value of SYS_USER.USER_IP
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserIp() {
    return userIp;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_IP
   * 
   * @param userIp the value for SYS_USER.USER_IP
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserIp(String userIp) {
    this.userIp = userIp == null ? null : userIp.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_LASTTIME
   * 
   * @return the value of SYS_USER.USER_LASTTIME
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Date getUserLasttime() {
    return userLasttime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_LASTTIME
   * 
   * @param userLasttime the value for SYS_USER.USER_LASTTIME
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserLasttime(Date userLasttime) {
    this.userLasttime = userLasttime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_ST
   * 
   * @return the value of SYS_USER.USER_ST
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserSt() {
    return userSt;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_ST
   * 
   * @param userSt the value for SYS_USER.USER_ST
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserSt(String userSt) {
    this.userSt = userSt == null ? null : userSt.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_REMARK
   * 
   * @return the value of SYS_USER.USER_REMARK
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserRemark() {
    return userRemark;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_REMARK
   * 
   * @param userRemark the value for SYS_USER.USER_REMARK
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserRemark(String userRemark) {
    this.userRemark = userRemark == null ? null : userRemark.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.IS_ENABLE
   * 
   * @return the value of SYS_USER.IS_ENABLE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getIsEnable() {
    return isEnable;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.IS_ENABLE
   * 
   * @param isEnable the value for SYS_USER.IS_ENABLE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setIsEnable(String isEnable) {
    this.isEnable = isEnable == null ? null : isEnable.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.CREATE_DATE
   * 
   * @return the value of SYS_USER.CREATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.CREATE_DATE
   * 
   * @param createDate the value for SYS_USER.CREATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.CREATE_USER
   * 
   * @return the value of SYS_USER.CREATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Integer getCreateUser() {
    return createUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.CREATE_USER
   * 
   * @param createUser the value for SYS_USER.CREATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setCreateUser(Integer createUser) {
    this.createUser = createUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.UPDATE_DATE
   * 
   * @return the value of SYS_USER.UPDATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Date getUpdateDate() {
    return updateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.UPDATE_DATE
   * 
   * @param updateDate the value for SYS_USER.UPDATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.UPDATE_USER
   * 
   * @return the value of SYS_USER.UPDATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Integer getUpdateUser() {
    return updateUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.UPDATE_USER
   * 
   * @param updateUser the value for SYS_USER.UPDATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUpdateUser(Integer updateUser) {
    this.updateUser = updateUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_USER.USER_ALIAS
   * 
   * @return the value of SYS_USER.USER_ALIAS
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getUserAlias() {
    return userAlias;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_USER.USER_ALIAS
   * 
   * @param userAlias the value for SYS_USER.USER_ALIAS
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUserAlias(String userAlias) {
    this.userAlias = userAlias == null ? null : userAlias.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof SysUser)) {
      return false;
    }
    SysUser other = (SysUser) that;
    return this.getUserId() == null ? other == null : this.getUserId().equals(other.getUserId())
        && this.getDeptId() == null ? other == null : this.getDeptId().equals(other.getDeptId())
        && this.getUserName() == null ? other == null : this.getUserName().equals(
        other.getUserName())
        && this.getUserPwd() == null ? other == null : this.getUserPwd().equals(other.getUserPwd())
        && this.getUserReal() == null ? other == null : this.getUserReal().equals(
        other.getUserReal())
        && this.getUserSex() == null ? other == null : this.getUserSex().equals(other.getUserSex())
        && this.getUserBirthday() == null ? other == null : this.getUserBirthday().equals(
        other.getUserBirthday())
        && this.getUserCardno() == null ? other == null : this.getUserCardno().equals(
        other.getUserCardno())
        && this.getUserPhone() == null ? other == null : this.getUserPhone().equals(
        other.getUserPhone())
        && this.getUserMphone() == null ? other == null : this.getUserMphone().equals(
        other.getUserMphone())
        && this.getUserIp() == null ? other == null : this.getUserIp().equals(other.getUserIp())
        && this.getUserLasttime() == null ? other == null : this.getUserLasttime().equals(
        other.getUserLasttime())
        && this.getUserSt() == null ? other == null : this.getUserSt().equals(other.getUserSt())
        && this.getUserRemark() == null ? other == null : this.getUserRemark().equals(
        other.getUserRemark())
        && this.getIsEnable() == null ? other == null : this.getIsEnable().equals(
        other.getIsEnable())
        && this.getCreateDate() == null ? other == null : this.getCreateDate().equals(
        other.getCreateDate())
        && this.getCreateUser() == null ? other == null : this.getCreateUser().equals(
        other.getCreateUser())
        && this.getUpdateDate() == null ? other == null : this.getUpdateDate().equals(
        other.getUpdateDate())
        && this.getUpdateUser() == null ? other == null : this.getUpdateUser().equals(
        other.getUpdateUser())
        && this.getUserAlias() == null ? other == null : this.getUserAlias().equals(
        other.getUserAlias());
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  @Override
  public int hashCode() {
    int hash = 23;
    if (getUserId() != null) {
      hash *= getUserId().hashCode();
    }
    if (getDeptId() != null) {
      hash *= getDeptId().hashCode();
    }
    if (getUserName() != null) {
      hash *= getUserName().hashCode();
    }
    if (getUserPwd() != null) {
      hash *= getUserPwd().hashCode();
    }
    if (getUserReal() != null) {
      hash *= getUserReal().hashCode();
    }
    if (getUserSex() != null) {
      hash *= getUserSex().hashCode();
    }
    if (getUserBirthday() != null) {
      hash *= getUserBirthday().hashCode();
    }
    if (getUserCardno() != null) {
      hash *= getUserCardno().hashCode();
    }
    if (getUserPhone() != null) {
      hash *= getUserPhone().hashCode();
    }
    if (getUserMphone() != null) {
      hash *= getUserMphone().hashCode();
    }
    if (getUserIp() != null) {
      hash *= getUserIp().hashCode();
    }
    if (getUserLasttime() != null) {
      hash *= getUserLasttime().hashCode();
    }
    if (getUserSt() != null) {
      hash *= getUserSt().hashCode();
    }
    if (getUserRemark() != null) {
      hash *= getUserRemark().hashCode();
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
    if (getUserAlias() != null) {
      hash *= getUserAlias().hashCode();
    }
    return hash;
  }

  private String headMenuId = "1";

  public String getHeadMenuId() {
    return headMenuId;
  }

  public void setHeadMenuId(String headMenuId) {
    this.headMenuId = headMenuId == null ? null : headMenuId.trim();
  }

  List menuList;

  public List getMenuList() {
    return menuList;
  }

  public void setMenuList(List menuList) {
    this.menuList = menuList;
  }

  private Integer roleId;

  public Integer getRoleId() {
    return roleId;
  }

  public void setRoleId(Integer roleId) {
    this.roleId = roleId;
  }

  private String roleIdsStr;

  public String getRoleIdsStr() {
    return roleIdsStr;
  }

  public void setRoleIdsStr(String roleIdsStr) {
    this.roleIdsStr = roleIdsStr;
  }

  private String roleName;

  public String getRoleName() {
    return roleName;
  }

  public void setRoleName(String roleName) {
    this.roleName = roleName;
  }

  private String areaNo;

  public String getAreaNo() {
    return areaNo;
  }

  public void setAreaNo(String areaNo) {
    this.areaNo = areaNo;
  }

  // ------------ for page
  int skip;
  int max;

  public int getSkip() {
    return skip;
  }

  public void setSkip(int skip) {
    this.skip = skip;
  }

  public int getMax() {
    return max;
  }

  public void setMax(int max) {
    this.max = max;
  }
}