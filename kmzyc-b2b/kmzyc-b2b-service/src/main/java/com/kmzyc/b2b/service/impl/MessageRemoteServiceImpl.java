package com.kmzyc.b2b.service.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.servlet.http.HttpSession;
// import com.km.framework.common.util.RemoteTool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.km.framework.mq.Sender;
import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.b2b.dao.EmMsgSendTaskDAO;
import com.kmzyc.b2b.model.EmMsgSendTask;
import com.kmzyc.b2b.service.MessageRemoteService;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.kmzyc.util.Randoms;
import com.kmzyc.zkconfig.ConfigurationUtil;
import com.whalin.MemCached.MemCachedClient;

@Service("messageRemoteService")
public class MessageRemoteServiceImpl implements MessageRemoteService {


    @Resource(name = "emMsgSendTaskDAOImpl")
    private EmMsgSendTaskDAO emMsgSendTaskDAO;

    @Resource(name = "memCachedClient")
    private MemCachedClient memCachedClient;

    private static Logger logger = LoggerFactory.getLogger(MessageRemoteServiceImpl.class);


    @Resource
    private JmsTemplate jmsTemplate;

    @Resource(name = "emailMsg")
    private Destination destination;


    /**
     * 短信发送 订单跟踪\免注册转会员\重置密码\
     * 
     * @param mobilePhone 手机号
     * @param flag (orderTrail,noRegisterToMember,resetPwd)
     * @return
     */
    @Override
    public boolean sendMobileVerifyCode(String mobilePhone, HttpSession session, String flag) {
        boolean result;

        String mobileVerifyCode = Randoms.randomNumber(6);
        String isTest = ConfigurationUtil.getString("isTestMobileVerifyCode");// 是否为测试环境
        if ("true".equals(isTest)) {
            mobileVerifyCode = "666666";
        }

        session.setAttribute("OrderTrailMobileVerifyCode", mobileVerifyCode);

        session.setAttribute("mobilePhone", mobilePhone);

        String dateKey = ConfigurationUtil.getString("b2b_send_message_date_memcached_key_prex")
                + mobilePhone + '_' + flag;
        Date date = (Date) memCachedClient.get(dateKey);
        if (null == date || (new Date().getTime() - date.getTime()) > (Long.parseLong(
                ConfigurationUtil.getString("b2b_common_page_msg_valid_time")) * 60 * 1000)) {
            memCachedClient.set(ConfigurationUtil.getString("b2b_send_message_memcached_key_prex")
                    + mobilePhone + '_' + flag, mobileVerifyCode);
            memCachedClient.set(dateKey, new Date());
            memCachedClient.set("mobilePhone", mobilePhone);
        } else {

            return false;
        }

        logger.info("发送验证码为:" + mobileVerifyCode + ",手机号=" + mobilePhone);
        if (isTest.equals("true")) {

            session.setAttribute("mobileVerifyCodeTime", new Date());
            return true;
        }
        try {
            List<String> mobilePhones = new ArrayList<>();
            mobilePhones.add(mobilePhone);
            KmMsg kmMsg = new KmMsg("6000", true);// 报文编号为1000,参数2为是否回复
            kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);
            kmMsg.getMsgData().put("mobilePhones", mobilePhones);
            kmMsg.getMsgData().put("mobileVerifyCode", mobileVerifyCode);
            kmMsg.getMsgData().put("modeType", flag);
            kmMsg.getMsgData().put("systemType", MessageConstants.KMMSG_SYSTEM_TYPE_B2B);
            kmMsg.getMsgData().put("msgSendType", MessageConstants.EM_SEND_TYPE_IMM);

            kmMsg.getMsgData().put("smsmailType", EmailSendType.MSG_VERIFY_CODE.getStatus());

            kmMsg.setReply(true);
            Sender.send(kmMsg, destination, jmsTemplate);

            session.setAttribute("mobileVerifyCodeTime", new Date());
        } catch (Exception e) {
            logger.error("调用短信模块失败" + e.getMessage(), e);
            return false;
        }
        return true;
    }


    @Override
    public boolean verifyMobileCode(String inputCode, HttpSession session) {
        boolean flag = false;
        String orderTrailMobileVerifyCode =
                (String) session.getAttribute("OrderTrailMobileVerifyCode");
        // 获得发送验证码的发送时间
        Date mobileVerifyCodeTime = (Date) session.getAttribute("mobileVerifyCodeTime");
        Date nowTime = new Date();
        // 精确到分钟
        if (null != mobileVerifyCodeTime) {
            long different = (nowTime.getTime() - mobileVerifyCodeTime.getTime()) / 1000 / 60;
            // 获取到短信验证码超时时间。
            int b2b_common_msg_valid_time =
                    Integer.parseInt(ConfigurationUtil.getString("b2b_common_msg_valid_time"));
            if (different > b2b_common_msg_valid_time) {
                logger.error("验证码发送的时间超过了" + b2b_common_msg_valid_time + "分钟");
                flag = false;
            } else if (orderTrailMobileVerifyCode != null && inputCode != null
                    && inputCode.equals(orderTrailMobileVerifyCode)) {
                flag = true;
            }
        }
        return flag;
    }


    /**
     * @param mobile 手机号
     * @param type 短信发送类型 预留
     * @return 成功 0 次数上限 1 过频繁 2
     */
    @Override
    public int validate(String mobile, String type) {
        if (memCachedClient.get("Msg_" + mobile + "_" + type) == null) {
            logger.info("时间戳缓存为NULL手机号：" + mobile + ",类型：" + type + "添加时间戳缓存");
            memCachedClient.set("Msg_" + mobile + "_" + type, System.currentTimeMillis());
        } else {
            Long newTime = System.currentTimeMillis();
            Long time = (Long) memCachedClient.get("Msg_" + mobile + "_" + type);
            Long time2 = time + Long.parseLong(ConfigurationUtil.getString("msg_max_time"));
            logger.info("时间戳对比：(当前)" + newTime + "(缓存)" + time2);
            if (newTime > time2) {
                logger.info("时间戳缓存不在" + Long.parseLong(ConfigurationUtil.getString("msg_max_time"))
                        + "内手机号：" + mobile + ",类型：" + type + "添加时间戳缓存");
                memCachedClient.set("Msg_" + mobile + "_" + type, System.currentTimeMillis());
            } else {
                logger.info("时间戳缓存还在" + Long.parseLong(ConfigurationUtil.getString("msg_max_time"))
                        + "内，手机号：" + mobile + ",类型：" + type);
                return 2;
            }
        }
        try {
            List<EmMsgSendTask> list = emMsgSendTaskDAO.selectEmMsgSendTaskByMobile(mobile, type);
            int count = Integer.parseInt(ConfigurationUtil.getString("msg_count"));
            if (list != null) {
                if (list.size() >= count) {
                    logger.info("发送次数达到上限,手机号：" + mobile + "类型：" + type + ",已发次数：" + list.size()
                            + ",允许发送次数：" + count);
                    return 1;
                } else {
                    logger.info("发送次数未达到上限,手机号：" + mobile + "类型：" + type + ",已发次数：" + list.size()
                            + ",允许发送次数：" + count);
                    return 0;
                }
            }
        } catch (SQLException e) {
            logger.error("查询发送次数出现异常：" + e.getMessage(), e);
            return 1;
        }
        return 0;
    }



}
