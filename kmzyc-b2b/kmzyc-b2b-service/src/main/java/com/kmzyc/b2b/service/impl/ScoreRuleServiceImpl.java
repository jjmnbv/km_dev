package com.kmzyc.b2b.service.impl;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.kmzyc.b2b.dao.ScoreRuleDao;
import com.kmzyc.b2b.model.ScoreRule;
import com.kmzyc.b2b.service.ScoreRuleService;
import com.kmzyc.b2b.shopcart.util.ShopUtil;
import com.kmzyc.framework.exception.ServiceException;
import com.kmzyc.util.StringUtil;

import redis.clients.jedis.JedisCluster;

@SuppressWarnings({"unchecked", "BigDecimalMethodWithoutRoundingCalled"})
@Service
public class ScoreRuleServiceImpl implements ScoreRuleService {

    @Value("${b2b.com.km.socre.rule.ru0006}")
    private String socreRuleKey;

    @Value("${b2b.com.km.socre.rule.ru0017}")
    private String memberSocreRuleKey;

    @Resource(name = "scoreRuleDaoImpl")
    private ScoreRuleDao scoreRuleDao;

    @Resource(name = "jedisCluster")
    private JedisCluster jedisCluster;

    // private static Logger logger = Logger.getLogger(ScoreRuleServiceImpl.class);
    private static Logger logger = LoggerFactory.getLogger(ScoreRuleServiceImpl.class);

    @Override
    public List<ScoreRule> findAllRule() throws Exception {

        return scoreRuleDao.findList("ScoreRule.findAllRule");
    }

    /**
     * 获取购物积分
     * 
     * @return amount订单金额
     * @throws ServiceException
     */
    @Override
    public int getBuyScore(BigDecimal amount) throws ServiceException {
        ScoreRule scoreRule = null;
        String ruleString;
        boolean isMemberDay = false;

        // 获取系统当前时间，如果是当前月是18日,则使用RU0017计算积分
        if (Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 18) {
            isMemberDay = true;
        }

        // 从缓存获取
        ruleString =
                isMemberDay ? jedisCluster.get(memberSocreRuleKey) : jedisCluster.get(socreRuleKey);

        if (!StringUtil.isEmpty(ruleString)) {
            scoreRule = JSONObject.parseObject(ruleString, ScoreRule.class);
        }
        if (scoreRule == null) {
            // cache不存在或过期，查询db
            try {
                if (isMemberDay) {
                    scoreRule = scoreRuleDao.findByCode("RU0017");
                    jedisCluster.setex(memberSocreRuleKey, ShopUtil.SETTLEMENT_TIME_OUT,
                            JSONObject.toJSONString(scoreRule));
                } else {
                    scoreRule = scoreRuleDao.findByCode("RU0006");
                    jedisCluster.setex(socreRuleKey, ShopUtil.SETTLEMENT_TIME_OUT,
                            JSONObject.toJSONString(scoreRule));
                }
            } catch (Exception e) {
                logger.error("", e);
            }
        }

        String scoreExpress;
        if (null != scoreRule && null != (scoreExpress = scoreRule.getScoreexpress())) {
            if (scoreExpress.indexOf('*') > 0) {
                return amount.multiply(new BigDecimal(scoreExpress.replace("A*", ""))).intValue();
            } else if (scoreExpress.indexOf('+') > 0) {
                return amount.add(new BigDecimal(scoreExpress.replace("A+", ""))).intValue();
            } else if (scoreExpress.indexOf('/') > 0) {
                return amount.divide(new BigDecimal(scoreExpress.replace("A/", ""))).intValue();
            } else if (scoreExpress.indexOf('-') > 0) {
                return amount.subtract(new BigDecimal(scoreExpress.replace("A-", ""))).intValue();
            }
        }

        return amount.intValue();
    }
}
