package com.pltfm.app.util;

import redis.clients.jedis.JedisCluster;

/**
 * 功能：
 *
 * @author Zhoujiwei
 * @since 2016/4/25 14:23
 */
public class RedisLock {

//    private static String SETNX_SCRIPT =
//            "local key = KEYS[1] " +
//                    "local ttl = KEYS[2] " +
//                    "local content = KEYS[3] " +
//                    "local lockSet = redis.call(\'setnx\', key, content) " +
//                    "if lockSet == 1 " +
//                    "then " +
//                    "   redis.call(\'expire\', key, ttl) " +
//                    "end " +
//                    "return lockSet ";

    private JedisCluster jedisCluster;

    public boolean tryLock(String key) {
        return tryLock(key,  10800);
    }

    public boolean tryLock(String key, int lockSeconds) {
        String lockKey = "JedisLock_".concat(key).intern();
        return setLockNx(lockKey,  lockSeconds);
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
        Long valueLong = System.currentTimeMillis() + (long) lockSeconds * 1000L + 1L;
        long setNxSuccess = jedisCluster.setnx(key, valueLong.toString());
        if(setNxSuccess == 1L) {
            long exSuccess = jedisCluster.expire(key, lockSeconds);
            if(exSuccess == 1L) {
                return true;
            }
        }

        return false;
    }

//    public boolean setNxEx(String key, String value, long seconds) {
//        boolean result;
//        long a = ((Long)jedisCluster.eval(SETNX_SCRIPT, 3, new String[]{key, String.valueOf(seconds), value})).longValue();
//        result = a == 1L;
//        return result;
//    }

    public JedisCluster getJedisCluster() {
        return jedisCluster;
    }

    public void setJedisCluster(JedisCluster jedisCluster) {
        this.jedisCluster = jedisCluster;
    }

}
