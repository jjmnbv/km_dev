package com.pltfm.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.kmzyc.commons.exception.ServiceException;
import com.kmzyc.express.util.RedisCacheUtil;
import com.pltfm.app.entities.OrderRiskCondition;
import com.pltfm.app.service.OrderRiskSettingService;
import com.pltfm.app.util.OrderRiskKey;
import com.pltfm.sys.util.ErrorCode;

import redis.clients.jedis.JedisCluster;

@Service
@Scope("singleton")
public class OrderRiskSettingServiceImpl implements OrderRiskSettingService {
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    /**
     * 保存
     */
    @Override
    public void saveOrderRiskSetting(List<OrderRiskCondition> list) throws ServiceException {
        try {
            if (null != list && !list.isEmpty()) {
                for (OrderRiskCondition orc : list) {
                    jedisCluster.hset(OrderRiskKey.ORDER_RISK_SETTING_KEY,
                            RedisCacheUtil.serialize(orc.getKey()), RedisCacheUtil.serialize(orc));
                }
            }
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "初始化风控设置发生异常：" + e.getMessage());
        }
    }

    /**
     * 查询风控设置
     */
    @Override
    public Map<String, OrderRiskCondition> queryOrderRiskCondition() throws ServiceException {
        Map<String, OrderRiskCondition> result = null;
        try {
            Map<byte[], byte[]> map = jedisCluster.hgetAll(OrderRiskKey.ORDER_RISK_SETTING_KEY);
            if (null != map) {
                result = new HashMap<>();
                for (byte[] key : map.keySet()) {
                    result.put((String) RedisCacheUtil.unserialize(key),
                            (OrderRiskCondition) RedisCacheUtil.unserialize(map.get(key)));
                }
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "初始化风控设置发生异常：" + e.getMessage());
        }
    }

    /**
     * 查询风控设置
     */
    @Override
    public OrderRiskCondition queryOrderRisk(String key) throws ServiceException {
        OrderRiskCondition result = null;
        try {
            byte[] value = jedisCluster
                    .hget(OrderRiskKey.ORDER_RISK_SETTING_KEY, RedisCacheUtil.serialize(key));
            if (null != value) {
                result = (OrderRiskCondition) RedisCacheUtil.unserialize(value);
            }
            return result;
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.INTER_ORDER_RISK_ERROR,
                    "初始化风控设置发生异常：" + e.getMessage());
        }
    }
}
