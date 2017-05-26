package com.kmzyc.express.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.kmzyc.express.util.constant.ExpressSubConstants;

public class ExpressSubscription implements Serializable {
  private static final long serialVersionUID = 1L;
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.sub_id
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private BigDecimal subId;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.order_code
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private String orderCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.channel
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private String channel;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.logistics_name
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private String logisticsName;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.logistics_no
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private String logisticsNo;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.logistics_code
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private String logisticsCode;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.sub_date
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private Date subDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.track_status
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private Integer trackStatus;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.latest_push_status
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private Integer latestPushStatus;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.latest_push_date
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private Date latestPushDate;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * KMORDER.EXPRESS_SUBSCRIPTION.express_status
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  private Integer expressStatus;

  private Integer uselessFlag;

  private Integer abortCount;

  private String fromCity;

  private String toCity;

  /**
   * 订阅对应的所有物流跟踪信息
   */
  List<ExpressTrack> expressTrackList;


  public List<ExpressTrack> getExpressTrackList() {
    return expressTrackList;
  }

  public void setExpressTrackList(List<ExpressTrack> expressTrackList) {
    this.expressTrackList = expressTrackList;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.sub_id
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.sub_id
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public BigDecimal getSubId() {
    return subId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.sub_id
   * 
   * @param subId the value for KMORDER.EXPRESS_SUBSCRIPTION.sub_id
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setSubId(BigDecimal subId) {
    this.subId = subId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.order_code
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.order_code
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public String getOrderCode() {
    return orderCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.order_code
   * 
   * @param orderCode the value for KMORDER.EXPRESS_SUBSCRIPTION.order_code
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setOrderCode(String orderCode) {
    this.orderCode = orderCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.channel
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.channel
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public String getChannel() {
    return channel;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.channel
   * 
   * @param channel the value for KMORDER.EXPRESS_SUBSCRIPTION.channel
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setChannel(String channel) {
    this.channel = channel;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.logistics_name
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.logistics_name
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public String getLogisticsName() {
    return logisticsName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.logistics_name
   * 
   * @param logisticsName the value for KMORDER.EXPRESS_SUBSCRIPTION.logistics_name
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setLogisticsName(String logisticsName) {
    this.logisticsName = logisticsName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.logistics_no
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.logistics_no
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public String getLogisticsNo() {
    return logisticsNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.logistics_no
   * 
   * @param logisticsNo the value for KMORDER.EXPRESS_SUBSCRIPTION.logistics_no
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setLogisticsNo(String logisticsNo) {
    this.logisticsNo = logisticsNo;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.logistics_code
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.logistics_code
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public String getLogisticsCode() {
    return logisticsCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.logistics_code
   * 
   * @param logisticsCode the value for KMORDER.EXPRESS_SUBSCRIPTION.logistics_code
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setLogisticsCode(String logisticsCode) {
    this.logisticsCode = logisticsCode;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.sub_date
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.sub_date
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public Date getSubDate() {
    return subDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.sub_date
   * 
   * @param subDate the value for KMORDER.EXPRESS_SUBSCRIPTION.sub_date
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setSubDate(Date subDate) {
    this.subDate = subDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.track_status
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.track_status
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public Integer getTrackStatus() {
    return trackStatus;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.track_status
   * 
   * @param trackStatus the value for KMORDER.EXPRESS_SUBSCRIPTION.track_status
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setTrackStatus(Integer trackStatus) {
    this.trackStatus = trackStatus;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.latest_push_status
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.latest_push_status
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public Integer getLatestPushStatus() {
    return latestPushStatus;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.latest_push_status
   * 
   * @param latestPushStatus the value for KMORDER.EXPRESS_SUBSCRIPTION.latest_push_status
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setLatestPushStatus(Integer latestPushStatus) {
    this.latestPushStatus = latestPushStatus;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.latest_push_date
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.latest_push_date
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public Date getLatestPushDate() {
    return latestPushDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.latest_push_date
   * 
   * @param latestPushDate the value for KMORDER.EXPRESS_SUBSCRIPTION.latest_push_date
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setLatestPushDate(Date latestPushDate) {
    this.latestPushDate = latestPushDate;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column KMORDER.EXPRESS_SUBSCRIPTION.express_status
   * 
   * @return the value of KMORDER.EXPRESS_SUBSCRIPTION.express_status
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public Integer getExpressStatus() {
    return expressStatus;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column KMORDER.EXPRESS_SUBSCRIPTION.express_status
   * 
   * @param expressStatus the value for KMORDER.EXPRESS_SUBSCRIPTION.express_status
   * 
   * @ibatorgenerated Mon Dec 07 16:53:58 CST 2015
   */
  public void setExpressStatus(Integer expressStatus) {
    this.expressStatus = expressStatus;
  }

  public String getTrackStatusName() {
    return ExpressSubConstants.TrackStatus.getByKey(this.getTrackStatus()).getValue();
  }

  public String getLatestPushStatusName() {
    return ExpressSubConstants.PushStatus.getValueByKey(this.getLatestPushStatus());
  }

  public String getExpressStatusName() {
    return ExpressSubConstants.ExpressStatus.getValueByKey(this.getExpressStatus());
  }

  public Integer getUselessFlag() {
    return uselessFlag;
  }

  public void setUselessFlag(Integer uselessFlag) {
    this.uselessFlag = uselessFlag;
  }

  public Integer getAbortCount() {
    return abortCount;
  }

  public void setAbortCount(Integer abortCount) {
    this.abortCount = abortCount;
  }

  public String getFromCity() {
    return fromCity;
  }

  public void setFromCity(String fromCity) {
    this.fromCity = fromCity;
  }

  public String getToCity() {
    return toCity;
  }

  public void setToCity(String toCity) {
    this.toCity = toCity;
  }
}