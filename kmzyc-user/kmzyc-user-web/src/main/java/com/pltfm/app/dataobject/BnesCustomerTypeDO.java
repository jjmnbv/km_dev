package com.pltfm.app.dataobject;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据对象
 * 
 * @since 2013-07-17
 */
public class BnesCustomerTypeDO implements Serializable {

  private static final long serialVersionUID = 137404452203478569L;


  /**
   * 客户类别ID column BNES_CUSTOMER_TYPE.CUSTOMER_TYPE_ID
   */
  private Integer customerTypeId;

  /**
   * 类别名称 column BNES_CUSTOMER_TYPE.NAME
   */
  private String name;

  /**
   * 类别描述 column BNES_CUSTOMER_TYPE.DESCRIPTION
   */
  private String description;

  /**
   * 父类别ID column BNES_CUSTOMER_TYPE.PARENT_ID
   */
  private Integer parentId;

  /**
   * 类别创建日期 column BNES_CUSTOMER_TYPE.CREATE_DATE
   */
  private Date createDate;

  /**
   * 创建人 column BNES_CUSTOMER_TYPE.CREATED_ID
   */
  private Integer createdId;

  /**
   * 修改日期 column BNES_CUSTOMER_TYPE.MODIFY_DATE
   */
  private Date modifyDate;

  /**
   * 修改人 column BNES_CUSTOMER_TYPE.MODIFIE_ID
   */
  private Integer modifieId;
  /**
   * 类别级别 column BNES_CUSTOMER_TYPE.CUST_LV
   */
  private String custLv;

  /**
   * 类别排序 column BNES_CUSTOMER_TYPE.SORTNO
   */
  private String sortno;

  /**
   * 是否有效 0--无效；1--有效 column BNES_CUSTOMER_TYPE.IS_ENABLE
   */
  private Integer isEnable;

  public String getCustLv() {
    return custLv;
  }

  public void setCustLv(String custLv) {
    this.custLv = custLv;
  }

  public String getSortno() {
    return sortno;
  }

  public void setSortno(String sortno) {
    this.sortno = sortno;
  }

  public Integer getIsEnable() {
    return isEnable;
  }

  public void setIsEnable(Integer isEnable) {
    this.isEnable = isEnable;
  }

  public BnesCustomerTypeDO() {
    super();
  }

  public BnesCustomerTypeDO(Integer customerTypeId, String name, String description,
      Integer parentId, Date createDate, Integer createdId, Date modifyDate, Integer modifieId,
      String custLv, String sortno, Integer isEnable) {
    this.customerTypeId = customerTypeId;
    this.name = name;
    this.description = description;
    this.parentId = parentId;
    this.createDate = createDate;
    this.createdId = createdId;
    this.modifyDate = modifyDate;
    this.modifieId = modifieId;
    this.custLv = custLv;
    this.sortno = sortno;
    this.isEnable = isEnable;
  }

  /**
   * getter for Column BNES_CUSTOMER_TYPE.CUSTOMER_TYPE_ID
   */
  public Integer getCustomerTypeId() {
    return customerTypeId;
  }

  /**
   * setter for Column BNES_CUSTOMER_TYPE.CUSTOMER_TYPE_ID
   * 
   * @param customerTypeId
   */
  public void setCustomerTypeId(Integer customerTypeId) {
    this.customerTypeId = customerTypeId;
  }

  /**
   * getter for Column BNES_CUSTOMER_TYPE.NAME
   */
  public String getName() {
    return name;
  }

  /**
   * setter for Column BNES_CUSTOMER_TYPE.NAME
   * 
   * @param name
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * getter for Column BNES_CUSTOMER_TYPE.DESCRIPTION
   */
  public String getDescription() {
    return description;
  }

  /**
   * setter for Column BNES_CUSTOMER_TYPE.DESCRIPTION
   * 
   * @param description
   */
  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * getter for Column BNES_CUSTOMER_TYPE.PARENT_ID
   */
  public Integer getParentId() {
    return parentId;
  }

  /**
   * setter for Column BNES_CUSTOMER_TYPE.PARENT_ID
   * 
   * @param parentId
   */
  public void setParentId(Integer parentId) {
    this.parentId = parentId;
  }

  /**
   * getter for Column BNES_CUSTOMER_TYPE.CREATE_DATE
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * setter for Column BNES_CUSTOMER_TYPE.CREATE_DATE
   * 
   * @param createDate
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * getter for Column BNES_CUSTOMER_TYPE.CREATED_ID
   */
  public Integer getCreatedId() {
    return createdId;
  }

  /**
   * setter for Column BNES_CUSTOMER_TYPE.CREATED_ID
   * 
   * @param createdId
   */
  public void setCreatedId(Integer createdId) {
    this.createdId = createdId;
  }

  /**
   * getter for Column BNES_CUSTOMER_TYPE.MODIFY_DATE
   */
  public Date getModifyDate() {
    return modifyDate;
  }

  /**
   * setter for Column BNES_CUSTOMER_TYPE.MODIFY_DATE
   * 
   * @param modifyDate
   */
  public void setModifyDate(Date modifyDate) {
    this.modifyDate = modifyDate;
  }

  /**
   * getter for Column BNES_CUSTOMER_TYPE.MODIFIE_ID
   */
  public Integer getModifieId() {
    return modifieId;
  }

  /**
   * setter for Column BNES_CUSTOMER_TYPE.MODIFIE_ID
   * 
   * @param modifieId
   */
  public void setModifieId(Integer modifieId) {
    this.modifieId = modifieId;
  }

}
