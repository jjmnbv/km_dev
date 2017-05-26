package com.kmzyc.framework.constants;

/**
 * 短信发送内容类型
 * 
 * @author luoyi
 * @createDate 2013/10/17
 * 
 */
public enum MessageTypeEnum {
  ORDERTRAIL(2, "orderTrail", "短信-验证码"),
  RESETPWD(3,"resetPwd", "短信-重置密码"),
  NOREGISTERTOMEMBER(4, "noRegisterToMember", "短信-免注册转会员"),
  ORDERPAYSUCCESS(5, "orderPaySuccess", "短信-订单支付成功"),
  SHIPPEDORDER(7, "shippedOrder", "短信-订单已发货"),
  COUPONEXPIRING(9, "couponExpiring", "短信-优惠券即将到期"),
  RETURNGOOD(11, "returnGood", "短信-退货成功"),
  VALIDMOBILE(14, "validMobile", "短信-手机验证"),
  MOBILEREGIST(16, "mobileRegist", "短信-手机注册"),
  ENCHASHMENTSUCCESS(31, "enchashmentSuccess", "短信-余额取现成功"),
  MSG_ENCHASHMENTREFUSE(32, "enchashmentRefuse","短信-余额取现拒绝"),
  MSG_SETTLEMENT(35, "settlement", "短信-微商结算"),
  MSG_ENCHASHMENTERROR(41, "enchashmentError","短信-余额取现失败"),
  MSG_SYSTEMSENDCOUNT(999, "systemSendCount", "发送到达一定条数通知"),
  MSG_PROMOTION(45, "promotion", "促销活动发券系统通知用"),
  MSG_PRESELL_FINALPAY(46,"presellFinalpay", "预售尾款支付通知"),
  MSG_APTITUDE_APPLY(47, "aptitudeApp", "资质重审通知");
  private int index;
  private String status;
  private String title = null;

  MessageTypeEnum(int index, String status, String title) {
    this.index = index;
    this.status = status;
    this.title = title;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  /**
   * 通过下标获取枚举
   * 
   * @param index
   * @return
   */
  public static MessageTypeEnum getMessageTypeEnumByIndex(int index) {
    MessageTypeEnum result = MessageTypeEnum.ORDERTRAIL;
    for (MessageTypeEnum mt : MessageTypeEnum.values()) {
      if (mt.index == index) {
        result = mt;
        break;
      }
    }
    return result;
  }
}
