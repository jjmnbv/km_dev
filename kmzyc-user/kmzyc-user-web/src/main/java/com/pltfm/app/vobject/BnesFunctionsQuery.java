package com.pltfm.app.vobject;



import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-17
 */
public class BnesFunctionsQuery implements Serializable {

  private static final long serialVersionUID = 137404452377619140L;

  /**
   * column BNES_FUNCTIONS.BINES_FUNCTION_ID
   */
  private Integer binesFunctionId;

  /**
   * column BNES_FUNCTIONS.FUNCTION_NAME
   */
  private String functionName;

  /**
   * column BNES_FUNCTIONS.URL
   */
  private String url;

  /**
   * column BNES_FUNCTIONS.DESCRIPTION
   */
  private String description;

  /**
   * column BNES_FUNCTIONS.FUNCTION_PARENT_ID
   */
  private Integer functionParentId;

  /**
   * column BNES_FUNCTIONS.IS_PARENT
   */
  private Short isParent;

  /**
   * column BNES_FUNCTIONS.CREATE_DATE
   */
  private Date createDate;

  /**
   * column BNES_FUNCTIONS.CREATED_ID
   */
  private Integer createdId;

  /**
   * column BNES_FUNCTIONS.MODIFY_DATE
   */
  private Date modifyDate;

  /**
   * column BNES_FUNCTIONS.MODIFIE_ID
   */
  private Integer modifieId;

  public BnesFunctionsQuery() {
    super();
  }

  public BnesFunctionsQuery(Integer binesFunctionId, String functionName, String url,
      String description, Integer functionParentId, Short isParent, Date createDate,
      Integer createdId, Date modifyDate, Integer modifieId) {
    this.binesFunctionId = binesFunctionId;
    this.functionName = functionName;
    this.url = url;
    this.description = description;
    this.functionParentId = functionParentId;
    this.isParent = isParent;
    this.createDate = createDate;
    this.createdId = createdId;
    this.modifyDate = modifyDate;
    this.modifieId = modifieId;
  }

  /**
   * getter for Column BNES_FUNCTIONS.BINES_FUNCTION_ID
   */
  public Integer getBinesFunctionId() {
    return binesFunctionId;
  }

  /**
   * setter for Column BNES_FUNCTIONS.BINES_FUNCTION_ID
   * 
   * @param binesFunctionId
   */
  public void setBinesFunctionId(Integer binesFunctionId) {
    this.binesFunctionId = binesFunctionId;
  }

  /**
   * getter for Column BNES_FUNCTIONS.FUNCTION_NAME
   */
  public String getFunctionName() {
    return functionName;
  }

  /**
   * setter for Column BNES_FUNCTIONS.FUNCTION_NAME
   * 
   * @param functionName
   */
  public void setFunctionName(String functionName) {
    this.functionName = functionName;
  }

  /**
   * getter for Column BNES_FUNCTIONS.URL
   */
  public String getUrl() {
    return url;
  }

  /**
   * setter for Column BNES_FUNCTIONS.URL
   * 
   * @param url
   */
  public void setUrl(String url) {
    this.url = url;
  }

  /**
   * getter for Column BNES_FUNCTIONS.DESCRIPTION
   */
  public String getDescription() {
    return description;
  }

  /**
   * setter for Column BNES_FUNCTIONS.DESCRIPTION
   * 
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * getter for Column BNES_FUNCTIONS.FUNCTION_PARENT_ID
   */
  public Integer getFunctionParentId() {
    return functionParentId;
  }

  /**
   * setter for Column BNES_FUNCTIONS.FUNCTION_PARENT_ID
   * 
   * @param functionParentId
   */
  public void setFunctionParentId(Integer functionParentId) {
    this.functionParentId = functionParentId;
  }

  /**
   * getter for Column BNES_FUNCTIONS.IS_PARENT
   */
  public Short getIsParent() {
    return isParent;
  }

  /**
   * setter for Column BNES_FUNCTIONS.IS_PARENT
   * 
   * @param isParent
   */
  public void setIsParent(Short isParent) {
    this.isParent = isParent;
  }

  /**
   * getter for Column BNES_FUNCTIONS.CREATE_DATE
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * setter for Column BNES_FUNCTIONS.CREATE_DATE
   * 
   * @param createDate
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * getter for Column BNES_FUNCTIONS.CREATED_ID
   */
  public Integer getCreatedId() {
    return createdId;
  }

  /**
   * setter for Column BNES_FUNCTIONS.CREATED_ID
   * 
   * @param createdId
   */
  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  /**
   * getter for Column BNES_FUNCTIONS.MODIFY_DATE
   */
  public Date getModifyDate() {
    return modifyDate;
  }

  /**
   * setter for Column BNES_FUNCTIONS.MODIFY_DATE
   * 
   * @param modifyDate
   */
  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  /**
   * getter for Column BNES_FUNCTIONS.MODIFIE_ID
   */
  public Integer getModifieId() {
    return modifieId;
  }

  /**
   * setter for Column BNES_FUNCTIONS.MODIFIE_ID
   * 
   * @param modifieId
   */
  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

}
