package com.pltfm.app.quartz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.kmzyc.b2b.service.OrderPvSyncRemoteService;
import com.pltfm.app.dao.OrderSyncDAO;
import com.pltfm.app.maps.OrderSyncStatus;

import redis.clients.jedis.JedisCluster;


/**
 * 同步时代订单
 *
 * @author xlg
 */
@SuppressWarnings("unchecked")
public class SyncTimeOrder extends QuartzJobBean {

    private static final Logger log = Logger.getLogger(SyncTimeOrder.class);

    private OrderSyncDAO orderSyncDAO;

    // 订单同步远程方法
    private OrderPvSyncRemoteService orderPvSyncRemoteService;

    private JedisCluster jedisCluster;


    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        String key = "SyncTimeOrder";
        try {
            log.info("同步时代订单任务开始" + new Date());

            Long i = jedisCluster.setnx(key, "1");
            if (i > 0) {// 并发控制
                try {
                    jedisCluster.expire(key, 60 * 60 * 3); // 缓存过期 3小时

                    List<String> list = orderSyncDAO.querySyncOrderCodeForJob();
                    log.info("同步时代订单:" + list);
                    if (null != list && !list.isEmpty()) {
                        Map result = orderPvSyncRemoteService.orderPvSync(list);
                        if (null != result && !result.isEmpty()) {
                            StringBuffer sb = new StringBuffer();
                            for (Object obj : result.keySet()) {
                                Object rsObj = result.get(obj);
                                int rs = 0;
                                if (null != rsObj) {
                                    rs = Integer.parseInt(String.valueOf(rsObj));
                                }
                                sb.append(obj).append(':')
                                        .append(rs == OrderSyncStatus.SUCCESS.getKey()
                                                || rs == OrderSyncStatus.REPEAT.getKey());
                            }
                            log.info(sb);
                        }
                    }
                } finally {
                    jedisCluster.del(key);
                }
            }

        } catch (Exception e) {
            log.error(e);
        }
        log.info("同步时代订单任务结束" + new Date());
    }

    public OrderSyncDAO getOrderSyncDAO() {
        return orderSyncDAO;
    }

    public void setOrderSyncDAO(OrderSyncDAO orderSyncDAO) {
        this.orderSyncDAO = orderSyncDAO;
    }

    public OrderPvSyncRemoteService getOrderPvSyncRemoteService() {
        return orderPvSyncRemoteService;
    }

    public void setOrderPvSyncRemoteService(OrderPvSyncRemoteService orderPvSyncRemoteService) {
        this.orderPvSyncRemoteService = orderPvSyncRemoteService;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}

