package com.kmzyc.promotion.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.JedisCluster;

/**
 * Redis相关的功能模板类.
 * 
 * @author hwf
 * @version 0.1.0
 */
public class RedisTemplate {

    private Logger logger = LoggerFactory.getLogger(RedisTemplate.class);

    @Resource
    private JedisCluster jedisCluster;

    public boolean exists(String key) {
        boolean result = false;
        try {
            result = jedisCluster.exists(key);
        } catch (Exception e) {

            logger.error("RedisTemplate method:exists,error:", e);
        }

        return result;
    }

    public void push(String queueName, String content) {
        try {
            jedisCluster.lpush(queueName, content);
        } catch (Exception e) {
            logger.error("RedisTemplate method:push,error:", e);
        }
    }

    public void set(String key, String value) {
        try {
            jedisCluster.set(key, value);
        } catch (Exception e) {

            logger.error("RedisTemplate method:set,error:", e);
        }
    }

    public void setex(String key, String value, int timeout) {
        try {
            jedisCluster.setex(key, timeout, value);
        } catch (Exception e) {

            logger.error("RedisTemplate method:setex,error:", e);
        }
    }

    public String get(String key) {
        String value = "";
        try {
            value = jedisCluster.get(key);
        } catch (Exception e) {

            logger.error("RedisTemplate method:setex,error:", e);
        }
        return value;
    }

    public String getAndDel(String key) {
        String value = "";
        try {
            value = jedisCluster.get(key);
            jedisCluster.del(key);
        } catch (Exception e) {

            logger.error("RedisTemplate method:getAndDel,error:", e);
        }
        return value;
    }

    public void del(String key) {
        try {
            jedisCluster.del(key);
        } catch (Exception e) {

            logger.error("RedisTemplate method:del,error:", e);
        }
    }

    public List<String> lRange(String key) {
        List<String> result = new ArrayList<String>();
        try {
            result = jedisCluster.lrange(key, 0, -1);

        } catch (Exception e) {

            logger.error("RedisTemplate method:lRange,error:", e);
        }

        return result;
    }

    /**
     * 不存在key，就设置值value，设置成功返回1，失败返回0
     * 
     * @param key
     * @param value
     * @return
     */
    public long setnx(String key, String value) {
        long setnxSuccess = 0l;
        try {
            setnxSuccess = jedisCluster.setnx(key, value);
        } catch (Exception e) {

            logger.error("RedisTemplate method:setnx,error:", e);
        }

        return setnxSuccess;
    }

