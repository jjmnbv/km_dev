package com.pltfm.app.entities;

import java.io.Serializable;

public class FareTypeWithBLOBs extends FareType implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.FARE_TYPE.DETAIL
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  private String detail;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.FARE_TYPE.CONFIG
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  private String config;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.FARE_TYPE.EXPRESSIONS
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  private String expressions;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.FARE_TYPE.DETAIL
   * 
   * @return the value of KMORDER.FARE_TYPE.DETAIL
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  public String getDetail() {
    return detail;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.FARE_TYPE.DETAIL
   * 
   * @param detail the value for KMORDER.FARE_TYPE.DETAIL
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  public void setDetail(String detail) {
    this.detail = detail;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.FARE_TYPE.CONFIG
   * 
   * @return the value of KMORDER.FARE_TYPE.CONFIG
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  public String getConfig() {
    return config;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.FARE_TYPE.CONFIG
   * 
   * @param config the value for KMORDER.FARE_TYPE.CONFIG
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  public void setConfig(String config) {
    this.config = config;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.FARE_TYPE.EXPRESSIONS
   * 
   * @return the value of KMORDER.FARE_TYPE.EXPRESSIONS
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  public String getExpressions() {
    return expressions;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.FARE_TYPE.EXPRESSIONS
   * 
   * @param expressions the value for KMORDER.FARE_TYPE.EXPRESSIONS
   * 
   * @ibatorgenerated Wed Aug 07 14:18:22 CST 2013
   */
  public void setExpressions(String expressions) {
    this.expressions = expressions;
  }
}
