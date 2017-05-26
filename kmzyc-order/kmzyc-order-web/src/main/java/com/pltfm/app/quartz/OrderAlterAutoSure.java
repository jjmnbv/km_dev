package com.pltfm.app.quartz;

import com.pltfm.app.service.OrderAlterOperateStatementService;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import redis.clients.jedis.JedisCluster;


/**
 * 退换货自动确认定时任务
 *
 * @author xlg
 */
public class OrderAlterAutoSure extends QuartzJobBean {

    private static final Logger log = Logger.getLogger(OrderAlterAutoSure.class);
    private OrderAlterOperateStatementService orderAlterOperateStatementService;

    @Resource
    private JedisCluster jedisCluster;


    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        List<String> orderAlterCodes = null;
        String key = "OrderAlterAutoSure";

        log.info("定时任务 -- 退换货自动确认开始" + new Date());
        try {
            Long i = jedisCluster.setnx(key, "1");
            if (i > 0) {//并发控制
                try {
                    jedisCluster.expire(key, 60 * 60 * 3); // 缓存过期 3小时
                    try {
                        orderAlterCodes = orderAlterOperateStatementService
                                .queryUnconfirmOrderAlterCode();
                    } catch (Exception e) {
                        log.error("退换货自动确认定时任务查询待确认的退换货编号发生异常", e);
                    }
                    if (null != orderAlterCodes) {
                        for (String orderAlterCode : orderAlterCodes) {
                            try {
                                if (orderAlterOperateStatementService
                                        .OrderAlterAutoSure(orderAlterCode)) {
                                    log.info("退换货" + orderAlterCode + "自动确认发生成功");
                                } else {
                                    log.info("退换货" + orderAlterCode + "自动确认发生失败");
                                }
                            } catch (Exception e) {
                                log.error("退换货" + orderAlterCode + "自动确认发生异常", e);
                            }
                        }
                    }
                } finally {
                    jedisCluster.del(key);
                }
            }
        } catch (Exception e) {
            log.error("退换货自动确认发生异常", e);
        }
        log.info("定时任务 -- 退换货自动确认结束" + new Date());
    }

    public OrderAlterOperateStatementService getOrderAlterOperateStatementService() {
        return orderAlterOperateStatementService;
    }

    public void setOrderAlterOperateStatementService(
            OrderAlterOperateStatementService orderAlterOperateStatementService) {
        this.orderAlterOperateStatementService = orderAlterOperateStatementService;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
