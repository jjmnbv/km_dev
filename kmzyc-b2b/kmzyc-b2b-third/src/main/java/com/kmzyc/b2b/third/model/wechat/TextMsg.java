package com.kmzyc.b2b.third.model.wechat;

import com.kmzyc.b2b.third.util.ThirdConstant;

/**
 * 推送消息 ---文本消息
 * 
 * @author Administrator
 * 
 */
public class TextMsg extends BaseMsg {

  /**
   * 文本内容
   */
  private String Content;

  public TextMsg() {}

  public TextMsg(String toUser, String fromUser, String createTime, String content) {
    this.setContent(content);
    this.setCreateTime(createTime);
    this.setToUserName(toUser);
    this.setFromUserName(fromUser);
    this.setMsgType(ThirdConstant.WX_MSG_TYPE_TEXT);
  }

  public String getContent() {
    return Content;
  }

  public void setContent(String content) {
    Content = content;
  }

}
