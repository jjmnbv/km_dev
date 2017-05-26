package com.pltfm.app.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.dao.PersonalityInfoDAO;
import com.pltfm.app.dao.RankDAO;
import com.pltfm.app.dao.ScoreInfoDAO;
import com.pltfm.app.dao.ScoreRuleDAO;
import com.pltfm.app.service.ScoreRuleService;
import com.pltfm.app.util.ListUtils;
import com.pltfm.app.vobject.ScoreRule;
import com.pltfm.app.vobject.ScoreRuleExample;

import redis.clients.jedis.JedisCluster;


/**
 * 客户积分规则业务逻辑实现类
 * 
 * @author zhl
 * @since 2013-07-10
 * 
 */
@Component(value = "scoreRuleService")
public class ScoreRuleServiceImpl implements ScoreRuleService {
    @Resource(name = "scoreRuleDAO")
    private ScoreRuleDAO scoreRuleDAO;
    // 积分明细dao
    @Resource(name = "scoreInfoDAO")
    private ScoreInfoDAO scoreInfoDAO;
    // 个人信息
    @Resource(name = "personalityInfoDAO")
    private PersonalityInfoDAO personalityInfoDAO;
    // 客户头衔
    @Resource(name = "rankDAO")
    private RankDAO rankDAO;

    @Resource
    private JedisCluster jedis;


    /**
     * 添加客户积分规则信息
     */
    @Override
    public void addScoreRule(ScoreRule scoreRule) throws Exception {
        scoreRuleDAO.insert(scoreRule);
    }

    /**
     * 删除多条客户积分规则信息
     */
    @Override
    public Integer deleteScoreRule(List<String> ruleIds) throws Exception {
        Integer count = 0;
        if (ListUtils.isNotEmpty(ruleIds)) {
            for (String ruleId : ruleIds) {
                count += scoreRuleDAO.deleteByPrimaryKey(Integer.valueOf(ruleId));
            }
        }
        return count;
    }

    /**
     * 通过主键查询客户积分规则信息
     */
    @Override
    public ScoreRule queryByPrimaryKey(Integer ruleId) throws Exception {
        return scoreRuleDAO.selectByPrimaryKey(ruleId);
    }

    /**
     * 多条件查询客户积分规则信息
     */
    @Override
    public List queryScoreRule(ScoreRuleExample example) throws Exception {
        return scoreRuleDAO.selectByExample(example);
    }

    /**
     * 删除单条客户积分规则信息
     * 
     * @param ruleId 客户积分规则id
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteByPrimaryKey(Integer ruleId) throws Exception {
        return scoreRuleDAO.deleteByPrimaryKey(ruleId);
    }

    /**
     * 更新客户积分规则信息
     */
    @Override
    public Integer updateScoreRule(ScoreRule scoreRule) throws Exception {
        // 积分规则获取，在 购物车优化中：获取购物积分 有使用redis cache,此处更新的时候需要清理该缓存
        if (scoreRule != null) {
            if (scoreRule.getCode().equals("RU0006")) {
                jedis.del("b2b.com.km.socre.rule.ru0006");
            }

            if (scoreRule.getCode().equals("RU0017")) {
                jedis.del("b2b.com.km.socre.rule.ru0017");
            }
        }

        return scoreRuleDAO.updateByPrimaryKey(scoreRule);
    }

    /**
     * 分页查询客户积分规则
     */
    @Override
    public Page queryForPageList(ScoreRule scoreRule, Page page) throws Exception {
        // 获取客户积分规则总数
        int totalNum = scoreRuleDAO.countByExample(scoreRule);
        if (totalNum != 0) {
            page.setRecordCount(totalNum);
            // 设置查询开始结束索引
            scoreRule.setStartIndex(page.getStartIndex());
            scoreRule.setEndIndex(page.getStartIndex() + page.getPageSize());
            page.setDataList(scoreRuleDAO.queryForPage(scoreRule));
        } else {
            page.setRecordCount(0);
            page.setDataList(null);
        }
        return page;
    }

    @Override
    public ScoreRule queryByRuleCode(String ruleCode) throws Exception {
        // TODO Auto-generated method stub
        return scoreRuleDAO.queryScoreRuleByRuleCode(ruleCode);
    }


    public ScoreRuleDAO getScoreRuleDAO() {
        return scoreRuleDAO;
    }

    public void setScoreRuleDAO(ScoreRuleDAO scoreRuleDAO) {
        this.scoreRuleDAO = scoreRuleDAO;
    }

    public ScoreInfoDAO getScoreInfoDAO() {
        return scoreInfoDAO;
    }

    public void setScoreInfoDAO(ScoreInfoDAO scoreInfoDAO) {
        this.scoreInfoDAO = scoreInfoDAO;
    }

    public PersonalityInfoDAO getPersonalityInfoDAO() {
        return personalityInfoDAO;
    }

    public void setPersonalityInfoDAO(PersonalityInfoDAO personalityInfoDAO) {
        this.personalityInfoDAO = personalityInfoDAO;
    }

    public RankDAO getRankDAO() {
        return rankDAO;
    }

    public void setRankDAO(RankDAO rankDAO) {
        this.rankDAO = rankDAO;
    }

}
