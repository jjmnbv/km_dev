package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 接种疫苗信息.
 * 
 *
 * 
 */
public class VaccinationInfo implements Serializable {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.N_VACCINATION_ID
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private Integer nVaccinationId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private Integer nHealthYgenericId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.VACCINE_NAME
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private String vaccineName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.D_VACCINATE_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private Date dVaccinateTime;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.D_ACTIVE_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private Date dActiveTime;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.FUNCTION
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private String function;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private Date dCreateDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private Integer nCreated;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private Date dModifyDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * VACCINATION_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  private Integer nModified;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.N_VACCINATION_ID
   *
   * @return the value of VACCINATION_INFO.N_VACCINATION_ID
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public Integer getnVaccinationId() {
    return nVaccinationId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.N_VACCINATION_ID
   *
   * @param nVaccinationId the value for VACCINATION_INFO.N_VACCINATION_ID
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setnVaccinationId(Integer nVaccinationId) {
    this.nVaccinationId = nVaccinationId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.N_HEALTH_YGENERIC_ID
   *
   * @return the value of VACCINATION_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public Integer getnHealthYgenericId() {
    return nHealthYgenericId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.N_HEALTH_YGENERIC_ID
   *
   * @param nHealthYgenericId the value for VACCINATION_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setnHealthYgenericId(Integer nHealthYgenericId) {
    this.nHealthYgenericId = nHealthYgenericId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.VACCINE_NAME
   *
   * @return the value of VACCINATION_INFO.VACCINE_NAME
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public String getVaccineName() {
    return vaccineName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.VACCINE_NAME
   *
   * @param vaccineName the value for VACCINATION_INFO.VACCINE_NAME
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setVaccineName(String vaccineName) {
    this.vaccineName = vaccineName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.D_VACCINATE_TIME
   *
   * @return the value of VACCINATION_INFO.D_VACCINATE_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public Date getdVaccinateTime() {
    return dVaccinateTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.D_VACCINATE_TIME
   *
   * @param dVaccinateTime the value for VACCINATION_INFO.D_VACCINATE_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setdVaccinateTime(Date dVaccinateTime) {
    this.dVaccinateTime = dVaccinateTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.D_ACTIVE_TIME
   *
   * @return the value of VACCINATION_INFO.D_ACTIVE_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public Date getdActiveTime() {
    return dActiveTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.D_ACTIVE_TIME
   *
   * @param dActiveTime the value for VACCINATION_INFO.D_ACTIVE_TIME
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setdActiveTime(Date dActiveTime) {
    this.dActiveTime = dActiveTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.FUNCTION
   *
   * @return the value of VACCINATION_INFO.FUNCTION
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public String getFunction() {
    return function;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.FUNCTION
   *
   * @param function the value for VACCINATION_INFO.FUNCTION
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setFunction(String function) {
    this.function = function;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.D_CREATE_DATE
   *
   * @return the value of VACCINATION_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public Date getdCreateDate() {
    return dCreateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.D_CREATE_DATE
   *
   * @param dCreateDate the value for VACCINATION_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.N_CREATED
   *
   * @return the value of VACCINATION_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public Integer getnCreated() {
    return nCreated;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.N_CREATED
   *
   * @param nCreated the value for VACCINATION_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setnCreated(Integer nCreated) {
    this.nCreated = nCreated;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.D_MODIFY_DATE
   *
   * @return the value of VACCINATION_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public Date getdModifyDate() {
    return dModifyDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.D_MODIFY_DATE
   *
   * @param dModifyDate the value for VACCINATION_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column VACCINATION_INFO.N_MODIFIED
   *
   * @return the value of VACCINATION_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public Integer getnModified() {
    return nModified;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column VACCINATION_INFO.N_MODIFIED
   *
   * @param nModified the value for VACCINATION_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 09:03:44 CST 2013
   */
  public void setnModified(Integer nModified) {
    this.nModified = nModified;
  }
}
