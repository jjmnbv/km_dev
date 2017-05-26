package com.kmzyc.b2b.third.model.wxCard;

import java.util.Date;

/**
 * 预约信息表 --仅限礼品券
 * 
 * @author Administrator
 * 
 */
public class WxReservation {

  /**
   * 预约流水id
   */
  private Long id;
  /**
   * 预约人姓名
   */
  private String username;

  /**
   * 预约人联系电话
   */
  private String telephone;

  /**
   * 指定的预约康美人生门店
   */
  private String shopId;

  /**
   * 预约领取时间
   */
  private Date reservationTime;

  /**
   * 卡券id,暂时没有建表引用,因为是临时的活动,一次性的,如果要统计,可通过微信公众平台后台去查
   */
  private String cardId;

  /**
   * 微信卡券唯一code码
   */
  private String code;

  /**
   * 该预约信息所对应的康美人生门店
   */
  private WxKmrsShopInfo kmrsShopInfo;

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column WX_RESERVATION.USERNAME
   * 
   * @return the value of WX_RESERVATION.USERNAME
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public String getUsername() {
    return username;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column WX_RESERVATION.USERNAME
   * 
   * @param username the value for WX_RESERVATION.USERNAME
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column WX_RESERVATION.TELEPHONE
   * 
   * @return the value of WX_RESERVATION.TELEPHONE
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public String getTelephone() {
    return telephone;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column WX_RESERVATION.TELEPHONE
   * 
   * @param telephone the value for WX_RESERVATION.TELEPHONE
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public void setTelephone(String telephone) {
    this.telephone = telephone;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column WX_RESERVATION.SHOP_ID
   * 
   * @return the value of WX_RESERVATION.SHOP_ID
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public String getShopId() {
    return shopId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column WX_RESERVATION.SHOP_ID
   * 
   * @param shopId the value for WX_RESERVATION.SHOP_ID
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public void setShopId(String shopId) {
    this.shopId = shopId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column WX_RESERVATION.RESERVATION_TIME
   * 
   * @return the value of WX_RESERVATION.RESERVATION_TIME
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public Date getReservationTime() {
    return reservationTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column WX_RESERVATION.RESERVATION_TIME
   * 
   * @param reservationTime the value for WX_RESERVATION.RESERVATION_TIME
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public void setReservationTime(Date reservationTime) {
    this.reservationTime = reservationTime;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column WX_RESERVATION.CARD_ID
   * 
   * @return the value of WX_RESERVATION.CARD_ID
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public String getCardId() {
    return cardId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column WX_RESERVATION.CARD_ID
   * 
   * @param cardId the value for WX_RESERVATION.CARD_ID
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public void setCardId(String cardId) {
    this.cardId = cardId;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column WX_RESERVATION.CODE
   * 
   * @return the value of WX_RESERVATION.CODE
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public String getCode() {
    return code;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column WX_RESERVATION.CODE
   * 
   * @param code the value for WX_RESERVATION.CODE
   * 
   * @ibatorgenerated Tue Jan 27 16:22:15 CST 2015
   */
  public void setCode(String code) {
    this.code = code;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public WxKmrsShopInfo getKmrsShopInfo() {
    return kmrsShopInfo;
  }

  public void setKmrsShopInfo(WxKmrsShopInfo kmrsShopInfo) {
    this.kmrsShopInfo = kmrsShopInfo;
  }
}
