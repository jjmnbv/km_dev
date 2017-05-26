package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据对象
 * 
 * @since 2014-10-24
 */
public class Crule8ExtentDO implements Serializable {

  private static final long serialVersionUID = 141413900952146319L;

  /**
   * column CRULE8_EXTENT.EXTEND_ID
   */
  private BigDecimal extendId;

  /**
   * column CRULE8_EXTENT.CRULE8_ID
   */
  private BigDecimal crule8Id;

  /**
   * column CRULE8_EXTENT.TYPE
   */
  private Short type;

  /**
   * column CRULE8_EXTENT.RELATE_CODE
   */
  private String relateCode;

  /**
   * 公司名称
   */
  private String corporateName;

  public String getCorporateName() {
    return corporateName;
  }

  public void setCorporateName(String corporateName) {
    this.corporateName = corporateName;
  }

  public Crule8ExtentDO() {
    super();
  }

  public Crule8ExtentDO(BigDecimal extendId, BigDecimal crule8Id, Short type, String relateCode,
      String corporateName) {
    this.extendId = extendId;
    this.crule8Id = crule8Id;
    this.type = type;
    this.relateCode = relateCode;
    this.corporateName = corporateName;
  }

  /**
   * getter for Column CRULE8_EXTENT.EXTEND_ID
   */
  public BigDecimal getExtendId() {
    return extendId;
  }

  /**
   * setter for Column CRULE8_EXTENT.EXTEND_ID
   * 
   * @param extendId
   */
  public void setExtendId(BigDecimal extendId) {
    this.extendId = extendId;
  }

  /**
   * getter for Column CRULE8_EXTENT.CRULE8_ID
   */
  public BigDecimal getCrule8Id() {
    return crule8Id;
  }

  /**
   * setter for Column CRULE8_EXTENT.CRULE8_ID
   * 
   * @param crule8Id
   */
  public void setCrule8Id(BigDecimal crule8Id) {
    this.crule8Id = crule8Id;
  }

  /**
   * getter for Column CRULE8_EXTENT.TYPE
   */
  public Short getType() {
    return type;
  }

  /**
   * setter for Column CRULE8_EXTENT.TYPE
   * 
   * @param type
   */
  public void setType(Short type) {
    this.type = type;
  }

  /**
   * getter for Column CRULE8_EXTENT.RELATE_CODE
   */
  public String getRelateCode() {
    return relateCode;
  }

  /**
   * setter for Column CRULE8_EXTENT.RELATE_CODE
   * 
   * @param relateCode
   */
  public void setRelateCode(String relateCode) {
    this.relateCode = relateCode;
  }

}
