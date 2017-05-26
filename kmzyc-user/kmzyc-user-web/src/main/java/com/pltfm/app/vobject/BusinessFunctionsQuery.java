package com.pltfm.app.vobject;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 数据对象
 * 
 * @since 2013-07-11
 */
public class BusinessFunctionsQuery implements Serializable {

  private static final long serialVersionUID = 137350680788413509L;

  /**
   * column BUSINESS_FUNCTIONS.N_BUSINESS_FUNCTION_ID
   */
  private BigDecimal nBusinessFunctionId;

  /**
   * column BUSINESS_FUNCTIONS.FUNCTION_NAME
   */
  private String functionName;

  /**
   * column BUSINESS_FUNCTIONS.URL
   */
  private String url;

  /**
   * column BUSINESS_FUNCTIONS.DESCRIPTION
   */
  private String description;

  /**
   * column BUSINESS_FUNCTIONS.N_FUNCTION_PARENT_ID
   */
  private BigDecimal nFunctionParentId;

  /**
   * column BUSINESS_FUNCTIONS.N_IS_PARENT
   */
  private Short nIsParent;

  public BusinessFunctionsQuery() {
    super();
  }

  public BusinessFunctionsQuery(BigDecimal nBusinessFunctionId, String functionName, String url,
      String description, BigDecimal nFunctionParentId, Short nIsParent) {
    this.nBusinessFunctionId = nBusinessFunctionId;
    this.functionName = functionName;
    this.url = url;
    this.description = description;
    this.nFunctionParentId = nFunctionParentId;
    this.nIsParent = nIsParent;
  }

  /**
   * getter for Column BUSINESS_FUNCTIONS.N_BUSINESS_FUNCTION_ID
   */
  public BigDecimal getnBusinessFunctionId() {
    return nBusinessFunctionId;
  }

  /**
   * setter for Column BUSINESS_FUNCTIONS.N_BUSINESS_FUNCTION_ID
   * 
   * @param nBusinessFunctionId
   */
  public void setnBusinessFunctionId(BigDecimal nBusinessFunctionId) {
    this.nBusinessFunctionId = nBusinessFunctionId;
  }

  /**
   * getter for Column BUSINESS_FUNCTIONS.FUNCTION_NAME
   */
  public String getFunctionName() {
    return functionName;
  }

  /**
   * setter for Column BUSINESS_FUNCTIONS.FUNCTION_NAME
   * 
   * @param functionName
   */
  public void setFunctionName(String functionName) {
    this.functionName = functionName;
  }

  /**
   * getter for Column BUSINESS_FUNCTIONS.URL
   */
  public String getUrl() {
    return url;
  }

  /**
   * setter for Column BUSINESS_FUNCTIONS.URL
   * 
   * @param url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * getter for Column BUSINESS_FUNCTIONS.DESCRIPTION
   */
  public String getDescription() {
    return description;
  }

  /**
   * setter for Column BUSINESS_FUNCTIONS.DESCRIPTION
   * 
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * getter for Column BUSINESS_FUNCTIONS.N_FUNCTION_PARENT_ID
   */
  public BigDecimal getnFunctionParentId() {
    return nFunctionParentId;
  }

  /**
   * setter for Column BUSINESS_FUNCTIONS.N_FUNCTION_PARENT_ID
   * 
   * @param nFunctionParentId
   */
  public void setnFunctionParentId(BigDecimal nFunctionParentId) {
    this.nFunctionParentId = nFunctionParentId;
  }

  /**
   * getter for Column BUSINESS_FUNCTIONS.N_IS_PARENT
   */
  public Short getnIsParent() {
    return nIsParent;
  }

  /**
   * setter for Column BUSINESS_FUNCTIONS.N_IS_PARENT
   * 
   * @param nIsParent
   */
  public void setnIsParent(Short nIsParent) {
    this.nIsParent = nIsParent;
  }

}
