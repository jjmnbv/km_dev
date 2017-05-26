package com.kmzyc.b2b.service;

import javax.servlet.http.HttpSession;

/**
 * 远程接口:短信验证
 * 
 * @author luoyi
 * @createDate 2013/12/05
 */
public interface MessageRemoteService {

    /**
     * 发送短信验证码. 订单跟踪\免注册转会员\重置密码\
     * 
     * @param mobilePhone
     * @param session
     * @param flag
     * @return
     */
    public boolean sendMobileVerifyCode(String mobilePhone, HttpSession session, String flag);

    /**
     * 验证短信验证码
     * 
     * @param inputCode
     * @param session
     * @return
     */
    public boolean verifyMobileCode(String inputCode, HttpSession session);

    /**
     * 发送前验证
     * 
     * @param mobile 手机号
     * @param type 发送类型
     * @return 成功 0 次数上限 1 过频繁 2
     */
    public int validate(String mobile, String type);
}
