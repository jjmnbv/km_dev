package com.pltfm.app.vobject;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2014-09-22
 */
public class PurchaseListDO implements Serializable {

  private static final long serialVersionUID = 141137054352395097L;

  /**
   * column PURCHASE_LIST.PURCHASE_ID
   */
  private BigDecimal purchaseId;

  /**
   * column PURCHASE_LIST.PURCHASE_NAME
   */
  private String purchaseName;

  /**
   * column PURCHASE_LIST.PURCHASE_TOTAL_PRICE
   */
  private BigDecimal purchaseTotalPrice;

  /**
   * column PURCHASE_LIST.CREATE_TIME
   */
  private Date createTime;

  /**
   * column PURCHASE_LIST.LOGIN_ID
   */
  private BigDecimal loginId;

  /**
   * column PURCHASE_LIST.UPDATE_TIME
   */
  private Date updateTime;

  /**
   * column PURCHASE_LIST.PRES_NAME
   */
  private String presName;

  /**
   * column PURCHASE_LIST.PRES_ULR
   */
  private String presUlr;

  /**
   * column PURCHASE_LIST.HOSPITAL
   */
  private String hospital;

  /**
   * column PURCHASE_LIST.DOCTOR
   */
  private String doctor;

  /**
   * column PURCHASE_LIST.PATIENT_NAME
   */
  private String patientName;

  /**
   * column PURCHASE_LIST.CLINICAL_DIAGNOSIS
   */
  private String clinicalDiagnosis;

  /**
   * column PURCHASE_LIST.PRES_STATUS
   */
  private Short presStatus;

  /**
   * column PURCHASE_LIST.REVIEW_DESCRIPTION
   */
  private String reviewDescription;

  /**
   * column PURCHASE_LIST.TYPE
   */
  private Short type;

  private String loginAccount;

  private String mobile;
  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;
  public BigDecimal dProductPrice;

  public BigDecimal dProductCount;



  public BigDecimal getdProductPrice() {
    return dProductPrice;
  }

  public void setdProductPrice(BigDecimal dProductPrice) {
    this.dProductPrice = dProductPrice;
  }

  public BigDecimal getdProductCount() {
    return dProductCount;
  }

  public void setdProductCount(BigDecimal dProductCount) {
    this.dProductCount = dProductCount;
  }

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

  public String getLoginAccount() {
    return loginAccount;
  }

  public void setLoginAccount(String loginAccount) {
    this.loginAccount = loginAccount;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }


  public PurchaseListDO() {
    super();
  }

  public PurchaseListDO(BigDecimal purchaseId, String purchaseName, BigDecimal purchaseTotalPrice,
      Date createTime, BigDecimal loginId, Date updateTime, String presName, String presUlr,
      String hospital, String doctor, String patientName, String clinicalDiagnosis,
      Short presStatus, String reviewDescription, Short type, String loginAccount, String mobile) {
    this.purchaseId = purchaseId;
    this.purchaseName = purchaseName;
    this.purchaseTotalPrice = purchaseTotalPrice;
    this.createTime = createTime;
    this.loginId = loginId;
    this.updateTime = updateTime;
    this.presName = presName;
    this.presUlr = presUlr;
    this.hospital = hospital;
    this.doctor = doctor;
    this.patientName = patientName;
    this.clinicalDiagnosis = clinicalDiagnosis;
    this.presStatus = presStatus;
    this.reviewDescription = reviewDescription;
    this.type = type;
    this.loginAccount = loginAccount;
    this.mobile = mobile;
  }

  /**
   * getter for Column PURCHASE_LIST.PURCHASE_ID
   */
  public BigDecimal getPurchaseId() {
    return purchaseId;
  }

  /**
   * setter for Column PURCHASE_LIST.PURCHASE_ID
   * 
   * @param purchaseId
   */
  public void setPurchaseId(BigDecimal purchaseId) {
    this.purchaseId = purchaseId;
  }

  /**
   * getter for Column PURCHASE_LIST.PURCHASE_NAME
   */
  public String getPurchaseName() {
    return purchaseName;
  }

  /**
   * setter for Column PURCHASE_LIST.PURCHASE_NAME
   * 
   * @param purchaseName
   */
  public void setPurchaseName(String purchaseName) {
    this.purchaseName = purchaseName;
  }

  /**
   * getter for Column PURCHASE_LIST.PURCHASE_TOTAL_PRICE
   */
  public BigDecimal getPurchaseTotalPrice() {
    return purchaseTotalPrice;
  }

