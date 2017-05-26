package com.kmzyc.mailmobile.service.impl;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.km.framework.mq.bean.KmMsg;
import com.kmzyc.framework.constants.EmailSendType;
import com.kmzyc.framework.constants.MessageConstants;
import com.kmzyc.mailmobile.model.MsgSendTask;
import com.kmzyc.mailmobile.mq.ConsumeEmailMsg;
import com.kmzyc.mailmobile.service.HandleMsgSendTaskService;
import com.kmzyc.mailmobile.service.MsgSendTaskService;
import com.kmzyc.mailmobile.service.SendMsgService;
import com.kmzyc.mframework.logger.MessagelLogger;
import com.kmzyc.zkconfig.ConfigurationUtil;

import redis.clients.jedis.JedisCluster;

@Service("handleMsgSendTaskService")
public class HandleMsgSendTaskServiceImpl implements HandleMsgSendTaskService {
    private final static String MOBILE = ConfigurationUtil.getString("sys_mobile");
    private final static String COUNT = ConfigurationUtil.getString("sys_count");

    private Logger logger = Logger.getLogger(HandleMsgSendTaskServiceImpl.class);

    @Resource(name = "msgSendTaskService")
    private MsgSendTaskService msgSendTaskService;
    @Resource(name="jedisCluster")
    private JedisCluster jedisCluster;
    @Resource(name = "sendMsgService")
    private SendMsgService sendMsgService;

    @Override
    public boolean handleMessage(MsgSendTask msgSendTask) {// 修改为先保存再修改
        boolean result = false;
        boolean sendSystem = false;
        String[] mobile = msgSendTask.getReceiver().split(",");

        for (int i = 0; i < mobile.length; i++) {
            Map<String, Object> map = null;
            msgSendTask.setReceiver(mobile[i]);
            // 发送类型为立即发送，发送后保存任务表
            if (MessageConstants.EM_SEND_TYPE_IMM.equals(msgSendTask.getType())) {

                try {

                    if (null != msgSendTask.getReceiver() && !"".equals(msgSendTask.getReceiver())) {
                        msgSendTask.setIsSuccess(MessageConstants.EM_MSG_SEND_READY);// 准备
                        Long msgId = msgSendTaskService.saveMsgSendTask(msgSendTask);
                        msgSendTask.setId(msgId);
                    } else {
                        logger.error("保存短信发送任务出错,空数据");
                        return false;
                    }
                    // 发送
                    map = sendMsgService.sendMsg(msgSendTask);
                    if (null != map.get("success")) {
                        result = (Boolean) map.get("success");
                    }
                    // 保存
                    if (result) {
                        msgSendTask.setIsSuccess(MessageConstants.EM_MSG_SEND_SUCCESS);
                    } else {
                        Integer re = 0;
                        if (null != map.get("reType")) {
                            re = (Integer) map.get("reType");
                        }
                        if (re == MessageConstants.EM_MSG_SEND_BLACK) {
                            msgSendTask.setIsSuccess(MessageConstants.EM_MSG_SEND_BLACK);
                        } else if (re == MessageConstants.EM_MSG_SEND_MAX) {
                            msgSendTask.setIsSuccess(MessageConstants.EM_MSG_SEND_MAX);
                        } else if (re == MessageConstants.EM_MSG_SEND_FAILED) {
                            msgSendTask.setIsSuccess(MessageConstants.EM_MSG_SEND_FAILED);
                        }
                    }
                    msgSendTask.setSendReturn(result ? (String) map.get("resultCode") : null);
                    // 发送次数如果为空，则发送次数置1，如果不为空则加1
                    msgSendTask.setSendCount(1);

                    msgSendTask.setChannelType((Integer) map.get("channelType"));// 发送通道类型
                    msgSendTask.setMsgChannel((String) map.get("channel"));// 发送通道名
                    msgSendTask.setBlackList((String) map.get("blackList"));// 单次发送时 属于黑名单的手机号·
                    msgSendTaskService.updateMsgSendTask(msgSendTask);
                    Date date = new Date();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 时间 格式
                    sdf.format(date);
                    Map<String, String> mapCount = new HashMap<String, String>();
                    mapCount.put("nowDate", sdf.format(date));
                    String CountKey = "sys_count";
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    calendar.set(Calendar.MILLISECOND, 59);
                    Date sdate = calendar.getTime();
                    Date now = new Date();
                    long reCount = jedisCluster.incr(CountKey);
                    logger.info("key:" + CountKey + "，value:" + reCount);
                    if (reCount == 1) {
                        jedisCluster.expire(CountKey, (int) (sdate.getTime() - now.getTime()) / 1000);
                    }
                    MessagelLogger.info("今日发送条数：" + reCount);
                    if (reCount == Integer.parseInt(COUNT)) {
                        MessagelLogger.info("达到设定条数发送通知给系统管理员,条数" + reCount);
                        sendSystem = true;
                    }
                } catch (Exception e) {
                    logger.error("保存短信发送任务出错" + e.getMessage(), e);
                }
            }
        }

        if (sendSystem) {
            try {
                List<String> lists = Arrays.asList(MOBILE.split(","));
                List<String> mobilePhones = new ArrayList(lists);
                KmMsg kmMsg = new KmMsg("6000", true);// 报文编号为1000,参数2为是否回复
                kmMsg.getMsgData().put("kmMsgType", MessageConstants.KMMSG_MOBIL);// (String)1
                kmMsg.getMsgData()
                        .put("smsmailType", EmailSendType.MSG_SYSTEMSENDCOUNT.getStatus());//
                kmMsg.getMsgData().put("systemType", 1);
                kmMsg.getMsgData().put("sendCount", COUNT);
                kmMsg.getMsgData().put("mobilePhones", mobilePhones);// 手机号码
                kmMsg.getMsgData().put("msgSendType", MessageConstants.EM_SEND_TYPE_IMM);// (int)
                                                                                  // 1为立即发送，2为定时发送
                ConsumeEmailMsg.consumeMobileMsg(kmMsg);
            } catch (Exception e) {
                logger.error("系统条数上限短信发送任务出错" + e.getMessage(), e);
            }
        }
        return result;

    }

    @Override
    public boolean handleMsgSendTask(MsgSendTask msgSendTask) {
        boolean result = true;
        Map<String, Object> map = sendMsgService.sendMsg(msgSendTask);
        result = (Boolean) map.get("success");
        msgSendTask.setIsSuccess(result
                ? MessageConstants.EM_MSG_SEND_SUCCESS
                : MessageConstants.EM_MSG_SEND_FAILED);
        msgSendTask.setSendReturn((String) map.get("resultCode"));
        if (msgSendTask.getSendCount() == null) {// 发送次数如果为空，则发送次数置1，如果不为空则加1
            msgSendTask.setSendCount(1);
        } else {
            msgSendTask.setSendCount(msgSendTask.getSendCount() + 1);
        }
        try {
            msgSendTask.setSendReturn("");
            msgSendTaskService.updateMsgSendTask(msgSendTask);
            msgSendTask.setSendCount(1);// 重发一次
            msgSendTask.setChannelType((Integer) map.get("channelType"));// 发送道类型
            msgSendTask.setMsgChannel((String) map.get("channel"));// 发送道名
            msgSendTask.setRepeatSendId(msgSendTask.getId());// //重发的信息ID
            msgSendTask.setBlackList((String) map.get("blackList"));// 单次发送时 属于黑名单的手机号·
            msgSendTaskService.saveMsgSendTask(msgSendTask);
        } catch (SQLException e) {
            logger.error("更新短信发送任务出错" + e.getMessage(), e);
        }
        return result;
    }
}
