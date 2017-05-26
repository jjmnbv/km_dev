package com.kmzyc.mailmobile.model;

import java.math.BigDecimal;

public class EmMsgChannel {

  private long id;

  private BigDecimal channelSwitch;

  private BigDecimal maxNumber;

  private BigDecimal channelMaxNumber;

  private BigDecimal channelType;

  private String channelName;

  private String alias;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public BigDecimal getChannelSwitch() {
    return channelSwitch;
  }

  public void setChannelSwitch(BigDecimal channelSwitch) {
    this.channelSwitch = channelSwitch;
  }

  public BigDecimal getMaxNumber() {
    return maxNumber;
  }

  public void setMaxNumber(BigDecimal maxNumber) {
    this.maxNumber = maxNumber;
  }

  public BigDecimal getChannelMaxNumber() {
    return channelMaxNumber;
  }

  public void setChannelMaxNumber(BigDecimal channelMaxNumber) {
    this.channelMaxNumber = channelMaxNumber;
  }

  public BigDecimal getChannelType() {
    return channelType;
  }

  public void setChannelType(BigDecimal channelType) {
    this.channelType = channelType;
  }

  public String getChannelName() {
    return channelName;
  }

  public void setChannelName(String channelName) {
    this.channelName = channelName;
  }

  public String getAlias() {
    return alias;
  }

  public void setAlias(String alias) {
    this.alias = alias;
  }


}