  /**
   * setter for Column PURCHASE_LIST.PURCHASE_TOTAL_PRICE
   * 
   * @param purchaseTotalPrice
   */
  public void setPurchaseTotalPrice(BigDecimal purchaseTotalPrice) {
    this.purchaseTotalPrice = purchaseTotalPrice;
  }

  /**
   * getter for Column PURCHASE_LIST.CREATE_TIME
   */
  public Date getCreateTime() {
    return createTime;
  }

  /**
   * setter for Column PURCHASE_LIST.CREATE_TIME
   * 
   * @param createTime
   */
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  /**
   * getter for Column PURCHASE_LIST.LOGIN_ID
   */
  public BigDecimal getLoginId() {
    return loginId;
  }

  /**
   * setter for Column PURCHASE_LIST.LOGIN_ID
   * 
   * @param loginId
   */
  public void setLoginId(BigDecimal loginId) {
    this.loginId = loginId;
  }

  /**
   * getter for Column PURCHASE_LIST.UPDATE_TIME
   */
  public Date getUpdateTime() {
    return updateTime;
  }

  /**
   * setter for Column PURCHASE_LIST.UPDATE_TIME
   * 
   * @param updateTime
   */
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  /**
   * getter for Column PURCHASE_LIST.PRES_NAME
   */
  public String getPresName() {
    return presName;
  }

  /**
   * setter for Column PURCHASE_LIST.PRES_NAME
   * 
   * @param presName
   */
  public void setPresName(String presName) {
    this.presName = presName;
  }

  /**
   * getter for Column PURCHASE_LIST.PRES_ULR
   */
  public String getPresUlr() {
    return presUlr;
  }

  /**
   * setter for Column PURCHASE_LIST.PRES_ULR
   * 
   * @param presUlr
   */
  public void setPresUlr(String presUlr) {
    this.presUlr = presUlr;
  }

  /**
   * getter for Column PURCHASE_LIST.HOSPITAL
   */
  public String getHospital() {
    return hospital;
  }

  /**
   * setter for Column PURCHASE_LIST.HOSPITAL
   * 
   * @param hospital
   */
  public void setHospital(String hospital) {
    this.hospital = hospital;
  }

  /**
   * getter for Column PURCHASE_LIST.DOCTOR
   */
  public String getDoctor() {
    return doctor;
  }

  /**
   * setter for Column PURCHASE_LIST.DOCTOR
   * 
   * @param doctor
   */
  public void setDoctor(String doctor) {
    this.doctor = doctor;
  }

  /**
   * getter for Column PURCHASE_LIST.PATIENT_NAME
   */
  public String getPatientName() {
    return patientName;
  }

  /**
   * setter for Column PURCHASE_LIST.PATIENT_NAME
   * 
   * @param patientName
   */
  public void setPatientName(String patientName) {
    this.patientName = patientName;
  }

  /**
   * getter for Column PURCHASE_LIST.CLINICAL_DIAGNOSIS
   */
  public String getClinicalDiagnosis() {
    return clinicalDiagnosis;
  }

  /**
   * setter for Column PURCHASE_LIST.CLINICAL_DIAGNOSIS
   * 
   * @param clinicalDiagnosis
   */
  public void setClinicalDiagnosis(String clinicalDiagnosis) {
    this.clinicalDiagnosis = clinicalDiagnosis;
  }

  /**
   * getter for Column PURCHASE_LIST.PRES_STATUS
   */
  public Short getPresStatus() {
    return presStatus;
  }

  /**
   * setter for Column PURCHASE_LIST.PRES_STATUS
   * 
   * @param presStatus
   */
  public void setPresStatus(Short presStatus) {
    this.presStatus = presStatus;
  }

  /**
   * getter for Column PURCHASE_LIST.REVIEW_DESCRIPTION
   */
  public String getReviewDescription() {
    return reviewDescription;
  }

  /**
   * setter for Column PURCHASE_LIST.REVIEW_DESCRIPTION
   * 
   * @param reviewDescription
   */
  public void setReviewDescription(String reviewDescription) {
    this.reviewDescription = reviewDescription;
  }

  /**
   * getter for Column PURCHASE_LIST.TYPE
   */
  public Short getType() {
    return type;
  }

  /**
   * setter for Column PURCHASE_LIST.TYPE
   * 
   * @param type
   */
  public void setType(Short type) {
    this.type = type;
  }

}
