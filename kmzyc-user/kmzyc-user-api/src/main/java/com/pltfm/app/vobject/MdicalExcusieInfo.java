package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 医务信息类
 * 
 * @author gwl
 * @since 2013-07-08
 */
public class MdicalExcusieInfo implements Serializable {
  private int n_medicalMattersExclusive_id;// 医务专属ID
  private int n_personal_id;
  private String name;// 真实姓名
  private String theCity;// 所在城市
  private String theHospital;// 所属医院
  private String hospitalLevel;// 医院等级
  private String theDepartment;// 所属科室
  private String professionName;// 职业称号
  private String professionalExprtise;// 专业专长
  private int n_certificateType;// 证书类型
  private String certificateNumber;// 证书编号\
  private String auditingPhone;// 审核电话
  private Date d_createDate;// 创建日期;
  private int n_created;// 创建人
  private Date d_modifyDate;// 修改日期;
  private int n_modified;// 修改人
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;

  public Integer getSkip() {
    return skip;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }

  public int getN_medicalMattersExclusive_id() {
    return n_medicalMattersExclusive_id;
  }

  public void setN_medicalMattersExclusive_id(int nMedicalMattersExclusiveId) {
    n_medicalMattersExclusive_id = nMedicalMattersExclusiveId;
  }

  public int getN_personal_id() {
    return n_personal_id;
  }

  public void setN_personal_id(int nPersonalId) {
    n_personal_id = nPersonalId;
  }

  public Date getD_createDate() {
    return d_createDate;
  }

  public void setD_createDate(Date dCreateDate) {
    d_createDate = dCreateDate;
  }

  public int getN_created() {
    return n_created;
  }

  public void setN_created(int nCreated) {
    n_created = nCreated;
  }

  public Date getD_modifyDate() {
    return d_modifyDate;
  }

  public void setD_modifyDate(Date dModifyDate) {
    d_modifyDate = dModifyDate;
  }

  public int getN_modified() {
    return n_modified;
  }

  public void setN_modified(int nModified) {
    n_modified = nModified;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTheCity() {
    return theCity;
  }

  public void setTheCity(String theCity) {
    this.theCity = theCity;
  }

  public String getTheHospital() {
    return theHospital;
  }

  public void setTheHospital(String theHospital) {
    this.theHospital = theHospital;
  }

  public String getHospitalLevel() {
    return hospitalLevel;
  }

  public void setHospitalLevel(String hospitalLevel) {
    this.hospitalLevel = hospitalLevel;
  }

  public String getProfessionName() {
    return professionName;
  }

  public void setProfessionName(String professionName) {
    this.professionName = professionName;
  }

  public String getProfessionalExprtise() {
    return professionalExprtise;
  }

  public void setProfessionalExprtise(String professionalExprtise) {
    this.professionalExprtise = professionalExprtise;
  }

  public String getTheDepartment() {
    return theDepartment;
  }

  public void setTheDepartment(String theDepartment) {
    this.theDepartment = theDepartment;
  }

  public int getN_certificateType() {
    return n_certificateType;
  }

  public void setN_certificateType(int nCertificateType) {
    n_certificateType = nCertificateType;
  }

  public String getCertificateNumber() {
    return certificateNumber;
  }

  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  public String getAuditingPhone() {
    return auditingPhone;
  }

  public void setAuditingPhone(String auditingPhone) {
    this.auditingPhone = auditingPhone;
  }

}
