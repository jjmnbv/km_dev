package com.kmzyc.framework.constants;

/**
 * 邮件模板类型枚举
 * 
 * @author luoyi
 * @createDate 2013/10/17
 * 
 */
public enum EmailTemplateStatus {
  REGISTER("successful-registration", "注册成功"), VALIDATEEMAIL("validate-email", "邮箱验证"), RETURNGOODS(
      "return-goods", "退货申请"), RESETPASSWORD("reset-password", "重置密码"), NONEREGISTER(
      "none-registration", "免注册转会员"), OUTSTOCK("out-stock", "缺货提醒"), PAYSUCCESS("pay-success",
      "付款成功提醒"), UNPAY("un-pay", "未付款成功提醒"), DELIVERY("un-pay", "未付款成功提醒"), UNCOMMENT("send-goods",
      "发货提醒"), COUPONARRIVED("coupon-arrived", "优惠劵到账提醒"), COUPONEXPIRING("coupon-expiring",
      "优惠劵到期提醒"), MEMBERLEVEL("member-level", "会员级别变动提醒"), BILLORDER("bill-order", "下单成功提醒"), BILLORDERNOREGISTER(
      "none-billOrder", "未注册下单成功提醒");

  private String title = null;// 模板ID
  private String instruction = null;// 作用说明

  EmailTemplateStatus(String title, String instruction) {
    this.title = title;
    this.instruction = instruction;
  }

  public String getInstruction() {
    return instruction;
  }

  public void setInstruction(String instruction) {
    this.instruction = instruction;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
