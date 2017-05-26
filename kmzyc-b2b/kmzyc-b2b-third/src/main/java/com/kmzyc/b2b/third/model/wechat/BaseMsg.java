package com.kmzyc.b2b.third.model.wechat;

/**
 * 消息的父类(即可做消息响应,也可做消息接收)
 * 
 * @author Administrator
 * 
 */
public class BaseMsg {

  /**
   * 1.当公众平台通过程序向用户发送消息时,这个字段代表的是接收方账号(openId) 2.当用户向公众平台发送消息时,这个字段代表的公众平台开发者的微信号
   */
  private String ToUserName;

  /**
   * 1.当公众平台通过程序向用户发送消息时,这个字段代表的是公众平台开发者的微信号 2.当用户向公众平台发送消息时,这个字段代表的用户的微信号
   */
  private String FromUserName;

  /*
   * 发送时间
   */
  private String CreateTime;

  /*
   * 消息类型(纯文本/图文/图片/地理位置)
   */
  private String MsgType;

  public String getToUserName() {
    return ToUserName;
  }

  public void setToUserName(String toUserName) {
    ToUserName = toUserName;
  }

  public String getFromUserName() {
    return FromUserName;
  }

  public void setFromUserName(String fromUserName) {
    FromUserName = fromUserName;
  }

  public String getCreateTime() {
    return CreateTime;
  }

  public void setCreateTime(String createTime) {
    CreateTime = createTime;
  }

  public String getMsgType() {
    return MsgType;
  }

  public void setMsgType(String msgType) {
    MsgType = msgType;
  }

}
