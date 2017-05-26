package com.kmzyc.mailmobile.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.kmzyc.mailmobile.dao.EmMsgChannelDAO;
import com.kmzyc.mailmobile.model.EmMsgChannel;
import com.kmzyc.mailmobile.model.MsgSendTask;
import com.kmzyc.mailmobile.service.SendMsgService;
import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.mframework.mobile.MobileInterface;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;

/**
 * 
 * @author 发送通道区分·
 */
@Service("sendMsgService")
public class SendMsgServiceImpl implements SendMsgService {
    private Logger logger = Logger.getLogger(SendMsgServiceImpl.class);

    private static final String SMS_MOBILE_YS = "sms_mobile_yz_";

    @Resource(name = "jxtMobileImpl")
    private MobileInterface jxtMobileInterface;

    @Resource(name = "etxMobileImpl")
    private MobileInterface etxMobileInterface;

    @Resource(name = "jcxgMobileImpl")
    private MobileInterface jcxgMobileInterface;

    @Resource(name = "mdkjMobileImpl")
    private MobileInterface mdkjMobileInterface;

    @Resource(name = "hbMobileImpl")
    private MobileInterface hbMobileInterface;

    @Resource(name = "yxMobileImpl")
    private MobileInterface yxMobileInterface;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Resource(name = "emMsgChannelDAO")
    private EmMsgChannelDAO emMsgChannelDAO;

    @Resource(name = "csymMobileImpl")
    private MobileInterface csymMobileInterface;

    @Override
    public Map<String, Object> sendMsg(MsgSendTask msgSendTask) {

        //校验并选择发送通道
        Map<String, Object> map = this.chooseUsableChannel(msgSendTask);
        if(null == map || !(boolean)map.get("success")){
            return map;
        }

        try {

            switch ((int)map.get("useChannel")) {
                case 0:
                    MessagelLogger.info("悦信：发送类型为【" + msgSendTask.getMsgType() + "】");
                    map = yxMobileInterface.sendMsg(msgSendTask.getReceiver().split(","), msgSendTask.getContent());
                    this.setSendChannelCountInfo(map,"yx",1,msgSendTask);
                    break;
                case 1:
                    MessagelLogger.info("昊博：发送类型为【" + msgSendTask.getMsgType() + "】");
                    map = hbMobileInterface.sendMsg(msgSendTask.getReceiver().split(","),msgSendTask.getContent());
                    this.setSendChannelCountInfo(map,"hb",1,msgSendTask);
                    break;
                case 2:
                    MessagelLogger.info("e信通：发送类型为【" + msgSendTask.getMsgType() + "】");
                    map = etxMobileInterface.sendMsg(msgSendTask.getReceiver().split(","), msgSendTask.getContent());
                    this.setSendChannelCountInfo(map,"ext",1,msgSendTask);
                    break;
                case 3:
                    MessagelLogger.info("君诚信鸽：发送类型为【" + msgSendTask.getMsgType() + "】");
                    map = jcxgMobileInterface.sendMsg(msgSendTask.getReceiver().split(","), msgSendTask.getContent());
                    this.setSendChannelCountInfo(map,"jcxg",1,msgSendTask);
                    break;
                case 4:
                    MessagelLogger.info("吉信通：发送类型为【" + msgSendTask.getMsgType() + "】");
                    map = jxtMobileInterface.sendMsg(msgSendTask.getReceiver().split(","), msgSendTask.getContent());
                    this.setSendChannelCountInfo(map,"jxt",1,msgSendTask);
                    break;
                case 5:
                    MessagelLogger.info("漫道科技：发送类型为【" + msgSendTask.getMsgType() + "】");
                    map = mdkjMobileInterface.sendMsg(msgSendTask.getReceiver().split(","), msgSendTask.getContent());
                    this.setSendChannelCountInfo(map,"mdkj",1,msgSendTask);
                    break;
                case 6:
                    MessagelLogger.info("创世易曼：发送类型为【" + msgSendTask.getMsgType() + "】");
                    map = csymMobileInterface.sendMsg(msgSendTask.getReceiver().split(","), msgSendTask.getContent());
                    this.setSendChannelCountInfo(map,"csym",1,msgSendTask);
                    break;
            }
        } catch (Exception e) {
            logger.error(" sendMsg 短信发送异常：" + e);
            map.put("reType", MessageConstants.EM_MSG_SEND_FAILED);
            map.put("resultCode", "");
            map.put("success", false);
            return map;
        }
        return map;
    }