    private boolean setLockNx(String key, int lockSeonds) {
        try {
            if (lockSeonds <= 0) {

                throw new RuntimeException("lockSeonds不能小于等于0");
            }
            Long valueLong = System.currentTimeMillis() + lockSeonds * 1000L + 1;
            long setnxSuccess = jedisCluster.setnx(key, valueLong.toString());
            if (setnxSuccess == 1) {
                long exSuccess = jedisCluster.expire(key, lockSeonds);
                if (exSuccess == 1) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {

            logger.error("RedisTemplate method:setLockNx,error:", e);
        }

        return false;
    }

    public boolean setNxEx(String key, String value, int seconds) {
        long rslt = jedisCluster.setnx(key, value);
        if (rslt > 0) {
            jedisCluster.expire(key, seconds);

            return true;
        }

        return false;
    }

    /**
     * redis加锁
     * 
     * @param key 锁定的key
     * @param lockTime 单位秒，锁定key的时间
     * @param timeOut 单位秒，如果已经被加锁，需等待的时间，等待超时将返回失败
     * @return
     * @throws InterruptedException
     */
    public boolean lock(String key, int lockTime, int timeOut) {
        long waitEndTime = System.currentTimeMillis() + (timeOut * 1000);
        String lockKey = ("JedisLock_".concat(key)).intern();
        while (!this.setLockNx(lockKey, lockTime)) {
            long currTime = System.currentTimeMillis();
            if (waitEndTime < currTime) {// 加锁失败 等待超时
                // LOG.error("key:{}加锁失败,等待超时!", key);
                return false;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }

    /** 给指定参数加锁，需要调用release释放 */
    public boolean tryLock(String key) {
        String lockKey = ("JedisLock_".concat(key)).intern();
        return this.setNxEx(lockKey, key, 60 * 60 * 3);
    }

    /** 给指定参数加锁,lockSeconds为锁定时间单位秒，需要调用release释放 */
    public boolean tryLock(String key, int lockSeconds) {
        String lockKey = ("JedisLock_".concat(key)).intern();
        return this.setNxEx(lockKey, key, lockSeconds);
    }

    /**
     * 释放key,这个方法在加锁成功锁使用完毕以后调用，需要放到finally里
     * 
     * @param key
     * @return
     */
    public boolean release(String key) {
        boolean ok = false;
        try {
            String lockKey = ("JedisLock_".concat(key)).intern();
            ok = jedisCluster.del(lockKey) == 1;
        } catch (Exception e) {

            logger.error("RedisTemplate method:release,error:", e);
        }

        return ok;
    }

    public static String getLockKey(String key) {
        return ("JedisLock_" + key).intern();
    }

    /**
     * 对指定的key自增,第一次是从0开始
     * 
     * @param key
     * @return 自增后的值
     */
    public long incr(String key) {
        long result = 0l;
        try {
            result = jedisCluster.incr(key);
        } catch (Exception e) {

            logger.error("RedisTemplate method:incr,error:", e);
        }

        return result;
    }

    /**
     * 对指定的key自减,第一次是从0开始
     * 
     * @param key
     * @return 自减后的值
     */
    public long decr(String key) {
        long result = 0l;
        try {
            result = jedisCluster.decr(key);
        } catch (Exception e) {

            logger.error("RedisTemplate method:decr,error:", e);
        }

        return result;
    }

    /** SortedSet（有序集合） **/
    public void zadd(String key, double socre, String value) {
        try {
            jedisCluster.zadd(key, socre, value);
        } catch (Exception e) {

            logger.error("RedisTemplate method:zadd,error:", e);
        }
    }

    /**
     * 获取有序集合 从start到end条 升序 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样。
     */
    public Set<String> zrange(String key, long start, long end) {
        try {
            return jedisCluster.zrange(key, start, end);
        } catch (Exception e) {

            logger.error("RedisTemplate method:zrange,error:", e);
        }

        return null;
    }

    /**
     * 获取有序集合 从start到end条 升序 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样。
     */
    public Set<String> zrangeByScore(String key, double min, double max) {
        try {
            return jedisCluster.zrangeByScore(key, min, max);
        } catch (Exception e) {

            logger.error("RedisTemplate method:zrangeByScore,error:", e);
        }

        return null;
    }

    /**
     * 获取有序集合 从start到end条 升序 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样。
     */
    public Set<String> zrevrangeByScore(String key, double min, double max) {
        try {
            return jedisCluster.zrevrangeByScore(key, max, min);
        } catch (Exception e) {

            logger.error("RedisTemplate method:zrevrangeByScore,error:", e);
        }

        return null;
    }

    /**
     * 获取有序集合 从start到end条 降序 除了成员按 score 值递减的次序排列这一点外， ZREVRANGE 命令的其他方面和 ZRANGE 命令一样。
     */
    public Set<String> zrevrange(String key, long start, long end) {
        try {
            return jedisCluster.zrevrange(key, start, end);
        } catch (Exception e) {

            logger.error("RedisTemplate method:zrevrange,error:", e);
        }

        return null;
    }

    /**
     * 
     * @param key
     * @param value
     * @param time 到这个时间过期
     */
    public void setexat(byte[] key, byte[] value, long expireAt) {
        try {
            jedisCluster.set(key, value);
            if (expireAt > 0) {
                int se = Long.valueOf((expireAt - System.currentTimeMillis()) / 1000).intValue();
                jedisCluster.expire(key, se);
            }
        } catch (Exception e) {

            logger.error("RedisTemplate method:setexat,error:", e);
        }

        return;
    }

    public void setexat(String key, String value, long expireAt) {
        try {
            jedisCluster.set(key, value);
            if (expireAt > 0) {
                int se = Long.valueOf((expireAt - System.currentTimeMillis()) / 1000).intValue();
                jedisCluster.expire(key, se);
            }
        } catch (Exception e) {

            logger.error("RedisTemplate method:setexat,error:", e);
        }
    }

    public byte[] get(byte[] key) {
        try {
            return jedisCluster.get(key);
        } catch (Exception e) {

            logger.error("RedisTemplate method:get,error:", e);
        }

        return null;
    }

    public void hmset(String key, Map<String, String> value) {
        try {
            jedisCluster.hmset(key, value);
        } catch (Exception e) {

            logger.error("RedisTemplate method:hmset,error:", e);
        }
    }

    /**
     * 
     * @param key
     * @param value
     * @param expireAt 到这个时间过期
     */
    public void hmset(String key, Map<String, String> value, long expireAt) {
        try {
            jedisCluster.hmset(key, value);
            if (expireAt > 0) {
                int se = Long.valueOf((expireAt - System.currentTimeMillis()) / 1000).intValue();
                jedisCluster.expire(key, se);
            }
        } catch (Exception e) {

            logger.error("RedisTemplate method:hmset,error:", e);
        }
    }

    public String hget(String key, String field) {
        try {
            return jedisCluster.hget(key, field);
        } catch (Exception e) {

            logger.error("RedisTemplate method:hget,error:", e);
        }

        return null;
    }

    public Long hset(String key, String field, String value) {
        try {
            return jedisCluster.hset(key, field, value);
        } catch (Exception e) {

            logger.error("RedisTemplate method:hset,error:", e);
        }

        return null;
    }

    public List<String> hmget(String key, String[] fields) {
        try {
            return jedisCluster.hmget(key, fields);
        } catch (Exception e) {

            logger.error("RedisTemplate method:hmget,error:", e);
        }

        return null;
    }

    public Map<String, String> hmgetAll(String key) {
        try {
            return jedisCluster.hgetAll(key);
        } catch (Exception e) {

            logger.error("RedisTemplate method:hmgetAll,error:", e);
        }

        return null;
    }

    public void sadd(String key, long expireAt, String... members) {
        try {
            jedisCluster.sadd(key, members);
            if (expireAt > 0) {
                int se = Long.valueOf((expireAt - System.currentTimeMillis()) / 1000).intValue();
                jedisCluster.expire(key, se);
            }
        } catch (Exception e) {

            logger.error("RedisTemplate method:sadd,error:", e);
        }

        return;
    }

    public boolean sismember(String key, String member) {
        try {
            return jedisCluster.sismember(key, member);
        } catch (Exception e) {

            logger.error("RedisTemplate method:sismember,error:", e);
        }

        return false;
    }

    public Set<String> smembers(String key) {
        try {

            return jedisCluster.smembers(key);
        } catch (Exception e) {

            logger.error("RedisTemplate method:smembers,error:", e);
        }

        return null;
    }

    public void zaddex(String key, long expireAt, Map<String, Double> skuIds) {
        try {
            jedisCluster.zadd(key, skuIds);
            int se = Long.valueOf((expireAt - System.currentTimeMillis()) / 1000).intValue();
            jedisCluster.expire(key, se);
        } catch (Exception e) {

            logger.error("RedisTemplate method:zaddex,error:", e);
        }

        return;
    }

    /**
     * 设置 key 在 seconds 秒后过期
     * 
     * @param key
     * @param seconds
     * @return
     */
    public Long expire(String key, int seconds) {
        try {
            return jedisCluster.expire(key, seconds);
        } catch (Exception e) {

            logger.error("RedisTemplate method:expire,error:", e);
        }

        return null;
    }
}
