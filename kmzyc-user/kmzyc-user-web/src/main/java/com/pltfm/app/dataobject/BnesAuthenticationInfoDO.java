package com.pltfm.app.dataobject;



import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-26
 */
public class BnesAuthenticationInfoDO implements Serializable {

  private static final long serialVersionUID = 137482549626835954L;

  /**
   * column BNES_AUTHENTICATION_INFO.AUTHENTICATION_ID
   */
  private Integer authenticationId;

  /**
   * column BNES_AUTHENTICATION_INFO.ACCOUNT_ID
   */
  private Integer accountId;

  /**
   * column BNES_AUTHENTICATION_INFO.AUTHENTICATION_MODE
   */
  private Short authenticationMode;

  /**
   * column BNES_AUTHENTICATION_INFO.CERTIFICATE_TYPE
   */
  private Short certificateType;

  /**
   * column BNES_AUTHENTICATION_INFO.CERTIFICATE_NUMBER
   */
  private String certificateNumber;

  /**
   * column BNES_AUTHENTICATION_INFO.CERTIFICATE_PICTURE
   */
  private String certificatePicture;

  /**
   * column BNES_AUTHENTICATION_INFO.EXAMINATION_VALUE
   */
  private Short examinationValue;

  /**
   * column BNES_AUTHENTICATION_INFO.EXAMINATION_PERSON
   */
  private Integer examinationPerson;

  /**
   * column BNES_AUTHENTICATION_INFO.EXAMINATION_DATE
   */
  private Date examinationDate;

  /**
   * column BNES_AUTHENTICATION_INFO.CREATE_DATE
   */
  private Date createDate;

  /**
   * column BNES_AUTHENTICATION_INFO.CREATED_ID
   */
  private Integer createdId;

  /**
   * column BNES_AUTHENTICATION_INFO.MODIFY_DATE
   */
  private Date modifyDate;

  /**
   * column BNES_AUTHENTICATION_INFO.MODIFIE_ID
   */
  private Integer modifieId;

  public BnesAuthenticationInfoDO() {
    super();
  }

  public BnesAuthenticationInfoDO(Integer authenticationId, Integer accountId,
      Short authenticationMode, Short certificateType, String certificateNumber,
      String certificatePicture, Short examinationValue, Integer examinationPerson,
      Date examinationDate, Date createDate, Integer createdId, Date modifyDate,
      Integer modifieId) {
    this.authenticationId = authenticationId;
    this.accountId = accountId;
    this.authenticationMode = authenticationMode;
    this.certificateType = certificateType;
    this.certificateNumber = certificateNumber;
    this.certificatePicture = certificatePicture;
    this.examinationValue = examinationValue;
    this.examinationPerson = examinationPerson;
    this.examinationDate = examinationDate;
    this.createDate = createDate;
    this.createdId = createdId;
    this.modifyDate = modifyDate;
    this.modifieId = modifieId;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.AUTHENTICATION_ID
   */
  public Integer getAuthenticationId() {
    return authenticationId;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.AUTHENTICATION_ID
   * 
   * @param authenticationId
   */
  public void setAuthenticationId(Integer authenticationId) {
    this.authenticationId = authenticationId;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.ACCOUNT_ID
   */
  public Integer getAccountId() {
    return accountId;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.ACCOUNT_ID
   * 
   * @param accountId
   */
  public void setAccountId(Integer accountId) {
    this.accountId = accountId;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.AUTHENTICATION_MODE
   */
  public Short getAuthenticationMode() {
    return authenticationMode;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.AUTHENTICATION_MODE
   * 
   * @param authenticationMode
   */
  public void setAuthenticationMode(Short authenticationMode) {
    this.authenticationMode = authenticationMode;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.CERTIFICATE_TYPE
   */
  public Short getCertificateType() {
    return certificateType;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.CERTIFICATE_TYPE
   * 
   * @param certificateType
   */
  public void setCertificateType(Short certificateType) {
    this.certificateType = certificateType;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.CERTIFICATE_NUMBER
   */
  public String getCertificateNumber() {
    return certificateNumber;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.CERTIFICATE_NUMBER
   * 
   * @param certificateNumber
   */
  public void setCertificateNumber(String certificateNumber) {
    this.certificateNumber = certificateNumber;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.CERTIFICATE_PICTURE
   */
  public String getCertificatePicture() {
    return certificatePicture;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.CERTIFICATE_PICTURE
   * 
   * @param certificatePicture
   */
  public void setCertificatePicture(String certificatePicture) {
    this.certificatePicture = certificatePicture;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.EXAMINATION_VALUE
   */
  public Short getExaminationValue() {
    return examinationValue;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.EXAMINATION_VALUE
   * 
   * @param examinationValue
   */
  public void setExaminationValue(Short examinationValue) {
    this.examinationValue = examinationValue;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.EXAMINATION_PERSON
   */
  public Integer getExaminationPerson() {
    return examinationPerson;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.EXAMINATION_PERSON
   * 
   * @param examinationPerson
   */
  public void setExaminationPerson(Integer examinationPerson) {
    this.examinationPerson = examinationPerson;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.EXAMINATION_DATE
   */
  public Date getExaminationDate() {
    return examinationDate;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.EXAMINATION_DATE
   * 
   * @param examinationDate
   */
  public void setExaminationDate(Date examinationDate) {
    this.examinationDate = examinationDate;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.CREATE_DATE
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.CREATE_DATE
   * 
   * @param createDate
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.CREATED_ID
   */
  public Integer getCreatedId() {
    return createdId;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.CREATED_ID
   * 
   * @param createdId
   */
  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.MODIFY_DATE
   */
  public Date getModifyDate() {
    return modifyDate;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.MODIFY_DATE
   * 
   * @param modifyDate
   */
  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  /**
   * getter for Column BNES_AUTHENTICATION_INFO.MODIFIE_ID
   */
  public Integer getModifieId() {
    return modifieId;
  }

  /**
   * setter for Column BNES_AUTHENTICATION_INFO.MODIFIE_ID
   * 
   * @param modifieId
   */
  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

}