    /**
     * 选择可使用的通道
     */
    private Map<String, Object> chooseUsableChannel(MsgSendTask msgSendTask) {
        Map<String, Object> map = new HashMap<>();
        try {

            if (msgSendTask.getMsgType().equals(EmailSendType.MSG_VERIFY_CODE.getStatus())
                    || msgSendTask.getMsgType() .equals(EmailSendType.MAIL_RESET_PASSWORD.getStatus())) {
                int yz_sendMaxCount = ConfigurationUtil.getIntValue("yz_sendMaxCount");
                if (null != jedisCluster.get(SMS_MOBILE_YS + msgSendTask.getReceiver())) {
                    String sendCount = jedisCluster .get(SMS_MOBILE_YS + msgSendTask.getReceiver());
                    if (Integer.parseInt(sendCount) >= yz_sendMaxCount) {
                        MessagelLogger.info("号码：" + msgSendTask.getReceiver() + ",验证码今日可用次数达上限;已发:" + sendCount + "----上限:" + yz_sendMaxCount);
                        map.put("reType", MessageConstants.EM_MSG_SEND_MAX);
                        map.put("resultCode", "");
                        map.put("success", false);
                        return map;
                    } else {
                        MessagelLogger.info("号码：" + msgSendTask.getReceiver() + "验证码今日已发:" + sendCount + "----上限:" + yz_sendMaxCount);
                    }
                }
            }

            List<EmMsgChannel> retList = this.checkChannelSendCondition(msgSendTask.getReceiver());

            if(null == retList || retList.size() == 0){
                map.put("reType", MessageConstants.EM_MSG_SEND_MAX);
                map.put("resultCode", "");
                map.put("success", false);
            }else{
                Random rand = new Random();
                map.put("reType", "");
                map.put("resultCode", "");
                map.put("useChannel",this.reInt(retList.get(rand.nextInt(retList.size())).getAlias()));
                map.put("success", true);
            }

        } catch (Exception e) {
            logger.error("chooseUsableChannel 通道筛选异常：" + e);
        }
        return map;
    }

    /**
     * 校验通道是否允许发送，并设置可使用的通道
     *
     * @param mobile 手机号
     */
    private  List<EmMsgChannel> checkChannelSendCondition(String mobile) {

        List<EmMsgChannel> retList = new ArrayList<>();
        try {
            List<EmMsgChannel> list = emMsgChannelDAO.findEmMsgChannelList();

            for (int i = 0; i < list.size(); i++) {

                String msgCount = jedisCluster.get(list.get(i).getAlias() + "_" + mobile);

                if (!StringUtils.isEmpty(msgCount)) {

                    MessagelLogger.info("号码：" + mobile + "在" + list.get(i).getAlias() + "的已发次数：" + msgCount + "通道最大次数：" + list.get(i).getMaxNumber().intValue());
                    if (Integer.parseInt(msgCount) < list.get(i).getMaxNumber().intValue()) {
                        retList.add(list.get(i));
                    }
                } else {
                    retList.add(list.get(i));
                }
            }
        }catch (Exception e){
            logger.error("checkChannelSendCondition 通道筛选异常：" + e);
        }
        return retList;
    }

    @Override
    public int reInt(String type) {
        int re = 6;
        if ("yx".equals(type)) {
            MessagelLogger.info("-------------选用通道为悦信");
            re = 0;
        } else if ("hb".equals(type)) {
            MessagelLogger.info("-------------选用通道为昊博"); //未使用
            re = 1;
        } else if ("ext".equals(type)) {
            MessagelLogger.info("-------------选用通道为E信通"); //未使用
            re = 2;
        } else if ("jcxg".equals(type)) {
            MessagelLogger.info("-------------选用通道为君诚信鸽");
            re = 3;
        }else if("jxt".equals(type)){
            MessagelLogger.info("-------------选用通道为吉信通");//未使用
            re = 4;
        }else if ("mdkj".equals(type)) {
            MessagelLogger.info("-------------选用通道为漫道科技");//未使用
            re = 5;
        } else if ("csym".equals(type)) {
            MessagelLogger.info("-------------选用通道为创世易曼");
            re = 6;
        }
        return re;
    }

    /**
     * 设置发送通道统计信息
     */
    public Map<String, Object> setSendChannelCountInfo(Map<String, Object> map,String channel,int channelType,MsgSendTask msgSendTask){

        String channel_ = channel + "_";

        map.put("channelType", channelType);
        map.put("channel", channel);
        if ((boolean)map.get("success")) {
            String[] mobile = msgSendTask.getReceiver().split(",");
            for (int i = 0; i < mobile.length; i++) {
                if (jedisCluster.get(channel_ + mobile[i]) == null) {
                    jedisCluster.set(channel_ + mobile[i], "1");
                    MessagelLogger.info(channel+"通道，号码为：" + mobile[i] + "第1次发送成功");
                } else {
                    int num = Integer.parseInt(jedisCluster.get(channel_ + mobile[i])) + 1;
                    jedisCluster.set(channel_ + mobile[i], String.valueOf(num));
                    MessagelLogger.info(channel+"通道，号码为：" + mobile[i] + "第" + num + "次发送成功");
                }

                if (msgSendTask.getMsgType() .equals(EmailSendType.MSG_VERIFY_CODE.getStatus())
                        || msgSendTask.getMsgType().equals( EmailSendType.MAIL_RESET_PASSWORD.getStatus())) {// 验证类短信
                    if (jedisCluster.get(SMS_MOBILE_YS + mobile[i]) == null) {
                        jedisCluster.set(SMS_MOBILE_YS + mobile[i], "1");
                        MessagelLogger.info(channel+"通道，验证类信息，号码为：" + mobile[i] + "第1次发送成功");
                    } else {
                        int num = Integer.parseInt( jedisCluster.get(SMS_MOBILE_YS + mobile[i])) + 1;
                        jedisCluster.set(SMS_MOBILE_YS + mobile[i], num + "");
                        MessagelLogger.info( channel+"通道，验证类信息，号码为：" + mobile[i] + "第" + num + "次发送成功");
                    }
                }
            }
        } else {
            map.put("reType", MessageConstants.EM_MSG_SEND_FAILED);
        }

        return map;
    }
}
