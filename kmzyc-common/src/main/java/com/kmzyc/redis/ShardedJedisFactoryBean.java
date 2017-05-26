package com.kmzyc.redis;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;
import java.util.List;

import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * Created by min on 2016/11/10.
 */
public class ShardedJedisFactoryBean implements
        FactoryBean<ShardedJedis> {

    private Logger logger= LoggerFactory.getLogger(ShardedJedisFactoryBean.class);
    private ShardedJedisPool shardedJedisPool;

    public ShardedJedisFactoryBean(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

    public ShardedJedisFactoryBean(GenericObjectPoolConfig poolConfig,
                                   List<JedisShardInfo> shards) {
        this.shardedJedisPool = new ShardedJedisPool(poolConfig, shards);
    }

    public ShardedJedisFactoryBean(List<JedisShardInfo> shards) {
        this.shardedJedisPool = new ShardedJedisPool(new JedisPoolConfig(), shards);
    }

    @Override
    public ShardedJedis getObject() throws Exception {
        return createInstance();
    }

    @Override
    public Class<ShardedJedis> getObjectType() {
        return ShardedJedis.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    protected ShardedJedis createInstance() throws Exception {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(ShardedJedis.class);
        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            if(method.getName().equals("close")){
                //这里没必要close，因为try-with-resources 会自动关闭
                logger.info("ShardedJedis GBlib Proxy close invalid");
                return null;
            }
            try(ShardedJedis shardedJedis = shardedJedisPool.getResource()){
                return methodProxy.invoke(shardedJedis, objects);
            }

        });
        return (ShardedJedis)enhancer.create(new Class[]{List.class},new Object[]{new ArrayList<>()});
    }
}
