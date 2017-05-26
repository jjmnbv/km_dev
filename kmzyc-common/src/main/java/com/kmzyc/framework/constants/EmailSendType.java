package com.kmzyc.framework.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件订阅类型枚举
 * 
 * @author luoyi
 * @createDate 2013/10/17
 * 
 */
public enum EmailSendType {
    MAIL_RESET_PASSWORD(30, "密码重置"),
    MSGCOUPONEXPIRING(19, "短信优惠劵到期提醒"),
    MSGPAYSUCCESS(23, "支付成功提醒"),
    MSGDELIVERY(24, "到货提醒"),
    MSGRETURNGOODS(25, "退货提醒"),
    MSGSPREAD(26, "推广信息提醒"),
    MSG_VERIFY_CODE(27, "验证码短信提醒"),
    MSG_REGISTER(28, "注册短信提醒"),
    MSG_ENCHASHMENTSUCCESS(31, "提现成功提醒"),
    MSG_ENCHASHMENTREFUSE(32,"提现拒绝提醒"),
    MSG_SETTLEMENT(35, "商家结算通知"),
    MSG_ACTIVECODE(40, "优惠券激活码"),
    MSG_ENCHASHMENTERROR(41, "提现失败提醒"),
    MSG_SYSTEMSENDCOUNT(999, "发送到达一定条数通知"),
    MSG_VOICE_VERIFY_CODE(42, "验证码语音提醒"),
    MSG_PROMOTION(45,"活动发券量达上限通知"),
    MSG_PRESELL_FINALPAY(46, "预售尾款支付通知"),
    MSG_APTITUDE_APPLY(47,"资质重审通知");


    private int status;
    private String title = null;


    EmailSendType(int status, String title) {
        this.status = status;
        this.title = title;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public static Map<Integer, String> getMsgTypeMap() {
        Map<Integer, String> msgTypeMap = new HashMap<Integer, String>();
        for (EmailSendType est : EmailSendType.values()) {
            msgTypeMap.put(est.getStatus(), est.getTitle());
        }
        return msgTypeMap;
    }

}
