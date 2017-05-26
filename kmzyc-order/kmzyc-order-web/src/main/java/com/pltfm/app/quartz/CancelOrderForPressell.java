/**
 * project : 康美中药城 module : km-order-web package : com.pltfm.app.quartz date: 2016年8月10日下午3:53:37
 * Copyright (c) 2016, KM@km.com All Rights Reserved.
 */
package com.pltfm.app.quartz;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pltfm.app.dao.OrderMainDAO;
import com.pltfm.app.service.OrderOperateStatementService;
import com.pltfm.app.util.SysConstants;

import redis.clients.jedis.JedisCluster;


/**
 * TODO 功能描述<br/>
 * 自动取消预售未付定金订单的定时任务
 *
 * @author KM
 * @date 2016年8月10日 下午3:53:37
 * @see
 */
public class CancelOrderForPressell extends QuartzJobBean {

    private static final Logger log = Logger.getLogger(CancelOrderForPressell.class);

    private OrderMainDAO orderMainDAO;
    @Resource
    private JedisCluster jedisCluster;
    @Resource
    private OrderOperateStatementService orderOperateStatementService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String key = "CancelOrderForPressell";
        List<String> orderCodeList = null;
        try {
            log.info("定时任务 -- 取消未支付定金订单开始" + new Date());
            Long i = jedisCluster.setnx(key, "1");
            if (i > 0) { // 并发控制
                try {
                    jedisCluster.expire(key, 60 * 60 * 3); // 缓存过期 3小时
                    orderCodeList = orderMainDAO.queryCancleOrderForPressell();// 得到要取消的订单号
                    if (null != orderCodeList && orderCodeList.size() > 0) {
                        log.info("取消订单开始:" + new Date() + "一共取消" + orderCodeList.size() + "个订单");
                        int total = 0;
                        for (String orderCode : orderCodeList) {

                            try {
                                orderOperateStatementService.changeOrderStatus(SysConstants.SYS,
                                        orderCode, -1l, null);
                                total++;
                            } catch (Exception e) {
                                log.error("取消订单" + orderCode + "失败", e);
                            }
                            log.info("取消订单" + orderCode + "成功");
                        }
                        log.info("取消订单结束:" + new Date() + "共成功取消" + total + "个订单");
                    }
                } finally {
                    jedisCluster.del(key);
                }

            }
            log.info("定时任务 -- 取消未支付定金订单结束" + new Date());
        } catch (Exception e) {
            log.error("查询skuId总数异常！", e);
        }

    }

    public void setOrderMainDAO(OrderMainDAO orderMainDAO) {
        this.orderMainDAO = orderMainDAO;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

    public void setOrderOperateStatementService(
            OrderOperateStatementService orderOperateStatementService) {
        this.orderOperateStatementService = orderOperateStatementService;
    }
}
