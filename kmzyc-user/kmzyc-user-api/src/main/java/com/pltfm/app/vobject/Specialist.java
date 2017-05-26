package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/***
 * 
 * 专家信息
 * 
 */
public class Specialist implements Serializable {
  /**
   * 客户类别名称
   */
  private String customerName;

  /**
   * 客户级别名称
   */
  private String levelName;
  /**
   * 头像照片
   */
  private String headSculpture;

  /**
   * 个人主键
   */
  private Integer n_PersonalId;

  /**
   * 姓名
   */
  private String name;

  /**
   * 性别
   */
  private String sex;

  /**
   * 年龄
   */
  private Integer n_Age;

  /**
   * 生日
   */
  private Date d_Birthday;

  /**
   * 手机号
   */
  private String mobile;

  /**
   * 电子邮箱
   */
  private String email;

  /**
   * 证件类型
   */
  private Integer n_CertificateType;

  /**
   * 证件号码
   */
  private String certificateNumber;

  /**
   * 所在地
   */
  private String location;

  /**
   * 故乡所在地
   */
  private String hometownLocation;

  /**
   * 教育状况
   */
  private String educationalStatus;

  /**
   * 工作单位
   */
  private String workUnit;


  /**
   * 职业
   */
  private String profession;

  /**
   * 年收入
   */
  private Integer n_AnnualIncome;

  /**
   * 状态
   */
  private Integer n_Status;

  public String getCustomerName() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName = customerName;
  }

  public String getLevelName() {
    return levelName;
  }

  public void setLevelName(String levelName) {
    this.levelName = levelName;
  }

  public String getHeadSculpture() {
    return headSculpture;
  }

  public void setHeadSculpture(String headSculpture) {
    this.headSculpture = headSculpture;
  }

  public Integer getN_PersonalId() {
    return n_PersonalId;
  }

  public void setN_PersonalId(Integer nPersonalId) {
    n_PersonalId = nPersonalId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSex() {
    return sex;
  }

  public void setSex(String sex) {
    this.sex = sex;
  }

  public Integer getN_Age() {
    return n_Age;
  }

  public void setN_Age(Integer nAge) {
    n_Age = nAge;
  }

  public Date getD_Birthday() {
    return d_Birthday;
  }

  public void setD_Birthday(Date dBirthday) {
    d_Birthday = dBirthday;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Integer getN_CertificateType() {
    return n_CertificateType;
  }

  public void setN_CertificateType(Integer nCertificateType) {
    n_CertificateType = nCertificateType;
  }

  public String getCertificateNumber() {
    return certificateNumber;
  }

  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getHometownLocation() {
    return hometownLocation;
  }

  public void setHometownLocation(String hometownLocation) {
    this.hometownLocation = hometownLocation;
  }

  public String getEducationalStatus() {
    return educationalStatus;
  }

  public void setEducationalStatus(String educationalStatus) {
    this.educationalStatus = educationalStatus;
  }

  public String getWorkUnit() {
    return workUnit;
  }

  public void setWorkUnit(String workUnit) {
    this.workUnit = workUnit;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public Integer getN_AnnualIncome() {
    return n_AnnualIncome;
  }

  public void setN_AnnualIncome(Integer nAnnualIncome) {
    n_AnnualIncome = nAnnualIncome;
  }

  public Integer getN_Status() {
    return n_Status;
  }

  public void setN_Status(Integer nStatus) {
    n_Status = nStatus;
  }
}
