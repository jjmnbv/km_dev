package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;

public class OrderHandle implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_HANDLE.HANDLE_NO
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  private Long handleNo;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_HANDLE.ORDER_CODE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  private String orderCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_HANDLE.HANDLE_STATE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  private Short handleState;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_HANDLE.HANDLE_ACCOUNT
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  private String handleAccount;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.ORDER_HANDLE.HANDLE_TIME
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  private Date handleTime;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_HANDLE.HANDLE_NO
   * 
   * @return the value of KMORDER.ORDER_HANDLE.HANDLE_NO
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public Long getHandleNo() {
    return handleNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_HANDLE.HANDLE_NO
   * 
   * @param handleNo the value for KMORDER.ORDER_HANDLE.HANDLE_NO
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public void setHandleNo(Long handleNo) {
    this.handleNo = handleNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_HANDLE.ORDER_CODE
   * 
   * @return the value of KMORDER.ORDER_HANDLE.ORDER_CODE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public String getOrderCode() {
    return orderCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_HANDLE.ORDER_CODE
   * 
   * @param orderCode the value for KMORDER.ORDER_HANDLE.ORDER_CODE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_HANDLE.HANDLE_STATE
   * 
   * @return the value of KMORDER.ORDER_HANDLE.HANDLE_STATE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public Short getHandleState() {
    return handleState;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_HANDLE.HANDLE_STATE
   * 
   * @param handleState the value for KMORDER.ORDER_HANDLE.HANDLE_STATE
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public void setHandleState(Short handleState) {
    this.handleState = handleState;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_HANDLE.HANDLE_ACCOUNT
   * 
   * @return the value of KMORDER.ORDER_HANDLE.HANDLE_ACCOUNT
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public String getHandleAccount() {
    return handleAccount;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_HANDLE.HANDLE_ACCOUNT
   * 
   * @param handleAccount the value for KMORDER.ORDER_HANDLE.HANDLE_ACCOUNT
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public void setHandleAccount(String handleAccount) {
    this.handleAccount = handleAccount;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.ORDER_HANDLE.HANDLE_TIME
   * 
   * @return the value of KMORDER.ORDER_HANDLE.HANDLE_TIME
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public Date getHandleTime() {
    return handleTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.ORDER_HANDLE.HANDLE_TIME
   * 
   * @param handleTime the value for KMORDER.ORDER_HANDLE.HANDLE_TIME
   * 
   * @ibatorgenerated Mon Feb 17 13:43:09 CST 2014
   */
  public void setHandleTime(Date handleTime) {
    this.handleTime = handleTime;
  }
}
