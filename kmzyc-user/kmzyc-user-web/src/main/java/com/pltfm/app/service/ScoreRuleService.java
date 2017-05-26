package com.pltfm.app.service;

import java.util.List;

import com.kmzyc.commons.page.Page;
import com.pltfm.app.vobject.ScoreRule;
import com.pltfm.app.vobject.ScoreRuleExample;

/**
 * 客户积分规则业务逻辑接口
 * 
 * @author zhl
 * @since 2013-07-10
 */
public interface ScoreRuleService {
  /**
   * 添加客户积分规则
   * 
   * @param scoreRule 积分规则实体
   * @return
   * @throws Exception 异常信息
   */
  public void addScoreRule(ScoreRule scoreRule) throws Exception;

  /**
   * 多条删除客户积分规则
   * 
   * @param ruleIds 客户积分规则主键集合
   * @return
   * @throws Exception 异常
   */
  public Integer deleteScoreRule(List<String> ruleIds) throws Exception;

  /**
   * 删除单条客户积分规则信息
   * 
   * @param ruleId
   * @return
   * @throws Exception
   */
  public Integer deleteByPrimaryKey(Integer ruleId) throws Exception;

  /**
   * 通过主键修改客户积分规则
   * 
   * @param scoreRule
   * @return
   * @throws Exception
   */
  public Integer updateScoreRule(ScoreRule scoreRule) throws Exception;

  /**
   * 多条件查询客户积分规则
   * 
   * @param example 查询实体
   * @return
   * @throws Excetpion 异常
   */
  public List queryScoreRule(ScoreRuleExample example) throws Exception;

  /**
   * 通过客户积分规则主键查询客户积分规则信息
   * 
   * @param ruleId
   * @return
   * @throws Exception
   */
  public ScoreRule queryByPrimaryKey(Integer ruleId) throws Exception;
  
  /**
   * 通过积分编码获取客户积分规则
   * 
   * @param ruleId
   * @return
   * @throws Exception
   */
  public ScoreRule queryByRuleCode(String ruleCode) throws Exception;
  

  /***
   * 分页查询
   * 
   * @param scoreRule 客户积分规则实体
   * @return
   * @throws Exception 异常
   */
  public Page queryForPageList(ScoreRule scoreRule, Page page) throws Exception;  
  
}
