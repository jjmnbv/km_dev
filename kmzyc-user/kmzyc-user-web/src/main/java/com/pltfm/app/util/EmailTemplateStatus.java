package com.pltfm.app.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件模板类型枚举
 * 
 * @author luoyi
 * @createDate 2013/10/17
 * 
 */
public enum EmailTemplateStatus {
  REGISTER("successful-registration", "注册成功"), VALIDATEEMAIL("validate-email", "邮箱验证"), RETURNGOODS(
      "return-goods", "退货提醒"), RESETPASSWORD("reset-password", "重置密码"), PAYSUCCESS("pay-success",
          "付款成功提醒"), UNCOMMENT("send-goods", "发货提醒"), COUPONARRIVED("coupon-arrived",
              "优惠劵到账提醒"), COUPONEXPIRING("coupon-expiring", "优惠劵到期提醒"), MEMBERLEVEL("member-level",
                  "会员级别变动提醒"), BILLORDER("bill-order", "下单成功提醒"), SPREAD("spread", "推广邮件");

  private String title = null;// 模板ID
  private String instruction = null;// 作用说明

  EmailTemplateStatus(String title, String instruction) {
    this.title = title;
    this.instruction = instruction;
  }

  public static Map<String, String> getEmailTemplateStatus() {
    Map<String, String> emailTemplateMap = new HashMap<String, String>();
    for (EmailTemplateStatus ets : EmailTemplateStatus.values()) {
      emailTemplateMap.put(ets.getTitle(), ets.getInstruction());
    }
    return emailTemplateMap;
  }

  public String getInstruction() {
    return instruction;
  }



  public String getTitle() {
    return title;
  }


}
