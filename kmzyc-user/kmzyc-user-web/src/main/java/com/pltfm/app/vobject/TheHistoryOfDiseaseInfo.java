package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 疾病史信息.
 *
 *
 * 
 */
public class TheHistoryOfDiseaseInfo implements Serializable {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.N_THE_HISTORY_OF_DISEASE_ID
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private Integer nTheHistoryOfDiseaseId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private Integer nHealthYgenericId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.DISEASE_NAME
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private String diseaseName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.N_WHETHER_CURE
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private Integer nWhetherCure;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.D_SICKEN_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private Date dSickenTime;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.D_CURE_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private Date dCureTime;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.THERAPEUTIC_REGIMEN
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private String therapeuticRegimen;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.CONDITION
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private String condition;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private Date dCreateDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private Integer nCreated;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private Date dModifyDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * THE_HISTORY_OF_DISEASE_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  private Integer nModified;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.N_THE_HISTORY_OF_DISEASE_ID
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.N_THE_HISTORY_OF_DISEASE_ID
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public Integer getnTheHistoryOfDiseaseId() {
    return nTheHistoryOfDiseaseId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.N_THE_HISTORY_OF_DISEASE_ID
   *
   * @param nTheHistoryOfDiseaseId the value for
   *        THE_HISTORY_OF_DISEASE_INFO.N_THE_HISTORY_OF_DISEASE_ID
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setnTheHistoryOfDiseaseId(Integer nTheHistoryOfDiseaseId) {
    this.nTheHistoryOfDiseaseId = nTheHistoryOfDiseaseId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.N_HEALTH_YGENERIC_ID
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public Integer getnHealthYgenericId() {
    return nHealthYgenericId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.N_HEALTH_YGENERIC_ID
   *
   * @param nHealthYgenericId the value for THE_HISTORY_OF_DISEASE_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setnHealthYgenericId(Integer nHealthYgenericId) {
    this.nHealthYgenericId = nHealthYgenericId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.DISEASE_NAME
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.DISEASE_NAME
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public String getDiseaseName() {
    return diseaseName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.DISEASE_NAME
   *
   * @param diseaseName the value for THE_HISTORY_OF_DISEASE_INFO.DISEASE_NAME
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setDiseaseName(String diseaseName) {
    this.diseaseName = diseaseName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.N_WHETHER_CURE
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.N_WHETHER_CURE
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public Integer getnWhetherCure() {
    return nWhetherCure;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.N_WHETHER_CURE
   *
   * @param nWhetherCure the value for THE_HISTORY_OF_DISEASE_INFO.N_WHETHER_CURE
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setnWhetherCure(Integer nWhetherCure) {
    this.nWhetherCure = nWhetherCure;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.D_SICKEN_TIME
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.D_SICKEN_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public Date getdSickenTime() {
    return dSickenTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.D_SICKEN_TIME
   *
   * @param dSickenTime the value for THE_HISTORY_OF_DISEASE_INFO.D_SICKEN_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setdSickenTime(Date dSickenTime) {
    this.dSickenTime = dSickenTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.D_CURE_TIME
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.D_CURE_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public Date getdCureTime() {
    return dCureTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.D_CURE_TIME
   *
   * @param dCureTime the value for THE_HISTORY_OF_DISEASE_INFO.D_CURE_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setdCureTime(Date dCureTime) {
    this.dCureTime = dCureTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.THERAPEUTIC_REGIMEN
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.THERAPEUTIC_REGIMEN
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public String getTherapeuticRegimen() {
    return therapeuticRegimen;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.THERAPEUTIC_REGIMEN
   *
   * @param therapeuticRegimen the value for THE_HISTORY_OF_DISEASE_INFO.THERAPEUTIC_REGIMEN
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setTherapeuticRegimen(String therapeuticRegimen) {
    this.therapeuticRegimen = therapeuticRegimen;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.CONDITION
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.CONDITION
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public String getCondition() {
    return condition;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.CONDITION
   *
   * @param condition the value for THE_HISTORY_OF_DISEASE_INFO.CONDITION
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setCondition(String condition) {
    this.condition = condition;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.D_CREATE_DATE
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public Date getdCreateDate() {
    return dCreateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.D_CREATE_DATE
   *
   * @param dCreateDate the value for THE_HISTORY_OF_DISEASE_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.N_CREATED
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public Integer getnCreated() {
    return nCreated;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.N_CREATED
   *
   * @param nCreated the value for THE_HISTORY_OF_DISEASE_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setnCreated(Integer nCreated) {
    this.nCreated = nCreated;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.D_MODIFY_DATE
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public Date getdModifyDate() {
    return dModifyDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.D_MODIFY_DATE
   *
   * @param dModifyDate the value for THE_HISTORY_OF_DISEASE_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column THE_HISTORY_OF_DISEASE_INFO.N_MODIFIED
   *
   * @return the value of THE_HISTORY_OF_DISEASE_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public Integer getnModified() {
    return nModified;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column THE_HISTORY_OF_DISEASE_INFO.N_MODIFIED
   *
   * @param nModified the value for THE_HISTORY_OF_DISEASE_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 09:32:27 CST 2013
   */
  public void setnModified(Integer nModified) {
    this.nModified = nModified;
  }
}
