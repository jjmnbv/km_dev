package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;

public class OrderCarry implements Serializable {
  /**
	 * 
	 */
  private static final long serialVersionUID = 2649856985476060711L;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_CARRY.HANDLE_ID
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  private Long handleId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_CARRY.CREATE_DATE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  private Date createDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_CARRY.OPERATOR
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  private String operator;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_CARRY.ORDER_SUM
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  private Long orderSum;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_CARRY.NO_ORDER_SUM
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  private Long noOrderSum;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_CARRY.EXCEL_ADDRESS
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  private String excelAddress;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_CARRY.HANDLE_ID
   * 
   * @return the value of KMORDER.ORDER_CARRY.HANDLE_ID
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Long getHandleId() {
    return handleId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_CARRY.HANDLE_ID
   * 
   * @param handleId the value for KMORDER.ORDER_CARRY.HANDLE_ID
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setHandleId(Long handleId) {
    this.handleId = handleId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_CARRY.CREATE_DATE
   * 
   * @return the value of KMORDER.ORDER_CARRY.CREATE_DATE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Date getCreateDate() {
    return createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_CARRY.CREATE_DATE
   * 
   * @param createDate the value for KMORDER.ORDER_CARRY.CREATE_DATE
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_CARRY.OPERATOR
   * 
   * @return the value of KMORDER.ORDER_CARRY.OPERATOR
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public String getOperator() {
    return operator;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_CARRY.OPERATOR
   * 
   * @param operator the value for KMORDER.ORDER_CARRY.OPERATOR
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setOperator(String operator) {
    this.operator = operator;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_CARRY.ORDER_SUM
   * 
   * @return the value of KMORDER.ORDER_CARRY.ORDER_SUM
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Long getOrderSum() {
    return orderSum;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_CARRY.ORDER_SUM
   * 
   * @param orderSum the value for KMORDER.ORDER_CARRY.ORDER_SUM
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setOrderSum(Long orderSum) {
    this.orderSum = orderSum;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_CARRY.NO_ORDER_SUM
   * 
   * @return the value of KMORDER.ORDER_CARRY.NO_ORDER_SUM
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public Long getNoOrderSum() {
    return noOrderSum;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_CARRY.NO_ORDER_SUM
   * 
   * @param noOrderSum the value for KMORDER.ORDER_CARRY.NO_ORDER_SUM
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setNoOrderSum(Long noOrderSum) {
    this.noOrderSum = noOrderSum;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_CARRY.EXCEL_ADDRESS
   * 
   * @return the value of KMORDER.ORDER_CARRY.EXCEL_ADDRESS
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public String getExcelAddress() {
    return excelAddress;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_CARRY.EXCEL_ADDRESS
   * 
   * @param excelAddress the value for KMORDER.ORDER_CARRY.EXCEL_ADDRESS
   * 
   * @ibatorgenerated Mon Jul 29 11:23:58 CST 2013
   */
  public void setExcelAddress(String excelAddress) {
    this.excelAddress = excelAddress;
  }
}