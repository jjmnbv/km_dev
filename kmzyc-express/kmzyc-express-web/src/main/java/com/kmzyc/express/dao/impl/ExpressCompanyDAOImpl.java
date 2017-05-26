package com.kmzyc.express.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.kmzyc.express.dao.BaseDAO;
import com.kmzyc.express.dao.ExpressCompanyDAO;
import com.kmzyc.express.entities.ExpressCompany;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings("unchecked")
@Component(value = "expressCompanyDAO")
public class ExpressCompanyDAOImpl extends BaseDAO<ExpressCompany> implements ExpressCompanyDAO {
  private Logger log = Logger.getLogger(ExpressCompanyDAOImpl.class);

  @Resource(name="jedisCluster")
  private JedisCluster jedisCluster;

  private static final String EXPRESS_COMPANY_REDIS_KEY = "expresscompanyrediskey";

  @Override
  public int queryExpressCompanyCount(Map<String, String> paramMap) throws Exception {
    return (Integer) sqlMapClient.queryForObject("KMORDER_EXPRESS_COMPANY.queryExpressCompanyCount",
        paramMap);
  }

  @Override
  public List queryExpressCompanyList(Map<String, String> paramMap) throws Exception {
    return sqlMapClient.queryForList("KMORDER_EXPRESS_COMPANY.queryExpressCompanyList", paramMap);
  }

  @Override
  public ExpressCompany selectExpressCompanyByCode(String code) throws Exception {
    return (ExpressCompany) sqlMapClient
        .queryForObject("KMORDER_EXPRESS_COMPANY.selectExpressCompanyByCode", code);
  }

  /**
   * 查询所有可用物流公司
   * 
   * @return
   * @throws Exception
   */
  @Override
  public List<ExpressCompany> queryAllEnableExpressCompany() throws Exception {
    List<ExpressCompany> ecList = null;
    try {
      String value = null;
      if (null != (value = jedisCluster.get(EXPRESS_COMPANY_REDIS_KEY))) {
        ecList = (List<ExpressCompany>) JSON.parseArray(value, ExpressCompany.class);
      }
      if (null == ecList || ecList.isEmpty()) {
        ecList = (List<ExpressCompany>) sqlMapClient
            .queryForList("KMORDER_EXPRESS_COMPANY.SQL_QUERY_ALL_ENABLE_EXPRESS_COMPANY");
        jedisCluster.setex(EXPRESS_COMPANY_REDIS_KEY, 24 * 60 * 60, JSON.toJSONString(ecList));
      }
    } catch (Exception e) {
      log.error(e);
    }
    return ecList;
  }
}
