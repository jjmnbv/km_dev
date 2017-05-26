package com.kmzyc.b2b.model;

/**
 * 订单评论
 * 
 * @author luoyi
 * @date 2013/12/18
 */
import java.io.Serializable;
import java.util.Date;

public class OrderAssessInfo implements Serializable {

  private static final long serialVersionUID = 3006022417649710594L;

  // 评价信息编号
  private Long assessInfoId;
  // 订单总评分
  private Long assessMark;
  // 客户账号
  private String guestNum;
  // 评价内容
  private String assessContext;
  // 订单号
  private Long orderId;
  // 生成时间
  private Date createDate;
  // 订单编码(外部展示用)
  private String orderCode;

  public Long getAssessInfoId() {
    return assessInfoId;
  }

  public void setAssessInfoId(Long assessInfoId) {
    this.assessInfoId = assessInfoId;
  }

  public Long getAssessMark() {
    return assessMark;
  }

  public void setAssessMark(Long assessMark) {
    this.assessMark = assessMark;
  }

  public String getGuestNum() {
    return guestNum;
  }

  public void setGuestNum(String guestNum) {
    this.guestNum = guestNum;
  }

  public String getAssessContext() {
    return assessContext;
  }

  public void setAssessContext(String assessContext) {
    this.assessContext = assessContext;
  }

  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(Date createDate) {
    this.createDate = createDate;
  }

  public String getOrderCode() {
    return orderCode;
  }

  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  @Override
  public String toString() {
    return "OrderAssessInfo [assessInfoId=" + assessInfoId + ", assessMark=" + assessMark
        + ", guestNum=" + guestNum + ", assessContext=" + assessContext + ", orderId=" + orderId
        + ", createDate=" + createDate + ", orderCode=" + orderCode + "]";
  }

}
