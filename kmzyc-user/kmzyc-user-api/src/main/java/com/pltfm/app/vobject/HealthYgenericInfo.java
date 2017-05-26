package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 健康信息实体. This field corresponds to the database column HEALTH_YGENERIC_INFO.N_HEALTH_YGENERIC_ID
 *
 * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
 */
public class HealthYgenericInfo implements Serializable {
  /**
   * 健康类属ID. This field corresponds to the database column HEALTH_YGENERIC_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private Integer n_HealthYgenericId;

  /**
   * 登录ID. This field corresponds to the database column HEALTH_YGENERIC_INFO.N_LOGIN_ID
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private Integer n_LoginId;

  /**
   * 婚姻状态. This field corresponds to the database column HEALTH_YGENERIC_INFO.N_MARITAL_STATUS
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private Integer n_MaritalStatus;

  /**
   * 健康状况. This field corresponds to the database column HEALTH_YGENERIC_INFO.HEALTHY_STATE
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private String healthyState;

  /**
   * 饮食习惯. This field corresponds to the database column HEALTH_YGENERIC_INFO.EATING_HABITS
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private String eatingHabits;

  /**
   * 烟酒史. This field corresponds to the database column
   * HEALTH_YGENERIC_INFO.SMOKING_AND_ALCOHOL_HISTORY
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private String smokingAndAlcoholHistory;

  /**
   * 过往病史. This field corresponds to the database column HEALTH_YGENERIC_INFO.PAST_MEDICAL_HISTORY
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private String pastMedicalHistory;

  /**
   * 社保号. This field corresponds to the database column HEALTH_YGENERIC_INFO.SOCIAL_SECURITY_NUMBER
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private String socialSecurityNumber;

  /**
   * 有无生育. This field corresponds to the database column
   * HEALTH_YGENERIC_INFO.N_THERE_IS_NO_FERTILITY
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private Integer n_ThereIsNoFertility;

  /**
   * 血型. This field corresponds to the database column HEALTH_YGENERIC_INFO.BLOOD_TYPE
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private String bloodType;

  /**
   * 健康咨询兴趣. This field corresponds to the database column
   * HEALTH_YGENERIC_INFO.HEALTHY_SEEK_ADVICE_FROM
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private String healthySeekAdviceFrom;

  /**
   * 创建日期. This field corresponds to the database column HEALTH_YGENERIC_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private Date d_CreateDate;

  /**
   * 创建人. This field corresponds to the database column HEALTH_YGENERIC_INFO.N_CREATED
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private Integer n_Created;

  /**
   * 修改日期. This field corresponds to the database column HEALTH_YGENERIC_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private Date d_ModifyDate;

  /**
   * 修改人. This field corresponds to the database column HEALTH_YGENERIC_INFO.N_MODIFIED
   *
   * @ibatorgenerated Thu Jul 11 09:01:21 CST 2013
   */
  private Integer n_Modified;

  public Integer getN_HealthYgenericId() {
    return n_HealthYgenericId;
  }

  public void setN_HealthYgenericId(Integer nHealthYgenericId) {
    n_HealthYgenericId = nHealthYgenericId;
  }

  public Integer getN_LoginId() {
    return n_LoginId;
  }

  public void setN_LoginId(Integer nLoginId) {
    n_LoginId = nLoginId;
  }

  public Integer getN_MaritalStatus() {
    return n_MaritalStatus;
  }

  public void setN_MaritalStatus(Integer nMaritalStatus) {
    n_MaritalStatus = nMaritalStatus;
  }

  public String getHealthyState() {
    return healthyState;
  }

  public void setHealthyState(String healthyState) {
    this.healthyState = healthyState;
  }

  public String getEatingHabits() {
    return eatingHabits;
  }

  public void setEatingHabits(String eatingHabits) {
    this.eatingHabits = eatingHabits;
  }

  public String getSmokingAndAlcoholHistory() {
    return smokingAndAlcoholHistory;
  }

  public void setSmokingAndAlcoholHistory(String smokingAndAlcoholHistory) {
    this.smokingAndAlcoholHistory = smokingAndAlcoholHistory;
  }

  public String getPastMedicalHistory() {
    return pastMedicalHistory;
  }

  public void setPastMedicalHistory(String pastMedicalHistory) {
    this.pastMedicalHistory = pastMedicalHistory;
  }

  public String getSocialSecurityNumber() {
    return socialSecurityNumber;
  }

  public void setSocialSecurityNumber(String socialSecurityNumber) {
    this.socialSecurityNumber = socialSecurityNumber;
  }

  public Integer getN_ThereIsNoFertility() {
    return n_ThereIsNoFertility;
  }

  public void setN_ThereIsNoFertility(Integer nThereIsNoFertility) {
    n_ThereIsNoFertility = nThereIsNoFertility;
  }

  public String getBloodType() {
    return bloodType;
  }

  public void setBloodType(String bloodType) {
    this.bloodType = bloodType;
  }

  public String getHealthySeekAdviceFrom() {
    return healthySeekAdviceFrom;
  }

  public void setHealthySeekAdviceFrom(String healthySeekAdviceFrom) {
    this.healthySeekAdviceFrom = healthySeekAdviceFrom;
  }

  public Date getD_CreateDate() {
    return d_CreateDate;
  }

  public void setD_CreateDate(Date dCreateDate) {
    d_CreateDate = dCreateDate;
  }

  public Integer getN_Created() {
    return n_Created;
  }

  public void setN_Created(Integer nCreated) {
    n_Created = nCreated;
  }

  public Date getD_ModifyDate() {
    return d_ModifyDate;
  }

  public void setD_ModifyDate(Date dModifyDate) {
    d_ModifyDate = dModifyDate;
  }

  public Integer getN_Modified() {
    return n_Modified;
  }

  public void setN_Modified(Integer nModified) {
    n_Modified = nModified;
  }


}
