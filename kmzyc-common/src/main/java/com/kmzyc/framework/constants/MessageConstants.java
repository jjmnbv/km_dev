package com.kmzyc.framework.constants;

/**
 * @title 短信系统常量类
 * @describtion 存放系统所有常量
 * @author Administrator
 * @date 2013-09-24
 */
public class MessageConstants {
    /**
     * 发送短信返回状态,0失败，1成功，2未发送，4发送异常
     */
    public static final String SEND_BACK_STATUS_FAIL = "0";

    /**
     * 发送短信返回状态,0失败，1成功，2未发送，4发送异常
     */
    public static final String SEND_BACK_STATUS_SUCCESS = "1";

    /**
     * 发送短信返回状态,0失败，1成功，2未发送，4发送异常
     */
    public static final String SEND_BACK_STATUS_UNSEND = "2";

    /**
     * 发送短信返回状态,0失败，1成功，2未发送，4发送异常
     */
    public static final String SEND_BACK_STATUS_EXCEPTION = "4";
    /**
     * 短信发送结果，-1为准备发送,0为失败，1成功，2为部分成功,3为达上限,4为黑名单
     */
    public static final Integer EM_MSG_SEND_READY = -1;


    /**
     * 短信发送结果，-1为准备发送,0为失败，1成功，2为部分成功,3为达上限,4为黑名单
     */
    public static final Integer EM_MSG_SEND_SUCCESS = 1;

    /**
     * 短信发送结果，-1为准备发送,0为失败，1成功，2为部分成功,3为达上限,4为黑名单
     */
    public static final Integer EM_MSG_SEND_FAILED = 0;

    /**
     * 短信发送结果，-1为准备发送,0为失败，1成功，2为部分成功,3为达上限,4为黑名单
     */
    public static final Integer EM_MSG_SEND_MAX = 3;

    /**
     * 短信发送结果，-1为准备发送,0为失败，1成功，2为部分成功,3为达上限,4为黑名单
     */
    public static final Integer EM_MSG_SEND_BLACK = 4;

    /**
     * 短信邮件发送类型，1为立即发送，2为定时发送
     */
    public static final Integer EM_SEND_TYPE_IMM = 1;

    /**
     * jms消息类型
     */
    public static final String KMMSG_MOBIL = "1";

    /**
     * 系统类型 1-b2b
     */
    public static final Integer KMMSG_SYSTEM_TYPE_B2B = 1;

}
