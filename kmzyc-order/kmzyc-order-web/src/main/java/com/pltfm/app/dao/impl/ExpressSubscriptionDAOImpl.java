package com.pltfm.app.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.express.entities.ExpressSubscription;
import com.kmzyc.express.util.RedisCacheUtil;
import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.ExpressSubscriptionDAO;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Component(value = "expressSubscriptionDAO")
public class ExpressSubscriptionDAOImpl extends BaseDAO<ExpressSubscription>
        implements ExpressSubscriptionDAO {

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    @Override
    public int queryExpressSubscriptionCount(Map<String, String> paramMap) throws Exception {
        return (Integer) sqlMapClient
                .queryForObject("KMORDER_EXPRESS_SUBSCRIPTION.queryExpressSubscriptionCount",
                        paramMap);
    }

    @Override
    public List queryExpressSubscriptionList(Map<String, String> paramMap) throws Exception {
        return sqlMapClient
                .queryForList("KMORDER_EXPRESS_SUBSCRIPTION.queryExpressSubscriptionList",
                        paramMap);
    }

    @Override
    public List queryExpressSubWithTrackDetail(final String logisticsCode,
                                               final String logisticsNo) throws Exception {
        List<ExpressSubscription> lstSubs = null;
        StringBuilder sBuilder = new StringBuilder(200);
        sBuilder.append("expresssubredis-").append(logisticsCode).append("-").append(logisticsNo);
        byte[] key = sBuilder.toString().getBytes();
        byte[] bt = jedisCluster.get(key);
        if (null != bt) {
            lstSubs = (List<ExpressSubscription>) RedisCacheUtil.unserialize(bt);
        } else {
            Map<String, String> paramMap = new HashMap<String, String>(2);
            paramMap.put("logisticsCode", logisticsCode);
            paramMap.put("logisticsNo", logisticsNo);
            lstSubs = sqlMapClient
                    .queryForList("KMORDER_EXPRESS_SUBSCRIPTION.queryExpressSubWithTrackDetail",
                            paramMap);
            jedisCluster.set(key, RedisCacheUtil.serialize(lstSubs));
            jedisCluster.expire(key, 5 * 60);// 有效期10分钟
        }
        return lstSubs;
    }

    @Override
    public ExpressSubscription selectSubscriptionByPrimaryKey(ExpressSubscription record) throws
            Exception {
        return (ExpressSubscription) sqlMapClient
                .queryForObject("KMORDER_EXPRESS_SUBSCRIPTION.selectSubscriptionByPrimaryKey",
                        record);
    }

    @Override
    public int querySubCountByLosCodeAndNo(Map<String, String> paramMap) throws Exception {
        return (Integer) sqlMapClient
                .queryForObject("KMORDER_EXPRESS_SUBSCRIPTION.querySubCountByLosCodeAndNo",
                        paramMap);
    }
}
