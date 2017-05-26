package com.pltfm.app.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.concurrent.atomic.AtomicInteger;


import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.JedisCluster;


public class OrderCodeUtil {
    private static AtomicInteger value = new AtomicInteger(0);

    private static JedisCluster jedisCluster;


    @Autowired(required=true)
    public void setJedisCluster(JedisCluster jedisCluster) {
        OrderCodeUtil.jedisCluster = jedisCluster;
    }



//    private static OrderCodeUtil orderCodeUtil;

//    @PostConstruct
//    public void init() {
//        orderCodeUtil = this;
//        orderCodeUtil.jedisCluster = this.jedisCluster;
//    }

    public static String generateOrderCode() {
        StringBuilder orderCode = new StringBuilder();
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        orderCode.append(sdf.format(Calendar.getInstance().getTime()));

        int now = genNow2();
        if (now < 10) {
            orderCode.append("00");
        } else if (now < 100) {
            orderCode.append("0");
        }
        orderCode.append(now);
        return orderCode.toString();
    }

    /**
     * 
     * @return
     */
    private static int genNow() {
        if (value.get() >= 999) {
            value.set(0);
        }
        return value.incrementAndGet();
    }

    private static int genNow2() { // 解决分布式订单重复问题  
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);//获取年份
        Long s  = jedisCluster.incr("generateOrderCode"+year);
        return Math.abs((int)(s%1000));
//        if (jedisCluster.exists("generateOrderCode")) {
//            
//        } else {
//            jedisCluster.setnx("generateOrderCode", "1");
//        }
//        if (null == s) {
//            return 1;
//        } else {
//            if (s.intValue() >= 999) {
//                jedisCluster.set("generateOrderCode", "0");
//            }
//            return s.intValue();
//        }
    }



    public static void main(String[] args) {
       /* System.out.println(Long.MAX_VALUE);
        System.out.println(Math.abs(Long.MIN_VALUE));
        System.out.println(Long.MIN_VALUE);*/
        
       /*  * int i = 0; for( i = 0;i<100;i++){ new Thread(new Runnable(){public void run(){
         * System.out.println(generateOrderCode()); }}).start();
         * 
         * } System.out.println(i);
         */

        /*
         * for(int i =0;i<10010;i++){ System.out.println(genNow()); }
         */

        // generateOrderCode();

        Calendar cal = Calendar.getInstance();
    
        int year = cal.get(Calendar.YEAR);//获取年份
        System.out.println(year);
          /*Set<HostAndPort> jedisClusterNodes = new HashSet<HostAndPort>();
          jedisClusterNodes.add(new HostAndPort("10.1.6.68", 6379)); 
          JedisCluster jc = new
          JedisCluster(jedisClusterNodes); for(int i =0;i<2020;i++){
              Long s  = jc.incr("sges"+date.getYear());
              System.out.println(Math.abs((int)(s%1000))); 
          }*/
    }

    }

