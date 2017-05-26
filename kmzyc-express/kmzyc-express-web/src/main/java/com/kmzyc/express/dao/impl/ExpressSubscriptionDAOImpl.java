package com.kmzyc.express.dao.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kmzyc.express.dao.BaseDAO;
import com.kmzyc.express.dao.ExpressSubscriptionDAO;
import com.kmzyc.express.entities.ExpressSubscription;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Component(value = "expressSubscriptionDAO")
public class ExpressSubscriptionDAOImpl extends BaseDAO<ExpressSubscription>
        implements
            ExpressSubscriptionDAO {

    @Resource(name="jedisCluster")
    private JedisCluster jedisCluster;

    @Override
    public int queryExpressSubscriptionCount(Map<String, String> paramMap) throws Exception {
        return (Integer) sqlMapClient.queryForObject(
                "KMORDER_EXPRESS_SUBSCRIPTION.queryExpressSubscriptionCount", paramMap);
    }

    @Override
    public List queryExpressSubscriptionList(Map<String, String> paramMap) throws Exception {
        return sqlMapClient.queryForList(
                "KMORDER_EXPRESS_SUBSCRIPTION.queryExpressSubscriptionList", paramMap);
    }

    @Override
    public List<ExpressSubscription> queryExpressSubWithTrackDetail(final String logisticsCode, final String logisticsNo)
            throws Exception {
        if (StringUtils.isEmpty(logisticsCode) || StringUtils.isEmpty(logisticsNo)) {
            return null;
        }

        Map<String, String> paramMap = new HashMap<String, String>(2);
        paramMap.put("logisticsCode", logisticsCode);
        paramMap.put("logisticsNo", logisticsNo);
        return sqlMapClient.queryForList("KMORDER_EXPRESS_SUBSCRIPTION.queryExpressSubWithTrackDetail", paramMap);
    }

    @Override
    public BigDecimal insertSubscription(ExpressSubscription record) throws SQLException {
        return (BigDecimal) sqlMapClient.insert("KMORDER_EXPRESS_SUBSCRIPTION.insertSubscription",
                record);
    }

    @Override
    public ExpressSubscription selectSubscriptionByPrimaryKey(ExpressSubscription record)
            throws Exception {
        return (ExpressSubscription) sqlMapClient.queryForObject(
                "KMORDER_EXPRESS_SUBSCRIPTION.selectSubscriptionByPrimaryKey", record);
    }

    @Override
    public int updateSubscriptionByPrimaryKey(ExpressSubscription record) throws Exception {
        int rows =
                sqlMapClient.update("KMORDER_EXPRESS_SUBSCRIPTION.updateSubscriptionByPrimaryKey",
                        record);
        return rows;
    }

    @Override
    public List queryAllAbortSubscriptionList(Map<String, String> paramMap) throws Exception {
        return sqlMapClient.queryForList(
                "KMORDER_EXPRESS_SUBSCRIPTION.queryAllAbortExpressSubscriptionList", paramMap);
    }

    @Override
    public int querySubCountByLosCodeAndNo(Map<String, String> paramMap) throws Exception {
        return (Integer) sqlMapClient.queryForObject(
                "KMORDER_EXPRESS_SUBSCRIPTION.querySubCountByLosCodeAndNo", paramMap);
    }

    @Override
    public List<ExpressSubscription> querySubscriptionByLosCodeAndNo(Map<String, String> paramMap) throws Exception {
        return sqlMapClient.queryForList("KMORDER_EXPRESS_SUBSCRIPTION.querySubscriptionByLosCodeAndNo", paramMap);
    }

    @Override
    public void clearExpressSubProcessingCacheFlag(String logisticsCode, String logisticsNo)
            throws Exception {
        StringBuilder sBuilder = new StringBuilder(200);
        sBuilder.append("expresssubprocessingflag-").append(logisticsCode).append("-").append(logisticsNo);
        jedisCluster.del(sBuilder.toString());
    }

    @Override
    public void insertExpressSubProcessingCacheFlag(String logisticsCode, String logisticsNo)throws Exception {
        StringBuilder sBuilder = new StringBuilder(200);
        sBuilder.append("expresssubprocessingflag-").append(logisticsCode).append("-").append(logisticsNo);
        String redisKey = sBuilder.toString();
        jedisCluster.setex(redisKey, 15 * 60, "true");// 有效期15分钟
    }

    @Override
    public boolean isExistExpressSubProcessingCacheFlag(String logisticsCode, String logisticsNo)
            throws Exception {
        boolean result = false;
        StringBuilder sBuilder = new StringBuilder(200);
        sBuilder.append("expresssubprocessingflag-").append(logisticsCode).append("-").append(logisticsNo);
        result = jedisCluster.exists(sBuilder.toString());
        return result;
    }
}
