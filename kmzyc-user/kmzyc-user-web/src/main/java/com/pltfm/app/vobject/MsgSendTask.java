package com.pltfm.app.vobject;

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

  private Long repeatSendId;
  private String msgChannel;
  private String sendSuccess;



  /**
   * 最小值
   */
  private Integer skip;
  /**
   * 最大值
   */
  private Integer max;


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

  public Integer getSkip() {
    return skip;
  }

  public void setSkip(Integer skip) {
    this.skip = skip;
  }

  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }

  public Long getRepeatSendId() {
    return repeatSendId;
  }

  public void setRepeatSendId(Long repeatSendId) {
    this.repeatSendId = repeatSendId;
  }

  public String getMsgChannel() {
    return msgChannel;
  }

  public void setMsgChannel(String msgChannel) {
    this.msgChannel = msgChannel;
  }

  public String getSendSuccess() {
    return sendSuccess;
  }

  public void setSendSuccess(String sendSuccess) {
    this.sendSuccess = sendSuccess;
  }



}
