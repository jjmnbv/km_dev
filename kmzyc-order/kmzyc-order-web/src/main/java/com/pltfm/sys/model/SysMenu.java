package com.pltfm.sys.model;

import java.io.Serializable;
import java.util.Date;

public class SysMenu implements Serializable {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.MENU_ID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Integer menuId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.MENU_UPID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Integer menuUpid;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.MENU_NAME
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String menuName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.MENU_LINK
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String menuLink;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.MENU_LV
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String menuLv;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.MENU_ST
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String menuSt;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.MENU_IMG
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String menuImg;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.MENU_REMARK
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String menuRemark;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.SORTNO
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String sortno;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.IS_ENABLE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private String isEnable;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.CREATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Date createDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.CREATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Integer createUser;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.UPDATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Date updateDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * SYS_MENU.UPDATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private Integer updateUser;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database table
   * SYS_MENU
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  private static final long serialVersionUID = 1L;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.MENU_ID
   * 
   * @return the value of SYS_MENU.MENU_ID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Integer getMenuId() {
    return menuId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.MENU_ID
   * 
   * @param menuId the value for SYS_MENU.MENU_ID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setMenuId(Integer menuId) {
    this.menuId = menuId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.MENU_UPID
   * 
   * @return the value of SYS_MENU.MENU_UPID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Integer getMenuUpid() {
    return menuUpid;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.MENU_UPID
   * 
   * @param menuUpid the value for SYS_MENU.MENU_UPID
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setMenuUpid(Integer menuUpid) {
    this.menuUpid = menuUpid;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.MENU_NAME
   * 
   * @return the value of SYS_MENU.MENU_NAME
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getMenuName() {
    return menuName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.MENU_NAME
   * 
   * @param menuName the value for SYS_MENU.MENU_NAME
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setMenuName(String menuName) {
    this.menuName = menuName == null ? null : menuName.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.MENU_LINK
   * 
   * @return the value of SYS_MENU.MENU_LINK
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getMenuLink() {
    return menuLink;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.MENU_LINK
   * 
   * @param menuLink the value for SYS_MENU.MENU_LINK
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setMenuLink(String menuLink) {
    this.menuLink = menuLink == null ? null : menuLink.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.MENU_LV
   * 
   * @return the value of SYS_MENU.MENU_LV
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getMenuLv() {
    return menuLv;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.MENU_LV
   * 
   * @param menuLv the value for SYS_MENU.MENU_LV
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setMenuLv(String menuLv) {
    this.menuLv = menuLv == null ? null : menuLv.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.MENU_ST
   * 
   * @return the value of SYS_MENU.MENU_ST
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getMenuSt() {
    return menuSt;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.MENU_ST
   * 
   * @param menuSt the value for SYS_MENU.MENU_ST
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setMenuSt(String menuSt) {
    this.menuSt = menuSt == null ? null : menuSt.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.MENU_IMG
   * 
   * @return the value of SYS_MENU.MENU_IMG
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getMenuImg() {
    return menuImg;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.MENU_IMG
   * 
   * @param menuImg the value for SYS_MENU.MENU_IMG
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setMenuImg(String menuImg) {
    this.menuImg = menuImg == null ? null : menuImg.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.MENU_REMARK
   * 
   * @return the value of SYS_MENU.MENU_REMARK
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getMenuRemark() {
    return menuRemark;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.MENU_REMARK
   * 
   * @param menuRemark the value for SYS_MENU.MENU_REMARK
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setMenuRemark(String menuRemark) {
    this.menuRemark = menuRemark == null ? null : menuRemark.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.SORTNO
   * 
   * @return the value of SYS_MENU.SORTNO
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getSortno() {
    return sortno;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.SORTNO
   * 
   * @param sortno the value for SYS_MENU.SORTNO
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setSortno(String sortno) {
    this.sortno = sortno == null ? null : sortno.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.IS_ENABLE
   * 
   * @return the value of SYS_MENU.IS_ENABLE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public String getIsEnable() {
    return isEnable;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.IS_ENABLE
   * 
   * @param isEnable the value for SYS_MENU.IS_ENABLE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setIsEnable(String isEnable) {
    this.isEnable = isEnable == null ? null : isEnable.trim();
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.CREATE_DATE
   * 
   * @return the value of SYS_MENU.CREATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.CREATE_DATE
   * 
   * @param createDate the value for SYS_MENU.CREATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.CREATE_USER
   * 
   * @return the value of SYS_MENU.CREATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Integer getCreateUser() {
    return createUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.CREATE_USER
   * 
   * @param createUser the value for SYS_MENU.CREATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setCreateUser(Integer createUser) {
    this.createUser = createUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.UPDATE_DATE
   * 
   * @return the value of SYS_MENU.UPDATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Date getUpdateDate() {
    return updateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.UPDATE_DATE
   * 
   * @param updateDate the value for SYS_MENU.UPDATE_DATE
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUpdateDate(Date updateDate) {
    this.updateDate = updateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SYS_MENU.UPDATE_USER
   * 
   * @return the value of SYS_MENU.UPDATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public Integer getUpdateUser() {
    return updateUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SYS_MENU.UPDATE_USER
   * 
   * @param updateUser the value for SYS_MENU.UPDATE_USER
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  public void setUpdateUser(Integer updateUser) {
    this.updateUser = updateUser;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method corresponds to the database
   * table SYS_MENU
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  @Override
  public boolean equals(Object that) {
    if (this == that) {
      return true;
    }
    if (!(that instanceof SysMenu)) {
      return false;
    }
    SysMenu other = (SysMenu) that;
    return this.getMenuId() == null ? other == null : this.getMenuId().equals(other.getMenuId())
        && this.getMenuUpid() == null ? other == null : this.getMenuUpid().equals(
        other.getMenuUpid())
        && this.getMenuName() == null ? other == null : this.getMenuName().equals(
        other.getMenuName())
        && this.getMenuLink() == null ? other == null : this.getMenuLink().equals(
        other.getMenuLink())
        && this.getMenuLv() == null ? other == null : this.getMenuLv().equals(other.getMenuLv())
        && this.getMenuSt() == null ? other == null : this.getMenuSt().equals(other.getMenuSt())
        && this.getMenuImg() == null ? other == null : this.getMenuImg().equals(other.getMenuImg())
        && this.getMenuRemark() == null ? other == null : this.getMenuRemark().equals(
        other.getMenuRemark())
        && this.getSortno() == null ? other == null : this.getSortno().equals(other.getSortno())
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
   * table SYS_MENU
   * 
   * @ibatorgenerated Fri Nov 25 22:18:12 CST 2011
   */
  @Override
  public int hashCode() {
    int hash = 23;
    if (getMenuId() != null) {
      hash *= getMenuId().hashCode();
    }
    if (getMenuUpid() != null) {
      hash *= getMenuUpid().hashCode();
    }
    if (getMenuName() != null) {
      hash *= getMenuName().hashCode();
    }
    if (getMenuLink() != null) {
      hash *= getMenuLink().hashCode();
    }
    if (getMenuLv() != null) {
      hash *= getMenuLv().hashCode();
    }
    if (getMenuSt() != null) {
      hash *= getMenuSt().hashCode();
    }
    if (getMenuImg() != null) {
      hash *= getMenuImg().hashCode();
    }
    if (getMenuRemark() != null) {
      hash *= getMenuRemark().hashCode();
    }
    if (getSortno() != null) {
      hash *= getSortno().hashCode();
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
