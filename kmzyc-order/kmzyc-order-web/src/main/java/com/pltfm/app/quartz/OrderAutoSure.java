package com.pltfm.app.quartz;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pltfm.app.service.OrderOperateStatementService;

import javax.annotation.Resource;

import redis.clients.jedis.JedisCluster;


/**
 * 订单自动确认定时任务
 *
 * @author xlg
 */
public class OrderAutoSure extends QuartzJobBean {

    private static final Logger log = Logger.getLogger(OrderAutoSure.class);
    private OrderOperateStatementService orderOperateStatementService;
    @Resource
    private JedisCluster jedisCluster;

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        List<String> orderCodes = null;
        String key = "OrderAutoSure";
        log.info("定时任务--订单自动确认收货开始" + new Date());
        try {
            Long i = jedisCluster.setnx(key, "1");
            if (i > 0) { // 并发控制
                try {
                    jedisCluster.expire(key, 60 * 60 * 3); // 缓存过期 3小时
                    try {
                        orderCodes = orderOperateStatementService.queryUnconfirmReceiptOrderCode();
                    } catch (Exception e) {
                        log.error("订单自动确认定时任务查询待确认收货的订单编号发生异常", e);
                    }
                    if (null != orderCodes) {
                        for (String orderCode : orderCodes) {
                            try {
                                if (orderOperateStatementService.OrderAutoSure(orderCode)) {
                                    log.info("订单" + orderCode + "自动确认收货发生成功");
                                } else {
                                    log.info("订单" + orderCode + "自动确认收货发生失败");
                                }
                            } catch (Exception e) {
                                log.error("订单" + orderCode + "自动确认收货发生异常", e);
                            }
                        }
                    }
                } finally {
                    jedisCluster.del(key);
                }
            }
        } catch (Exception e) {
            log.error("订单自动确认收货发生异常", e);
        }
        log.info("定时任务--订单自动确认收货结束" + new Date());
    }

    public OrderOperateStatementService getOrderOperateStatementService() {
        return orderOperateStatementService;
    }

    public void setOrderOperateStatementService(
            OrderOperateStatementService orderOperateStatementService) {
        this.orderOperateStatementService = orderOperateStatementService;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
