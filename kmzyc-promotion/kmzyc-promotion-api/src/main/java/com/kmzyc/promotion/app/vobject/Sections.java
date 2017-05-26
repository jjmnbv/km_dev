package com.kmzyc.promotion.app.vobject;

import java.util.Date;

public class Sections implements java.io.Serializable {
  /**
   * 
   */
  private static final long serialVersionUID = -6165443721926635104L;

  /**
   * 栏目ID
   */
  private Long sectionsId;

  /**
   * 栏目编码
   */
  private String sectionsCode;

  /**
   * 栏目名称
   */
  private String sectionsName;

  /**
   * 创建时间
   */
  private Date createTime;

  /**
   * 栏目标识，用以与前端约定显示
   */
  private String identification;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SECTIONS.SECTIONS_ID
   * 
   * @return the value of SECTIONS.SECTIONS_ID
   * 
   * @ibatorgenerated Fri Jul 12 16:51:15 CST 2013
   */
  public Long getSectionsId() {
    return sectionsId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SECTIONS.SECTIONS_ID
   * 
   * @param sectionsId the value for SECTIONS.SECTIONS_ID
   * 
   * @ibatorgenerated Fri Jul 12 16:51:15 CST 2013
   */
  public void setSectionsId(Long sectionsId) {
    this.sectionsId = sectionsId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SECTIONS.SECTIONS_CODE
   * 
   * @return the value of SECTIONS.SECTIONS_CODE
   * 
   * @ibatorgenerated Fri Jul 12 16:51:15 CST 2013
   */
  public String getSectionsCode() {
    return sectionsCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SECTIONS.SECTIONS_CODE
   * 
   * @param sectionsCode the value for SECTIONS.SECTIONS_CODE
   * 
   * @ibatorgenerated Fri Jul 12 16:51:15 CST 2013
   */
  public void setSectionsCode(String sectionsCode) {
    this.sectionsCode = sectionsCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SECTIONS.SECTIONS_NAME
   * 
   * @return the value of SECTIONS.SECTIONS_NAME
   * 
   * @ibatorgenerated Fri Jul 12 16:51:15 CST 2013
   */
  public String getSectionsName() {
    return sectionsName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SECTIONS.SECTIONS_NAME
   * 
   * @param sectionsName the value for SECTIONS.SECTIONS_NAME
   * 
   * @ibatorgenerated Fri Jul 12 16:51:15 CST 2013
   */
  public void setSectionsName(String sectionsName) {
    this.sectionsName = sectionsName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column SECTIONS.CREATE_TIME
   * 
   * @return the value of SECTIONS.CREATE_TIME
   * 
   * @ibatorgenerated Fri Jul 12 16:51:15 CST 2013
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column SECTIONS.CREATE_TIME
   * 
   * @param createTime the value for SECTIONS.CREATE_TIME
   * 
   * @ibatorgenerated Fri Jul 12 16:51:15 CST 2013
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
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

  public String getIdentification() {
    return identification;
  }

  public void setIdentification(String identification) {
    this.identification = identification;
  }
}
