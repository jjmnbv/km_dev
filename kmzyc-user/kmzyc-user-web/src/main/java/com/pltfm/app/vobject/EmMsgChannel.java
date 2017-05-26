package com.pltfm.app.vobject;

import java.math.BigDecimal;

public class EmMsgChannel {
  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * EM_MSG_CHANNEL.ID
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  private long id;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * EM_MSG_CHANNEL.CHANNEL_SWITCH
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  private BigDecimal channelSwitch;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * EM_MSG_CHANNEL.MAX_NUMBER
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  private BigDecimal maxNumber;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * EM_MSG_CHANNEL.CHANNEL_MAX_NUMBER
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  private BigDecimal channelMaxNumber;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * EM_MSG_CHANNEL.CHANNEL_TYPE
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  private BigDecimal channelType;

  /**
   * This field was generated by Apache iBATIS ibator. This field corresponds to the database column
   * EM_MSG_CHANNEL.CHANNEL_NAME
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  private String channelName;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public BigDecimal getChannelSwitch() {
    return channelSwitch;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column EM_MSG_CHANNEL.CHANNEL_SWITCH
   * 
   * @param channelSwitch the value for EM_MSG_CHANNEL.CHANNEL_SWITCH
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  public void setChannelSwitch(BigDecimal channelSwitch) {
    this.channelSwitch = channelSwitch;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column EM_MSG_CHANNEL.MAX_NUMBER
   * 
   * @return the value of EM_MSG_CHANNEL.MAX_NUMBER
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  public BigDecimal getMaxNumber() {
    return maxNumber;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column EM_MSG_CHANNEL.MAX_NUMBER
   * 
   * @param maxNumber the value for EM_MSG_CHANNEL.MAX_NUMBER
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  public void setMaxNumber(BigDecimal maxNumber) {
    this.maxNumber = maxNumber;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column EM_MSG_CHANNEL.CHANNEL_MAX_NUMBER
   * 
   * @return the value of EM_MSG_CHANNEL.CHANNEL_MAX_NUMBER
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  public BigDecimal getChannelMaxNumber() {
    return channelMaxNumber;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column EM_MSG_CHANNEL.CHANNEL_MAX_NUMBER
   * 
   * @param channelMaxNumber the value for EM_MSG_CHANNEL.CHANNEL_MAX_NUMBER
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  public void setChannelMaxNumber(BigDecimal channelMaxNumber) {
    this.channelMaxNumber = channelMaxNumber;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column EM_MSG_CHANNEL.CHANNEL_TYPE
   * 
   * @return the value of EM_MSG_CHANNEL.CHANNEL_TYPE
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  public BigDecimal getChannelType() {
    return channelType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column EM_MSG_CHANNEL.CHANNEL_TYPE
   * 
   * @param channelType the value for EM_MSG_CHANNEL.CHANNEL_TYPE
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  public void setChannelType(BigDecimal channelType) {
    this.channelType = channelType;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method returns the value of the
   * database column EM_MSG_CHANNEL.CHANNEL_NAME
   * 
   * @return the value of EM_MSG_CHANNEL.CHANNEL_NAME
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  public String getChannelName() {
    return channelName;
  }

  /**
   * This method was generated by Apache iBATIS ibator. This method sets the value of the database
   * column EM_MSG_CHANNEL.CHANNEL_NAME
   * 
   * @param channelName the value for EM_MSG_CHANNEL.CHANNEL_NAME
   * 
   * @ibatorgenerated Tue Dec 08 16:24:58 CST 2015
   */
  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }
}