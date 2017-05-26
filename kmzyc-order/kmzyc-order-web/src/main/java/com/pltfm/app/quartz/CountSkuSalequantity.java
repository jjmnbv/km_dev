package com.pltfm.app.quartz;


import com.pltfm.app.dao.OrderMainDAO;

import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import redis.clients.jedis.JedisCluster;


public class CountSkuSalequantity extends QuartzJobBean {

    private static final Logger log = Logger.getLogger(CountSkuSalequantity.class);

    private OrderMainDAO orderMainDAO;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {

        log.info("定时任务 -- 统计销量开始" + new Date());
        List<String> skuIdList;
        Map<String, String> skuAndCount = new HashMap<>();
        long size;
        String key = "CountSkuSalequantity";
        try {
            Long re = jedisCluster.setnx(key, "1");
            if (re > 0) { //并发控制
                try {
                    jedisCluster.expire(key, 60 * 60 * 3); // 缓存过期 3小时
                    //1.查询出skuCode总个数
                    Long total = orderMainDAO.getSkuIdTotal();
                    if (null != total) {
                        if (total % 1000 == 0) {
                            size = total / 1000;
                        } else {
                            size = (total / 1000) + 1;
                        }
                        for (int i = 0; i < size; i++) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("start", i * 1000 + 1);
                            map.put("end", i * 1000 + 1000);
                            skuIdList = orderMainDAO.getSkuIdList(map);
                            Map<String, String> skuSale;
                            skuSale = orderMainDAO.skuIdAndCount(skuIdList);
                            skuAndCount.putAll(skuSale);
                        }

                    }

                    //加入缓存
                    if (!skuAndCount.isEmpty()) {

                        if (jedisCluster.exists("skuId_count")) {
                            jedisCluster.del("skuId_count");
                        }

                        jedisCluster.hmset("skuId_count", skuAndCount);
                        //        List<String> s = jedis.hmget("skuId_count", "9544");
                        //        List<String> s2 = jedis.hmget("skuId_count", "sletest0009");
                        //        String temp = (null == s.get(0))?"0":s.get(0);
                        //        System.out.println("SKU_ID:9544 = "+temp);
                        //        String temp2 = (null == s2.get(0))?"0":s2.get(0);
                        //        System.out.println("SKU_CODE:sletest0009 = "+ temp2);
                        Calendar cal = Calendar.getInstance();
                        cal.set(Calendar.DATE, 7);
                        jedisCluster.expireAt("skuId_count", cal.getTimeInMillis());

                    }
                } finally {
                    jedisCluster.del(key);
                }
            }

            log.info("定时任务 -- 统计销量结束" + new Date());
        } catch (Exception e) {

            log.error("查询skuId总数异常！" , e);
            e.printStackTrace();
        }

    }

    public OrderMainDAO getOrderMainDAO() {
        return orderMainDAO;
    }

    public void setOrderMainDAO(OrderMainDAO orderMainDAO) {
        this.orderMainDAO = orderMainDAO;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
