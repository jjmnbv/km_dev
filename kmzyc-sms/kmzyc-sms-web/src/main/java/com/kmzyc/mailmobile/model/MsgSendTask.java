package com.kmzyc.mailmobile.model;

import java.io.Serializable;
import java.util.Date;

public class MsgSendTask implements Serializable {

  /**
	 * 
	 */
  private static final long serialVersionUID = -7852627513556757290L;

  private Long id;

  private String receiver;

  private String content;

  private Integer type;

  private Date sendTime;

  private Integer isSuccess;

  private Integer sendCount;

  private Date createTime;

  private Date updateTime;

  private Integer msgType;

  private String sendReturn;

  private String sendSuccess;

  private Date returnTime;
  // 短信通道
  private String msgChannel;
  // 1: 验证|2:通知|3:营销
  private Integer channelType;
  // 发送返回 CODe
  private String sendCode;
  // 重发ID 记录对应关系
  private Long repeatSendId;
  // 发送时存在黑名单内的电话号码
  private String blackList;


  public String getBlackList() {
    return blackList;
  }

  public void setBlackList(String blackList) {
    this.blackList = blackList;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public Date getSendTime() {
    return sendTime;
  }

  public void setSendTime(Date sendTime) {
    this.sendTime = sendTime;
  }

  public Integer getIsSuccess() {
    return isSuccess;
  }

  public void setIsSuccess(Integer isSuccess) {
    this.isSuccess = isSuccess;
  }

  public Integer getSendCount() {
    return sendCount;
  }

  public void setSendCount(Integer sendCount) {
    this.sendCount = sendCount;
  }

  public Date getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }

  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  public Integer getMsgType() {
    return msgType;
  }

  public void setMsgType(Integer msgType) {
    this.msgType = msgType;
  }

  public String getSendReturn() {
    return sendReturn;
  }

  public void setSendReturn(String sendReturn) {
    this.sendReturn = sendReturn;
  }


  public String getSendSuccess() {
    return sendSuccess;
  }

  public void setSendSuccess(String sendSuccess) {
    this.sendSuccess = sendSuccess;
  }

  public Date getReturnTime() {
    return returnTime;
  }

  public void setReturnTime(Date returnTime) {
    this.returnTime = returnTime;
  }

  public String getMsgChannel() {
    return msgChannel;
  }

  public void setMsgChannel(String msgChannel) {
    this.msgChannel = msgChannel;
  }

  public Integer getChannelType() {
    return channelType;
  }

  public void setChannelType(Integer channelType) {
    this.channelType = channelType;
  }

  public String getSendCode() {
    return sendCode;
  }

  public void setSendCode(String sendCode) {
    this.sendCode = sendCode;
  }

  public Long getRepeatSendId() {
    return repeatSendId;
  }

  public void setRepeatSendId(Long repeatSendId) {
    this.repeatSendId = repeatSendId;
  }



}
