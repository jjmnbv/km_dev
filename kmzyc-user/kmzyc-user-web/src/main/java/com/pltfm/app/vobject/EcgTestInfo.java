package com.pltfm.app.vobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 心电测试信息.
 * 
 *
 * 
 */
public class EcgTestInfo implements Serializable {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ECG_TEST_INFO.N_ECG_TEST_ID
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  private Integer nEcgTestId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ECG_TEST_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  private Integer nHealthYgenericId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ECG_TEST_INFO.D_ACQUISITION_TIME
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  private Date dAcquisitionTime;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ECG_TEST_INFO.N_HEART_RATE
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  private Integer nHeartRate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ECG_TEST_INFO.DETECTION_RESULT
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  private String detectionResult;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ECG_TEST_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  private Date dCreateDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ECG_TEST_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  private Integer nCreated;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ECG_TEST_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  private Date dModifyDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * ECG_TEST_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  private Integer nModified;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ECG_TEST_INFO.N_ECG_TEST_ID
   *
   * @return the value of ECG_TEST_INFO.N_ECG_TEST_ID
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public Integer getnEcgTestId() {
    return nEcgTestId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ECG_TEST_INFO.N_ECG_TEST_ID
   *
   * @param nEcgTestId the value for ECG_TEST_INFO.N_ECG_TEST_ID
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public void setnEcgTestId(Integer nEcgTestId) {
    this.nEcgTestId = nEcgTestId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ECG_TEST_INFO.N_HEALTH_YGENERIC_ID
   *
   * @return the value of ECG_TEST_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public Integer getnHealthYgenericId() {
    return nHealthYgenericId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ECG_TEST_INFO.N_HEALTH_YGENERIC_ID
   *
   * @param nHealthYgenericId the value for ECG_TEST_INFO.N_HEALTH_YGENERIC_ID
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public void setnHealthYgenericId(Integer nHealthYgenericId) {
    this.nHealthYgenericId = nHealthYgenericId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ECG_TEST_INFO.D_ACQUISITION_TIME
   *
   * @return the value of ECG_TEST_INFO.D_ACQUISITION_TIME
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public Date getdAcquisitionTime() {
    return dAcquisitionTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ECG_TEST_INFO.D_ACQUISITION_TIME
   *
   * @param dAcquisitionTime the value for ECG_TEST_INFO.D_ACQUISITION_TIME
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public void setdAcquisitionTime(Date dAcquisitionTime) {
    this.dAcquisitionTime = dAcquisitionTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ECG_TEST_INFO.N_HEART_RATE
   *
   * @return the value of ECG_TEST_INFO.N_HEART_RATE
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public Integer getnHeartRate() {
    return nHeartRate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ECG_TEST_INFO.N_HEART_RATE
   *
   * @param nHeartRate the value for ECG_TEST_INFO.N_HEART_RATE
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public void setnHeartRate(Integer nHeartRate) {
    this.nHeartRate = nHeartRate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ECG_TEST_INFO.DETECTION_RESULT
   *
   * @return the value of ECG_TEST_INFO.DETECTION_RESULT
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public String getDetectionResult() {
    return detectionResult;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ECG_TEST_INFO.DETECTION_RESULT
   *
   * @param detectionResult the value for ECG_TEST_INFO.DETECTION_RESULT
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public void setDetectionResult(String detectionResult) {
    this.detectionResult = detectionResult;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ECG_TEST_INFO.D_CREATE_DATE
   *
   * @return the value of ECG_TEST_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public Date getdCreateDate() {
    return dCreateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ECG_TEST_INFO.D_CREATE_DATE
   *
   * @param dCreateDate the value for ECG_TEST_INFO.D_CREATE_DATE
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public void setdCreateDate(Date dCreateDate) {
    this.dCreateDate = dCreateDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ECG_TEST_INFO.N_CREATED
   *
   * @return the value of ECG_TEST_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public Integer getnCreated() {
    return nCreated;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ECG_TEST_INFO.N_CREATED
   *
   * @param nCreated the value for ECG_TEST_INFO.N_CREATED
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public void setnCreated(Integer nCreated) {
    this.nCreated = nCreated;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ECG_TEST_INFO.D_MODIFY_DATE
   *
   * @return the value of ECG_TEST_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public Date getdModifyDate() {
    return dModifyDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ECG_TEST_INFO.D_MODIFY_DATE
   *
   * @param dModifyDate the value for ECG_TEST_INFO.D_MODIFY_DATE
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public void setdModifyDate(Date dModifyDate) {
    this.dModifyDate = dModifyDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column ECG_TEST_INFO.N_MODIFIED
   *
   * @return the value of ECG_TEST_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public Integer getnModified() {
    return nModified;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column ECG_TEST_INFO.N_MODIFIED
   *
   * @param nModified the value for ECG_TEST_INFO.N_MODIFIED
   *
   * @ibatorgenerated Fri Jul 12 08:56:54 CST 2013
   */
  public void setnModified(Integer nModified) {
    this.nModified = nModified;
  }
}