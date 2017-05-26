package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 过敏症状信息.
 * 
 *
 * 
 */
public class AllergySymptomsInfo implements Serializable {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.N_ALLERGY_SYMPTOMS_ID
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private Integer nAllergySymptomsId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private Integer nHealthYgenericId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.ALLERGY_TYPE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private String allergyType;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.EFFECT_DEGREE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private String effectDegree;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private Date dAllergicSymptomsTime;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_DISAPPEAR_
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private Date dAllergicSymptomsDisappear;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private Date dCreateDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private Integer nCreated;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private Date dModifyDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ALLERGY_SYMPTOMS_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  private Integer nModified;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.N_ALLERGY_SYMPTOMS_ID
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.N_ALLERGY_SYMPTOMS_ID
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public Integer getnAllergySymptomsId() {
    return nAllergySymptomsId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.N_ALLERGY_SYMPTOMS_ID
   *
   * @param nAllergySymptomsId the value for ALLERGY_SYMPTOMS_INFO.N_ALLERGY_SYMPTOMS_ID
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setnAllergySymptomsId(Integer nAllergySymptomsId) {
    this.nAllergySymptomsId = nAllergySymptomsId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.N_HEALTH_YGENERIC_ID
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public Integer getnHealthYgenericId() {
    return nHealthYgenericId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.N_HEALTH_YGENERIC_ID
   *
   * @param nHealthYgenericId the value for ALLERGY_SYMPTOMS_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setnHealthYgenericId(Integer nHealthYgenericId) {
    this.nHealthYgenericId = nHealthYgenericId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.ALLERGY_TYPE
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.ALLERGY_TYPE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public String getAllergyType() {
    return allergyType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.ALLERGY_TYPE
   *
   * @param allergyType the value for ALLERGY_SYMPTOMS_INFO.ALLERGY_TYPE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setAllergyType(String allergyType) {
    this.allergyType = allergyType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.EFFECT_DEGREE
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.EFFECT_DEGREE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public String getEffectDegree() {
    return effectDegree;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.EFFECT_DEGREE
   *
   * @param effectDegree the value for ALLERGY_SYMPTOMS_INFO.EFFECT_DEGREE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setEffectDegree(String effectDegree) {
    this.effectDegree = effectDegree;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_TIME
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public Date getdAllergicSymptomsTime() {
    return dAllergicSymptomsTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_TIME
   *
   * @param dAllergicSymptomsTime the value for ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setdAllergicSymptomsTime(Date dAllergicSymptomsTime) {
    this.dAllergicSymptomsTime = dAllergicSymptomsTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_DISAPPEAR_
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_DISAPPEAR_
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public Date getdAllergicSymptomsDisappear() {
    return dAllergicSymptomsDisappear;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_DISAPPEAR_
   *
   * @param dAllergicSymptomsDisappear the value for
   *        ALLERGY_SYMPTOMS_INFO.D_ALLERGIC_SYMPTOMS_DISAPPEAR_
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setdAllergicSymptomsDisappear(Date dAllergicSymptomsDisappear) {
    this.dAllergicSymptomsDisappear = dAllergicSymptomsDisappear;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.D_CREATE_DATE
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public Date getdCreateDate() {
    return dCreateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.D_CREATE_DATE
   *
   * @param dCreateDate the value for ALLERGY_SYMPTOMS_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.N_CREATED
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public Integer getnCreated() {
    return nCreated;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.N_CREATED
   *
   * @param nCreated the value for ALLERGY_SYMPTOMS_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setnCreated(Integer nCreated) {
    this.nCreated = nCreated;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.D_MODIFY_DATE
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public Date getdModifyDate() {
    return dModifyDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.D_MODIFY_DATE
   *
   * @param dModifyDate the value for ALLERGY_SYMPTOMS_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ALLERGY_SYMPTOMS_INFO.N_MODIFIED
   *
   * @return the value of ALLERGY_SYMPTOMS_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public Integer getnModified() {
    return nModified;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ALLERGY_SYMPTOMS_INFO.N_MODIFIED
   *
   * @param nModified the value for ALLERGY_SYMPTOMS_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 09:41:36 CST 2013
   */
  public void setnModified(Integer nModified) {
    this.nModified = nModified;
  }
}
