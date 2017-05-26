package com.pltfm.app.quartz;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import com.kmzyc.commons.exception.ServiceException;
import com.pltfm.app.service.SellerSettlementService;

import redis.clients.jedis.JedisCluster;


/**
 * 结算单自动生定时任务
 *
 * @author xlg
 */
@SuppressWarnings("unchecked")
public class SettlementGenerate extends QuartzJobBean {

    private static final Logger log = Logger.getLogger(SettlementGenerate.class);

    private SellerSettlementService sellerSettlementService;
    private JedisCluster jedisCluster;

    @Override
    protected void executeInternal(JobExecutionContext arg0) throws JobExecutionException {
        log.info("定时任务 -- 结束单自动生成开始" + new Date());
        Map map = new HashMap();
        String period = getPeriodFromJobExecutionContext(arg0);
        map.put("settlePeriod", period);
        List list = null;
        String key = "SettlementGenerate";
        try {
            Long i = jedisCluster.setnx(key, "1");
            if (i > 0) { // 并发控制
                try {
                    jedisCluster.expire(key, 60 * 60 * 3); // 缓存过期 3小时
                    try {
                        list = sellerSettlementService.selectSellersNeedSettle(map);
                        log.info("需要自动结算的列表为：" + (null == list ? "需结算列表为空" : list.toString()));
                    } catch (Exception e) {
                        jedisCluster.del(key);
                        log.error("查询未结算商户异常", e);
                    }
                    if (CollectionUtils.isNotEmpty(list)) {
                        map.put("sellerIds", list);
                        try {
                            log.info("自动结算商家非空，数据为：" + list.toString());
                            sellerSettlementService.sysAutoGenerateSettlements(map);
                        } catch (ServiceException e) {
                            log.error("生成商户结算数据异常", e);
                        }
                    }
                } finally {
                    jedisCluster.del(key);
                }
            }
        } catch (Exception e) {
            log.error("生成商户结算数据异常！" , e);
        }
        log.info("定时任务 -- 结束单自动生成结束" + new Date());
    }

    /**
     * 通过定时任务的开始时间、下次开始时间的执行日判断是结算上月H2期结算单还是当月H1期结算单
     */
    private String getPeriodFromJobExecutionContext(JobExecutionContext arg0) {
        Calendar firstDate = Calendar.getInstance();
        firstDate.setTime(firstDate.getTime());
        int month = firstDate.get(Calendar.MONTH) + 1;
        int year = firstDate.get(Calendar.YEAR);
        // 计算上半月结算标识
        boolean isH1 = false;
        // 当第一个自动结算日为16号以后，确认要结算的周期为H1期
        if (firstDate.get(Calendar.DAY_OF_MONTH) >= 16) isH1 = true;
        // 当月份为1月，并且不是结H1期账期时，则为结算上月月H2
        month = (month == 1 && !isH1) ? 12 : (!isH1 ? month - 1 : month);
        // 当年份为1月，且不是结H1账期时，则结算年份为上年
        year = (firstDate.get(Calendar.MONTH) == 0 && !isH1) ? year - 1 : year;
        StringBuffer period = new StringBuffer();
        period.append(year);
        // 通过判断当前执行日大于或小于下一执行日，确定生成上月下半月结算数据还是当月上半月结算数据：当前执行日<下一执行日，则生成上月下半月结算数据
//        if (month < 9 && year <= 2015) period = null;
        if (isH1) {
            period.append(month > 9 ? month : "0" + month).append("H1");
        } else {
            period.append(month > 9 ? month : "0" + month).append("H2");
        }
        return period.toString();
    }

    public SellerSettlementService getSellerSettlementService() {
        return sellerSettlementService;
    }

    public void setSellerSettlementService(SellerSettlementService sellerSettlementService) {
        this.sellerSettlementService = sellerSettlementService;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
