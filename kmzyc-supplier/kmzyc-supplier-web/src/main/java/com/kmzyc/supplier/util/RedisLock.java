package com.kmzyc.supplier.util;

import com.google.common.collect.Lists;

import redis.clients.jedis.JedisCluster;


/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/4/25 14:23
 */
public class RedisLock {

    private static String SETNX_SCRIPT =
            "local key = KEYS[1] " +
            "local content = ARGV[1] " +
            "local ttl = ARGV[2] " +
            "local lockSet = redis.call(\'setnx\', key, content) " +
            "if lockSet == 1 " +
            "then " +
            "   redis.call(\'expire\', key, ttl) " +
            "end " +
            "return lockSet ";

    private JedisCluster jedisCluster;

    public boolean tryLock(String key) {
        String lockKey = "JedisLock_".concat(key).intern();
        return setNxEx(lockKey, key, 10800L);
    }

    public boolean tryLock(String key, long lockSeconds) {
        String lockKey = "JedisLock_".concat(key).intern();
        return setNxEx(lockKey, key, lockSeconds);
    }

    public boolean lock(String key, int lockTime, int timeOut) {
        long waitEndTime = System.currentTimeMillis() + (long)(timeOut * 1000);
        String lockKey = "JedisLock_".concat(key).intern();

        while(!setLockNx(lockKey, lockTime)) {
            long currTime = System.currentTimeMillis();
            if(waitEndTime < currTime) {
                return false;
            }

            try {
                Thread.sleep(100L);
            } catch (InterruptedException var10) {
                throw new RuntimeException(var10);
            }
        }

        return true;
    }

    public boolean release(String key) {
        String lockKey = "JedisLock_".concat(key).intern();
        return jedisCluster.del(lockKey).longValue() == 1L;
    }

    public static String getLockKey(String key) {
        return ("JedisLock_" + key).intern();
    }

    private boolean setLockNx(String key, int lockSeconds) {
        if(lockSeconds <= 0) {
            throw new RuntimeException("lockSeconds不能小于等于0");
        }
        Long valueLong = Long.valueOf(System.currentTimeMillis() + (long)lockSeconds * 1000L + 1L);
        long setNxSuccess = jedisCluster.setnx(key, valueLong.toString()).longValue();
        if(setNxSuccess == 1L) {
            long exSuccess = jedisCluster.expire(key, lockSeconds).longValue();
            if(exSuccess == 1L) {
                return true;
            }
        }

        return false;
    }

    public boolean setNxEx(String key, String value, long seconds) {
        long a = ((Long)jedisCluster.eval(SETNX_SCRIPT, Lists.newArrayList(key), Lists.newArrayList(value, String.valueOf(seconds)))).longValue();
        return a == 1L;
    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

}