package com.pltfm.app.entities;

import java.io.Serializable;
import java.util.Date;

public class OrderCancelReason implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = 9192137355100829180L;
  private Long id;
  private String userAccount;
  private String orderCode;
  private Integer handleStatus;
  // private Integer paidStatus;
  private String reason;
  private String memberNo;
  private Date createDate;
  private Date completeDate;
  private String remark;

  /**
   * 
   * @return订单号
   */
  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  /**
   * 订单取消原因
   * 
   * @return
   */
  public String getReason() {
    return reason;
  }

  public Integer getHandleStatus() {
    return handleStatus;
  }

  public void setHandleStatus(Integer handleStatus) {
    this.handleStatus = handleStatus;
  }

  public void setReason(String reason) {
    this.reason = reason;
  }

  public String getMemberNo() {
    return memberNo;
  }

  public void setMemberNo(String memberNo) {
    this.memberNo = memberNo;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public Date getCompleteDate() {
    return completeDate;
  }

  public void setCompleteDate(Date completeDate) {
    this.completeDate = completeDate;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getUserAccount() {
    return userAccount;
  }

  public void setUserAccount(String userAccount) {
    this.userAccount = userAccount;
  }

}
