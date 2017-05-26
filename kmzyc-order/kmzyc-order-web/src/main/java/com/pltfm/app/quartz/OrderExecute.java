package com.pltfm.app.quartz;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.pltfm.app.service.OrderExecuteService;
import com.pltfm.app.util.OrderDictionaryEnum;
import com.pltfm.app.util.SysConstants;

import redis.clients.jedis.JedisCluster;


/**
 * 订单结转的定时任务实现类
 *
 * @author xlg
 */
public class OrderExecute extends QuartzJobBean {

    private static final Logger log = Logger.getLogger(OrderExecute.class);
    private OrderExecuteService orderExecuteService;
    private JedisCluster jedisCluster;

    /**
     * 订单结转
     */
    @SuppressWarnings("unchecked")
    private void executeOrder(String orderChannel) throws Exception {
        try {
            Map mapQuery = new HashMap();
            Date ExecuDate = new Date();
            mapQuery.put("endDate", ExecuDate);// 结转时间
            mapQuery.put("orderChannel", orderChannel);// 订单渠道
            List<Integer> otherStatus = new ArrayList<Integer>();
//      otherStatus.add(OrderDictionaryEnum.Order_Status.Pay_Done.getKey());
            otherStatus.add(OrderDictionaryEnum.Order_Status.Risk_Pass.getKey());//风控通过
            otherStatus.add(OrderDictionaryEnum.Order_Status.Splited_Not_Settle.getKey());
//      otherStatus.add(OrderDictionaryEnum.Order_Status.Merge_Not_Settle.getKey());
            otherStatus.add(OrderDictionaryEnum.Order_Status.Settle_Not_Stock.getKey());
            mapQuery.put("orderStatus", otherStatus);
            mapQuery.put("operator", SysConstants.SYS);// 操作人
            orderExecuteService.OrderExecuteEntrance(orderChannel, mapQuery);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 定时任务执行入口
     */
    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        log.info("订单结转开始！");
        String key = "OrderExecute";
        try {
            Long i = jedisCluster.setnx(key, "1");
            if (i > 0) { // 并发控制
                try {
                    jedisCluster.expire(key, 60 * 60 * 3); // 缓存过期 3小时
                    executeOrder(arg0.getJobDetail().getDescription());
                } finally {
                    jedisCluster.del(key);
                }
            }
        } catch (Exception e) {
            log.error("定时订单结转发生异常", e);
        }
        log.info("订单结转结束！");
    }

    public OrderExecuteService getOrderExecuteService() {
        return orderExecuteService;
    }

    public void setOrderExecuteService(OrderExecuteService orderExecuteService) {
        this.orderExecuteService = orderExecuteService;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
