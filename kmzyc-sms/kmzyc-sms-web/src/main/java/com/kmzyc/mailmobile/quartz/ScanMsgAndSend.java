package com.kmzyc.mailmobile.quartz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.kmzyc.mailmobile.model.MsgSendTask;
import com.kmzyc.mailmobile.service.HandleMsgSendTaskService;
import com.kmzyc.mailmobile.service.MsgSendTaskService;
import com.kmzyc.mailmobile.util.SpringBeanUtil;

import redis.clients.jedis.JedisCluster;

public class ScanMsgAndSend {
    private Logger logger = Logger.getLogger(ScanMsgAndSend.class);
    @Resource(name="msgSendTaskService")
    private MsgSendTaskService msgSendTaskService;
    @Resource(name="handleMsgSendTaskService")
    private HandleMsgSendTaskService handleMsgSendTaskService;
    @Resource(name="jedisCluster")
    private JedisCluster jedisCluster;


    /**
     * 立即发送
     */
    public void scanMsgAndSendTimely() {

        String key = "ScanMsgAndSend.scanMsgAndSendTimely";// 锁的key有类名.方法名组成
        try {
            if (jedisCluster.setnx(key, "running") == 1) {// key不存在,获得锁
                jedisCluster.expire(key, 60);// 锁定60秒
                final List<MsgSendTask> msgSendTaskList = msgSendTaskService.getMsgSendTaskListTimely();
                MsgSendTask msg = null;
                logger.info("调用度短信《即时》发送定时器 条数为：" + msgSendTaskList.size());
                for (int i = 0; i < msgSendTaskList.size(); i++) {
                    msg = msgSendTaskList.get(i);
                    logger.info("《及时》发送:" + "第" + (i + 1) + "条，" + "Id为:" + msg.getId() + ",手机号为："
                            + msg.getReceiver() + ",已重发次数：" + msg.getSendCount() + ",短信类型为："
                            + msg.getMsgType());
                    handleMsgSendTaskService.handleMsgSendTask(msg);
                }
            }
        } catch (Exception e) {
            logger.error("查询即时发送短信发送任务出错" + e.getMessage(), e);
        }
    }



    /**
     * 定时发送
     */
    public void scanMsgAndSendTiming() {

        List<MsgSendTask> msgSendTaskList ;

        String key = "ScanMsgAndSend.scanMsgAndSendTiming";// 锁的key有类名.方法名组成
        try {
            if (jedisCluster.setnx(key, "running") == 1) {// key不存在,获得锁
                jedisCluster.expire(key, 60);// 锁定60秒
                msgSendTaskList = msgSendTaskService.getMsgSendTaskListTiming();
                MsgSendTask msg ;
                logger.info("调用度短信《定时》发送定时器 条数为：" + msgSendTaskList.size());
                for (int i = 0; i < msgSendTaskList.size(); i++) {
                    msg =  msgSendTaskList.get(i);
                    logger.info("《定时》发送:" + "第" + (i + 1) + "条，" + "Id为:" + msg.getId() + ",手机号为："
                            + msg.getReceiver() + ",已重发次数：" + msg.getSendCount() + ",短信类型为："
                            + msg.getMsgType());
                    handleMsgSendTaskService.handleMsgSendTask(msg);
                }
            }
        } catch (SQLException e) {
            logger.error("查询定时发送短信发送任务出错" + e.getMessage(), e);
        } finally {
            jedisCluster.del(key);// 释放锁
        }
    }

}
