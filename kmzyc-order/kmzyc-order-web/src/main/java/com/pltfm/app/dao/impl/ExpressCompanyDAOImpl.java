package com.pltfm.app.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.kmzyc.express.entities.ExpressCompany;
import com.kmzyc.express.util.RedisCacheUtil;
import com.pltfm.app.dao.BaseDAO;
import com.pltfm.app.dao.ExpressCompanyDAO;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Component(value = "expressCompanyDAO")
public class ExpressCompanyDAOImpl extends BaseDAO<ExpressCompany> implements ExpressCompanyDAO {
    private Logger log = Logger.getLogger(ExpressCompanyDAOImpl.class);
    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;
    private static final String EXPRESS_COMPANY_REDIS_KEY = "expresscompanyrediskey";

    @Override
    public int queryExpressCompanyCount(Map<String, String> paramMap) throws Exception {
        return (Integer) sqlMapClient
                .queryForObject("KMORDER_EXPRESS_COMPANY.queryExpressCompanyCount", paramMap);
    }

    @Override
    public List queryExpressCompanyList(Map<String, String> paramMap) throws Exception {
        return sqlMapClient
                .queryForList("KMORDER_EXPRESS_COMPANY.queryExpressCompanyList", paramMap);
    }

    @Override
    public ExpressCompany selectExpressCompanyByCode(String code) throws Exception {
        return (ExpressCompany) sqlMapClient
                .queryForObject("KMORDER_EXPRESS_COMPANY.selectExpressCompanyByCode", code);
    }

    /**
     * 查询所有可用物流公司
     */
    @Override
    public List<ExpressCompany> queryAllEnableExpressCompany() throws Exception {
        List<ExpressCompany> ecList = null;
        byte[] key = EXPRESS_COMPANY_REDIS_KEY.getBytes();
        byte[] bt = jedisCluster.get(key);
        if (null != bt) {
            ecList = (List<ExpressCompany>) RedisCacheUtil.unserialize(bt);
        }
        if (null == ecList || ecList.isEmpty()) {
            ecList = sqlMapClient
                    .queryForList("KMORDER_EXPRESS_COMPANY.SQL_QUERY_ALL_ENABLE_EXPRESS_COMPANY");
            jedisCluster.set(key, RedisCacheUtil.serialize(ecList));
            jedisCluster.expire(key, 24 * 60 * 60);// 有效期24个小时
        }
        return ecList;
    }
}
