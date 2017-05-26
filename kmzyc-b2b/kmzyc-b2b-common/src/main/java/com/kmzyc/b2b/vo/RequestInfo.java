package com.kmzyc.b2b.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 请求明细
 * 
 * @author xiongliguang
 * 
 */
public class RequestInfo implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * 主键
   */
  private Long rid;
  /**
   * 访问的系统
   */
  private String systemCode;
  /**
   * 请求接口
   */
  private String serviceName;
  /**
   * 请求来源
   */
  private String requestSource;
  /**
   * 请求IP
   */
  private String requestIP;
  /**
   * 请求用户ID
   */
  private Long uid;
  /**
   * 请求参数
   */
  private String requestParams;

  /**
   * 请求时间
   */
  private Date requestStartDate;

  /**
   * 请求结束时间
   */
  private Date requestEndDate;
  /**
   * 请求状态
   */
  private String requestStatus;

  public Long getRid() {
    return rid;
  }

  public void setRid(Long rid) {
    this.rid = rid;
  }

  public String getSystemCode() {
    return systemCode;
  }

  public void setSystemCode(String systemCode) {
    this.systemCode = systemCode;
  }

  public String getServiceName() {
    return serviceName;
  }

  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  public String getRequestSource() {
    return requestSource;
  }

  public void setRequestSource(String requestSource) {
    this.requestSource = requestSource;
  }

  public String getRequestIP() {
    return requestIP;
  }

  public void setRequestIP(String requestIP) {
    this.requestIP = requestIP;
  }

  public Long getUid() {
    return uid;
  }

  public void setUid(Long uid) {
    this.uid = uid;
  }

  public String getRequestParams() {
    return requestParams;
  }

  public void setRequestParams(String requestParams) {
    this.requestParams = requestParams;
  }

  public Date getRequestStartDate() {
    return requestStartDate;
  }

  public void setRequestStartDate(Date requestStartDate) {
    this.requestStartDate = requestStartDate;
  }

  public Date getRequestEndDate() {
    return requestEndDate;
  }

  public void setRequestEndDate(Date requestEndDate) {
    this.requestEndDate = requestEndDate;
  }

  public String getRequestStatus() {
    return requestStatus;
  }

  public void setRequestStatus(String requestStatus) {
    this.requestStatus = requestStatus;
  }

  @Override
  public String toString() {
    StringBuilder sb =
        new StringBuilder("[systemCode=").append(systemCode).append("][requestSource=").append(
            requestSource).append("][requestStatus=").append(requestStatus)
            .append("][serviceName=").append(serviceName).append("][").append("][ip=").append(
                requestIP).append("][serviceName=").append(serviceName).append("][uid=")
            .append(uid).append("][requestParams").append(requestParams).append(
                "][requestStartDate=").append(requestStartDate).append("]").append(
                "[requestEndDate=").append(requestEndDate).append("]");
    return sb.toString();
  }
}
