package com.kmzyc.quartz;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import redis.clients.jedis.JedisCluster;


import com.alipay.api.internal.util.StringUtils;

public class QuartzIncreaseCount extends QuartzJobBean {
    // static Logger logger = LogManager.getLogger(Log4j2TestController.class.getName());

    private static final Logger log = LoggerFactory.getLogger(QuartzIncreaseCount.class);

    private JedisCluster jedisCluster;

    private static BigDecimal count = new BigDecimal(1960);



    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        String exp = ((CronTrigger) context.getTrigger()).getCronExpression();

        try {
            String key = "b2b_quartzIncreaseCount";

            long flag = jedisCluster.setnx(key, "1");
            if (flag > 0) {

                try {

                    jedisCluster.expire(key, 1800);

                    Scheduler scheduler = context.getScheduler();
                    String jobName = context.getJobDetail().getName();
                    String triggerName = context.getTrigger().getName();
                    scheduler.unscheduleJob(jobName, Scheduler.DEFAULT_GROUP);
                    scheduler.deleteJob(jobName, Scheduler.DEFAULT_GROUP);
                    SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date next = new Date();

                    String nextCron;
                    Calendar ca = Calendar.getInstance();
                    log.info("现在进入触发器的时间为:" + sim.format(ca.getTime()));
                    int hour = ca.get(Calendar.HOUR_OF_DAY);

                    if (StringUtils.isEmpty(jedisCluster.get("index_count"))) { // 在缓存中设置人数基数
                        jedisCluster.set("index_count", count.toString());
                    }
                    if (hour >= 8 && hour <= 22) { // 这次是在8点到22点之间触发的 ,人数增长为3人 ,这个时候不用判断日期是否要增长
                        next =
                                this.radomDate(
                                        ca.get(Calendar.YEAR) + "-" + (ca.get(Calendar.MONTH) + 1)
                                                + "-" + ca.get(Calendar.DATE) + " "
                                                + (ca.get(Calendar.HOUR_OF_DAY) + 1) + ":00"
                                                + ":00",
                                        ca.get(Calendar.YEAR) + "-" + (ca.get(Calendar.MONTH) + 1)
                                                + "-" + (ca.get(Calendar.DATE)) + " "
                                                + (ca.get(Calendar.HOUR_OF_DAY) + 1) + ":59"
                                                + ":59");
                        ca.setTime(next);
                        nextCron =
                                ca.get(Calendar.SECOND) + " " + ca.get(Calendar.MINUTE) + " "
                                        + ca.get(Calendar.HOUR_OF_DAY) + " * * ?";
                        count = new BigDecimal(jedisCluster.get("index_count")).add(new BigDecimal(3));
                        // 增加人数
                        jedisCluster.set("index_count", count.toString());
                        log.info("增加人数成功");
                    } else { // 是在23：00到7:59之间触发的
                        if (hour == 23) { // 取得下一天
                            ca.add(Calendar.HOUR_OF_DAY, 1); // 截止时间
                            System.out.println(sim.format(ca.getTime()));
                            next =
                                    this.radomDate(
                                            ca.get(Calendar.YEAR) + "-"
                                                    + (ca.get(Calendar.MONTH) + 1) + "-"
                                                    + ca.get(Calendar.DATE) + " "
                                                    + ca.get(Calendar.HOUR_OF_DAY) + ":00" + ":00",
                                            ca.get(Calendar.YEAR) + "-"
                                                    + (ca.get(Calendar.MONTH) + 1) + "-"
                                                    + (ca.get(Calendar.DATE)) + " "
                                                    + (ca.get(Calendar.HOUR_OF_DAY)) + ":59"
                                                    + ":59");
                        } else {
                            next =
                                    this.radomDate(
                                            ca.get(Calendar.YEAR) + "-"
                                                    + (ca.get(Calendar.MONTH) + 1) + "-"
                                                    + ca.get(Calendar.DATE) + " "
                                                    + (ca.get(Calendar.HOUR) + 1) + ":00" + ":00",
                                            ca.get(Calendar.YEAR) + "-"
                                                    + (ca.get(Calendar.MONTH) + 1) + "-"
                                                    + (ca.get(Calendar.DATE)) + " "
                                                    + (ca.get(Calendar.HOUR_OF_DAY) + 1) + ":59"
                                                    + ":59");
                        }
                        ca.setTime(next);
                        nextCron =
                                ca.get(Calendar.SECOND) + " " + ca.get(Calendar.MINUTE) + " "
                                        + ca.get(Calendar.HOUR_OF_DAY) + " * * ?";
                        count = new BigDecimal(jedisCluster.get("index_count")).add(new BigDecimal(1));
                        // 增加人数
                        jedisCluster.set("index_count", count.toString());
                        System.out.println(jedisCluster.get("index_count"));
                        log.info("增加人数成功");
                    }
                    log.info("下次的触发时间为:" + sim.format(next) + "触发器时间格式为：" + nextCron);

                    System.out.println(sim.format(next) + "----" + nextCron);
                    if (!nextCron.equals(exp)) {
                        CronTrigger trigger = new CronTrigger(triggerName, Scheduler.DEFAULT_GROUP);
                        trigger.setCronExpression(nextCron);
                        JobDetail jobDetail =
                                new JobDetail(jobName, Scheduler.DEFAULT_GROUP, this.getClass());
                        scheduler.scheduleJob(jobDetail, trigger);
                    }
                } finally {
                    jedisCluster.del(key);
                }
            }
        } catch (Exception e) {
            log.error("更新提示人数报错" + e.getMessage(), e);
        }
    }

    private Date radomDate(String startTime, String endTime) {
        SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date s = new Date();
        Date e = new Date();
        try {
            s = sim.parse(startTime);
            e = sim.parse(endTime);
        } catch (ParseException e1) {
            log.error(e1.getMessage(),e);
        }
        long ramdam = random(s.getTime(), e.getTime());
        return new Date(ramdam);
    }

    private static long random(long begin, long end) {
        long rtn = (long) ((Math.random() * (end + 1 - begin) + begin));
        // 如果返回的是开始时间和结束时间，则递归调用本函数查找随机值
        if (rtn == begin || rtn == end) {
            return random(begin, end);
        }
        return rtn;
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }
}
